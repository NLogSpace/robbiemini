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
import de.leifaktor.robbiemini.actor.Sperre;
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
