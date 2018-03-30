package de.leifaktor.robbiemini.items;

import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.SoundPlayer;
import de.leifaktor.robbiemini.actor.Actor;
import de.leifaktor.robbiemini.actor.ItemActor;
import de.leifaktor.robbiemini.actor.Player;
import de.leifaktor.robbiemini.commands.PlaySoundCommand;

public class Notiz extends Item {
	
	String text;
	
	public Notiz() {} // no-arg constructor for JSON
	
	public Notiz(String text) {
		this.text = text;
	}

	@Override
	public void onUse(Room room, int x, int y, int z) {
		room.showTextbox(text);
	}

	@Override
	public Item clone() {		
		return new Notiz(text);
	}
	
	@Override
	public void onHitBy(Room room, ItemActor itemActor, Actor actor, int x, int y, int z) {		
		if (actor instanceof Player) {
			room.commands.add(new PlaySoundCommand(SoundPlayer.SOUND_COLLECT_SONG_1));
			itemActor.collect(room, (Player) actor);
		}
	}

}
