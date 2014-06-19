package common.utils;

/**
 * Class that represent a point on a line chart
 * @author Jaime Romero
 */
public class Point {

	// --------------------------------------------
	// Attributes
	// --------------------------------------------
	
	/**
	 * X coordinate of the point 
	 */
	public double x;
	
	/**
	 * Y coordinate of the point 
	 */
	public double y;
	
	// --------------------------------------------
	// Constructor
	// --------------------------------------------
	/**
	 * Constructor of the class
	 * @param x - X coordinate of the point
	 * @param y - Y coordinate of the point
	 */
	public Point (double x, double y){
		this.x = x;
		this.y = y;
	}
	
	// ---------------------------------------
	// Methods
	// ---------------------------------------
	
	public String toString(){
		return "<" + x + "," + y + ">";
	}
	
	// --------------------------------------------
	// Getters and setters
	// --------------------------------------------	
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	
	
	
}
