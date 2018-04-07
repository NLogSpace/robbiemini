package de.leifaktor.robbiemini.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;

import de.leifaktor.robbiemini.RobbieMini;
import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.RoomCreator;
import de.leifaktor.robbiemini.render.Graphics;
import de.leifaktor.robbiemini.render.RoomRenderer;

public class MainMenuScreen implements Screen {
	
	ScreenManager sm;
	
	Texture title;
	
	Camera camera;
	Viewport viewport;
	
	RoomRenderer roomRenderer;
	
	Room backgroundRoom;
	
	int selected = 0;
	
	public MainMenuScreen(ScreenManager sm, Viewport viewport, Camera camera) {
		this.sm = sm;
		this.viewport = viewport;
		this.camera = camera;

		title = new Texture(Gdx.files.internal("title.png"));		
		roomRenderer = new RoomRenderer();
		backgroundRoom = RoomCreator.createTitleMenuRoom(RobbieMini.WIDTH, RobbieMini.HEIGHT+1);
		roomRenderer.setRoom(backgroundRoom);
	}

	@Override
	public void show() {
		camera.update();
	}

	@Override
	public void render(float delta) {
		if (Gdx.input.isKeyJustPressed(Keys.DOWN)) {
			if (selected < 2) selected++;
		}
		if (Gdx.input.isKeyJustPressed(Keys.UP)) {
			if (selected > 0) selected--;
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
			if (selected == 0) sm.setGame();
			else if (selected == 1) sm.setEditor();
			else if (selected == 2) Gdx.app.exit();
		}
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		sm.batch.setProjectionMatrix(camera.combined);
		sm.batch.begin();
		roomRenderer.render(sm.batch);
		sm.batch.draw(title, (RobbieMini.getVirtualWidth() - title.getWidth()) / 2, 230);
		Graphics.largeFont.draw(sm.batch, "Das Spiel von", RobbieMini.getVirtualWidth() / 2-60, 270);
		Graphics.largeFont.draw(sm.batch, "Start", RobbieMini.getVirtualWidth() / 2-30, 170);
		Graphics.largeFont.draw(sm.batch, "Editor", RobbieMini.getVirtualWidth() / 2-30, 150);
		Graphics.largeFont.draw(sm.batch, "Ende", RobbieMini.getVirtualWidth() / 2-30, 130);
		sm.batch.draw(Graphics.textures.get("arrow_1"), RobbieMini.getVirtualWidth() / 2-50, 160 - 20*selected);
		sm.batch.draw(Graphics.textures.get("arrow_3"), RobbieMini.getVirtualWidth() / 2+50, 160 - 20*selected);
		sm.batch.end();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);		
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
		// TODO Auto-generated method stub
		
	}

}
