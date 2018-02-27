package de.leifaktor.robbiemini.actor;

import com.badlogic.gdx.Gdx;

import de.leifaktor.robbiemini.CollisionDetector;
import de.leifaktor.robbiemini.Direction;
import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.Vec2;
import de.leifaktor.robbiemini.actor.Actor.MoveState;
import de.leifaktor.robbiemini.movement.IMovingBehaviour;

public abstract class Actor {
	
	public int x;
	public int y;
	
	private Vec2 pos;
	private Vec2 vel;

	protected int direction;
	protected float speed; // in tiles per frame
	protected float remainingDistance; // how much distance can this entity still move in this frame?
	protected float distanceUntilNextTile;
	protected float stateTime;
	protected MoveState state;
	protected IMovingBehaviour movingBehaviour;
	
	public enum MoveState {
		IDLE,
		MOVING,
		REACHED_TILE,
		RESPAWNING
	}
	
	boolean shouldBeRemoved;
	
	public Actor(int x, int y) {
		setPosition(x, y);
		vel = new Vec2(0,0);
		this.direction = -1;
		this.speed = 0f;
		this.remainingDistance = 0;
		this.state = MoveState.IDLE;
		this.stateTime = 0f;
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
		switch (state) {
		case IDLE: break;
		case REACHED_TILE: break;
		case RESPAWNING: break;
		case MOVING:
			move(Math.min(remainingDistance, distanceUntilNextTile));
			break;
		}
	}

	public void update(Room room) {
		if (state == MoveState.REACHED_TILE) room.onEnter(this, x, y, direction);
		
		switch (state) {
		case REACHED_TILE:
			performAction(room);
			break;
		case IDLE:
			performAction(room);
			break;
		case MOVING:
			move(Math.min(remainingDistance, distanceUntilNextTile));
			break;
		case RESPAWNING:
			break;
		}
		
		stateTime += Gdx.graphics.getDeltaTime();
	}
	
	private void move(float distance) {
		Vec2 vector = new Vec2(vel.x, vel.y);
		vector.normalize();
		vector.multiply(distance);
		pos.add(vector);
		distanceUntilNextTile -= distance;
		remainingDistance -= distance;
		if (distanceUntilNextTile < 0.000000001) {
			distanceUntilNextTile = 0;
			setPosition(x, y);
			this.state = MoveState.REACHED_TILE;
		}
	}
	
	private void performAction(Room room) {
		if (movingBehaviour != null) {
			int intendedDir = movingBehaviour.getMoveDirection(this, room);
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
				} else {				
					state = MoveState.IDLE;
				}
			} else {
				state = MoveState.IDLE;
			}
		}
	}

	private void initMove(int dir) {
		switch (state) {
		case MOVING: break;
		case RESPAWNING: break;
		case IDLE:
			if (dir >= 0 && dir < 8) {
				direction = dir;
				vel = Direction.directionToVector(dir);
				vel.multiply(speed);
				state = MoveState.MOVING;
				distanceUntilNextTile = (dir < 4 ? 1 : 1.414213562f);
				stateTime = 0;
				x += Direction.DIR_X[dir];
				y += Direction.DIR_Y[dir];
			}
			break;
		case REACHED_TILE:
			if (dir >= 0 && dir < 8) {
				direction = dir;
				vel = Direction.directionToVector(dir);
				vel.multiply(speed);
				state = MoveState.MOVING;
				distanceUntilNextTile = (dir < 4 ? 1 : 1.414213562f);
				x += Direction.DIR_X[dir];
				y += Direction.DIR_Y[dir];
			} else {
				state = MoveState.IDLE;
				stateTime = 0;
			}
			break;
		}
	}
	
	public float getStateTime() {
		return stateTime;
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
		this.pos = new Vec2(x,y);
	}

	public void setPosition(Vec2 position) {
		setPosition((int) position.x, (int) position.y);
	}

	public Vec2 getPosition() {
		return pos;
	}

	public MoveState getMoveState() {
		return state;
	}

	public void setMovingBehaviour(IMovingBehaviour movingBehaviour) {
		this.movingBehaviour = movingBehaviour;
	}
	
	public IMovingBehaviour getMovingBehaviour() {		
		return movingBehaviour;
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
	
	public boolean isRespawning() {
		return state == MoveState.RESPAWNING;
	}
	
	public abstract Actor clone();
	
}
