package de.leifaktor.robbiemini.tiles;

import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.SoundPlayer;
import de.leifaktor.robbiemini.actor.Actor;
import de.leifaktor.robbiemini.actor.Player;
import de.leifaktor.robbiemini.commands.PlaySoundCommand;
import de.leifaktor.robbiemini.items.Key;

public class Door extends Tile {

	int number;

	public Door(int number) {
		this.number = number;
	}

	public int getNumber() {
		return number;
	}

	@Override
	public boolean canBeEntered(Actor actor, int dir) {
		if (!(actor instanceof Player)) return false;
		Player p = (Player) actor;
		if (!p.inventory.hasItem(new Key(number))) return false;
		return true;
	}

	@Override
	public void onEnter(Room room, Actor actor, int dir) {
		if (actor instanceof Player) room.commands.add(new PlaySoundCommand(SoundPlayer.SOUND_DOOR));
	}
	
	
	
}
