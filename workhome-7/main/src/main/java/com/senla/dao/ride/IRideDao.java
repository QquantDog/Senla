package com.senla.dao.ride;

import com.senla.models.ride.Ride;
import com.senla.util.dao.GenericDao;
import jakarta.persistence.Tuple;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IRideDao extends GenericDao<Ride, Long> {
    List<Ride> findBySpecification(Specification<Ride> specification);
//    List<Tuple> findRidesWithAvgDistanceMoreThan(BigDecimal distance);
//    List<Ride> findLowestAVGPerDistance();
//    List<Tuple> fullJoin();
    Optional<Ride> getGraph1();
    Optional<Ride> getGraph2();

    Ride getSpecificRideAndCheckCustomer(Long rideId, Long customerId);

    List<Ride> getFullRides();

    List<Ride> findActiveRide(Long customerId);

    Double getMinimalCartesianDistance(Point startPoint, Point endPoint);

    Tuple matchRideAndShift(Long rideId, Long driverId);

    Ride verifyRideByDriver(Long rideId, Long driverId);

    Ride verifyRideByCustomer(Long rideId, Long customerId);

    Ride getRideWithPromocode(Long rideId);
}
