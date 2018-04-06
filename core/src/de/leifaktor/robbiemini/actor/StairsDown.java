package de.leifaktor.robbiemini.actor;

import de.leifaktor.robbiemini.Room;

public class StairsDown extends Actor {
	
	public StairsDown() {} // no-arg constructor for JSON
	
	public StairsDown(int x, int y, int z) {
		super(x, y, z);
	}
	
	@Override
	public boolean canBeEntered(Actor other) {
		return other instanceof Player;
	}

	@Override
	public void hitBy(Room room, Actor actor) {
		if (actor instanceof Player) room.walkDownstairs();
	}

	@Override
	public Actor clone() {
		return new StairsDown();
	}

}
