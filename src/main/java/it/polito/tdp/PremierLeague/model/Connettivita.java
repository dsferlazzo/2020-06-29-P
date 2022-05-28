package it.polito.tdp.PremierLeague.model;

public class Connettivita {
	int matchId1;
	int matchId2;
	int peso;
	public Connettivita(int matchId1, int matchId2, int peso) {
		super();
		this.matchId1 = matchId1;
		this.matchId2 = matchId2;
		this.peso = peso;
	}
	public int getMatchId1() {
		return matchId1;
	}
	public int getMatchId2() {
		return matchId2;
	}
	public int getPeso() {
		return peso;
	}
	
	
}
