package de.leifaktor.robbiemini.actor;

import de.leifaktor.robbiemini.CollisionDetector;
import de.leifaktor.robbiemini.Room;

public class FlyingBullet extends Actor {

	public FlyingBullet(int x, int y, int z, int direction, float speed) {
		super(x, y, z);
		this.direction = direction;
		this.speed = speed;
	}

	@Override
	public void update(Room room) {
		super.update(room);
		if (isOnTile) {
			boolean canMove = CollisionDetector.canMoveTo(this, room, direction);
			if (canMove) {
				room.onLeave(this, x, y, z, direction);
				initMove(direction);
				move(Math.min(remainingDistance, distanceUntilNextTile));
			} else {
				remove();
			}
		}
	}
	
	@Override
	public Actor clone() {
		return new FlyingBullet(x, y, z, direction, speed);
	}

}
