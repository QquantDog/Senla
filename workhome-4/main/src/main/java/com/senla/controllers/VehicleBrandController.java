package com.senla.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.models.vehiclebrand.VehicleBrand;
import com.senla.dto.vehiclebrand.VehicleBrandCreateDto;
import com.senla.dto.vehiclebrand.VehicleBrandResponseDto;
import com.senla.dto.vehiclebrand.VehicleBrandUpdateDto;
import com.senla.services.impl.VehicleBrandService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class VehicleBrandController {

    private final VehicleBrandService vehicleBrandService;
    private final ObjectMapper jsonMapper;
    private final ModelMapper modelMapper;

    @Autowired
    public VehicleBrandController(VehicleBrandService vehicleBrandService, ObjectMapper jsonMapper, ModelMapper modelMapper) {
        this.vehicleBrandService = vehicleBrandService;
        this.jsonMapper = jsonMapper;
        this.modelMapper = modelMapper;
    }

    public String getAllBrands() throws JsonProcessingException {
        Collection<VehicleBrand> brands = this.vehicleBrandService.findAll();
        return buildBrandResponse(
                brands.stream()
                        .map(this::convertToResponseDto)
                        .collect(Collectors.toList()));
    }

    public String getBrandById(Long id) throws JsonProcessingException {
        Optional<VehicleBrand> brand = this.vehicleBrandService.findById(id);
        if(brand.isPresent()){
            return buildBrandResponse(convertToResponseDto(brand.get()));
        } else{
            return "No brand found";
        }
    }
    public String createBrand(VehicleBrandCreateDto vehicleBrandCreateDto) throws JsonProcessingException {
        VehicleBrand vehicleBrand = this.modelMapper.map(vehicleBrandCreateDto, VehicleBrand.class);
        return buildBrandResponse(convertToResponseDto(vehicleBrandService.save(vehicleBrand)));
    }
    public String updateBrand(VehicleBrandUpdateDto vehicleBrandUpdateDto) throws JsonProcessingException {
        VehicleBrand vehicleBrand = this.modelMapper.map(vehicleBrandUpdateDto, VehicleBrand.class);
        return buildBrandResponse(convertToResponseDto(vehicleBrandService.save(vehicleBrand)));
    }
    public String debugGetAllBrands() throws JsonProcessingException {
        Collection<VehicleBrand> brands = this.vehicleBrandService.findAll();
        return jsonMapper.writeValueAsString(brands);
    }

    public void deleteBrand(Long id) {
        vehicleBrandService.deleteById(id);
    }

    public void existsBrand(Long id) {
        System.out.println("Does exist brand with ID: " + id +  " " + vehicleBrandService.existsById(id));
    }

    private VehicleBrandResponseDto convertToResponseDto(VehicleBrand vehicleBrand){
        return modelMapper.map(vehicleBrand, VehicleBrandResponseDto.class);
    }
    private String buildBrandResponse(List<VehicleBrandResponseDto> vehicleBrandResponseDto) throws JsonProcessingException {
        return jsonMapper.writeValueAsString(vehicleBrandResponseDto);
    }
    private String buildBrandResponse(VehicleBrandResponseDto vehicleBrandResponseDto) throws JsonProcessingException {
        return jsonMapper.writeValueAsString(vehicleBrandResponseDto);
    }

}