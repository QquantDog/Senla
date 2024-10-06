package com.senla.services.taxicompany;

import com.senla.dto.taxicompany.TaxiCompanyCreateDto;
import com.senla.dto.taxicompany.TaxiCompanyResponseDto;
import com.senla.dto.taxicompany.TaxiCompanyUpdateDto;
import com.senla.dto.user.UserCreateDto;
import com.senla.dto.user.UserUpdateDto;
import com.senla.models.taxicompany.TaxiCompany;
import com.senla.models.user.User;
import com.senla.util.service.GenericService;

import java.time.LocalDateTime;
import java.util.List;


public interface TaxiCompanyService extends GenericService<TaxiCompany, Long> {
    TaxiCompany createTaxiCompany(TaxiCompanyCreateDto taxiCompanyCreateDto);
    TaxiCompany updateTaxiCompany(Long id, TaxiCompanyUpdateDto taxiCompanyUpdateDto);
    List<TaxiCompany> getAllWithCabs();
}
