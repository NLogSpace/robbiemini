package de.leifaktor.robbiemini.tiles;

import de.leifaktor.robbiemini.actor.Actor;

public class Air extends Tile {

	@Override
	public boolean canBeEntered(Actor actor) {
		return true;
	}

}
