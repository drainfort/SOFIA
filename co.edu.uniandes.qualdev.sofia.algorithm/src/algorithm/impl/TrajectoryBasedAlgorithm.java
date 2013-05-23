package algorithm.impl;

import java.util.Properties;

import algorithm.SchedulingAlgorithm;
import structure.IStructure;

import common.utils.ExecutionResults;

/**
 * Class for executing a trajectory based algorithm. A trajectory based algorithm
 * is an algorithm that from an only initial solution execute a metaheuristic.
 * 
 * @author David Mendez-Acuna
 * @author Juan Pablo Caballero-Villalobos
 */
public class TrajectoryBasedAlgorithm extends SchedulingAlgorithm {
	
	// -------------------------------------------
	// Constructor
	// -------------------------------------------
	
	public TrajectoryBasedAlgorithm(Properties algorithmConfiguration, Properties problemConfiguration, String currentBks) throws InstantiationException, IllegalAccessException, ClassNotFoundException, Exception{
		super(algorithmConfiguration, problemConfiguration, currentBks);
	}
	
	// -------------------------------------------
	// Methods
	// -------------------------------------------
	
	public ExecutionResults execute(String instanceName) throws Exception {
		long initialExecutionTime = System.currentTimeMillis();
		
		IStructure initialSolution = constructiveAlgorithm.getInitialSolutionBuilder().createInitialSolution(problem.getProblemFiles(), problem.getBetas(), problem.getStructrureFactory(), iterativeAlgorithm.getGammaCalculator());
		
		ExecutionResults results = iterativeAlgorithm.execute(problem, initialSolution);
		long finalExecutionTime = System.currentTimeMillis();
		
		results.setExecutionTime(finalExecutionTime-initialExecutionTime);
		results.setInstanceName(instanceName);
		
		return results;
	}

}
