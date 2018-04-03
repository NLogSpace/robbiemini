package de.leifaktor.robbiemini.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import de.leifaktor.robbiemini.RobbieMini;
import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.XYPos;
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
import de.leifaktor.robbiemini.actor.Schalter;
import de.leifaktor.robbiemini.actor.Skull;
import de.leifaktor.robbiemini.actor.Sperre;
import de.leifaktor.robbiemini.actor.Teleporter;
import de.leifaktor.robbiemini.items.Item;
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
import de.leifaktor.robbiemini.tiles.Wall;

public class RoomRenderer {

	final int TILESIZE = 16;
	Room room;

	float xOffset;
	float yOffset;
	
	int minRenderLayer;
	int maxRenderLayer;

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

	public void render(SpriteBatch batch) {
		if (room == null) return;
		int numberOfLayers = room.getNumberOfLayers();
		for (int z = 0; z < numberOfLayers; z++) {
			for (int i = 0; i < room.width; i++) {
				for (int j = 0; j < room.height; j++) {
					if (room.getTile(i, j, z) instanceof Wall) draw(batch, Graphics.textures.get("wall_tile"), i, j);
					if (room.getTile(i, j, z) instanceof DarkWall) draw(batch, Graphics.textures.get("dark_wall_tile"), i, j);
					if (room.getTile(i, j, z) instanceof EmptyTile) draw(batch, Graphics.textures.get("empty_tile"), i, j);
					if (room.getTile(i, j, z) instanceof Door) {
						Door d = (Door) room.getTile(i, j, z);
						draw(batch, Graphics.textures.get("door_" + d.getNumber()), i, j);
					}
					if (room.getTile(i, j, z) instanceof BridgeLeft) draw(batch, Graphics.textures.get("bridge_left"), i, j);
					if (room.getTile(i, j, z) instanceof BridgeLR) draw(batch, Graphics.textures.get("bridge_lr"), i, j);
					if (room.getTile(i, j, z) instanceof BridgeRight) draw(batch, Graphics.textures.get("bridge_right"), i, j);
					if (room.getTile(i, j, z) instanceof BridgeUp) draw(batch, Graphics.textures.get("bridge_up"), i, j);
					if (room.getTile(i, j, z) instanceof BridgeUD) draw(batch, Graphics.textures.get("bridge_ud"), i, j);
					if (room.getTile(i, j, z) instanceof BridgeDown) draw(batch, Graphics.textures.get("bridge_down"), i, j);
					if (room.getTile(i, j, z) instanceof Glass) draw(batch, Graphics.textures.get("glass_tile"), i, j);
				}
			}
			for (Actor a : room.actors) {
				if (a.z != z) continue;
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
					if (((BulletStack)a).getNumber() > 0) {
						draw(batch, Graphics.textures.get("bullets_" + ((BulletStack)a).getNumber()), a.x, a.y);
					}				
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
				if (a instanceof Schalter) {
					Schalter schalter = (Schalter) a;
					if (schalter.isWallOnTheLeft() && schalter.isActive()) {
						draw(batch, Graphics.textures.get("schalter_left_on"),a.x, a.y);
					} else if (schalter.isWallOnTheLeft() && !schalter.isActive()) {
						draw(batch, Graphics.textures.get("schalter_left_off"),a.x, a.y);
					} else if (!schalter.isWallOnTheLeft() && schalter.isActive()) {
						draw(batch, Graphics.textures.get("schalter_right_on"),a.x, a.y);
					} else if (!schalter.isWallOnTheLeft() && !schalter.isActive()) {
						draw(batch, Graphics.textures.get("schalter_rightoff"),a.x, a.y);
					}
				}
				if (a instanceof Sperre) {
					Sperre sperre = (Sperre) a;
					if (!sperre.isOpen()) {
						draw(batch, Graphics.textures.get("sperre_closed"),a.x, a.y);
					} else {
						if (sperre.isLeftRight()) {
							draw(batch, Graphics.textures.get("sperre_lr"),a.x, a.y);
						} else {
							draw(batch, Graphics.textures.get("sperre_ud"),a.x, a.y);
						}
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
	}

	private void draw(SpriteBatch batch, TextureRegion graphic, float x, float y) {
		batch.draw(graphic,(x+xOffset)*TILESIZE, (y+yOffset)*TILESIZE, TILESIZE, TILESIZE);		
	}

	public XYPos getPositionInRoom(int x, int y, Room room) {
		x -= xOffset;
		y -= yOffset;
		int tileX = x / (RobbieMini.TILESIZE);
		int tileY = y / (RobbieMini.TILESIZE);
		if (tileX < 0 || tileX >= room.width) return null;
		if (tileY < 0 || tileY >= room.height) return null;
		return new XYPos(tileX, tileY);
	}

}
