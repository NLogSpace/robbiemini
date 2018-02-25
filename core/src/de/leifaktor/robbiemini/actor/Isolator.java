package de.leifaktor.robbiemini.actor;

public class Isolator extends Actor {

	public Isolator(int x, int y) {
		super(x, y);
	}	

	@Override
	public boolean canBeEntered(Actor other) {
		return false;
	}

	@Override
	public boolean canBeShifted(Actor actor) {		
		return actor instanceof Player;
	}	
	

}
