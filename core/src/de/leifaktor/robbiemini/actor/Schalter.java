package de.leifaktor.robbiemini.actor;

import de.leifaktor.robbiemini.Room;

public class Schalter extends Actor {
	
	boolean wallOnTheLeft;
	boolean active;
	String variableName;

	public Schalter(int x, int y, int z, boolean wallOnTheLeft, boolean active, String variableName) {
		super(x, y, z);
		this.wallOnTheLeft = wallOnTheLeft;
		this.active = active;
		this.variableName = variableName;
	}

	@Override
	public Actor clone() {
		return new Schalter(x, y, z, wallOnTheLeft, active, variableName);
	}

	@Override
	public boolean canBeEntered(Actor other) {
		return other instanceof Player;
	}

	@Override
	public void hitBy(Room room, Actor actor) {
		if (actor instanceof Player) {
			if (actor.getDirection() == 0) {
				active = true;
				room.setGlobalBoolean(variableName, true);
			}
			if (actor.getDirection() == 2) {
				active = false;
				room.setGlobalBoolean(variableName, false);
			}
		}
	}
	
	public boolean isActive() {
		return active;
	}
	
	public boolean isWallOnTheLeft() {
		return wallOnTheLeft;
	}	

}
