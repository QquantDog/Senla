package com.senla.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.models.ride.Ride;
import com.senla.dto.ride.RideCreateDto;
import com.senla.dto.ride.RideResponseDto;
import com.senla.dto.ride.RideUpdateDto;
import com.senla.services.RideService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RideController {

    private final RideService rideService;
    private final ObjectMapper jsonMapper;
    private final ModelMapper modelMapper;

    @Autowired
    public RideController(RideService rideService, ObjectMapper jsonMapper, ModelMapper modelMapper) {
        this.rideService = rideService;
        this.jsonMapper = jsonMapper;
        this.modelMapper = modelMapper;
    }

    public String getAllRides() throws JsonProcessingException {
        Collection<Ride> rides = this.rideService.findAll();
        return buildRideResponse(
                rides.stream()
                        .map(this::convertToResponseDto)
                        .collect(Collectors.toList()));
    }

    public String getRideById(Long id) throws JsonProcessingException {
        Optional<Ride> ride = this.rideService.findById(id);
        if(ride.isPresent()){
            return buildRideResponse(convertToResponseDto(ride.get()));
        } else{
            return "No Ride found";
        }
    }
    public String createRide(RideCreateDto rideCreateDto) throws JsonProcessingException {
        Ride ride = this.modelMapper.map(rideCreateDto, Ride.class);
        return buildRideResponse(convertToResponseDto(rideService.save(ride)));
    }
    public String updateRide(RideUpdateDto rideUpdateDto) throws JsonProcessingException {
        Ride ride = this.modelMapper.map(rideUpdateDto, Ride.class);
        return buildRideResponse(convertToResponseDto(rideService.save(ride)));
    }

    public void deleteRide(Long id) {
        rideService.deleteById(id);
    }

    public void existsRide(Long id) {
        System.out.println("Does exist brand with ID: " + id +  " " + rideService.existsById(id));
    }

    private RideResponseDto convertToResponseDto(Ride ride){
        return modelMapper.map(ride, RideResponseDto.class);
    }
    private String buildRideResponse(List<RideResponseDto> rideResponseDto) throws JsonProcessingException {
        return jsonMapper.writeValueAsString(rideResponseDto);
    }
    private String buildRideResponse(RideResponseDto rideResponseDto) throws JsonProcessingException {
        return jsonMapper.writeValueAsString(rideResponseDto);
    }
}
