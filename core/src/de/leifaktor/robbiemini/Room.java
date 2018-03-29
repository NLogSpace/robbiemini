package de.leifaktor.robbiemini;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.leifaktor.robbiemini.actor.Actor;
import de.leifaktor.robbiemini.actor.Explosion;
import de.leifaktor.robbiemini.actor.IShiftable;
import de.leifaktor.robbiemini.actor.Player;
import de.leifaktor.robbiemini.actor.Robot;
import de.leifaktor.robbiemini.commands.AddActorCommand;
import de.leifaktor.robbiemini.commands.Command;
import de.leifaktor.robbiemini.commands.PlaySoundCommand;
import de.leifaktor.robbiemini.commands.RemoveActorCommand;
import de.leifaktor.robbiemini.items.Item;
import de.leifaktor.robbiemini.screens.GameScreen;
import de.leifaktor.robbiemini.tiles.Tile;

public class Room {
	
	public int width;
	public int height;
	public List<RoomLayer> layers;
	
	Player player;
	public List<Actor> actors;

	public List<Command> commands;
	
	private GameScreen gameScreen;
	
	MagneticField magneticField;
	
	public Room(int width, int height, List<RoomLayer> layers, List<Actor> actors) {
		this.width = width;
		this.height = height;
		this.actors = actors;
		this.layers = layers;
		commands = new ArrayList<Command>();
		magneticField = new MagneticField(this);
	}
	
	public void update() {
		magneticField.update();
		continueMovement();
		updateActors();
		executeCommands();
		checkForRoomTransition();
		if (player.getLives() == 0) getGameScreen().gameOver();
	}
	
	private void continueMovement() {
		for (Actor a: actors) {
			a.continueMovement();
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
	
	private void checkForRoomTransition() {
		if (!isInBounds(player.x, player.y)) {
			if (getGameScreen() != null) {
				int dx = player.x < 0 ? -1 : player.x < width ? 0 : 1;
				int dy = player.y < 0 ? -1 : player.y < height ? 0 : 1;
				
				XYZPos currentRoomPosition = getGameScreen().getCurrentRoomPosition();
				XYZPos newRoomPosition = new XYZPos(currentRoomPosition.x + dx, currentRoomPosition.y + dy, currentRoomPosition.z);
				player.setPosition((player.x+width) % width, (player.y + height)%height, player.z);
				getGameScreen().setRoom(newRoomPosition);
			}
		}
	}
	
	public void makeExplosion(Vec2 position) {
		Explosion explosion = new Explosion(0, 0, 0);
		explosion.setPosition(position);
		commands.add(new AddActorCommand(explosion));
		commands.add(new PlaySoundCommand(SoundPlayer.SOUND_EXPLOSION));
	}
	
	public boolean isInBounds(int x, int y) {
		return (x >= 0 && x < width && y >= 0 && y < height);
	}
	
	public void removeActor(Actor a) {
		actors.remove(a);		
	}

	public void addActor(Actor a) {
		actors.add(a);		
	}
	
	public void putPlayer(Player player, int x, int y, int z) {
		player.spawn(x, y, z);
		this.player = player;
		actors.remove(player);
		actors.add(player);
	}

	public Player getPlayer() {
		return player;
	}
	
	public void setTile(int x, int y, int z, Tile tile) {
		if (layers.get(z) != null) {
			layers.get(z).setTile(x, y, tile);
		}		
	}
	
	public Tile getTile(int x, int y, int z) {
		if (z < 0 || z >= layers.size()) return null;	
		return layers.get(z).getTile(x, y);
	}

	public void onEnter(Actor actor, int x, int y, int z, int direction) {
		getTile(x, y, z).onEnter(this, actor, direction);
		for (Actor other: actors) {
			if (!other.equals(actor) && actor.x == other.x && actor.y == other.y) {
				other.hitBy(this, actor);
			}
		}
	}
	
	public void onLeave(Actor actor, int x, int y, int z, int direction) {
		getTile(x, y, z).onLeave(this, actor, direction);
	}

	public void startShift(Actor actor, int x, int y, int z, int direction) {
		int newx = x + Direction.DIR_X[direction];
		int newy = y + Direction.DIR_Y[direction];
		for (Actor a : actors) {
			if (a.x == newx && a.y == newy && a instanceof IShiftable) {
				((IShiftable)a).startShift(direction, actor.getSpeed(), actor.getRemainingDistance(), this);
			}
		}
	}

	public boolean hasAnyActorsAt(int x, int y, int z) {
		for (Actor a : actors) if (a.x == x && a.y == y && a.z == z) return true;
		return false;
	}

	public List<Actor> getActorsAt(int x, int y, int z) {
		List<Actor> result = new ArrayList<Actor>();
		for (Actor a : actors) {
			if (a.x == x && a.y == y && a.z == z) {
				result.add(a);
			}
		}
		return Collections.unmodifiableList(result);
	}

	public void removePlayer() {
		actors.remove(player);
		player = null;
	}
	
	public void setGameScreen(GameScreen gameScreen) {
		this.gameScreen = gameScreen;
	}

	public boolean hasNeighborRoomAt(int x, int y) {
		if (getGameScreen() == null) return false;
		
		int dx = x < 0 ? -1 : x < width ? 0 : 1;
		int dy = y < 0 ? -1 : y < height ? 0 : 1;
		
		XYZPos currentRoomPosition = getGameScreen().getCurrentRoomPosition();
		XYZPos roomToCheckPosition = new XYZPos(currentRoomPosition.x + dx, currentRoomPosition.y + dy, currentRoomPosition.z);		

		return getGameScreen().getRoomManager().doesRoomExist(roomToCheckPosition);
	}

	public GameScreen getGameScreen() {
		return gameScreen;
	}

	public void useItem(Item item, int x, int y, int z) {
		item.onUse(this, x, y, z);		
	}

	public Vec2 getMagneticGradientAt(int x, int y) {
		return magneticField.getGradientAt(x, y);
	}

	public void showTextbox(String text) {
		gameScreen.showTextbox(text);		
	}
	
	public void teleportTo(XYZPos roomPosition, XYZPos insideRoomPosition) {
		gameScreen.teleportTo(roomPosition, insideRoomPosition);
	}

	public void setGlobalBoolean(String variableName, boolean value) {
		gameScreen.setGlobalBoolean(variableName, value);
	}
	
	public boolean getGlobalBoolean(String variableName) {
		return gameScreen.getGlobalBoolean(variableName);
	}
	
	public int getNumberOfLivingRobots() {
		int result = 0;
		for (Actor actor : actors) {
			if (actor instanceof Robot) result++;
		}
		return result;
	}

	public int getNumberOfLayers() {
		return layers.size();
	}
	
}
