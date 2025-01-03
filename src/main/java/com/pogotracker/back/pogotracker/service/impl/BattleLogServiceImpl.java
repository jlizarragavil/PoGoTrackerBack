package com.pogotracker.back.pogotracker.service.impl;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.pogotracker.back.pogotracker.dto.BattleStats;
import com.pogotracker.back.pogotracker.entity.XPTracker;
import com.pogotracker.back.pogotracker.entity.exceptions.EntityNotFoundException;
import com.pogotracker.back.pogotracker.entity.exceptions.NoBattleLogException;
import com.pogotracker.back.pogotracker.model.BattleLog;
import com.pogotracker.back.pogotracker.repository.XPTrackerRepository;
import com.pogotracker.back.pogotracker.services.BattleLogService;

@Service
public class BattleLogServiceImpl implements BattleLogService {

	private final XPTrackerRepository xpTrackerRepository;

	public BattleLogServiceImpl(XPTrackerRepository xpTrackerRepository) {
		this.xpTrackerRepository = xpTrackerRepository;
	}

	@Override
	public XPTracker addBattleLog(String id, BattleLog battleLog) {
		if (battleLog == null) {
			throw new IllegalArgumentException("BattleLog cannot be null");
		}

		return xpTrackerRepository.findById(id).map(xpTracker -> {
			List<BattleLog> battleLogs = Optional.ofNullable(xpTracker.getBattleLog()).orElse(new ArrayList<>());
			battleLogs.add(battleLog);
			xpTracker.setBattleLog(battleLogs);
			return xpTrackerRepository.save(xpTracker);
		}).orElseGet(() -> xpTrackerRepository.save(createNewXPTracker(id, battleLog)));
	}
	
	@Override
	public List<BattleLog> getBattleLogsBySeason(String id, int season) {
	    return xpTrackerRepository.findById(id)
	            .map(xpTracker -> xpTracker.getBattleLog().stream()
	                    .filter(log -> log.getSeason() == season)
	                    .collect(Collectors.toList()))
	            .orElseThrow(() -> new EntityNotFoundException("XPTracker not found for id: " + id));
	}

	@Override
	public List<BattleLog> getBattleLog(String id) {
		return xpTrackerRepository.findById(id).map(XPTracker::getBattleLog).orElseThrow();
	}

	@Override
	public int getLatestElo(String id) {
		return xpTrackerRepository.findById(id).map(xpTracker -> {
			List<BattleLog> logs = xpTracker.getBattleLog();
			if (logs != null && !logs.isEmpty()) {
				return logs.get(logs.size() - 1).getElo();
			} else {
				throw new NoBattleLogException("No battle logs found for player: " + id);
			}
		}).orElseThrow(() -> new EntityNotFoundException("XPTracker not found for id: " + id));
	}

	@Override
	public BattleStats getStats(String id, String league, String subLeague) {
		XPTracker xpTracker = xpTrackerRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("XPTracker not found for id: " + id));

		List<BattleLog> logs = xpTracker.getBattleLog();

		if (logs == null || logs.isEmpty()) {
			throw new NoBattleLogException("No battle logs found for player: " + id);
		}

		List<BattleLog> filteredLogs;
		if ("all".equalsIgnoreCase(league)) {
			filteredLogs = logs;
		} else {
			filteredLogs = logs.stream()
					.filter(log -> league.equalsIgnoreCase(log.getLeague())
							&& (subLeague == null || subLeague.equalsIgnoreCase(log.getSubLeague())))
					.collect(Collectors.toList());
		}

		int totalVictories = filteredLogs.stream().mapToInt(BattleLog::getVictories).sum();
		int totalDefeats = filteredLogs.stream().mapToInt(BattleLog::getDefeats).sum();
		int totalSets = filteredLogs.size();

		double winRate = totalSets > 0 ? (double) totalVictories / (totalSets * 5) * 100 : 0;
		int totalBattles = totalVictories + totalDefeats;
		double winRatio = totalBattles > 0 ? (double) totalVictories / totalBattles : 0;

		double averageElo = filteredLogs.stream().mapToInt(BattleLog::getElo).average().orElse(0);

		return new BattleStats(league, subLeague, totalVictories, totalDefeats, totalSets, winRate, winRatio,
				totalBattles, averageElo);
	}

	@Override
	public XPTracker updateBattleLog(String id, BattleLog updatedLog) {
		return xpTrackerRepository.findById(id).map(xpTracker -> {
			List<BattleLog> logs = xpTracker.getBattleLog();
			for (int i = 0; i < logs.size(); i++) {
				if (logs.get(i).getSetNumber() == updatedLog.getSetNumber()) {
					logs.set(i, updatedLog);
					break;
				}
			}
			xpTracker.setBattleLog(logs);
			return xpTrackerRepository.save(xpTracker);
		}).orElseThrow(() -> new EntityNotFoundException("XPTracker not found for id: " + id));
	}

	@Override
	public List<BattleLog> getBattleLogsByDateRange(String id, Date startDate, Date endDate) {
		return xpTrackerRepository.findById(id)
				.map(xpTracker -> xpTracker.getBattleLog().stream()
						.filter(log -> !log.getDate().before(startDate) && !log.getDate().after(endDate))
						.collect(Collectors.toList()))
				.orElseThrow(() -> new EntityNotFoundException("XPTracker not found for id: " + id));
	}

	@Override
	public XPTracker resetBattleLogs(String id) {
		return xpTrackerRepository.findById(id).map(xpTracker -> {
			xpTracker.setBattleLog(new ArrayList<>());
			return xpTrackerRepository.save(xpTracker);
		}).orElseThrow(() -> new EntityNotFoundException("XPTracker not found for id: " + id));
	}

	@Override
	public List<BattleLog> getBattleLogsByLeagueAndSubLeague(String id, String league, String subLeague) {
		return xpTrackerRepository.findById(id).map(xpTracker -> {
			List<BattleLog> logs = xpTracker.getBattleLog();
			if (logs == null || logs.isEmpty()) {
				throw new NoBattleLogException("No battle logs found for player: " + id);
			}

			return logs.stream()
					.filter(log -> log.getLeague().equalsIgnoreCase(league) && (subLeague == null || subLeague.isEmpty()
							|| (log.getSubLeague() != null && log.getSubLeague().equalsIgnoreCase(subLeague))))
					.collect(Collectors.toList());
		}).orElseThrow(() -> new EntityNotFoundException("XPTracker not found for id: " + id));
	}

	@Override
	public XPTracker deleteBattleLogEntry(String id, String date) {
	    try {
	        System.out.println("Date: " + date);
	        ZonedDateTime zonedDateTime = ZonedDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME);
	        
	        Date targetDate = Date.from(zonedDateTime.toInstant());
	        
	        return xpTrackerRepository.findById(id).map(xpTracker -> {
	        	xpTracker.getBattleLog().removeIf(entry -> entry.getDate() != null && entry.getDate().equals(targetDate));
	            return xpTrackerRepository.save(xpTracker);
	        }).orElseThrow(() -> new EntityNotFoundException("XPTracker not found for id: " + id));
	    } catch (DateTimeParseException e) {
	        throw new IllegalArgumentException("Invalid date format: " + date, e);
	    }
	}

	@Override
	public List<BattleLog> getBattleLogsByLeague(String id, String league) {
		return xpTrackerRepository.findById(id).map(xpTracker -> {
			List<BattleLog> logs = xpTracker.getBattleLog();
			if (logs == null || logs.isEmpty()) {
				throw new NoBattleLogException("No battle logs found for player: " + id);
			}

			return logs.stream().filter(log -> log.getLeague().equalsIgnoreCase(league)).collect(Collectors.toList());
		}).orElseThrow(() -> new EntityNotFoundException("XPTracker not found for id: " + id));
	}

	private XPTracker createNewXPTracker(String id, BattleLog battleLog) {
		XPTracker xpTracker = new XPTracker();
		xpTracker.setId(id);
		xpTracker.setPlayerName(id);
		List<BattleLog> battleLogs = new ArrayList<>();
		battleLogs.add(battleLog);
		xpTracker.setBattleLog(battleLogs);
		return xpTracker;
	}
}
