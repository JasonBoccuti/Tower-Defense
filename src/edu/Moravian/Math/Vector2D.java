package edu.Moravian.Math;

/**
 * This class represents a two-dimensional vector.  For efficiency,
 * the class offers two versions of most operations:  one mutates
 * the vector (this avoids the need to create a new object) and the
 * other computes the result as a new vector without changing any
 * components of the computation.  In addition, because computations
 * of the form v + d * u are common (u, v are vectors; d is a scalar),
 * they are supported as a single method.
 *
 * The static class value TOL is used in *all* computations to check
 * for value close to zero.  (see note on this field)
 *
 *  @author mebjc01
 */
public class Vector2D {
	/**
	 * TOL is the threshold for determining whether a component "goes to zero."
	 * In any vector where |x| < TOL or |y| < TOL, then that value becomes zero.
	 */
	public static final double TOL = 0.00000001;

	private double x;
	private double y;

	/**
	 * Create a zero vector
	 */
	public Vector2D() {
		x = y = 0.0;
	}

	/**
	 * Create a vector equal to the specified one
	 * 
	 * @param v
	 *            the vector to "copy"
	 */
	public Vector2D(Vector2D v) {
		this.x = v.x;
		this.y = v.y;
	}

	/**
	 * Create a vector with a specified value
	 * 
	 * @param x
	 *            the x component of the new vector
	 * @param y
	 *            the y component of the new vector
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;

		checkForZero();
	}

	/**
	 * Get the x value of the vector
	 * 
	 * @return the x component
	 */
	public double getX() {
		return x;
	}

	/**
	 * Get the y value of the vector
	 * 
	 * @return the y component
	 */
	public double getY() {
		return y;
	}

	/**
	 * Change the value of the vector to the specified values.
	 * 
	 * @param x
	 *            the new x value
	 * @param y
	 *            the new y value
	 */
	public void set(double x, double y) {
		this.x = x;
		this.y = y;

		checkForZero();
	}

	/**
	 * Change the value of the vector to equal the parameter
	 * 
	 * @param v
	 *            the vector to "copy"
	 */
	public void set(Vector2D v) {
		this.x = v.x;
		this.y = v.y;
	}

	/**
	 * Get the magnitude of the vector
	 * 
	 * @return the magnitude
	 */
	public double magnitude() {
		return Math.sqrt(x * x + y * y);
	}

	/**
	 * Get the square of the magnitude
	 * 
	 * @return the square of the magnitude
	 */
	public double magnitudeSq() {
		return x * x + y * y;
	}

	/**
	 * Determine tha angle of the vector as measured from the positive x axis
	 * 
	 * @return an angle in radians from -PI to PI
	 */
	public double angle() {
		return Math.atan2(y, x);
	}

	/**
	 * Compute the right orthogonal vector without changing this vector
	 * 
	 * @return the right orthogonal vector
	 */
	public Vector2D getRightOrtho() {
		return new Vector2D(y, -x);
	}

	/**
	 * Change this vector to equal its right orthogonal vector.
	 */
	public void setRightOrtho() {
		double tempx = x;

		x = y;
		y = -tempx;
	}

	/**
	 * Compute the left orthogonal vector without changing this vector
	 * 
	 * @return the left orthogonal vector
	 */
	public Vector2D getLeftOrtho() {
		return new Vector2D(-y, x);
	}

	/**
	 * Change this vector to equal its left orthogonal vector.
	 */
	public void setLeftOrtho() {
		double tempx = x;

		x = -y;
		y = tempx;
	}

	/**
	 * Change this vector to one in the same direction with magnitude of 1. If
	 * the vector is the zero vector, it is unchanged.
	 */
	public void normalize() {
		double mag = magnitude();

		if (mag < TOL)
			return;

		x /= mag;
		y /= mag;

		checkForZero();
	}

	/**
	 * Compute the normalized vector (same direction with magnitude 1) without
	 * changing this vector. If this vector is the zero vector, a zero vector
	 * will be returned (a new object).
	 * 
	 * @return the normalized vector
	 */
	public Vector2D getNormalized() {
		Vector2D ret = new Vector2D(this);

		ret.normalize();

		return ret;
	}

	/**
	 * Compute the sum of this vector and the parameter without changing this
	 * vector: return this + sum
	 * 
	 * @param rhs
	 *            the right hand side of the addition
	 * @return the sum of the two vectors
	 */
	public Vector2D plus(Vector2D rhs) {
		return new Vector2D(this.x + rhs.x, this.y + rhs.y);
	}

	/**
	 * Make this vector equal to the sum of itself and the specified vector:
	 * this = this + rhs
	 * 
	 * @param rhs
	 *            the vector to add (right hand side of the sum)
	 */
	public void plusEquals(Vector2D rhs) {
		x += rhs.x;
		y += rhs.y;

		checkForZero();
	}

	/**
	 * Compute the difference of this vector and the parameter without changing
	 * this vector: return this - rhs
	 * 
	 * @param rhs
	 *            the right hand side of the difference
	 * @return the difference of the two vectors
	 */
	public Vector2D minus(Vector2D rhs) {
		return new Vector2D(this.x - rhs.x, this.y - rhs.y);
	}

	/**
	 * Make this vector equal to the difference of itself and the specified
	 * vector: this = this - rhs
	 * 
	 * @param rhs
	 *            the vector to subtrace (right hand side of the difference)
	 */
	public void minusEquals(Vector2D rhs) {
		x -= rhs.x;
		y -= rhs.y;

		checkForZero();
	}

	/**
	 * Compute the product of this vector and a scalar without changing this
	 * vector: return d * this
	 * 
	 * @param d
	 *            the scalar
	 * @return the product of the scalar and the vector
	 */
	public Vector2D times(double d) {
		return new Vector2D(x * d, y * d);
	}

	/**
	 * Make this vector equal to the product of itself and the scalar: this = d
	 * * this
	 * 
	 * @param d
	 *            the scalar
	 */
	public void timesEquals(double d) {
		this.x *= d;
		this.y *= d;

		checkForZero();
	}

	/**
	 * Compute the product of this vector and the inverse of a scalar without
	 * changing this vector: return 1/d * this
	 * 
	 * @param d
	 *            the scalar
	 * @return the product of the inverse of the scalar and the vector
	 */
	public Vector2D divide(double d) {
		return new Vector2D(x / d, y / d);
	}

	/**
	 * Make this vector equal to the product of itself and the inverse of the
	 * scalar: this = 1/d * this
	 * 
	 * @param d
	 *            the scalar
	 */
	public void divideEquals(double d) {
		this.x /= d;
		this.y /= d;

		checkForZero();
	}

	/**
	 * Change this vector to the negation of itself: this = -1 * this
	 */
	public void negate() {
		this.x *= -1;
		this.y *= -1;
	}

	/**
	 * Change the sign on the x component. If v = (x, y), then this method will
	 * make it v = (-x, y)
	 */
	public void reflectX() {
		x *= -1;
	}

	/**
	 * Change the sign on the y component. If v = (x, y), then this method will
	 * make it v = (x, -y)
	 */
	public void reflectY() {
		y *= -1;
	}

	/**
	 * Compute the dot product of this vector and the parameter: return this *
	 * rhs (where '*' is the dot product)
	 * 
	 * @param rhs
	 *            the right hand side of the dot product
	 * @return the dot product of the two vectors
	 */
	public double dot(Vector2D rhs) {
		return x * rhs.x + y * rhs.y;
	}

	/**
	 * Compute the sum of this vector and v scaled by "scalar": return this +
	 * scalar * v - This computation will not change this vector
	 * 
	 * @param scalar
	 *            the scalar
	 * @param v
	 *            the vector
	 * @return the Vector result from the sum
	 */
	public Vector2D scalePlus(double scalar, Vector2D v) {
		return new Vector2D(this.x + v.x * scalar, this.y + v.y * scalar);
	}

	/**
	 * Change this vector to the sum of this vector and the scaled vector v:
	 * this = this + scalar * v
	 * 
	 * @param scalar
	 *            the scalar
	 * @param v
	 *            the vector
	 */
	public void scalePlusEquals(double scalar, Vector2D v) {
		x += scalar * v.x;
		y += scalar * v.y;

		checkForZero();
	}

	/**
	 * Two vectors are equal if both the x and y components are within TOL of
	 * each other
	 * 
	 * @param obj
	 *            the object to compare
	 * @return true of they are the same, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if ((obj != null) && (obj.getClass().equals(this.getClass()))) {
			Vector2D that = (Vector2D) obj;

			return Math.abs(this.x - that.x) < TOL
					&& Math.abs(this.y - that.y) < TOL;
		}

		return false;
	}

	/**
	 * Compute the hash value for this vector
	 * 
	 * @return the has value
	 */
	@Override
	public int hashCode() {
		return (int) (x + y);
	}

	/**
	 * A string of the form "(x, y)"
	 * 
	 * @return the string representing the vector
	 */
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

	private void checkForZero() {
		if (Math.abs(x) < TOL)
			x = 0.0;

		if (Math.abs(y) < TOL)
			y = 0.0;
	}
}
