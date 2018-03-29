package de.leifaktor.robbiemini;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import de.leifaktor.robbiemini.actor.Actor;
import de.leifaktor.robbiemini.actor.Arrow;
import de.leifaktor.robbiemini.actor.BulletStack;
import de.leifaktor.robbiemini.actor.ElectricFence;
import de.leifaktor.robbiemini.actor.Gold;
import de.leifaktor.robbiemini.actor.Isolator;
import de.leifaktor.robbiemini.actor.ItemActor;
import de.leifaktor.robbiemini.actor.Robot;
import de.leifaktor.robbiemini.actor.Schalter;
import de.leifaktor.robbiemini.actor.Skull;
import de.leifaktor.robbiemini.actor.Sperre;
import de.leifaktor.robbiemini.actor.Teleporter;
import de.leifaktor.robbiemini.condition.Condition;
import de.leifaktor.robbiemini.condition.GlobalBooleanCondition;
import de.leifaktor.robbiemini.condition.IsTermZeroCondition;
import de.leifaktor.robbiemini.condition.RobotsAliveTerm;
import de.leifaktor.robbiemini.items.Acid;
import de.leifaktor.robbiemini.items.Blaumann;
import de.leifaktor.robbiemini.items.Key;
import de.leifaktor.robbiemini.items.Life;
import de.leifaktor.robbiemini.items.Magnet;
import de.leifaktor.robbiemini.items.Notiz;
import de.leifaktor.robbiemini.items.Schleuder;
import de.leifaktor.robbiemini.tiles.Air;
import de.leifaktor.robbiemini.tiles.Door;
import de.leifaktor.robbiemini.tiles.EmptyTile;
import de.leifaktor.robbiemini.tiles.Tile;
import de.leifaktor.robbiemini.tiles.Wall;

public class RoomCreator {
	
	public static Random random = new Random();
	
	public static Room createEmptyRoom(int width, int height) {
		Tile wall = new Wall();
		Tile empty = new EmptyTile();
		Tile[] map = new Tile[width*height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (x == 0 || x == width-1 || y == 0 || y == height-1) map[y*width+x] = wall;
				else map[y*width+x] = empty;
			}
		}
		map[5*width+0] = empty;
		map[6*width+0] = empty;
		map[7*width+0] = empty;
		map[5*width+width-1] = empty;
		map[6*width+width-1] = empty;
		map[7*width+width-1] = empty;
		map[5] = empty;
		map[6] = empty;
		map[7] = empty;
		map[(height-1)*width+5] = empty;
		map[(height-1)*width+6] = empty;
		map[(height-1)*width+7] = empty;
		
		RoomLayer layer = new RoomLayer(width, height, map);		
		ArrayList<RoomLayer> layers = new ArrayList<RoomLayer>();
		layers.add(layer);
		layers.add(someWalls(width,height));
		
		ArrayList<Actor> actors = new ArrayList<Actor>();
		Condition killedAllRobots = new IsTermZeroCondition(new RobotsAliveTerm());
		actors.add(new Sperre(5, 0, 0, killedAllRobots, false, false));
		actors.add(new Sperre(6, 0, 0, killedAllRobots, false, false));
		actors.add(new Sperre(7, 0, 0, killedAllRobots, false, false));
		actors.add(new Sperre(5, height-1, 0, killedAllRobots, false, false));
		actors.add(new Sperre(6, height-1, 0, killedAllRobots, false, false));
		actors.add(new Sperre(7, height-1, 0, killedAllRobots, false, false));
		actors.add(new Sperre(0, 5, 0, killedAllRobots, true, false));
		actors.add(new Sperre(0, 6, 0, killedAllRobots, true, false));
		actors.add(new Sperre(0, 7, 0, killedAllRobots, true, false));
		actors.add(new Sperre(width-1, 5, 0, killedAllRobots, true, false));
		actors.add(new Sperre(width-1, 6, 0, killedAllRobots, true, false));
		actors.add(new Sperre(width-1, 7, 0, killedAllRobots, true, false));
		
		Room room = new Room(width, height, layers, actors);
		return room;
	}
	
	public static Room createWallRoom(int width, int height) {
		Tile wall = new Wall();
		Tile empty = new EmptyTile();
		Tile[] map = new Tile[width*height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (x == 0 || x == width-1 || y == 0 || y == height-1) map[y*width+x] = wall;
				else map[y*width+x] = empty;
			}
		}
		for (int i = 0; i < 20; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int len = random.nextInt(5)+5;
			for (int j = 0; j < len; j++) {
				if (y*width+x+j < width*height) map[y*width+x+j] = wall; 
			}
		}
		for (int i = 0; i < 20; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height-11);
			int len = random.nextInt(5)+5;
			for (int j = 0; j < len; j++) {
				if ((y+j)*width+x < width*height) map[(y+j)*width+x] = wall; 
			}
		}
		
		RoomLayer layer = new RoomLayer(width, height, map);		
		ArrayList<RoomLayer> layers = new ArrayList<RoomLayer>();
		layers.add(layer);
		
		ArrayList<Actor> actors = new ArrayList<Actor>();
		actors.add(new ItemActor(6, 9, 0, new Acid()));
		
		Room room = new Room(width, height, layers, actors);
		addRandomRobots(room,10);
		return room;
	}
	
	public static Room createMazeRoom(int width, int height) {
		int mazewidth = (width-1)/2;
		int mazeheight = (height-1)/2;
		boolean[] vertical = new boolean[(mazewidth+1) * (mazeheight+1)];
		boolean[] horizontal = new boolean[(mazewidth+1) * (mazeheight+1)];
		boolean[] visited = new boolean[mazewidth * mazeheight];
		Point current = new Point(0,0);
		int numVisit = 1;
		visited[0] = true;
		Stack<Point> stack = new Stack<Point>();
		stack.push(current);
		while (numVisit < mazewidth * mazeheight) {
			ArrayList<Point> unvisitedNeighbors = new ArrayList<Point>();
			current = stack.peek();
			int x; int y; 
			x = current.x; y = current.y + 1;
			if (y < mazeheight && !visited[mazewidth*y+x]) unvisitedNeighbors.add(new Point(x,y));
			x = current.x; y = current.y - 1;
			if (y >= 0 && !visited[mazewidth*y+x]) unvisitedNeighbors.add(new Point(x,y));
			x = current.x + 1; y = current.y;
			if (x < mazewidth && !visited[mazewidth*y+x]) unvisitedNeighbors.add(new Point(x,y));
			x = current.x - 1; y = current.y;
			if (x >= 0 && !visited[mazewidth*y+x]) unvisitedNeighbors.add(new Point(x,y));
			if (unvisitedNeighbors.size() > 0) {
				numVisit++;
				Point choice = unvisitedNeighbors.get(random.nextInt(unvisitedNeighbors.size()));
				int dx = choice.x - current.x;
				int dy = choice.y - current.y;
				if (dx == 1) {
					horizontal[choice.y*(mazewidth+1) + choice.x] = true;
				} else if (dx == -1) {
					horizontal[current.y*(mazewidth+1) + current.x] = true;
				} else if (dy == 1) {
					vertical[choice.y*(mazewidth+1) + choice.x] = true;
				} else if (dy == -1) {
					vertical[current.y*(mazewidth+1) + current.x] = true;
				}
				stack.push(choice);
				visited[choice.y*mazewidth+choice.x] = true;
			} else {
				stack.pop();
			}
		}
		Tile wall = new Wall();
		Tile empty = new EmptyTile();
		Tile[] map = new Tile[width*height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				map[y*width+x] = empty;
			}
		}		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (x == 0 || x == width-1 || y == 0 || y == height-1) map[y*width+x] = wall;
				else if ((x%2==0) && (y%2 == 0)) map[y*width+x] = wall;
			}
		}
		for (int i = 0; i < mazewidth; i++) {
			for (int j = 0; j < mazeheight; j++) {
				if (i < mazewidth && !horizontal[j*(mazewidth+1)+i]) {
					map[(2*j+1)*width + 2*i] = wall;
				}
				if (j < mazeheight && !vertical[j*(mazewidth+1)+i]) {
					map[2*j*width + 2*i+1] = wall;
				}
			}
		}
		map[random.nextInt(height/2)*2*width+random.nextInt(width/2)*2+1] = new Door(0);
		map[random.nextInt(height/2)*2*width+random.nextInt(width/2)*2+1] = new Door(1);
		map[random.nextInt(height/2)*2*width+random.nextInt(width/2)*2+1] = new Door(2);
		
		for (int x = width/3; x < 2*width/3; x++) {
			for (int y = height/3; y < 2*height/3; y++) {
				map[y*width+x] = empty;
			}
		}
		
		RoomLayer layer = new RoomLayer(width, height, map);		
		ArrayList<RoomLayer> layers = new ArrayList<RoomLayer>();
		layers.add(layer);
		
		ArrayList<Actor> actors = new ArrayList<Actor>();
		actors.add(new ItemActor(11, 19, 0, new Key(0)));
		actors.add(new ItemActor(17, 11, 0, new Key(1)));
		actors.add(new ItemActor(31, 23, 0, new Key(2)));
		actors.add(new Gold(21, 13, 0));
		actors.add(new Gold(3, 23, 0));
		actors.add(new Gold(23, 5, 0));
		actors.add(new Arrow(17, 17, 0, 2));
		actors.add(new Isolator(5, 5, 0));
		actors.add(new ElectricFence(5,6, 0));
		actors.add(new ElectricFence(5,7, 0));
		actors.add(new Isolator(7,5, 0));
		actors.add(new ElectricFence(7,6, 0));
		actors.add(new ElectricFence(7,7, 0));
		actors.add(new Isolator(9,5, 0));
		actors.add(new ElectricFence(9,6, 0));
		actors.add(new ElectricFence(9,7, 0));
		
		Room room = new Room(width, height, layers, actors);
		
		addRandomActors(new ItemActor(0, 0, 0, new Acid()), room, 5);		
		addRandomRobots(room, 5);
		
		return room;		
	}
	
	public static Room createShiftRoom(int width, int height) {
		Room room = createEmptyRoom(width, height);		

		Isolator isolator = new Isolator(0,0, 0);
		addRandomActors(isolator, room, 100);
		
		ElectricFence electricFence = new ElectricFence(0,0, 0);
		addRandomActors(electricFence, room, 10);
		
		addRandomActors(new ItemActor(0,0, 0,new Acid()), room, 3);
		addRandomActors(new ItemActor(0,0, 0,new Life()), room, 2);
		
		addRandomActors(new ItemActor(0,0, 0,new Magnet(true)), room, 2);
		addRandomActors(new ItemActor(0,0, 0,new Magnet(false)), room, 2);
		
		addRandomActors(new ItemActor(0,0, 0,new Blaumann()), room, 2);
		
		addRandomActors(new Gold(0,0, 0), room, 10);
		
		addRandomActors(new BulletStack(0,0, 0, random.nextInt(6) + 1), room, 3);
		addRandomActors(new ItemActor(0,0, 0,new Schleuder()), room, 1);
		
		Notiz notiz = new Notiz("Na sieh' mal einer an. Wenn das keine Notiz ist!");
		room.actors.add(new ItemActor(18, 20, 0, notiz));
		
		Teleporter teleporter = new Teleporter(20,10, 0, new GlobalBooleanCondition("schalter"));
		teleporter.setDestination(new XYZPos(2,1,1), new XYZPos(10,10,0));
		room.actors.add(teleporter);		
		
		room.actors.add(new Schalter(1, 10, 0, true, false, "schalter"));
		
		Skull skull = new Skull(0,0, 0);
		addRandomActors(skull, room, 5);
		
		for (int i = 0; i < 10; i++) {
			Arrow arrow = new Arrow(0,0, 0,Util.random.nextInt(4));
			addRandomActors(arrow, room, 1);
		}
		
		addRandomRobots(room, 5);
		
		return room;
	}
	
	public static Room createTitleMenuRoom(int width, int height) {
		Tile wall = new Wall();
		Tile empty = new EmptyTile();
		Tile[] map = new Tile[width*height];
		ArrayList<Actor> actors = new ArrayList<Actor>();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (x < 5 || x >= width-5 || y < 5 || y >= height-5) map[y*width+x] = wall;
				else {
					map[y*width+x] = empty;
					if (x == 5 || x == width-6 || y == 5 || y == height-6) actors.add(new ElectricFence(x, y, 0));
				}				
			}
		}
		
		RoomLayer layer = new RoomLayer(width, height, map);		
		ArrayList<RoomLayer> layers = new ArrayList<RoomLayer>();
		layers.add(layer);
		
		actors.add(new Gold(7, 16, 0));
		actors.add(new Arrow(7, 15, 0, 2));
		actors.add(new ItemActor(7, 14, 0, new Key(1)));
		actors.add(new ItemActor(8, 16, 0, new Blaumann()));
		actors.add(new ItemActor(8, 15, 0, new Magnet(true)));
		actors.add(new ItemActor(8, 14, 0, new Life()));
		
		actors.add(new Isolator(26, 16, 0));
		actors.add(new Skull(26, 15, 0));
		actors.add(new ItemActor(26, 14, 0, new Schleuder()));
		actors.add(new ItemActor(27, 16, 0, new Notiz("Wie hast Du es denn bitte geschafft, die Notiz auf dem Titelbilschirm einzusammeln?")));
		actors.add(new ItemActor(27, 15, 0, new Acid()));
		actors.add(new BulletStack(27, 14, 0, 6));
		
		Room room = new Room(width, height, layers, actors);
		return room;
	}
	
	public static Room createMagneticRoom(int width, int height) {
		Room room = createEmptyRoom(width, height);
		addRandomActors(new ItemActor(0,0,0,new Magnet(true)), room, 10);
		addRandomActors(new ItemActor(0,0,0,new Magnet(false)), room, 10);
		
		for (int i = 0; i < 300; i++) {
			Arrow arrow = new Arrow(0,0,0,Util.random.nextInt(4));
			addRandomActors(arrow, room, 1);
		}
		
		Notiz notiz = new Notiz("In diesem Raum gibt es echt viele Pfeile! Da kann man vielleicht mal die Magneten ausprobieren, die sollen ja bekanntlich dabei helfen, Pfeile umzudrehen. Ich schreibe hier jetzt einfach einen extrem langen Text in diese Notiz, um zu testen, ob die Zeilenumbrüche funktionieren.");
		room.addActor(new ItemActor(27, 7, 0, notiz));
		
		return room;
	}
	
	public static void addRandomRobots(Room room, int number) {
		for (int i = 0; i < number; i++) {
			Robot robot = randomRobot(room.width, room.height);
			room.actors.add(robot);
		}
	}
	
	public static void addRandomActors(Actor prototype, Room room, int number) {
		int width = room.width;
		int height = room.height;
		while (number > 0) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			if (room.getTile(x, y, 0) instanceof EmptyTile) {
				if (!room.hasAnyActorsAt(x, y, 0)) {
					Actor actor = prototype.clone();
					actor.setPosition(x, y, 0);
					room.actors.add(actor);
					number--;
				}
			}
		}
	}
	
	public static Robot randomRobot(int width, int height) {
		int x = random.nextInt(width);
		int y = random.nextInt(height);
		float speed = random.nextFloat()*0.032f+0.006f;		
		int graphicType = random.nextInt(7);
		Robot robot = new Robot(x, y, 0, speed, graphicType);
		return robot;
	}
	
	public static RoomLayer someWalls(int width, int height) {
		Tile[] map = new Tile[width*height];
		Tile air = new Air();
		Tile wall = new Wall();
		for (int i = 0; i < map.length; i++) {
			if (Util.random.nextFloat() < 0.1f) map[i] = wall; else map[i] = air;
		}
		RoomLayer layer = new RoomLayer(width, height, map);
		return layer;
	}

}

class Point {
	public Point(int x, int y) {this.x = x; this.y = y;}		
	int x; 
	int y;
}
