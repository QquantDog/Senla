package com.senla.services.rate;

import com.senla.dto.rate.RateCreateDto;
import com.senla.dto.rate.RateUpdateDto;
import com.senla.dto.user.UserCreateDto;
import com.senla.dto.user.UserUpdateDto;
import com.senla.models.rate.Rate;
import com.senla.models.user.User;
import com.senla.util.service.GenericService;

public interface RateService extends GenericService<Rate, Long> {
    Rate createRate(RateCreateDto dto);
    Rate updateRate(Long id, RateUpdateDto dto);
}
