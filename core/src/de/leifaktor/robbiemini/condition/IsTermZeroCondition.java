package de.leifaktor.robbiemini.condition;

import de.leifaktor.robbiemini.Room;

public class IsTermZeroCondition extends Condition {
	
	Term term;
	
	public IsTermZeroCondition() {} // no-arg constructor for JSON
	
	public IsTermZeroCondition(Term term) {
		this.term = term;
	}

	@Override
	public boolean evaluate(Room room) {
		return term.evaluate(room) == 0;
	}

}
