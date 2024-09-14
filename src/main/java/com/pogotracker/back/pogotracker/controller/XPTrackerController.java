package com.pogotracker.back.pogotracker.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pogotracker.back.pogotracker.entity.XPTracker;
import com.pogotracker.back.pogotracker.model.BattleLog;
import com.pogotracker.back.pogotracker.model.XPRecord;
import com.pogotracker.back.pogotracker.service.impl.XPTrackerServiceImpl;
import com.pogotracker.back.pogotracker.services.XPTrackerService;

@RestController
@EnableMongoRepositories
@CrossOrigin()
public class XPTrackerController {

    private final XPTrackerService xpTrackerService;

    public XPTrackerController(XPTrackerServiceImpl xpTrackerService) {
        this.xpTrackerService = xpTrackerService;
    }

    @GetMapping("/xpTracker")
    public ResponseEntity<List<XPTracker>> getXPTracker() {
        List<XPTracker> xpTrackers = xpTrackerService.findAll();
        return new ResponseEntity<>(xpTrackers, HttpStatus.OK);
    }
    
    @GetMapping("/xpTracker/{id}")
    public ResponseEntity<XPTracker> getXPTrackerById(@PathVariable("id") String id) {
        Optional<XPTracker> xpTracker = xpTrackerService.findById(id);
        if (xpTracker.isPresent()) {
            return new ResponseEntity<>(xpTracker.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/xpTracker/add")
    public ResponseEntity<XPTracker> createXPTracker(@RequestBody XPTracker xpTracker) {
        XPTracker createdXPTracker = xpTrackerService.save(xpTracker);
        return new ResponseEntity<>(createdXPTracker, HttpStatus.CREATED);
    }
    
    @PutMapping("/xpTracker/update/{id}")
    public ResponseEntity<XPTracker> updateXPTracker(@PathVariable String id, @RequestBody XPTracker xpTracker) {
        XPTracker updatedXPTracker = xpTrackerService.update(id, xpTracker);
        if (updatedXPTracker != null) {
            return new ResponseEntity<>(updatedXPTracker, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PatchMapping("/xpTracker/update/{id}")
    public ResponseEntity<XPTracker> updateXPTrackerFields(@PathVariable String id, @RequestBody Map<String, Object> updates) {
        XPTracker updatedXPTracker = xpTrackerService.updateFields(id, updates);
        if (updatedXPTracker != null) {
            return new ResponseEntity<>(updatedXPTracker, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PatchMapping("/xpTracker/addXPRecord/{id}")
    public ResponseEntity<XPTracker> addXPRecord(@PathVariable String id, @RequestBody XPRecord xpRecordDTO) {
        XPTracker updatedXPTracker = xpTrackerService.addXPRecord(id, xpRecordDTO.getTotalXP(), xpRecordDTO.getXpEvent(), xpRecordDTO.isLuckyEgg());
        
        if (updatedXPTracker != null) {
            return new ResponseEntity<>(updatedXPTracker, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/xpTracker/{id}/battle-log")
    public ResponseEntity<XPTracker> addBattleLog(@PathVariable String id, @RequestBody BattleLog battleLog) {
        XPTracker updatedXPTracker = xpTrackerService.addBattleLog(id, battleLog);
        return updatedXPTracker != null ? ResponseEntity.ok(updatedXPTracker) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}