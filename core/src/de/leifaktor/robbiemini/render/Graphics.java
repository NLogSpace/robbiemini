package de.leifaktor.robbiemini.render;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Graphics {
	
	public static HashMap<String, TextureRegion> textures = new HashMap<String, TextureRegion>();
	public static HashMap<String, Animation<TextureRegion>> animations = new HashMap<String, Animation<TextureRegion>>();
	
	public static void loadGraphics() {
		Tileset tileset = new Tileset("tileset16.png", 16);
		
		// Tiles
		textures.put("empty_tile", tileset.getTile(0, 0));
		textures.put("wall_tile", tileset.getTile(1, 0));
		textures.put("dark_wall_tile", tileset.getTile(3, 0));
		textures.put("ice_tile", tileset.getTile(8, 0));
		for (int i = 0; i < 16; i++) textures.put("door_" + i, tileset.getTile(i, 3));

		// Items
		textures.put("acid", tileset.getTile(2, 10));
		for (int i = 0; i < 16; i++) textures.put("key_" + i, tileset.getTile(i, 4));	
		textures.put("life", tileset.getTile(7, 11));
		textures.put("magnet_positive", tileset.getTile(10, 9));
		textures.put("magnet_negative", tileset.getTile(11, 9));
		textures.put("blaumann", tileset.getTile(3, 10));

		// Player
		textures.put("player", tileset.getTile(0, 7));
		animations.put("player_walking", new Animation<TextureRegion>(0.12f,tileset.getTiles(1, 7, 2)));
		textures.put("player_blaumann", tileset.getTile(3, 7));
		animations.put("player_walking_blaumann", new Animation<TextureRegion>(0.12f,tileset.getTiles(4, 7, 2)));
		
		// Actor
		textures.put("gold", tileset.getTile(1, 10));
		for (int i = 0; i < 8; i++) textures.put("arrow_" + i, tileset.getTile(6+i, 10));
		textures.put("isolator", tileset.getTile(14, 10));
		textures.put("electric_fence", tileset.getTile(0, 10));
		textures.put("skull", tileset.getTile(9, 9));
		animations.put("dissolving_wall", new Animation<TextureRegion>(0.25f,tileset.getTiles(0, 1, 16)));
		for (int i = 0; i < 7; i++)	animations.put("robot_" + i, new Animation<TextureRegion>(0.6f,tileset.getTiles(2*i, 8, 2)));
		animations.put("explosion", new Animation<TextureRegion>(0.3f,tileset.getTiles(6, 7, 5)));
		
		// Status Bar
		textures.put("status_bar_outer_background", tileset.getTile(3, 13));
		textures.put("status_bar_inner_background", tileset.getTile(2, 13));
		textures.put("full_heart", tileset.getTile(0, 13));
		textures.put("empty_heart", tileset.getTile(1, 13));		
		
		// Inventory
		textures.put("inventory_outer_background", tileset.getTile(5, 13));
		textures.put("inventory_unselected_background", tileset.getTile(2, 13));
		textures.put("inventory_selected_background", tileset.getTile(4, 13));
	}


}
