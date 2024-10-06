package com.senla.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.dto.cab.CabResponseDto;
import com.senla.dto.promocode.PromocodeCreateDto;
import com.senla.dto.promocode.PromocodeResponseDto;
import com.senla.dto.promocode.PromocodeUpdateDto;
import com.senla.exceptions.DaoCheckedException;
import com.senla.exceptions.DaoException;
import com.senla.models.cab.Cab;
import com.senla.models.promocode.Promocode;
import com.senla.models.user.User;
import com.senla.services.promocode.PromocodeService;
import com.senla.util.NotFoundByIdException;
import jakarta.validation.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/promocodes")
public class PromocodeController {
    private final PromocodeService promocodeService;
    private final ModelMapper modelMapper;

    @Autowired
    public PromocodeController(PromocodeService promocodeService, ObjectMapper jsonMapper, ModelMapper modelMapper) {
        this.promocodeService = promocodeService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<PromocodeResponseDto>> getAllPromocodes() {
        List<Promocode> promocodes = this.promocodeService.findAll();
        return new ResponseEntity<>(promocodes.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }


//    @GetMapping("/search")
//    public ResponseEntity<List<PromocodeResponseDto>> searchPromocodes(
//            @RequestParam(name = "code", required = false) String codeParam,
//            @RequestParam(name = "start-date", required = false) List<LocalDate> startDateParam,
//            @RequestParam(name = "end-date", required = false) List<LocalDate> endDateParam,
//            @RequestParam(name = "discount", required = false) List<BigDecimal> discountParam
//    ) {
//        List<Promocode> promocodes = this.promocodeService.findPromocodesBySpecification(codeParam, startDateParam, endDateParam, discountParam);
//        List<PromocodeResponseDto> dtos = promocodes.stream()
//                .map(this::convertToResponseDto)
//                .collect(Collectors.toList());
//        return new ResponseEntity<>(dtos, HttpStatus.OK);
//    }


    @GetMapping("/{id}")
    public ResponseEntity<PromocodeResponseDto> getUserById(@PathVariable("id") Long id) throws JsonProcessingException, NotFoundByIdException {
        Optional<Promocode> promocode = this.promocodeService.findById(id);
        if(promocode.isPresent()){
            return new ResponseEntity<>(convertToResponseDto(promocode.get()), HttpStatus.OK);
        } else{
            throw new NotFoundByIdException(User.class);
        }
    }

    @PostMapping
    public ResponseEntity<PromocodeResponseDto> createPromocode(@Valid @RequestBody PromocodeCreateDto promocodeCreateDto, BindingResult bindingResult) throws JsonProcessingException, DaoException, NoSuchMethodException, MethodArgumentNotValidException {
        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
            throw new MethodArgumentNotValidException(new MethodParameter(this.getClass().getMethod("createPromocode", PromocodeCreateDto.class, BindingResult.class), 0), bindingResult);
        }
        Promocode resp = promocodeService.create(modelMapper.map(promocodeCreateDto, Promocode.class));
        return new ResponseEntity<>(modelMapper.map(resp, PromocodeResponseDto.class), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PromocodeResponseDto> updatePromocode(@Valid @RequestBody PromocodeUpdateDto promocodeUpdateDto, BindingResult bindingResult, @PathVariable("id") Long id) throws JsonProcessingException, DaoCheckedException, NoSuchMethodException, MethodArgumentNotValidException {
        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
            throw new MethodArgumentNotValidException(new MethodParameter(this.getClass().getMethod("updatePromocode", PromocodeUpdateDto.class, BindingResult.class, Long.class), 0), bindingResult);
        }
        Promocode resp = promocodeService.updatePromocode(id, promocodeUpdateDto);
        return new ResponseEntity<>(modelMapper.map(resp, PromocodeResponseDto.class), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePromocode(@PathVariable("id") Long id) {
        promocodeService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private PromocodeResponseDto convertToResponseDto(Promocode promocode) {
        return modelMapper.map(promocode, PromocodeResponseDto.class);
    }
}