package de.leifaktor.robbiemini.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.viewport.Viewport;

import de.leifaktor.robbiemini.Episode;
import de.leifaktor.robbiemini.RobbieMini;
import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.RoomManager;
import de.leifaktor.robbiemini.XYPos;
import de.leifaktor.robbiemini.XYZPos;
import de.leifaktor.robbiemini.render.RoomRenderer;
import de.leifaktor.robbiemini.render.TilePaletteRenderer;
import de.leifaktor.robbiemini.screens.editor.Tiles;
import de.leifaktor.robbiemini.tiles.Tile;

public class EditorScreen implements Screen {
	
	ScreenManager sm;
	
	Viewport viewport;
	Camera camera;
	
	Episode episode;
	
	RoomManager roomManager;
	Room currentRoom;
	XYZPos currentRoomPosition;
	int currentLayer = 0;
	
	RoomRenderer roomRenderer;
	TilePaletteRenderer tilePaletteRenderer;
	
	State state;
	
	Tile selectedTile;
	
	enum State {
		DRAW,
		TILE_PALETTE
	}
	
	public EditorScreen(ScreenManager sm, Viewport viewport, Camera camera) {
		this.sm = sm;
		this.viewport = viewport;
		this.camera = camera;
		
		roomRenderer = new RoomRenderer();
		roomRenderer.setOffset(0, 0);
		
		Tiles.init();
		
		tilePaletteRenderer = new TilePaletteRenderer();
		tilePaletteRenderer.setOffset(0, 0);
		tilePaletteRenderer.setTilesPerRow(15);
		
		state = State.DRAW;
	}
	
	public void set(Episode episode, XYZPos roomPosition) {
		this.episode = episode;
		this.roomManager = episode.roomManager;
		this.currentRoomPosition = roomPosition;
		this.currentRoom = roomManager.getRoom(currentRoomPosition);		
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(new InputListener());		
	}

	@Override
	public void render(float delta) {
		sm.batch.setProjectionMatrix(camera.combined);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		sm.batch.begin();
		roomRenderer.render(sm.batch, currentRoom);
		if (state == State.TILE_PALETTE) {
			tilePaletteRenderer.render(sm.batch);
		}
		sm.batch.end();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	class InputListener extends InputAdapter {
		
		@Override
	    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
			System.out.println(screenY);
			switch (state) {
			case DRAW:
				if (selectedTile != null) {
					XYPos clickedTilePosition = roomRenderer.getPositionInRoom(screenX / RobbieMini.SCALE, (Gdx.graphics.getHeight() - screenY) / RobbieMini.SCALE, currentRoom);
					currentRoom.setTile(clickedTilePosition.x, clickedTilePosition.y, currentLayer, selectedTile);
				}
				break;
			case TILE_PALETTE:
				selectedTile = tilePaletteRenderer.getTileAt(screenX / RobbieMini.SCALE, (Gdx.graphics.getHeight() - screenY) / RobbieMini.SCALE);
				if (selectedTile != null) state = State.DRAW;
			}
	        return true;
	    }		
	    
	    @Override
		public boolean keyDown(int keycode) {
			System.out.println("keyDown");
	    	switch (state) {
			case DRAW:
				if (keycode == Keys.SPACE) state = State.TILE_PALETTE;
				break;
			case TILE_PALETTE:

			}
			return super.keyDown(keycode);
		}
	}

}
