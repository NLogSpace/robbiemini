package de.leifaktor.robbiemini.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;

import de.leifaktor.robbiemini.RobbieMini;

public class TextboxRenderer {
	
	float xOffset;
	float yOffset;
	
	int height;
	int width;
	
	GlyphLayout glyphLayout;
	
	public TextboxRenderer() {
		glyphLayout = new GlyphLayout();
	}
	
	public void render(SpriteBatch batch, String text, boolean largeFont, boolean centered) {
		TextureRegion graphic;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (i == 0) {
					if (j == 0) {
						graphic = Graphics.textures.get("textbox_7");
					} else if (j < height-1) {
						graphic = Graphics.textures.get("textbox_4");
					} else {
						graphic = Graphics.textures.get("textbox_1");
					}
				} else if (i < width-1) {
					if (j == 0) {
						graphic = Graphics.textures.get("textbox_8");
					} else if (j < height-1) {
						graphic = Graphics.textures.get("textbox_5");
					} else {
						graphic = Graphics.textures.get("textbox_2");
					}
				} else {
					if (j == 0) {
						graphic = Graphics.textures.get("textbox_9");
					} else if (j < height-1) {
						graphic = Graphics.textures.get("textbox_6");
					} else {
						graphic = Graphics.textures.get("textbox_3");
					}
				}
				batch.draw(graphic, xOffset+i*RobbieMini.TILESIZE, yOffset+j*RobbieMini.TILESIZE);				
			}
		}
		int align = centered ? Align.center : Align.left;
		BitmapFont font;
		if (largeFont) font = Graphics.largeFont; else font = Graphics.smallFont;
		font.draw(batch, text, xOffset + RobbieMini.TILESIZE, yOffset+RobbieMini.TILESIZE*(height-1)-3, (width-2)*RobbieMini.TILESIZE, align, true);
	}

	public void setOffset(float x, float y) {
		this.xOffset = x;
		this.yOffset = y;
	}
	
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public float getHeight(String text) {
		glyphLayout.setText(Graphics.smallFont, text, Color.BLACK, (width-2)*RobbieMini.TILESIZE, Align.left, true);
		return glyphLayout.height;
	}

}
