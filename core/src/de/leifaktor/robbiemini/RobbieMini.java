package de.leifaktor.robbiemini;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;

import de.leifaktor.robbiemini.screens.GameScreen;
import de.leifaktor.robbiemini.screens.MainMenuScreen;

public class RobbieMini extends Game {
	
	Screen screen;	
	FPSLogger fpsLogger;
	
	@Override
	public void create () {
		fpsLogger = new FPSLogger();
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {		
		super.render();
		fpsLogger.log();
	}
}
