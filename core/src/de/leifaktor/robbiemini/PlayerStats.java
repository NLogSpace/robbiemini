package de.leifaktor.robbiemini;

public class PlayerStats {
	
	public Inventory inventory;
	public int lives;
	public int munition;
	public int gold;
	
	public PlayerStats() {
		lives = 3;
		munition = 0;
		inventory = new Inventory();
	}

}
