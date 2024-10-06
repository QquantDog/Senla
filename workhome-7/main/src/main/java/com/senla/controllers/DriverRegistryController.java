package com.senla.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.senla.dto.driverregistry.DriverRegistryCreateDto;
import com.senla.dto.driverregistry.DriverRegistryFullResponseDto;
import com.senla.dto.driverregistry.DriverRegistryUpdateDto;
import com.senla.dto.driverregistry.DriverRegistryWithCompanyResponseDto;
import com.senla.exceptions.DaoCheckedException;
import com.senla.models.driverregistry.DriverRegistry;
import com.senla.services.driverregistry.DriverRegistryService;
import com.senla.util.NotFoundByIdException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/registry")
public class DriverRegistryController {

    private final DriverRegistryService driverRegistryService;
    private final ModelMapper modelMapper;

    @Autowired
    public DriverRegistryController(DriverRegistryService driverRegistryService, ModelMapper modelMapper) {
        this.driverRegistryService = driverRegistryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    public ResponseEntity<List<DriverRegistryFullResponseDto>> getAllEntries() throws JsonProcessingException {
        List<DriverRegistry> entries = driverRegistryService.findAllEntries();
        return new ResponseEntity<>(entries.stream().map(this::convertToFullResponseDto).collect(Collectors.toList()), HttpStatus.OK);
    }

//    это переделать с секьюрити на без pathvariable
    @GetMapping("/{driverId}")
    public ResponseEntity<List<DriverRegistryWithCompanyResponseDto>> getEntriesForDriver(@PathVariable("driverId") Long driverId) throws JsonProcessingException, NotFoundByIdException {
        List<DriverRegistry> driverRegistry = this.driverRegistryService.findDriverCompanies(driverId);
        return new ResponseEntity<>(driverRegistry.stream().map(this::convertToCompanyResponseDto).collect(Collectors.toList()), HttpStatus.OK);
    }


    @PostMapping("/register")
    public ResponseEntity<DriverRegistryWithCompanyResponseDto> registerDriverInCompany(@Valid @RequestBody DriverRegistryCreateDto driverRegistryCreateDto) throws JsonProcessingException, DaoCheckedException {
        DriverRegistry driverRegistry = driverRegistryService.registerDriverId(driverRegistryCreateDto);
        return new ResponseEntity<>(this.convertToCompanyResponseDto(driverRegistry), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<DriverRegistryWithCompanyResponseDto> update(@Valid @RequestBody DriverRegistryUpdateDto driverRegistryUpdateDto) throws JsonProcessingException {
        return new ResponseEntity<>(convertToCompanyResponseDto(driverRegistryService.updateRegistration(driverRegistryUpdateDto)), HttpStatus.CREATED);
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteEntry(@PathVariable("id") Long id) {
//        driverRegistryService.deleteById(id);
//        return ResponseEntity.noContent().build();
//    }

    private DriverRegistryWithCompanyResponseDto convertToCompanyResponseDto(DriverRegistry driverRegistry) {
        return modelMapper.map(driverRegistry, DriverRegistryWithCompanyResponseDto.class);
    }

    private DriverRegistryFullResponseDto convertToFullResponseDto(DriverRegistry driverRegistry) {
        return modelMapper.map(driverRegistry, DriverRegistryFullResponseDto.class);
    }
//    private UserResponseWithRoleDto convertToResponseWithRoleDto(User user){
//        return modelMapper.map(user, UserResponseWithRoleDto.class);
//    }

}
