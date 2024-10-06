package com.senla.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.senla.dto.driver.DriverResponseDto;
import com.senla.dto.driver.DriverUpdateDto;
import com.senla.exceptions.DaoCheckedException;
import com.senla.models.driver.Driver;
import com.senla.models.user.User;
import com.senla.services.driver.DriverService;
import com.senla.util.NotFoundByIdException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/drivers")
public class DriverController {
    private final DriverService driverService;
    private final ModelMapper modelMapper;

    @Autowired
    public DriverController(DriverService driverService, ModelMapper modelMapper) {
        this.driverService = driverService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<DriverResponseDto>> getAllDrivers() throws JsonProcessingException {
        Collection<Driver> drivers = this.driverService.findAll();
        return new ResponseEntity<>(drivers.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverResponseDto> getDriverById(@PathVariable("id") Long id) throws JsonProcessingException, NotFoundByIdException {
        Optional<Driver> driver = this.driverService.findById(id);
        if(driver.isPresent()){
            return new ResponseEntity<>(convertToResponseDto(driver.get()), HttpStatus.OK);
        } else{
            throw new NotFoundByIdException(User.class);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<DriverResponseDto> updateDriver(@Valid @RequestBody DriverUpdateDto driverUpdateDto, BindingResult bindingResult, @PathVariable("id") Long id) throws JsonProcessingException, DaoCheckedException {
        Driver driver = this.modelMapper.map(driverUpdateDto, Driver.class);
        return new ResponseEntity<>(convertToResponseDto(driverService.updateDriver(id, driver)), HttpStatus.CREATED);
    }

    public ResponseEntity<?> deleteDriver(Long id) {
        driverService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public void existsDriver(Long id) {
        System.out.println("Does exist driver with ID: " + id +  " " + driverService.existsById(id));
    }

    private DriverResponseDto convertToResponseDto(Driver driver) {
        return modelMapper.map(driver, DriverResponseDto.class);
    }
}
