package de.leifaktor.robbiemini;

public class Vec2 {

	public float x;
	public float y;
	
	public Vec2(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void add(Vec2 other) {
		this.x += other.x;
		this.y += other.y;
	}
	
	public void multiply(float scalar) {
		x *= scalar;
		y *= scalar;
	}
	
	public void normalize() {
		float length = getLength();
		x /= length;
		y /= length;
	}
	
	public float getLength() {
		return (float) Math.sqrt(x*x+y*y);
	}
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
}
