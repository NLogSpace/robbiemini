package de.leifaktor.robbiemini.items;

import de.leifaktor.robbiemini.InputManager;
import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.SoundPlayer;
import de.leifaktor.robbiemini.actor.Actor;
import de.leifaktor.robbiemini.actor.FlyingBullet;
import de.leifaktor.robbiemini.actor.ItemActor;
import de.leifaktor.robbiemini.actor.Player;
import de.leifaktor.robbiemini.commands.AddActorCommand;
import de.leifaktor.robbiemini.commands.PlaySoundCommand;

public class Schleuder extends Item {	

	@Override
	public void onUse(Room room, int x, int y, int z) {
		room.commands.add(new AddActorCommand(new ItemActor(x, y, z, this)));
	}	

	@Override
	public void onHitBy(Room room, ItemActor itemActor, Actor actor, int x, int y, int z) {
		if (actor instanceof Player) {
			Player player = (Player) actor;
			if (InputManager.pressed[InputManager.SHIFT] || player.getBullets() == 0) {
				itemActor.collect(room, player);
				room.commands.add(new PlaySoundCommand(SoundPlayer.SOUND_COLLECT_CLICK));
			} else {
				room.commands.add(new AddActorCommand(new FlyingBullet(x, y, z, player.getDirection(), 0.25f)));
				player.removeBullet();
			}
		}
	}

	@Override
	public Item clone() {
		return new Schleuder();
	}

}
