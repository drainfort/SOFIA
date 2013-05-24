package common.types;

import java.util.ArrayList;

public class BetaVO {

	// -----------------------------------------
	// Attributes
	// -----------------------------------------
	
	private String name;
	
	private String classCanonicalName;
	
	private ArrayList<String> informationFiles;
	
	private boolean considered;
	
	// -----------------------------------------
	// Constructor
	// -----------------------------------------
	
	public BetaVO(String name, String classCanonicalName, ArrayList<String> informationFiles, boolean considered){
		this.name = name;
		this.classCanonicalName = classCanonicalName;
		this.informationFiles = informationFiles;
		this.considered = considered;
		
		System.out.println("BetaVO." + name + "." + considered);
	}

	// -----------------------------------------
	// Methods
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