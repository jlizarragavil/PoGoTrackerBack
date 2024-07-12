package com.pogotracker.back.pogotracker.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pogotracker.back.pogotracker.entity.XPTracker;
import com.pogotracker.back.pogotracker.model.XPRecord;
import com.pogotracker.back.pogotracker.repository.XPTrackerRepository;
import com.pogotracker.back.pogotracker.utils.XPTrackerUtils;

@Service
public class XPTrackerService {

	private final XPTrackerRepository xpTrackerRepository;

	@Autowired
	public XPTrackerService(XPTrackerRepository xpTrackerRepository) {
		this.xpTrackerRepository = xpTrackerRepository;
	}

	public List<XPTracker> findAll() {
		return xpTrackerRepository.findAll();
	}
	public Optional<XPTracker> findById(String id) {
		return xpTrackerRepository.findById(id);
	}

	public XPTracker save(XPTracker xpTracker) {
		return xpTrackerRepository.save(xpTracker);
	}

	public XPTracker update(String id, XPTracker xpTracker) {
		Optional<XPTracker> existingXPTracker = xpTrackerRepository.findById(id);
		if (existingXPTracker.isPresent()) {
			XPTracker updatedXPTracker = existingXPTracker.get();
			updatedXPTracker.setPlayerName(xpTracker.getPlayerName());
			updatedXPTracker.setXpRecords(xpTracker.getXpRecords());
			return xpTrackerRepository.save(updatedXPTracker);
		} else {
			// Handle the case where the XPTracker doesn't exist
			// For example, throw an exception or return null
			return null;
		}
	}
	
	public XPTracker updateFields(String id, Map<String, Object> updates) {
        Optional<XPTracker> existingXPTracker = xpTrackerRepository.findById(id);
        if (existingXPTracker.isPresent()) {
            XPTracker xpTracker = existingXPTracker.get();
            updates.forEach((key, value) -> {
                switch (key) {
                    case "playerName":
                        xpTracker.setPlayerName((String) value);
                        break;
                    case "xpRecords":
                        xpTracker.setXpRecords((List<XPRecord>) value);
                        break;
                    // Añade más casos si tienes más campos que actualizar
                    default:
                        throw new IllegalArgumentException("Campo no válido: " + key);
                }
            });
            return xpTrackerRepository.save(xpTracker);
        } else {
            return null;
        }
    }
	
	public XPTracker addXPRecord(String id, int newTotalXP, int xpEvent, boolean luckyEgg) {
	    Optional<XPTracker> existingXPTracker = xpTrackerRepository.findById(id);
	    if (existingXPTracker.isPresent()) {
	        XPTracker xpTracker = existingXPTracker.get();
	        List<XPRecord> xpRecords = xpTracker.getXpRecords();

	        int lastTotalXP = xpRecords.isEmpty() ? 0 : xpRecords.get(xpRecords.size() - 1).getTotalXP();
	        int dailyXPDifference = newTotalXP - lastTotalXP;

	        XPRecord newXPRecord = new XPRecord();
	        newXPRecord.setTotalXP(newTotalXP);
	        newXPRecord.setDailyXPDifference(dailyXPDifference);
	        newXPRecord.setDate(LocalDateTime.now());
	        newXPRecord.setXpEvent(xpEvent);
	        newXPRecord.setLuckyEgg(luckyEgg);
	        //newXPRecord.setAvgDailyXp(newTotalXP/(xpRecords.size() + 1));
	        System.out.println(xpRecords.size() + 1);
	        xpRecords.add(newXPRecord);
	        xpTracker.setXpRecords(xpRecords);

	        XPTrackerUtils.calculateAndSetAverageDailyXPDifference(xpRecords);
	        
	        
	        return xpTrackerRepository.save(xpTracker);
	    } else {
	        return null;
	    }
	}
	
}