package de.leifaktor.robbiemini.actor;

import com.badlogic.gdx.Gdx;

import de.leifaktor.robbiemini.CollisionDetector;
import de.leifaktor.robbiemini.Direction;
import de.leifaktor.robbiemini.InputManager;
import de.leifaktor.robbiemini.Inventory;
import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.SoundPlayer;
import de.leifaktor.robbiemini.XYZPos;
import de.leifaktor.robbiemini.commands.PlaySoundCommand;
import de.leifaktor.robbiemini.tiles.Ice;
import de.leifaktor.robbiemini.tiles.Water;

public class Player extends Actor {

	public Inventory inventory;
	int gold;
	int bullets;
	int lives;

	int respawnTimer;
	XYZPos respawnPosition;
	State state;
	float stateTime;
	
	float walkSoundTimer;
	static final float WALK_SOUND_INTERVAL = 0.14f;

	public enum State {
		IDLE,
		WALKING,
		RESPAWNING,
		SLIDING,
		SWIMMING
	}

	public Player() {
		this(0,0,0);
	} // no-arg constructor for JSON

	public Player(int x, int y, int z) {		
		super(x, y, z);
		spawn(x, y, z);
		inventory = new Inventory();
		speed = 0.12f;
		lives = 3;
		stateTime = 0;
	}

	@Override
	public void update(Room room) {
		super.update(room);
		stateTime += Gdx.graphics.getDeltaTime();
		if (isOnTile && state != State.RESPAWNING) {
			if (room.getTile(x, y, z) instanceof Ice) setState(State.SLIDING);
			else if (room.getTile(x, y, z) instanceof Water) setState(State.SWIMMING);
			else {
				if (state == State.SLIDING || state == State.SWIMMING) {
					setState(State.IDLE);
				}
			}
		}
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
			if (isOnTile) performWalkAction(room);
			break;
		case WALKING:
			walkSoundTimer += Gdx.graphics.getDeltaTime();
			if (walkSoundTimer > WALK_SOUND_INTERVAL) {
				walkSoundTimer -= WALK_SOUND_INTERVAL;
				room.commands.add(new PlaySoundCommand(SoundPlayer.SOUND_STEPS));
			}
			if (isOnTile) performWalkAction(room);			
			break;
		case SLIDING:
			if (isOnTile) performSlideAction(room);
			break;
		case SWIMMING:
			if (isOnTile) performSwimAction(room);
			break;
		default:
			break;
		}
	}

	private void performWalkAction(Room room) {
		speedMultiplier = 1f;
		int intendedDir = getKeyboardDirection(room);
		if (intendedDir > -1) {
			boolean canMove = CollisionDetector.canMoveTo(this, room, intendedDir);
			boolean canShift = CollisionDetector.canShiftTo(this, room, intendedDir);
			if (canMove) {
				room.onLeave(this, x, y, z, intendedDir);
				initMove(intendedDir);						
				move(Math.min(remainingDistance, distanceUntilNextTile));
				setState(State.WALKING);
			} else if (canShift) {
				room.onLeave(this, x, y, z, intendedDir);
				room.startShift(this, x, y, z, intendedDir);
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

	private void performSlideAction(Room room) {
		speedMultiplier = 1f;
		int intendedDir = getKeyboardDirection(room);
		int slideDirection = direction;
		boolean canMove = CollisionDetector.canMoveTo(this, room, slideDirection);
		boolean canShift = CollisionDetector.canShiftTo(this, room, slideDirection);
		if (!inventory.hasIceSkates() && (canMove || canShift)) {
			intendedDir = direction;
		} else {
			if (intendedDir == -1) intendedDir = direction;
		}
		if (intendedDir > -1) {
			canMove = CollisionDetector.canMoveTo(this, room, intendedDir);
			canShift = CollisionDetector.canShiftTo(this, room, intendedDir);
			if (canMove) {
				room.onLeave(this, x, y, z, intendedDir);
				initMove(intendedDir);						
				move(Math.min(remainingDistance, distanceUntilNextTile));
			} else if (canShift) {
				room.onLeave(this, x, y, z, intendedDir);
				room.startShift(this, x, y, z, intendedDir);
				initMove(intendedDir);						
				move(Math.min(remainingDistance, distanceUntilNextTile));
			} else {				
				setState(State.IDLE);
			}
		} else {
			setState(State.IDLE);
		}
	}

	private void performSwimAction(Room room) {
		speedMultiplier = 0.4f;
		int intendedDir = getKeyboardDirection(room);
		int waterDir = ((Water)room.getTile(x, y, z)).type;
		if (inventory.hasFlossen()) {
			if (Direction.isOpposite(intendedDir, waterDir)) {
				speedMultiplier = 0.1f;
				intendedDir = waterDir;
			} else if (Direction.isLeftOf(intendedDir, waterDir)) {
				intendedDir = Direction.getNextDirCounterClockwise(waterDir);
			} else if (Direction.isLeftOf(waterDir, intendedDir)) {
				intendedDir = Direction.getNextDirClockwise(waterDir);
			}
			if (intendedDir == -1) intendedDir = waterDir;
		} else {
			if (waterDir != -1) {
				boolean canMove = CollisionDetector.canMoveTo(this, room, waterDir);
				boolean canShift = CollisionDetector.canShiftTo(this, room, waterDir);
				if (!canMove && !canShift) {
					die(room);
					return;
				}
				intendedDir = waterDir;
			}	
		}
		if (intendedDir > -1) {
			boolean canMove = CollisionDetector.canMoveTo(this, room, intendedDir);
			boolean canShift = CollisionDetector.canShiftTo(this, room, intendedDir);
			if (canMove) {
				room.onLeave(this, x, y, z, intendedDir);
				initMove(intendedDir);						
				move(Math.min(remainingDistance, distanceUntilNextTile));
			} else if (canShift) {
				room.onLeave(this, x, y, z, intendedDir);
				room.startShift(this, x, y, z, intendedDir);
				initMove(intendedDir);						
				move(Math.min(remainingDistance, distanceUntilNextTile));
			}
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
		return new Player(x, y, z);
	}

	public void spawn(int x, int y, int z) {
		setPosition(x, y, z);
		respawnPosition = new XYZPos(x, y, z);
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
