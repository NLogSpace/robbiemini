package de.leifaktor.robbiemini.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.leifaktor.robbiemini.RobbieMini;
import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.XYPos;
import de.leifaktor.robbiemini.actor.Actor;

public class RoomRenderer {

	final int TILESIZE = 16;
	Room room;

	float xOffset;
	float yOffset;
	
	int maxRenderLayer;
	int highlightLayer;

	public void setOffset(float x, float y) {
		this.xOffset = x;
		this.yOffset = y;
	}
	
	public void setRenderLayer(int max) {
		this.maxRenderLayer = max;
	}

	public void setRoom(Room room) {
		this.room = room;
	}
	
	public void highlightLayer(int layer) {
		highlightLayer = layer;
	}

	public void render(SpriteBatch batch) {
		if (room == null) {
			Graphics.largeFont.draw(batch, "Dieser Raum existiert nicht. Drücke ENTER zum Erstellen!", 30,230);
			return;
		}
		int numberOfLayers = room.getNumberOfLayers();
		for (int z = 0; z < numberOfLayers; z++) {
			if (highlightLayer == -1) {
				if (z > maxRenderLayer) batch.setColor(1, 1, 1, 0.5f); else batch.setColor(1, 1, 1, 1);
			} else {
				if (z == highlightLayer) batch.setColor(1,1,1,1); else batch.setColor(1, 1, 1, 0.2f);
			}			
			for (int i = 0; i < room.width; i++) {
				for (int j = 0; j < room.height; j++) {
					TileRenderer.render(batch, room.getTile(i, j, z), (i+xOffset)*TILESIZE, (j+yOffset)*TILESIZE);
				}
			}
			for (Actor a : room.actors) {
				if (a.z != z) continue;
				ActorRenderer.render(batch, a, (a.getPosition().x+xOffset)*RobbieMini.TILESIZE, (a.getPosition().y+yOffset)*RobbieMini.TILESIZE);				
			}
		}
		batch.setColor(1, 1, 1, 1);
	}

	public XYPos getPositionInRoom(int x, int y, Room room) {
		x -= xOffset;
		y -= yOffset;
		int tileX = x / (RobbieMini.TILESIZE);
		int tileY = y / (RobbieMini.TILESIZE);
		if (tileX < 0 || tileX >= room.width) return null;
		if (tileY < 0 || tileY >= room.height) return null;
		return new XYPos(tileX, tileY);
	}

}
