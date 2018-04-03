package de.leifaktor.robbiemini.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.leifaktor.robbiemini.RobbieMini;
import de.leifaktor.robbiemini.tiles.BridgeDown;
import de.leifaktor.robbiemini.tiles.BridgeLR;
import de.leifaktor.robbiemini.tiles.BridgeLeft;
import de.leifaktor.robbiemini.tiles.BridgeRight;
import de.leifaktor.robbiemini.tiles.BridgeUD;
import de.leifaktor.robbiemini.tiles.BridgeUp;
import de.leifaktor.robbiemini.tiles.DarkWall;
import de.leifaktor.robbiemini.tiles.Door;
import de.leifaktor.robbiemini.tiles.EmptyTile;
import de.leifaktor.robbiemini.tiles.Glass;
import de.leifaktor.robbiemini.tiles.Tile;
import de.leifaktor.robbiemini.tiles.Wall;

public class TileRenderer {
	
	public static void render(SpriteBatch batch, Tile tile, float x, float y) {
		render(batch, tile, x, y, 1);
	}

	public static void render(SpriteBatch batch, Tile tile, float x, float y, int scale) {
		if (tile instanceof Wall) batch.draw(Graphics.textures.get("wall_tile"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
		if (tile instanceof DarkWall) batch.draw(Graphics.textures.get("dark_wall_tile"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
		if (tile instanceof EmptyTile) batch.draw(Graphics.textures.get("empty_tile"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
		if (tile instanceof Door) {
			Door d = (Door) tile;
			batch.draw(Graphics.textures.get("door_" + d.getNumber()), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
		}
		if (tile instanceof BridgeLeft) batch.draw(Graphics.textures.get("bridge_left"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
		if (tile instanceof BridgeLR) batch.draw(Graphics.textures.get("bridge_lr"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
		if (tile instanceof BridgeRight) batch.draw(Graphics.textures.get("bridge_right"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
		if (tile instanceof BridgeUp) batch.draw(Graphics.textures.get("bridge_up"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
		if (tile instanceof BridgeUD) batch.draw(Graphics.textures.get("bridge_ud"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
		if (tile instanceof BridgeDown) batch.draw(Graphics.textures.get("bridge_down"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
		if (tile instanceof Glass) batch.draw(Graphics.textures.get("glass_tile"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
	}

}
