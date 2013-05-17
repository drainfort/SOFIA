package launcher.generator.vos;

public class AlgorithmConfigurationVO {

	// ----------------------------------------------------
	// Attributes
	// ----------------------------------------------------
	
	private String initialSolutionBuilder;
	
	private String metaheuristic;
	
	private String modifier;
	
	private String neighborhood;
	
	private String representation;
	
	private String objectiveFunction;
	
	// ----------------------------------------------------
	// Constructor
	// ----------------------------------------------------
	
	public AlgorithmConfigurationVO(){
		
	}

	// ----------------------------------------------------
	// Getters and setters
	// ----------------------------------------------------
	
	public String getInitialSolutionBuilder() {
		return initialSolutionBuilder;
	}

	public void setInitialSolutionBuilder(String initialSolutionBuilder) {
		this.initialSolutionBuilder = initialSolutionBuilder;
	}

	public String getMetaheuristic() {
		return metaheuristic;
	}

	public void setMetaheuristic(String metaheuristic) {
		this.metaheuristic = metaheuristic;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public String getRepresentation() {
		return representation;
	}

	public void setRepresentation(String representation) {
		this.representation = representation;
	}

	public String getObjectiveFunction() {
		return objectiveFunction;
	}

	public void setObjectiveFunction(String objectiveFunction) {
		this.objectiveFunction = objectiveFunction;
	}
	
	public String toString(){
		return "Initial solution: " +  initialSolutionBuilder + "\n" +
				"Metaheuristic: " + metaheuristic + "\n" + 
				"Modifier: " + modifier + "\n" + 
				"Neighborhood: " + neighborhood + "\n" + 
				"Representation " +  representation + "\n" + 
				"Gamma: " + objectiveFunction;
	}
}
