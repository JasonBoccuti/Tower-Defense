package edu.Moravian.Game;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.tiled.TiledMap;

import edu.Moravian.Entity.Agent;
import edu.Moravian.Entity.Bullet;
import edu.Moravian.Entity.Tower;
import edu.Moravian.Math.CoordinateTranslator;
import edu.Moravian.Math.Point2D;
import edu.Moravian.Game.Game;

public class Game extends BasicGame {
	
	private static Game instance;

	// 4 x 3 ratio Width x Height
	private int screenWidth = 800;
	private int screenHeight = 640;
	// 3200 feet x 2560 feet
	private double worldWidth = 3200;
	private double worldHeight = 2560;
	
	private TiledMap tileMap;
	private CoordinateTranslator coordTran;
	private int mouseX, mouseY;
	private ArrayList<Agent> agents;
	private ArrayList<Tower> towers;
	private ArrayList<Bullet> bullets;
	private boolean placeTower, exit;
	private boolean tower1, tower2, tower3;
	private int lives;
	private Point2D destination;
	private GameLog log;
	private Tower tempTower;
	private boolean wave1Done, wave2Done, wave3Done;
	private boolean betweenWave1And2, betweenWave2And3, betweenWave3AndEnd;
	private int wave1 = 10;
	private int wave2 = 15;
	private int wave3 = 20;
	private int count = 0;
	private Sound quicktips, hit, win, boop;
	private Music neverGonna;

	private int waveTimer;

	private boolean gameLog;
	
	private Game(String title) {
		super(title);
	}
	
	public static Game getInstance() {
		if (instance == null) {
			instance = new Game("Tower Defense");
		}
		return instance;
	}

	public void init(GameContainer arg0) throws SlickException {
		File file = new File("res/info.txt");
        try {
            Scanner scanner = new Scanner(file);
            String mapPath = scanner.nextLine();
            tileMap = new TiledMap(mapPath);
            scanner.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        }
        
		coordTran = new CoordinateTranslator(screenWidth, screenHeight, worldWidth, worldHeight, new Point2D(0, 0));
		agents = new ArrayList<Agent>();
		towers = new ArrayList<Tower>();
		bullets = new ArrayList<Bullet>();
		placeTower = false;
		waveTimer = 0;
		lives = 10;
		destination = new Point2D(3136, 704);
		
		tower1 = true;
		tower2 = tower3 = false;
		gameLog = false;
		log = new GameLog(this);
		wave1Done = wave2Done = wave3Done = false;
		betweenWave1And2 = betweenWave2And3 = betweenWave3AndEnd = false;
		quicktips = new Sound("res/QuickTips.wav");
		hit = new Sound("res/hit.wav");
		win = new Sound("res/win.wav");
		boop = new Sound("res/boop.wav");
		neverGonna = new Music("res/nevergonna.wav");
	}
	
	public void update(GameContainer gc, int delta) throws SlickException {
		if (placeTower) {
			
			// make a new tower with screen coordinates
			if (tower1) {
				tempTower = new Tower(500.0, 100, new Point((int)(mouseX),(int)(mouseY)));
			}
			else if (tower2) {
				tempTower = new Tower(750.0, 70, new Point((int)(mouseX),(int)(mouseY)));
			}
			else if (tower3) {
				tempTower = new Tower(1000.0, 40, new Point((int)(mouseX),(int)(mouseY)));
			}
			
			boolean isTowerThere = false;
			
			// Checks to see if tower is already there
			for (Tower t : towers) {
				if (t.getTileLocation().equals(tempTower.getTileLocation())) {
					isTowerThere = true;
				}
			}
			
			if (!isTowerThere && tileMap.getTileId(mouseX/32, mouseY/32, tileMap.getLayerIndex("Path")) == 0) {
				towers.add(tempTower);
			}
			
			placeTower = false;
		}
		
		if (exit) {
			gc.exit();
		}
		
		for (Tower t : towers) {
			// Find the closest target
			t.setTarget(agents);
			if (t.hasCurrentTarget()) {
				Agent currTarg = t.getCurrentTarget();
				if (currTarg.getHealth() == 0) {
					agents.remove(currTarg);
				}
				else {
					// Attack it
					t.attack(t.getCurrentTarget(), delta);
				}
			}
		}
		
		// Spawns Agents based on timer
		waveTimer += delta;
		if (waveTimer > 1000) {
			if (!wave1Done) {
				if (count < wave1) {
					agents.add(new Agent(400));
					boop.play();
					count++;
					waveTimer = 0;
				}
				else {
					count = 0;
					wave1Done = true;
					System.out.println("Wave 1 Ending");
					waveTimer = -10000;
					betweenWave1And2 = true;
					win.play();
					lives = 10;
				}
			}
			else if (!wave2Done) {
				betweenWave1And2 = false;
				if (count < wave2) {
					agents.add(new Agent(500));
					boop.play();
					count++;
					waveTimer = 0;
				}
				else {
					count = 0;
					wave2Done = true;
					System.out.println("Wave 2 Ending");
					waveTimer = -15000;
					betweenWave2And3 = true;
					quicktips.play();
					lives = 10;
				}
			}
			else if (!wave3Done) {
				betweenWave2And3 = false;
				if (count < wave3) {
					agents.add(new Agent(600));
					boop.play();
					count++;
					waveTimer = 0;
				}
				else {
					count = 0;
					wave3Done = true;
					betweenWave3AndEnd=true;
					neverGonna.play();
					//exit = true;
				}
			}
		}
		
		if (waveTimer >= -1000 && waveTimer <= -800) {
			towers.removeAll(towers);
			hit.play();
		}
		
		// Delete Agents
		for (int i = 0; i < agents.size(); i++) {
			if (agents.get(i).isAgentDead()) {
				agents.remove(agents.get(i));
			}
		}
		
		for (Agent a : agents) {
			a.update(delta);
		}
		
		for (int i = 0; i < bullets.size(); i++) {
			if (bullets.get(i).isBulletDead()) {
				bullets.get(i).killBullet();
				bullets.remove(bullets.get(i));
			}
		}
		
		for (Bullet b : bullets) {
			b.update(delta);
		}
		
		if (lives == 0) {
			exit = true;
		}
	}
	
	public void render(GameContainer gc, Graphics g) throws SlickException {
		tileMap.render(0, 0);
		g.setColor(Color.white);
		g.drawString("Lives: " + lives, screenWidth-100, 10);
		// Render the towers
		for (Tower t : towers) {
			t.render(g);
		}
		// Render the agents
		for (Agent a: agents) {
			a.render(g);
		}
		// Render the bullets
		for (Bullet b : bullets) {
			b.render(g);
		}
		
		if (gameLog) {
			log.display(g);
		}
		if (betweenWave1And2) {
			g.setColor(Color.cyan);
			g.drawString("Wave 1 Ending!  Prepare for Wave 2!", screenWidth/2, screenHeight/2);
		}
		if (betweenWave2And3) {
			g.setColor(Color.cyan);
			g.drawString("Wave 2 Ending!  Prepare for Wave 3!", screenWidth/2, screenHeight/2);
		}
		if (betweenWave3AndEnd) {
			g.setColor(Color.cyan);
			g.drawString("Wave 3 Ending!  YOU WON!!!!", screenWidth/2, screenHeight/2);	
		}
	}
	
	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
	      mouseX = x;
	      mouseY = y;
	      placeTower = true;  
	}
	
	@Override
    public void keyPressed(int key, char c) {
		if(c == 'q' || c == 'Q') {
			exit = true;
		}
		if(c == '1') {
			tower1 = true;
			tower2 = tower3 = false;
		}
		if(c == '2') {
			tower2 = true;
			tower1 = tower3 = false;
		}
		if(c == '3') {
			tower3 = true;
			tower1 = tower2 = false;
		}
		if(key == Input.KEY_TAB) {
			gameLog = gameLog == false;
		}
	}
	@Override
    public void keyReleased(int key, char c) {
		if(c == 'q' || c == 'Q') {
			exit = false;
		}
	}
	
	public CoordinateTranslator getCT() {
		return coordTran;
	}
	
	public void addBullet(Bullet b) {
		bullets.add(b);
	}
	
	public int getLives() {
		return lives;
	}
	
	public void setLives(int newLives) {
		lives = newLives;
	}
	
	public String getTowerState() {
		if (tower1) {
			return "Tower1";
		}
		else if (tower2) {
			return "Tower2";
		}
		else if (tower3) {
			return "Tower3";
		}
		return "Tower1";
	}
	
	public int getScreenWidth() {
		return screenWidth;
	}
	public int getScreenHeight() {
		return screenHeight;
	}
	
	public ArrayList<Tower> getTowers() {
		return towers;
	}
}
