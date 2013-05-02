package control.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import structure.IStructure;

import common.types.PairVO;
import common.utils.ExecutionResults;
import control.Control;

import modifier.IModifier;
import neighborCalculator.INeighborCalculator;
import gammaCalculator.IGammaCalculator;

/**
 * Class that implements the iterative phase of the GRASP metaheuristic
 * 
 * @author Juan Pablo Caballero
 */
public class GRASP extends Control {

	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------

	public GRASP() {
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
		
		// Obtaining the parameters from the algorithm configuration.
		executionResults.setOptimal(optimal);
		
		int maxNumberImprovements =0;
		if(params.get("maxNumberImprovements")!=null){
			maxNumberImprovements = (Integer)params.get("maxNumberImprovements");
		}
		
		boolean optimalAchieved = false;
		
		if(optimal.intValue() >= bestGamma){
			System.out.println("****Optimal CMax found!");
			optimalAchieved = true;
		}else{
			ArrayList<PairVO> arrayNeighbors = neighborCalculator.calculateNeighborhood(initialSolution, 0, (initialSolution.getTotalStations() * initialSolution.getTotalJobs()) - 1);
			Integer k = arrayNeighbors.size();
			
			//Fase de busqueda local Best-First
			while (k > 0 && !optimalAchieved){
				IStructure candidate = modifier.performModification(arrayNeighbors.get(k-1), best);
				numberOfVisitedNeighbors++;
				int gammaCandidate = gammaCalculator.calculateGamma(candidate);
				int delta = bestGamma-gammaCandidate;
				
				if(delta > 0){
					best = candidate.cloneStructure();
					bestGamma = gammaCandidate;
					
					if(optimal.intValue() >= bestGamma){
						System.out.print(" ******* Optimal CMax found!");
						optimalAchieved = true;
					}
					if( maxNumberImprovements!=0){
						if(yuSolution>=bestGamma){
							maxNumberImprovements--;
							if(maxNumberImprovements<=0){
								optimalAchieved = true;
							}
						}
					}
					k = arrayNeighbors.size();
				}
				k--;
			}
		}
		ExecutionResults result = obtainExecutionResults(best, gammaCalculator);
		result.setNumberOfVisitedNeighbors(numberOfVisitedNeighbors);
		return result;
	}	
}