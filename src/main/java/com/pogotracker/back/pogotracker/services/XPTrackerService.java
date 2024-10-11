package com.pogotracker.back.pogotracker.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.pogotracker.back.pogotracker.entity.XPTracker;
import com.pogotracker.back.pogotracker.model.BattleLog;

public interface XPTrackerService {

	List<XPTracker> findAll();

	Optional<XPTracker> findById(String id);

	XPTracker save(XPTracker xpTracker);

	XPTracker update(String id, XPTracker xpTracker);

	XPTracker updateFields(String id, Map<String, Object> updates);

	XPTracker addXPRecord(String id, int newTotalXP, int xpEvent, boolean luckyEgg);

	XPTracker deleteXPRecord(String id, long totalXP, long dailyXPDifference);
}
