package com.senla.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.senla.dto.cab.*;
import com.senla.models.cab.Cab;
import com.senla.models.user.User;
import com.senla.services.cab.CabService;
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
@RequestMapping("/cabs")
public class CabController {

    private final CabService cabService;
    private final ModelMapper modelMapper;

    @Autowired
    public CabController(CabService cabService, ModelMapper modelMapper) {
        this.cabService = cabService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<CabListResponseDto>> getAllCabs() throws JsonProcessingException {
        List<Cab> cabs = cabService.getAll();
        return new ResponseEntity<>(cabs.stream().map(this::convertToListResponseDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CabResponseDto> getUserById(@PathVariable("id") Long id) throws JsonProcessingException, NotFoundByIdException {
        Optional<Cab> cab = this.cabService.findById(id);
        if(cab.isPresent()){
            return new ResponseEntity<>(convertToResponseDto(cab.get()), HttpStatus.OK);
        } else{
            throw new NotFoundByIdException(User.class);
        }
    }


    @PostMapping
    public ResponseEntity<CabFullResponseDto> createUser(@Valid @RequestBody CabCreateDto cabCreateDto) throws JsonProcessingException {
        Cab cab = cabService.createCab(cabCreateDto);
        return new ResponseEntity<>(convertToResponseFullDto(cab), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CabResponseDto> updateUser(@Valid @RequestBody CabUpdateDto cabUpdateDto, @PathVariable("id") Long id) throws JsonProcessingException {
        return new ResponseEntity<>(convertToResponseDto(cabService.updateCab(id, cabUpdateDto)), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        cabService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private CabResponseDto convertToResponseDto(Cab cab){
        return modelMapper.map(cab, CabResponseDto.class);
    }

    private CabFullResponseDto convertToResponseFullDto(Cab cab){
        return modelMapper.map(cab, CabFullResponseDto.class);
    }
    private CabListResponseDto convertToListResponseDto(Cab cab){
        return modelMapper.map(cab, CabListResponseDto.class);
    }
}
