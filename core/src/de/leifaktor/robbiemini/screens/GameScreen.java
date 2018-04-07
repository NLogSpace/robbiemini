package de.leifaktor.robbiemini.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import de.leifaktor.robbiemini.Episode;
import de.leifaktor.robbiemini.GlobalVars;
import de.leifaktor.robbiemini.IO;
import de.leifaktor.robbiemini.InputManager;
import de.leifaktor.robbiemini.RobbieMini;
import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.RoomManager;
import de.leifaktor.robbiemini.TestEpisode;
import de.leifaktor.robbiemini.XYZPos;
import de.leifaktor.robbiemini.actor.Player;
import de.leifaktor.robbiemini.render.InventoryRenderer;
import de.leifaktor.robbiemini.render.RoomRenderer;
import de.leifaktor.robbiemini.render.StatusBarRenderer;
import de.leifaktor.robbiemini.render.TextboxRenderer;

public class GameScreen extends ScreenAdapter {
	
	ScreenManager sm;
	
	Viewport viewport;
	Camera camera;
	
	Episode episode;
	
	RoomManager roomManager;
	Room currentRoom;
	XYZPos currentRoomPosition;
	XYZPos newRoomPosition;
	Player player;
	boolean startRoomTransitionAfterThisFrame;
	
	boolean startTeleportAfterThisFrame;
	XYZPos playerTeleportPosition;
	
	RoomRenderer roomRenderer;
	StatusBarRenderer barRenderer;
	InventoryRenderer inventoryRenderer;
	TextboxRenderer textboxRenderer;
	
	int textboxWidth = 22;
	int textboxHeight = 1;

	String textboxText;
	boolean showTextboxFromNextFrame;
	boolean textboxCentered;
	boolean textboxLargeFont;
	
	GlobalVars globalVars;
	
	State state;
	
	enum State {
		GAME,
		INVENTORY,
		TEXTBOX
	}
	
	public GameScreen(ScreenManager sm, Viewport viewport, Camera camera) {
		this.sm = sm;
		this.viewport = viewport;
		this.camera = camera;
		camera = new OrthographicCamera();
		camera.position.set(RobbieMini.getVirtualWidth()/2, RobbieMini.getVirtualHeight()/2, 1);
		viewport = new FitViewport(RobbieMini.getVirtualWidth(), RobbieMini.getVirtualHeight(), camera);
		camera.update();
		
		roomRenderer = new RoomRenderer();
		roomRenderer.setOffset(0, 0);
		roomRenderer.highlightLayer(-1);
		barRenderer = new StatusBarRenderer();
		barRenderer.setOffset(0, RobbieMini.getVirtualHeight()-RobbieMini.TILESIZE);
		inventoryRenderer = new InventoryRenderer();
		inventoryRenderer.setOffset(0, RobbieMini.getVirtualHeight()-RobbieMini.TILESIZE);
		textboxRenderer = new TextboxRenderer();
		textboxRenderer.setSize(textboxWidth, textboxHeight);

		loadEpisode(TestEpisode.createTestEpisode());
		
		InputManager.initKeyMap();
	}
	
	@Override
	public void render(float delta) {
		InputManager.update();
		if (Gdx.input.isKeyJustPressed(Keys.S)) save();
		if (Gdx.input.isKeyJustPressed(Keys.L)) load();
		if (Gdx.input.isKeyJustPressed(Keys.E)) {
			sm.setEditor(episode, currentRoomPosition);			
		}
		if (showTextboxFromNextFrame) {
			state = State.TEXTBOX;
			showTextboxFromNextFrame = false;
		}
		if (state == State.TEXTBOX) {
			if (InputManager.justPressed[InputManager.ENTER]) state = State.GAME;
		} else {
			processInventory();
			currentRoom.update();
		}
		renderTheGame();		
		if (startRoomTransitionAfterThisFrame) {
			startRoomTransition();
			startRoomTransitionAfterThisFrame = false;
		}
		if (startTeleportAfterThisFrame) {
			startTeleport();
			startTeleportAfterThisFrame = false;
		}
	}
	
	public void loadEpisode(Episode epi) {
		episode = epi;
		roomManager = epi.roomManager;
		globalVars = epi.globalVars;
		currentRoomPosition = epi.startingRoom;
		player = epi.player;
		setRoom(currentRoomPosition, new XYZPos(player.x, player.y, player.z));
	}

	public void gameOver() {
		sm.setMainMenu();
	}
	
	public void showTextbox(String text, boolean largeFont, boolean centered) {
		this.textboxText = text;
		this.textboxLargeFont = largeFont;
		this.textboxCentered = centered;
		textboxHeight = (int) textboxRenderer.getHeight(text) / RobbieMini.TILESIZE + 3;
		textboxRenderer.setOffset((RobbieMini.getVirtualWidth()-textboxWidth*RobbieMini.TILESIZE)/2, (RobbieMini.getVirtualHeight()-textboxHeight*RobbieMini.TILESIZE)/2);
		textboxRenderer.setSize(textboxWidth, textboxHeight);
		showTextboxFromNextFrame = true;
	}
	
	public void transitionToRoom(XYZPos newRoomPosition) {
		if (roomManager.getRoom(newRoomPosition) == null) return;
		this.newRoomPosition = newRoomPosition;
		startRoomTransitionAfterThisFrame = true;
	}
	
	public void teleportTo(XYZPos roomPosition, XYZPos playerPosition) {
		if (roomManager.getRoom(roomPosition) == null) return;
		this.newRoomPosition = roomPosition;
		this.playerTeleportPosition = playerPosition;
		startTeleportAfterThisFrame = true;
	}
	
	public void teleportTo(XYZPos roomPosition) {
		teleportTo(roomPosition, new XYZPos(player.x, player.y, player.z));
	}
	
	public boolean getGlobalBoolean(String key) {
		return globalVars.getBoolean(key);
	}
	
	public void setGlobalBoolean(String key, boolean value) {
		globalVars.setBoolean(key, value);
	}
	
	public RoomManager getRoomManager() {
		return roomManager;		
	}
	
	public XYZPos getCurrentRoomPosition() {
		return currentRoomPosition;
	}
	
	public boolean isInventoryOpen() {
		return state == State.INVENTORY;
	}
	
	@Override
	public void show() {
				
	}
	
	@Override
	public void resize(int width, int height) {
		viewport.update(width,  height);
	}
	
	private void save() {
		episode.startingRoom = currentRoomPosition;
		IO.save(episode, "episode.rob");
	}
	
	private void load() {
		loadEpisode(IO.load("episode.rob"));
	}
	
	private void startTeleport() {
		setRoom(newRoomPosition, playerTeleportPosition);
	}
	
	private void setRoom(XYZPos roomPos, XYZPos playerPos) {		
		if (currentRoom != null) currentRoom.removePlayer();
		currentRoomPosition = roomPos;
		currentRoom = roomManager.getRoom(roomPos);
		currentRoom.putPlayer(player, playerPos.x, playerPos.y, playerPos.z);
		player.stopMoving();
		currentRoom.setGameScreen(this);
		if (!getGlobalBoolean(""+currentRoomPosition)) {
			setGlobalBoolean(""+currentRoomPosition, true);
			if (currentRoom.name != null) showTextbox(currentRoom.name, true, true);
		}
		roomRenderer.setRoom(currentRoom);
		roomRenderer.setRenderLayer(currentRoom.getNumberOfLayers());
	}
	
	private void processInventory() {
		if (state == State.INVENTORY) {
			player.inventory.update();
			if (InputManager.justPressed[InputManager.ENTER]) {
				if (player.inventory.getSelectedItem() != null) {
					currentRoom.useItem(player.inventory.getSelectedItem(), player.x, player.y, player.z);
					player.inventory.removeSelectedItem();
					player.inventory.decreasePointer();
				}
				state = State.GAME;
			} else if (InputManager.justPressed[InputManager.ESCAPE]) {
				state = State.GAME;
			}
		} else {
			if (InputManager.justPressed[InputManager.ENTER]) state = State.INVENTORY;
		}
	}
	
	private void renderTheGame() {
		sm.batch.setProjectionMatrix(camera.combined);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		sm.batch.begin();
		roomRenderer.render(sm.batch);
		if (state == State.INVENTORY) {
			inventoryRenderer.render(sm.batch, currentRoom, player.inventory);
		} else {
			barRenderer.render(sm.batch, currentRoom);
		}
		if (state == State.TEXTBOX) {
			textboxRenderer.render(sm.batch, textboxText, textboxLargeFont, textboxCentered);
		}		
		sm.batch.end();
	}
	
	private void startRoomTransition() {
		int dx = newRoomPosition.x - currentRoomPosition.x;
		int dy = newRoomPosition.y - currentRoomPosition.y;
		
		int direction = -1;
		if (dx == 0 && dy == 1) direction = 0;
		else if (dx == 1 && dy == 0) direction = 1;
		else if (dx == 0 && dy == -1) direction = 2;
		else if (dx == -1 && dy == 0) direction = 3;
		
		Room newRoom = roomManager.getRoom(newRoomPosition);
		sm.setRoomTransition(currentRoom, newRoom, direction);
		
		setRoom(newRoomPosition, new XYZPos(player.x, player.y, player.z));
	}

}
