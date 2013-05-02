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

public class TabuSearch extends Control {

	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------

	public TabuSearch() {
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
		int GammaInitialSolution = gammaCalculator
				.calculateGamma(initialSolution);
		executionResults.setInitialCmax(GammaInitialSolution);
		this.So = initialSolution.cloneStructure();

		int vectorSize = initialSolution.getTotalJobs() * initialSolution.getTotalStations() ;
		int tabuSize = vectorSize;

		IStructure current = initialSolution.cloneStructure();

		// Initializes the best solution (XBest) as the first one (X)
		IStructure best = current.cloneStructure();
		int bestGamma = gammaCalculator.calculateGamma(best);
		System.out.println("initial solution (XBestCMax): " + bestGamma);

		executionResults.setOptimal(optimal);

		boolean optimalAchieved = false;

		if (optimal.intValue() >= bestGamma) {
			System.out.println("Optimal CMax found!");
			optimalAchieved = true;
		}

		int count = 0;
		ArrayList<PairVO> arrayTabu = new ArrayList<PairVO>();
		int tabuIndex = 0;

		// parameter
		int iterations = vectorSize * 10000;
		// parameter
		int nonimproving = (int) (iterations * (Double) params.get("non-improving"));
		int maxNumberImprovements =0;
		if(params.get("maxNumberImprovements")!=null){
			maxNumberImprovements = (Integer)params.get("maxNumberImprovements");
		}

		// parameter
		double percent =(Double) params.get("percent");
		int neighborhodSize = (int) ((vectorSize * (vectorSize - 1)) / 2 * percent);

		ArrayList<PairVO> arrayNeighbors = neighborCalculator
				.calculateNeighborhood(current, 0, neighborhodSize);

		while (iterations >= 0 && nonimproving >= 0 && !optimalAchieved) {
			IStructure bestCandidate = null;
			int gammaBestCandidate = Integer.MAX_VALUE;
			PairVO bestPairCandidate = null;

			for (int index = 0; index < arrayNeighbors.size()
					&& !optimalAchieved; index++) {
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
			}

			if (bestCandidate != null) {
				if (gammaBestCandidate < bestGamma) {
					best = bestCandidate.cloneStructure();
					bestGamma = gammaBestCandidate;
					nonimproving = (int) (iterations * (Double) params.get("non-improving"));
					System.out.println("Improvement: " + bestGamma);

					if (optimal.intValue() >= bestGamma) {
						System.out.println("Optimal CMax found!");
						optimalAchieved = true;
					}
					if( maxNumberImprovements!=0){
						if(yuSolution>=bestGamma){
							maxNumberImprovements--;
							if(maxNumberImprovements<=0){
								System.out.println("Yu improved " + (Integer)params.get("maxNumberImprovements") + " times during iterative phase!");
								optimalAchieved = true;
							}
						}
					}

				}
				// System.out.println("current: " + current.getVector());
				current = bestCandidate.cloneStructure();
				count++;

				if (tabuIndex > tabuSize) {
					arrayTabu.remove(0);
				}
				arrayTabu.add(bestPairCandidate);
				tabuIndex++;

			}
			// Avance while
			iterations--;
			nonimproving--;
			arrayNeighbors = neighborCalculator.calculateNeighborhood(current,
					0,
					(current.getTotalStations() * current.getTotalJobs()) - 1);
		}

		System.out.println();
		ExecutionResults result = obtainExecutionResults(best, gammaCalculator);
		result.setNumberOfVisitedNeighbors(numberOfVisitedNeighbors);
		return result;
	}
}