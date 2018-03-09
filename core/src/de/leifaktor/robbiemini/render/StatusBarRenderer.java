package de.leifaktor.robbiemini.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import de.leifaktor.robbiemini.RobbieMini;
import de.leifaktor.robbiemini.Room;

public class StatusBarRenderer {
	
	Room room;
	
	float xOffset;
	float yOffset;
	
	TextureRegion grayBackground;
	TextureRegion whiteBackground;
	TextureRegion fullHeart;
	TextureRegion emptyHeart;
	
	public StatusBarRenderer() {
		Tileset tileset = new Tileset("tileset16.png", 16);
		grayBackground = tileset.getTile(3, 13);
		whiteBackground = tileset.getTile(2, 13);
		fullHeart = tileset.getTile(0, 13);
		emptyHeart = tileset.getTile(1, 13);
	}
	
	public void render(SpriteBatch batch, Room room) {
		for (int i = 0; i < room.getWidth(); i++) {
			if (i < 4 || i >= room.getWidth() - 4) {
				batch.draw(grayBackground, xOffset+RobbieMini.TILESIZE*i, yOffset);
			} else {
				batch.draw(whiteBackground, xOffset+RobbieMini.TILESIZE*i, yOffset);
			}
		}
		for (int i = 0; i < 3; i++) {
			if (room.getPlayer().getLives() > i) {
				batch.draw(fullHeart, xOffset+RobbieMini.TILESIZE*(i+5), yOffset);
			} else {
				batch.draw(emptyHeart, xOffset+RobbieMini.TILESIZE*(i+5), yOffset);
			}
		}
	}
	
	public void setOffset(float x, float y) {
		this.xOffset = x;
		this.yOffset = y;
	}

}
