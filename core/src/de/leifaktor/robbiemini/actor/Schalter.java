package de.leifaktor.robbiemini.actor;

import de.leifaktor.robbiemini.Room;

public class Schalter extends Actor {
	
	boolean wallOnTheLeft;
	boolean active;

	public Schalter(int x, int y, boolean wallOnTheLeft, boolean active) {
		super(x, y);
		this.wallOnTheLeft = wallOnTheLeft;
		this.active = active;
	}

	@Override
	public Actor clone() {
		return new Schalter(x, y, wallOnTheLeft, active);
	}

	@Override
	public boolean canBeEntered(Actor other) {
		return other instanceof Player;
	}

	@Override
	public void hitBy(Room room, Actor actor) {
		if (actor instanceof Player) {
			if (actor.getDirection() == 0) active = true;
			if (actor.getDirection() == 2) active = false;
		}
	}
	
	public boolean isActive() {
		return active;
	}
	
	public boolean isWallOnTheLeft() {
		return wallOnTheLeft;
	}
	
	

}
