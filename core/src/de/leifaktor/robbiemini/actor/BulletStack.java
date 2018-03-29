package de.leifaktor.robbiemini.actor;

import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.SoundPlayer;
import de.leifaktor.robbiemini.commands.AddActorCommand;
import de.leifaktor.robbiemini.commands.PlaySoundCommand;

public class BulletStack extends Actor {
	
	int number;

	public BulletStack(int x, int y, int z, int number) {
		super(x, y, z);
		this.number = number;
	}	

	@Override
	public void hitBy(Room room, Actor actor) {		
		if (actor instanceof Player) {
			((Player)actor).collectBullets(number);
			room.commands.add(new PlaySoundCommand(SoundPlayer.SOUND_COLLECT_CLICK));
			this.remove();
		}
		if (actor instanceof Robot) {
			number--;
			if (number == 0) remove();
			room.commands.add(new AddActorCommand(new FlyingBullet(x, y, z, actor.direction, 0.25f)));
		}
	}

	@Override
	public boolean canBeEntered(Actor other) {		
		return other instanceof Player || other instanceof Robot;
	}

	@Override
	public boolean canBeShifted(Actor actor) {
		return false;
	}

	@Override
	public Actor clone() {
		return new BulletStack(x, y, z, number);
	}
	
	public int getNumber() {
		return number;
	}

}
