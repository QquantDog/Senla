package com.senla.dao.taxicompany;

import com.senla.models.taxicompany.TaxiCompany;
import com.senla.util.dao.GenericDao;

import java.util.List;

public interface ITaxiCompanyDao extends GenericDao<TaxiCompany, Long>  {
    List<TaxiCompany> getAllWithCabs();
}
