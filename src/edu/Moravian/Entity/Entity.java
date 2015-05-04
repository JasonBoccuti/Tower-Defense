package edu.Moravian.Entity;

import edu.Moravian.Math.Point2D;

public abstract class Entity {

	protected Point2D location;
	
	// In World Coordinates
	public Point2D getLocation() {
		return location;
	}
}
