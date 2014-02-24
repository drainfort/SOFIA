package control.impl;

import gammaCalculator.IGammaCalculator;

import java.util.ArrayList;
import java.util.Properties;

import modifier.IModifier;
import neighborCalculator.INeighborCalculator;
import structure.IStructure;

import common.types.PairVO;
import common.utils.ExecutionResults;

import control.Control;

public class VNS extends Control{
	
	
	public VNS(){
		super();
	}

	@Override
	public ExecutionResults execute(IStructure initialSolution,
			INeighborCalculator neighborCalculator,
			ArrayList<IModifier> modifiers, IGammaCalculator gammaCalculator,
			Properties params, Integer optimal, boolean isOptimal)
			throws Exception {

		
		So = initialSolution;
		IStructure best = initialSolution.cloneStructure();
		double GammaInitialSolution = gammaCalculator.calculateGamma(best);
		System.out.println("Initial Solution: "+GammaInitialSolution);
		double gammaBestCandidate = gammaCalculator.calculateGamma(best);
		
		executionResults = new ExecutionResults();
		executionResults.setInitialCmax(GammaInitialSolution);
		executionResults.setOptimal(optimal);
		
		
		//El ciclo de afuera se encarga de parar la heurística si ya se pasa el número de iteraciones máximas
		//es el que cambia de vecindario (de modifier)
		
		int maxIt = 1000; //este número lo defino yo, es el máximo número permitido de iteraciones en toda la heurística
		int numIt=0; //contador de iteraciones
		int numberOfVisitedNeighbors=0;
		int maxItWithoutImprovement=100; //este número lo elijo yo, es el máximo de iteraciones permitidas sin mejora
		int i =0; //contador para número de iteraciones sin mejora
		int lastBestFoundOn; 
		
		boolean optimalAchieved = false;

		if (optimal.intValue() >= gammaBestCandidate) {
			if(isOptimal){
				System.out.println("optimal found!");
				System.out.println();
				optimalAchieved = true;
			}
		}
		
		while (i<maxItWithoutImprovement && numIt<maxIt && !optimalAchieved)
		{
			double gammaLast= gammaCalculator.calculateGamma(best);
			int neighborhood = 0;
			
			//el ciclo de adentro es el que hace todas las modificaciones para encontrar diferentes vecinos 
			//y evaluar si mejora la FO encontrando mínimos locales y cambiando de vecindarios
			
			while (neighborhood < modifiers.size()&& !optimalAchieved)
			{
				if(numIt > maxIt){
                    break;
				}

				//PairVO pairCandidate = neighborCalculator.calculateNeighbor(current); //escoge al azar pareja 
				//IModifier modifier = modifiers.get(neighborhood);
				//IStructure candidate = modifier.performModification(pairCandidate,current);
				
				IStructure candidate = localSearch(neighborhood, best, neighborCalculator, modifiers, gammaCalculator);

				//if(candidate ==null)
					//continue;
				double gammaCandidate = gammaCalculator.calculateGamma(candidate);
				
				//acá toca hacer un local search para encontrar el mínimo local usando el modifier actual
				
				if (gammaCandidate <gammaBestCandidate){
					best = candidate; //no se si poner current o candidate
					neighborhood = 0; //no se si reiniciar o poner en el modifier actual
					System.out.println("Improvement: "+gammaCandidate);
					gammaBestCandidate = gammaCandidate;
					lastBestFoundOn = numIt;
					if (optimal.intValue() >= gammaBestCandidate) {
						if(isOptimal){
							System.out.println("optimal found!");
							System.out.println();
							optimalAchieved = true;
						}
					}
				}
				else {
                    neighborhood++;
				}
				numIt++;
			}
            
            if(gammaLast > gammaBestCandidate) {
                    i = 0; //se reinicia la cuenta de no mejoras a cero
            } 
            else {
                    i++;
            }
		}

		//aca termina la heuristica
		//el tiempo de ejecucion
		System.out.println();
		ExecutionResults result = obtainExecutionResults(best, gammaCalculator, (Boolean)params.get("printTable"), (Boolean)params.get("printSolutions"),(Boolean)params.get("printInitialSolution"), (Boolean)params.get("printLog"), 0);
		result.setNumberOfVisitedNeighbors(numberOfVisitedNeighbors);
		return result;
	
	}
	
	//método de búsqueda local para un modifier dado
	
		public IStructure localSearch (int neighborhood, IStructure current, INeighborCalculator neighborCalculator, ArrayList<IModifier> modifiers, IGammaCalculator gammaCalculator) throws Exception
		  {
			int maxIterLocal=50; //este número lo elijo yo
			double gammaMejor = Integer.MAX_VALUE;
			IStructure mejor = null;
			int n = current.getOperations().size();
			int r = 2;
			int nPr = (int)permutacion(n, r);
			ArrayList<PairVO> candidates = neighborCalculator.calculateNeighborhood(current, nPr); //escoge al azar pareja 
			IModifier modifier = modifiers.get(neighborhood);
			for (int i = 0; i < candidates.size(); i++) {
				PairVO actual = candidates.get(i);
				IStructure modify = modifier.performModification(actual, current);
				double gamma = gammaCalculator.calculateGamma(modify);
				if(gamma<gammaMejor){
					mejor = modify.cloneStructure();
					gammaMejor = gamma;
				}
			}
			return mejor;
		  }
		
		public static long permutacion(int N, int r){
		    long multi = 1;
		    for (int i = N-r+1; i <= N; i++) {
		        multi = multi * i;
		    }
		    return multi;
		}

	}


