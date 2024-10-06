package com.senla.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.senla.dto.taxicompany.TaxiCompanyCreateDto;
import com.senla.dto.taxicompany.TaxiCompanyResponseDto;
import com.senla.dto.taxicompany.TaxiCompanyUpdateDto;
import com.senla.dto.taxicompany.TaxiCompanyWithCabsResponseDto;
import com.senla.models.taxicompany.TaxiCompany;
import com.senla.services.taxicompany.TaxiCompanyService;
import com.senla.util.NotFoundByIdException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/companies")
public class TaxiCompanyController {

    private final TaxiCompanyService taxiCompanyService;
    private final ModelMapper modelMapper;

    @Autowired
    public TaxiCompanyController(TaxiCompanyService taxiCompanyService, ModelMapper modelMapper) {
        this.taxiCompanyService = taxiCompanyService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<TaxiCompanyResponseDto>> getAllCompanies() throws JsonProcessingException {
        List<TaxiCompany> companies = taxiCompanyService.findAll();
        return new ResponseEntity<>(companies.stream().map(this::convertToResponseDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaxiCompanyResponseDto> getUserById(@PathVariable("id") Long id) throws JsonProcessingException, NotFoundByIdException {
        Optional<TaxiCompany> company = this.taxiCompanyService.findById(id);
        if(company.isPresent()){
            return new ResponseEntity<>(convertToResponseDto(company.get()), HttpStatus.OK);
        } else{
            throw new NotFoundByIdException(TaxiCompany.class);
        }
    }

//    @GetMapping("/cabs")
//    public ResponseEntity<List<TaxiCompanyWithCabsResponseDto>> getAllCabs() throws JsonProcessingException {
//        List<TaxiCompany> companies = taxiCompanyService.findAll();
//        List<TaxiCompanyWithCabsResponseDto> arr = new ArrayList<>();
//        companies.forEach(c -> arr.add(modelMapper.map(c, TaxiCompanyWithCabsResponseDto.class)));
//        return new ResponseEntity<>(arr, HttpStatus.OK);
//    }

    @PostMapping
    public ResponseEntity<TaxiCompanyResponseDto> createUser(@Valid @RequestBody TaxiCompanyCreateDto taxiCompanyCreateDto) throws JsonProcessingException {
        return new ResponseEntity<>(convertToResponseDto(taxiCompanyService.createTaxiCompany(taxiCompanyCreateDto)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaxiCompanyResponseDto> updateUser(@Valid @RequestBody TaxiCompanyUpdateDto taxiCompanyUpdateDto, @PathVariable("id") Long id) throws JsonProcessingException {
        return new ResponseEntity<>(convertToResponseDto(taxiCompanyService.updateTaxiCompany(id, taxiCompanyUpdateDto)), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        taxiCompanyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private TaxiCompanyResponseDto convertToResponseDto(TaxiCompany taxiCompany){
        return modelMapper.map(taxiCompany, TaxiCompanyResponseDto.class);
    }

    private TaxiCompanyWithCabsResponseDto convertToFullResponseDto(TaxiCompany taxiCompany){
        return modelMapper.map(taxiCompany, TaxiCompanyWithCabsResponseDto.class);
    }
}
