package de.leifaktor.robbiemini.movement;

import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.actor.MoveableActor;

public interface IMovingBehaviour {
	
	public int getMoveDirection(MoveableActor actor, Room room);

}
