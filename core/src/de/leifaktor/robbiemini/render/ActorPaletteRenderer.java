package de.leifaktor.robbiemini.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.leifaktor.robbiemini.RobbieMini;
import de.leifaktor.robbiemini.actor.Actor;
import de.leifaktor.robbiemini.screens.editor.Actors;

public class ActorPaletteRenderer {

	float xOffset;
	float yOffset;
	int tilesPerRow;
	int numberOfRows;
	int scale = 2;

	public void render(SpriteBatch batch) {
		for (int i = 0; i < Actors.actors.size(); i++) {			
			float x = xOffset + (i % tilesPerRow)*RobbieMini.TILESIZE*scale;
			float y = yOffset + (i / tilesPerRow)*RobbieMini.TILESIZE*scale;			
			batch.draw(Graphics.textures.get("palette_background"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
			ActorRenderer.render(batch,Actors.actors.get(i), x, y, scale);
		}
	}
	
	public void setOffset(float xOffset, float yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	public void setTilesPerRow(int tilesPerRow) {
		this.tilesPerRow = tilesPerRow;
		this.numberOfRows = Actors.actors.size() / tilesPerRow;
	}


	public Actor getActorAt(int screenX, int screenY) {
		screenX -= xOffset;
		screenY -= yOffset;
		int tileX = screenX / (RobbieMini.TILESIZE*scale);
		int tileY = screenY / (RobbieMini.TILESIZE*scale);
		if (tileX >= tilesPerRow) return null;
		int index = tileY*tilesPerRow + tileX;
		if (index >= 0 && index < Actors.actors.size()) return Actors.actors.get(index);
		else return null;
	}
	
}
