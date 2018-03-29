package de.leifaktor.robbiemini.actor;

import de.leifaktor.robbiemini.CollisionDetector;
import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.items.Blaumann;

public class ElectricFence extends Actor implements IShiftable {
	
	boolean shouldStartShift;

	public ElectricFence(int x, int y, int z) {
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
		if (other instanceof Player && ((Player)other).getInventory().hasItem(new Blaumann())) return false;
		if (other instanceof Isolator || other instanceof ElectricFence) return false;
		else return true;
	}

	@Override
	public boolean canBeShifted(Actor actor) {
		if (actor instanceof Player && ((Player)actor).getInventory().hasItem(new Blaumann())) return true;
		if (actor instanceof Isolator || actor instanceof ElectricFence) return true;
		else return false;
	}

	@Override
	public void hitBy(Room room, Actor actor) {
		if (actor instanceof Player) {
			((Player) actor).die(room);
			this.remove();
		}
		if (actor instanceof Robot) {
			((Robot)actor).explode(room);
			this.remove();
		}
		if (actor instanceof FlyingBullet) {
			room.makeExplosion(getPosition());
			actor.remove();
			this.remove();			
		}
	}

	@Override
	public Actor clone() {
		return new ElectricFence(x, y, z);
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
