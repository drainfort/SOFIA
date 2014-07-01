package structure.impl;

/**
 * Class that represent an attribute of a job, station or machine
 * @author Jaime Romero
 *
 */
public class Attribute {
	
	// -------------------------------------------------
	// Attributes
	// -------------------------------------------------
	/**
	 * Data type of the attribute
	 */
	private String type;
	
	/**
	 * Name of the attribute
	 */
	private String name;
	
	/**
	 * Value of the attribute
	 */
	private String value;

	
	// -------------------------------------------------
	// Constructor
	// -------------------------------------------------
	/**
	 * Constructor of the class
	 * @param nType - Type of the attribute
	 * @param nName - name of the attribute
	 * @param nValue - value of the attribute
	 */
	public Attribute (String nType, String nName,String nValue){
		type= nType;
		name = nName;
		value= nValue;
	}

	// -------------------------------------------------
	// Methods
	// -------------------------------------------------

	// -------------------------------------------------
	// Getters and Setters
	// -------------------------------------------------
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	
}
