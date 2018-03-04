package de.leifaktor.robbiemini;

public class XYZPos {
    public int x;
    public int y;
    public int z;
    
    public XYZPos() {} // no-arg constructor for JSON
    
    public XYZPos(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof XYZPos) {
            XYZPos pos = (XYZPos) obj;
            return pos.x == x && pos.y == y && pos.z == z;
        }
        return false;
    }

    /**
     * Has to be implemented somehow, so identification in HashMaps work
     */
    
    @Override
    public int hashCode() {
        return z*928+y*185+x*177;
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + ", " + z + "]";
    }        

}
