package de.leifaktor.robbiemini.movement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.actor.Actor;

public class KeyboardMovement implements IMovingBehaviour {

	@Override
	public int getMoveDirection(Actor actor, Room room) {
		int intendedDir = -1;
		if (Gdx.input.isKeyPressed(Keys.UP)) intendedDir = 0;
		else if (Gdx.input.isKeyPressed(Keys.RIGHT)) intendedDir = 1;
		else if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			intendedDir = 2;
		}
		else if (Gdx.input.isKeyPressed(Keys.LEFT)) intendedDir = 3;
		else if (Gdx.input.isKeyPressed(Keys.PAGE_UP)) intendedDir = 4;
		else if (Gdx.input.isKeyPressed(Keys.PAGE_DOWN)) intendedDir = 5;
		else if (Gdx.input.isKeyPressed(Keys.END)) intendedDir = 6;
		else if (Gdx.input.isKeyPressed(Keys.HOME)) intendedDir = 7;
		return intendedDir;
	}

}
