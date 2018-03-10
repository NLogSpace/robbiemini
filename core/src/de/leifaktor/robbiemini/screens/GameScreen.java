package de.leifaktor.robbiemini.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import de.leifaktor.robbiemini.InputManager;
import de.leifaktor.robbiemini.RobbieMini;
import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.RoomCreator;
import de.leifaktor.robbiemini.RoomManager;
import de.leifaktor.robbiemini.XYZPos;
import de.leifaktor.robbiemini.actor.Player;
import de.leifaktor.robbiemini.render.InventoryRenderer;
import de.leifaktor.robbiemini.render.RoomRenderer;
import de.leifaktor.robbiemini.render.StatusBarRenderer;

public class GameScreen implements Screen {
	
	RobbieMini game;
	
	Viewport viewport;
	Camera camera;
	
	RoomManager roomManager;
	Room currentRoom;
	XYZPos currentRoomPosition;
	XYZPos newRoomPosition;
	Player player;
	boolean startRoomTransitionAfterThisFrame;
	
	RoomRenderer renderer;
	StatusBarRenderer barRenderer;
	InventoryRenderer inventoryRenderer;
	
	boolean inventoryOpen;
	
	public GameScreen(RobbieMini game) {
		this.game = game;
		camera = new OrthographicCamera();
		camera.position.set(RobbieMini.getVirtualWidth()/2, RobbieMini.getVirtualHeight()/2, 1);
		viewport = new FitViewport(RobbieMini.getVirtualWidth(), RobbieMini.getVirtualHeight(), camera);
		camera.update();
		
		setUpSomeTestRooms();
		
		renderer = new RoomRenderer();
		renderer.setOffset(0, 0);
		barRenderer = new StatusBarRenderer();
		barRenderer.setOffset(0, RobbieMini.getVirtualHeight()-RobbieMini.TILESIZE);
		inventoryRenderer = new InventoryRenderer();
		inventoryRenderer.setOffset(0, RobbieMini.getVirtualHeight()-RobbieMini.TILESIZE);
		
		InputManager.initKeyMap();
	}
	
	@Override
	public void render(float delta) {
		InputManager.update();
		processInventory();
		currentRoom.update();
		renderTheGame();		
		if (startRoomTransitionAfterThisFrame) {
			startRoomTransition();
			startRoomTransitionAfterThisFrame = false;
		}
	}
	
	private void processInventory() {
		if (inventoryOpen) {
			player.inventory.update();
			if (InputManager.justPressed[InputManager.ENTER]) {
				if (player.inventory.getSelectedItem() != null) {
					currentRoom.useItem(player.inventory.getSelectedItem(), player.x, player.y);
					player.inventory.removeSelectedItem();
					player.inventory.decreasePointer();
				}
				inventoryOpen = false;
			} else if (InputManager.justPressed[InputManager.ESCAPE]) {
				inventoryOpen = false;
			}
		} else {
			if (InputManager.justPressed[InputManager.ENTER]) inventoryOpen = true;
		}
	}
	
	private void renderTheGame() {
		game.batch.setProjectionMatrix(camera.combined);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		renderer.render(game.batch, currentRoom);
		if (inventoryOpen) {
			inventoryRenderer.render(game.batch, currentRoom, player.inventory);
		} else {
			barRenderer.render(game.batch, currentRoom);
		}
		game.batch.end();
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
		game.setScreen(new RoomTransitionScreen(game, this, currentRoom, newRoom, direction));
		
		currentRoom.removePlayer();
		currentRoomPosition = newRoomPosition;
		currentRoom = newRoom;
		currentRoom.putPlayer(player, player.x, player.y);
		currentRoom.setGameScreen(this);
	}

	public void setRoom(XYZPos newRoomPosition) {
		this.newRoomPosition = newRoomPosition;
		startRoomTransitionAfterThisFrame = true;
	}
	
	@Override
	public void show() {
				
	}
	
	@Override
	public void resize(int width, int height) {
		viewport.update(width,  height);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {

	}

	public RoomManager getRoomManager() {
		return roomManager;		
	}
	
	public XYZPos getCurrentRoomPosition() {
		return currentRoomPosition;
	}
	
	public void gameOver() {
		game.setScreen(new MainMenuScreen(game));
	}
	
	private void setUpSomeTestRooms() {
		roomManager = new RoomManager();
		Room room111 = RoomCreator.createShiftRoom(RobbieMini.WIDTH, RobbieMini.HEIGHT);
		Room room112 = RoomCreator.createMazeRoom(RobbieMini.WIDTH, RobbieMini.HEIGHT);
		Room room121 = RoomCreator.createEmptyRoom(RobbieMini.WIDTH, RobbieMini.HEIGHT);
		Room room122 = RoomCreator.createWallRoom(RobbieMini.WIDTH, RobbieMini.HEIGHT);
		roomManager.setRoom(1, 1, 1, room111);
		roomManager.setRoom(1, 1, 2, room112);
		roomManager.setRoom(1, 2, 1, room121);
		roomManager.setRoom(1, 2, 2, room122);
		currentRoomPosition = new XYZPos(1,1,1);
		currentRoom = roomManager.getRoom(currentRoomPosition);
		player = new Player(3,3);
		currentRoom.putPlayer(player, player.x, player.y);
		currentRoom.setGameScreen(this);
	}
	
	public boolean isInventoryOpen() {
		return inventoryOpen;
	}

}
