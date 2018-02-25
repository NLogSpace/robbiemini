package de.leifaktor.robbiemini.tiles;

import de.leifaktor.robbiemini.CollisionDetector;
import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.actor.Actor;
import de.leifaktor.robbiemini.movement.SlideMovement;

public class Ice extends Tile {

	@Override
	public boolean canBeEntered(Actor actor) {
		return true;
	}

	@Override
	public void onEnter(Room room, Actor actor, int dir) {
		if (CollisionDetector.canMoveTo(actor, room, dir)) {
			if (! (actor.getMovingBehaviour() instanceof SlideMovement)) {
				actor.setMovingBehaviour(new SlideMovement(dir, actor.getMovingBehaviour()));
			}
		} else {
			if ((actor.getMovingBehaviour() instanceof SlideMovement)) {
				actor.setMovingBehaviour(((SlideMovement) actor.getMovingBehaviour()).getUsualBehaviour());
			}
		}
	}

	@Override
	public void onLeave(Room room, Actor actor, int dir) {
		if (actor.getMovingBehaviour() instanceof SlideMovement) {
			actor.setMovingBehaviour(((SlideMovement) actor.getMovingBehaviour()).getUsualBehaviour());
		}
	}
	
	
	
	

}
