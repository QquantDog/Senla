package com.senla.dao.cab;

import com.senla.models.cab.Cab;
import com.senla.util.dao.GenericDao;

import java.util.List;

public interface ICabDao extends GenericDao<Cab, Long> {
    List<Cab> getAllCabs();
}
