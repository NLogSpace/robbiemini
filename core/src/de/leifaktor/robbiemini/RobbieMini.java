package de.leifaktor.robbiemini;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;

import de.leifaktor.robbiemini.screens.GameScreen;

public class RobbieMini extends Game {
	
	Screen screen;	
	FPSLogger fpsLogger;
	
	@Override
	public void create () {
		fpsLogger = new FPSLogger();
		this.setScreen(new GameScreen(this));
	}

	@Override
	public void render () {		
		super.render();
		fpsLogger.log();
	}
}
