package edu.Moravian.Game;

import java.awt.Point;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import edu.Moravian.Entity.Tower;
import edu.Moravian.Math.CoordinateTranslator;

public class GameLog {
	
	private final Game game;
	private ArrayList<Tower> towers;
	private CoordinateTranslator coordTran;
	private Color whiteAlpha = new Color(1f, 1f, 1f, 0.1f);
	private Color greenAlpha = new Color(0f, 1f, 0f, 0.1f);
	private Color redAlpha = new Color(1f, 0f, 0f, 0.1f);
	
	public GameLog(Game game) {
		this.game = game;
	}
	
	public void display(Graphics g) {
		g.setColor(Color.white);
//		g.drawString("Lives: " + game.getLives(), game.getInstance().getScreenWidth()-100, 10);
		towers = Game.getInstance().getTowers();
		coordTran = Game.getInstance().getCT();
		for (Tower t : towers) {
			if (t.getState() == "Tower1") {
				g.setColor(whiteAlpha);
			}
			else if (t.getState() == "Tower2") {
				g.setColor(greenAlpha);
			}
			else if (t.getState() == "Tower3") {
				g.setColor(redAlpha);
			}
//			int range = (int) coordTran.worldToScreenDistance(t.getRange());
//			g.fillOval((float)(coordTran.worldToScreen(t.getLocation()).x-(range/2)), (float)(coordTran.worldToScreen(t.getLocation()).y-(range/2)),(float)range, (float)range);
//			g.drawOval((float)(coordTran.worldToScreen(t.getLocation()).x-(range/2)), (float)(coordTran.worldToScreen(t.getLocation()).y-(range/2)),(float)range, (float)range);
			g.fillOval((float)(coordTran.worldToScreen(t.getLocation()).x-(t.getRange()/2)), (float)(coordTran.worldToScreen(t.getLocation()).y-(t.getRange()/2)),(float)t.getRange(), (float)t.getRange());
			g.drawOval((float)(coordTran.worldToScreen(t.getLocation()).x-(t.getRange()/2)), (float)(coordTran.worldToScreen(t.getLocation()).y-(t.getRange()/2)),(float)t.getRange(), (float)t.getRange());
		}
		
	}

}
