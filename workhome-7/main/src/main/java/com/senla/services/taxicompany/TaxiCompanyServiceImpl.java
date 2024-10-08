package com.senla.services.taxicompany;

import com.senla.dao.taxicompany.TaxiCompanyDao;
import com.senla.dto.taxicompany.TaxiCompanyCreateDto;
import com.senla.dto.taxicompany.TaxiCompanyUpdateDto;

import com.senla.exceptions.DaoException;
import com.senla.models.taxicompany.TaxiCompany;
import com.senla.util.service.AbstractLongIdGenericService;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class TaxiCompanyServiceImpl extends AbstractLongIdGenericService<TaxiCompany> implements TaxiCompanyService {

    @Autowired
    private TaxiCompanyDao taxiCompanyDao;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @PostConstruct
    protected void init() {
        super.abstractDao = taxiCompanyDao;
    }

    @Override
    @Transactional
    public TaxiCompany createTaxiCompany(TaxiCompanyCreateDto dto) {
        TaxiCompany taxiCompany = modelMapper.map(dto, TaxiCompany.class);
        return abstractDao.create(taxiCompany);
    }

    @Override
    @Transactional
    public TaxiCompany updateTaxiCompany(Long id, TaxiCompanyUpdateDto dto) {
        Optional<TaxiCompany> resp = abstractDao.findById(id);
        if (resp.isEmpty()) throw new DaoException("Can't find entity with id " + id);

        TaxiCompany taxiCompanyToUpdate =  resp.get();
        modelMapper.map(dto, taxiCompanyToUpdate);
        return abstractDao.update(taxiCompanyToUpdate);

    }

    @Override
    public List<TaxiCompany> getAllWithCabs() {
        return taxiCompanyDao.getAllWithCabs();
    }
}
