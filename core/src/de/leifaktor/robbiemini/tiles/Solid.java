package de.leifaktor.robbiemini.tiles;

import de.leifaktor.robbiemini.actor.Actor;

public class Solid extends Tile {
	
	public static final int DARK_WALL = 0;
	public static final int MOUNTAIN_1 = 1;
	public static final int MOUNTAIN_2 = 2;
	public static final int MOUNTAIN_3 = 3;
	public static final int MOUNTAIN_4 = 4;	
	
	public int type;
	
	public Solid() {} // no-arg constructor for JSON
	
	public Solid(int type) {
		this.type = type;
	}

	@Override
	public boolean canBeEntered(Actor actor, int dir) {
		return false;
	}

}
