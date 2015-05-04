package edu.Moravian.View;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import edu.Moravian.Entity.Agent;
import edu.Moravian.Entity.Entity;
import edu.Moravian.Entity.Bullet;
import edu.Moravian.Game.Game;
import edu.Moravian.Math.CoordinateTranslator;
import edu.Moravian.Math.Point2D;

public class SpriteRenderer {
	
	private Image spriteSheetImage = null;
	private SpriteSheet spriteSheet;
	private Animation vegetaUp, vegetaDown, vegetaLeft, vegetaRight;
	private int spriteWidth;
	private int spriteHeight;
	private float spriteSheetWidth;
	private float spriteSheetHeight;
	private int spritesPerRow = 6;
	private int spritesPerColumn = 4;
	private int duration = 100; // Time to display each sprite

	private CoordinateTranslator coordTran;
	
	public SpriteRenderer() throws SlickException {
		
		spriteSheetImage = new Image("res/dbzcharacters.png");
		spriteSheetWidth = spriteSheetImage.getWidth();
	    spriteSheetHeight = spriteSheetImage.getHeight();
	    // Get width of sprite based on width of the sheet and how many sprites are in it
	    spriteWidth = (int)(spriteSheetWidth/spritesPerRow);
	    // Get height similarly
	    spriteHeight = (int)(spriteSheetHeight/spritesPerColumn);
	    // Now create a SpriteSheet objecoordTran with the new SpriteSheet
	    spriteSheet = new SpriteSheet(spriteSheetImage, spriteWidth, spriteHeight);
	    // SpriteSheet, Start Column, Start Row, End Column, End Row, Scan Horizontally, How long per Image, Continually Cycle 
	    vegetaUp    = new Animation(spriteSheet, 3, 0, 5, 0, true, duration, true);
	    vegetaRight = new Animation(spriteSheet, 3, 1, 5, 1, true, duration, true);
	    vegetaDown  = new Animation(spriteSheet, 3, 2, 5, 2, true, duration, true);
	    vegetaLeft  = new Animation(spriteSheet, 3, 3, 5, 3, true, duration, true);
	    
	    coordTran = Game.getInstance().getCT();
	}
	
	public void render(Entity e, Point2D location) {
		if (e instanceof Agent) {
			renderAgent((Agent)e, location);
		}
		else {
			throw new RuntimeException("Could not render");
		}	
	}
	
	private void renderAgent(Agent agent, Point2D location) {
		if (agent.getState() == "up") {
			vegetaUp.draw((float)(coordTran.worldToScreen(location).x - (int)(spriteWidth*0.5)), (float)(coordTran.worldToScreen(location).y - (spriteHeight*0.8)));
		}
		if (agent.getState() == "down") {
			vegetaDown.draw((float)(coordTran.worldToScreen(location).x - (int)(spriteWidth*0.5)), (float)(coordTran.worldToScreen(location).y - (spriteHeight*0.8)));
		}
		if (agent.getState() == "left") {
			vegetaLeft.draw((float)(coordTran.worldToScreen(location).x - (int)(spriteWidth*0.5)), (float)(coordTran.worldToScreen(location).y - (spriteHeight*0.8)));
		}
		if (agent.getState() == "right") {
			vegetaRight.draw((float)(coordTran.worldToScreen(location).x - (int)(spriteWidth*0.5)), (float)(coordTran.worldToScreen(location).y - (spriteHeight*0.8)));
		}
	}
}
