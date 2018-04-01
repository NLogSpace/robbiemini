package de.leifaktor.robbiemini.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.viewport.Viewport;

import de.leifaktor.robbiemini.Episode;
import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.RoomManager;
import de.leifaktor.robbiemini.XYZPos;
import de.leifaktor.robbiemini.render.RoomRenderer;

public class EditorScreen implements Screen {
	
	ScreenManager sm;
	
	Viewport viewport;
	Camera camera;
	
	Episode episode;
	
	RoomManager roomManager;
	Room currentRoom;
	XYZPos currentRoomPosition;
	
	RoomRenderer roomRenderer;
	
	public EditorScreen(ScreenManager sm, Viewport viewport, Camera camera) {
		this.sm = sm;
		this.viewport = viewport;
		this.camera = camera;
		
		roomRenderer = new RoomRenderer();
		roomRenderer.setOffset(0, 0);
	}
	
	public void set(Episode episode, XYZPos roomPosition) {
		this.episode = episode;
		this.roomManager = episode.roomManager;
		this.currentRoomPosition = roomPosition;
		this.currentRoom = roomManager.getRoom(currentRoomPosition);		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		sm.batch.setProjectionMatrix(camera.combined);
		System.out.println(camera.position);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		sm.batch.begin();
		roomRenderer.render(sm.batch, currentRoom);
		sm.batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
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

}
