package com.senla.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.senla.dto.shift.ShiftFinishDto;
import com.senla.dto.shift.ShiftFullResponseDto;
import com.senla.dto.shift.ShiftResponseDto;
import com.senla.dto.shift.ShiftStartDto;
import com.senla.models.shift.Shift;
import com.senla.services.shift.ShiftService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/shifts")
public class ShiftController {

    private final ShiftService shiftService;
    private final ModelMapper modelMapper;

    @Autowired
    public ShiftController(ShiftService shiftService, ModelMapper modelMapper) {
        this.shiftService = shiftService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<ShiftFullResponseDto>> getAllShifts() throws JsonProcessingException {
        List<Shift> shifts = shiftService.getShiftFullResponse();
        return new ResponseEntity<>(shifts.stream().map(this::convertToFullResponseDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @PostMapping("/start")
    public ResponseEntity<ShiftFullResponseDto> startShift(@RequestBody @Valid ShiftStartDto shiftStartDto) throws JsonProcessingException {
        Shift shift = shiftService.startShift(shiftStartDto);
        return new ResponseEntity<>(convertToFullResponseDto(shift), HttpStatus.CREATED);
    }

//    Со спринг секьюрити можно не передавать дто
    @PostMapping("/finish")
    public ResponseEntity<ShiftFullResponseDto> finishShift(@RequestBody @Valid ShiftFinishDto shiftFinishDto) throws JsonProcessingException {
        Shift shift = shiftService.finishShift(shiftFinishDto);
        return new ResponseEntity<>(convertToFullResponseDto(shift), HttpStatus.CREATED);
    }
//    @GetMapping("/{id}")
//    public ResponseEntity<CabResponseDto> getUserById(@PathVariable("id") Long id) throws JsonProcessingException, NotFoundByIdException {
//        Optional<Cab> cab = this.cabService.findById(id);
//        if(cab.isPresent()){
//            return new ResponseEntity<>(convertToResponseDto(cab.get()), HttpStatus.OK);
//        } else{
//            throw new NotFoundByIdException(User.class);
//        }
//    }

//    @PostMapping
//    public ResponseEntity<CabFullResponseDto> createUser(@Valid @RequestBody CabCreateDto cabCreateDto) throws JsonProcessingException {
//        Cab cab = cabService.createCab(cabCreateDto);
//        return new ResponseEntity<>(convertToResponseFullDto(cab), HttpStatus.CREATED);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<CabResponseDto> updateUser(@Valid @RequestBody CabUpdateDto cabUpdateDto, @PathVariable("id") Long id) throws JsonProcessingException {
//        return new ResponseEntity<>(convertToResponseDto(cabService.updateCab(id, cabUpdateDto)), HttpStatus.CREATED);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
//        cabService.deleteById(id);
//        return ResponseEntity.noContent().build();
//    }


    private ShiftResponseDto convertToResponseDto(Shift shift){
        return modelMapper.map(shift, ShiftResponseDto.class);
    }
    private ShiftFullResponseDto convertToFullResponseDto(Shift shift){
        return modelMapper.map(shift, ShiftFullResponseDto.class);
    }
//    private ShiftResponseDto convertToResponseDto(Shift shift, ShiftFinishDto shiftFinishDto){
//        return modelMapper.map(shift, ShiftResponseDto.class);
//    }
}
