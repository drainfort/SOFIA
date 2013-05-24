package launcher.generator.vos;

public class ParameterVO {

	// -------------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------------
	
	private String name;
	
	private String value;
	
	// -------------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------------
	
	public ParameterVO(){
		
	}

	// -------------------------------------------------------------------------------
	// Methods
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