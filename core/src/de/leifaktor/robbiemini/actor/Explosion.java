package de.leifaktor.robbiemini.actor;

import com.badlogic.gdx.Gdx;

import de.leifaktor.robbiemini.Room;

public class Explosion extends Actor {

	float time;
	
	public Explosion() {} // no-arg constructor for JSON
	
	public Explosion(int x, int y, int z) {
		super(x, y, z);
	}
	
	@Override
	public void update(Room room) {
		time += Gdx.graphics.getDeltaTime();
		if (time > 1.5f) remove();
	}
	
	public float getTime() {
		return time;
	}

	@Override
	public Actor clone() {
		return new Explosion(x, y, z);
	}

}
