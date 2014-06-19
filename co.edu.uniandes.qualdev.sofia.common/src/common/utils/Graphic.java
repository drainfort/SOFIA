package common.utils;

import java.util.ArrayList;

/**
 * Class that represent a linear chart
 * @author Jaime Romero
 */
public class Graphic {
	
	// --------------------------------------------
	// Attributes
	// --------------------------------------------
	
	/**
	 * Points that conform the graphic
	 */	
	private ArrayList<Point> points;
	
	// --------------------------------------------
	// Constructor
	// --------------------------------------------
	public Graphic(){
		points = new ArrayList<Point>();
	}
	// ---------------------------------------
	// Methods
	// ---------------------------------------
	
	public String toString() {
		String respuesta = "";
		for (int i = 0; i < points.size(); i++) {
			respuesta +=  points.get(i).toString();
		}
		return respuesta;
	}
	
	// --------------------------------------------
	// Getters and setters
	// --------------------------------------------
	public ArrayList<Point> getPoints() {
		return points;
	}

	public void setPoints(ArrayList<Point> points) {
		this.points = points;
	}

	public void addPoint( Point newPoint){
		points.add(newPoint);
	}
	
	
}
