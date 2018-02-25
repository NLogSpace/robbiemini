package de.leifaktor.robbiemini.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.Screen;

import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.RoomCreator;
import de.leifaktor.robbiemini.render.RoomRenderer;

public class GameScreen implements Screen {
	
	Game game;
	
	Viewport viewport;
	Camera camera;
	
	SpriteBatch batch;
	Room room;
	RoomRenderer renderer;
	
	final int WIDTH = 39;
	final int HEIGHT = 29;
	
	public GameScreen(Game game) {
		this.game = game;
		camera = new OrthographicCamera();
		camera.position.set(WIDTH*8, HEIGHT*8, 1);
		viewport = new FitViewport(WIDTH*16, HEIGHT*16, camera);
		
		batch = new SpriteBatch();
		room = RoomCreator.createMazeRoom(WIDTH, HEIGHT);
		renderer = new RoomRenderer();
	}

	@Override
	public void show() {
				
	}

	@Override
	public void render(float delta) {
		room.update();
		if (room.getPlayer().getLives() == 0) {
			game.setScreen(new MainMenuScreen(game));
		}
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		renderer.render(batch, room);
		batch.end();		
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

}
