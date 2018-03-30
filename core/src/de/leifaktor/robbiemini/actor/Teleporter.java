package de.leifaktor.robbiemini.actor;

import com.badlogic.gdx.Gdx;

import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.XYZPos;
import de.leifaktor.robbiemini.condition.Condition;

public class Teleporter extends Actor {
	
	float stateTime;
	boolean active;
	XYZPos destinationRoom;
	XYZPos destinationInsideRoom;
	Condition activeCondition;
	
	public Teleporter() {} // no-arg constructor for JSON

	public Teleporter(int x, int y, int z, Condition activeCondition) {
		super(x, y, z);
		active = true;
		this.activeCondition = activeCondition;
	}
	
	public void setDestination(XYZPos destinationRoom, XYZPos destinationInsideRoom) {
		this.destinationRoom = destinationRoom;
		this.destinationInsideRoom = destinationInsideRoom;
	}	

	@Override
	public void hitBy(Room room, Actor actor) {
		if (active && actor instanceof Player) {
			if (destinationRoom != null && destinationInsideRoom != null) {
				room.teleportTo(destinationRoom, destinationInsideRoom);
			}
		}
	}

	@Override
	public boolean canBeEntered(Actor other) {		
		return other instanceof Player;
	}	

	@Override
	public void update(Room room) {
		super.update(room);
		active = activeCondition.evaluate(room);
		stateTime += Gdx.graphics.getDeltaTime();
	}

	@Override
	public Actor clone() {
		return new Teleporter(x, y, z, activeCondition);
	}	
	
	public float getStateTime() {
		return stateTime;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public XYZPos getDestinationRoom() {
		return destinationRoom;
	}
	
	public XYZPos getDestinationInsideRoom() {
		return destinationInsideRoom;
	}

}
