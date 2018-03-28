package de.leifaktor.robbiemini.condition;

import de.leifaktor.robbiemini.Room;

public class GlobalBooleanCondition extends Condition {
	
	String variableName;
	
	public GlobalBooleanCondition(String variableName) {
		this.variableName = variableName;
	}

	@Override
	public boolean evaluate(Room room) {
		return room.getGlobalBoolean(variableName);
	}

}
