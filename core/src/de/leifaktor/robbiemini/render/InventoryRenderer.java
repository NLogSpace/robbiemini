package de.leifaktor.robbiemini.render;

import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.leifaktor.robbiemini.Inventory;
import de.leifaktor.robbiemini.ItemStack;
import de.leifaktor.robbiemini.RobbieMini;
import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.items.Item;

public class InventoryRenderer {

	Room room;
	
	float xOffset;
	float yOffset;
	
	public void render(SpriteBatch batch, Room room, Inventory inventory) {
		float startX = xOffset + RobbieMini.TILESIZE*room.width / 2 - inventory.getSize()*RobbieMini.TILESIZE / 2;
		
		for (int i = 0; i < room.width; i++) {
			batch.draw(Graphics.textures.get("inventory_outer_background"), xOffset+RobbieMini.TILESIZE*i, yOffset);
		}
		
		for (int i = 0; i < inventory.getSize(); i++) {
			if (i != inventory.getPointer()) {
				batch.draw(Graphics.textures.get("inventory_unselected_background"), startX+RobbieMini.TILESIZE*i, yOffset);
			} else {
				batch.draw(Graphics.textures.get("inventory_selected_background"), startX+RobbieMini.TILESIZE*i, yOffset);
			}
		}
		List<ItemStack> items = inventory.getItems();
		for (int i = 0; i < items.size(); i++) {
			ItemStack stack = items.get(i);
			Item item = stack.getItem();
			int amount = stack.getAmount();
			ItemRenderer.render(batch, item, startX+RobbieMini.TILESIZE*i, yOffset);
			if (amount > 1) {
				Graphics.smallFont.setColor(Color.BLACK);
				Graphics.smallFont.draw(batch, "" + amount, startX+RobbieMini.TILESIZE*i+4, yOffset+4);				
				Graphics.smallFont.setColor(Color.RED);
				Graphics.smallFont.draw(batch, "" + amount, startX+RobbieMini.TILESIZE*i+3, yOffset+5);
			}
			
		}
	}
	
	public void setOffset(float x, float y) {
		this.xOffset = x;
		this.yOffset = y;
	}
	
}
