package com.pogotracker.back.pogotracker.dto;

public class BattleStats {
    private String league;
    private String subLeague; 
    private int totalVictories;
    private int totalDefeats;
    private int totalSets;
    private double winRate;
    private double winRatio;
    private int totalBattles;
    private double averageElo;

    public BattleStats(String league, String subLeague, int totalVictories, int totalDefeats, int totalSets, double winRate, double winRatio, int totalBattles, double averageElo) {
        this.league = league;
        this.subLeague = subLeague;
        this.totalVictories = totalVictories;
        this.totalDefeats = totalDefeats;
        this.totalSets = totalSets;
        this.winRate = winRate;
        this.winRatio = winRatio;
        this.totalBattles = totalBattles;
        this.averageElo = averageElo;
    }

	public int getTotalVictories() {
		return totalVictories;
	}

	public void setTotalVictories(int totalVictories) {
		this.totalVictories = totalVictories;
	}

	public int getTotalDefeats() {
		return totalDefeats;
	}

	public void setTotalDefeats(int totalDefeats) {
		this.totalDefeats = totalDefeats;
	}

	public int getTotalSets() {
		return totalSets;
	}

	public void setTotalSets(int totalSets) {
		this.totalSets = totalSets;
	}

	public double getWinRate() {
		return winRate;
	}

	public void setWinRate(double winRate) {
		this.winRate = winRate;
	}

	public double getWinRatio() {
		return winRatio;
	}

	public void setWinRatio(double winRatio) {
		this.winRatio = winRatio;
	}

	public String getLeague() {
		return league;
	}

	public void setLeague(String league) {
		this.league = league;
	}

	public String getSubLeague() {
		return subLeague;
	}

	public void setSubLeague(String subLeague) {
		this.subLeague = subLeague;
	}

	public int getTotalBattles() {
		return totalBattles;
	}

	public void setTotalBattles(int totalBattles) {
		this.totalBattles = totalBattles;
	}

	public double getAverageElo() {
		return averageElo;
	}

	public void setAverageElo(double averageElo) {
		this.averageElo = averageElo;
	}

    
}