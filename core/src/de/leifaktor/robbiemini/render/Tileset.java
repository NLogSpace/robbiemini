package de.leifaktor.robbiemini.render;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import de.leifaktor.robbiemini.Direction;

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
	
	public TextureRegion[] getTileTransition(int x, int y, int dir, int numberOfFrames) {
		TextureRegion[] tiles = new TextureRegion[numberOfFrames];
		for (int i = 0; i < numberOfFrames; i++) {
			tiles[i] = new TextureRegion(
					texture,
					(int) ((x+i*Direction.DIR_X[dir]/((float)numberOfFrames))*tilesize),
					(int) ((y-i*Direction.DIR_Y[dir]/((float)numberOfFrames))*tilesize),
					tilesize,
					tilesize);		
		}
		return tiles;
	}

}
