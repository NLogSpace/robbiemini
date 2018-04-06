package de.leifaktor.robbiemini.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.leifaktor.robbiemini.RobbieMini;
import de.leifaktor.robbiemini.XYZPos;
import de.leifaktor.robbiemini.screens.EditorScreen;
import de.leifaktor.robbiemini.screens.EditorScreen.State;

public class EditorStatusBarRenderer {
	
	float xOffset;
	float yOffset;
	
	public void setOffset(float x, float y) {
		this.xOffset = x;
		this.yOffset = y;
	}

	public void render(SpriteBatch batch, EditorScreen screen) {
		batch.draw(Graphics.textures.get("status_bar_inner_background"), xOffset, yOffset, Gdx.graphics.getWidth(), RobbieMini.TILESIZE);
		// Room Position
		XYZPos pos = screen.currentRoomPosition;
		
		// Room Name
		String roomNameToDraw = "";
		if (screen.currentRoom != null) {
			roomNameToDraw = screen.currentRoom.name;
		}		
		if (screen.state == State.ENTER_ROOM_NAME) roomNameToDraw = screen.roomNameTyping;
		Graphics.smallFont.draw(batch, "[" + pos.x + "/" + pos.y + "/" + pos.z + "] " + roomNameToDraw, xOffset+2, yOffset+5);
		if (screen.state == State.CONFIRM_CREATE_LAYER) Graphics.smallFont.draw(batch, "Create new Layer?", xOffset+180, yOffset+5);
	}
	
}
