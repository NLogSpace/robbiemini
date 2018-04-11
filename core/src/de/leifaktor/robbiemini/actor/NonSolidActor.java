package de.leifaktor.robbiemini.actor;

public class NonSolidActor extends Actor {

	public static final int GRASS_NW = 0;
	public static final int GRASS_NE = 1;
	public static final int GRASS_SE = 2;
	public static final int GRASS_SW = 3;
	
	public int type;
	
	public NonSolidActor() {} // no-arg constructor for JSON
	
	public NonSolidActor(int type) {
		this.type = type;
	}
	
	@Override
	public Actor clone() {
		return new NonSolidActor(type);
	}

}
