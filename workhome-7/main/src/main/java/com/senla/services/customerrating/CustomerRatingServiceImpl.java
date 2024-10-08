package com.senla.services.customerrating;

import com.senla.dao.customerrating.CustomerRatingDao;
import com.senla.dao.ride.RideDao;
import com.senla.dto.customerrating.CustomerRatingCreateDto;
import com.senla.dto.customerrating.CustomerRatingUpdateDto;
import com.senla.exceptions.DaoException;
import com.senla.models.customerrating.CustomerRating;

import com.senla.models.ride.Ride;
import com.senla.util.service.AbstractLongIdGenericService;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class CustomerRatingServiceImpl extends AbstractLongIdGenericService<CustomerRating> implements CustomerRatingService {

    @Autowired
    private CustomerRatingDao customerRatingDao;
    @Autowired
    private RideDao rideDao;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @PostConstruct
    protected void init() {
        super.abstractDao = customerRatingDao;
    }


//
//    здесь должна быть защита(секурити то что айдишник кастомера в райде равен айдишнику в jwt)
//

    @Override
    @Transactional
    public CustomerRating createCustomerRate(CustomerRatingCreateDto customerRatingCreateDto) {
        Long rideId = customerRatingCreateDto.getRideId();
        Long customerId = customerRatingCreateDto.getCustomerId();

        Ride ride = rideDao.getRideWithPromocode(rideId);
        if(ride.getCustomerRating() != null) throw new DaoException("Ride has already been rated");
        if(!ride.getCustomer().getId().equals(customerId)) throw new DaoException("Ride has different customer");

        CustomerRating customerRating = modelMapper.map(customerRatingCreateDto, CustomerRating.class);
        customerRating.setRide(ride);
        ride.setCustomerRating(customerRating);
        customerRating.setCreatedAt(LocalDateTime.now());

        return abstractDao.create(customerRating);
    }

    @Override
    @Transactional
    public CustomerRating updateCustomerRate(CustomerRatingUpdateDto customerRatingUpdateDto) {
        Long rideId = customerRatingUpdateDto.getRideId();
        Long customerId = customerRatingUpdateDto.getCustomerId();

        Ride ride = rideDao.getRideWithPromocode(rideId);
        if(ride.getCustomerRating() == null) throw new DaoException("Ride wasn't rated - cannot modify rate");
        if(!ride.getCustomer().getId().equals(customerId)) throw new DaoException("Ride has different customer");

        CustomerRating customerRating = modelMapper.map(customerRatingUpdateDto, CustomerRating.class);
        customerRating.setRide(ride);
        ride.setCustomerRating(customerRating);
        customerRating.setCreatedAt(LocalDateTime.now());

        return abstractDao.update(customerRating);
    }
//    @Override
//    @Transactional
//    public void deleteCustomerRate(CustomerRatingUpdateDto customerRatingCreateUpdateDto) {
//        Long rideId = customerRatingCreateUpdateDto.getRideId();
//        Long customerId = customerRatingCreateUpdateDto.getCustomerId();
//
//        Ride ride = rideDao.getRideWithPromocode(rideId);
//        if(ride.getCustomerRating() == null) throw new DaoException("Ride wasn't rated - cannot modify rate");
//        if(!ride.getCustomer().getId().equals(customerId)) throw new DaoException("Ride has different customer");
//
//        CustomerRating customerRating = modelMapper.map(customerRatingCreateUpdateDto, CustomerRating.class);
//        customerRating.setRide(ride);
//        ride.setCustomerRating(customerRating);
//        customerRating.setCreatedAt(LocalDateTime.now());
//
//        return abstractDao.update(customerRating);
//    }
}
