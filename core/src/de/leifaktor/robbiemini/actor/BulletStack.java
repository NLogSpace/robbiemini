package de.leifaktor.robbiemini.actor;

import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.commands.AddActorCommand;

public class BulletStack extends Actor {
	
	int number;

	public BulletStack(int x, int y, int number) {
		super(x, y);
		this.number = number;
	}	

	@Override
	public void hitBy(Room room, Actor actor) {		
		if (actor instanceof Player) {
			((Player)actor).collectBullets(number);
			this.remove();
		}
		if (actor instanceof Robot) {			
			number--;
			if (number == 0) remove();
			room.commands.add(new AddActorCommand(new FlyingBullet(x, y, actor.direction, 0.25f)));
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
		return new BulletStack(x, y, number);
	}
	
	public int getNumber() {
		return number;
	}

}
