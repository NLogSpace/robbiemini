package de.leifaktor.robbiemini.actor;

public class SolidActor extends Actor {
	
	public static final int MOUNTAIN_1 = 0;
	public static final int MOUNTAIN_2 = 1;
	public static final int MOUNTAIN_3 = 2;
	public static final int MOUNTAIN_4 = 3;
	public static final int MOUNTAIN_5 = 4;
	public static final int MOUNTAIN_6 = 5;
	public static final int MOUNTAIN_7 = 6;
	public static final int MOUNTAIN_8 = 7;
	public static final int MOUNTAIN_9 = 8;
	public static final int MOUNTAIN_10 = 9;
	public static final int MOUNTAIN_11 = 10;
	public static final int MOUNTAIN_12 = 11;
	public static final int MOUNTAIN_13 = 12;
	public static final int MOUNTAIN_14 = 13;
	public static final int MOUNTAIN_15 = 14;
	public static final int MOUNTAIN_16 = 15;
	public static final int DARK_WALL_1 = 16;
	public static final int DARK_WALL_2 = 17;
	public static final int DARK_WALL_3 = 18;
	public static final int DARK_WALL_4 = 19;
	
	public int type;
	
	public SolidActor(int type) {
		this.type = type;
	}
	
	public SolidActor() {} // no-arg constructor for JSON

	@Override
	public boolean canBeEntered(Actor other) {
		return false;
	}

	@Override
	public Actor clone() {
		return new SolidActor(type);
	}

}
