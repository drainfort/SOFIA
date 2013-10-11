package common.utils;

import java.util.ArrayList;

public class Graphic {
	
	ArrayList<Point> points;
	
	public Graphic(){
		points = new ArrayList<Point>();
	}

	public ArrayList<Point> getPoints() {
		return points;
	}

	public void setPoints(ArrayList<Point> points) {
		this.points = points;
	}

	public void addPoint( Point newPoint){
		points.add(newPoint);
	}
	
	public String toString() {
		String respuesta = "";
		for (int i = 0; i < points.size(); i++) {
			respuesta +=  points.get(i).toString();
		}
		return respuesta;
	}
}
