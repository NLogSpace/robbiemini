package de.leifaktor.robbiemini.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.leifaktor.robbiemini.RobbieMini;
import de.leifaktor.robbiemini.items.Acid;
import de.leifaktor.robbiemini.items.Blaumann;
import de.leifaktor.robbiemini.items.Flossen;
import de.leifaktor.robbiemini.items.IceSkates;
import de.leifaktor.robbiemini.items.Item;
import de.leifaktor.robbiemini.items.Key;
import de.leifaktor.robbiemini.items.Life;
import de.leifaktor.robbiemini.items.Magnet;
import de.leifaktor.robbiemini.items.Notiz;
import de.leifaktor.robbiemini.items.Schleuder;

public class ItemRenderer {
	
	public static void render(SpriteBatch batch, Item item, float x, float y) {
		render(batch, item, x, y, 1);		
	}

	public static void render(SpriteBatch batch, Item item, float x, float y, int scale) {
		if (item instanceof Acid) {
			batch.draw(Graphics.textures.get("acid"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
		} else if (item instanceof Key) {
			Key key = (Key) item;
			batch.draw(Graphics.textures.get("key_" + key.getNumber()), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
		} else if (item instanceof Life) {
			batch.draw(Graphics.textures.get("life"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
		} else if (item instanceof Magnet) {
			if (((Magnet)item).isPositive()) {
				batch.draw(Graphics.textures.get("magnet_positive"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
			} else {
				batch.draw(Graphics.textures.get("magnet_negative"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
			}
		} else if (item instanceof Blaumann) {
			batch.draw(Graphics.textures.get("blaumann"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
		} else if (item instanceof Notiz) {
			batch.draw(Graphics.textures.get("notiz"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
		} else if (item instanceof Schleuder) {
			batch.draw(Graphics.textures.get("schleuder"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
		} else if (item instanceof IceSkates) {
			batch.draw(Graphics.textures.get("ice_skates"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
		} else if (item instanceof Flossen) {
			batch.draw(Graphics.textures.get("flossen"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
		}else {
			batch.draw(Graphics.textures.get("inventory_outer_background"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
		}	
	}

}
