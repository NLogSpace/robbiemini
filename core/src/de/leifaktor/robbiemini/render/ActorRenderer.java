package de.leifaktor.robbiemini.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.leifaktor.robbiemini.RobbieMini;
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
import de.leifaktor.robbiemini.actor.Robot;
import de.leifaktor.robbiemini.actor.Schalter;
import de.leifaktor.robbiemini.actor.Skull;
import de.leifaktor.robbiemini.actor.SolidActor;
import de.leifaktor.robbiemini.actor.Sperre;
import de.leifaktor.robbiemini.actor.StairsDown;
import de.leifaktor.robbiemini.actor.StairsUp;
import de.leifaktor.robbiemini.actor.Teleporter;
import de.leifaktor.robbiemini.actor.Player.State;
import de.leifaktor.robbiemini.items.Item;

public class ActorRenderer {

	public static void render(SpriteBatch batch, Actor a, float x, float y, int scale) {
		if (a instanceof DissolvingWall) {
			DissolvingWall d = ((DissolvingWall)a);
			batch.draw(Graphics.animations.get("dissolving_wall").getKeyFrame(d.getTime(), false), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
		}
		if (a instanceof ItemActor) {
			Item item = ((ItemActor)a).getItem();
			ItemRenderer.render(batch, item, x, y, scale);			
		}			
		if (a instanceof Explosion) {
			Explosion e = ((Explosion)a);
			batch.draw(Graphics.animations.get("explosion").getKeyFrame(e.getTime(), false),x,y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
		}
		if (a instanceof Gold) {
			batch.draw(Graphics.textures.get("gold"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
		}
		if (a instanceof Arrow) {
			batch.draw(Graphics.textures.get("arrow_" + ((Arrow)a).getDir()), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
		}
		if (a instanceof Isolator) {
			batch.draw(Graphics.textures.get("isolator"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
		}
		if (a instanceof ElectricFence) {
			batch.draw(Graphics.textures.get("electric_fence"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
		}			
		if (a instanceof Skull) {
			batch.draw(Graphics.textures.get("skull"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
		}
		if (a instanceof FlyingBullet) {
			batch.draw(Graphics.textures.get("flying_bullet"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
		}
		if (a instanceof BulletStack) {
			if (((BulletStack)a).getNumber() > 0) {
				batch.draw(Graphics.textures.get("bullets_" + ((BulletStack)a).getNumber()), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
			}				
		}
		if (a instanceof Robot) {
			Robot r = (Robot) a;
			batch.draw(Graphics.animations.get("robot_" + r.graphicType).getKeyFrame(r.getStateTime(), true), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
		}
		if (a instanceof Teleporter) {
			if (((Teleporter)a).isActive()) {
				batch.draw(Graphics.animations.get("teleport").getKeyFrame(((Teleporter)a).getStateTime(), true), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
			} else {
				batch.draw(Graphics.textures.get("teleport_off"), x, y);
			}				
		}
		if (a instanceof StairsUp) {
			batch.draw(Graphics.textures.get("stairs_up"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
		}
		if (a instanceof StairsDown) {
			batch.draw(Graphics.textures.get("stairs_down"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
		} 
		if (a instanceof Schalter) {
			Schalter schalter = (Schalter) a;
			if (schalter.isWallOnTheLeft() && schalter.isActive()) {
				batch.draw(Graphics.textures.get("schalter_left_on"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
			} else if (schalter.isWallOnTheLeft() && !schalter.isActive()) {
				batch.draw(Graphics.textures.get("schalter_left_off"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
			} else if (!schalter.isWallOnTheLeft() && schalter.isActive()) {
				batch.draw(Graphics.textures.get("schalter_right_on"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
			} else if (!schalter.isWallOnTheLeft() && !schalter.isActive()) {
				batch.draw(Graphics.textures.get("schalter_right_off"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
			}
		}
		if (a instanceof Sperre) {
			Sperre sperre = (Sperre) a;
			if (!sperre.isOpen()) {
				batch.draw(Graphics.textures.get("sperre_closed"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
			} else {
				if (sperre.isLeftRight()) {
					batch.draw(Graphics.textures.get("sperre_lr"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
				} else {
					batch.draw(Graphics.textures.get("sperre_ud"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
				}
			}
		}
		if (a instanceof SolidActor) {
			int type = ((SolidActor) a).type;
			switch (type) {
				case SolidActor.MOUNTAIN_1: batch.draw(Graphics.textures.get("half_mountain_1"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale); break;
				case SolidActor.MOUNTAIN_2: batch.draw(Graphics.textures.get("half_mountain_2"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale); break;
				case SolidActor.MOUNTAIN_3: batch.draw(Graphics.textures.get("half_mountain_3"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale); break;
				case SolidActor.MOUNTAIN_4: batch.draw(Graphics.textures.get("half_mountain_4"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale); break;
				case SolidActor.MOUNTAIN_5: batch.draw(Graphics.textures.get("half_mountain_5"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale); break;
				case SolidActor.MOUNTAIN_6: batch.draw(Graphics.textures.get("half_mountain_6"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale); break;
				case SolidActor.MOUNTAIN_7: batch.draw(Graphics.textures.get("half_mountain_7"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale); break;
				case SolidActor.MOUNTAIN_8: batch.draw(Graphics.textures.get("half_mountain_8"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale); break;
				case SolidActor.MOUNTAIN_9: batch.draw(Graphics.textures.get("half_mountain_9"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale); break;
				case SolidActor.MOUNTAIN_10: batch.draw(Graphics.textures.get("half_mountain_10"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale); break;
				case SolidActor.MOUNTAIN_11: batch.draw(Graphics.textures.get("half_mountain_11"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale); break;
				case SolidActor.MOUNTAIN_12: batch.draw(Graphics.textures.get("half_mountain_12"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale); break;
				case SolidActor.MOUNTAIN_13: batch.draw(Graphics.textures.get("half_mountain_13"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale); break;
				case SolidActor.MOUNTAIN_14: batch.draw(Graphics.textures.get("half_mountain_14"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale); break;
				case SolidActor.MOUNTAIN_15: batch.draw(Graphics.textures.get("half_mountain_15"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale); break;
				case SolidActor.MOUNTAIN_16: batch.draw(Graphics.textures.get("half_mountain_16"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale); break;
				case SolidActor.DARK_WALL_1: batch.draw(Graphics.textures.get("dark_wall_tile_sw"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale); break;
				case SolidActor.DARK_WALL_2: batch.draw(Graphics.textures.get("dark_wall_tile_se"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale); break;
				case SolidActor.DARK_WALL_3: batch.draw(Graphics.textures.get("dark_wall_tile_ne"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale); break;
				case SolidActor.DARK_WALL_4: batch.draw(Graphics.textures.get("dark_wall_tile_nw"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale); break;
			}
		}
		if (a instanceof Player) {
			Player p = (Player)a;
			if (p.getState() == State.IDLE) {
				if (p.inventory.hasBlaumann()) {
					batch.draw(Graphics.textures.get("player_blaumann"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
				} else {
					batch.draw(Graphics.textures.get("player"), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
				}

			} else if (p.getState() == State.WALKING) {
				if (p.inventory.hasBlaumann()) {
					batch.draw(Graphics.animations.get("player_walking_blaumann").getKeyFrame(p.getStateTime(), true), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
				} else {
					batch.draw(Graphics.animations.get("player_walking").getKeyFrame(p.getStateTime(), true), x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
				}
			}
		}		
	}

	public static void render(SpriteBatch batch, Actor a, float x, float y) {
		render(batch, a, x, y, 1);		
	}

}
