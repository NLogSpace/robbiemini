package de.leifaktor.robbiemini.tiles;

import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.actor.Actor;

public class Air extends Tile {
	
	@Override
	public boolean canBeEntered(Actor actor, int dir) {
		return true;
	}

	@Override
	public void onEnter(Room room, Actor actor, int dir) {
		room.fallDown(actor);
	}
	
	

}
