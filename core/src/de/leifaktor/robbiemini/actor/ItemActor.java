package de.leifaktor.robbiemini.actor;

import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.SoundPlayer;
import de.leifaktor.robbiemini.commands.PlaySoundCommand;
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
		if (actor instanceof Player) {
			item.onCollect(room, x, y);
			room.commands.add(new RemoveActorCommand(this));
			((Player)actor).inventory.add(item);
			room.commands.add(new PlaySoundCommand(SoundPlayer.SOUND_COLLECT));
		}
	}

	@Override
	public boolean canBeEntered(Actor other) {
		if (other instanceof Isolator || other instanceof Robot) return false;
		return true;
	}

	@Override
	public Actor clone() {
		return new ItemActor(x,y,item.clone());
	}


	public Item getItem() {
		return item;
	}

}
