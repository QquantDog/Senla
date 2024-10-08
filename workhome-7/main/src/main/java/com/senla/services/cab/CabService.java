package com.senla.services.cab;

import com.senla.dto.cab.CabCreateDto;
import com.senla.dto.cab.CabUpdateDto;
import com.senla.models.cab.Cab;
import com.senla.util.service.GenericService;

import java.util.List;

public interface CabService extends GenericService<Cab, Long> {
    Cab createCab(CabCreateDto dto);
    Cab updateCab(Long id, CabUpdateDto dto);
    List<Cab> getAll();
}
