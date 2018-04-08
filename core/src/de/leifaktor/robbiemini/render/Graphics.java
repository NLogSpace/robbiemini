package de.leifaktor.robbiemini.render;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Graphics {
	
	public static HashMap<String, TextureRegion> textures = new HashMap<String, TextureRegion>();
	public static HashMap<String, Animation<TextureRegion>> animations = new HashMap<String, Animation<TextureRegion>>();
	
	public static BitmapFont smallFont;
	public static BitmapFont largeFont;
	
	public static void loadGraphics() {
		smallFont = new BitmapFont(Gdx.files.internal("small.fnt"));
		smallFont.setColor(Color.BLACK);
		largeFont = new BitmapFont(Gdx.files.internal("large.fnt"));
		largeFont.setColor(Color.BLACK);
		
		Tileset tileset = new Tileset("tileset16.png", 16);
		
		// Tiles
		textures.put("empty_tile", tileset.getTile(0, 0));
		textures.put("wall_tile", tileset.getTile(1, 0));
		textures.put("dark_wall_tile", tileset.getTile(3, 0));
		textures.put("glass_tile", tileset.getTile(4, 0));
		textures.put("ice_tile", tileset.getTile(5, 0));
		for (int i = 0; i < 16; i++) textures.put("door_" + i, tileset.getTile(i, 3));
		textures.put("bridge_left", tileset.getTile(2, 12));
		textures.put("bridge_lr", tileset.getTile(3, 12));
		textures.put("bridge_right", tileset.getTile(4, 12));
		textures.put("bridge_up", tileset.getTile(5, 12));
		textures.put("bridge_ud", tileset.getTile(6, 12));
		textures.put("bridge_down", tileset.getTile(7, 12));
		textures.put("mountain_1", tileset.getTile(6, 2));
		textures.put("mountain_2", tileset.getTile(7, 2));
		textures.put("mountain_3", tileset.getTile(8, 2));
		textures.put("mountain_4", tileset.getTile(9, 2));
		textures.put("grass", tileset.getTile(8, 0));
		textures.put("water_normal", tileset.getTile(0,6));
		animations.put("water_right", new Animation<TextureRegion>(0.18f, tileset.getTiles(0, 6, 8)));
		Animation<TextureRegion> anim = new Animation<TextureRegion>(0.18f, tileset.getTiles(0, 6, 8));
		anim.setPlayMode(PlayMode.LOOP_REVERSED);
		animations.put("water_left", (anim));
		animations.put("water_down", new Animation<TextureRegion>(0.18f, tileset.getTiles(8, 6, 8)));
		anim = new Animation<TextureRegion>(0.18f, tileset.getTiles(8, 6, 8));
		anim.setPlayMode(PlayMode.LOOP_REVERSED);
		animations.put("water_up", (anim));
		animations.put("water_dr", new Animation<TextureRegion>(0.18f, tileset.getTiles(0, 7, 8)));
		anim = new Animation<TextureRegion>(0.18f, tileset.getTiles(0, 7, 8));
		anim.setPlayMode(PlayMode.LOOP_REVERSED);
		animations.put("water_ul", (anim));
		animations.put("water_dl", new Animation<TextureRegion>(0.18f, tileset.getTiles(8, 7, 8)));
		anim = new Animation<TextureRegion>(0.18f, tileset.getTiles(8, 7, 8));
		anim.setPlayMode(PlayMode.LOOP_REVERSED);
		animations.put("water_ur", (anim));

		// Items
		textures.put("acid", tileset.getTile(2, 10));
		for (int i = 0; i < 16; i++) textures.put("key_" + i, tileset.getTile(i, 4));	
		textures.put("life", tileset.getTile(7, 11));
		textures.put("magnet_positive", tileset.getTile(9, 11));
		textures.put("magnet_negative", tileset.getTile(10, 11));
		textures.put("blaumann", tileset.getTile(3, 10));
		textures.put("notiz", tileset.getTile(11, 11));
		for (int i = 1; i <= 6; i++) textures.put("bullets_" + i, tileset.getTile(9+i, 2));
		textures.put("schleuder", tileset.getTile(4, 10));
		textures.put("ice_skates", tileset.getTile(6, 11));
		textures.put("flossen", tileset.getTile(12, 11));

		// Player
		textures.put("player", tileset.getTile(0, 9));
		animations.put("player_walking", new Animation<TextureRegion>(0.12f,tileset.getTiles(1, 9, 2)));
		textures.put("player_blaumann", tileset.getTile(3, 9));
		animations.put("player_walking_blaumann", new Animation<TextureRegion>(0.12f,tileset.getTiles(4, 9, 2)));
		
		// Actor
		textures.put("gold", tileset.getTile(1, 10));
		for (int i = 0; i < 8; i++) textures.put("arrow_" + i, tileset.getTile(6+i, 10));
		textures.put("isolator", tileset.getTile(14, 10));
		textures.put("electric_fence", tileset.getTile(0, 10));
		textures.put("skull", tileset.getTile(8, 11));
		animations.put("dissolving_wall", new Animation<TextureRegion>(0.25f,tileset.getTiles(0, 1, 16)));
		for (int i = 0; i < 7; i++)	animations.put("robot_" + i, new Animation<TextureRegion>(0.6f,tileset.getTiles(2*i, 8, 2)));
		animations.put("explosion", new Animation<TextureRegion>(0.3f,tileset.getTiles(6, 9, 6)));
		textures.put("flying_bullet", tileset.getTile(10, 2));
		animations.put("teleport", new Animation<TextureRegion>(0.25f, tileset.getTiles(11, 0, 4)));
		textures.put("teleport_off", tileset.getTile(15, 0));
		textures.put("schalter_left_on", tileset.getTile(12, 9));
		textures.put("schalter_left_off", tileset.getTile(13, 9));
		textures.put("schalter_right_off", tileset.getTile(14, 9));
		textures.put("schalter_right_on", tileset.getTile(15, 9));
		textures.put("sperre_closed", tileset.getTile(2, 11));
		textures.put("sperre_lr", tileset.getTile(3, 11));
		textures.put("sperre_ud", tileset.getTile(4, 11));
		textures.put("stairs_up", tileset.getTile(6, 0));
		textures.put("stairs_down", tileset.getTile(7, 0));
		textures.put("dark_wall_tile_sw", tileset.getTile(0, 2));
		textures.put("dark_wall_tile_se", tileset.getTile(1, 2));
		textures.put("dark_wall_tile_ne", tileset.getTile(2, 2));
		textures.put("dark_wall_tile_nw", tileset.getTile(3, 2));
		for (int i = 1; i <= 16; i++) textures.put("half_mountain_" + i, tileset.getTile(i, 5));
		
		// Status Bar
		textures.put("status_bar_outer_background", tileset.getTile(3, 13));
		textures.put("status_bar_inner_background", tileset.getTile(2, 13));
		textures.put("full_heart", tileset.getTile(0, 13));
		textures.put("empty_heart", tileset.getTile(1, 13));		
		
		// Inventory
		textures.put("inventory_outer_background", tileset.getTile(5, 13));
		textures.put("inventory_unselected_background", tileset.getTile(2, 13));
		textures.put("inventory_selected_background", tileset.getTile(4, 13));
		
		// Textbox
		textures.put("textbox_1", tileset.getTile(13, 13));
		textures.put("textbox_2", tileset.getTile(14, 13));
		textures.put("textbox_3", tileset.getTile(15, 13));
		textures.put("textbox_4", tileset.getTile(13, 14));
		textures.put("textbox_5", tileset.getTile(14, 14));
		textures.put("textbox_6", tileset.getTile(15, 14));
		textures.put("textbox_7", tileset.getTile(13, 15));
		textures.put("textbox_8", tileset.getTile(14, 15));
		textures.put("textbox_9", tileset.getTile(15, 15));
		
		// Editor
		textures.put("palette_background", tileset.getTile(6,13));
		textures.put("brush_1", tileset.getTile(7, 13));
		textures.put("brush_2", tileset.getTile(8, 13));
		textures.put("brush_3", tileset.getTile(9, 13));
	}


}
