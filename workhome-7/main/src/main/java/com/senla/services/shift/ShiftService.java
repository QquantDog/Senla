package com.senla.services.shift;

import com.senla.dto.shift.ShiftFinishDto;
import com.senla.dto.shift.ShiftStartDto;
import com.senla.models.shift.Shift;
import com.senla.util.service.GenericService;

import java.time.LocalDateTime;
import java.util.List;

public interface ShiftService extends GenericService<Shift, Long> {
    void findByIdJPQL(Long userId);
    void findAllByJPQL();
//    void findShiftsWithinCityAndStatuses(String cityName, List<RideStatus> statuses);

    void findShiftBySpecification(LocalDateTime startFrom, LocalDateTime startTo, LocalDateTime endFrom, LocalDateTime endTo, boolean isActive);

    Shift startShift(ShiftStartDto shiftStartDto);
    Shift finishShift(ShiftFinishDto shiftFinishDto);

    List<Shift> getShiftFullResponse();
//    void justTransactionalTest();
}
