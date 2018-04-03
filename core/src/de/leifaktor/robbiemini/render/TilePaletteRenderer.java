package de.leifaktor.robbiemini.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.leifaktor.robbiemini.RobbieMini;
import de.leifaktor.robbiemini.screens.editor.Tiles;
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

public class TilePaletteRenderer {
	
	float xOffset;
	float yOffset;
	int tilesPerRow;
	int numberOfRows;
	int scale = 2;

	public void render(SpriteBatch batch) {
		for (int i = 0; i < Tiles.tiles.size(); i++) {
			float x = xOffset + (i % tilesPerRow)*RobbieMini.TILESIZE*scale;
			float y = yOffset + (i / tilesPerRow)*RobbieMini.TILESIZE*scale;
			batch.draw(Graphics.textures.get("palette_background"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
			if (Tiles.tiles.get(i) instanceof Wall) batch.draw(Graphics.textures.get("wall_tile"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
			if (Tiles.tiles.get(i) instanceof DarkWall) batch.draw(Graphics.textures.get("dark_wall_tile"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
			if (Tiles.tiles.get(i) instanceof EmptyTile) batch.draw(Graphics.textures.get("empty_tile"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
			if (Tiles.tiles.get(i) instanceof Door) {
				Door d = (Door) Tiles.tiles.get(i);
				batch.draw(Graphics.textures.get("door_" + d.getNumber()), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
			}
			if (Tiles.tiles.get(i) instanceof BridgeLeft) batch.draw(Graphics.textures.get("bridge_left"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
			if (Tiles.tiles.get(i) instanceof BridgeLR) batch.draw(Graphics.textures.get("bridge_lr"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
			if (Tiles.tiles.get(i) instanceof BridgeRight) batch.draw(Graphics.textures.get("bridge_right"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
			if (Tiles.tiles.get(i) instanceof BridgeUp) batch.draw(Graphics.textures.get("bridge_up"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
			if (Tiles.tiles.get(i) instanceof BridgeUD) batch.draw(Graphics.textures.get("bridge_ud"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
			if (Tiles.tiles.get(i) instanceof BridgeDown) batch.draw(Graphics.textures.get("bridge_down"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
			if (Tiles.tiles.get(i) instanceof Glass) batch.draw(Graphics.textures.get("glass_tile"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
		}
	}

	
	public void setOffset(float xOffset, float yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	public void setTilesPerRow(int tilesPerRow) {
		this.tilesPerRow = tilesPerRow;
		this.numberOfRows = Tiles.tiles.size() / tilesPerRow;
	}


	public Tile getTileAt(int screenX, int screenY) {
		screenX -= xOffset;
		screenY -= yOffset;
		int tileX = screenX / (RobbieMini.TILESIZE*scale);
		int tileY = screenY / (RobbieMini.TILESIZE*scale);
		if (tileX >= tilesPerRow) return null;
		int index = tileY*tilesPerRow + tileX;
		if (index >= 0 && index < Tiles.tiles.size()) return Tiles.tiles.get(index);
		else return null;
	}
	
}
