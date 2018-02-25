package de.leifaktor.robbiemini.actor;

import com.badlogic.gdx.Gdx;

import de.leifaktor.robbiemini.Room;

public class DissolvingWall extends Actor {

	private float time;
	
	public DissolvingWall(int x, int y) {
		super(x, y);
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
	
	
	
}
