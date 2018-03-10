package de.leifaktor.robbiemini.render;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import de.leifaktor.robbiemini.Inventory;
import de.leifaktor.robbiemini.RobbieMini;
import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.items.Acid;
import de.leifaktor.robbiemini.items.Item;
import de.leifaktor.robbiemini.items.Key;

public class InventoryRenderer {

	Room room;
	
	float xOffset;
	float yOffset;
	
	TextureRegion greenBackground;
	TextureRegion whiteBackground;
	TextureRegion blackBackground;
	TextureRegion acid;
	TextureRegion[] keys;
	
	public InventoryRenderer() {
		Tileset tileset = new Tileset("tileset16.png", 16);
		greenBackground = tileset.getTile(5, 13);
		whiteBackground = tileset.getTile(2, 13);
		blackBackground = tileset.getTile(4, 13);
		acid = tileset.getTile(2, 10);
		keys = tileset.getTiles(0, 12, 16);
	}
	
	public void render(SpriteBatch batch, Room room, Inventory inventory) {
		float startX = xOffset + RobbieMini.TILESIZE*room.getWidth() / 2 - inventory.getSize()*RobbieMini.TILESIZE / 2;
		
		for (int i = 0; i < room.getWidth(); i++) {
			batch.draw(greenBackground, xOffset+RobbieMini.TILESIZE*i, yOffset);
		}
		
		for (int i = 0; i < inventory.getSize(); i++) {
			if (i != inventory.getPointer()) {
				batch.draw(whiteBackground, startX+RobbieMini.TILESIZE*i, yOffset);
			} else {
				batch.draw(blackBackground, startX+RobbieMini.TILESIZE*i, yOffset);
			}
		}
		List<Item> items = inventory.getItems();
		for (int i = 0; i < items.size(); i++) {
			Item item = items.get(i);
			if (item instanceof Acid) {
				batch.draw(acid, startX+RobbieMini.TILESIZE*i, yOffset);
			} else if (item instanceof Key) {
				Key key = (Key) item;
				batch.draw(keys[key.getNumber()], startX+RobbieMini.TILESIZE*i, yOffset);
			} else {
				batch.draw(greenBackground, startX+RobbieMini.TILESIZE*i, yOffset);
			}
		}
	}
	
	public void setOffset(float x, float y) {
		this.xOffset = x;
		this.yOffset = y;
	}
	
}
