package com.senla.services.promocode;

import com.senla.dao.promocode.PromocodeDao;
import com.senla.dto.promocode.PromocodeUpdateDto;
import com.senla.exceptions.DaoCheckedException;
import com.senla.exceptions.DaoException;
import com.senla.models.promocode.Promocode;
import com.senla.specification.PromocodeSpecification;
import com.senla.util.service.AbstractLongIdGenericService;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PromocodeServiceImpl extends AbstractLongIdGenericService<Promocode> implements PromocodeService {

    @Autowired
    private PromocodeDao promocodeDao;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @PostConstruct
    protected void init() {
        this.abstractDao = promocodeDao;
    }

    @Override
    @Transactional
    public Promocode updatePromocode(Long id, PromocodeUpdateDto promocodeUpdateDto) throws DaoCheckedException {
        Optional<Promocode> resp = abstractDao.findById(id);
        if (resp.isEmpty()) throw new DaoException("Can't find entity with id " + id);
        else {
            Promocode promocode = resp.get();
            modelMapper.map(promocodeUpdateDto, promocode);
            abstractDao.update(promocode);
            return promocode;
        }
    }

    @Override
    public List<Promocode> findPromocodesBySpecification(String code, List<LocalDate> startDate, List<LocalDate> endDate, List<BigDecimal> discount) {
        return promocodeDao.findBySpecification(PromocodeSpecification.buildSpecification(code, startDate, endDate, discount));
    }

//
//    @Override
//    public PromocodeResponseDto customSave(Promocode promocode) {
////        if(mod)
//        Promocode resp = abstractDao.create(promocode);
//        return modelMapper.map(resp, PromocodeResponseDto.class);
//    }


}
