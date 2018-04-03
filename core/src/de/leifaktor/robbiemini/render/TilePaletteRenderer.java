package de.leifaktor.robbiemini.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.leifaktor.robbiemini.RobbieMini;
import de.leifaktor.robbiemini.screens.editor.Tiles;
import de.leifaktor.robbiemini.tiles.Tile;

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
			TileRenderer.render(batch, Tiles.tiles.get(i), x, y, scale);
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
