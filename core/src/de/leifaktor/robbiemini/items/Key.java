package de.leifaktor.robbiemini.items;

import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.SoundPlayer;
import de.leifaktor.robbiemini.actor.Actor;
import de.leifaktor.robbiemini.actor.ItemActor;
import de.leifaktor.robbiemini.actor.Player;
import de.leifaktor.robbiemini.commands.AddActorCommand;
import de.leifaktor.robbiemini.commands.PlaySoundCommand;

public class Key extends Item {
	
	int number;
	
	public Key(int number) {
		this.number = number;
	}

	@Override
	public void onUse(Room room, int x, int y) {
		room.commands.add(new AddActorCommand(new ItemActor(x, y, this)));
	}

	@Override
	public Item clone() {
		return new Key(number);
	}

	public int getNumber() {		
		return number;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Key && ((Key)obj).getNumber() == number;
	}
	
	@Override
	public void onHitBy(Room room, ItemActor itemActor, Actor actor, int x, int y) {		
		if (actor instanceof Player) {
			room.commands.add(new PlaySoundCommand(SoundPlayer.SOUND_COLLECT));
			itemActor.collect(room, (Player) actor);
		}
	}
	
	
	

}
