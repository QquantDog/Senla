package com.senla.services.ride;

import com.senla.dto.ride.*;
import com.senla.models.ride.Ride;
import com.senla.util.service.GenericService;
import jakarta.persistence.Tuple;

import java.util.List;

public interface RideService extends GenericService<Ride, Long> {


    List<Ride> getAllRidesFull();
    Ride initializeRide(RideCreateDto rideCreateDto);

    Ride matchConfirmRide(RideMatchDto rideMatchDto);
    void matchDeclineRide(RideMatchDto rideMatchDto);

    Ride waitForClient(RideProcessDto rideProcessDto);

    Ride startRide(RideProcessDto rideProcessDto);

    Ride endRide(RideEndDto rideEndDto);
    Ride cancelRide(RideCancelDto rideCancelDto);

    Ride activatePromocodeOnRide(RidePromocodeEnterDto ridePromocodeEnterDto);

    Ride rideTip(RideTipDto rideTipDto);
}
