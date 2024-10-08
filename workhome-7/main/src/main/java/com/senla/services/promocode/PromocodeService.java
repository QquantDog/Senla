package com.senla.services.promocode;

import com.senla.dto.customerrating.CustomerRatingCreateDto;
import com.senla.dto.promocode.PromocodeUpdateDto;
import com.senla.exceptions.DaoCheckedException;
import com.senla.models.customerrating.CustomerRating;
import com.senla.models.promocode.Promocode;
import com.senla.util.service.GenericService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface PromocodeService extends GenericService<Promocode, Long> {
//    PromocodeResponseDto customSave(Promocode promocode);
//    void customGetAll();
    Promocode updatePromocode(Long id, PromocodeUpdateDto promocodeUpdateDto) throws DaoCheckedException;
    List<Promocode> findPromocodesBySpecification(String code, List<LocalDate> startDate, List<LocalDate> endDate, List<BigDecimal> discount);
}
