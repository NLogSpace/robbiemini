package de.leifaktor.robbiemini.commands;

import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.actor.Actor;

public class RemoveActorCommand extends Command {
	
	Actor a;

	public RemoveActorCommand(Actor a) {
		this.a = a;
	}

	@Override
	public void execute(Room room) {
		room.removeActor(a);
	}
	
}
