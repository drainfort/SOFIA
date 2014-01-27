package control.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

import structure.IOperation;
import structure.IStructure;

import common.types.PairVO;
import common.utils.ExecutionLogger;
import common.utils.ExecutionResults;
import control.Control;

import modifier.IModifier;
import neighborCalculator.INeighborCalculator;
import gammaCalculator.IGammaCalculator;

public class IteratedTabuSearch extends Control {

	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------
	
	TabuSearchRN a = new TabuSearchRN();

	public IteratedTabuSearch() {
		super();
	}

	// -----------------------------------------------
	// Methods
	// -----------------------------------------------

	@Override
	public ExecutionResults execute(IStructure So,
			INeighborCalculator neighborCalculator, ArrayList<IModifier> modifiers,
			IGammaCalculator gammaCalculator, Properties params, Integer optimal, boolean isOptimal)
			throws Exception {
		
		IModifier modifier = modifiers.get(0);
		long startTime = System.currentTimeMillis();
		long stopTime = Integer.MAX_VALUE;
		
		if(params.get("maxExecutionTime")!=null ){
			if((Integer) params.get("maxExecutionTime")!=-1)
				stopTime = (Integer) params.get("maxExecutionTime") * 1000;	
		}
		
		executionResults = new ExecutionResults();
		executionResults.setOptimal(optimal);
		
		// So		So - initial solution
		// GammaSo	f(So) - objective function
		// Sb		S* - best solution known
		// Sg		S^ - current solution
		
		this.So = So.cloneStructure();
		double GammaSo = gammaCalculator.calculateGamma(this.So);
		System.out.println("initial solution: " + GammaSo);
		executionResults.setInitialCmax(GammaSo);
		
		boolean optimalAchieved = false;
		IStructure Sb = null;
		double GammaSb = 0;
		
		if (optimal.intValue() >= GammaSo) {
			if(isOptimal){
				System.out.println("Optimal found in the constructive phase!");
				optimalAchieved = true;
				Sb = So.cloneStructure();
				GammaSb = gammaCalculator.calculateGamma(Sb);
			}
		}

		if(!optimalAchieved){
			IStructure Sg = improveByTabuSearch(So, neighborCalculator, modifier, gammaCalculator, params, optimal, isOptimal, startTime);
			double GammaSg = gammaCalculator.calculateGamma(Sg);
			if (optimal.intValue() >= GammaSg) {
				Sb = Sg.cloneStructure();
				GammaSb = GammaSg;
				
				System.out.println("Optimal found!");
				optimalAchieved = true;
			}
			
			Sb = Sg.cloneStructure();
			GammaSb = gammaCalculator.calculateGamma(Sb);
			System.out.println("Improvement: " + GammaSb);
			
			int iter = (Integer) params.get("outerIterations");
			while(iter > 0 && !optimalAchieved){
				IStructure Sa = perturbate(Sg);
				
				System.out.println();
				System.out.println("Perturbation** " + gammaCalculator.calculateGamma(Sa));
				
				IStructure Sv = improveByTabuSearch(Sa, neighborCalculator, modifier, gammaCalculator, params, optimal, isOptimal, startTime);
				
				Sg = Sv.cloneStructure();
				GammaSg = gammaCalculator.calculateGamma(Sg);
				
				if (optimal.intValue() >= GammaSg) {
					Sb = Sg.cloneStructure();
					GammaSb = GammaSg;
					System.out.println("Optimal found!");
					optimalAchieved = true;
					break;
				}
				if(GammaSg < GammaSb){
					Sb = Sv.cloneStructure();
					GammaSb = GammaSg;
				}
				iter--;
				Sa.clean();
				
				long actualTime = System.currentTimeMillis();
			    long elapsedTime = actualTime - startTime;
			    
			    if(elapsedTime>=stopTime){
			    	System.out.println("TIME!!!");
			    	optimalAchieved = true;
			    	executionResults.setStopCriteria(2);
			    }
			
			}
		}
		
		ExecutionResults result = obtainExecutionResults(Sb, gammaCalculator, (Boolean)params.get("printTable"), (Boolean)params.get("printSolutions"),(Boolean)params.get("printInitialSolution"), (Boolean)params.get("printLog"), 0);
		result.setExecutionTime(System.currentTimeMillis() - startTime);
		System.out.println();
		return result;
	}

	
	public IStructure improveByTabuSearch(IStructure initialSolution,
			INeighborCalculator neighborCalculator, IModifier modifier,
				IGammaCalculator gammaCalculator, Properties params, Integer optimal, 
					boolean isOptimal, long startTime) throws Exception {
			
		ExecutionLogger.getInstance().initializeLogger(resultFile, instanceName);
		int numberNeighbors = executionResults.getNumberOfVisitedNeighbors();

		long stopTime = Integer.MAX_VALUE;
		
		if(params.get("maxExecutionTime")!=null ){
			if((Integer) params.get("maxExecutionTime")!=-1)
				stopTime = (Integer) params.get("maxExecutionTime") * 1000;	
		}
		
		double GammaInitialSolution = gammaCalculator.calculateGamma(initialSolution);
		
		executionResults = new ExecutionResults();
		executionResults.setInitialCmax(GammaInitialSolution);
		
		this.So = initialSolution.cloneStructure();
		int tabuSize = (Integer) params.get("tabulist-size");
		IStructure current = initialSolution.cloneStructure();

		// Initializes the best solution (XBest) as the first one (X)
		IStructure best = current.cloneStructure();
		double bestGamma = gammaCalculator.calculateGamma(best);
		ExecutionLogger.getInstance().printLog("initial solution: " + bestGamma);
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
		int nonImprovingOut = (Integer) params.get("non-improving-out");
		
		if(nonImprovingOut<0)
			nonImprovingOut= Integer.MAX_VALUE;
		
		// parameters
		long neighborhodSize =  (Integer) params.get("neighborhodSize");	
		int n = initialSolution.getOperations().size();
		int r = 2;
		long nPr = permutacion(n, r);	
		
		if(neighborhodSize > nPr && nPr > 0)
			neighborhodSize = nPr;
		
		ArrayList<PairVO> arrayNeighbors = neighborCalculator.calculateNeighborhood(current, neighborhodSize);
		
		while (iterations >= 0 && nonImprovingOut >= 0 && !optimalAchieved) {
			IStructure bestCandidate = null;
			double gammaBestCandidate = Integer.MAX_VALUE;
			PairVO bestPairCandidate = null;
			
			int nonImprovingIn = (Integer) params.get("non-improving-in");
			if(nonImprovingIn<0)
				nonImprovingIn= Integer.MAX_VALUE;

			for (int index = 0; index < arrayNeighbors.size() && !optimalAchieved && nonImprovingIn>=0; index++) {
				numberNeighbors+=1;
				PairVO pairCandidate = arrayNeighbors.get(index);
				IStructure candidate = modifier.performModification(pairCandidate,current);
				
				double gammaCandidate = gammaCalculator.calculateGamma(candidate);
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
							nonImprovingIn = (Integer) params.get("non-improving-in");
							if(nonImprovingIn<=0)
								nonImprovingIn=Integer.MAX_VALUE;
						}
					} else {
						bestCandidate = candidate.cloneStructure();
						gammaBestCandidate = gammaCandidate;
						bestPairCandidate = pairCandidate;
						nonImprovingIn = (Integer) params.get("non-improving-in");
						if(nonImprovingIn<=0)
							nonImprovingIn=Integer.MAX_VALUE;
					}
				}
				candidate.clean();
				nonImprovingIn--;
				
				long actualTime = System.currentTimeMillis();
			    long elapsedTime = actualTime - startTime;
			    
			    if(elapsedTime>=stopTime){
			    	optimalAchieved = true;
			    	executionResults.setStopCriteria(2);
			    }
			}

			if (bestCandidate != null) {
				if (gammaBestCandidate < bestGamma) {
					best = bestCandidate.cloneStructure();
					bestGamma = gammaBestCandidate;
					nonImprovingOut = (Integer) params.get("non-improving-out");
					
					if(nonImprovingOut <= 0)
						nonImprovingOut = Integer.MAX_VALUE;
					
					System.out.println("Improvement: " + bestGamma);
					ExecutionLogger.getInstance().printLog("Improvement: "+bestGamma);

					if (optimal.intValue() >= bestGamma) {
						if(isOptimal){
							System.out.println("optimal found!");
							System.out.println();
							optimalAchieved = true;
						}
						else{
							if (optimal.intValue() > bestGamma)
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
			}
			
			// Avance while
			iterations--;
			nonImprovingOut--;
			arrayNeighbors = neighborCalculator.calculateNeighborhood(current, neighborhodSize);
		}

		executionResults.setNumberOfVisitedNeighbors(numberNeighbors);

		return best;
	}
	
	public static long permutacion(int N, int r){
        long multi = 1;
        for (int i = N-r+1; i <= N; i++) {
            multi = multi * i;
        }
        return multi;
    }
	
	
	private IStructure perturbate(IStructure Sg) throws Exception {
		IStructure Sa = Sg.cloneStructure();
		ArrayList<IOperation> operations = Sa.getOperations();
		
		int pi = randomNumber((int) (Sa.getVector().size()/2), (int) Sa.getVector().size()-1);
		ArrayList<IOperation> toStruggle = new ArrayList<IOperation>();
		for (int i = pi; i < Sg.getVector().size(); i++) {
			toStruggle.add(operations.get(i));
		}
		
		for (int i = Sg.getVector().size() - 1; i >= pi; i--) {
			Sa.removeOperationFromSchedule(operations.get(i).getOperationIndex());
		}
		
		Collections.shuffle(toStruggle);
		
		for (int i = 0; i < toStruggle.size(); i++) {
			Sa.scheduleOperation(toStruggle.get(i).getOperationIndex());
		}
		Sa.calculateCMatrix(0);
		
		return Sa;
	}
	
	// -----------------------------------------------
	// Utilities
	// -----------------------------------------------
	
	/**
	 * Returns a random number in the interval between the min and the max
	 * parameters
	 * 
	 * @param min
	 *            . Lower value of the interval
	 * @param max
	 * @return
	 */
	private static int randomNumber(int min, int max) {
		return (int) Math.round((Math.random() * (max - min)) + min);
	}
}