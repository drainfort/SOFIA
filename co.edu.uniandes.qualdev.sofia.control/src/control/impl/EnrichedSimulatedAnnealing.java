package control.impl;

import java.util.Properties;

import structure.IStructure;

import common.types.PairVO;
import common.utils.ExecutionResults;
import control.Control;

import modifier.IModifier;
import neighborCalculator.INeighborCalculator;
import gammaCalculator.IGammaCalculator;

public class EnrichedSimulatedAnnealing extends Control {

	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------

	public EnrichedSimulatedAnnealing() {
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
		
		Double temperature = (Double) params.get("T0");

		// Creates an initial solution (Sk) from the problem
		IStructure Sk = initialSolution;
		int GammaSk = gammaCalculator.calculateGamma(Sk);

		// Initializes the best solution (S0) as the first one (Sk)
		IStructure S0 = Sk.cloneStructure();
		int GammaS0 = gammaCalculator.calculateGamma(S0);
		System.out.println("initial solution: " + GammaS0);
		
		Integer nonImproving = (Integer) params.get("non-improving");
		Double boltzmann = (Double) params.get("boltzmann");
		Integer restarts = (Integer) params.get("restarts");
		Double finalTemperature = (Double) params.get("Tf");
		executionResults.setOptimal(optimal);
		
		boolean optimalAchieved = false;
		int maxNumberImprovements =0;
		if(params.get("maxNumberImprovements")!=null){
			maxNumberImprovements = (Integer)params.get("maxNumberImprovements");
		}
		
		if(optimal.intValue() >= GammaSk){
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
		
		int temperatureReductions = 0;
		int tries = 0;
		
		while(restarts >= 0 && !optimalAchieved){
			System.out.println("SimulatedAnnealing: Try: " + tries );
			// Loop 1: The one that iterates over the temperature
			while (temperature >= finalTemperature &&  temperatureReductions < nonImproving && !optimalAchieved) {

				Integer k = (Integer) params.get("k");
				
				while (k > 0 && !optimalAchieved) {

					// Calculates a candidate solution by creating a neighbor of the current Sk
					PairVO ScMovement = neighborCalculator.calculateNeighbor(Sk);
					IStructure Sc = modifier.performModification(ScMovement, Sk);
					numberOfVisitedNeighbors++;
					int GammaSc = gammaCalculator.calculateGamma(Sc);

					if ((GammaS0 < GammaSc)
							&& (GammaSc < GammaSk)) {
						Sk.clean();
						Sk = null;
						Sk = Sc.cloneStructure();
						GammaSk = GammaSc;
					}

					if ((GammaSc < GammaS0)
							&& (GammaS0 <= GammaSk)) {
						System.out.println("currrent cmax: " + GammaSc);
						
						if(optimal.intValue() >= GammaSk){
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
						S0.clean();
						S0 = null;
						S0 = Sc.cloneStructure();
						GammaS0 = GammaSc;
						Sk.clean();
						Sk = null;
						Sk = Sc.cloneStructure();
						GammaSk = GammaSc;
						
						temperatureReductions = 0;
					}

					if (GammaSc > GammaSk) {
						double u = Math.random();
						double decisionValue = Math.pow(Math.E,
								(-(GammaSc - GammaSk) / (boltzmann * temperature)));
						if (u <= decisionValue) {
							
							Sk.clean();
							Sk = null;
							Sk = Sc.cloneStructure();
							GammaSk = GammaSc;
						}
					}

					k--;
				}

				// Algorithm advance
				Double coolingFactor = (Double) params.get("coolingFactor");
				temperature = temperature * (coolingFactor);
				temperatureReductions ++;

				if (temperature >= finalTemperature) {
					PairVO ScMovement = neighborCalculator.calculateNeighbor(Sk);
					IStructure neighbor = modifier.performModification(ScMovement, Sk);
					numberOfVisitedNeighbors++;
					Sk.clean();
					Sk = null;
					Sk = neighbor;
				}
			}
			
			temperature = (Double) params.get("T0");
			nonImproving = (Integer) params.get("non-improving");
			restarts--;
			tries++;
		}
		System.out.println();
		ExecutionResults result = obtainExecutionResults(S0, gammaCalculator, (Boolean)params.get("printTable"), (Boolean)params.get("printSolutions"),(Boolean)params.get("printInitialSolution"));
		result.setNumberOfVisitedNeighbors(numberOfVisitedNeighbors);
		return result;
	}
}
