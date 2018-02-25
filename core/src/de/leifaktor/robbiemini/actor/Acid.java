package de.leifaktor.robbiemini.actor;

import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.SoundPlayer;
import de.leifaktor.robbiemini.commands.PlaySoundCommand;
import de.leifaktor.robbiemini.commands.RemoveActorCommand;

public class Acid extends Actor {
	
	public Acid(int x, int y) {
		super(x, y);
	}

	@Override
	public void update(Room room) {
		
	}

	@Override
	public void hitBy(Room room, Actor actor) {
		if (actor instanceof Player) {
			((Player)actor).inventory.addAcid();
			room.commands.add(new RemoveActorCommand(this));
			room.commands.add(new PlaySoundCommand(SoundPlayer.SOUND_COLLECT));
		}
	}

	@Override
	public boolean canBeEntered(Actor other) {
		if (other instanceof Isolator) return false;
		return true;
	}
	
	
	
	

}
