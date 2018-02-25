package de.leifaktor.robbiemini.movement;

import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.actor.Actor;

public class FixedMovement implements IMovingBehaviour {
	
	int direction;
	boolean done;
	
	public FixedMovement(int direction) {
		this.direction = direction;
		done = false;
	}

	@Override
	public int getMoveDirection(Actor actor, Room room) {
		if (done) {
			return -1;
		} else {
			done = true;
			return direction;
		}
	}

}
