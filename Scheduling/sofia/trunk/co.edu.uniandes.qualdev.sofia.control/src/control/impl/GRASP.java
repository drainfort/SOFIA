package control.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import structure.IStructure;
import structure.impl.CriticalPath;

import common.types.PairVO;
import common.utils.ExecutionLogger;
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
			INeighborCalculator neighborCalculator, ArrayList<IModifier> modifiers,
			IGammaCalculator gammaCalculator, Properties params, Integer optimal, boolean isOptimal)
			throws Exception {

		
		IModifier modifier = modifiers.get(0);
		int numberOfVisitedNeighbors=0;
		double gammaInitialSolution = gammaCalculator.calculateGamma(initialSolution);
		executionResults = new ExecutionResults();
		executionResults.setInitialCmax(gammaInitialSolution);
		this.So = initialSolution.cloneStructure();
		
		// Initializes the best solution (XBest) as the first one (X)
		IStructure best = initialSolution.cloneStructure();
		double bestGamma = gammaCalculator.calculateGamma(best);
		
		// Obtaining the parameters from the algorithm configuration.
		executionResults.setOptimal(optimal);
		
		int maxNumberImprovements =0;
		if(params.get("maxNumberImprovements")!=null){
			maxNumberImprovements = (Integer)params.get("maxNumberImprovements");
		}
		
		long startTime = System.currentTimeMillis();
		
		long stopTime = Integer.MAX_VALUE;
		
		if(params.get("maxExecutionTime")!=null ){
			if((Integer) params.get("maxExecutionTime")!=-1)
				stopTime = (Integer) params.get("maxExecutionTime") * 1000;	
		}
		
		boolean optimalAchieved = false;
		
		if(optimal.intValue() >= bestGamma){
			if(optimal.intValue() >= bestGamma){
				if(isOptimal){
					System.out.println("optimal found!");
					System.out.println();
					optimalAchieved = true;
				}
				else{
					if(maxNumberImprovements==0){
						optimalAchieved = true;
					}
				}
			}
		}else{
			ArrayList<PairVO> arrayNeighbors = neighborCalculator.calculateNeighborhood(initialSolution, (initialSolution.getTotalStations() * initialSolution.getTotalJobs()) - 1);
			Integer k = arrayNeighbors.size();
			
			//Fase de busqueda local Best-First
			while (k > 0 && !optimalAchieved){
				IStructure candidate = modifier.performModification(arrayNeighbors.get(k-1), best);
				numberOfVisitedNeighbors++;
				double gammaCandidate = gammaCalculator.calculateGamma(candidate);
				double delta = bestGamma-gammaCandidate;
				
				if(delta > 0){
					best = candidate.cloneStructure();
					bestGamma = gammaCandidate;
					
					ExecutionLogger.getInstance().printLog("Improvement: "+bestGamma);
					if(ExecutionLogger.getInstance().isUseLogger()){
						ExecutionLogger.getInstance().printLog("Vector: "+arrayNeighbors.get(k-1));
						ArrayList<CriticalPath> paths = best.getCriticalPaths();
						ExecutionLogger.getInstance().printLog("Critical Paths: "+paths);
						ExecutionLogger.getInstance().printLog("Pair X: "+arrayNeighbors.get(k-1).getoX());
						ExecutionLogger.getInstance().printLog("Pair X: "+arrayNeighbors.get(k-1).getoY());
						boolean contains = false;
						for (int i = 0; i < paths.size() && !contains; i++) {
							contains = paths.get(i).containsOperationIndex(arrayNeighbors.get(k-1).getoX());
						}
						boolean containsY = false;
						for (int i = 0; i < paths.size() && !containsY; i++) {
							containsY = paths.get(i).containsOperationIndex(arrayNeighbors.get(k-1).getoY());
						}
						ExecutionLogger.getInstance().printLog("In critical Path  X: "+ contains);
						ExecutionLogger.getInstance().printLog("In critical Path  Y: "+ containsY);
					}
					
					if(optimal.intValue() >= bestGamma){
						if(isOptimal){
							System.out.println("optimal found!");
							System.out.println();
							optimalAchieved = true;
						}
						else{
							maxNumberImprovements--;
							if(maxNumberImprovements==0){
								optimalAchieved = true;
							}
						}
					}
					
					k = arrayNeighbors.size();
				}
				k--;
				candidate.clean();
				
				long actualTime = System.currentTimeMillis();
			    long elapsedTime = actualTime - startTime;
			    
			    if(elapsedTime>=stopTime){
			    	optimalAchieved = true;
			    	executionResults.setStopCriteria(2);
			    }
			}
		}
		ExecutionResults result = obtainExecutionResults(best, gammaCalculator, (Boolean)params.get("printTable"), (Boolean)params.get("printSolutions"),(Boolean)params.get("printInitialSolution"), (Boolean)params.get("printLog"), 0);
		result.setNumberOfVisitedNeighbors(numberOfVisitedNeighbors);
		return result;
	}	
}
