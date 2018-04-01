package de.leifaktor.robbiemini.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import de.leifaktor.robbiemini.Episode;
import de.leifaktor.robbiemini.RobbieMini;
import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.XYZPos;

public class ScreenManager {
	
	RobbieMini game;
	
	Screen currentScreen;
	
	public SpriteBatch batch;
	
	GameScreen gameScreen;
	MainMenuScreen mainMenuScreen;
	RoomTransitionScreen roomTransitionScreen;
	EditorScreen editorScreen;
	
	Camera camera;
	Viewport viewport;
	
	public ScreenManager(RobbieMini game) {
		this.game = game;
		camera = new OrthographicCamera(RobbieMini.getVirtualWidth(), RobbieMini.getVirtualHeight());
		camera.position.set(RobbieMini.getVirtualWidth() / 2, RobbieMini.getVirtualHeight() / 2, 1);
		viewport = new FitViewport(RobbieMini.getVirtualWidth(), RobbieMini.getVirtualHeight(), camera);
		batch = new SpriteBatch();
		
		gameScreen = new GameScreen(this, viewport, camera);
		mainMenuScreen = new MainMenuScreen(this, viewport, camera);
		editorScreen = new EditorScreen(this, viewport, camera);
		roomTransitionScreen = new RoomTransitionScreen(this);
	}	
	
	public void setMainMenu() {
		game.setScreen(mainMenuScreen);
	}
	
	public void setGame() {
		game.setScreen(gameScreen);
	}
	
	public void setRoomTransition(Room oldRoom, Room newRoom, int direction) {
		roomTransitionScreen.set(oldRoom, newRoom, direction);
		game.setScreen(roomTransitionScreen);
	}
	
	public void setEditor(Episode episode, XYZPos roomPosition) {
		editorScreen.set(episode, roomPosition);
		game.setScreen(editorScreen);
	}

}
