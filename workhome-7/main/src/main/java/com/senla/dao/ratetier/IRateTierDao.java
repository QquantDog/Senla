package com.senla.dao.ratetier;

import com.senla.models.ratetier.RateTier;
import com.senla.util.dao.GenericDao;

public interface IRateTierDao extends GenericDao<RateTier, Long> {
    RateTier findTierForCab(Long cabId);
}
