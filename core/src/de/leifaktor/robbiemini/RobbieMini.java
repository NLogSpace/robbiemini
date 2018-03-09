package de.leifaktor.robbiemini;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.leifaktor.robbiemini.screens.MainMenuScreen;

public class RobbieMini extends Game {
	
	FPSLogger fpsLogger;	
	public SpriteBatch batch;
	
	public static final int WIDTH = 39;
	public static final int HEIGHT = 27;
	public static final int TILESIZE = 16;
	public static final int SCALE = 2;
	
	@Override
	public void create() {
		fpsLogger = new FPSLogger();
		this.setScreen(new MainMenuScreen(this));
		batch = new SpriteBatch();
	}

	@Override
	public void render () {
		super.render();
		fpsLogger.log();
	}
	
	public static int getVirtualWidth() {
		return WIDTH*TILESIZE;
	}
	
	public static int getVirtualHeight() {
		return (HEIGHT+1)*TILESIZE;
	}
}
