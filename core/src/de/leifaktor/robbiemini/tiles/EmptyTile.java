package de.leifaktor.robbiemini.tiles;

import de.leifaktor.robbiemini.actor.Actor;

public class EmptyTile extends Tile {

	@Override
	public boolean canBeEntered(Actor actor) {
		return true;
	}

}
