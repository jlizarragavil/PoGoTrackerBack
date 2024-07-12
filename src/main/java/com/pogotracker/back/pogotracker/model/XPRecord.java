package com.pogotracker.back.pogotracker.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class XPRecord {

	private int totalXP;
	private int dailyXPDifference;
	private LocalDateTime date;
	private BigDecimal avgDailyXp;
	private int xpEvent;
	private boolean luckyEgg;

	// Getters y setters
	public int getTotalXP() {
		return totalXP;
	}

	public void setTotalXP(int totalXP) {
		this.totalXP = totalXP;
	}

	public int getDailyXPDifference() {
		return dailyXPDifference;
	}

	public void setDailyXPDifference(int dailyXPDifference) {
		this.dailyXPDifference = dailyXPDifference;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public BigDecimal getAvgDailyXp() {
		return avgDailyXp;
	}

	public void setAvgDailyXp(BigDecimal bd) {
		this.avgDailyXp = bd;
	}

	public int getXpEvent() {
		return xpEvent;
	}

	public void setXpEvent(int xpEvent) {
		this.xpEvent = xpEvent;
	}

	public boolean isLuckyEgg() {
		return luckyEgg;
	}

	public void setLuckyEgg(boolean luckyEgg) {
		this.luckyEgg = luckyEgg;
	}
}
