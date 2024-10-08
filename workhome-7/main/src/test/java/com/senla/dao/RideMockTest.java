package com.senla.dao;


import com.senla.dao.match.MatchDao;
import com.senla.dao.rate.RateDao;
import com.senla.dao.ride.RideDao;
import com.senla.dao.shift.ShiftDao;
import com.senla.dao.user.UserDao;
import com.senla.dto.ride.RideCreateDto;
import com.senla.dto.ride.RideProcessDto;
import com.senla.exceptions.DaoException;
import com.senla.models.driver.Driver;
import com.senla.models.rate.Rate;
import com.senla.models.ride.Ride;
import com.senla.models.ride.RideStatus;
import com.senla.models.shift.Shift;
import com.senla.models.user.User;
import com.senla.services.ride.PriceCalculation;
import com.senla.services.ride.RideServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RideMockTest {
    @InjectMocks
    private RideServiceImpl rideService;

    @Mock
    private RideDao rideDao;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private UserDao userDao;
    @Mock
    private RateDao rateDao;
    @Mock
    private ShiftDao shiftDao;
    @Mock
    private MatchDao matchDao;
    @Mock
    private PriceCalculation priceCalculation;


    @Test
    public void rideServiceSuccessfulStartTest() {
        Driver driver = Driver.builder().isOnRide(true).isOnShift(true).build();
        Shift shift = Shift.builder().driver(driver).build();
        Ride rideFromDao = Ride.builder()
                .shift(shift)
                .status(RideStatus.WAITING_CLIENT)
                .rideDriverWaiting(LocalDateTime.now()).build();

        RideProcessDto dto = RideProcessDto.builder()
                .rideId(1L)
                .driverId(1L)
                .build();
        when(rideDao.verifyRideByDriver(dto.getRideId(), dto.getDriverId())).thenReturn(rideFromDao);
        when(rideDao.update(rideFromDao)).thenReturn(rideFromDao);


        Ride ride = rideService.startRide(dto);

        assertNotNull(ride);

        assertNull(ride.getRideEndTime());
        assertEquals(RideStatus.IN_WAY, ride.getStatus());
        assertTrue(ride.getRideStartTime().isAfter(ride.getRideDriverWaiting()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"pending", "cancelled", "in-way", "completed"})
    public void rideServiceFailureStartTest(String status) {
        Ride rideFromDao = Ride.builder()
                .status(RideStatus.fromStatus(status)).build();
        RideProcessDto dto = RideProcessDto.builder()
                .rideId(1L)
                .driverId(1L)
                .build();
        when(rideDao.verifyRideByDriver(dto.getRideId(), dto.getDriverId())).thenReturn(rideFromDao);

        DaoException thrown = Assertions.assertThrows(DaoException.class, ()->rideService.startRide(dto));
        assertTrue(thrown.getMessage().contains("Ride is not at ACCEPTED/WAITING_CLIENT state: "));
    }

    @Test
    public void rideServiceInitializeTest() throws NoSuchMethodException {
        Point rideStartPoint = new GeometryFactory().createPoint(new Coordinate(12.12, 24.24));
        Point rideEndPoint = new GeometryFactory().createPoint(new Coordinate(12.15, 24.27));
        RideCreateDto dto = RideCreateDto.builder()
                .customerId(1L)
                .rateId(1L)
                .paymentMethod("cash")
                .startPoint(rideStartPoint)
                .endPoint(rideEndPoint)
                .build();
        List<Ride> activeRides = new ArrayList<>();
        when(rideDao.findActiveRide(dto.getCustomerId())).thenReturn(activeRides);

        Ride ride = Ride.builder()
                .startPoint(rideStartPoint)
                .endPoint(rideEndPoint)
                .rideId(1L).build();
        when(modelMapper.map(dto, Ride.class)).thenReturn(ride);


        User customer = User.builder().userId(1L).build();
        when(userDao.findCustomer(dto.getCustomerId())).thenReturn(customer);

        Rate rate = Rate.builder().rateId(1L).build();
        when(rateDao.findById(dto.getRateId())).thenReturn(Optional.of(rate));
        double expectedDistance = 1000.0;
        double expectedPrice = 100.0;
        when(rideDao.getMinimalCartesianDistance(rideStartPoint, rideEndPoint)).thenReturn(expectedDistance);
        when(priceCalculation.calculateExpectedPricePerDistance(any(), any())).thenReturn(BigDecimal.valueOf(expectedPrice));
        when(rideDao.create(ride)).thenReturn(ride);
        when(shiftDao.getMatchingShifts(any(), any(), any())).thenReturn(new ArrayList<>());

        Ride rideToInit = rideService.initializeRide(dto);

        assertNotNull(rideToInit);
        assertEquals(RideStatus.PENDING, rideToInit.getStatus());
        assertEquals(1L, ride.getRideId());
        assertEquals(ride.getCustomer(), customer);
        assertEquals(ride.getRate(), rate);
        assertEquals(expectedDistance, ride.getRideDistanceExpectedMeters().doubleValue(), 0.001);

    }

    @Test
    public void rideServiceFailureInitDueToHavingActiveRideTest() {
        RideCreateDto dto = RideCreateDto.builder()
                .customerId(1L)
                .rateId(1L)
                .paymentMethod("cash")
                .startPoint(new GeometryFactory().createPoint(new Coordinate(12.12, 24.24)))
                .endPoint(new GeometryFactory().createPoint(new Coordinate(12.15, 24.27)))
                .build();
        List<Ride> activeRides = new ArrayList<>();
        activeRides.add(new Ride());
        when(rideDao.findActiveRide(dto.getCustomerId())).thenReturn(activeRides);

        DaoException thrown = Assertions.assertThrows(DaoException.class, ()->rideService.initializeRide(dto));
        assertTrue(thrown.getMessage().contains("Could not create ride - placed ride already exists"));
    }
}
