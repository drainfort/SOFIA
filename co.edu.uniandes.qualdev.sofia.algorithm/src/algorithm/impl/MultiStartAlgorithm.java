package algorithm.impl;

import java.util.Properties;

import algorithm.SchedulingAlgorithm;

import structure.IStructure;
import common.utils.ExecutionResults;

public class MultiStartAlgorithm extends SchedulingAlgorithm {

	// -----------------------------------------------
	// Attributes
	// -----------------------------------------------
	
	/**
	 * Number of the start for the multistart algorithm
	 */
	private int startsNumber;
	
	
	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------
	/**
	 * Constructor of the class 
	 * @param algorithmConfiguration
	 * @param problemConfiguration
	 * @param currentBks
	 * @param instanceType
	 * @param hasOptimal
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	public MultiStartAlgorithm(Properties algorithmConfiguration, Properties problemConfiguration, String currentBks, String instanceType, boolean hasOptimal)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, Exception {
		super(algorithmConfiguration, problemConfiguration, currentBks, instanceType, hasOptimal);
		
		startsNumber = 500;
	}
	
	// -----------------------------------------------
	// Methods
	// -----------------------------------------------

	@Override
	public ExecutionResults execute(String instanceName, String instanceFile)
			throws Exception {

		double bestGamma = Integer.MAX_VALUE;
		ExecutionResults bestResults = null;
		boolean optimal = false;
		int neighborAmount = 0;
		
		for (int i = 0; i < startsNumber && !optimal; i++) {
			IStructure initialSolution = constructiveAlgorithm.getInitialSolutionBuilder().createInitialSolution(problem.getProblemFiles(), problem.getBetas(), 
					problem.getStructrureFactory(), iterativeAlgorithm.getGammaCalculator(), iterativeAlgorithm.getDecodingStrategy());
			
			System.out.print("s"+i+": ");
			ExecutionResults results = iterativeAlgorithm.execute(problem, initialSolution);
				
			if(results.getBestCmax() < bestGamma){
				bestResults = results;
				bestGamma = results.getBestCmax();

				if( results.getBestCmax() <= problem.getCurrentBksValue()){
					optimal = true;
					System.out.println("******* Optimal found: " + results.getBestCmax() );
				}
			}
			neighborAmount += results.getNumberOfVisitedNeighbors();
			
			results.setInstanceName(instanceName);
		}
		
		bestResults.setNumberOfVisitedNeighbors(neighborAmount);
		return bestResults;
	}
}
