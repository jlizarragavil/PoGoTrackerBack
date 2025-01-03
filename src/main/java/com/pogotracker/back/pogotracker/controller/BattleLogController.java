package com.pogotracker.back.pogotracker.controller;

import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pogotracker.back.pogotracker.dto.BattleStats;
import com.pogotracker.back.pogotracker.entity.XPTracker;
import com.pogotracker.back.pogotracker.model.BattleLog;
import com.pogotracker.back.pogotracker.model.DeleteRequest;
import com.pogotracker.back.pogotracker.services.BattleLogService;


@RestController
@EnableMongoRepositories
@CrossOrigin()
@RequestMapping("/xpTracker")
public class BattleLogController {

	private final BattleLogService battleLogService;

	public BattleLogController(BattleLogService battleLogService) {
		this.battleLogService = battleLogService;
	}

	@PostMapping("/{id}/battle-log")
	public ResponseEntity<XPTracker> addBattleLog(@PathVariable String id, @RequestBody BattleLog battleLog) {
		XPTracker updatedXPTracker = battleLogService.addBattleLog(id, battleLog);
		return updatedXPTracker != null ? ResponseEntity.ok(updatedXPTracker)
				: ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@GetMapping("/{id}/battle-log/season/{season}")
	public ResponseEntity<List<BattleLog>> getBattleLogsBySeason(@PathVariable String id, @PathVariable int season) {
	    List<BattleLog> battleLogs = battleLogService.getBattleLogsBySeason(id, season);
	    return battleLogs != null && !battleLogs.isEmpty() ? ResponseEntity.ok(battleLogs)
	            : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@GetMapping("/{id}/battles")
    public ResponseEntity<List<BattleLog>> getBattleLog(@PathVariable String id) {
        List<BattleLog> battleLogs = battleLogService.getBattleLog(id);
        return ResponseEntity.ok(battleLogs);
    }
	
	@GetMapping("/{id}/elo")
	public ResponseEntity<Integer> getLatestElo(@PathVariable String id) {
	    int latestElo = battleLogService.getLatestElo(id);
	    return ResponseEntity.ok(latestElo);
	}
	
	@GetMapping("/{id}/stats")
    public ResponseEntity<BattleStats> getStats(
            @PathVariable String id, 
            @RequestParam(required = false, defaultValue = "all") String league,
            @RequestParam(required = false) String subLeague) {
        BattleStats stats = battleLogService.getStats(id, league, subLeague);
        return ResponseEntity.ok(stats);
    }
	
	@PutMapping("/{id}/battles")
	public ResponseEntity<XPTracker> updateBattleLog(@PathVariable String id, @RequestBody BattleLog updatedLog) {
	    XPTracker updatedXPTracker = battleLogService.updateBattleLog(id, updatedLog);
	    return ResponseEntity.ok(updatedXPTracker);
	}
	
	@GetMapping("/{id}/battles/date-range")
	public ResponseEntity<List<BattleLog>> getBattleLogsByDateRange(
	    @PathVariable String id,
	    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
	    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {

	    List<BattleLog> battleLogs = battleLogService.getBattleLogsByDateRange(id, startDate, endDate);
	    return ResponseEntity.ok(battleLogs);
	}
	
	@DeleteMapping("/{id}/battles/reset")
	public ResponseEntity<XPTracker> resetBattleLogs(@PathVariable String id) {
	    XPTracker xpTracker = battleLogService.resetBattleLogs(id);
	    return ResponseEntity.ok(xpTracker);
	}
	@DeleteMapping("/{id}/battles")
	public ResponseEntity<XPTracker> deleteBattleLogEntry(@PathVariable String id, @RequestBody DeleteRequest dateRequest) {
	    try {
	        XPTracker xpTracker = battleLogService.deleteBattleLogEntry(id, dateRequest.getDate());
	        return ResponseEntity.ok(xpTracker);
	    } catch (DateTimeParseException e) {
	        return ResponseEntity.badRequest().body(null);
	    }
	}
	
	@GetMapping("/{id}/battles/league/{league}/subleague/{subLeague}")
	public ResponseEntity<List<BattleLog>> getBattleLogsByLeagueAndSubLeague(
	        @PathVariable String id, 
	        @PathVariable String league, 
	        @PathVariable(required = false) String subLeague) {
	    
	    List<BattleLog> battleLogs = battleLogService.getBattleLogsByLeagueAndSubLeague(id, league, subLeague);
	    return ResponseEntity.ok(battleLogs);
	}
	
	@GetMapping("/{id}/battles/league/{league}")
	public ResponseEntity<List<BattleLog>> getBattleLogsByLeague(@PathVariable String id, 
	    @PathVariable String league) {
	    
	    List<BattleLog> battleLogs = battleLogService.getBattleLogsByLeague(id, league);
	    return ResponseEntity.ok(battleLogs);
	}
}
