package de.leifaktor.robbiemini.actor;

import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.items.Blaumann;

public class ElectricFence extends Actor {

	public ElectricFence(int x, int y) {
		super(x, y);
	}

	@Override
	public boolean canBeEntered(Actor other) {
		if (other instanceof Player && ((Player)other).getInventory().hasItem(new Blaumann())) return false;
		if (other instanceof Isolator || other instanceof ElectricFence) return false;
		else return true;
	}

	@Override
	public boolean canBeShifted(Actor actor) {
		if (actor instanceof Player && ((Player)actor).getInventory().hasItem(new Blaumann())) return true;
		if (actor instanceof Isolator || actor instanceof ElectricFence) return true;
		else return false;
	}

	@Override
	public void hitBy(Room room, Actor actor) {
		if (actor instanceof Player) {
			((Player) actor).die(room);
			this.remove();
		}
		if (actor instanceof Robot) {
			((Robot)actor).explode(room);
			this.remove();
		}
	}

	@Override
	public Actor clone() {
		return new ElectricFence(x,y);
	}
	
	
	
	

}
