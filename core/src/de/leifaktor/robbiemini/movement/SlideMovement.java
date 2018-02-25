package de.leifaktor.robbiemini.movement;

import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.actor.MoveableActor;

public class SlideMovement implements IMovingBehaviour {

	int dir;
	IMovingBehaviour usualBehaviour;
	
	public SlideMovement(int dir, IMovingBehaviour usualBehaviour) {
		this.dir = dir;
		this.usualBehaviour = usualBehaviour;
	}
	
	@Override
	public int getMoveDirection(MoveableActor actor, Room room) {
		return dir;
	}
	
	public IMovingBehaviour getUsualBehaviour() {
		return usualBehaviour;
	}

}
