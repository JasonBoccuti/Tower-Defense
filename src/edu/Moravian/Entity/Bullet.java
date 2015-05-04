package edu.Moravian.Entity;

import java.awt.Point;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import edu.Moravian.Game.Game;
import edu.Moravian.Math.CoordinateTranslator;
import edu.Moravian.Math.Point2D;

public class Bullet extends Entity {
	
	private Point2D agentLoc;
	private boolean dead;
	private double velocity, distance, locX, locY, nextX, nextY;
	private Agent agent;
	private CoordinateTranslator CT;
	private Point screenPoint;
	private int towerDamage;
	private double tolerance = 10;

	public Bullet(Point2D towerLoc, Agent agent, int towerDamage) {
		this.location = towerLoc;
		dead = false;
		velocity = 800; //(int)(Math.random()*1000)+500;
		this.agent = agent;
		CT = Game.getInstance().getCT();
		this.towerDamage = towerDamage;
	}
	
	public void update(int delta) {
		agentLoc = agent.getLocation();
		if (Math.abs(location.getX() - agentLoc.getX()) < tolerance &&
			Math.abs(location.getY() - agentLoc.getY()) < tolerance) {
			dead = true;
		}
		
		distance = (velocity*delta)/1000;
		
		nextX = agentLoc.getX();
		nextY = agentLoc.getY();
		locX = location.getX();
		locY = location.getY();
		
		if (locX < nextX) {
			locX += distance;
		}
		else if (locX > nextX) {
			locX -= distance;
		}
		if (locY < nextY) {
			locY += distance;
		}
		else if(locY > nextY) {
			locY -= distance;
		}
		
		location = new Point2D(locX, locY);
	}
	
	public void killBullet() {
		agent.takeDamage(towerDamage);
	}
	
	public void render(Graphics g) {
		if (agent.isAgentDead()) {
			dead = true;
		}
		else {
			screenPoint = CT.worldToScreen(location);
			g.setColor(Color.yellow);
			g.fillOval((float)screenPoint.getX(), (float)screenPoint.getY(), 7, 7);
			g.drawOval((float)screenPoint.getX(), (float)screenPoint.getY(), 7, 7);
		}
	}
	
	public Boolean isBulletDead() {
		return dead;
	}
}
