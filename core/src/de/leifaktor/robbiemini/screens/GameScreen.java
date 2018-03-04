package de.leifaktor.robbiemini.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.Screen;

import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.RoomCreator;
import de.leifaktor.robbiemini.RoomManager;
import de.leifaktor.robbiemini.XYZPos;
import de.leifaktor.robbiemini.actor.Player;
import de.leifaktor.robbiemini.movement.KeyboardMovement;
import de.leifaktor.robbiemini.render.RoomRenderer;

public class GameScreen implements Screen {
	
	Game game;
	
	Viewport viewport;
	Camera camera;
	SpriteBatch batch;
	
	RoomManager roomManager;
	Room currentRoom;
	XYZPos currentRoomPosition;
	RoomRenderer renderer;
	
	public static final int WIDTH = 33;
	public static final int HEIGHT = 23;
	
	public GameScreen(Game game) {
		this.game = game;
		camera = new OrthographicCamera();
		camera.position.set(WIDTH*8, HEIGHT*8, 1);
		viewport = new FitViewport(WIDTH*16, HEIGHT*16, camera);
		
		setUpSomeTestRooms();
		
		batch = new SpriteBatch();
		renderer = new RoomRenderer();
	}
	
	private void setUpSomeTestRooms() {
		roomManager = new RoomManager();
		Room room111 = RoomCreator.createShiftRoom(WIDTH, HEIGHT);
		Room room112 = RoomCreator.createMazeRoom(WIDTH, HEIGHT);
		Room room121 = RoomCreator.createEmptyRoom(WIDTH, HEIGHT);
		Room room122 = RoomCreator.createWallRoom(WIDTH, HEIGHT);
		roomManager.setRoom(1, 1, 1, room111);
		roomManager.setRoom(1, 1, 2, room112);
		roomManager.setRoom(1, 2, 1, room121);
		roomManager.setRoom(1, 2, 2, room122);
		currentRoomPosition = new XYZPos(1,1,1);
		currentRoom = roomManager.getRoom(currentRoomPosition);
		Player player = new Player(3,3);
		setRoom(currentRoomPosition, player);
	}

	@Override
	public void render(float delta) {
		currentRoom.update();
		if (currentRoom.getPlayer().getLives() == 0) {
			game.setScreen(new MainMenuScreen(game));
		}
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		renderer.render(batch, currentRoom);
		batch.end();		
	}

	public void setRoom(XYZPos newRoomPosition, Player player) {
		currentRoom.removePlayer();
		currentRoomPosition = newRoomPosition;
		currentRoom = roomManager.getRoom(currentRoomPosition);
		currentRoom.putPlayer(player, player.x, player.y);
		currentRoom.setGameScreen(this);
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
		batch.dispose();
	}

	public RoomManager getRoomManager() {
		return roomManager;		
	}
	
	public XYZPos getCurrentRoomPosition() {
		return currentRoomPosition;
	}

}
