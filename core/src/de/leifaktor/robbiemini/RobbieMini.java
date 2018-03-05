package de.leifaktor.robbiemini;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.leifaktor.robbiemini.screens.MainMenuScreen;

public class RobbieMini extends Game {
	
	Screen screen;	
	FPSLogger fpsLogger;
	
	public SpriteBatch batch;
	
	@Override
	public void create () {
		fpsLogger = new FPSLogger();
		this.setScreen(new MainMenuScreen(this));
		batch = new SpriteBatch();
	}

	@Override
	public void render () {		
		super.render();
		fpsLogger.log();
	}
}
