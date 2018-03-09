package de.leifaktor.robbiemini.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import de.leifaktor.robbiemini.RobbieMini;
import de.leifaktor.robbiemini.render.Tileset;

public class MainMenuScreen implements Screen {
	
	RobbieMini game;
	
	Texture title;
	TextureRegion background;
	
	Camera camera;
	Viewport viewport;
	
	public MainMenuScreen(RobbieMini game) {
		this.game = game;
		title = new Texture(Gdx.files.internal("title.png"));
		background = new Tileset("tileset16.png", 16).getTile(1, 0);
		camera = new OrthographicCamera(RobbieMini.getVirtualWidth(), RobbieMini.getVirtualHeight());
		camera.position.set(RobbieMini.getVirtualWidth() / 2, RobbieMini.getVirtualHeight() / 2, 1);
		viewport = new FitViewport(RobbieMini.getVirtualWidth(), RobbieMini.getVirtualHeight(), camera);
	}

	@Override
	public void show() {
		camera.update();
	}

	@Override
	public void render(float delta) {
		if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
			game.setScreen(new GameScreen(game));
		}
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		for (int x = 0; x < RobbieMini.getVirtualWidth() / RobbieMini.TILESIZE; x++) {
			for (int y = 0; y < RobbieMini.getVirtualHeight() / RobbieMini.TILESIZE; y++) {
				game.batch.draw(background, x*RobbieMini.TILESIZE, y*RobbieMini.TILESIZE, RobbieMini.TILESIZE, RobbieMini.TILESIZE);
			}				
		}		
		game.batch.draw(title, RobbieMini.getVirtualWidth() / 4, (RobbieMini.getVirtualHeight()) / 2, RobbieMini.getVirtualWidth() / 2, 90);
		game.batch.end();
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
