package de.leifaktor.robbiemini.actor;

import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.SoundPlayer;
import de.leifaktor.robbiemini.commands.PlaySoundCommand;
import de.leifaktor.robbiemini.commands.RemoveActorCommand;

public class Key extends Actor {
	
	int number;	
	
	public Key(int x, int y, int number) {
		super(x, y);
		this.number = number;
	}

	public int getNumber() {
		return number;
	}
	
	@Override
	public void update(Room room) {
		
	}

	@Override
	public void hitBy(Room room, Actor actor) {
		if (actor instanceof Player) {
			((Player)actor).inventory.addKey(number);
			room.commands.add(new RemoveActorCommand(this));
			room.commands.add(new PlaySoundCommand(SoundPlayer.SOUND_COLLECT));
		}
	}

	@Override
	public Actor clone() {
		return new Key(x, y, number);
	}
	
	

}
