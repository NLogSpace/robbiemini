package de.leifaktor.robbiemini.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import de.leifaktor.robbiemini.RobbieMini;
import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.RoomCreator;
import de.leifaktor.robbiemini.RoomManager;
import de.leifaktor.robbiemini.XYZPos;
import de.leifaktor.robbiemini.actor.Player;
import de.leifaktor.robbiemini.render.RoomRenderer;

public class GameScreen implements Screen {
	
	RobbieMini game;
	
	Viewport viewport;
	Camera camera;
	
	RoomManager roomManager;
	Room currentRoom;
	XYZPos currentRoomPosition;
	RoomRenderer renderer;
	
	XYZPos newRoomPosition;
	Player player;
	boolean startRoomTransitionAfterThisFrame;
	
	public static final int WIDTH = 33;
	public static final int HEIGHT = 23;
	
	public GameScreen(RobbieMini game) {
		this.game = game;
		camera = new OrthographicCamera();
		camera.position.set(WIDTH*8, HEIGHT*8, 1);
		viewport = new FitViewport(WIDTH*16, HEIGHT*16, camera);
		
		setUpSomeTestRooms();
		
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
		player = new Player(3,3);
		currentRoom.putPlayer(player, player.x, player.y);
		currentRoom.setGameScreen(this);
	}

	@Override
	public void render(float delta) {
		currentRoom.update();
		if (currentRoom.getPlayer().getLives() == 0) {
			game.setScreen(new MainMenuScreen(game));
		}
		
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		renderer.setOffset(0, 0);
		renderer.render(game.batch, currentRoom);
		game.batch.end();
		
		if (startRoomTransitionAfterThisFrame) {
			startRoomTransition();
			startRoomTransitionAfterThisFrame = false;
		}
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

	public void setRoom(XYZPos newRoomPosition, Player player) {
		this.newRoomPosition = newRoomPosition;
		this.player = player;
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

}
