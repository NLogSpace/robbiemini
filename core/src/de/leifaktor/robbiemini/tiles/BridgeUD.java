package de.leifaktor.robbiemini.tiles;

import de.leifaktor.robbiemini.actor.Actor;

public class BridgeUD extends Tile {

	@Override
	public boolean canBeEntered(Actor actor, int dir) {
		return true;
	}
}
