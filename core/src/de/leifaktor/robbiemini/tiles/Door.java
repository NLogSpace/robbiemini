package de.leifaktor.robbiemini.tiles;

import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.SoundPlayer;
import de.leifaktor.robbiemini.actor.Actor;
import de.leifaktor.robbiemini.actor.MoveableActor;
import de.leifaktor.robbiemini.actor.Player;
import de.leifaktor.robbiemini.commands.PlaySoundCommand;

public class Door extends Tile {

	int number;

	public Door(int number) {
		this.number = number;
	}

	public int getNumber() {
		return number;
	}

	@Override
	public boolean canBeEntered(Actor actor) {
		if (!(actor instanceof Player)) return false;
		Player p = (Player) actor;
		if (!p.inventory.hasKey(number)) return false;
		return true;
	}

	@Override
	public void onEnter(Room room, MoveableActor actor, int dir) {
		if (actor instanceof Player) room.commands.add(new PlaySoundCommand(SoundPlayer.SOUND_DOOR));
	}
	
	
	
}
