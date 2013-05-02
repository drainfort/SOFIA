package algorithm.impl;

import java.util.Properties;

import algorithm.SchedulingAlgorithm;

import structure.IStructure;
import common.utils.ExecutionResults;

public class MultiStartAlgorithm extends SchedulingAlgorithm {

	private int startsNumber;
	
	public MultiStartAlgorithm(Properties algorithmConfiguration, Properties problemConfiguration)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, Exception {
		super(algorithmConfiguration, problemConfiguration);
		
		startsNumber = 500;
	}

	@Override
	public ExecutionResults execute(String resultsFile, String instanceName)
			throws Exception {

		int bestGamma = Integer.MAX_VALUE;
		ExecutionResults bestResults = null;
		boolean optimal = false;
		
		for (int i = 0; i < startsNumber && !optimal; i++) {
			IStructure initialSolution = constructiveAlgorithm.getInitialSolutionBuilder().createInitialSolution(problem.getProblemFiles(), problem.getBetas(), problem.getStructrureFactory(), iterativeAlgorithm.getGammaCalculator());
			
			System.out.print("s"+i+": ");
			ExecutionResults results = iterativeAlgorithm.execute(problem, initialSolution);
				
			if(results.getBestCmax() < bestGamma){
				bestResults = results;
				bestGamma = results.getBestCmax();

				if( results.getBestCmax() <= problem.getOptimal()){
					optimal = true;
					System.out.println("******* Optimal found: " + results.getBestCmax() );
				}
			}
			
			results.setInstanceName(instanceName);
		}
				
		return bestResults;
	}
}
