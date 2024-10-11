package com.pogotracker.back.pogotracker.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.pogotracker.back.pogotracker.entity.XPTracker;
import com.pogotracker.back.pogotracker.model.BattleLog;
import com.pogotracker.back.pogotracker.model.XPRecord;
import com.pogotracker.back.pogotracker.repository.XPTrackerRepository;
import com.pogotracker.back.pogotracker.services.XPTrackerService;
import com.pogotracker.back.pogotracker.utils.XPTrackerUtils;

@Service
public class XPTrackerServiceImpl implements XPTrackerService {

	private final XPTrackerRepository xpTrackerRepository;

	public XPTrackerServiceImpl(XPTrackerRepository xpTrackerRepository) {
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
			// newXPRecord.setAvgDailyXp(newTotalXP/(xpRecords.size() + 1));
			System.out.println(xpRecords.size() + 1);
			xpRecords.add(newXPRecord);
			xpTracker.setXpRecords(xpRecords);

			XPTrackerUtils.calculateAndSetAverageDailyXPDifference(xpRecords);

			return xpTrackerRepository.save(xpTracker);
		} else {
			XPTracker xpTracker = new XPTracker();
			xpTracker.setId(id);
			xpTracker.setPlayerName(id);
			List<XPRecord> xpRecords = new ArrayList<>();
			XPRecord newXPRecord = new XPRecord();
			newXPRecord.setTotalXP(newTotalXP);
			newXPRecord.setDailyXPDifference(0);
			newXPRecord.setDate(LocalDateTime.now());
			double averageXp = 0.0;
			BigDecimal averageXpBigDecimal = BigDecimal.valueOf(averageXp);
			newXPRecord.setAvgDailyXp(averageXpBigDecimal);
			newXPRecord.setLuckyEgg(luckyEgg);
			newXPRecord.setXpEvent(xpEvent);
			xpRecords.add(newXPRecord);
			xpTracker.setXpRecords(xpRecords);
			return xpTrackerRepository.save(xpTracker);
		}
	}

	@Override
	public XPTracker deleteXPRecord(String id, long totalXP, long dailyXPDifference) {
		Optional<XPTracker> existingXPTracker = xpTrackerRepository.findById(id);

		if (existingXPTracker.isPresent()) {
			XPTracker xpTracker = existingXPTracker.get();
			List<XPRecord> xpRecords = xpTracker.getXpRecords();

			System.out.println("XPRecords antes de eliminar: " + xpRecords);

			xpRecords.removeIf(record -> {
				boolean shouldRemove = record.getTotalXP() == totalXP
						&& record.getDailyXPDifference() == dailyXPDifference;
				if (shouldRemove) {
					System.out.println("Eliminando registro: " + record);
				}
				return shouldRemove;
			});

			xpTracker.setXpRecords(xpRecords);
			XPTracker updatedXPTracker = xpTrackerRepository.save(xpTracker);
			System.out.println("XPRecords después de eliminar: " + updatedXPTracker.getXpRecords());
			return updatedXPTracker;
		} else {
			return null;
		}
	}

	

}