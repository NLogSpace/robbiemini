package de.leifaktor.robbiemini.tiles;

import de.leifaktor.robbiemini.actor.Actor;

public class DarkWall extends Tile {
	
	public static final int FULL = 0;
	public static final int NE = 1;
	public static final int SE = 2;
	public static final int SW = 3;
	public static final int NW = 4;
	
	public int type;
	
	public DarkWall() {} // no-arg constructor for JSON
	
	public DarkWall(int type) {
		if (type < 0 || type > 4) type = 0;
		this.type = type;
	}

	@Override
	public boolean canBeEntered(Actor actor, int dir) {
		return false;
	}

}
