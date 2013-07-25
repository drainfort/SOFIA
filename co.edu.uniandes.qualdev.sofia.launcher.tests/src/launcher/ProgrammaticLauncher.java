package launcher;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;

import common.utils.ExecutionResults;

import chart.printer.ChartPrinter;

import algorithm.SchedulingAlgorithm;
import algorithm.impl.MultiStartAlgorithm;
import algorithm.impl.TrajectoryBasedAlgorithm;

import launcher.generator.vos.AlgorithmConfigurationVO;
import launcher.generator.vos.ParameterVO;

public class ProgrammaticLauncher {

	// ------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------
	
	// Initial solution builders
	public final static String LPTNonDelay = "initialSolBuilder.impl.LPTNonDelay";
	public final static String LRPTNonDelay = "initialSolBuilder.impl.LRPTNonDelay";
	public final static String SPTNonDelay = "initialSolBuilder.impl.SPTNonDelay";
	public final static String SRPTNonDelay = "initialSolBuilder.impl.SRPTNonDelay";
	public final static String RandomDispatchingRule = "initialSolBuilder.impl.RandomDispatchingRule";
	public final static String BestDispatchingRule = "initialSolBuilder.impl.BestDispatchingRule";
	public final static String StochasticERM = "initialSolBuilder.impl.StochasticERM";
	public final static String StochasticLPT = "initialSolBuilder.impl.StochasticLPTNonDelay";
	public final static String StochasticSRT = "initialSolBuilder.impl.StochasticSPTNonDelay";
	
	// Metaheuristics
	public final static String TABU_SEARCH_COMPLETE_NEIGHBORHOOD = "control.impl.TabuSearchCN";
	public final static String TABU_SEARCH_RESTRICTED_NEIGHBORHOOD = "control.impl.TabuSearchRN";
	public final static String SIMULATED_ANNEALING = "control.impl.SimpleSimulatedAnnealing";
	public final static String GRASP = "control.impl.GRASPERLS";
	
	// Neighborhoods
	public static String RANDOM_NEIGHBORHOOD = "neighborCalculator.impl.N1_Random";
	public static String API_NEIGHBORHOOD = "neighborCalculator.impl.Api";
	public static String CRITICAL_ROUTE_ADJACENT_NEIGHBORHOOD= "neighborCalculator.impl.N3_AdjacentInCriticalPaths";
	public static String CRITICAL_ROUTE_ADJACENT_MACHINES_NEIGHBORHOOD = "neighborCalculator.impl.N4_AdjacentInCriticalPathMachinesOnly";
	public static String CRITICAL_BLOCK_RANDOM_NEIGHBORHOOD = "neighborCalculator.impl.N5_RandomInCriticalBlock";
	public static String CRITICAL_BLOCK_ADJACENT_ON_END_NEIGHBORHOOD = "neighborCalculator.impl.ShiftBlockAdjOnEnds";
	public static String CRITICAL_BLOCK_END_START_ANY_NEIGHBORHOOD = "neighborCalculator.impl.ShiftBlockEndStartAnyCriticalRoute";
	public static String CRITICAL_WEIGHTED_NODES_NEIGHBORHOOD = "neighborCalculator.impl.ShiftWeightedNodesCriticalRoute";
	
	// Modifiers
	public static String RANDOM_MODFIER = "modifier.impl.RandomNeighbor";
	public static String SWAP = "modifier.impl.Swap";
	public static String LEFT_INSERTION = "modifier.impl.LeftInsertion";
	public static String RIGHT_INSERTION = "modifier.impl.RightInsertion";
	
	// Representation structure
	public static String GRAPH = "structure.factory.impl.GraphFactory";
	public static String VECTOR = "structure.factory.impl.VectorFactory";
	
	// Objective function
	public static String CMAX = "gammaCalculator.impl.CMaxCalculator";
	public static String MEAN_FLOW_TIME = "gammaCalculator.impl.MeanFlowTimeCalculator";
	
	public static String CMAX_BKS = "cmax";
	public static String MEAN_FLOW_TIME_BKS = "mft";
	
	// ------------------------------------------------------------
	// Constructor
	// ------------------------------------------------------------
	
	public ProgrammaticLauncher(){
		
	}
	
	// ------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------

	public void launch(ArrayList<String> instancesToExecute, String instanceType,
			AlgorithmConfigurationVO algorithmDefinition, String resultsFile) throws InstantiationException, IllegalAccessException, ClassNotFoundException, Exception {
		boolean multiStart = false;
		
		// Obtaining the initial solution builder
		String initialSolutionBuilder = null;
		if(algorithmDefinition.getInitialSolutionBuilder().equals(AlgorithmConfigurationVO.LPTNonDelay)){
			initialSolutionBuilder = LPTNonDelay;
		}else if(algorithmDefinition.getInitialSolutionBuilder().equals(AlgorithmConfigurationVO.LRPTNonDelay)){
			initialSolutionBuilder = LRPTNonDelay;
		}else if(algorithmDefinition.getInitialSolutionBuilder().equals(AlgorithmConfigurationVO.SPTNonDelay)){
			initialSolutionBuilder = SPTNonDelay;
		}else if(algorithmDefinition.getInitialSolutionBuilder().equals(AlgorithmConfigurationVO.SRPTNonDelay)){
			initialSolutionBuilder = SRPTNonDelay;
		}else if(algorithmDefinition.getInitialSolutionBuilder().equals(AlgorithmConfigurationVO.BestDispatchingRule)){
			initialSolutionBuilder = BestDispatchingRule;
		}else if(algorithmDefinition.getInitialSolutionBuilder().equals(AlgorithmConfigurationVO.RandomDispatchingRule)){
			initialSolutionBuilder = RandomDispatchingRule;
		}else if(algorithmDefinition.getInitialSolutionBuilder().equals(AlgorithmConfigurationVO.StochasticERM)){
			initialSolutionBuilder = StochasticERM;
		}else if(algorithmDefinition.getInitialSolutionBuilder().equals(AlgorithmConfigurationVO.StochasticLPTNonDelay)){
			initialSolutionBuilder = StochasticLPT;
		}else if(algorithmDefinition.getInitialSolutionBuilder().equals(AlgorithmConfigurationVO.StochasticSPTNonDelay)){
			initialSolutionBuilder = StochasticSRT;
		}
		
		String control = null;
		if(algorithmDefinition.getMetaheuristic().equals(AlgorithmConfigurationVO.TABU_SEARCH_COMPLETE_NEIGHBORHOOD)){
			control = TABU_SEARCH_COMPLETE_NEIGHBORHOOD;
		}else if(algorithmDefinition.getMetaheuristic().equals(AlgorithmConfigurationVO.TABU_SEARCH_RESTRICTED_NEIGHBORHOOD)){
			control = TABU_SEARCH_RESTRICTED_NEIGHBORHOOD;
		}else if(algorithmDefinition.getMetaheuristic().equals(AlgorithmConfigurationVO.SIMULATED_ANNELING)){
			control = SIMULATED_ANNEALING;
		}else if(algorithmDefinition.getMetaheuristic().equals(AlgorithmConfigurationVO.GRASP)){
			control = GRASP;
			multiStart = true;
		}
		
		String neighborhoodCalculator = null;
		if(algorithmDefinition.getNeighborhood().equals(AlgorithmConfigurationVO.RANDOM_NEIGHBORHOOD)){
			neighborhoodCalculator = RANDOM_NEIGHBORHOOD;
		}else if(algorithmDefinition.getNeighborhood().equals(AlgorithmConfigurationVO.API_NEIGHBORHOOD)){
			neighborhoodCalculator = API_NEIGHBORHOOD;
		}else if(algorithmDefinition.getNeighborhood().equals(AlgorithmConfigurationVO.CRITICAL_ROUTE_ADJACENT_NEIGHBORHOOD)){
			neighborhoodCalculator = CRITICAL_ROUTE_ADJACENT_NEIGHBORHOOD;
		}else if(algorithmDefinition.getNeighborhood().equals(AlgorithmConfigurationVO.CRITICAL_ROUTE_ADJACENT_MACHINES_NEIGHBORHOOD)){
			neighborhoodCalculator = CRITICAL_ROUTE_ADJACENT_MACHINES_NEIGHBORHOOD;
		}else if(algorithmDefinition.getNeighborhood().equals(AlgorithmConfigurationVO.CRITICAL_BLOCK_RANDOM_NEIGHBORHOOD)){
			neighborhoodCalculator = CRITICAL_BLOCK_RANDOM_NEIGHBORHOOD;
		}else if(algorithmDefinition.getNeighborhood().equals(AlgorithmConfigurationVO.CRITICAL_BLOCK_ADJACENT_ON_END_NEIGHBORHOOD)){
			neighborhoodCalculator = CRITICAL_BLOCK_ADJACENT_ON_END_NEIGHBORHOOD;
		}else if(algorithmDefinition.getNeighborhood().equals(AlgorithmConfigurationVO.CRITICAL_BLOCK_END_START_ANY_NEIGHBORHOOD)){
			neighborhoodCalculator = CRITICAL_BLOCK_END_START_ANY_NEIGHBORHOOD;
		}else if(algorithmDefinition.getNeighborhood().equals(AlgorithmConfigurationVO.CRITICAL_WEIGHTED_NODES_NEIGHBORHOOD)){
			neighborhoodCalculator = CRITICAL_WEIGHTED_NODES_NEIGHBORHOOD;
		}
		
		String modifier = null;
		if(algorithmDefinition.getModifier().equals(AlgorithmConfigurationVO.LEFT_INSERTION)){
			modifier = LEFT_INSERTION;
		}else if(algorithmDefinition.getModifier().equals(AlgorithmConfigurationVO.RANDOM_MODFIER)){
			modifier = RANDOM_MODFIER;
		}else if(algorithmDefinition.getModifier().equals(AlgorithmConfigurationVO.SWAP)){
			modifier = SWAP;
		}else if(algorithmDefinition.getModifier().equals(AlgorithmConfigurationVO.RIGHT_INSERTION)){
			modifier = RIGHT_INSERTION;
		}
		
		String representation = null;
		if(algorithmDefinition.getRepresentation().equals(AlgorithmConfigurationVO.GRAPH)){
			representation = GRAPH;
		}else if(algorithmDefinition.getRepresentation().equals(AlgorithmConfigurationVO.VECTOR)){
			representation = VECTOR;
		}
		
		String gammaCalculator = null;
		String gammaBKS = null;
		if(algorithmDefinition.getObjectiveFunction().equals(AlgorithmConfigurationVO.CMAX)){
			gammaCalculator = CMAX;
			gammaBKS = CMAX_BKS;
		}else if(algorithmDefinition.getObjectiveFunction().equals(AlgorithmConfigurationVO.MEAN_FLOW_TIME)){
			gammaCalculator = MEAN_FLOW_TIME;
			gammaBKS = MEAN_FLOW_TIME_BKS;
		}
		
		Properties algorithmConfiguration = new Properties();
		
		// Scheduling algorithm configuration
		algorithmConfiguration.setProperty("scheduling.structureFactory", representation);
		algorithmConfiguration.setProperty("scheduling.neighborCalculator", neighborhoodCalculator);
		algorithmConfiguration.setProperty("scheduling.modifier", modifier);
		algorithmConfiguration.setProperty("scheduling.gammaCalculator", gammaCalculator);
		algorithmConfiguration.setProperty("scheduling.control", control);
		algorithmConfiguration.setProperty("scheduling.initialSolutionBuilder", initialSolutionBuilder);
		algorithmConfiguration.setProperty("scheduling.parametersLoader", control + "ParametersLoader");
		
		// Execution report configuration
		String consolidateTable = "false";
		String initialSolutions = "false";
		String finalSolutions = "false";
		
		ArrayList<String> reportConfiguration = algorithmDefinition.getReportConfiguration();
		for (String report : reportConfiguration) {
			if(report.equals(AlgorithmConfigurationVO.CONSOLIDATION_TABLE)){
				consolidateTable = "true";
			}else if(report.equals(AlgorithmConfigurationVO.INITIAL_SOLUTIONS)){
				initialSolutions = "true";
			}else if(report.equals(AlgorithmConfigurationVO.FINAL_SOLUTIONS)){
				finalSolutions = "true";
			}
		}
		algorithmConfiguration.setProperty("report.consolidationTable", consolidateTable);
		algorithmConfiguration.setProperty("report.gantt.initialsolutions", initialSolutions);
		algorithmConfiguration.setProperty("report.gantt.bestsolutions", finalSolutions);
		
		String currentBks = "gamma." + gammaBKS + ".bks.";
		
		ArrayList<String> selectedBetas = algorithmDefinition.getSelectedBetas();
		boolean travelTimeSelected = false;
		boolean setupSelected = false;
		
		for (String selectedBeta : selectedBetas) {
			if(selectedBeta.equals(AlgorithmConfigurationVO.TRAVEL_TIMES)){
				travelTimeSelected = true;
			}else if(selectedBeta.equals(AlgorithmConfigurationVO.SETUP_TIMES)){
				setupSelected = true;
			}
		}
		
		if(!travelTimeSelected && !setupSelected){
			currentBks += "om";
		}
		else if(!travelTimeSelected && setupSelected){
			currentBks += "om.s";
		}
		else if(travelTimeSelected && !setupSelected){
			currentBks += "om.tt";
		}
		else if(travelTimeSelected && setupSelected){
			currentBks += "om.tt.s";
		}
		
		System.out.println("currentBks: " + currentBks);
		
		// Metaheuristic's parameters
		ArrayList<ParameterVO> parameters = algorithmDefinition.getMetaheuristicParams();
		for (ParameterVO parameterVO : parameters) {
			algorithmConfiguration.setProperty(parameterVO.getName(), parameterVO.getValue());
		}
		
		// Launching instances
		// TODO: Make this execution parallel
		for (String instance : instancesToExecute) {
			String problemFile = "./data/FilesIndex/" + instance.substring(0, 5) + "/" + instance + ".properties";
			if(instance.substring(5, 8).equals("x02")){
				problemFile = "./data/FilesIndex/" + instance.substring(0, 8) + "/" + instance + ".properties";
			}
			
			Properties problem = loadProductConfiguration(new File(problemFile));
			boolean hasOptimal = false;
			if(instance.substring(0, 5).equals("04x04")||instance.substring(0, 5).equals("05x05")){
				hasOptimal=true;
			}
		
			SchedulingAlgorithm algorithm = null;
			
			if(!multiStart){
				algorithm = new TrajectoryBasedAlgorithm(algorithmConfiguration, problem, currentBks, instanceType, hasOptimal);
			}else{
				algorithm = new MultiStartAlgorithm(algorithmConfiguration, problem, currentBks, instanceType, hasOptimal);
			}
			
			ArrayList<ExecutionResults> results = new ArrayList<ExecutionResults>();
			for (int i = 0; i < algorithmDefinition.getAmountOfExecutionsPerInstance(); i++) {
				ExecutionResults result = algorithm.execute(instance);
				results.add(result);
			}
			ChartPrinter.getInstance().addResults(results);
		}
		ChartPrinter.getInstance().printGlobalResultsHTML(resultsFile);
	}
	
	// ------------------------------------------------------------
	// Utilities
	// ------------------------------------------------------------
	
	/**
	 * Loads the configuration of the scheduling algorithm from a properties
	 * file.
	 * 
	 * @param file
	 *            . File that contains the configuration of the scheduling
	 *            algorithm.
	 * @return data. Properties object with the configuration of the scheduling
	 *         algorithm.
	 * @throws Exception. Either
	 *             the file does not exist or it does not exist.
	 */
	private Properties loadProductConfiguration(File file) {
		Properties data = new Properties();

		try {
			System.out.println(file);
			FileInputStream in = new FileInputStream(file);
			data.load(in);
			System.out.println(data);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
}