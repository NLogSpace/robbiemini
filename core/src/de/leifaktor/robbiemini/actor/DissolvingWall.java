package de.leifaktor.robbiemini.actor;

import com.badlogic.gdx.Gdx;

import de.leifaktor.robbiemini.Room;

public class DissolvingWall extends Actor {

	private float time;
	
	public DissolvingWall() {} // no-arg constructor for JSON
	
	public DissolvingWall(int x, int y, int z) {
		super(x, y, z);
		time = 0;
	}
	
	public void update(Room room) {
		time += Gdx.graphics.getDeltaTime();
		if (time >= 4f) remove();
	}
	
	public float getTime() {
		return time;
	}

	@Override
	public boolean canBeEntered(Actor other) {
		return false;
	}

	@Override
	public Actor clone() {
		return new DissolvingWall(x, y, z);
	}
	
	
	
}
