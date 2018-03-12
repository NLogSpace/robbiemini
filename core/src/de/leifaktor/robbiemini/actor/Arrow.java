package de.leifaktor.robbiemini.actor;

import de.leifaktor.robbiemini.Direction;
import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.Vec2;

public class Arrow extends Actor {

	int baseDir;
	int dir;

	public Arrow(int x, int y, int baseDir) {
		super(x, y);
		this.baseDir = baseDir;
		this.dir = baseDir;
	}

	@Override
	public void update(Room room) {
		Vec2 gradient = room.getMagneticGradientAt(x, y);
		if (Math.abs(gradient.getLength()) < 0.3) dir = baseDir;
		else dir = Direction.roundAngleToDirection(Math.atan2(gradient.y, gradient.x));
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
		return new Arrow(x,y,baseDir);
	}	

}
