package de.leifaktor.robbiemini.tiles;

import de.leifaktor.robbiemini.actor.Actor;

public class Water extends Tile {
	
	public static final int NORMAL = 0;
	public static final int RIGHT = 1;
	public static final int LEFT = 2;
	public static final int UP = 3;
	public static final int DOWN = 4;
	public static final int DL = 5;
	public static final int UR = 6;
	public static final int UL = 7;
	public static final int DR = 8;
	
	public int type;
	
	public Water() {} // no-arg constructor for JSON
	
	public Water(int type) {
		this.type = type;
	}

	@Override
	public boolean canBeEntered(Actor actor, int dir) {
		return true;
	}

}
