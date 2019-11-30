package de.leifaktor.robbiemini;

import de.leifaktor.robbiemini.items.Item;

public class ItemStack {
	
	Item item;
	int amount;

	public ItemStack(Item item, int amount) {
		this.item = item;
		this.amount = amount;
	}
	
	public void push() {
		amount++;
	}
	
	public void pop() {
		amount--;
	}
	
	public Item getItem() {
		return item;
	}
	
	public int getAmount() {
		return amount;
	}
	
}
