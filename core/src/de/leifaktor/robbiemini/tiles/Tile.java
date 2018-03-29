package de.leifaktor.robbiemini.tiles;

import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.actor.Actor;

public abstract class Tile {
	
	public abstract boolean canBeEntered(Actor actor, int dir);
	
	public void onEnter(Room room, Actor actor, int dir) {}
	
	public void onLeave(Room room, Actor actor, int dir) {}
	
}
