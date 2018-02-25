package de.leifaktor.robbiemini.movement;

import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.actor.Actor;

public interface IMovingBehaviour {
	
	public int getMoveDirection(Actor actor, Room room);

}
