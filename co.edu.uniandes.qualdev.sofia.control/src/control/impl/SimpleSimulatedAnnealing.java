package control.impl;

import java.util.Properties;

import structure.IStructure;

import common.types.PairVO;
import common.utils.ExecutionResults;
import control.Control;

import modifier.IModifier;
import neighborCalculator.INeighborCalculator;
import gammaCalculator.IGammaCalculator;

public class SimpleSimulatedAnnealing extends Control {

	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------

	public SimpleSimulatedAnnealing() {
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

		long startTime = System.currentTimeMillis();
		long stopTime = Integer.MAX_VALUE*1000;
		if(params.get("maxExecutionTime")!=null){
			stopTime = (Integer) params.get("maxExecutionTime") *1000;	
		}
		executionResults = new ExecutionResults();
		int numberOfVisitedNeighbors=0;
		int GammaInitialSolution = gammaCalculator.calculateGamma(initialSolution);
		executionResults.setInitialCmax(GammaInitialSolution);
		this.So = initialSolution.cloneStructure();
		
		Double temperature = (Double) params.get("T0");

		
		// Provides an initial solution (X) from the problem
		IStructure X = initialSolution;
		int XCMax = gammaCalculator.calculateGamma(X);
		
		// Initializes the best solution (XBest) as the first one (X)
		IStructure XBest = X.cloneStructure();
		int XBestCMax = gammaCalculator.calculateGamma(XBest);
		System.out.println("initial solution (XBestCMax): " + XBestCMax);
		
		// Obtaining the parameters from the algorithm configuration.
		Integer nonImproving = (Integer) params.get("non-improving");
		Double boltzmann = (Double) params.get("boltzmann");
		Double finalTemperature = (Double) params.get("Tf");
		executionResults.setOptimal(optimal);
		
		int maxNumberImprovements =0;
		if(params.get("maxNumberImprovements")!=null){
			maxNumberImprovements = (Integer)params.get("maxNumberImprovements");
		}
		
		boolean optimalAchieved = false;
		
		if(optimal.intValue() >= XBestCMax){
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
		
		
		int temperatureReductions = 0;
		
		while (temperature >= finalTemperature &&  temperatureReductions < nonImproving && !optimalAchieved) {
			Integer k = (Integer) params.get("k");
			
			while (k > 0 && !optimalAchieved){
				
				// Obtains a next solution (Y) from the current one (X)
				PairVO YMovement = neighborCalculator.calculateNeighbor(X);
				IStructure Y = modifier.performModification(YMovement, X);
				numberOfVisitedNeighbors++;
				int YCMax = gammaCalculator.calculateGamma(Y);
				
				int deltaXY = YCMax - XCMax;

				if(deltaXY > 0){
					double acceptaceValue = Math.pow(Math.E, (-deltaXY/(boltzmann*temperature)));
					double acceptanceRandom = Math.random();
					
					if(acceptanceRandom <= acceptaceValue){
						X = Y.cloneStructure();
						XCMax = gammaCalculator.calculateGamma(X);
					}
				}else{
					X = Y.cloneStructure();
					XCMax = gammaCalculator.calculateGamma(X);
				}
				
				if(XCMax < XBestCMax){
					XBest = X.cloneStructure();
					XBestCMax = gammaCalculator.calculateGamma(XBest);
					temperatureReductions = 0;
					System.out.println("CMax improvement: " + XBestCMax);
					
					if(optimal.intValue() >= XBestCMax){
						if(isOptimal){
							System.out.println("optimal found!");
							System.out.println();
							optimalAchieved = true;
						}
						else{
							if (optimal.intValue() >XBestCMax)
								maxNumberImprovements--;
							if(maxNumberImprovements<=0){
								optimalAchieved = true;
							}
						}
					}

				}
				
				long actualTime = System.currentTimeMillis();
			    long elapsedTime = actualTime - startTime;
			    if(elapsedTime>=stopTime)
			    	optimalAchieved = true;
				
				k--;
				Y.clean();
			}
			
			// Temperature reductions
			Double coolingFactor = (Double) params.get("coolingFactor");
			temperature = temperature * (coolingFactor);
			temperatureReductions ++;
		}
		
		System.out.println();
		ExecutionResults result = obtainExecutionResults(XBest, gammaCalculator, (Boolean)params.get("printTable"), (Boolean)params.get("printSolutions"),(Boolean)params.get("printInitialSolution"));
		result.setNumberOfVisitedNeighbors(numberOfVisitedNeighbors);
		return result;
	}
}
