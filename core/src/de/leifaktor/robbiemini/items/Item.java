package de.leifaktor.robbiemini.items;

import de.leifaktor.robbiemini.Room;

public abstract class Item {
	
	public abstract void onUse(Room room, int x, int y);
	
	public abstract Item clone();	
	
}
