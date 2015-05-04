package edu.Moravian.Entity;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import edu.Moravian.Game.Game;
import edu.Moravian.Math.Point2D;
import edu.Moravian.Math.Vector2D;

public class Tower extends Entity {
	
	private Double attackRange;
	private int attackPower;
	private Agent currentTarget;
	private HashMap<Agent, Double> map;
	private Point tileLocation;
	private Image img;
	private int shotTimer;
	private double rotation;
	private String towerState;
	
	public Tower (Double attackRange, int attackPower, Point tileLocation) throws SlickException {
		this.attackRange = attackRange;
		this.attackPower = attackPower;
		// Convert the screen coordinates to tile coordinates
		this.tileLocation = new Point((int)tileLocation.x/32, (int)tileLocation.y/32);
		// Convert the screen coordinates to world coordinates
		this.location = Game.getInstance().getCT().screenToWorld(new Point(this.tileLocation.x*32+16, this.tileLocation.y*32+16));
		this.currentTarget = null;
		img = new Image("res/tower1.png");
		shotTimer = 1000;
		if (Game.getInstance().getTowerState() == "Tower1") {
			img = new Image("res/tower1.png");
			towerState = "Tower1";
		}
		else if (Game.getInstance().getTowerState() == "Tower2") {
			img = new Image("res/tower2.png");
			towerState = "Tower2";
		}
		else if (Game.getInstance().getTowerState() == "Tower3") {
			img = new Image("res/tower3.png");
			towerState = "Tower3";
		}
	}
	
	public void render(Graphics g) {
		if (currentTarget != null) {
			this.faceTarget();
			img.setRotation((float)(-rotation*180/Math.PI));
		}
		g.drawImage(img, (float)(tileLocation.x*32), (float)(tileLocation.y*32));
	}
	
	public void setTarget(ArrayList<Agent> agents) {
		
		map = new HashMap<Agent, Double>();
		
		if (agents.size() == 0) {
			this.currentTarget = null;
			return;
		}
	
		for (Agent a : agents) {
			
			// Tower's Location component - Agent's component
			double x = this.getLocation().getX() - a.getLocation().getX();
			double y = this.getLocation().getX() - a.getLocation().getY();
			double r = attackRange*attackRange;
			double d = x*x + y*y;

			// If it's in range, add it to the map
			if (r > d) {
				map.put(a, d);
			}
		}
	
		
		if (map.size() == 0) {
			this.currentTarget = null;
			return;
		}
		Double minValueInMap=(Collections.min(map.values()));
        for (Entry<Agent, Double> entry : map.entrySet()) {
            if (entry.getValue()==minValueInMap) {
                this.currentTarget = entry.getKey();
            }
        }
	}
	
	public void attack(Agent target, int delta) {
		if (shotTimer >= 1000) {
			if (target == currentTarget) {
				Bullet b = new Bullet(location, target, attackPower);
				Game.getInstance().addBullet(b);
			}
			else {
				return;
			}
			shotTimer = 0;
		}
		else {
			shotTimer += delta;
		}
	}
	
	public Agent getCurrentTarget() {
		return currentTarget;
	}
	
	public Point getTileLocation() {
		return tileLocation;
	}
	
	public Boolean hasCurrentTarget() {
		if (currentTarget != null) {
			return true;
		}
		return false;
	}
	
	public void faceTarget() {
		Vector2D direction = new Vector2D(currentTarget.location.getX() - location.getX(), currentTarget.location.getY() - location.getY());
	    direction.normalize();
	 
	    rotation = direction.angle();
	}
	
	public double getRange() {
		return attackRange;
	}
	
	public String getState() {
		return towerState;
	}
}
