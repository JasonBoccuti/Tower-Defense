package edu.Moravian.Main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import edu.Moravian.Game.Game;

public class Main {
	
	// 4 x 3 ratio Width x Height
	private static int screenWidth = 800;
	private static int screenHeight = 640;

	public static void main(String[] args) throws SlickException {
		
		try {
			Game game = Game.getInstance();
			AppGameContainer agc = new AppGameContainer(game, screenWidth, screenHeight, false);
			agc.setTargetFrameRate(60);
			agc.start();
		}
		catch (SlickException ex)
        {
            System.out.println("Error starting game");
        }

	}

}
