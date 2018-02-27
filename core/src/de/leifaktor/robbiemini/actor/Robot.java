package de.leifaktor.robbiemini.actor;

import de.leifaktor.robbiemini.Room;

public class Robot extends Actor{
	
	public int graphicType;

	public Robot(int x, int y, float speed, int graphicType) {
		super(x, y);
		this.speed = speed;
		this.graphicType = graphicType;
	}

	@Override
	public void hitBy(Room room, Actor actor) {
		if (actor instanceof Robot) {
			this.remove();
			room.makeExplosion(x, y);
		}
		if (actor instanceof Player) {
			this.remove();
			room.makeExplosion(x, y);
			((Player) actor).die(room);
		}
	}
	
	public void explode(Room room) {
		this.remove();
		room.makeExplosion(x, y);
	}

	@Override
	public Actor clone() {
		return new Robot(x,y,speed, graphicType);
	}

	@Override
	public boolean canBeEntered(Actor other) {
		if (other instanceof Isolator || other instanceof ElectricFence) return false;
		else return true;
	}
	
	

}
