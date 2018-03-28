package de.leifaktor.robbiemini;

import java.util.HashMap;

public class GlobalVars {
	
	HashMap<String, Boolean> bools;
	
	public GlobalVars() {
		bools = new HashMap<String, Boolean>();
	}
	
	public void setBoolean(String key, boolean value) {
		bools.put(key,  value);
	}
	
	public boolean getBoolean(String key) {
		if (!bools.containsKey(key)) return false;
		return bools.get(key);
	}

}
