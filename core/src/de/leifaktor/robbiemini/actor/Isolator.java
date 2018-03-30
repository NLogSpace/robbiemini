package de.leifaktor.robbiemini.actor;

import de.leifaktor.robbiemini.CollisionDetector;
import de.leifaktor.robbiemini.Room;

public class Isolator extends Actor implements IShiftable {
	
	boolean shouldStartShift;
	
	public Isolator() {} // no-arg constructor for JSON

	public Isolator(int x, int y, int z) {
		super(x, y, z);
	}	
	
	@Override
	public void update(Room room) {
		super.update(room);
		if (isOnTile && shouldStartShift) {
			shouldStartShift = false;
			if (direction > -1) {
				boolean canMove = CollisionDetector.canMoveTo(this, room, direction);
				boolean canShift = CollisionDetector.canShiftTo(this, room, direction);
				if (canMove) {
					room.onLeave(this, x, y, z, direction);
					initMove(direction);						
					move(Math.min(remainingDistance, distanceUntilNextTile));
				} else if (canShift) {
					room.onLeave(this, x, y, z, direction);
					room.startShift(this, x, y, z, direction);
					initMove(direction);						
					move(Math.min(remainingDistance, distanceUntilNextTile));
				}
			}
		}
	}

	@Override
	public boolean canBeEntered(Actor other) {
		return false;
	}

	@Override
	public boolean canBeShifted(Actor actor) {		
		return (actor instanceof Player || actor instanceof ElectricFence || actor instanceof Isolator);
	}

	@Override
	public Actor clone() {
		return new Isolator(x, y, z);
	}

	@Override
	public void startShift(int direction, float speed, float remaniningDistanceInTheFirstFrame, Room room) {
		this.direction = direction;
		this.speed = speed;
		this.remainingDistance = remaniningDistanceInTheFirstFrame;
		shouldStartShift = true;
		update(room);
	}	
	

}
