package de.leifaktor.robbiemini.actor;

import com.badlogic.gdx.Gdx;

import de.leifaktor.robbiemini.CollisionDetector;
import de.leifaktor.robbiemini.Direction;
import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.Util;

public class Robot extends Actor{
	
	public int graphicType;
	float stateTime;
	float stressLevel;

	public Robot(int x, int y, float speed, int graphicType) {
		super(x, y);
		this.speed = speed;
		this.graphicType = graphicType;
		stressLevel = 0;
	}	

	@Override
	public void update(Room room) {
		super.update(room);
		stateTime += Gdx.graphics.getDeltaTime();		
		if (isOnTile) {
			decreaseStress(0.05f);
			performAction(room);
		}
		if (stressLevel > 0.6f) explode(room);
	}
	
	private void performAction(Room room) {
		int intendedDir = getMoveDirection(room);
		if (intendedDir > -1) {
			boolean canMove = CollisionDetector.canMoveTo(this, room, intendedDir);
			boolean canShift = CollisionDetector.canShiftTo(this, room, intendedDir);
			if (canMove) {
				room.onLeave(this, x, y, intendedDir);
				initMove(intendedDir);						
				move(Math.min(remainingDistance, distanceUntilNextTile));
			} else if (canShift) {
				room.onLeave(this, x, y, intendedDir);
				room.startShift(this, x, y, intendedDir);
				initMove(intendedDir);						
				move(Math.min(remainingDistance, distanceUntilNextTile));
			}
		}
	}
	
	public int getMoveDirection(Room room) {
		int[] possibleDirs = CollisionDetector.getPossibleDirections(this, room);
		if (possibleDirs.length == 0) {
			increaseStress(1);
		} else if (possibleDirs.length == 1) {
			increaseStress(0.21f);
		} else if (possibleDirs.length >= 5) {
			decreaseStress(0.1f);
		}		
		if (possibleDirs.length == 0) return -1;
		if (possibleDirs.length == 1) return possibleDirs[0];
		int bestDir = getDirTowardsPlayer(room);		
		if (bestDir >= 0) {
			for (int i = 0; i < possibleDirs.length; i++) {
				if (possibleDirs[i] == bestDir) return bestDir;
			}
		}
		increaseStress(0.15f);
		return possibleDirs[Util.random.nextInt(possibleDirs.length)];
	}
	
	private void increaseStress(float increment) {
		stressLevel += increment;
		if (stressLevel > 1) stressLevel = 1;
	}
	
	private void decreaseStress(float decrement) {
		stressLevel -= decrement;
		if (stressLevel < 0) stressLevel = 0;
	}
	
	private int getDirTowardsPlayer(Room room) {
		Player player = room.getPlayer();
		if (player == null) return -1;
		
		int dx = player.x - this.x;
		int dy = player.y - this.y;
		
		double angle = Math.atan2(dy, dx);
		
		return Direction.roundAngleToDirection(angle);
	}

	@Override
	public void hitBy(Room room, Actor actor) {
		if (actor instanceof Robot) {
			explode(room);
		}
		if (actor instanceof FlyingBullet) {
			room.makeExplosion(getPosition());
			actor.remove();
			this.remove();			
		}
	}
	
	public void explode(Room room) {
		this.remove();
		room.makeExplosion(pos);
	}

	@Override
	public Actor clone() {
		return new Robot(x,y,speed, graphicType);
	}

	@Override
	public boolean canBeEntered(Actor other) {
		if (other instanceof Isolator || other instanceof ElectricFence) return false;
		else return true;
	}
	
	public float getStateTime() {
		return stateTime;
	}	

}
