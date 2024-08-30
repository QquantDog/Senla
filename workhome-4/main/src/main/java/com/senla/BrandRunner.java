package com.senla;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.senla.config.SpringConfig;
import com.senla.controllers.VehicleBrandController;
import com.senla.dto.vehiclebrand.VehicleBrandCreateDto;
import com.senla.dto.vehiclebrand.VehicleBrandUpdateDto;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BrandRunner {
    public static void main(String[] args) throws JsonProcessingException {
        var ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
        VehicleBrandController c = ctx.getBean(VehicleBrandController.class);
        testRead(c);
        testBulk(c);

    }
    private static void testRead(VehicleBrandController c) throws JsonProcessingException {
        System.out.println(c.getAllBrands());
        System.out.println(c.debugGetAllBrands());
        System.out.println(c.getBrandById(2L));
    }
    private static void testBulk(VehicleBrandController c) throws JsonProcessingException {
        VehicleBrandCreateDto brandCreateDto = VehicleBrandCreateDto.builder().brandName("Lada").build();
        System.out.println("Brand to insert: " + c.createBrand(brandCreateDto));
        c.debugGetAllBrands();

        VehicleBrandUpdateDto brandUpdateDto = VehicleBrandUpdateDto.builder().brandId(1L).brandName("ChangedThroughUpdate").build();
        System.out.println("Brand to Update: " + brandUpdateDto);
        System.out.println("Brand Updated: " + c.updateBrand(brandUpdateDto));

        c.existsBrand(2L);
        c.deleteBrand(2L);
        c.existsBrand(2L);

        System.out.println(c.debugGetAllBrands());
    }
}
