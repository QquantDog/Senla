package com.senla.services.vehicle;

import com.senla.dto.vehicle.VehicleCreateDto;
import com.senla.dto.vehicle.VehicleUpdateDto;
import com.senla.models.vehicle.Vehicle;
import com.senla.util.service.GenericService;

public interface VehicleService extends GenericService<Vehicle, Long> {
    Vehicle createVehicle(VehicleCreateDto vehicleCreateDto);
    Vehicle updateVehicle(Long id, VehicleUpdateDto vehicleUpdateDto);
}
