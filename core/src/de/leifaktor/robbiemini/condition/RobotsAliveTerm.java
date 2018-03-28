package de.leifaktor.robbiemini.condition;

import de.leifaktor.robbiemini.Room;

public class RobotsAliveTerm extends Term {

	@Override
	public int evaluate(Room room) {
		return room.getNumberOfLivingRobots();
	}
	
}
