package de.leifaktor.robbiemini.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.leifaktor.robbiemini.RobbieMini;
import de.leifaktor.robbiemini.Room;

public class StatusBarRenderer {
	
	Room room;
	
	float xOffset;
	float yOffset;
	
	public void render(SpriteBatch batch, Room room) {
		for (int i = 0; i < room.getWidth(); i++) {
			if (i < 4 || i >= room.getWidth() - 4) {
				batch.draw(Graphics.textures.get("status_bar_outer_background"), xOffset+RobbieMini.TILESIZE*i, yOffset);
			} else {
				batch.draw(Graphics.textures.get("status_bar_inner_background"), xOffset+RobbieMini.TILESIZE*i, yOffset);
			}
		}
		for (int i = 0; i < 3; i++) {
			if (room.getPlayer().getLives() > i) {
				batch.draw(Graphics.textures.get("full_heart"), xOffset+RobbieMini.TILESIZE*(i+5), yOffset);
			} else {
				batch.draw(Graphics.textures.get("empty_heart"), xOffset+RobbieMini.TILESIZE*(i+5), yOffset);
			}
		}
		
		batch.draw(Graphics.textures.get("gold"), xOffset+RobbieMini.TILESIZE*(28), yOffset);
		Graphics.smallFont.draw(batch, "" + room.getPlayer().getGold(), xOffset+RobbieMini.TILESIZE*(29), yOffset+6);
	}
	
	public void setOffset(float x, float y) {
		this.xOffset = x;
		this.yOffset = y;
	}

}
