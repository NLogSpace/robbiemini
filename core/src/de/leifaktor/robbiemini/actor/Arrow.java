package de.leifaktor.robbiemini.actor;

import de.leifaktor.robbiemini.Direction;
import de.leifaktor.robbiemini.Room;

public class Arrow extends Actor {

	int dir;

	public Arrow(int x, int y, int dir) {
		super(x, y);
		this.dir = dir;
	}

	@Override
	public void update(Room room) {

	}

	@Override
	public boolean canBeEntered(Actor other) {
		if (other instanceof Player || other instanceof Robot) {
			int actorDir = Direction.coordinatesToDirection(this.x - other.x, this.y - other.y);
			return actorDir == dir;
		} else {
			return false;
		}
	}

	public int getDir() {
		return dir;
	}

	@Override
	public Actor clone() {
		return new Arrow(x,y,dir);
	}	

}
