package de.leifaktor.robbiemini.commands;

import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.SoundPlayer;

public class PlaySoundCommand extends Command {
	
	int id;
	
	public PlaySoundCommand(int id) {
		this.id = id;
	}

	@Override
	public void execute(Room room) {
		SoundPlayer.getInstance().play(id);
	}

}
