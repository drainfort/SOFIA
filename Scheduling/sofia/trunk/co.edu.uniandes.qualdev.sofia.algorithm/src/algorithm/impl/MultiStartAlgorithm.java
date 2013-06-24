package algorithm.impl;

import java.util.Properties;

import algorithm.SchedulingAlgorithm;

import structure.IStructure;
import common.utils.ExecutionResults;

public class MultiStartAlgorithm extends SchedulingAlgorithm {

	private int startsNumber;
	
	public MultiStartAlgorithm(Properties algorithmConfiguration, Properties problemConfiguration, String currentBks, String instanceType, boolean hasOptimal)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, Exception {
		super(algorithmConfiguration, problemConfiguration, currentBks, instanceType, hasOptimal);
		
		startsNumber = 500;
	}

	@Override
	public ExecutionResults execute(String instanceName)
			throws Exception {

		int bestGamma = Integer.MAX_VALUE;
		ExecutionResults bestResults = null;
		boolean optimal = false;
		int neighborAmount = 0;
		
		for (int i = 0; i < startsNumber && !optimal; i++) {
			IStructure initialSolution = constructiveAlgorithm.getInitialSolutionBuilder().createInitialSolution(problem.getProblemFiles(), problem.getBetas(), problem.getStructrureFactory(), iterativeAlgorithm.getGammaCalculator());
			
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
