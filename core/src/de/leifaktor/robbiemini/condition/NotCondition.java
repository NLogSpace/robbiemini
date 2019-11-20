package de.leifaktor.robbiemini.condition;

import de.leifaktor.robbiemini.Room;

public class NotCondition extends Condition {
	
	Condition condition;
	
	public NotCondition() {} // no-arg constructor for JSON
	
	public NotCondition(Condition c) {
		this.condition = c;
	}

	@Override
	public boolean evaluate(Room room) {
		return !condition.evaluate(room);
	}

}
