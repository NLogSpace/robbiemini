package de.leifaktor.robbiemini;

import de.leifaktor.robbiemini.actor.Actor;
import de.leifaktor.robbiemini.actor.ItemActor;
import de.leifaktor.robbiemini.actor.Magnetic;

public class MagneticField {

	int width;
	int height;
	float[] field;
	
	public MagneticField() {} // no-arg constructor for JSON
	
	public MagneticField(Room room) {
		this.width = room.width;
		this.height = room.height;
		this.field = new float[width*height];
	}
	
	public void update(Room room) {
		for (int i = 0; i < field.length; i++) field[i] = 0;
		for (Actor actor: room.actors) {
			float polarizationOfThisActor = 0;
			if (actor instanceof Magnetic) {
				polarizationOfThisActor = ((Magnetic) actor).getPolarization();
			} else if (actor instanceof ItemActor) {
				if (((ItemActor)actor).getItem() instanceof Magnetic) {
					polarizationOfThisActor = ((Magnetic) ((ItemActor) actor).getItem()).getPolarization();
				}
			}
			if (polarizationOfThisActor != 0) {
				for (int x = -20; x <= 20; x++) {
					for (int y = -20; y <= 20; y++) {
						if (room.isInBounds(actor.x+x, actor.y+y)) {
							field[width*(actor.y+y) + actor.x+x] += polarizationOfThisActor*calcPolarization((float) Math.sqrt(x*x+y*y));
						}
					}
				}
			}
		}
	}
	
	private float calcPolarization(float distance) {
		return 50/(distance+1)/(distance+1);
	}
	
	public Vec2 getGradientAt(int x, int y, Room room) {
		Vec2 result = new Vec2(0,0);
		int numberOfVectors = 0;
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if ((i!=0 || j!=0) && room.isInBounds(x+i, y+j)) {
					numberOfVectors++;
					Vec2 summand = new Vec2(i,j);
					summand.normalize();
					summand.multiply(field[(y+j)*width + x+i]);
					result.add(summand);
				}
			}
		}
		result.multiply(1 / (float) numberOfVectors);
		return result;
	}
	
	@Override
	public String toString() {
		String result = "";
		for (int y = height-1; y >= 0; y--) {
			for (int x = 0; x < width; x++) {
				result += field[width*y+x] + " ";
			}
			result += "\n";
		}
		return result;
	}
	
}
