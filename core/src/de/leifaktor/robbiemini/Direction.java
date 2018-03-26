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
    
}
