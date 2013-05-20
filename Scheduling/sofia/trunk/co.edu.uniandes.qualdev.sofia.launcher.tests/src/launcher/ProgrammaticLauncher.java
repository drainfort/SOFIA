package launcher;

import java.util.ArrayList;
import java.util.Properties;

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
	
	// Metaheuristics
	public final static String TABU_SEARCH_COMPLETE_NEIGHBORHOOD = "control.impl.TabuSearchCN";
	public final static String TABU_SEARCH_RESTRICTED_NEIGHBORHOOD = "control.impl.TabuSearchRN";
	public final static String SIMULATED_ANNEALING = "control.impl.SimpleSimulatedAnnealing";
	public final static String GRASP = "control.impl.GRASPERLS";
	
	// Neighborhoods
	public static String RANDOM_NEIGHBORHOOD = "neighborCalculator.impl.Random";
	public static String CRITICAL_ADJACENT= "neighborCalculator.impl.AdjacentShiftOnCriticalRoutes";
	public static String CRITICAL_ADJACENT_MACHINES = "neighborCalculator.impl.AdjacentMachinesShiftOnCriticalRoutes";
	public static String CRITICAL_BLOCK = "neighborCalculator.impl.ShiftBlockCriticalRoute";
	public static String CRITICAL_BLOCK_ADJACENT = "neighborCalculator.impl.ShiftBlockADJCriticalRoute";
	public static String CRITICAL_BLOCK_ENDSTART = "neighborCalculator.impl.ShiftBlockEndStartAnyCriticalRoute";
	
	// Modifiers
	public static String RANDOM_MODFIER = "modifier.impl.RandomNeighbor";
	public static String SWAP = "modifier.impl.Swap";
	public static String LEFT_INSERTION = "modifier.impl.LeftInsertion";
	public static String RIGHT_INSERTION = "modifier.impl.RightInsertion";
	
	// Representation structure
	public static String GRAPH = "structure.impl.Graph";
	public static String VECTOR = "structure.impl.Vector";
	
	// Objective function
	public static String CMAX = "gammaCalculator.impl.CMaxCalculator";
	public static String MEAN_FLOW_TIME = "gammaCalculator.impl.MeanFlowTimeCalculator";
	
	// ------------------------------------------------------------
	// Constructor
	// ------------------------------------------------------------
	
	public ProgrammaticLauncher(){
		
	}
	
	// ------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------

	public void launch(ArrayList<String> instancesToExecute,
			AlgorithmConfigurationVO algorithmDefinition) {
		
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
		}
		System.out.println("initialSolutionBuilder: " + initialSolutionBuilder);
		
		String control = null;
		if(algorithmDefinition.getMetaheuristic().equals(AlgorithmConfigurationVO.TABU_SEARCH_COMPLETE_NEIGHBORHOOD)){
			control = TABU_SEARCH_COMPLETE_NEIGHBORHOOD;
		}else if(algorithmDefinition.getMetaheuristic().equals(AlgorithmConfigurationVO.TABU_SEARCH_RESTRICTED_NEIGHBORHOOD)){
			control = TABU_SEARCH_RESTRICTED_NEIGHBORHOOD;
		}else if(algorithmDefinition.getMetaheuristic().equals(AlgorithmConfigurationVO.SIMULATED_ANNELING)){
			control = SIMULATED_ANNEALING;
		}else if(algorithmDefinition.getMetaheuristic().equals(AlgorithmConfigurationVO.GRASP)){
			control = GRASP;
		}
		System.out.println("control: " + control);
		
		String neighborhoodCalculator = null;
		if(algorithmDefinition.getNeighborhood().equals(AlgorithmConfigurationVO.RANDOM_NEIGHBORHOOD)){
			neighborhoodCalculator = RANDOM_NEIGHBORHOOD;
		}else if(algorithmDefinition.getNeighborhood().equals(AlgorithmConfigurationVO.CRITICAL_ADJACENT)){
			neighborhoodCalculator = CRITICAL_ADJACENT;
		}else if(algorithmDefinition.getNeighborhood().equals(AlgorithmConfigurationVO.CRITICAL_ADJACENT_MACHINES)){
			neighborhoodCalculator = CRITICAL_ADJACENT_MACHINES;
		}else if(algorithmDefinition.getNeighborhood().equals(AlgorithmConfigurationVO.CRITICAL_BLOCK)){
			neighborhoodCalculator = CRITICAL_BLOCK;
		}else if(algorithmDefinition.getNeighborhood().equals(AlgorithmConfigurationVO.CRITICAL_BLOCK_ADJACENT)){
			neighborhoodCalculator = CRITICAL_BLOCK_ADJACENT;
		}else if(algorithmDefinition.getNeighborhood().equals(AlgorithmConfigurationVO.CRITICAL_BLOCK_ENDSTART)){
			neighborhoodCalculator = CRITICAL_BLOCK_ENDSTART;
		}
		System.out.println("neighborhoodCalculator: " + neighborhoodCalculator);
		
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
		System.out.println("modifier: " + modifier);
		
		String representation = null;
		if(algorithmDefinition.getRepresentation().equals(AlgorithmConfigurationVO.GRAPH)){
			representation = GRAPH;
		}else if(algorithmDefinition.getRepresentation().equals(AlgorithmConfigurationVO.VECTOR)){
			representation = VECTOR;
		}
		System.out.println("representation: " + representation);
		
		String gammaCalculator = null;
		if(algorithmDefinition.getObjectiveFunction().equals(AlgorithmConfigurationVO.CMAX)){
			gammaCalculator = CMAX;
		}else if(algorithmDefinition.getObjectiveFunction().equals(AlgorithmConfigurationVO.MEAN_FLOW_TIME)){
			gammaCalculator = MEAN_FLOW_TIME;
		}
		System.out.println("gammaCalculator: " + gammaCalculator);
		
		
		Properties algorithmConfiguration = new Properties();
		
		// Scheduling algorithm configuration
		algorithmConfiguration.setProperty("scheduling.structureFactory", representation);
		algorithmConfiguration.setProperty("scheduling.neighborCalculator", neighborhoodCalculator);
		algorithmConfiguration.setProperty("scheduling.modifier", modifier);
		algorithmConfiguration.setProperty("scheduling.gammaCalculator", gammaCalculator);
		algorithmConfiguration.setProperty("scheduling.control", control);
		algorithmConfiguration.setProperty("scheduling.initialSolutionBuilder", initialSolutionBuilder);
		algorithmConfiguration.setProperty("scheduling.parametersLoader", control + "ParametersLoader");
		
		// Metaheuristic's parameters
		ArrayList<ParameterVO> parameters = algorithmDefinition.getMetaheuristicParams();
		for (ParameterVO parameterVO : parameters) {
			algorithmConfiguration.setProperty(parameterVO.getName(), parameterVO.getValue());
		}
		
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
	}
}