package de.leifaktor.robbiemini;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import de.leifaktor.robbiemini.actor.Acid;
import de.leifaktor.robbiemini.actor.Actor;
import de.leifaktor.robbiemini.actor.Arrow;
import de.leifaktor.robbiemini.actor.Gold;
import de.leifaktor.robbiemini.actor.Key;
import de.leifaktor.robbiemini.actor.Player;
import de.leifaktor.robbiemini.actor.Robot;
import de.leifaktor.robbiemini.movement.FollowPlayerMovement;
import de.leifaktor.robbiemini.movement.KeyboardMovement;
import de.leifaktor.robbiemini.tiles.Door;
import de.leifaktor.robbiemini.tiles.EmptyTile;
import de.leifaktor.robbiemini.tiles.Ice;
import de.leifaktor.robbiemini.tiles.Tile;
import de.leifaktor.robbiemini.tiles.Wall;

public class RoomCreator {
	
	public static Random random = new Random();
	
	public static Room createWallRoom(int width, int height) {
		Random rand = new Random();
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
			int x = rand.nextInt(width);
			int y = rand.nextInt(height);
			int len = rand.nextInt(5)+5;
			for (int j = 0; j < len; j++) {
				map[y*width+x+j] = new Wall(); 
			}
		}
		for (int i = 0; i < 20; i++) {
			int x = rand.nextInt(width);
			int y = rand.nextInt(height-11);
			int len = rand.nextInt(5)+5;
			for (int j = 0; j < len; j++) {
				map[(y+j)*width+x] = new Wall(); 
			}
		}
		Player player = new Player(3,3);
		player.setMovingBehaviour(new KeyboardMovement());
		ArrayList<Actor> actors = new ArrayList<Actor>();
		actors.add(new Acid(6,9));
		
		for (int i = 0; i < 10; i++) {
			Robot robot = randomRobot();
			actors.add(robot);
		}
		
		Room room = new Room(width, height, map, actors);
		room.putPlayer(player, 3, 3);
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
			System.out.println(current.x + " " + current.y);
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
		Tile ice = new Ice();
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
		map[18*width+19] = new Door(0);
		map[20*width+9] = new Door(1);
		map[5*width+16] = new Door(2);
		
		for (int x = width/3; x < 2*width/3; x++) {
			for (int y = height/3; y < 2*height/3; y++) {
				map[y*width+x] = ice;
			}
		}
		
		Player player = new Player(3,3);
		player.setMovingBehaviour(new KeyboardMovement());
		ArrayList<Actor> actors = new ArrayList<Actor>();
		actors.add(new Acid(7,9));
		actors.add(new Acid(33,27));
		actors.add(new Acid(1,11));
		actors.add(new Key(11, 19, 0));
		actors.add(new Key(17, 11, 1));
		actors.add(new Key(31, 23, 2));
		actors.add(new Gold(21, 13));
		actors.add(new Gold(3, 23));
		actors.add(new Gold(23, 5));
		actors.add(new Arrow(17, 17, 2));
		
		for (int i = 0; i < 10; i++) {
			Robot robot = randomRobot();
			actors.add(robot);
		}
		
		Room room = new Room(width, height, map, actors);
		room.putPlayer(player, 3, 3);
		return room;		
	}
	
	public static Robot randomRobot() {
		int x = random.nextInt(40);
		int y = random.nextInt(30);
		float speed = random.nextFloat()*0.029f+0.011f;		
		int graphicType = random.nextInt(7);
		Robot robot = new Robot(x, y, speed, graphicType);
		robot.setMovingBehaviour(new FollowPlayerMovement());
		return robot;
	}

}

class Point {
	public Point(int x, int y) {this.x = x; this.y = y;}		
	int x; 
	int y;
}
