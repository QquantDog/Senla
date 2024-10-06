package com.senla.services.ride;

import com.senla.dao.driver.DriverDao;
import com.senla.dao.match.MatchDao;
import com.senla.dao.payment.PaymentDao;
import com.senla.dao.promocode.PromocodeDao;
import com.senla.dao.rate.RateDao;
import com.senla.dao.ride.IRideDao;
import com.senla.dao.ride.RideDao;

import com.senla.dao.shift.ShiftDao;
import com.senla.dao.user.UserDao;
import com.senla.dto.ride.*;
import com.senla.exceptions.DaoException;
import com.senla.models.driver.Driver;
import com.senla.models.match.Match;
import com.senla.models.payment.Payment;
import com.senla.models.promocode.Promocode;
import com.senla.models.rate.Rate;
import com.senla.models.ride.Ride;
import com.senla.models.ride.RideStatus;
import com.senla.models.shift.Shift;
import com.senla.models.user.User;
import com.senla.util.service.AbstractLongIdGenericService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.Tuple;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Component
public class RideServiceImpl extends AbstractLongIdGenericService<Ride> implements RideService {

    @Autowired
    private RideDao rideDao;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserDao userDao;
    @Autowired
    private RateDao rateDao;
    @Autowired
    private ShiftDao shiftDao;
    @Autowired
    private MatchDao matchDao;
    @Autowired
    private PromocodeDao promocodeDao;
    @Autowired
    private DriverDao driverDao;
    @Autowired
    private PaymentDao paymentDao;

    @PostConstruct
    @Override
    public void init() {
        super.abstractDao = rideDao;
    }

    @Override
    public List<Ride> getAllRidesFull() {
        return rideDao.getFullRides();
    }


    @Override
    @Transactional
    public Ride initializeRide(RideCreateDto rideCreateDto) {

        Long customerId = rideCreateDto.getCustomerId();
        Long rateId = rideCreateDto.getRateId();
        String paymentMethod = rideCreateDto.getPaymentMethod();

        List<Ride> activeRides = rideDao.findActiveRide(customerId);
        if(activeRides.size() > 1) throw new RuntimeException("Inconsistent rides state - more than 1 active ride");
        if(activeRides.size() == 1) throw new DaoException("Could not create ride - placed ride already exists");

        Ride rideToStart = modelMapper.map(rideCreateDto, Ride.class);

//        здесь автовыкинетчся если не customer
        User customer = userDao.findCustomer(customerId);
        rideToStart.setCustomer(customer);

        rideToStart.setCreatedAt(LocalDateTime.now());

        Payment payment = Payment.builder().method(paymentMethod).build();
        rideToStart.setPayment(payment);

//        ПОМенять на нормалбный jpql
        Optional<Rate> rateResp = rateDao.findById(rateId);
        if(rateResp.isEmpty()) throw new DaoException("Could not find rate with id " + rateId);
        Rate rate = rateResp.get();
        rideToStart.setRate(rate);

//        обсчет идет через бд - единственное место где можно поменять на обсчет на сервере
        BigDecimal expectedDistance = BigDecimal.valueOf(rideDao.getMinimalCartesianDistance(
                rideCreateDto.getStartPoint(), rideCreateDto.getEndPoint()
        ));
        rideToStart.setRideExpectedPrice(calculateExpectedPricePerDistance(expectedDistance, rate).setScale(1, RoundingMode.UP));
        rideToStart.setRideDistanceExpectedMeters(expectedDistance.setScale(0, RoundingMode.CEILING));
        rideToStart.setStatus(RideStatus.PENDING);

//        find shifts and drivers - capable to
//        и возможно с шедулингом
        List<Shift> matchedShifts = shiftDao.getMatchingShifts(rateId, rideToStart.getStartPoint(), 20000.0);
        matchedShifts.forEach(shift -> {
            Match match = Match.builder().ride(rideToStart).shift(shift).upperThreshold(LocalDateTime.now().plusHours(1)).build();
            matchDao.create(match);
        });


        return abstractDao.create(rideToStart);
    }

//    это делает driver - драйвер становитя onRide - шифт становится

    @Override
    @Transactional
    public Ride matchConfirmRide(RideMatchDto rideMatchDto){
        Long shiftId = rideMatchDto.getShiftId();
        Long rideId = rideMatchDto.getRideId();

        Tuple rideAndShift = rideDao.matchRideAndShift(rideId, shiftId);
        Ride ride = rideAndShift.get(0, Ride.class);
        Shift shift = rideAndShift.get(1, Shift.class);

        if(ride.getStatus() != RideStatus.PENDING) throw new DaoException("Ride is not at PENDING state: " + ride.getStatus());

        ride.setShift(shift);
        ride.setRideAcceptedAt(LocalDateTime.now());
        ride.setStatus(RideStatus.ACCEPTED);

        Driver driver = ride.getShift().getDriver();
        driver.setIsOnRide(true);
        driverDao.update(driver);

        matchDao.clearRideEntriesOnAccept(shift.getShiftId());


        return rideDao.update(ride);
    }

    @Override
    @Transactional
    public void matchDeclineRide(RideMatchDto rideMatchDto) {
        Long shiftId = rideMatchDto.getShiftId();
        Long rideId = rideMatchDto.getRideId();

        Match match = matchDao.matchRideAndShift(rideId, shiftId);
        matchDao.deleteById(match.getId());
    }

    @Override
    @Transactional
    public Ride waitForClient(RideProcessDto rideProcessDto){
        Long driverId = rideProcessDto.getDriverId();
        Long rideId = rideProcessDto.getRideId();

        Ride ride = rideDao.verifyRideByDriver(rideId, driverId);

        if(ride.getStatus() != RideStatus.ACCEPTED) throw new DaoException("Ride is not at ACCEPTED state: " + ride.getStatus());
        ride.setStatus(RideStatus.WAITING_CLIENT);
        ride.setRideDriverWaiting(LocalDateTime.now());

        return abstractDao.update(ride);
    }

    @Override
    @Transactional
    public Ride startRide(RideProcessDto rideProcessDto){
        Long driverId = rideProcessDto.getDriverId();
        Long rideId = rideProcessDto.getRideId();

        Ride ride = rideDao.verifyRideByDriver(rideId, driverId);
        if(ride.getStatus() != RideStatus.ACCEPTED &&
                ride.getStatus() != RideStatus.WAITING_CLIENT) throw new DaoException("Ride is not at ACCEPTED/WAITING_CLIENT state: " + ride.getStatus());

        LocalDateTime startTime = LocalDateTime.now();
        if(ride.getStatus() == RideStatus.ACCEPTED) ride.setRideDriverWaiting(startTime);
        ride.setRideStartTime(startTime);
        ride.setStatus(RideStatus.IN_WAY);

        return abstractDao.update(ride);
    }

    @Override
    @Transactional
    public Ride endRide(RideEndDto rideEndDto) {
        Long driverId = rideEndDto.getDriverId();
        Long rideId = rideEndDto.getRideId();

        Ride ride = rideDao.verifyRideByDriver(rideId, driverId);

        if(ride.getStatus() != RideStatus.IN_WAY) throw new DaoException("Ride is not in IN_WAY state: " + ride.getStatus());
        ride.setStatus(RideStatus.COMPLETED);
        ride.setRideEndTime(LocalDateTime.now());

        Optional<Rate> rateResp = rateDao.findById(ride.getRate().getId());
        if(rateResp.isEmpty()) throw new DaoException("Could not find rate with id " + ride.getRate().getId());
        Rate rate = rateResp.get();

        if(rideEndDto.getActualDistance() == null) {
            ride.setRideDistanceActualMeters(ride.getRideDistanceExpectedMeters());
            ride.setRideActualPrice(ride.getRideExpectedPrice());
        }
        else {
            ride.setRideDistanceActualMeters(rideEndDto.getActualDistance().setScale(0, RoundingMode.CEILING));
            ride.setRideActualPrice(calculateActualPricePerTrip(ride, rate).setScale(1, RoundingMode.UP));
        }

        Driver driver = ride.getShift().getDriver();
        driver.setIsOnRide(false);
        driverDao.update(driver);

        Payment payment = ride.getPayment();
        payment.setOverallPrice(calculateFinishPrice(ride));
        paymentDao.update(payment);

        return abstractDao.update(ride);
    }

    @Override
    @Transactional
    public Ride cancelRide(RideCancelDto rideCancelDto) {
        Long customerId = rideCancelDto.getCustomerId();
        Long rideId = rideCancelDto.getRideId();

//        это можно вытянут только ride
        Ride ride = rideDao.verifyRideByCustomer(rideId, customerId);
        if(ride.getStatus() == RideStatus.CANCELLED || ride.getStatus() == RideStatus.COMPLETED) throw new DaoException("Ride is not active. Current status is " + ride.getStatus());
        LocalDateTime cancelTime = LocalDateTime.now();

        if(ride.getStatus() == RideStatus.ACCEPTED || ride.getStatus() == RideStatus.WAITING_CLIENT || ride.getStatus() == RideStatus.IN_WAY){
            Driver driver = ride.getShift().getDriver();
            driver.setIsOnRide(false);
            driverDao.update(driver);
//            if(ride.getStatus() == RideStatus.ACCEPTED || ride.getStatus() == RideStatus.WAITING_CLIENT) {
////                назначить чето другое не 0 возможно
//                payment.setOverallPrice(BigDecimal.ZERO);
//            }
            if(ride.getStatus() == RideStatus.IN_WAY){
                Payment payment = ride.getPayment();
                payment.setOverallPrice(BigDecimal.valueOf(0.5).multiply(ride.getRideExpectedPrice()).setScale(1, RoundingMode.UP));
                paymentDao.update(payment);
                ride.setPayment(payment);
            }
        }
        ride.setRideEndTime(cancelTime);
        ride.setStatus(RideStatus.CANCELLED);
        return abstractDao.update(ride);
    }

    @Override
    @Transactional
    public Ride activatePromocodeOnRide(RidePromocodeEnterDto ridePromocodeEnterDto){
        Long customerId = ridePromocodeEnterDto.getCustomerId();
        Long rideId = ridePromocodeEnterDto.getRideId();

        Ride ride = rideDao.verifyRideByCustomer(rideId, customerId);
        if(ride.getStatus() == RideStatus.COMPLETED || ride.getStatus() == RideStatus.CANCELLED) throw new DaoException("Cannot activate promocode - ride is either finished or aborted");

        String code = ridePromocodeEnterDto.getPromocodeEnterCode();
        Promocode promocode = promocodeDao.findByCode(code);
        ride.setPromocode(promocode);

        return abstractDao.update(ride);
    }

    @Override
    @Transactional
    public Ride rideTip(RideTipDto rideTipDto) {
        Long customerId = rideTipDto.getCustomerId();
        Long rideId = rideTipDto.getRideId();

        Ride ride = rideDao.verifyRideByCustomer(rideId, customerId);
        ride.setRideTip(rideTipDto.getTipAmount());
        return abstractDao.update(ride);
    }


    private BigDecimal calculateExpectedPricePerDistance(BigDecimal dist, Rate rate) {
        return rate.getInitPrice().add(dist.multiply(rate.getRatePerKm().divide(BigDecimal.valueOf(1000), RoundingMode.UP))).setScale(1, RoundingMode.UP);
    }

    private BigDecimal calculateActualPricePerTrip(Ride ride, Rate rate) {
        BigDecimal distancePrice = ride.getRideDistanceActualMeters().multiply(rate.getRatePerKm().divide(BigDecimal.valueOf(1000), RoundingMode.UP));
        BigDecimal initPrice = rate.getInitPrice();
        BigDecimal seconds = BigDecimal.valueOf(Duration.between(ride.getRideStartTime(), ride.getRideDriverWaiting()).toSeconds());
        BigDecimal paidDelta = seconds.compareTo(BigDecimal.valueOf(rate.getFreeTimeInSeconds())) > 0 ?
                    seconds
                        .subtract( BigDecimal.valueOf(rate.getFreeTimeInSeconds()))
                        .multiply(rate
                                    .getPaidWaitingPerMinute()
                                    .divide(BigDecimal.valueOf(60), RoundingMode.UP)
                        ) :
                BigDecimal.ZERO;

        return initPrice.add(distancePrice).add(paidDelta).setScale(1, RoundingMode.UP);
    }
    private BigDecimal calculateFinishPrice(Ride ride) {
        BigDecimal actualPrice = ride.getRideActualPrice();
        BigDecimal rideTip = ride.getRideTip() == null ? BigDecimal.ZERO : ride.getRideTip();
        BigDecimal discount = ride.getPromocode() == null ? BigDecimal.ZERO : ride.getPromocode().getDiscountValue();
        return actualPrice.multiply(BigDecimal.ONE.subtract(discount)).add(rideTip).setScale(1, RoundingMode.UP);
    }

}
