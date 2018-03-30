package de.leifaktor.robbiemini;

import de.leifaktor.robbiemini.tiles.Tile;

public class RoomLayer {
	
	int width;
	int height;
	Tile[] tiles;
	
	public RoomLayer() {} // no-arg constructor for JSON
	
	public RoomLayer(int width, int height, Tile[] tiles) {
		this.width = width;
		this.height = height;
		this.tiles = tiles;
	}
	
	public boolean isInBounds(int x, int y) {
		return (x >= 0 && x < width && y >= 0 && y < height);
	}
	
	public void setTile(int x, int y, Tile tile) {
		if (isInBounds(x,y)) tiles[y*width+x] = tile;
	}
	
	public Tile getTile(int x, int y) {
		if (!isInBounds(x, y)) return null;
		return tiles[width*y + x];
	}

}
