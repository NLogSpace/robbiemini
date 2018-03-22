package de.leifaktor.robbiemini.items;

import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.SoundPlayer;
import de.leifaktor.robbiemini.commands.PlaySoundCommand;

public class Notiz extends Item {
	
	String text;
	
	public Notiz(String text) {
		this.text = text;
	}

	@Override
	public void onUse(Room room, int x, int y) {
		room.showTextbox(text);
	}

	@Override
	public Item clone() {		
		return new Notiz(text);
	}
	
	@Override
	public void onCollect(Room room, int x, int y) {
		room.commands.add(new PlaySoundCommand(SoundPlayer.SOUND_COLLECT_CLICK));
	}

}
