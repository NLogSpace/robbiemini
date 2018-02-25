package de.leifaktor.robbiemini.tiles;

import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.actor.Actor;
import de.leifaktor.robbiemini.actor.MoveableActor;

public abstract class Tile {
	
	public abstract boolean canBeEntered(Actor actor);
	
	public void onEnter(Room room, MoveableActor actor, int dir) {}
	
	public void onLeave(Room room, MoveableActor actor, int dir) {}
	
}
