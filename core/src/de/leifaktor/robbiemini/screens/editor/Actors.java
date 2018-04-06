package de.leifaktor.robbiemini.screens.editor;

import java.util.ArrayList;
import java.util.List;

import de.leifaktor.robbiemini.actor.Actor;
import de.leifaktor.robbiemini.actor.Arrow;
import de.leifaktor.robbiemini.actor.BulletStack;
import de.leifaktor.robbiemini.actor.ElectricFence;
import de.leifaktor.robbiemini.actor.Gold;
import de.leifaktor.robbiemini.actor.Isolator;
import de.leifaktor.robbiemini.actor.ItemActor;
import de.leifaktor.robbiemini.actor.Schalter;
import de.leifaktor.robbiemini.actor.Skull;
import de.leifaktor.robbiemini.actor.Sperre;
import de.leifaktor.robbiemini.actor.StairsDown;
import de.leifaktor.robbiemini.actor.StairsUp;
import de.leifaktor.robbiemini.actor.Teleporter;
import de.leifaktor.robbiemini.condition.IsTermZeroCondition;
import de.leifaktor.robbiemini.condition.RobotsAliveTerm;
import de.leifaktor.robbiemini.items.Acid;
import de.leifaktor.robbiemini.items.Blaumann;
import de.leifaktor.robbiemini.items.Key;
import de.leifaktor.robbiemini.items.Life;
import de.leifaktor.robbiemini.items.Magnet;
import de.leifaktor.robbiemini.items.Notiz;
import de.leifaktor.robbiemini.items.Schleuder;

public class Actors {
	
	public static List<Actor> actors;
	
	public static void init() {
		actors = new ArrayList<Actor>();
		for (int i = 0; i < 8; i++) actors.add(new Arrow(0,0,0,i));
		for (int i = 1; i <= 6; i++) actors.add(new BulletStack(0,0,0,i));
		actors.add(new ElectricFence(0,0,0));
		actors.add(new Gold(0,0,0));
		actors.add(new Isolator(0,0,0));
		actors.add(new Schalter(0,0,0,false,false,""));
		actors.add(new Schalter(0,0,0,false,true,""));
		actors.add(new Schalter(0,0,0,true,false,""));
		actors.add(new Schalter(0,0,0,true,true,""));
		actors.add(new Skull(0,0,0));
		actors.add(new Sperre(0,0,0, new IsTermZeroCondition(new RobotsAliveTerm()), false, true));
		actors.add(new Sperre(0,0,0, new IsTermZeroCondition(new RobotsAliveTerm()), true, true));
		actors.add(new Teleporter(0,0,0,null));
		actors.add(new ItemActor(0,0,0,new Acid()));
		actors.add(new ItemActor(0,0,0,new Blaumann()));
		actors.add(new ItemActor(0,0,0,new Life()));
		actors.add(new ItemActor(0,0,0,new Magnet(true)));
		actors.add(new ItemActor(0,0,0,new Magnet(false)));
		actors.add(new ItemActor(0,0,0,new Notiz("")));
		actors.add(new ItemActor(0,0,0,new Schleuder()));
		for (int i = 0; i < 16; i++) actors.add(new ItemActor(0,0,0,new Key(i)));
		actors.add(new StairsUp(0,0,0));
		actors.add(new StairsDown(0,0,0));
	}

}
