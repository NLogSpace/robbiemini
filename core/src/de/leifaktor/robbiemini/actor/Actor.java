package de.leifaktor.robbiemini.actor;

import de.leifaktor.robbiemini.Room;

public abstract class Actor {
	
	public int x;
	public int y;
	
	boolean shouldBeRemoved;
	
	public Actor(int x, int y) {
		setPosition(x,y);
		shouldBeRemoved = false;
	}
	
	public abstract void update(Room room);
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	};
	
	public boolean canBeEntered(Actor other) {
		return true;
	}
	
	public boolean shouldBeRemoved() {
		return shouldBeRemoved;
	};
	
	public void remove() {
		shouldBeRemoved = true;
	}

	public void hitBy(Room room, MoveableActor actor) {
		
	}

}
