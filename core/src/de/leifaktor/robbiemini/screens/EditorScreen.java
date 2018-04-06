package de.leifaktor.robbiemini.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.viewport.Viewport;

import de.leifaktor.robbiemini.Episode;
import de.leifaktor.robbiemini.IO;
import de.leifaktor.robbiemini.RobbieMini;
import de.leifaktor.robbiemini.Room;
import de.leifaktor.robbiemini.RoomCreator;
import de.leifaktor.robbiemini.RoomLayer;
import de.leifaktor.robbiemini.RoomManager;
import de.leifaktor.robbiemini.XYPos;
import de.leifaktor.robbiemini.XYZPos;
import de.leifaktor.robbiemini.actor.Actor;
import de.leifaktor.robbiemini.render.ActorPaletteRenderer;
import de.leifaktor.robbiemini.render.EditorStatusBarRenderer;
import de.leifaktor.robbiemini.render.RoomRenderer;
import de.leifaktor.robbiemini.render.TilePaletteRenderer;
import de.leifaktor.robbiemini.tiles.Tile;

public class EditorScreen extends ScreenAdapter {

	ScreenManager sm;

	Viewport viewport;
	Camera camera;

	Episode episode;

	RoomManager roomManager;
	public Room currentRoom;
	public XYZPos currentRoomPosition;
	int currentLayer = 0;

	RoomRenderer roomRenderer;
	TilePaletteRenderer tilePaletteRenderer;
	ActorPaletteRenderer actorPaletteRenderer;
	EditorStatusBarRenderer statusBarRenderer;

	public State state;

	Tile selectedTile;
	Actor selectedActor;

	boolean drawTile;

	public String roomNameTyping;

	public enum State {
		DRAW,
		TILE_PALETTE,
		ACTOR_PALETTE,
		ENTER_ROOM_NAME,
		SET_START,
		CONFIRM_CREATE_LAYER;
	}

	public EditorScreen(ScreenManager sm, Viewport viewport, Camera camera) {
		this.sm = sm;
		this.viewport = viewport;
		this.camera = camera;

		roomRenderer = new RoomRenderer();
		roomRenderer.setOffset(0, 0);

		tilePaletteRenderer = new TilePaletteRenderer();
		tilePaletteRenderer.setOffset(0, 0);
		tilePaletteRenderer.setTilesPerRow(15);

		actorPaletteRenderer = new ActorPaletteRenderer();
		actorPaletteRenderer.setOffset(0, 0);
		actorPaletteRenderer.setTilesPerRow(15);

		statusBarRenderer = new EditorStatusBarRenderer();
		statusBarRenderer.setOffset(0, RobbieMini.getVirtualHeight()-RobbieMini.TILESIZE);

		state = State.DRAW;
	}

	public void set(Episode episode, XYZPos roomPosition) {
		this.episode = episode;
		this.roomManager = episode.roomManager;
		this.currentRoomPosition = roomPosition;
		this.currentRoom = roomManager.getRoom(currentRoomPosition);
		this.roomRenderer.setRoom(currentRoom);
	}

	public void setRoom(XYZPos roomPosition) {
		this.currentRoomPosition = roomPosition;
		this.currentRoom = roomManager.getRoom(currentRoomPosition);
		this.roomRenderer.setRoom(currentRoom);
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
		roomRenderer.render(sm.batch);
		statusBarRenderer.render(sm.batch, this);
		if (state == State.TILE_PALETTE) {
			tilePaletteRenderer.render(sm.batch);
		} else if (state == State.ACTOR_PALETTE) {
			actorPaletteRenderer.render(sm.batch);
		}
		sm.batch.end();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);		
	}

	class InputListener extends InputAdapter {

		@Override
		public boolean touchDown(int screenX, int screenY, int pointer, int button) {
			XYPos clickedTilePosition = roomRenderer.getPositionInRoom(screenX / RobbieMini.SCALE, (Gdx.graphics.getHeight() - screenY) / RobbieMini.SCALE, currentRoom);
			switch (state) {
			case DRAW:				
				if (button == Buttons.LEFT) {
					if (drawTile && selectedTile != null) {					
						currentRoom.setTile(clickedTilePosition.x, clickedTilePosition.y, currentLayer, selectedTile);
					}
					if (!drawTile && selectedActor != null) {
						Actor a = selectedActor.clone();
						a.setPosition(clickedTilePosition.x, clickedTilePosition.y, currentLayer);
						currentRoom.addActor(a);
					}
				} else {
					currentRoom.removeActorsAt(clickedTilePosition.x, clickedTilePosition.y, currentLayer);
				}
				break;
			case TILE_PALETTE:
				selectedTile = tilePaletteRenderer.getTileAt(screenX / RobbieMini.SCALE, (Gdx.graphics.getHeight() - screenY) / RobbieMini.SCALE);
				if (selectedTile != null) {
					state = State.DRAW;
					drawTile = true;
				}
				break;
			case ACTOR_PALETTE:
				selectedActor = actorPaletteRenderer.getActorAt(screenX / RobbieMini.SCALE, (Gdx.graphics.getHeight() - screenY) / RobbieMini.SCALE);
				if (selectedActor != null) {
					state = State.DRAW;
					drawTile = false;
				}
				break;
			case ENTER_ROOM_NAME:
				break;
			case SET_START:
				episode.roomManager.getRoom(episode.startingRoom).removePlayer();
				episode.player.setPosition(new XYZPos(clickedTilePosition.x, clickedTilePosition.y, currentLayer));
				episode.startingRoom = currentRoomPosition;
				currentRoom.putPlayer(episode.player, episode.player.x, episode.player.y, episode.player.z);
				state = State.DRAW;
				break;
			default:
				break;
			}
			return true;
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			XYPos clickedTilePosition = roomRenderer.getPositionInRoom(screenX / RobbieMini.SCALE, (Gdx.graphics.getHeight() - screenY) / RobbieMini.SCALE, currentRoom);
			if (clickedTilePosition != null && state == State.DRAW && drawTile && selectedTile != null) {					
				currentRoom.setTile(clickedTilePosition.x, clickedTilePosition.y, currentLayer, selectedTile);
			}
			return true;
		}

		@Override
		public boolean keyDown(int keycode) {
			switch (state) {
			case DRAW:
				switch (keycode) {
				case Keys.Y:
					state = State.TILE_PALETTE;
					break;
				case Keys.X:
					state = State.ACTOR_PALETTE;
					break;
				case Keys.P:
					sm.setGame();
					break;
				case Keys.Q:
					if (currentLayer < currentRoom.getNumberOfLayers() - 1) currentLayer++;
					else {
						state = State.CONFIRM_CREATE_LAYER;
					}
					roomRenderer.setRenderLayer(currentLayer);
					roomRenderer.highlightLayer(currentLayer);
					break;
				case Keys.A:
					if (currentLayer > 0) currentLayer--;
					roomRenderer.setRenderLayer(currentLayer);
					roomRenderer.highlightLayer(currentLayer);
					break;
				case Keys.S:
					IO.save(episode, "episode.rob");
					break;
				case Keys.F1:
					state = State.ENTER_ROOM_NAME;
					roomNameTyping = "";
					break;
				case Keys.F5:
					state = State.SET_START;
					break;
				case Keys.UP:
					setRoom(new XYZPos(currentRoomPosition.x, currentRoomPosition.y + 1, currentRoomPosition.z));
					break;
				case Keys.DOWN:
					setRoom(new XYZPos(currentRoomPosition.x, currentRoomPosition.y - 1, currentRoomPosition.z));
					break;
				case Keys.LEFT:
					setRoom(new XYZPos(currentRoomPosition.x - 1, currentRoomPosition.y, currentRoomPosition.z));
					break;
				case Keys.RIGHT:
					setRoom(new XYZPos(currentRoomPosition.x + 1, currentRoomPosition.y, currentRoomPosition.z));
					break;
				case Keys.PAGE_UP:
					setRoom(new XYZPos(currentRoomPosition.x, currentRoomPosition.y, currentRoomPosition.z + 1));
					break;
				case Keys.PAGE_DOWN:
					setRoom(new XYZPos(currentRoomPosition.x, currentRoomPosition.y, currentRoomPosition.z - 1));
					break;
				case Keys.ENTER:
					if (currentRoom == null) {
						Room room = RoomCreator.createEmptyRoom(RobbieMini.WIDTH, RobbieMini.HEIGHT);
						roomManager.setRoom(currentRoomPosition.z, currentRoomPosition.x, currentRoomPosition.y, room);
						setRoom(currentRoomPosition);
					}
					break;
				}
				break;
			case TILE_PALETTE:
				break;
			case ACTOR_PALETTE:
				break;
			case ENTER_ROOM_NAME:
				if (keycode == Keys.ENTER) {
					currentRoom.name = roomNameTyping;
					state = State.DRAW;
				}
				if (keycode == Keys.ESCAPE) {
					state = State.DRAW;
				}
				break;
			case CONFIRM_CREATE_LAYER:
				switch (keycode) {
				case Keys.ENTER:
					RoomLayer layer = RoomCreator.airLayer(RobbieMini.WIDTH, RobbieMini.HEIGHT);
					currentRoom.layers.add(layer);
					currentLayer++;
					state = State.DRAW;
					break;
				case Keys.ESCAPE:
					state = State.DRAW;
					break;
				}
				break;
			default:
				break;
			}
			return true;
		}

		@Override
		public boolean keyTyped(char character) {
			if (state == State.ENTER_ROOM_NAME) {
				roomNameTyping += character;
			}
			return true;
		}
	}

}
