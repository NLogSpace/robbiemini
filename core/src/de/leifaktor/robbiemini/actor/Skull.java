package de.leifaktor.robbiemini.actor;

import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.commands.AddActorCommand;
import de.leifaktor.robbiemini.tiles.EmptyTile;

public class Skull extends Actor {

	public Skull(int x, int y, int z) {
		super(x, y, z);
	}

	@Override
	public Actor clone() {
		return new Skull(x, y, z);
	}

	@Override
	public boolean canBeEntered(Actor other) {
		return other instanceof Player || other instanceof FlyingBullet;
	}

	@Override
	public void hitBy(Room room, Actor actor) {
		if (actor instanceof Player) {
			for (int i = x-1; i <= x+1; i++) {
				for (int j = y-1; j <= y+1; j++) {
					if ((i != x || j != y) 
							&& room.isInBounds(i, j) 
							&& room.getTile(i, j, z) instanceof EmptyTile 
							&& !room.hasAnyActorsAt(i, j, z)) {
						ElectricFence fence = new ElectricFence(i, j, z);
						room.commands.add(new AddActorCommand(fence));
					}
				}
			}
			this.remove();
		} else if (actor instanceof FlyingBullet) {
			room.makeExplosion(getPosition());
			actor.remove();
			this.remove();			
		}
	}
	

}
