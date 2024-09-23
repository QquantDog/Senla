package com.senla.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.dto.driver.DriverCreateDto;
import com.senla.dto.driver.DriverResponseDto;
import com.senla.dto.driver.DriverUpdateDto;
import com.senla.models.driver.Driver;
import com.senla.services.DriverService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DriverController {
    private final DriverService driverService;
    private final ObjectMapper jsonMapper;
    private final ModelMapper modelMapper;

    @Autowired
    public DriverController(DriverService driverService, ObjectMapper jsonMapper, ModelMapper modelMapper) {
        this.driverService = driverService;
        this.jsonMapper = jsonMapper;
        this.modelMapper = modelMapper;
    }

    public String getAllDrivers() throws JsonProcessingException {
        Collection<Driver> drivers = this.driverService.findAll();
        return buildRideResponse(
                drivers.stream()
                        .map(this::convertToResponseDto)
                        .collect(Collectors.toList()));
    }

    public String getDriverById(Long id) throws JsonProcessingException {
        Optional<Driver> driver = this.driverService.findById(id);
        if(driver.isPresent()){
            return buildRideResponse(convertToResponseDto(driver.get()));
        } else{
            return "No Driver found";
        }
    }
    public String createDriver(DriverCreateDto driverCreateDto) throws JsonProcessingException {
        Driver driver = this.modelMapper.map(driverCreateDto, Driver.class);
        return buildRideResponse(convertToResponseDto(driverService.save(driver)));
    }
    public String updateDriver(DriverUpdateDto driverUpdateDto) throws JsonProcessingException {
        Driver driver = this.modelMapper.map(driverUpdateDto, Driver.class);
        return buildRideResponse(convertToResponseDto(driverService.save(driver)));
    }

    public void deleteDriver(Long id) {
        driverService.deleteById(id);
    }

    public void existsDriver(Long id) {
        System.out.println("Does exist driver with ID: " + id +  " " + driverService.existsById(id));
    }

    private DriverResponseDto convertToResponseDto(Driver driver) {
        return modelMapper.map(driver, DriverResponseDto.class);
    }
    private String buildRideResponse(List<DriverResponseDto> driverResponseDtos) throws JsonProcessingException {
        return jsonMapper.writeValueAsString(driverResponseDtos);
    }
    private String buildRideResponse(DriverResponseDto driverResponseDto) throws JsonProcessingException {
        return jsonMapper.writeValueAsString(driverResponseDto);
    }
}
