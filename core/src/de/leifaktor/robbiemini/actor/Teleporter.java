package de.leifaktor.robbiemini.actor;

import com.badlogic.gdx.Gdx;

import de.leifaktor.robbiemini.Room;

public class Teleporter extends Actor {
	
	float stateTime;
	boolean active;

	public Teleporter(int x, int y) {
		super(x, y);
		active = false;
	}	

	@Override
	public boolean canBeEntered(Actor other) {		
		return other instanceof Player;
	}	

	@Override
	public void update(Room room) {
		super.update(room);
		stateTime += Gdx.graphics.getDeltaTime();
	}

	@Override
	public Actor clone() {
		return new Teleporter(x, y);
	}	
	
	public float getStateTime() {
		return stateTime;
	}
	
	public boolean isActive() {
		return active;
	}

}
