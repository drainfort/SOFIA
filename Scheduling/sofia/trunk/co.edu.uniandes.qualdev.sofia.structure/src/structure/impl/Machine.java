package structure.impl;

import java.util.ArrayList;

/**
 * Class that represents a machine at its characteristics in the problem
 * @author Jaime Romero
 *
 */
public class Machine {
	
	// -------------------------------------------------
	// Attributes
	// -------------------------------------------------
	/**
	 * Id of the machine
	 */
	private int id;
	/**
	 * Attribute of the machine
	 */
	private ArrayList<Attribute> atributes;
	
	/**
	 * Class name of the machine
	 */
	private String nameClass;
	
	// -------------------------------------------------
	// Contructor
	// -------------------------------------------------	
	/**
	 * Constructor of the class
	 * @param nId - id of the machine
	 * @param nameClass -Class name of the machine
	 */	
	public Machine(int nId, String nameClass){
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
