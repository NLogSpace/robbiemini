package de.leifaktor.robbiemini;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class InputManager {
	
	public static final int NUMBER_OF_KEYS = 9;
	
	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	public static final int NORTH_EAST = 4;
	public static final int SOUTH_EAST = 5;
	public static final int SOUTH_WEST = 6;
	public static final int NORTH_WEST = 7;
	public static final int ENTER = 8;

	public static boolean[] pressed = new boolean[NUMBER_OF_KEYS];
	public static boolean[] justPressed = new boolean[NUMBER_OF_KEYS];
	
	public static int[] keyMap = new int[NUMBER_OF_KEYS];
	
	public static void initKeyMap() {
		keyMap[NORTH] = Keys.UP;
		keyMap[EAST] = Keys.RIGHT;
		keyMap[SOUTH] = Keys.DOWN;
		keyMap[WEST] = Keys.LEFT;
		keyMap[NORTH_EAST] = Keys.PAGE_UP;
		keyMap[SOUTH_EAST] = Keys.PAGE_DOWN;
		keyMap[SOUTH_WEST] = Keys.END;
		keyMap[NORTH_WEST] = Keys.HOME;
		keyMap[ENTER] = Keys.ENTER;
	}

	
	public static void update() {
		for (int i = 0; i < NUMBER_OF_KEYS; i++) {
			pressed[i] = Gdx.input.isKeyPressed(keyMap[i]);
			justPressed[i] = Gdx.input.isKeyJustPressed(keyMap[i]);
		}
	}
	
}
