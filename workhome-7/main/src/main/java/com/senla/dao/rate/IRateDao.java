package com.senla.dao.rate;

import com.senla.models.rate.Rate;
import com.senla.util.dao.GenericDao;

public interface IRateDao extends GenericDao<Rate, Long> {
    Rate getRateByTierAndCity(Long tierId, Long cityId);
}
