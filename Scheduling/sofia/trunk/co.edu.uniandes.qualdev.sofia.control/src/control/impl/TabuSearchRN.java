package control.impl;

import java.util.ArrayList;
import java.util.Properties;

import structure.IStructure;

import common.types.PairVO;
import common.utils.ExecutionResults;
import control.Control;

import modifier.IModifier;
import neighborCalculator.INeighborCalculator;
import gammaCalculator.IGammaCalculator;

public class TabuSearchRN extends Control {

	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------

	public TabuSearchRN() {
		super();
	}

	// -----------------------------------------------
	// Methods
	// -----------------------------------------------

	@Override
	public ExecutionResults execute(IStructure initialSolution,
			INeighborCalculator neighborCalculator, IModifier modifier,
			IGammaCalculator gammaCalculator, Properties params, Integer optimal, boolean isOptimal)
			throws Exception {
		
		executionResults = new ExecutionResults();
		int numberOfVisitedNeighbors=0;
		int GammaInitialSolution = gammaCalculator.calculateGamma(initialSolution);
		executionResults.setInitialCmax(GammaInitialSolution);
		this.So = initialSolution.cloneStructure();

		int vectorSize = initialSolution.getTotalJobs() * initialSolution.getTotalStations() ;
		int a = (Integer) params.get("tabulist-size");
		int tabuSize = (int)a;

		IStructure current = initialSolution.cloneStructure();

		// Initializes the best solution (XBest) as the first one (X)
		IStructure best = current.cloneStructure();
		int bestGamma = gammaCalculator.calculateGamma(best);
		System.out.println("initial solution (XBestCMax): " + bestGamma);

		executionResults.setOptimal(optimal);

		int maxNumberImprovements =0;
		if(params.get("maxNumberImprovements")!=null){
			maxNumberImprovements = (Integer)params.get("maxNumberImprovements");
		}

		System.out.println(maxNumberImprovements);
		System.out.println(isOptimal);
		boolean optimalAchieved = false;

		if (optimal.intValue() >= bestGamma) {
			if(isOptimal){
				System.out.println("optimal found!");
				System.out.println();
				optimalAchieved = true;
			}
			else{
				if(maxNumberImprovements<=0){
					optimalAchieved = true;
				}
			}
		}

		ArrayList<PairVO> arrayTabu = new ArrayList<PairVO>();
		int tabuIndex = 0;

		int iterations = vectorSize * 10000;
		int nonimproving = (int) (iterations * (Double) params.get("non-improving"));
		
		// parameter
		double percent = (Double) params.get("percent");
		int neighborhodSize = (int) ((vectorSize * (vectorSize - 1)) / 2 * percent);
		ArrayList<PairVO> arrayNeighbors = neighborCalculator.calculateNeighborhood(current, neighborhodSize);

		while (iterations >= 0 && nonimproving >= 0 && !optimalAchieved) {
			IStructure bestCandidate = null;
			int gammaBestCandidate = Integer.MAX_VALUE;
			PairVO bestPairCandidate = null;

			for (int index = 0; index < arrayNeighbors.size() && !optimalAchieved; index++) {
				PairVO pairCandidate = arrayNeighbors.get(index);
				IStructure candidate = modifier.performModification(pairCandidate,current);
				
				numberOfVisitedNeighbors++;
				int gammaCandidate = gammaCalculator.calculateGamma(candidate);
				if (gammaCandidate <= gammaBestCandidate) {
					boolean tabu = false;
					for (int i = 0; i < arrayTabu.size() && !tabu; i++) {
						PairVO currentTabu = arrayTabu.get(i);
						if (currentTabu.equals(pairCandidate))
							tabu = true;
					}
					if (tabu) {
						// Criterio de aspiracion
						double acceptaceValue = Math.random();
						if (acceptaceValue <= 0.5) {
							bestCandidate = candidate.cloneStructure();
							gammaBestCandidate = gammaCandidate;
							bestPairCandidate = pairCandidate;
						}
					} else {
						bestCandidate = candidate.cloneStructure();
						gammaBestCandidate = gammaCandidate;
						bestPairCandidate = pairCandidate;
					}
				}
				candidate.clean();
			}

			if (bestCandidate != null) {
				if (gammaBestCandidate < bestGamma) {
					best = bestCandidate.cloneStructure();
					bestGamma = gammaBestCandidate;
					nonimproving = (int) (iterations * (Double) params.get("non-improving"));
					System.out.println("Improvement: " + bestGamma);

					if (optimal.intValue() >= bestGamma) {
						if(isOptimal){
							System.out.println("optimal found!");
							System.out.println();
							optimalAchieved = true;
						}
						else{
							maxNumberImprovements--;
							if(maxNumberImprovements<=0){
								optimalAchieved = true;
							}
						}
					}
					

				}
				
				current = bestCandidate.cloneStructure();
				bestCandidate.clean();

				if (tabuIndex > tabuSize) {
					arrayTabu.remove(0);
				}
				arrayTabu.add(bestPairCandidate);
				tabuIndex++;
			}
			
			// Avance while
			iterations--;
			nonimproving--;
			arrayNeighbors = neighborCalculator.calculateNeighborhood(current, neighborhodSize);
		}

		System.out.println();
		ExecutionResults result = obtainExecutionResults(best, gammaCalculator, (Boolean)params.get("printTable"), (Boolean)params.get("printSolutions"),(Boolean)params.get("printInitialSolution"));
		result.setNumberOfVisitedNeighbors(numberOfVisitedNeighbors);
		return result;
	}
}