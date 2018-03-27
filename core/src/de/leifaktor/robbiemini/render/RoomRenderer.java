package de.leifaktor.robbiemini.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.actor.Actor;
import de.leifaktor.robbiemini.actor.Arrow;
import de.leifaktor.robbiemini.actor.BulletStack;
import de.leifaktor.robbiemini.actor.DissolvingWall;
import de.leifaktor.robbiemini.actor.ElectricFence;
import de.leifaktor.robbiemini.actor.Explosion;
import de.leifaktor.robbiemini.actor.FlyingBullet;
import de.leifaktor.robbiemini.actor.Gold;
import de.leifaktor.robbiemini.actor.Isolator;
import de.leifaktor.robbiemini.actor.ItemActor;
import de.leifaktor.robbiemini.actor.Player;
import de.leifaktor.robbiemini.actor.Player.State;
import de.leifaktor.robbiemini.actor.Robot;
import de.leifaktor.robbiemini.actor.Skull;
import de.leifaktor.robbiemini.actor.Teleporter;
import de.leifaktor.robbiemini.items.Item;
import de.leifaktor.robbiemini.tiles.DarkWall;
import de.leifaktor.robbiemini.tiles.Door;
import de.leifaktor.robbiemini.tiles.EmptyTile;
import de.leifaktor.robbiemini.tiles.Wall;

public class RoomRenderer {
	
	final int TILESIZE = 16;
	Room room;
	
	float xOffset;
	float yOffset;
	
	public RoomRenderer() {
		xOffset = 0;
		yOffset = 0;
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
				if (room.tiles[room.width*j + i] instanceof Wall) draw(batch, Graphics.textures.get("wall_tile"), i, j);
				if (room.tiles[room.width*j + i] instanceof DarkWall) draw(batch, Graphics.textures.get("dark_wall_tile"), i, j);
				if (room.tiles[room.width*j + i] instanceof EmptyTile) draw(batch, Graphics.textures.get("empty_tile"), i, j);
				if (room.tiles[room.width*j + i] instanceof Door) {
					Door d = (Door) room.tiles[room.width*j + i];
					draw(batch, Graphics.textures.get("door_" + d.getNumber()), i, j);
				}
			}
		}
		for (Actor a : room.actors) {			
			if (a instanceof DissolvingWall) {
				DissolvingWall d = ((DissolvingWall)a);
				draw(batch, Graphics.animations.get("dissolving_wall").getKeyFrame(d.getTime(), false),d.x, d.y);
			}
			if (a instanceof ItemActor) {
				Item item = ((ItemActor)a).getItem();
				ItemRenderer.render(batch, item, (a.x+xOffset)*TILESIZE, (a.y+yOffset)*TILESIZE);				
			}			
			if (a instanceof Explosion) {
				Explosion e = ((Explosion)a);
				draw(batch, Graphics.animations.get("explosion").getKeyFrame(e.getTime(), false),e.getPosition().x, e.getPosition().y);
			}
			if (a instanceof Gold) {
				draw(batch, Graphics.textures.get("gold"), a.x, a.y);
			}
			if (a instanceof Arrow) {
				draw(batch, Graphics.textures.get("arrow_" + ((Arrow)a).getDir()),a.x, a.y);
			}
			if (a instanceof Isolator) {
				draw(batch, Graphics.textures.get("isolator"), a.getPosition().x, a.getPosition().y);
			}
			if (a instanceof ElectricFence) {
				draw(batch, Graphics.textures.get("electric_fence"), a.getPosition().x, a.getPosition().y);
			}			
			if (a instanceof Skull) {
				draw(batch, Graphics.textures.get("skull"), a.getPosition().x, a.getPosition().y);
			}
			if (a instanceof FlyingBullet) {
				draw(batch, Graphics.textures.get("flying_bullet"), a.getPosition().x, a.getPosition().y);
			}
			if (a instanceof BulletStack) {
				draw(batch, Graphics.textures.get("bullets_" + ((BulletStack)a).getNumber()), a.x, a.y);
			}
			if (a instanceof Robot) {
				Robot r = (Robot) a;
				draw(batch, Graphics.animations.get("robot_" + r.graphicType).getKeyFrame(r.getStateTime(), true),r.getPosition().x, r.getPosition().y);
			}
			if (a instanceof Teleporter) {
				if (((Teleporter)a).isActive()) {
					draw(batch, Graphics.animations.get("teleport").getKeyFrame(((Teleporter)a).getStateTime(), true),a.x, a.y);
				} else {
					draw(batch, Graphics.textures.get("teleport_off"),a.x, a.y);
				}				
			}
			if (a instanceof Player) {
				Player p = (Player)a;
				if (p.getState() == State.IDLE) {
					if (p.inventory.hasBlaumann()) {
						draw(batch, Graphics.textures.get("player_blaumann"), p.getPosition().x, p.getPosition().y);
					} else {
						draw(batch, Graphics.textures.get("player"), p.getPosition().x, p.getPosition().y);
					}
					
				} else if (p.getState() == State.WALKING) {
					if (p.inventory.hasBlaumann()) {
						draw(batch, Graphics.animations.get("player_walking_blaumann").getKeyFrame(p.getStateTime(), true),p.getPosition().x, p.getPosition().y);
					} else {
						draw(batch, Graphics.animations.get("player_walking").getKeyFrame(p.getStateTime(), true),p.getPosition().x, p.getPosition().y);
					}
					
				}
			}
		}		
	}
	
	private void draw(SpriteBatch batch, TextureRegion graphic, float x, float y) {
		batch.draw(graphic,(x+xOffset)*TILESIZE, (y+yOffset)*TILESIZE, TILESIZE, TILESIZE);
	}

}
