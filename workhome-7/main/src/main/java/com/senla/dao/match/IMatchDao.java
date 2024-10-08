package com.senla.dao.match;

import com.senla.models.match.Match;
import com.senla.util.dao.GenericDao;

public interface IMatchDao extends GenericDao<Match, Long> {
    void clearRideEntriesOnAccept(Long shiftId);
    void clearSpecificMatch(Long shiftId);
    Match findMatchByRideAndShift(Long rideId, Long shiftId);
    Match matchRideAndShift(Long rideId, Long shiftId);
}
