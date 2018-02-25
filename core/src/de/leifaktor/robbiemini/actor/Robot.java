package de.leifaktor.robbiemini.actor;

import de.leifaktor.robbiemini.Room;

public class Robot extends MoveableActor{
	
	public int graphicType;

	public Robot(int x, int y, float speed, int graphicType) {
		super(x, y);
		this.speed = speed;
		this.graphicType = graphicType;
	}

	@Override
	public void hitBy(Room room, MoveableActor actor) {
		if (actor instanceof Robot) {
			this.remove();
			room.makeExplosion(x, y);
		}
	}
	
	

}
