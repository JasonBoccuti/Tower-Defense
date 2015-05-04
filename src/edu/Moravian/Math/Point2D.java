package edu.Moravian.Math;

/**
 * This class represents a basic two-dimensional point.
 * Methods that correspond to common operations with vectors
 * are supported.
 *
 * @author mebjc01
 */
public class Point2D {
    /**
     * This field is used as the tolerance for comparing two
     * points for equality.
     */
    public static final double TOL = 0.000000001;
    private double x;
    private double y;

    /**
     * Create a new point representing the origin
     */
    public Point2D() {
        x = y = 0.0;
    }

    /**
     * Create a new point using the specified values
     * @param x the x value of the point
     * @param y the y value of the point
     */
    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Create a point with x and y values equally the parameter
     * @param p the point to "copy"
     */
    public Point2D(Point2D p) {
        this.x = p.x;
        this.y = p.y;
    }

    /**
     * Get the x component of the point
     * @return the value of x
     */
    public double getX() {
        return x;
    }

    /**
     * Get the y component of the point
     * @return the value of y
     */
    public double getY() {
        return y;
    }

    /**
     * Change the x component of the point
     * @param x the new value
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Change the y component of the point
     * @param y the new value
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Make this point have the same value as the parameter
     * @param p the point to "copy"
     */
    public void set(Point2D p) {
        x = p.x;
        y = p.y;
    }

    /**
     * Compute the vector representing this - rhs
     * @param rhs the right hand side of the difference
     * @return the vector pointing from rhs to this
     */
    public Vector2D minus(Point2D rhs) {
        return new Vector2D(x - rhs.x, y - rhs.y);
    }

    /**
     * Add the vector to this point: this = this + rhs
     * @param rhs the vector to add
     */
    public void plusEquals(Vector2D rhs) {
        x += rhs.getX();
        y += rhs.getY();
    }

    /**
     * Compute the sum of this point and the vector parameter.  The
     * point will not be modified: return this + rhs
     * @param rhs the vector to add
     * @return the sum of the point and the vector
     */
    public Point2D plus(Vector2D rhs) {
        return new Point2D(x + rhs.getX(), y + rhs.getY());
    }

    /**
     * Modify this point by adding the scaled version of the vector:
     * this = this + scalar * v
     * @param scalar the scalar
     * @param v the vector
     */
    public void scalePlusEquals(double scalar, Vector2D v) {
        x += scalar * v.getX();
        y += scalar * v.getY();
    }

    /**
     * Compute the addition of this point and the scaled vector.  This
     * point is not changed by this operation: return this + scalar * v
     * @param scalar the scalar
     * @param v the vector
     * @return the sum of this point and the scaled vector.
     */
    public Point2D scalePlus(double scalar, Vector2D v) {
        return new Point2D(x + scalar * v.getX(), y + scalar * v.getY());
    }

    /**
     * Two points are equal if their x and y components are both
     * within TOL of each other
     * @param obj the object to compare against
     * @return true if they are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Point2D other = (Point2D) obj;
        if (Math.abs(this.x - other.x) > TOL)
        {
            return false;
        }
        if (Math.abs(this.y - other.y) > TOL)
        {
            return false;
        }
        return true;
    }
    
    /**
     * Compute the hash code for this point
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return (int)(x + y);
    }

    /**
     * Convert this point to a string of the form "(x, y)"
     * @return the string representation of this point
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

//	public int compareTo(Point2D p) {
//		return 0;
//	}
}
