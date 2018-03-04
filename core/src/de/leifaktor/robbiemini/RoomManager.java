package de.leifaktor.robbiemini;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

public class RoomManager {
    
    /**
     * Diese Abbildung ordnet jeder Position den Raum zu. Dabei steht die Z-Koordinate für den
     * Floor, X und Y für die Position innerhalb des Floors. Es gibt keine null-Einträge, d.h.
     * wenn man einen Raum auf null setzt, wird der entsprechende Eintrag aus der Map entfernt.
     */
    HashMap<XYZPos, Room> rooms;
    
    /**
     * Die Abbildungen minx, maxx, miny und maxy merken sich für jeden Floor, welches die größten
     * und kleinsten vorkommenden Koordinaten sind.
     */
    
    HashMap<Integer, Integer> minx;
    HashMap<Integer, Integer> maxx;
    HashMap<Integer, Integer> miny;
    HashMap<Integer, Integer> maxy;
    
    /**
     * Die Floors, die existieren
     */
    
    HashSet<Integer> floors;
    
    public RoomManager() {
        rooms = new HashMap<XYZPos, Room>();
        minx = new HashMap<Integer, Integer>();
        maxx = new HashMap<Integer, Integer>();
        miny = new HashMap<Integer, Integer>();
        maxy = new HashMap<Integer, Integer>();
        floors = new HashSet<Integer>();
    }
    
    /**
     * Setzt den Eintrag für den gegebenen Room an der gegebenen Position, wobei floor
     * die Z-Koordinate ist. Falls der übergebene Room null ist, wird kein Eintrag erzeugt, sondern
     * ggf. ein vorhandener an dieser Stelle gelöscht. In jedem Fall werden danach die Werte
     * in minx, maxx, miny und maxy aktualisiert.
     */
    
    public void setRoom(int floor, int x, int y, Room room) {
        if (room != null) {
            rooms.put(new XYZPos(x, y, floor), room);
            if (minx.get(floor) == null || minx.get(floor) > x) minx.put(floor, x);
            if (miny.get(floor) == null || miny.get(floor) > y) miny.put(floor, y);
            if (maxx.get(floor) == null || maxx.get(floor) < x) maxx.put(floor, x);
            if (maxy.get(floor) == null || maxy.get(floor) < y) maxy.put(floor, y);
            floors.add(floor);
        } else {
            rooms.remove(new XYZPos(x, y, floor));
            boolean firstRoom = true;
            int newminx=0, newmaxx=0, newminy=0, newmaxy=0;
            for (XYZPos pos: rooms.keySet()) {
                if (pos.z == floor) {
                    if (firstRoom) {
                        newminx = pos.x;
                        newmaxx = pos.x;
                        newminy = pos.y;
                        newmaxy = pos.y;
                        firstRoom = false;
                    } else {
                        if (pos.x < newminx) newminx = pos.x;
                        if (pos.x > newmaxx) newmaxx = pos.x;
                        if (pos.y < newminy) newminy = pos.y;
                        if (pos.y > newmaxy) newmaxy = pos.y;                        
                    }
                }
            }
            if (firstRoom == true) {
                minx.remove(floor);
                maxx.remove(floor);
                miny.remove(floor);
                maxy.remove(floor);
                floors.remove(floor);
            } else {
                minx.put(floor, newminx);
                maxx.put(floor, newmaxx);
                miny.put(floor, newminy);
                maxy.put(floor, newmaxy);
            }
        }
    }
    
    public boolean doesFloorExist(int floor) {
        return floors.contains(floor);
    }
    
    public boolean doAnyRoomsExist() {
        return !floors.isEmpty();
    }
    
    public int getMinX(int floor) {
        return minx.get(floor);
    }
    
    public int getMaxX(int floor) {
        return maxx.get(floor);
    }
    
    public int getMinY(int floor) {
        return miny.get(floor);
    }
    
    public int getMaxY(int floor) {
        return maxy.get(floor);
    }
    
    public int getWidth(int floor) {
        return maxx.get(floor) - minx.get(floor) + 1;
    }
    
    public int getHeight(int floor) {
        return maxy.get(floor) - miny.get(floor) + 1;
    }
    
    public Room getRoom(int floor, int x, int y) {
        return getRoom(new XYZPos(x, y, floor));
    }
    
    public Room getRoom(XYZPos roomPosition) {
        return rooms.get(roomPosition);
    }
    
    public int[] getFloorIndices() {
        int[] result = new int[floors.size()];
        int count = 0;
        for (Integer floor: floors) {
            result[count] = floor;
            count++;
        }
        return result;
    }
    
    public Iterator<Entry<XYZPos, Room>> getRoomIterator() {
        return rooms.entrySet().iterator();
    }

	public boolean doesRoomExist(XYZPos roomToCheckPosition) {		
		return getRoom(roomToCheckPosition) != null;
	}
    
}
