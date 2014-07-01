package structure.impl;

import java.util.ArrayList;

/**
 * Class that represents a station at its characteristics in the problem
 * @author Jaime Romero
 *
 */
public class Station {

	// -------------------------------------------------
	// Attributes
	// -------------------------------------------------
	/**
	 * Id of the station
	 */
	private int id;
	/**
	 * Attribute of the station
	 */
	private ArrayList<Attribute> atributes;
	
	/**
	 * Class name of the station
	 */
	private String nameClass;

	// -------------------------------------------------
	// Contructor
	// -------------------------------------------------	
	/**
	 * Constructor of the class
	 * @param nId - id of the station
	 * @param nameClass -Class name of the station
	 */	
	public Station(int nId, String nameClass){
		id= nId;
		this.nameClass = nameClass;		
		atributes = new ArrayList<Attribute>();
	}
	
	// -------------------------------------------------
	// Getters and Setters
	// -------------------------------------------------		

	public String getNameClass() {
		return nameClass;
	}

	public void setNameClass(String nameClass) {
		this.nameClass = nameClass;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<Attribute> getAtributes() {
		return atributes;
	}

	public void setAtributes(ArrayList<Attribute> atributes) {
		this.atributes = atributes;
	}
}
