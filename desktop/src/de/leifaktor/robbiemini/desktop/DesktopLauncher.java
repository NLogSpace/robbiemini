package de.leifaktor.robbiemini.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.leifaktor.robbiemini.RobbieMini;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = RobbieMini.getVirtualWidth()*RobbieMini.SCALE;
		config.height = RobbieMini.getVirtualHeight()*RobbieMini.SCALE;
		new LwjglApplication(new RobbieMini(), config);
	}
}
