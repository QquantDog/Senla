package com.senla.dao.shift;

import com.senla.models.ride.RideStatus;
import com.senla.models.shift.Shift;
import com.senla.util.dao.GenericDao;
import jakarta.persistence.Tuple;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface IShiftDao extends GenericDao<Shift, Long> {
    Optional<Shift> findByIdJPQL(Long userId);
    List<Shift> findAllByJPQL();
//    List<Shift> findShiftsWithinCityAndStatuses(String cityName, List<RideStatus> statuses);
    List<Shift> findOpenShifts();

    List<Shift> findShiftBySpecification(Specification<Shift> shiftSpecification);

    Tuple findShiftDriverCab();

    List<Shift> getFullResponse();
    Shift getSingleResponse(Long shiftId);

    List<Shift> getMatchingShifts(Long rateId, Point customerStartPoint, Double radiusThresholdMeters);
}
