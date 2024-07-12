package com.pogotracker.back.pogotracker.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.pogotracker.back.pogotracker.model.XPRecord;

@Document(collection = "xp_tracker")
public class XPTracker {

    @Id
    @Field("_id")
    private String id;
    private String playerName;
    private List<XPRecord> xpRecords;

    // Getters y setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public List<XPRecord> getXpRecords() {
        return xpRecords;
    }

    public void setXpRecords(List<XPRecord> xpRecords) {
        this.xpRecords = xpRecords;
    }
}
