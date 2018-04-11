package de.leifaktor.robbiemini.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

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
		TextureRegion pictureToDraw = null;
		if (a instanceof DissolvingWall) {
			pictureToDraw = Graphics.animations.get("dissolving_wall").getKeyFrame(((DissolvingWall)a).getTime(), false);			
		} else if (a instanceof ItemActor) {
			Item item = ((ItemActor)a).getItem();
			ItemRenderer.render(batch, item, x, y, scale);			
		} else if (a instanceof Explosion) {
			pictureToDraw = Graphics.animations.get("explosion").getKeyFrame(((Explosion)a).getTime());			
		} else if (a instanceof Gold) {
			pictureToDraw = Graphics.textures.get("gold");
		} else if (a instanceof Arrow) {
			pictureToDraw = Graphics.textures.get("arrow_" + ((Arrow)a).getDir());
		} else if (a instanceof Isolator) {
			pictureToDraw = Graphics.textures.get("isolator");
		} else if (a instanceof ElectricFence) {
			pictureToDraw = Graphics.textures.get("electric_fence");
		} else if (a instanceof Skull) {
			pictureToDraw = Graphics.textures.get("skull");
		} else if (a instanceof FlyingBullet) {
			pictureToDraw = Graphics.textures.get("flying_bullet");
		} else if (a instanceof BulletStack) {
			if (((BulletStack)a).getNumber() > 0) {
				pictureToDraw = Graphics.textures.get("bullets_" + ((BulletStack)a).getNumber());
			}				
		} else if (a instanceof Robot) {
			Robot r = (Robot) a;
			pictureToDraw = Graphics.animations.get("robot_" + r.graphicType).getKeyFrame(r.getStateTime(), true);
		} else if (a instanceof Teleporter) {
			if (((Teleporter)a).isActive()) {
				pictureToDraw = Graphics.animations.get("teleport").getKeyFrame(((Teleporter)a).getStateTime(), true);
			} else {
				pictureToDraw = Graphics.textures.get("teleport_off");
			}				
		} else if (a instanceof StairsUp) {
			pictureToDraw = Graphics.textures.get("stairs_up");
		} else if (a instanceof StairsDown) {
			pictureToDraw = Graphics.textures.get("stairs_down");
		} else if (a instanceof Schalter) {
			Schalter schalter = (Schalter) a;
			if (schalter.isWallOnTheLeft() && schalter.isActive()) {
				pictureToDraw = Graphics.textures.get("schalter_left_on");
			} else if (schalter.isWallOnTheLeft() && !schalter.isActive()) {
				pictureToDraw = Graphics.textures.get("schalter_left_off");
			} else if (!schalter.isWallOnTheLeft() && schalter.isActive()) {
				pictureToDraw = Graphics.textures.get("schalter_right_on");
			} else if (!schalter.isWallOnTheLeft() && !schalter.isActive()) {
				pictureToDraw = Graphics.textures.get("schalter_right_off");
			}
		} else if (a instanceof Sperre) {
			Sperre sperre = (Sperre) a;
			if (!sperre.isOpen()) {
				pictureToDraw = Graphics.textures.get("sperre_closed");
			} else {
				if (sperre.isLeftRight()) {
					pictureToDraw = Graphics.textures.get("sperre_lr");
				} else {
					pictureToDraw = Graphics.textures.get("sperre_ud");
				}
			}
		} else if (a instanceof SolidActor) {
			int type = ((SolidActor) a).type;
			switch (type) {
				case SolidActor.MOUNTAIN_1: pictureToDraw = Graphics.textures.get("half_mountain_1"); break;
				case SolidActor.MOUNTAIN_2: pictureToDraw = Graphics.textures.get("half_mountain_2"); break;
				case SolidActor.MOUNTAIN_3: pictureToDraw = Graphics.textures.get("half_mountain_3"); break;
				case SolidActor.MOUNTAIN_4: pictureToDraw = Graphics.textures.get("half_mountain_4"); break;
				case SolidActor.MOUNTAIN_5: pictureToDraw = Graphics.textures.get("half_mountain_5"); break;
				case SolidActor.MOUNTAIN_6: pictureToDraw = Graphics.textures.get("half_mountain_6"); break;
				case SolidActor.MOUNTAIN_7: pictureToDraw = Graphics.textures.get("half_mountain_7"); break;
				case SolidActor.MOUNTAIN_8: pictureToDraw = Graphics.textures.get("half_mountain_8"); break;
				case SolidActor.MOUNTAIN_9: pictureToDraw = Graphics.textures.get("half_mountain_9"); break;
				case SolidActor.MOUNTAIN_10: pictureToDraw = Graphics.textures.get("half_mountain_10"); break;
				case SolidActor.MOUNTAIN_11: pictureToDraw = Graphics.textures.get("half_mountain_11"); break;
				case SolidActor.MOUNTAIN_12: pictureToDraw = Graphics.textures.get("half_mountain_12"); break;
				case SolidActor.MOUNTAIN_13: pictureToDraw = Graphics.textures.get("half_mountain_13"); break;
				case SolidActor.MOUNTAIN_14: pictureToDraw = Graphics.textures.get("half_mountain_14"); break;
				case SolidActor.MOUNTAIN_15: pictureToDraw = Graphics.textures.get("half_mountain_15"); break;
				case SolidActor.MOUNTAIN_16: pictureToDraw = Graphics.textures.get("half_mountain_16"); break;
				case SolidActor.DARK_WALL_1: pictureToDraw = Graphics.textures.get("dark_wall_tile_sw"); break;
				case SolidActor.DARK_WALL_2: pictureToDraw = Graphics.textures.get("dark_wall_tile_se"); break;
				case SolidActor.DARK_WALL_3: pictureToDraw = Graphics.textures.get("dark_wall_tile_ne"); break;
				case SolidActor.DARK_WALL_4: pictureToDraw = Graphics.textures.get("dark_wall_tile_nw"); break;
			}
		} else if (a instanceof Player) {
			Player p = (Player)a;
			if (p.getState() == State.IDLE || p.getState() == State.SLIDING) {
				if (p.inventory.hasBlaumann()) {
					pictureToDraw = Graphics.textures.get("player_blaumann");
				} else {
					pictureToDraw = Graphics.textures.get("player");
				}
			} else if (p.getState() == State.WALKING) {
				if (p.inventory.hasBlaumann()) {
					pictureToDraw = Graphics.animations.get("player_walking_blaumann").getKeyFrame(p.getStateTime(), true);
				} else {
					pictureToDraw = Graphics.animations.get("player_walking").getKeyFrame(p.getStateTime(), true);
				}
			} else if (p.getState() == State.SWIMMING) {
				if (p.inventory.hasBlaumann()) {
					pictureToDraw = Graphics.animations.get("player_swimming_blaumann").getKeyFrame(p.getStateTime(), true);
				} else {
					pictureToDraw = Graphics.animations.get("player_swimming").getKeyFrame(p.getStateTime(), true);
				}
			}
		}
		if (pictureToDraw != null) {
			batch.draw(pictureToDraw, x, y, RobbieMini.TILESIZE*scale, RobbieMini.TILESIZE*scale);
		}
	}

	public static void render(SpriteBatch batch, Actor a, float x, float y) {
		render(batch, a, x, y, 1);		
	}

}
