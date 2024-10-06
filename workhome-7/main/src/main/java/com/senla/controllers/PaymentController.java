package com.senla.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.senla.dto.cab.CabCreateDto;
import com.senla.dto.cab.CabFullResponseDto;
import com.senla.dto.cab.CabResponseDto;
import com.senla.dto.cab.CabUpdateDto;
import com.senla.dto.payment.PaymentResponseDto;
import com.senla.models.cab.Cab;
import com.senla.models.payment.Payment;
import com.senla.models.user.User;
import com.senla.services.cab.CabService;
import com.senla.services.payment.PaymentService;
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

//ТОЛЬКО для чтения ендпоинт
@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final ModelMapper modelMapper;

    @Autowired
    public PaymentController(PaymentService paymentService, ModelMapper modelMapper) {
        this.paymentService = paymentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<PaymentResponseDto>> getAllPayments() throws JsonProcessingException {
        List<Payment> payments = paymentService.findAll();
        return new ResponseEntity<>(payments.stream().map(this::convertToResponseDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDto> getUserById(@PathVariable("id") Long id) throws JsonProcessingException, NotFoundByIdException {
        Optional<Payment> payment = this.paymentService.findById(id);
        if(payment.isPresent()){
            return new ResponseEntity<>(convertToResponseDto(payment.get()), HttpStatus.OK);
        } else{
            throw new NotFoundByIdException(User.class);
        }
    }
    @GetMapping("customer/{customerId}")
    public ResponseEntity<List<PaymentResponseDto>> getAllPaymentsByCustomer(@PathVariable("customerId") Long customerId) throws JsonProcessingException {
        List<Payment> payments = paymentService.getAllByCustomerId(customerId);
        return new ResponseEntity<>(payments.stream().map(this::convertToResponseDto).collect(Collectors.toList()), HttpStatus.OK);
    }



    private PaymentResponseDto convertToResponseDto(Payment payment){
        return modelMapper.map(payment, PaymentResponseDto.class);
    }
//    private PaymentResponseDto convertToResponseFullDto(Payment payment){
//        return modelMapper.map(payment, PaymentResponseDto.class);
//    }
}
