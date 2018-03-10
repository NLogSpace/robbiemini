package de.leifaktor.robbiemini.movement;

import de.leifaktor.robbiemini.InputManager;
import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.actor.Actor;

public class KeyboardMovement implements IMovingBehaviour {

	@Override
	public int getMoveDirection(Actor actor, Room room) {
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

}
