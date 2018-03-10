package de.leifaktor.robbiemini.render;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.actor.Actor;
import de.leifaktor.robbiemini.actor.Actor.MoveState;
import de.leifaktor.robbiemini.items.Acid;
import de.leifaktor.robbiemini.items.Item;
import de.leifaktor.robbiemini.items.Key;
import de.leifaktor.robbiemini.items.Life;
import de.leifaktor.robbiemini.actor.Arrow;
import de.leifaktor.robbiemini.actor.DissolvingWall;
import de.leifaktor.robbiemini.actor.ElectricFence;
import de.leifaktor.robbiemini.actor.Explosion;
import de.leifaktor.robbiemini.actor.Gold;
import de.leifaktor.robbiemini.actor.Isolator;
import de.leifaktor.robbiemini.actor.ItemActor;
import de.leifaktor.robbiemini.actor.Player;
import de.leifaktor.robbiemini.actor.Robot;
import de.leifaktor.robbiemini.actor.Skull;
import de.leifaktor.robbiemini.tiles.DarkWall;
import de.leifaktor.robbiemini.tiles.Door;
import de.leifaktor.robbiemini.tiles.EmptyTile;
import de.leifaktor.robbiemini.tiles.Ice;
import de.leifaktor.robbiemini.tiles.Wall;

public class RoomRenderer {
	
	final int TILESIZE = 16;
	Room room;
	
	float xOffset;
	float yOffset;
	
	TextureRegion wall;
	TextureRegion darkWall;
	TextureRegion empty;
	TextureRegion ice;
	TextureRegion acid;
	TextureRegion player;
	TextureRegion[] keys;
	TextureRegion[] doors;
	TextureRegion gold;
	TextureRegion[] arrows;
	TextureRegion isolator;
	TextureRegion electricFence;
	TextureRegion skull;
	TextureRegion life;
	Animation<TextureRegion> playerWalking;
	Animation<TextureRegion> dissolvingWall;
	ArrayList<Animation<TextureRegion>> robots;
	Animation<TextureRegion> explosion;
	
	public RoomRenderer() {
		xOffset = 0;
		yOffset = 0;
		
		Tileset tileset = new Tileset("tileset16.png", 16);
		empty = tileset.getTile(0, 0);
		wall = tileset.getTile(1, 0);
		darkWall = tileset.getTile(3, 0);
		ice = tileset.getTile(8, 0);
		acid = tileset.getTile(2, 10);
		player = tileset.getTile(0, 7);
		playerWalking = new Animation<TextureRegion>(0.12f,tileset.getTiles(1, 7, 2));
		keys = tileset.getTiles(0, 12, 16);
		doors = tileset.getTiles(0, 2, 16);
		gold = tileset.getTile(1, 10);
		arrows = tileset.getTiles(6, 10, 4);
		isolator = tileset.getTile(14, 10);
		electricFence = tileset.getTile(0, 10);
		skull = tileset.getTile(9, 9);
		life = tileset.getTile(7, 11);
		dissolvingWall = new Animation<TextureRegion>(0.25f,tileset.getTiles(0, 1, 16));
		robots = new ArrayList<Animation<TextureRegion>>();
		for (int i = 0; i < 7; i++) {
			robots.add(new Animation<TextureRegion>(0.6f,tileset.getTiles(2*i, 8, 2)));
		}		
		explosion = new Animation<TextureRegion>(0.3f,tileset.getTiles(6, 7, 5));
	}
	
	public void setOffset(float x, float y) {
		this.xOffset = x;
		this.yOffset = y;
	}
	
	public void setRoom(Room room) {
		this.room = room;
	}
	
	
	public void render(SpriteBatch batch, Room room) {
		for (int i = 0; i < room.width; i++) {
			for (int j = 0; j < room.height; j++) {
				if (room.tiles[room.width*j + i] instanceof Wall) draw(batch, wall, i, j);
				if (room.tiles[room.width*j + i] instanceof DarkWall) draw(batch, darkWall, i, j);
				if (room.tiles[room.width*j + i] instanceof EmptyTile) draw(batch, empty, i, j);
				if (room.tiles[room.width*j + i] instanceof Ice) draw(batch, ice, i, j);
				if (room.tiles[room.width*j + i] instanceof Door) {
					Door d = (Door) room.tiles[room.width*j + i];
					draw(batch, doors[d.getNumber()], i, j);
				}
			}
		}
		for (Actor a : room.actors) {			
			if (a instanceof DissolvingWall) {
				DissolvingWall d = ((DissolvingWall)a);
				draw(batch, dissolvingWall.getKeyFrame(d.getTime(), false),d.x, d.y);
			}
			if (a instanceof ItemActor) {
				Item item = ((ItemActor)a).getItem();
				if (item instanceof Acid) {
					draw(batch, acid, a.x, a.y);
				} else if (item instanceof Key) {
					Key key = (Key) item;
					draw(batch, keys[key.getNumber()], a.x, a.y);
				} else if (item instanceof Life) {
					draw(batch, life, a.x, a.y);
				}
			}			
			if (a instanceof Explosion) {
				Explosion e = ((Explosion)a);
				draw(batch, explosion.getKeyFrame(e.getTime(), false),e.x, e.y);
			}
			if (a instanceof Gold) {
				draw(batch, gold, a.x, a.y);
			}
			if (a instanceof Arrow) {
				draw(batch, arrows[((Arrow)a).getDir()],a.x, a.y);
			}
			if (a instanceof Isolator) {
				draw(batch, isolator, a.getPosition().x, a.getPosition().y);
			}
			if (a instanceof ElectricFence) {
				draw(batch, electricFence,a.getPosition().x, a.getPosition().y);
			}			
			if (a instanceof Skull) {
				draw(batch, skull, a.getPosition().x, a.getPosition().y);
			}
			if (a instanceof Robot) {
				Robot r = (Robot) a;
				draw(batch, robots.get(r.graphicType).getKeyFrame(r.getStateTime(), true),r.getPosition().x, r.getPosition().y);
			}			
			if (a instanceof Player) {
				Player p = (Player)a;
				if (p.getMoveState() == MoveState.IDLE) {
					draw(batch, player, p.getPosition().x, p.getPosition().y);
				} else if (p.getMoveState() == MoveState.MOVING) {
					draw(batch, playerWalking.getKeyFrame(p.getStateTime(), true),p.getPosition().x, p.getPosition().y);
				}
			}
		}		
	}	
	
	private void draw(SpriteBatch batch, TextureRegion graphic, float x, float y) {
		batch.draw(graphic,(x+xOffset)*TILESIZE, (y+yOffset)*TILESIZE, TILESIZE, TILESIZE);
	}

}
