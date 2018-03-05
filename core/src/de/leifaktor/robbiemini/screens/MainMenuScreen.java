package de.leifaktor.robbiemini.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import de.leifaktor.robbiemini.RobbieMini;
import de.leifaktor.robbiemini.render.Tileset;

public class MainMenuScreen implements Screen {
	
	RobbieMini game;
	Texture title;
	TextureRegion background;
	
	SpriteBatch batch;
	
	public MainMenuScreen(RobbieMini game) {
		this.game = game;
		title = new Texture(Gdx.files.internal("title.png"));
		batch = new SpriteBatch();
		Tileset tileset = new Tileset("tileset16.png", 16);
		background = tileset.getTile(1, 0);
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
			game.setScreen(new GameScreen(game));
		}
		
		Gdx.gl.glClearColor(0.8f, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		for (int x = 0; x < Gdx.graphics.getWidth() / 16; x++) {
			for (int y = 0; y < Gdx.graphics.getHeight() / 16; y++) {
				batch.draw(background, x*32, y*32, 32, 32);
			}				
		}		
		batch.draw(title, (Gdx.graphics.getWidth() - title.getWidth()) / 2, (Gdx.graphics.getHeight() - title.getHeight()) / 2 + 150);
		batch.end();
		

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
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
