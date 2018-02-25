package de.leifaktor.robbiemini.commands;

import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.actor.Actor;

public class AddActorCommand extends Command {
	
	Actor a;
	
	public AddActorCommand(Actor a) {
		this.a = a;
	}

	@Override
	public void execute(Room room) {
		room.addActor(a);
		
	}

}
