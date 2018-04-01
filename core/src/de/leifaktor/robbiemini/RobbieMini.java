package de.leifaktor.robbiemini;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;

import de.leifaktor.robbiemini.render.Graphics;
import de.leifaktor.robbiemini.screens.ScreenManager;

public class RobbieMini extends Game {
	
	FPSLogger fpsLogger;
	ScreenManager sm;
	
	public static final int WIDTH = 35;
	public static final int HEIGHT = 23;
	public static final int TILESIZE = 16;
	public static final int SCALE = 2;
	
	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Graphics.loadGraphics();
		fpsLogger = new FPSLogger();
		sm = new ScreenManager(this);
		sm.setMainMenu();
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
