package control.impl;

import java.util.Properties;

import structure.IStructure;

import common.types.PairVO;
import common.utils.ExecutionResults;
import control.Control;

import modifier.IModifier;
import neighborCalculator.INeighborCalculator;
import gammaCalculator.IGammaCalculator;

public class RandomWalk extends Control {

	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------

	public RandomWalk() {
		super();
	}

	// -----------------------------------------------
	// Methods
	// -----------------------------------------------

	@Override
	public ExecutionResults execute(IStructure initialSolution,
			INeighborCalculator neighborCalculator, IModifier modifier,
			IGammaCalculator gammaCalculator, Properties params, Integer optimal, int yuSolution)
			throws Exception {
		
		int numberOfVisitedNeighbors=0;
		int GammaInitialSolution = gammaCalculator.calculateGamma(initialSolution);
		executionResults.setInitialCmax(GammaInitialSolution);
		this.So = initialSolution.cloneStructure();
		
		// Initializes the best solution (XBest) as the first one (X)
		IStructure best = initialSolution.cloneStructure();
		int bestGamma = gammaCalculator.calculateGamma(best);
		System.out.println("initial solution (XBestCMax): " + bestGamma);
		
		IStructure candidate =best.cloneStructure();
			
		// Obtaining the parameters from the algorithm configuration.
		//Integer optimal = (Integer) params.get("optimal");
		executionResults.setOptimal(optimal);
		
		boolean optimalAchieved = false;
		
		if(optimal.intValue() >= bestGamma){
			System.out.println("****Optimal CMax found!");
			optimalAchieved = true;
		}else{
			
			
			Integer k = (Integer) params.get("k");
			
			while (k > 0 && !optimalAchieved){
				
				PairVO arrayNeighbor = neighborCalculator.calculateNeighbor(best);
				candidate = modifier.performModification(arrayNeighbor, candidate);
				numberOfVisitedNeighbors++;
				int gammaCandidate = gammaCalculator.calculateGamma(candidate);
				
				if(bestGamma > gammaCandidate){
					best = candidate.cloneStructure();
					bestGamma = gammaCandidate;
					
					System.out.println("Improvement: " + gammaCandidate );
					
					if(optimal.intValue() >= bestGamma){
						System.out.print(" ******* Optimal CMax found!");
						optimalAchieved = true;
					}			
				}
				k--;
			}
		}
		System.out.println();
		ExecutionResults result = obtainExecutionResults(best, gammaCalculator);
		result.setNumberOfVisitedNeighbors(numberOfVisitedNeighbors);
		return result;
		

		



	}
}