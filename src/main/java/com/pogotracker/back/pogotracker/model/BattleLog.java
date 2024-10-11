package com.pogotracker.back.pogotracker.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class BattleLog {
	private String league;
	private String subLeague;
	private int victories;
	private int defeats;
	private int elo;
	private int battlesInSet;
	private int setNumber;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Date date;

	public String getLeague() {
		return league;
	}

	public void setLeague(String league) {
		this.league = league;
	}

	public int getVictories() {
		return victories;
	}

	public void setVictories(int victories) {
		this.victories = victories;
	}

	public int getDefeats() {
		return defeats;
	}

	public void setDefeats(int defeats) {
		this.defeats = defeats;
	}

	public int getElo() {
		return elo;
	}

	public void setElo(int elo) {
		this.elo = elo;
	}

	public int getBattlesInSet() {
		return battlesInSet;
	}

	public void setBattlesInSet(int battlesInSet) {
		this.battlesInSet = battlesInSet;
	}

	public int getSetNumber() {
		return setNumber;
	}

	public void setSetNumber(int setNumber) {
		this.setNumber = setNumber;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getSubLeague() {
		return subLeague;
	}

	public void setSubLeague(String subLeague) {
		this.subLeague = subLeague;
	}

}