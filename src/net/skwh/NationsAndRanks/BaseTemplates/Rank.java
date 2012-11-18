package net.skwh.NationsAndRanks.BaseTemplates;

public class Rank {
	public String name;
	private Rank up;
	private Rank down;
	public Rank getUp() {
		return this.up;
	}
	public Rank getDown() {
		return this.down;
	}
	
	public Rank(Rank u,Rank d,String n) {
		name = n;
		up = u;
		down = d;
	}
}
