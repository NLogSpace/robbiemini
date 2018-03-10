package de.leifaktor.robbiemini.actor;

import de.leifaktor.robbiemini.Inventory;
import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.Vec2;

public class Player extends Actor {
	
	public Inventory inventory;
	int lives;
	int respawnTimer;
	Vec2 respawnPosition;
	
	public Player(int x, int y) {		
		super(x, y);
		spawn(x, y);
		inventory = new Inventory();
		speed = 0.16f;
		lives = 3;
	}

	public void die(Room room) {
		room.makeExplosion(x, y);
		lives--;
		if (lives > 0) {
			state = MoveState.RESPAWNING;
			respawnTimer = 180;
		}
	}
	
	@Override
	public void update(Room room) {
		super.update(room);
		if (state == MoveState.RESPAWNING) {
			respawnTimer--;
			if (respawnTimer == 0) {
				state = MoveState.IDLE;
				setPosition(respawnPosition);
			}
		}
	}

	public int getLives() {
		return lives;
	}
	
	@Override
	public void hitBy(Room room, Actor actor) {
		if (actor instanceof Robot) {
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
		state = MoveState.IDLE;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void increaseLives() {
		lives++;		
	}

}
