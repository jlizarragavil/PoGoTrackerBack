package com.pogotracker.back.pogotracker.services;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.pogotracker.back.pogotracker.dto.BattleStats;
import com.pogotracker.back.pogotracker.entity.XPTracker;
import com.pogotracker.back.pogotracker.model.BattleLog;

public interface BattleLogService {
	XPTracker addBattleLog(String id, BattleLog battleLog);
	List<BattleLog> getBattleLog(String id);
	int getLatestElo(String id);
	BattleStats getStats(String id, String league, String subLeague, Integer season);
	XPTracker patchBattleLog(String id, Map<String, Object> updates);
	List<BattleLog> getBattleLogsByDateRange(String id, Date startDate, Date endDate);
	XPTracker resetBattleLogs(String id);
	XPTracker deleteBattleLogEntry(String id, String date);
	List<BattleLog> getBattleLogsByLeagueAndSubLeague(String id, String league, String subLeague);
	List<BattleLog> getBattleLogsByLeague(String id, String league);
	public List<BattleLog> getBattleLogsBySeason(String id, int season);
}
