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
	
	/**
	 * Constructor of the class
	 * @param currentBks - current restrictions of the scheduling problem
	 * @param algorithmConfiguration - Parameter configuration of the algorithm
	 * @param problemConfiguration - Problem configuration
	 * @param instanceType - Benchmark of the instance
	 * @param hasOptimal - If the instance has an optimal solution
	 * @throws InstantiationException - Parameter error
	 * @throws IllegalAccessException - Parameter error
	 * @throws ClassNotFoundException - Parameter error
	 * @throws Exception - Functionality error
	 */
	public TrajectoryBasedAlgorithm(Properties algorithmConfiguration, Properties problemConfiguration, String currentBks, String instanceType, boolean hasOptimal) throws InstantiationException, IllegalAccessException, ClassNotFoundException, Exception{
		super(algorithmConfiguration, problemConfiguration, currentBks, instanceType,hasOptimal);
	}
	
	// -------------------------------------------
	// Methods
	// -------------------------------------------
	
	public ExecutionResults execute(String instanceName, String resultFile) throws Exception {
		long initialExecutionTime = System.currentTimeMillis();
		
		IStructure initialSolution = constructiveAlgorithm.getInitialSolutionBuilder().createInitialSolution(problem.getProblemFiles(), problem.getBetas(), 
				problem.getStructrureFactory(), iterativeAlgorithm.getGammaCalculator(), iterativeAlgorithm.getDecodingStrategy());
		problem.setInstanceName(instanceName);
		problem.setResultFile(resultFile);
		
		ExecutionResults results = iterativeAlgorithm.execute(problem, initialSolution);
		long finalExecutionTime = System.currentTimeMillis();
		
		results.setExecutionTime(finalExecutionTime-initialExecutionTime);
		results.setInstanceName(instanceName);
		results.setParameters(algorithmConfiguration);
		
		return results;
	}

}
