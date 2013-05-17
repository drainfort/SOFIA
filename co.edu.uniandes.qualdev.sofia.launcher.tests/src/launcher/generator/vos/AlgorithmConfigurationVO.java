package launcher.generator.vos;

public class AlgorithmConfigurationVO {

	// ----------------------------------------------------
	// Constants
	// ----------------------------------------------------

	// Metaheuristics
	public static String TABU_SEARCH_COMPLETE_NEIGHBORHOOD = "Tabu search (CN)";
	public static String TABU_SEARCH_RESTRICTED_NEIGHBORHOOD = "Tabu search (RN)";
	public static String SIMULATED_ANNELING = "SimulatedAnnealing";
	public static String GRASP = "GRASP";
	public static String RANDOM_WALK = "Random";
	
	// Neighborhoods
	public static String RANDOM_NEIGHBORHOOD = "Random";
	public static String CRITICAL_ADJACENT="CR: Adjacent";
	public static String CRITICAL_ADJACENT_MACHINES ="CR: Adjacent machines";
	public static String CRITICAL_BLOCK = "CR: Random in random block";
	public static String CRITICAL_BLOCK_ADJACENT = "CR: Adjacent in random block";
	public static String CRITICAL_BLOCK_ENDSTART = "CR: Borders in random block";
	
	// Modifiers
	public static String RANDOM_MODFIER = "Random";
	public static String SWAP = "Swap";
	public static String LEFT_INSERTION = "Left insertion";
	public static String RIGHT_INSERTION = "Right insertion";
	
	// Representation structure
	public static String GRAPH = "Graph";
	public static String VECTOR = "Vector";
	
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