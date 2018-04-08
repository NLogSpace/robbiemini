package de.leifaktor.robbiemini.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.leifaktor.robbiemini.RobbieMini;
import de.leifaktor.robbiemini.tiles.BridgeDown;
import de.leifaktor.robbiemini.tiles.BridgeLR;
import de.leifaktor.robbiemini.tiles.BridgeLeft;
import de.leifaktor.robbiemini.tiles.BridgeRight;
import de.leifaktor.robbiemini.tiles.BridgeUD;
import de.leifaktor.robbiemini.tiles.BridgeUp;
import de.leifaktor.robbiemini.tiles.Door;
import de.leifaktor.robbiemini.tiles.EmptyTile;
import de.leifaktor.robbiemini.tiles.Glass;
import de.leifaktor.robbiemini.tiles.Ice;
import de.leifaktor.robbiemini.tiles.Solid;
import de.leifaktor.robbiemini.tiles.Tile;
import de.leifaktor.robbiemini.tiles.Wall;
import de.leifaktor.robbiemini.tiles.Water;

public class TileRenderer {
	
	public static void render(SpriteBatch batch, Tile tile, float x, float y) {
		render(batch, tile, x, y, 1);
	}

	public static void render(SpriteBatch batch, Tile tile, float x, float y, int scale) {
		if (tile instanceof Wall) batch.draw(Graphics.textures.get("wall_tile"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
		if (tile instanceof Solid) {
			int type = ((Solid) tile).type;
			if (type == Solid.DARK_WALL) batch.draw(Graphics.textures.get("dark_wall_tile"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
			if (type == Solid.MOUNTAIN_1) batch.draw(Graphics.textures.get("mountain_1"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
			if (type == Solid.MOUNTAIN_2) batch.draw(Graphics.textures.get("mountain_2"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
			if (type == Solid.MOUNTAIN_3) batch.draw(Graphics.textures.get("mountain_3"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
			if (type == Solid.MOUNTAIN_4) batch.draw(Graphics.textures.get("mountain_4"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
		}
		if (tile instanceof EmptyTile) {
			int type = ((EmptyTile) tile).type;
			if (type == EmptyTile.WHITE) batch.draw(Graphics.textures.get("empty_tile"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
			if (type == EmptyTile.GRASS) batch.draw(Graphics.textures.get("grass"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
		}
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
		if (tile instanceof Ice) batch.draw(Graphics.textures.get("ice_tile"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
		if (tile instanceof Water) {
			switch (((Water)tile).type) {
			case Water.NORMAL:
				batch.draw(Graphics.textures.get("water_normal"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
				break;
			case Water.RIGHT:
				batch.draw(Graphics.animations.get("water_right").getKeyFrame(RobbieMini.clock, true), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
				break;
			case Water.LEFT:
				batch.draw(Graphics.animations.get("water_left").getKeyFrame(RobbieMini.clock, true), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
				break;
			case Water.UP:
				batch.draw(Graphics.animations.get("water_up").getKeyFrame(RobbieMini.clock, true), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
				break;
			case Water.DOWN:
				batch.draw(Graphics.animations.get("water_down").getKeyFrame(RobbieMini.clock, true), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
				break;
			case Water.DL:
				batch.draw(Graphics.animations.get("water_dl").getKeyFrame(RobbieMini.clock, true), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
				break;
			case Water.DR:
				batch.draw(Graphics.animations.get("water_dr").getKeyFrame(RobbieMini.clock, true), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
				break;
			case Water.UL:
				batch.draw(Graphics.animations.get("water_ul").getKeyFrame(RobbieMini.clock, true), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
				break;
			case Water.UR:
				batch.draw(Graphics.animations.get("water_ur").getKeyFrame(RobbieMini.clock, true), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
				break;				
			}
		}
	}

}
