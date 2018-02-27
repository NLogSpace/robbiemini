package de.leifaktor.robbiemini.movement;

import de.leifaktor.robbiemini.CollisionDetector;
import de.leifaktor.robbiemini.Direction;
import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.Util;
import de.leifaktor.robbiemini.actor.Actor;
import de.leifaktor.robbiemini.actor.Player;

public class FollowPlayerMovement implements IMovingBehaviour {
	
	int lastDir = 0;

	@Override
	public int getMoveDirection(Actor actor, Room room) {	
		int[] possibleDirs = CollisionDetector.getPossibleDirections(actor, room);
		if (possibleDirs.length == 0) return -1;
		if (possibleDirs.length == 1) return possibleDirs[0];
		int bestDir = getDirTowardsPlayer(actor, room);
		
		if (bestDir >= 0) {
			for (int i = 0; i < possibleDirs.length; i++) {
				if (possibleDirs[i] == bestDir) return bestDir;
			}
		}
		
		return possibleDirs[Util.random.nextInt(possibleDirs.length)];
	}
	
	private int getDirTowardsPlayer(Actor actor, Room room) {
		Player player = room.getPlayer();
		if (player == null) return -1;
		
		int dx = player.x - actor.x;
		int dy = player.y - actor.y;
		
		double angle = Math.atan2(dy, dx);
		
		return Direction.roundAngleToDirection(angle);
	}
	


}
