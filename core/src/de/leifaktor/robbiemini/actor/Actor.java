package de.leifaktor.robbiemini.actor;

import de.leifaktor.robbiemini.Direction;
import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.Vec2;
import de.leifaktor.robbiemini.XYZPos;

public abstract class Actor {

	public int x;
	public int y;
	public int z;

	protected Vec2 pos;
	private Vec2 vel;

	protected int direction;
	protected float speed; // in tiles per frame
	protected float remainingDistance; // how much distance can this entity still move in this frame?
	protected float distanceUntilNextTile;
	boolean moving;
	boolean justReachedTile;
	boolean isOnTile;

	boolean shouldBeRemoved;

	public Actor(int x, int y, int z) {
		setPosition(x, y, z);
		vel = new Vec2(0,0);
		this.direction = -1;
		this.speed = 0f;
		this.remainingDistance = 0;
		this.moving = false;
		this.justReachedTile = false;
		this.isOnTile = true;
		shouldBeRemoved = false;
	}

	public boolean canBeEntered(Actor other) {
		return true;
	}

	public boolean canBeShifted(Actor actor) {
		return false;
	}

	public boolean shouldBeRemoved() {
		return shouldBeRemoved;
	}

	public void remove() {
		shouldBeRemoved = true;
	}

	public void hitBy(Room room, Actor actor) {

	}

	public void continueMovement() {
		this.remainingDistance = speed;
		if (moving) move(Math.min(remainingDistance, distanceUntilNextTile));
	}

	public void update(Room room) {
		if (justReachedTile) {
			room.onEnter(this, x, y, z, direction);
			justReachedTile = false;
		}
		if (moving)	move(Math.min(remainingDistance, distanceUntilNextTile));
	}

	protected void move(float distance) {
		Vec2 vector = new Vec2(vel.x, vel.y);
		vector.normalize();
		vector.multiply(distance);
		pos.add(vector);
		distanceUntilNextTile -= distance;
		remainingDistance -= distance;
		if (distanceUntilNextTile < 0.000000001) {
			distanceUntilNextTile = 0;
			setPosition(x, y, z);
			justReachedTile = true;
			isOnTile = true;
			moving = false;
		}
	}

	protected void initMove(int dir) {
		if (dir >= 0 && dir < 8) {
			direction = dir;
			vel = Direction.directionToVector(dir);
			vel.multiply(speed);
			moving = true;
			isOnTile = false;
			distanceUntilNextTile = (dir < 4 ? 1 : 1.414213562f);
			x += Direction.DIR_X[dir];
			y += Direction.DIR_Y[dir];
		}
	}
	
	public void stopMoving() {
		moving = false;
		isOnTile = true;
	}

	public void setPosition(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.pos = new Vec2(x,y);
	}

	public void setPosition(Vec2 position) {
		setPosition((int) position.x, (int) position.y, z);
		this.pos = position;
	}
	
	public void setPosition(XYZPos position) {
		setPosition(position.x, position.y, position.z);
	}

	public Vec2 getPosition() {
		return pos;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getRemainingDistance() {		
		return remainingDistance;
	}

	public void setRemainingDistance(float remainingDistance) {
		this.remainingDistance = remainingDistance;
	}

	public abstract Actor clone();

	public boolean isOnTile() {		
		return isOnTile;
	}
	
	public int getDirection() {
		return direction;
	}

}
