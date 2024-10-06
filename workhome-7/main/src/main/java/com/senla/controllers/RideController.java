package com.senla.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.senla.dto.promocode.PromocodeResponseDto;
import com.senla.dto.ride.*;
import com.senla.models.ride.Ride;
import com.senla.models.user.User;
import com.senla.services.ride.RideService;
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
@RequestMapping("/rides")
public class RideController {

    private final RideService rideService;
    private final ModelMapper modelMapper;

    @Autowired
    public RideController(RideService rideService, ModelMapper modelMapper) {
        this.rideService = rideService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<RideFullResponseDto>> getAllRides() throws JsonProcessingException {
        List<Ride> rides = rideService.getAllRidesFull();
        return new ResponseEntity<>(rides.stream().map(this::convertToFullResponseDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RideResponseDto> getRideById(@PathVariable("id") Long id) throws JsonProcessingException, NotFoundByIdException {
        Optional<Ride> ride = this.rideService.findById(id);
        if(ride.isPresent()){
            return new ResponseEntity<>(convertToResponseDto(ride.get()), HttpStatus.OK);
        } else{
            throw new NotFoundByIdException(User.class);
        }
    }

//    это пассажир
    @PostMapping("/init")
    public ResponseEntity<RideFullResponseDto> initializeRide(@Valid @RequestBody RideCreateDto rideCreateDto) throws JsonProcessingException {
        Ride ride = rideService.initializeRide(rideCreateDto);
        return new ResponseEntity<>(convertToFullResponseDto(ride), HttpStatus.CREATED);
    }

//    это водитель
    @PostMapping("/match-confirm")
    public ResponseEntity<RideResponseDto> matchRide(@Valid @RequestBody RideMatchDto rideMatchDto) throws JsonProcessingException {
        Ride ride = rideService.matchConfirmRide(rideMatchDto);
        return new ResponseEntity<>(convertToResponseDto(ride), HttpStatus.CREATED);
    }

    @PostMapping("/match-decline")
    public ResponseEntity<?> declineRide(@Valid @RequestBody RideMatchDto rideMatchDto) throws JsonProcessingException {
        rideService.matchDeclineRide(rideMatchDto);
        return new ResponseEntity<>(ResponseEntity.ok().build(), HttpStatus.OK);
    }

    @PostMapping("/wait")
    public ResponseEntity<RideResponseDto> waitForClientRide(@Valid @RequestBody RideProcessDto rideProcessDto) throws JsonProcessingException {
        Ride ride = rideService.waitForClient(rideProcessDto);
        return new ResponseEntity<>(convertToResponseDto(ride), HttpStatus.CREATED);
    }
    @PostMapping("/start")
    public ResponseEntity<RideResponseDto> startRide(@Valid @RequestBody RideProcessDto rideProcessDto) throws JsonProcessingException {
        Ride ride = rideService.startRide(rideProcessDto);
        return new ResponseEntity<>(convertToResponseDto(ride), HttpStatus.CREATED);
    }
    @PostMapping("/end")
    public ResponseEntity<RideWithPaymentResponseDto> endRide(@Valid @RequestBody RideEndDto rideEndDto) throws JsonProcessingException {
        Ride ride = rideService.endRide(rideEndDto);
        return new ResponseEntity<>(convertToResponseWithPaymentDto(ride), HttpStatus.CREATED);
    }
    //    это для пассажира
    @PostMapping("/cancel")
    public ResponseEntity<RideResponseDto> cancelRide(@Valid @RequestBody RideCancelDto rideCancelDto) throws JsonProcessingException {
        Ride ride = rideService.cancelRide(rideCancelDto);
        return new ResponseEntity<>(convertToResponseDto(ride), HttpStatus.CREATED);
    }

    @PostMapping("/promocodes/activate")
    public ResponseEntity<RideWithPromocodeResponseDto> activatePromocode(@Valid @RequestBody RidePromocodeEnterDto ridePromocodeEnterDto) throws JsonProcessingException {
        Ride ride = rideService.activatePromocodeOnRide(ridePromocodeEnterDto);
        return new ResponseEntity<>(convertToResponseWithPromocodeDto(ride), HttpStatus.CREATED);
    }

    @PostMapping("/tip")
    public ResponseEntity<RideResponseDto> tipDriver(@Valid @RequestBody RideTipDto rideTipDto) throws JsonProcessingException {
        Ride ride = rideService.rideTip(rideTipDto);
        return new ResponseEntity<>(convertToResponseDto(ride), HttpStatus.CREATED);
    }


    private RideFullResponseDto convertToFullResponseDto(Ride ride){
        return modelMapper.map(ride, RideFullResponseDto.class);
    }
    private RideWithPromocodeResponseDto convertToResponseWithPromocodeDto(Ride ride){
        return modelMapper.map(ride, RideWithPromocodeResponseDto.class);
    }
    private RideWithPaymentResponseDto convertToResponseWithPaymentDto(Ride ride){
        return modelMapper.map(ride, RideWithPaymentResponseDto.class);
    }
    private RideResponseDto convertToResponseDto(Ride ride){
        return modelMapper.map(ride, RideResponseDto.class);
    }

}