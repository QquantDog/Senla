package com.senla.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.senla.dto.customerrating.CustomerRatingCreateDto;
import com.senla.dto.customerrating.CustomerRatingResponseDto;
import com.senla.dto.customerrating.CustomerRatingUpdateDto;
import com.senla.models.customerrating.CustomerRating;
import com.senla.services.customerrating.CustomerRatingService;
import com.senla.services.promocode.PromocodeService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customer-ratings")
public class CustomerRatingController {

    private final CustomerRatingService customerRatingService;
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerRatingController(CustomerRatingService customerRatingService, ModelMapper modelMapper, PromocodeService promocodeService) {
        this.customerRatingService = customerRatingService;
        this.modelMapper = modelMapper;
    }

//    только для админа с серчем - для пользователя нельзя отдавать
    @GetMapping("/all")
    public ResponseEntity<List<CustomerRatingResponseDto>> getAllCustomerRatings() throws JsonProcessingException {
        List<CustomerRating> ratings = customerRatingService.findAll();
        return new ResponseEntity<>(ratings.stream().map(this::convertToResponseDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<CustomerRatingResponseDto> rateDriver(@Valid @RequestBody CustomerRatingCreateDto customerRatingCreateDto) throws JsonProcessingException {
        CustomerRating customerRating = customerRatingService.createCustomerRate(customerRatingCreateDto);
        return new ResponseEntity<>(convertToResponseDto(customerRating), HttpStatus.CREATED);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<CabResponseDto> getUserById(@PathVariable("id") Long id) throws JsonProcessingException, NotFoundByIdException {
//        if(cab.isPresent()){
//            Optional<Cab> cab = this.customerRatingService.findById(id);
//            return new ResponseEntity<>(convertToResponseDto(cab.get()), HttpStatus.OK);
//        } else{
//            throw new NotFoundByIdException(User.class);
//        }
//    }


    @PutMapping()
    public ResponseEntity<CustomerRatingResponseDto> updateCustomerRating(@Valid @RequestBody CustomerRatingUpdateDto customerRatingUpdateDto) throws JsonProcessingException {
        return new ResponseEntity<>(convertToResponseDto(customerRatingService.updateCustomerRate(customerRatingUpdateDto)), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomerRating(@PathVariable("id") Long id) {
        customerRatingService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private CustomerRatingResponseDto convertToResponseDto(CustomerRating customerRating) {
        return modelMapper.map(customerRating, CustomerRatingResponseDto.class);
    }
}
