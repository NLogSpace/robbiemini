package de.leifaktor.robbiemini.items;

import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.SoundPlayer;
import de.leifaktor.robbiemini.actor.ItemActor;
import de.leifaktor.robbiemini.actor.Magnetic;
import de.leifaktor.robbiemini.commands.AddActorCommand;
import de.leifaktor.robbiemini.commands.PlaySoundCommand;

public class Magnet extends Item implements Magnetic {
	
	boolean positive;
	
	public Magnet(boolean positive) {
		this.positive = positive;
	}

	@Override
	public void onUse(Room room, int x, int y) {
		room.commands.add(new AddActorCommand(new ItemActor(x, y, this)));
	}

	@Override
	public Item clone() {
		return new Magnet(positive);
	}

	@Override
	public float getPolarization() {
		return positive ? 1 : -1;
	}

	public boolean isPositive() {		
		return positive;
	}

	@Override
	public void onCollect(Room room, int x, int y) {
		room.commands.add(new PlaySoundCommand(SoundPlayer.SOUND_COLLECT_CLICK));
	}

}
