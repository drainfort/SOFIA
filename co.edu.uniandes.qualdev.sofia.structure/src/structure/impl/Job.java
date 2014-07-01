package structure.impl;

import java.util.ArrayList;

/**
 * Class that represents a job at its characteristics in the problem
 * @author Jaime Romero
 *
 */
public class Job {
	
	// -------------------------------------------------
	// Attributes
	// -------------------------------------------------
	/**
	 * Id of the job
	 */
	private int id;
	/**
	 * Attribute of the job
	 */
	private ArrayList<Attribute> atributes;
	
	/**
	 * Class name of the job
	 */
	private String nameClass;
	

	// -------------------------------------------------
	// Contructor
	// -------------------------------------------------	
	/**
	 * Constructor of the class
	 * @param nId - id of the job
	 * @param nameClass -Class name of the job
	 */
	public Job(int nId, String nameClass){
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
