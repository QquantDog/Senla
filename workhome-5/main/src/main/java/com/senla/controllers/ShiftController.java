package com.senla.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.dto.shift.ShiftCreateDto;
import com.senla.dto.shift.ShiftResponseDto;
import com.senla.dto.shift.ShiftUpdateDto;
import com.senla.models.shift.Shift;
import com.senla.services.ShiftService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ShiftController {
    private final ShiftService shiftService;
    private final ObjectMapper jsonMapper;
    private final ModelMapper modelMapper;

    @Autowired
    public ShiftController(ShiftService shiftService, ObjectMapper jsonMapper, ModelMapper modelMapper) {
        this.shiftService = shiftService;
        this.jsonMapper = jsonMapper;
        this.modelMapper = modelMapper;
    }

    public String getAllShifts() throws JsonProcessingException {
        Collection<Shift> shifts = this.shiftService.findAll();
        return buildShiftResponse(
                shifts.stream()
                        .map(this::convertToResponseDto)
                        .collect(Collectors.toList()));
    }

    public String getShiftById(Long id) throws JsonProcessingException {
        Optional<Shift> shift = this.shiftService.findById(id);
        if(shift.isPresent()){
            return buildShiftResponse(convertToResponseDto(shift.get()));
        } else{
            return "No Shift found";
        }
    }
    public String createDriver(ShiftCreateDto shiftCreateDto) throws JsonProcessingException {
        Shift shift = this.modelMapper.map(shiftCreateDto, Shift.class);
        return buildShiftResponse(convertToResponseDto(shiftService.save(shift)));
    }
    public String updateShift(ShiftUpdateDto shiftUpdateDto) throws JsonProcessingException {
        Shift shift = this.modelMapper.map(shiftUpdateDto, Shift.class);
        return buildShiftResponse(convertToResponseDto(shiftService.save(shift)));
    }

    public void deleteRide(Long id) {
        shiftService.deleteById(id);
    }

    public void existsShift(Long id) {
        System.out.println("Does exist shift with ID: " + id +  " " + shiftService.existsById(id));
    }

    private ShiftResponseDto convertToResponseDto(Shift shift) {
        return modelMapper.map(shift, ShiftResponseDto.class);
    }
    private String buildShiftResponse(List<ShiftResponseDto> shiftResponseDtos) throws JsonProcessingException {
        return jsonMapper.writeValueAsString(shiftResponseDtos);
    }
    private String buildShiftResponse(ShiftResponseDto shiftResponseDto) throws JsonProcessingException {
        return jsonMapper.writeValueAsString(shiftResponseDto);
    }
}

