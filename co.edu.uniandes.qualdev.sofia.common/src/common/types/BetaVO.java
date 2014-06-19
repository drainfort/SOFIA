package common.types;

import java.util.ArrayList;


/**
 * Contains the information of an specific beta, information files.
 * 
 * @author Rubby Casallas
 * @author David Méndez Acuña
 * @author Jaime Romero
 * 
 */
public class BetaVO {

	// -----------------------------------------
	// Attributes
	// -----------------------------------------
	
	/**
	 * Name of the Beta
	 */
	private String name;
	
	/**
	 * Name of the class thats implements the beta
	 */
	private String classCanonicalName;
	
	/**
	 * Information files needed to calculate the beta
	 */
	private ArrayList<String> informationFiles;
	
	/**
	 * Attribute that specifies if the beta has to be considered
	 */
	private boolean considered;
	
	// -----------------------------------------
	// Constructor
	// -----------------------------------------
	
	/**
	 * Constructor of the class
	 * @param name - name of the beta
	 * @param classCanonicalName - name of the class that implements the beta
	 * @param informationFiles - information files with the data of beta
	 * @param considered - beta is considered in the problem or not
	 */
	public BetaVO(String name, String classCanonicalName, ArrayList<String> informationFiles, boolean considered){
		this.name = name;
		this.classCanonicalName = classCanonicalName;
		this.informationFiles = informationFiles;
		this.considered = considered;
	}

	// -----------------------------------------
	// Getters and Setters
	// -----------------------------------------
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassCanonicalName() {
		return classCanonicalName;
	}

	public void setClassCanonicalName(String classCanonicalName) {
		this.classCanonicalName = classCanonicalName;
	}

	public ArrayList<String> getInformationFiles() {
		return informationFiles;
	}

	public void setInformationFiles(ArrayList<String> informationFiles) {
		this.informationFiles = informationFiles;
	}

	public boolean isConsidered() {
		return considered;
	}

	public void setConsidered(boolean considered) {
		this.considered = considered;
	}
}