package com.senla.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.senla.dto.rate.RateCreateDto;
import com.senla.dto.rate.RateFullResponseDto;
import com.senla.dto.rate.RateResponseDto;
import com.senla.dto.rate.RateUpdateDto;

import com.senla.models.rate.Rate;
import com.senla.models.user.User;
import com.senla.services.rate.RateService;
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
@RequestMapping("/rates")
public class RateController {

    private final RateService rateService;
    private final ModelMapper modelMapper;

    @Autowired
    public RateController(RateService rateService, ModelMapper modelMapper) {
        this.rateService = rateService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<RateResponseDto>> getAllUsers() throws JsonProcessingException {
        List<Rate> rates = rateService.findAll();
        return new ResponseEntity<>(rates.stream().map(this::convertToResponseDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RateResponseDto> getUserById(@PathVariable("id") Long id) throws JsonProcessingException, NotFoundByIdException {
        Optional<Rate> rate = this.rateService.findById(id);
        if(rate.isPresent()){
            return new ResponseEntity<>(convertToResponseDto(rate.get()), HttpStatus.OK);
        } else{
            throw new NotFoundByIdException(User.class);
        }
    }

    @PostMapping
    public ResponseEntity<RateFullResponseDto> createUser(@Valid @RequestBody RateCreateDto rateCreateDto) throws JsonProcessingException {
        Rate rate = rateService.createRate(rateCreateDto);
        return new ResponseEntity<>(convertToResponseFullDto(rate), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RateResponseDto> updateUser(@Valid @RequestBody RateUpdateDto rateUpdateDto, @PathVariable("id") Long id) throws JsonProcessingException {
        return new ResponseEntity<>(convertToResponseDto(rateService.updateRate(id, rateUpdateDto)), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        rateService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private RateResponseDto convertToResponseDto(Rate rate){
        return modelMapper.map(rate, RateResponseDto.class);
    }
    private RateFullResponseDto convertToResponseFullDto(Rate rate){
        return modelMapper.map(rate, RateFullResponseDto.class);
    }
}
