package de.leifaktor.robbiemini.screens.editor;

import java.util.ArrayList;
import java.util.List;

import de.leifaktor.robbiemini.tiles.Air;
import de.leifaktor.robbiemini.tiles.BridgeDown;
import de.leifaktor.robbiemini.tiles.BridgeLR;
import de.leifaktor.robbiemini.tiles.BridgeLeft;
import de.leifaktor.robbiemini.tiles.BridgeRight;
import de.leifaktor.robbiemini.tiles.BridgeUD;
import de.leifaktor.robbiemini.tiles.BridgeUp;
import de.leifaktor.robbiemini.tiles.DarkWall;
import de.leifaktor.robbiemini.tiles.Door;
import de.leifaktor.robbiemini.tiles.EmptyTile;
import de.leifaktor.robbiemini.tiles.Glass;
import de.leifaktor.robbiemini.tiles.Tile;
import de.leifaktor.robbiemini.tiles.Wall;

public class Tiles {
	
	public static List<Tile> tiles;
	
	public static void init() {
		tiles = new ArrayList<Tile>();
		tiles.add(new EmptyTile());
		tiles.add(new Wall());
		tiles.add(new DarkWall());
		tiles.add(new Glass());
		for (int i = 0; i < 16; i++) tiles.add(new Door(i));
		tiles.add(new Air());
		tiles.add(new BridgeDown());
		tiles.add(new BridgeUD());
		tiles.add(new BridgeUp());
		tiles.add(new BridgeLeft());
		tiles.add(new BridgeLR());
		tiles.add(new BridgeRight());
	}

}
