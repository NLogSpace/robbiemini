package de.leifaktor.robbiemini.actor;

import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.commands.RemoveActorCommand;
import de.leifaktor.robbiemini.items.Item;

public class ItemActor extends Actor {

	Item item;

	public ItemActor(int x, int y, Item item) {
		super(x, y);
		this.item = item;
	}

	@Override
	public void hitBy(Room room, Actor actor) {
		item.onHitBy(room, this, actor, x, y);
	}
	
	public void collect(Room room, Player player) {
		room.commands.add(new RemoveActorCommand(this));
		player.inventory.add(item);
	}

	@Override
	public boolean canBeEntered(Actor other) {		
		return other instanceof Player;
	}

	@Override
	public Actor clone() {
		return new ItemActor(x,y,item.clone());
	}


	public Item getItem() {
		return item;
	}

}
