package de.leifaktor.robbiemini.render;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.actor.Acid;
import de.leifaktor.robbiemini.actor.Actor;
import de.leifaktor.robbiemini.actor.Actor.MoveState;
import de.leifaktor.robbiemini.actor.Arrow;
import de.leifaktor.robbiemini.actor.DissolvingWall;
import de.leifaktor.robbiemini.actor.ElectricFence;
import de.leifaktor.robbiemini.actor.Explosion;
import de.leifaktor.robbiemini.actor.Gold;
import de.leifaktor.robbiemini.actor.Isolator;
import de.leifaktor.robbiemini.actor.Key;
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
	Animation<TextureRegion> playerWalking;
	Animation<TextureRegion> dissolvingWall;
	ArrayList<Animation<TextureRegion>> robots;
	Animation<TextureRegion> explosion;
	
	public RoomRenderer() {
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
		dissolvingWall = new Animation<TextureRegion>(0.25f,tileset.getTiles(0, 1, 16));
		robots = new ArrayList<Animation<TextureRegion>>();
		for (int i = 0; i < 7; i++) {
			robots.add(new Animation<TextureRegion>(0.6f,tileset.getTiles(2*i, 8, 2)));
		}
		
		explosion = new Animation<TextureRegion>(0.3f,tileset.getTiles(6, 7, 5));
	}
	
	public void setRoom(Room room) {
		this.room = room;
	}
	
	
	public void render(SpriteBatch batch, Room room) {
		for (int x = 0; x < room.width; x++) {
			for (int y = 0; y < room.height; y++) {
				if (room.tiles[room.width*y + x] instanceof Wall) batch.draw(wall, x*TILESIZE, y*TILESIZE, TILESIZE, TILESIZE);
				if (room.tiles[room.width*y + x] instanceof DarkWall) batch.draw(darkWall, x*TILESIZE, y*TILESIZE, TILESIZE, TILESIZE);
				if (room.tiles[room.width*y + x] instanceof EmptyTile) batch.draw(empty, x*TILESIZE, y*TILESIZE, TILESIZE, TILESIZE);
				if (room.tiles[room.width*y + x] instanceof Ice) batch.draw(ice, x*TILESIZE, y*TILESIZE, TILESIZE, TILESIZE);
				if (room.tiles[room.width*y + x] instanceof Door) {
					Door d = (Door) room.tiles[room.width*y + x];
					batch.draw(doors[d.getNumber()], x*TILESIZE, y*TILESIZE, TILESIZE, TILESIZE);
				}
			}
		}
		for (Actor a : room.actors) {			
			if (a instanceof DissolvingWall) {
				DissolvingWall d = ((DissolvingWall)a);
				batch.draw(dissolvingWall.getKeyFrame(d.getTime(), false),d.x*TILESIZE, d.y*TILESIZE, TILESIZE, TILESIZE);
			}
			if (a instanceof Acid) batch.draw(acid,a.x*TILESIZE,a.y*TILESIZE,TILESIZE,TILESIZE);
			if (a instanceof Explosion) {
				Explosion e = ((Explosion)a);
				batch.draw(explosion.getKeyFrame(e.getTime(), false),e.x*TILESIZE, e.y*TILESIZE, TILESIZE, TILESIZE);
			}
			if (a instanceof Key) {
				Key key = (Key) a;
				batch.draw(keys[key.getNumber()], key.x*TILESIZE,key.y*TILESIZE,TILESIZE,TILESIZE);
			}

			if (a instanceof Gold) {
				batch.draw(gold,a.x*TILESIZE, a.y*TILESIZE, TILESIZE, TILESIZE);
			}
			if (a instanceof Arrow) {
				batch.draw(arrows[((Arrow)a).getDir()],a.x*TILESIZE, a.y*TILESIZE, TILESIZE, TILESIZE);
			}
			if (a instanceof Isolator) {
				batch.draw(isolator,a.getPosition().x*TILESIZE, a.getPosition().y*TILESIZE, TILESIZE, TILESIZE);
			}
			if (a instanceof ElectricFence) {
				batch.draw(electricFence,a.getPosition().x*TILESIZE, a.getPosition().y*TILESIZE, TILESIZE, TILESIZE);
			}			
			if (a instanceof Skull) {
				batch.draw(skull,a.getPosition().x*TILESIZE, a.getPosition().y*TILESIZE, TILESIZE, TILESIZE);
			}
			if (a instanceof Robot) {
				Robot r = (Robot) a;
				batch.draw(robots.get(r.graphicType).getKeyFrame(r.getStateTime(), true),r.getPosition().x*TILESIZE, r.getPosition().y*TILESIZE, TILESIZE, TILESIZE);
			}			
			if (a instanceof Player) {
				Player p = (Player)a;
				if (p.getMoveState() == MoveState.IDLE) {
					batch.draw(player, p.getPosition().x*TILESIZE, p.getPosition().y*TILESIZE, TILESIZE, TILESIZE);
				} else if (p.getMoveState() == MoveState.MOVING) {
					batch.draw(playerWalking.getKeyFrame(p.getStateTime(), true),p.getPosition().x*TILESIZE, p.getPosition().y*TILESIZE, TILESIZE, TILESIZE);
				}
			}
		}

		
	}		

}
