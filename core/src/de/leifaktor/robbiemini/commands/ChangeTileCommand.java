package de.leifaktor.robbiemini.commands;

import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.tiles.Tile;

public class ChangeTileCommand extends Command {

	int x;
	int y;
	Tile tile;
	
	public ChangeTileCommand(int x, int y, Tile tile) {
		this.x = x;
		this.y = y;
		this.tile = tile;
	}
	
	public void execute(Room room) {
		room.setTile(x, y, tile);
	}
}
