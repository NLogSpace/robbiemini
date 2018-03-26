package de.leifaktor.robbiemini.actor;

import com.badlogic.gdx.Gdx;

import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.items.Item;

public class TeleportDesReichtums extends Actor {
	
	Item item;
	float stateTime;

	public TeleportDesReichtums(int x, int y, Item item) {
		super(x, y);
		this.item = item;
	}	

	@Override
	public boolean canBeEntered(Actor other) {
		if (other instanceof Player) {
			if (((Player)other).getInventory().hasItem(item)) return true;
		}
		return false;
	}	

	@Override
	public void update(Room room) {
		super.update(room);
		stateTime += Gdx.graphics.getDeltaTime();
	}

	@Override
	public Actor clone() {
		return new TeleportDesReichtums(x, y, item.clone());
	}	
	
	public Item getItem() {
		return item;
	}

	public float getStateTime() {
		return stateTime;
	}	

}
