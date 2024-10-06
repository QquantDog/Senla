package com.senla.dao.promocode;

import com.senla.models.promocode.Promocode;
import com.senla.util.dao.GenericDao;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface IPromocodeDao extends GenericDao<Promocode, Long> {
    List<Promocode> findBySpecification(Specification<Promocode> specification);
    Promocode findByCode(String code);
}
