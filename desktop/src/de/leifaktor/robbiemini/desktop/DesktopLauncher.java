package de.leifaktor.robbiemini.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.leifaktor.robbiemini.RobbieMini;
import de.leifaktor.robbiemini.screens.GameScreen;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = GameScreen.WIDTH*16*2;
		config.height = GameScreen.HEIGHT*16*2;
		new LwjglApplication(new RobbieMini(), config);
	}
}
