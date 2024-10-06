package com.senla.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.senla.dto.user.UserCreateDto;
import com.senla.dto.user.UserResponseWithRoleDto;
import com.senla.dto.vehicle.VehicleCreateDto;
import com.senla.dto.vehicle.VehicleResponseDto;
import com.senla.dto.vehicle.VehicleUpdateDto;
import com.senla.models.user.User;
import com.senla.dto.user.UserResponseDto;
import com.senla.models.vehicle.Vehicle;
import com.senla.services.vehicle.VehicleService;
import com.senla.util.NotFoundByIdException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;
    private final ModelMapper modelMapper;

    @Autowired
    public VehicleController(VehicleService vehicleService, ModelMapper modelMapper) {
        this.vehicleService = vehicleService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<VehicleResponseDto>> getAllUsers() throws JsonProcessingException {
        List<Vehicle> vehicles = vehicleService.findAll();
        return new ResponseEntity<>(vehicles.stream().map(this::convertToResponseDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponseDto> getUserById(@PathVariable("id") Long id) throws JsonProcessingException, NotFoundByIdException {
        Optional<Vehicle> vehicle = this.vehicleService.findById(id);
        if(vehicle.isPresent()){
            return new ResponseEntity<>(convertToResponseDto(vehicle.get()), HttpStatus.OK);
        } else{
            throw new NotFoundByIdException(User.class);
        }
    }

    @PostMapping
    public ResponseEntity<VehicleResponseDto> createUser(@Valid @RequestBody VehicleCreateDto vehicleCreateDto) throws JsonProcessingException {
        Vehicle vehicle = vehicleService.createVehicle(vehicleCreateDto);
        return new ResponseEntity<>(convertToResponseDto(vehicle), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponseDto> updateUser(@Valid @RequestBody VehicleUpdateDto vehicleUpdateDto, @PathVariable("id") Long id) throws JsonProcessingException {
        Vehicle vehicle = vehicleService.updateVehicle(id, vehicleUpdateDto);
        return new ResponseEntity<>(convertToResponseDto(vehicle), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        vehicleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private VehicleResponseDto convertToResponseDto(Vehicle vehicle){
        return modelMapper.map(vehicle, VehicleResponseDto.class);
    }
//    private VehicleResponseDto convertToResponseWithRoleDto(User user){
//        return modelMapper.map(user, UserResponseWithRoleDto.class);
//    }

}
