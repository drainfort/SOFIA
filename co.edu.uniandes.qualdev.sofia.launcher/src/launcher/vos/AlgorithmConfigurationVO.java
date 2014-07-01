package launcher.vos;

import java.util.ArrayList;

/**
 * Class that contains all the elements of the algorithm
 * @author David Mendez
 * @author Jaime Romero
 *
 */
public class AlgorithmConfigurationVO {

	// ----------------------------------------------------
	// Constants
	// ----------------------------------------------------

	// Initial solution builders
	public final static String YU = "Yu";
	public final static String TAILLARD = "Taillard";
	
	// Initial solution builders
	public final static String LPTNonDelay = "LPT";
	public final static String LRPTNonDelay = "LRPT";
	public final static String SPTNonDelay = "SPT";
	public final static String SRPTNonDelay = "SRPT";
	public final static String RandomDispatchingRule = "Select random";
	public final static String BestDispatchingRule = "Select best";
	public final static String StochasticERM = "Stochastic ERM";
	public final static String StochasticLPTNonDelay = "Stochastic LPT";
	public final static String StochasticSPTNonDelay = "Stochastic SPT";
	public static final String PARALLEL = "Parallel";
	
	// Meta-heuristics
	public static String TABU_SEARCH_COMPLETE_NEIGHBORHOOD = "Tabu search (CN)";
	public static String TABU_SEARCH_RESTRICTED_NEIGHBORHOOD = "Tabu search (RN)";
	public static String SIMULATED_ANNELING = "SimulatedAnnealing";
	public static String GRASP = "GRASP";
	public static String RANDOM_WALK = "Random";
	
	// Neighborhoods
	public static String RANDOM_NEIGHBORHOOD = "Random";
	public static String API_NEIGHBORHOOD = "Api";
	public static String CRITICAL_ROUTE_ADJACENT_NEIGHBORHOOD="CR: Adjacent";
	public static String CRITICAL_ROUTE_ADJACENT_MACHINES_NEIGHBORHOOD ="CR: Adjacent (machines only)";
	public static String CRITICAL_BLOCK_RANDOM_NEIGHBORHOOD = "CR-Block: Random";
	public static String CRITICAL_BLOCK_ADJACENT_ON_END_NEIGHBORHOOD = "CR-Block: Adjacent on end";
	public static String CRITICAL_BLOCK_END_START_ANY_NEIGHBORHOOD = "CR-Block: End/start/any";
	public static String CRITICAL_WEIGHTED_NODES_NEIGHBORHOOD = "CR-Weighted: Sorted nodes";
	
	// Modifiers
	public static String RANDOM_MODFIER = "Random";
	public static String SWAP = "Swap";
	public static String LEFT_INSERTION = "Left insertion";
	public static String RIGHT_INSERTION = "Right insertion";
	
	// Representation structure
	public static String GRAPH = "Graph";
	public static String VECTOR = "Vector";
	
	// Objective function
	public static String CMAX = "CMax";
	public static String MEAN_FLOW_TIME = "Mean flow time";
	
	// Betas
	public static String TRAVEL_TIMES = "Travel times";
	public static String SETUP_TIMES = "Setup times";
	
	// Report configuration
	public static String CONSOLIDATION_TABLE = "Consolidation table";
	public static String INITIAL_SOLUTIONS = "Initial solutions";
	public static String FINAL_SOLUTIONS = "Final solutions";
	public static String LOG = "Log";
	
	// ----------------------------------------------------
	// Attributes
	// ----------------------------------------------------
	
	/**
	 * Type of problem 
	 */
	private String instanceType;
	
	/**
	 * Class name for the component to build the initial solution
	 */
	private String initialSolutionBuilder;
	
	/**
	 * Class name for the control component
	 */
	private String metaheuristic;
	
	/**
	 * Parameters of the metaheuristic
	 */
	private ArrayList<ParameterVO> metaheuristicParams;
	
	/**
	 * list of class names for the modifier component
	 */
	private ArrayList<String> modifiers;
	
	/**
	 * Class name for the neighbor calculator component
	 */
	private String neighborhood;
	
	/**
	 * Class name of the structure component
	 */
	private String representation;
	
	/**
	 * Class name for the gamma calculator
	 */
	private String objectiveFunction;
	
	/**
	 * List of betas
	 */
	private ArrayList<String> selectedBetas;
	
	/**
	 * Configuration for the report
	 */
	private ArrayList<String> reportConfiguration;
	
	/**
	 * Amount of executions per instance
	 */
	private int amountOfExecutionsPerInstance;
	
	// ----------------------------------------------------
	// Constructor
	// ----------------------------------------------------
	
	/**
	 * Constructor of the class
	 */
	public AlgorithmConfigurationVO(){
		selectedBetas = new ArrayList<String>();
		reportConfiguration = new ArrayList<String>();
		metaheuristicParams = new ArrayList<ParameterVO>();
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

	public ArrayList<String> getModifier() {
		return modifiers;
	}

	public void setModifier(ArrayList<String> modifiers) {
		this.modifiers = modifiers;
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
	
	public ArrayList<String> getSelectedBetas() {
		return selectedBetas;
	}

	public ArrayList<String> getReportConfiguration() {
		return reportConfiguration;
	}

	public ArrayList<ParameterVO> getMetaheuristicParams() {
		return metaheuristicParams;
	}

	public int getAmountOfExecutionsPerInstance() {
		return amountOfExecutionsPerInstance;
	}

	public void setAmountOfExecutionsPerInstance(int amountOfExecutionsPerInstance) {
		this.amountOfExecutionsPerInstance = amountOfExecutionsPerInstance;
	}

	public String getInstanceType() {
		return instanceType;
	}

	public void setInstanceType(String instanceType) {
		this.instanceType = instanceType;
	}

	public String toString(){
		return "Initial solution: " +  initialSolutionBuilder + "\n" +
				"Metaheuristic: " + metaheuristic + "\n" + 
				"Modifier: " + modifiers.toString() + "\n" + 
				"Neighborhood: " + neighborhood + "\n" + 
				"Representation " +  representation + "\n" + 
				"Gamma: " + objectiveFunction;
	}
}