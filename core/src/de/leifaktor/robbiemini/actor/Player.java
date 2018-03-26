package de.leifaktor.robbiemini.actor;

import com.badlogic.gdx.Gdx;

import de.leifaktor.robbiemini.CollisionDetector;
import de.leifaktor.robbiemini.InputManager;
import de.leifaktor.robbiemini.Inventory;
import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.Vec2;

public class Player extends Actor {

	public Inventory inventory;
	int gold;
	int bullets;
	int lives;
	
	int respawnTimer;
	Vec2 respawnPosition;
	State state;
	float stateTime;
	
	public enum State {
		IDLE,
		WALKING,
		RESPAWNING
	}

	public Player(int x, int y) {		
		super(x, y);
		spawn(x, y);
		inventory = new Inventory();
		speed = 0.16f;
		lives = 3;
		stateTime = 0;
	}

	@Override
	public void update(Room room) {
		super.update(room);
		stateTime += Gdx.graphics.getDeltaTime();
		switch (state) {
		case RESPAWNING:
			respawnTimer--;
			if (respawnTimer == 0) {
				setState(State.IDLE);
				stateTime = 0;
				setPosition(respawnPosition);
			}
			break;
		case IDLE:
			if (isOnTile) performAction(room);
			break;
		case WALKING:
			if (isOnTile) performAction(room);
			break;
		}
	}

	private void performAction(Room room) {
		int intendedDir = getKeyboardDirection(room);
		if (intendedDir > -1) {
			boolean canMove = CollisionDetector.canMoveTo(this, room, intendedDir);
			boolean canShift = CollisionDetector.canShiftTo(this, room, intendedDir);
			if (canMove) {
				room.onLeave(this, x, y, intendedDir);
				initMove(intendedDir);						
				move(Math.min(remainingDistance, distanceUntilNextTile));
				setState(State.WALKING);
			} else if (canShift) {
				room.onLeave(this, x, y, intendedDir);
				room.startShift(this, x, y, intendedDir);
				initMove(intendedDir);						
				move(Math.min(remainingDistance, distanceUntilNextTile));
				setState(State.WALKING);
			} else {				
				setState(State.IDLE);
			}
		} else {
			setState(State.IDLE);
		}
	}
	
	private void setState(State state) {
		if (this.state != state) {
			stateTime = 0;
		}
		this.state = state;		
	}

	private int getKeyboardDirection(Room room) {
		if (room.getGameScreen().isInventoryOpen()) return -1;
		int intendedDir = -1;
		if (InputManager.pressed[InputManager.NORTH]) intendedDir = 0;
		else if (InputManager.pressed[InputManager.EAST]) intendedDir = 1;
		else if (InputManager.pressed[InputManager.SOUTH])	intendedDir = 2;
		else if (InputManager.pressed[InputManager.WEST]) intendedDir = 3;
		else if (InputManager.pressed[InputManager.NORTH_EAST]) intendedDir = 4;
		else if (InputManager.pressed[InputManager.SOUTH_EAST]) intendedDir = 5;
		else if (InputManager.pressed[InputManager.SOUTH_WEST]) intendedDir = 6;
		else if (InputManager.pressed[InputManager.NORTH_WEST]) intendedDir = 7;
		return intendedDir;
	}
	
	public void die(Room room) {
		room.makeExplosion(pos);
		lives--;
		if (lives > 0) {
			setState(State.RESPAWNING);
			respawnTimer = 180;
		}
	}

	public int getLives() {
		return lives;
	}

	@Override
	public void hitBy(Room room, Actor actor) {
		if (actor instanceof Robot || actor instanceof FlyingBullet) {
			if (!isRespawning()) {				
				die(room);
			}
		}
	}

	@Override
	public Actor clone() {
		return new Player(x,y);
	}

	public void spawn(int x, int y) {
		setPosition(x, y);
		respawnPosition = new Vec2(x, y);
		setState(State.IDLE);
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void increaseLives() {
		lives++;		
	}

	public boolean isRespawning() {
		return state == State.RESPAWNING;
	}
	
	public State getState() {
		return state;
	}
	
	public float getStateTime() {
		return stateTime;
	}

	public void collectGold() {
		gold++;
	}
	
	public int getGold() {
		return gold;
	}
	
	public void collectBullets(int number) {
		bullets += number;
	}
	
	public int getBullets() {
		return bullets;
	}

	public void removeBullet() {
		if (bullets > 0) bullets--;
	}

}
