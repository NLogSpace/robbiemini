package de.leifaktor.robbiemini;

public class Inventory {
	
	int acids;
	int gold;
	boolean[] keys;
	
	public Inventory() {
		keys = new boolean[16];
	}
	
	public int getAcids() {
		return acids;
	}
	
	public void addAcid() {
		acids++;
	}
	
	public void removeAcid() {
		acids--;
	}
	
	public int getGold() {
		return gold;
	}
	
	public void addGold() {
		gold++;
	}
	
	public void removeGold() {
		gold--;
	}
	
	public void addKey(int number) {
		keys[number] = true;
	}
	
	public boolean hasKey(int number) {
		return keys[number];
	}

}
