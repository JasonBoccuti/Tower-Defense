package edu.Moravian.Entity;


import java.util.ArrayList;
import java.util.Iterator;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import edu.Moravian.Game.Game;
import edu.Moravian.Math.Point2D;
import edu.Moravian.View.SpriteRenderer;

public class Agent extends Entity {
	
	private int health;
	private int maxHealth;
	private int velocity;
	private double distance;
	private String currentState;
	private ArrayList<Point2D> path;
	private Iterator<Point2D> pathIterator;
	private Point2D nextNode;
	private double locX, locY, nextX, nextY;
	private boolean dead;
	private SpriteRenderer ren;
	private ArrayList<String> states;
	private Iterator<String> stateIterator;
	private double tolerance = 10;
	
	
	public Agent(int maxHealth) throws SlickException {
		this.health = maxHealth;
		this.maxHealth = maxHealth;
		distance = 0;
		// feet/sec
		velocity = 300;
		dead = false;
		path = new ArrayList<Point2D>();
		path.add(new Point2D(64, 1728));
		path.add(new Point2D(1216, 1728));
		path.add(new Point2D(1216, 704));
		path.add(new Point2D(3136, 704));
		
		pathIterator = path.iterator();
		
		states = new ArrayList<String>();
		states.add("right");
		states.add("down");
		states.add("right");
		states.add("right");
		
		stateIterator = states.iterator();
		
		this.location = nextNode = pathIterator.next();
		locX = nextX = location.getX();
		locY = nextY = location.getY();
		
		ren = new SpriteRenderer();
	}
	
	public void update(int delta) {
		if (Math.abs(nextNode.getX() - location.getX()) < tolerance &&
			Math.abs(nextNode.getY() - location.getY()) < tolerance) {
			if (pathIterator.hasNext() && stateIterator.hasNext()) {
				nextNode = pathIterator.next();
				currentState = stateIterator.next();
			}
			else {
				dead = true;
				Game.getInstance().setLives(Game.getInstance().getLives()-1);
			}
		}
		
		distance = (velocity*delta)/1000;
		
		nextX = nextNode.getX();
		nextY = nextNode.getY();
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
		else if (locY > nextY) {
			locY -= distance;
		}
		
		location = new Point2D(locX, locY);	
	}
	
	public void render(Graphics g) {
		ren.render(this, location);
	}
	
	public void move() {
		
	}
	
	public void takeDamage(int damage) {
		if ((health - damage) < 0) {
			health = 0;
		}
		else {
			this.health -= damage;
		}
	}
	
	public int getHealth() {
		return health;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}
	
	public void setState(String state) {
		this.currentState = state;
	}
	
	public String getState() {
		return currentState;
	}
	
	public Boolean isAgentDead() {
		return dead;
	}
}
