package de.leifaktor.robbiemini.items;

import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.SoundPlayer;
import de.leifaktor.robbiemini.actor.ItemActor;
import de.leifaktor.robbiemini.commands.AddActorCommand;
import de.leifaktor.robbiemini.commands.PlaySoundCommand;

public class Blaumann extends Item {

	@Override
	public void onUse(Room room, int x, int y) {
		room.commands.add(new AddActorCommand(new ItemActor(x, y, this)));
	}

	@Override
	public Item clone() {
		return new Blaumann();
	}

	@Override
	public boolean equals(Object obj) {		
		return obj instanceof Blaumann;
	}
	
	@Override
	public void onCollect(Room room, int x, int y) {
		room.commands.add(new PlaySoundCommand(SoundPlayer.SOUND_COLLECT));
	}	

}
