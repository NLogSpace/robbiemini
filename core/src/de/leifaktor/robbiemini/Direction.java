package de.leifaktor.robbiemini;

public class Direction {
    
    /**
     * Directions:
     * 0 = NORDEN
     * 1 = OSTEN
     * 2 = SÜDEN
     * 3 = WESTEN
     * 4 = NORD-OST
     * 5 = SÜD-OST
     * 6 = SÜD-WEST
     * 7 = NORD-WEST
     */

    public static final int DIR_X[] = {0,1,0,-1,1,1,-1,-1};
    public static final int DIR_Y[] = {1,0,-1,0,1,-1,-1,1};
    
    // Wenn man in Richtung (x,y) geht, welche Richtung ist das?
    public static int coordinatesToDirection(int x, int y) {
        switch (x * 3 + y) {
        case 1: return 0;
        case 3: return 1;
        case -1: return 2;
        case -3: return 3;
        case 4: return 4;
        case 2: return 5;
        case -4: return 6;
        case -2: return 7;
        default: return -1;
        }
    }
    
    public static Vec2 directionToVector(int direction) {
    	Vec2 result = new Vec2(DIR_X[direction], DIR_Y[direction]);    
    	result.normalize();
    	return result;
    }
    
    public static int roundAngleToDirection(double angle) {
    	if (angle < -7*Math.PI/8) return 3;
    	if (angle < -5*Math.PI/8) return 6;
    	if (angle < -3*Math.PI/8) return 2;
    	if (angle < -1*Math.PI/8) return 5;
    	if (angle < 1*Math.PI/8) return 1;
    	if (angle < 3*Math.PI/8) return 4;
    	if (angle < 5*Math.PI/8) return 0;
    	if (angle < 7*Math.PI/8) return 7;
    	else return 3;
    }

	public static int oppositeDir(int dir) {
		if (dir < 0) return -1;
		if (dir < 4) return (dir+2)%4;
		else return (dir+2)%4 + 4;
	}

	public static boolean isOpposite(int dir1, int dir2) {
		return dir1 == oppositeDir(dir2);
	}
	
	public static boolean isLeftOf(int dir1, int dir2) {
		if (dir1 < 0 || dir2 < 0) return false;
		if (dir2 == 0) return dir1 == 3 || dir1 == 6 || dir1 == 7;
		if (dir2 == 1) return dir1 == 0 || dir1 == 7 || dir1 == 4;
		if (dir2 == 2) return dir1 == 1 || dir1 == 4 || dir1 == 5;
		if (dir2 == 3) return dir1 == 2 || dir1 == 5 || dir1 == 6;
		if (dir2 == 4) return dir1 == 0 || dir1 == 3 || dir1 == 7;
		if (dir2 == 5) return dir1 == 1 || dir1 == 0 || dir1 == 4;
		if (dir2 == 6) return dir1 == 2 || dir1 == 1 || dir1 == 5;
		if (dir2 == 7) return dir1 == 3 || dir1 == 2 || dir1 == 6;
		return false;

	}

	public static int getNextDirCounterClockwise(int dir) {
		switch (dir) {
		case 0: return 7;
		case 1: return 4;
		case 2: return 5;
		case 3: return 6;
		case 4: return 0;
		case 5: return 1;
		case 6: return 2;
		case 7: return 3;
		}
		return -1;
	}
	
	public static int getNextDirClockwise(int dir) {
		switch (dir) {
		case 0: return 4;
		case 1: return 5;
		case 2: return 6;
		case 3: return 7;
		case 4: return 1;
		case 5: return 2;
		case 6: return 3;
		case 7: return 0;
		}
		return -1;
	}
    
}
