package de.leifaktor.robbiemini.render;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.leifaktor.robbiemini.Inventory;
import de.leifaktor.robbiemini.RobbieMini;
import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.items.Acid;
import de.leifaktor.robbiemini.items.Item;
import de.leifaktor.robbiemini.items.Key;
import de.leifaktor.robbiemini.items.Life;
import de.leifaktor.robbiemini.items.Magnet;

public class InventoryRenderer {

	Room room;
	
	float xOffset;
	float yOffset;
	
	public void render(SpriteBatch batch, Room room, Inventory inventory) {
		float startX = xOffset + RobbieMini.TILESIZE*room.getWidth() / 2 - inventory.getSize()*RobbieMini.TILESIZE / 2;
		
		for (int i = 0; i < room.getWidth(); i++) {
			batch.draw(Graphics.textures.get("inventory_outer_background"), xOffset+RobbieMini.TILESIZE*i, yOffset);
		}
		
		for (int i = 0; i < inventory.getSize(); i++) {
			if (i != inventory.getPointer()) {
				batch.draw(Graphics.textures.get("inventory_unselected_background"), startX+RobbieMini.TILESIZE*i, yOffset);
			} else {
				batch.draw(Graphics.textures.get("inventory_selected_background"), startX+RobbieMini.TILESIZE*i, yOffset);
			}
		}
		List<Item> items = inventory.getItems();
		for (int i = 0; i < items.size(); i++) {
			Item item = items.get(i);
			if (item instanceof Acid) {
				batch.draw(Graphics.textures.get("acid"), startX+RobbieMini.TILESIZE*i, yOffset);
			} else if (item instanceof Key) {
				Key key = (Key) item;
				batch.draw(Graphics.textures.get("key_" + key.getNumber()), startX+RobbieMini.TILESIZE*i, yOffset);
			} else if (item instanceof Life) {
				batch.draw(Graphics.textures.get("life"), startX+RobbieMini.TILESIZE*i, yOffset);
			} else if (item instanceof Magnet) {
				if (((Magnet)item).isPositive()) {
					batch.draw(Graphics.textures.get("magnet_positive"), startX+RobbieMini.TILESIZE*i, yOffset);
				} else {
					batch.draw(Graphics.textures.get("magnet_negative"), startX+RobbieMini.TILESIZE*i, yOffset);
				}
			} else {
				batch.draw(Graphics.textures.get("inventory_outer_background"), startX+RobbieMini.TILESIZE*i, yOffset);
			}
		}
	}
	
	public void setOffset(float x, float y) {
		this.xOffset = x;
		this.yOffset = y;
	}
	
}
