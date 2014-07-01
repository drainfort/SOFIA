package launcher.vos;

/**
 * Class that represent a parameter of an algorithm
 * @author David Mendez
 * @author Jaime Romero
 *
 */
public class ParameterVO {

	// -------------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------------
	
	/**
	 * Name of the parameter
	 */
	private String name;
	
	/**
	 * Value of the parameter
	 */
	private String value;
	
	// -------------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------------
	
	/**
	 * Constructor of the class
	 */
	public ParameterVO(){
		
	}

	// -------------------------------------------------------------------------------
	// Methods
	// -------------------------------------------------------------------------------

	// -------------------------------------------------------------------------------
	// Getters and Setters
	// -------------------------------------------------------------------------------	
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