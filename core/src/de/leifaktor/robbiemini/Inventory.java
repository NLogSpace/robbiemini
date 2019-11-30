package de.leifaktor.robbiemini;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.leifaktor.robbiemini.items.Blaumann;
import de.leifaktor.robbiemini.items.Flossen;
import de.leifaktor.robbiemini.items.IceSkates;
import de.leifaktor.robbiemini.items.Item;

public class Inventory {
	
	List<ItemStack> items;
	
	int pointer;
		
	public Inventory() {
		items = new ArrayList<ItemStack>();
		pointer = 0;
	}
	
	public void update() {
		if (InputManager.justPressed[InputManager.EAST]) increasePointer();
		if (InputManager.justPressed[InputManager.WEST]) decreasePointer();
	}

	public void add(Item item) {
		if (!item.isStackable()) {
			items.add(new ItemStack(item,1));
		} else {
			for (int i = 0; i < items.size(); i++) {
				if (items.get(i).item.getClass().equals(item.getClass())) {
					items.get(i).push();
					return;
				}
			}
			items.add(new ItemStack(item,1));
		}
	}
	
	public boolean hasItem(Item other) {
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).item.equals(other)) return true;
		}
		return false;
	}
	
	public List<ItemStack> getItems() {
		return Collections.unmodifiableList(items);
	}
	
	public int getPointer() {
		return pointer;
	}
	
	public void increasePointer() {
		if (pointer < items.size() - 1) pointer++;
	}
	
	public void decreasePointer() {
		if (pointer > 0) pointer--;
	}
	
	public Item getSelectedItem() {
		if (pointer >= 0 && pointer < items.size()) return items.get(pointer).getItem();
		else return null;
	}

	public void removeSelectedItem() {
		items.get(pointer).pop();
		if (items.get(pointer).getAmount() <= 0) items.remove(pointer);
	}

	public int getSize() {
		return items.size();
	}

	public boolean hasBlaumann() {
		return hasItem(new Blaumann());
	}

	public boolean hasIceSkates() {		
		return hasItem(new IceSkates());
	}

	public boolean hasFlossen() {
		return hasItem(new Flossen());
	}	
}
