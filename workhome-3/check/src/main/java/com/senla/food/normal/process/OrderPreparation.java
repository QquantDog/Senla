package com.senla.food.normal.process;

import com.senla.annotations.Autowire;
//import com.senla.annotations.Value;
import com.senla.annotations.Component;
import com.senla.annotations.PostConstruct;
import com.senla.annotations.Value;
import com.senla.food.normal.services.AddressService;
import com.senla.food.normal.services.CourierService;
import com.senla.food.normal.services.DiscountService;
import com.senla.food.normal.services.FoodService;
import com.senla.food.normal.services.implementations.price.CheapPriceServiceImpl;

@Component
public class OrderPreparation {
//
    @Autowire
    private FoodService foodService;
    private CheapPriceServiceImpl priceService;
    private AddressService addressService;
    private CourierService courierService;
    private DiscountService discountService;

    @Value("property1")
    private String dataFromValueAnnotation;

    @Value("db.password")
    private String password;

    @PostConstruct
    public void someInfo(){
        System.out.println("postConstruct called - after beforePostProcessInit and before afterPostProcessInit ");
    }

    @Autowire
    public void setOutFields(AddressService addressService, CheapPriceServiceImpl priceService){
        this.addressService = addressService;
        this.priceService = priceService;
    }

    private OrderPreparation(){}


    @Autowire
    private OrderPreparation(CourierService courierService, DiscountService discountService){
        this.courierService = courierService;
        this.discountService = discountService;
    }

    public void debug(){
        System.out.println(foodService.getFoodResult());
        System.out.println(addressService.getFullAddress());
        System.out.println(priceService.getPriceResult());
        System.out.println(courierService.getCourier());
        System.out.println(discountService.getDiscount());
        System.out.println(dataFromValueAnnotation);
        System.out.println(password);
    }
}
