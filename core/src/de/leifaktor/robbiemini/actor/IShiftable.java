package de.leifaktor.robbiemini.actor;

import de.leifaktor.robbiemini.Room;

public interface IShiftable {

	public void startShift(int direction, float speed, float remaniningDistanceInTheFirstFrame, Room room);
	
}
