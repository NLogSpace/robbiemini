package de.leifaktor.robbiemini.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import de.leifaktor.robbiemini.RobbieMini;
import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.render.RoomRenderer;

public class RoomTransitionScreen implements Screen {
	
	RobbieMini game;
	GameScreen parent;
	Room oldRoom;
	Room newRoom;
	int direction;	
	
	float duration = 0.34f;
	float screenTime;
	
	RoomRenderer renderer;
	
	public static final int WIDTH = 33;
	public static final int HEIGHT = 23;
	
	int newRoomXOffset;
	int newRoomYOffset;
	
	Texture title;
	
	public RoomTransitionScreen(RobbieMini game, GameScreen parent, Room oldRoom, Room newRoom, int direction) {
		this.game = game;
		this.parent = parent;
		this.oldRoom = oldRoom;
		this.newRoom = newRoom;
		this.direction = direction;
		screenTime = 0;
		
		renderer = new RoomRenderer();
		
		switch (direction) {
		case 0: newRoomXOffset = 0; newRoomYOffset = HEIGHT; break;
		case 1: newRoomXOffset = WIDTH; newRoomYOffset = 0; break;
		case 2: newRoomXOffset = 0; newRoomYOffset = -HEIGHT; break;
		case 3: newRoomXOffset = -WIDTH; newRoomYOffset = 0; break;
		}
		
		title = new Texture(Gdx.files.internal("title.png"));
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		float fraction = screenTime / duration;
		if (fraction >= 1) {
			fraction = 1;
			game.setScreen(parent);
		}
		screenTime += delta;
		float screenProgress = tanhInterpolation(fraction);
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();		
		renderer.setOffset(-screenProgress*newRoomXOffset, -screenProgress*newRoomYOffset);
		renderer.render(game.batch, oldRoom);
		renderer.setOffset((1-screenProgress)*newRoomXOffset, (1-screenProgress)*newRoomYOffset);
		renderer.render(game.batch, newRoom);
		game.batch.end();
	}
	
	private float tanhInterpolation(float fraction) {
		return (float) (0.5 * (Math.tanh(3*(2*fraction-1))+1));
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
