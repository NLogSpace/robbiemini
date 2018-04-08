package de.leifaktor.robbiemini.tiles;

import de.leifaktor.robbiemini.actor.Actor;

public class EmptyTile extends Tile {
	
	public static final int WHITE = 0;
	public static final int GRASS = 1;
	
	public int type;
	
	public EmptyTile() {} // no-arg constructor for JSON
	
	public EmptyTile(int type) {
		this.type = type;
	}

	@Override
	public boolean canBeEntered(Actor actor, int dir) {
		return true;
	}

}
