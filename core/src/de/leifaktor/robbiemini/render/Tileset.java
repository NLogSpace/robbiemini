package de.leifaktor.robbiemini.render;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Tileset {
	
	Texture texture;
	int tilesize;
	
	public Tileset(String filename, int tilesize) {
		this.texture = new Texture(filename);
		this.tilesize = tilesize;
	}
	
	public TextureRegion getTile(int x, int y) {
		return new TextureRegion(texture, x*tilesize, y*tilesize, tilesize, tilesize);
	}
	
	public TextureRegion[] getTiles(int x, int y, int number) {
		TextureRegion[] tiles = new TextureRegion[number];
		int count = 0;
		while (count < number) {
			tiles[count] = getTile(x,y);
			count++;
			x++;
			if (x == 16) {
				y++;
				x = 0;
			}
		}
		return tiles;
	}

}
