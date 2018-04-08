package de.leifaktor.robbiemini;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.leifaktor.robbiemini.items.Blaumann;
import de.leifaktor.robbiemini.items.Flossen;
import de.leifaktor.robbiemini.items.IceSkates;
import de.leifaktor.robbiemini.items.Item;

public class Inventory {
	
	List<Item> items;
	
	int pointer;
		
	public Inventory() {
		items = new ArrayList<Item>();
		pointer = 0;
	}
	
	public void update() {
		if (InputManager.justPressed[InputManager.EAST]) increasePointer();
		if (InputManager.justPressed[InputManager.WEST]) decreasePointer();
	}

	public void add(Item item) {
		items.add(item);		
	}
	
	public boolean hasItem(Item other) {
		return items.contains(other);
	}
	
	public List<Item> getItems() {
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
		if (pointer >= 0 && pointer < items.size()) return items.get(pointer);
		else return null;
	}

	public void removeSelectedItem() {
		items.remove(pointer);
	}

	public int getSize() {
		return items.size();
	}

	public boolean hasBlaumann() {
		return items.contains(new Blaumann());
	}

	public boolean hasIceSkates() {		
		return items.contains(new IceSkates());
	}

	public boolean hasFlossen() {
		return items.contains(new Flossen());
	}	
}
