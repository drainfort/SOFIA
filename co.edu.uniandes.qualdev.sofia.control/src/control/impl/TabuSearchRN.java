package control.impl;

import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import structure.IStructure;

import common.types.PairVO;
import common.utils.ExecutionLogger;
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
			
		ExecutionLogger.getInstance().initializeLogger(resultFile, instanceName);
		long startTime = System.currentTimeMillis();
		long stopTime = Integer.MAX_VALUE;
		if(params.get("maxExecutionTime")!=null ){
			if((Integer) params.get("maxExecutionTime")!=-1)
				stopTime = (Integer) params.get("maxExecutionTime") *1000;	
		}	
		executionResults = new ExecutionResults();
		int numberOfVisitedNeighbors=0;
		int GammaInitialSolution = gammaCalculator.calculateGamma(initialSolution);
		executionResults.setInitialCmax(GammaInitialSolution);
		this.So = initialSolution.cloneStructure();

		
		int a = (Integer) params.get("tabulist-size");
		int tabuSize = (int)a;

		IStructure current = initialSolution.cloneStructure();

		// Initializes the best solution (XBest) as the first one (X)
		IStructure best = current.cloneStructure();
		int bestGamma = gammaCalculator.calculateGamma(best);
		System.out.println("initial solution (XBestCMax): " + bestGamma);
		ExecutionLogger.getInstance().printLog("initial solution (XBestCMax): " + bestGamma);

		executionResults.setOptimal(optimal);

		int maxNumberImprovements = -1;
		if(params.get("maxNumberImprovements")!=null){
			maxNumberImprovements = (Integer)params.get("maxNumberImprovements");
		}
		
		boolean optimalAchieved = false;

		if (optimal.intValue() >= bestGamma) {
			if(isOptimal){
				System.out.println("optimal found!");
				System.out.println();
				optimalAchieved = true;
			}
		}

		ArrayList<PairVO> arrayTabu = new ArrayList<PairVO>();
		int tabuIndex = 0;

		int iterations =  (Integer) params.get("iterations");
		int nonimprovingout = (Integer) params.get("non-improving-out");
		
		if(nonimprovingout<0)
			nonimprovingout= Integer.MAX_VALUE;
		
		// parameter
		int neighborhodSize =  (Integer) params.get("neighborhodSize");		
		System.out.println(neighborhodSize);
		ArrayList<PairVO> arrayNeighbors = neighborCalculator.calculateNeighborhood(current, neighborhodSize);

		while (iterations >= 0 && nonimprovingout >= 0 && !optimalAchieved) {
			IStructure bestCandidate = null;
			int gammaBestCandidate = Integer.MAX_VALUE;
			PairVO bestPairCandidate = null;
			
			int nonimprovingin = (Integer) params.get("non-improving-in");
			if(nonimprovingin<0)
				nonimprovingin= Integer.MAX_VALUE;

			for (int index = 0; index < arrayNeighbors.size() && !optimalAchieved && nonimprovingin>=0; index++) {
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
							nonimprovingin = (Integer) params.get("non-improving-in");
							if(nonimprovingin<=0)
								nonimprovingin=Integer.MAX_VALUE;
						}
					} else {
						bestCandidate = candidate.cloneStructure();
						gammaBestCandidate = gammaCandidate;
						bestPairCandidate = pairCandidate;
						nonimprovingin = (Integer) params.get("non-improving-in");
						if(nonimprovingin<=0)
							nonimprovingin=Integer.MAX_VALUE;
					}
				}
				candidate.clean();
				nonimprovingin--;
			}

			if (bestCandidate != null) {
				if (gammaBestCandidate < bestGamma) {
					best = bestCandidate.cloneStructure();
					bestGamma = gammaBestCandidate;
					nonimprovingout = (Integer) params.get("non-improving-out");
					if(nonimprovingout<=0)
						nonimprovingout=Integer.MAX_VALUE;
					System.out.println("Improvement: " + bestGamma);
					ExecutionLogger.getInstance().printLog("Improvement: "+bestGamma);

					if (optimal.intValue() >= bestGamma) {
						if(isOptimal){
							System.out.println("optimal found!");
							System.out.println();
							optimalAchieved = true;
						}
						else{
							if (optimal.intValue() >bestGamma)
								maxNumberImprovements--;
							if(maxNumberImprovements==0){
								optimalAchieved = true;
								executionResults.setStopCriteria(3);
								System.out.println("Stop Criteria: Max number improvements");
								ExecutionLogger.getInstance().printLog("Stop Criteria: Max number improvements");
							}
						}
					}
					

				}
				
				current = bestCandidate.cloneStructure();
				bestCandidate.clean();

				if (tabuIndex > tabuSize) {
					arrayTabu.remove(0);
					tabuIndex--;
				}
				arrayTabu.add(bestPairCandidate);
				tabuIndex++;
				
				long actualTime = System.currentTimeMillis();
			    long elapsedTime = actualTime - startTime;
			    if(elapsedTime>=stopTime){
			    	optimalAchieved = true;
			    	executionResults.setStopCriteria(2);
			    	System.out.println("Stop Criteria: Max execution time");
			    	ExecutionLogger.getInstance().printLog("Stop Criteria: Max execution time");
			    }
			}
			
			// Avance while
			iterations--;
			nonimprovingout--;
			arrayNeighbors = neighborCalculator.calculateNeighborhood(current, neighborhodSize);
		}
		
		if(nonimprovingout<=0){
			executionResults.setStopCriteria(1);
			System.out.println("Stop Criteria: Non Improving");
			ExecutionLogger.getInstance().printLog("Stop Criteria: Non Improving");
		}

		System.out.println();
		ExecutionResults result = obtainExecutionResults(best, gammaCalculator, (Boolean)params.get("printTable"), (Boolean)params.get("printSolutions"),(Boolean)params.get("printInitialSolution"));
		result.setNumberOfVisitedNeighbors(numberOfVisitedNeighbors);
		return result;
	}
	
	public static long factorial(int N)
    {
        long multi = 1;
        for (int i = 1; i <= N; i++) {
            multi = multi * i;
        }
        return multi;
    }
}