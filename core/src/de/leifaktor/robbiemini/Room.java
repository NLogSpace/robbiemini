package de.leifaktor.robbiemini;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import de.leifaktor.robbiemini.actor.Actor;
import de.leifaktor.robbiemini.actor.Actor.MoveState;
import de.leifaktor.robbiemini.actor.DissolvingWall;
import de.leifaktor.robbiemini.actor.Explosion;
import de.leifaktor.robbiemini.actor.Player;
import de.leifaktor.robbiemini.commands.AddActorCommand;
import de.leifaktor.robbiemini.commands.ChangeTileCommand;
import de.leifaktor.robbiemini.commands.Command;
import de.leifaktor.robbiemini.commands.PlaySoundCommand;
import de.leifaktor.robbiemini.commands.RemoveActorCommand;
import de.leifaktor.robbiemini.movement.FixedMovement;
import de.leifaktor.robbiemini.tiles.EmptyTile;
import de.leifaktor.robbiemini.tiles.Tile;
import de.leifaktor.robbiemini.tiles.Wall;

public class Room {
	
	public int width;
	public int height;
	public Tile[] tiles;
	Player player;
	public List<Actor> actors;

	public List<Command> commands;
	
	public Room(int width, int height, Tile[] tiles, List<Actor> actors) {
		this.width = width;
		this.height = height;
		this.tiles = tiles;
		this.actors = actors;
		commands = new ArrayList<Command>();
	}
	
	public void update() {
		continueMovement();
		checkAcidCommand();
		updateActors();
		executeCommands();
	}
	
	private void continueMovement() {
		for (Actor a: actors) {
			a.continueMovement();
		}
	}	

	private void checkAcidCommand() {		
		boolean useAcid = false;
		if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {			
			if (player.getMoveState() == MoveState.IDLE) {
				if (player.inventory.getAcids() > 0) {
					int px = player.x;
					int py = player.y;					
					for (int i = px - 1; i <= px+1; i++) {
						for (int j = py - 1; j <= py+1; j++) {
							if (isInBounds(i,j)) {							
								if (tiles[width*j+i] instanceof Wall) {
									useAcid = true;
									commands.add(new ChangeTileCommand(i,j,new EmptyTile()));
									commands.add(new AddActorCommand(new DissolvingWall(i,j)));
								}
							}
						}
					}
				}
			}
		}
		if (useAcid) {
			player.inventory.removeAcid();
			commands.add(new PlaySoundCommand(SoundPlayer.SOUND_ACID));
		}
	}
	
	private void updateActors() {
		for (Actor a : actors) {
			a.update(this);
			if (a.shouldBeRemoved()) commands.add(new RemoveActorCommand(a));
		}
	}
	
	private void executeCommands() {
		for (Command c : commands) c.execute(this);
		commands.clear();
	}
	
	public void makeExplosion(int x, int y) {
		Explosion explosion = new Explosion(x, y);
		commands.add(new AddActorCommand(explosion));
		commands.add(new PlaySoundCommand(SoundPlayer.SOUND_EXPLOSION));
	}
	
	public boolean isInBounds(int x, int y) {
		return (x >= 0 && x < width && y >= 0 && y < height);
	}
	
	public void setTile(int x, int y, Tile tile) {
		if (isInBounds(x,y)) tiles[y*width+x] = tile;
	}

	public void removeActor(Actor a) {
		actors.remove(a);		
	}

	public void addActor(Actor a) {
		actors.add(a);		
	}
	
	public void putPlayer(Player player, int x, int y) {
		player.setPosition(x, y);
		this.player = player;
		actors.remove(player);
		actors.add(player);
	}

	public Player getPlayer() {
		return player;
	}

	public Tile getTile(int x, int y) {
		if (!isInBounds(x, y)) return null;
		return tiles[width*y + x];
	}

	public void onEnter(Actor actor, int x, int y, int direction) {
		getTile(x, y).onEnter(this, actor, direction);
		for (Actor other: actors) {
			if (!other.equals(actor) && actor.x == other.x && actor.y == other.y) {
				other.hitBy(this, actor);
			}
		}
	}
	
	public void onLeave(Actor actor, int x, int y, int direction) {
		getTile(x, y).onLeave(this, actor, direction);
	}

	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

	public void startShift(Actor actor, int x, int y, int direction) {
		int newx = x + Direction.DIR_X[direction];
		int newy = y + Direction.DIR_Y[direction];
		for (Actor a : actors) {
			if (a.x == newx && a.y == newy) {
				a.setSpeed(actor.getSpeed());
				a.setRemainingDistance(actor.getRemainingDistance());
				a.setMovingBehaviour(new FixedMovement(direction));
				a.update(this);
			}
		}
	}

	public boolean hasAnyActorsAt(int x, int y) {
		for (Actor a : actors) if (a.x == x && a.y == y) return true;
		return false;
	}
	
}
