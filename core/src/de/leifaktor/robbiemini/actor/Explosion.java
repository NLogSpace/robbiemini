package de.leifaktor.robbiemini.actor;

import com.badlogic.gdx.Gdx;

import de.leifaktor.robbiemini.Room;

public class Explosion extends Actor {

	float time;
	
	public Explosion(int x, int y) {
		super(x, y);
	}
	
	@Override
	public void update(Room room) {
		time += Gdx.graphics.getDeltaTime();
		if (time > 1.5f) remove();
	}
	
	public float getTime() {
		return time;
	}

}
