package de.leifaktor.robbiemini.items;

import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.SoundPlayer;
import de.leifaktor.robbiemini.actor.Actor;
import de.leifaktor.robbiemini.actor.ItemActor;
import de.leifaktor.robbiemini.actor.Player;
import de.leifaktor.robbiemini.commands.AddActorCommand;
import de.leifaktor.robbiemini.commands.PlaySoundCommand;

public class Life extends Item {

	@Override
	public void onUse(Room room, int x, int y) {
		if (room.getPlayer().getLives() < 3) {
			room.getPlayer().increaseLives();
			room.commands.add(new PlaySoundCommand(SoundPlayer.SOUND_LIFE));
		} else {
			room.commands.add(new AddActorCommand(new ItemActor(x, y, this)));
		}
	}

	@Override
	public Item clone() {
		return new Life();
	}

	@Override
	public void onHitBy(Room room, ItemActor itemActor, Actor actor, int x, int y) {		
		if (actor instanceof Player) {
			room.commands.add(new PlaySoundCommand(SoundPlayer.SOUND_COLLECT));
			itemActor.collect(room, (Player) actor);
		}
	}

}
