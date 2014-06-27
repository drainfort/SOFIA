package control.impl;

import gammaCalculator.IGammaCalculator;

import java.util.ArrayList;
import java.util.Properties;

import modifier.IModifier;
import neighborCalculator.INeighborCalculator;
import structure.IStructure;
import structure.impl.CriticalPath;

import common.types.PairVO;
import common.utils.ExecutionLogger;
import common.utils.ExecutionResults;
import common.utils.Graphic;
import common.utils.Point;

import control.Control;

public class VNS extends Control{
	
	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------
	
	private int numberOfVisitedNeighbors=0;
	private int neighSize =0; // size of the neighborhood to perform the local search
	private int bestNeighborsSize;
	private boolean optimalAchieved = false;
	
	private SimpleSimulatedAnnealing simulated = new SimpleSimulatedAnnealing();
	private TabuSearch tabu = new TabuSearch();
	
	
	public VNS(){
		super();
	}
	
	// -----------------------------------------------
	// Methods
	// -----------------------------------------------

	@Override
	public ExecutionResults execute(IStructure initialSolution,
			INeighborCalculator neighborCalculator,
			ArrayList<IModifier> modifiers, IGammaCalculator gammaCalculator,
			Properties params, Integer optimal, boolean isOptimal)
			throws Exception {
		
		System.out.println("Entro");
		numberOfVisitedNeighbors=0;
		So = initialSolution.cloneStructure();
		IStructure best = So;
		double GammaInitialSolution = gammaCalculator.calculateGamma(So);
		System.out.println("Initial Solution: "+GammaInitialSolution);
		double gammaBestCandidate = GammaInitialSolution;
		
		executionResults = new ExecutionResults();
		executionResults.setInitialCmax(GammaInitialSolution);
		executionResults.setOptimal(optimal);
		
		int maxIt=0; //captures the max number of iterations allowed
		int maxItsWOImprovement =0; //captures the max number of iterations w/o improvement allowed
		int i =0; // counter for the number of iterations w/o improvement
		long startTime = System.currentTimeMillis();
		long stopTime = Integer.MAX_VALUE;
		long elapsedTime =0;
		int opti =0; //set to 1 if optimality is reached
		int numIt=0; //iteration counter
		optimalAchieved=false;
		
		if(params.get("maxExecutionTime")!=null ){
			System.out.println("tiempo por instancia "+params.get("maxExecutionTime")+" secs");
			if((Integer) params.get("maxExecutionTime")!=-1)
				stopTime = (Integer) params.get("maxExecutionTime") * 1000;	
		}
		if(params.get("maxTotalIterations")!=null){
			if((Integer)params.get("maxTotalIterations")!=-1)
				maxIt= (Integer) params.get("maxTotalIterations"); 
		}
		if(params.get("maxItsWOImprovement")!=null){
			if((Integer)params.get("maxItsWOImprovement")!=-1)
				maxItsWOImprovement= (Integer) params.get("maxItsWOImprovement"); 
		}	
		if(params.get("neighSize")!=null){
			if((Integer)params.get("neighSize")!=-1)
				neighSize= (Integer) params.get("neighSize"); 
		}	
		if (optimal.intValue() >= GammaInitialSolution) {
			if(isOptimal){
				System.out.println("optimal found! sol inicial es el optimo");
				System.out.println();
				optimalAchieved = true;
				opti=1;
			}
		}
		int ls = -1;
		if(params.get("ls")!=null){
			ls = ((Integer)params.get("ls"));
		}
		System.out.println("ls: "+ls);
		double gammaLast= GammaInitialSolution;
		
		//VNS heuristic 
		
		while (i<maxItsWOImprovement && numIt<maxIt && optimalAchieved==false)
		{
			
			int neighborhood = 0;
			
			// Inner cycle is in charge of making modifications to current solutions and find the best neighbor and evaluate if the objective function improves. 
			// It jumps to another neighborhood after every local search. 
			
			while (neighborhood < modifiers.size() && optimalAchieved==false)
			{
				if(numIt > maxIt){
                    break;
				}

				//Shake: generating a random point from the best sol.
				PairVO shakePair = neighborCalculator.calculateNeighbor(best); //escoge al azar pareja 
				IModifier modifier = modifiers.get(neighborhood); 
				IStructure shakedSol = modifier.performModification(shakePair, best);
					//Second stage for the shaking process
					shakePair = neighborCalculator.calculateNeighbor(shakedSol); //escoge al azar pareja 
					shakedSol = modifier.performModification(shakePair, shakedSol);
					
				//The local search starting from the shakedSol as initial solution can be performed with simulated annealing, tabu search or a simple local search
				
				IStructure candidate = null;
				if (ls==0){
					candidate = simulated.simulatedAnnealing(params, shakedSol, gammaCalculator, neighborCalculator, modifier, optimal, isOptimal, executionResults, startTime, stopTime);
				}
				else if(ls==1){
					candidate = tabu.tabuSearch(shakedSol, neighborCalculator, modifier, gammaCalculator, params, optimal, isOptimal, GammaInitialSolution, executionResults, startTime, stopTime);
				}
				else	
					candidate = localSearch(neighborhood, shakedSol, neighborCalculator, modifiers, gammaCalculator, startTime, stopTime);
				
				double gammaCandidate = gammaCalculator.calculateGamma(candidate);
				
				//Evaluate if update the best solution or not. 
				if (gammaCandidate < gammaBestCandidate && gammaCandidate < GammaInitialSolution ){
					best = candidate; 
					System.out.println("Improvement: "+ gammaBestCandidate + "a" + gammaCandidate + "en neigh "+neighborhood);
					neighborhood = 0;
					gammaBestCandidate = gammaCandidate;
					if (optimal.intValue() >= gammaBestCandidate) {
						if(isOptimal){
							System.out.println("optimal found! gamma LS es el optimo");
							System.out.println();
							optimalAchieved = true;
							opti=1;
						}
					}
				}
				else {
                    neighborhood++;	
                }
				numIt++;
				long actualTime = System.currentTimeMillis();
			    elapsedTime = actualTime - startTime;
			    
			    if(elapsedTime>=stopTime){
			    	optimalAchieved = true;
			    	executionResults.setStopCriteria(2);
			    }
			}
            
            if(gammaLast > gammaBestCandidate) {
                    i = 0; //Restart counter to zero
                    gammaLast= gammaBestCandidate;
                    System.out.println("gammaLast es:" +gammaLast);
					System.out.println();
            } 
            else {
                    i++;
            }
            
            long actualTime = System.currentTimeMillis();
		    elapsedTime = actualTime - startTime;
		    
		    if(elapsedTime>=stopTime){ 
		    	optimalAchieved = true;
		    	executionResults.setStopCriteria(2);
		    }
		}
		
	if(i>=maxItsWOImprovement)
	{
	   System.out.println("salio del while por maxItWOImp");
	   executionResults.setStopCriteria(4);	
	}
	if(numIt>=maxIt)
	{
	   System.out.println("salio del while por maxItTotales");
	   executionResults.setStopCriteria(3);
	}
	if(opti==1)
	{
	   System.out.println("salio del while por optimo");
	   executionResults.setStopCriteria(1);	
	}
	
	//End of the heuristic
		
	System.out.println("elapsed time (sec): "+elapsedTime/1000);
	System.out.println("iterations w/o imp. " +i);
	System.out.println("iterations " +numIt);
	System.out.println();
	ExecutionResults result = obtainExecutionResults(best, gammaCalculator, (Boolean)params.get("printTable"), (Boolean)params.get("printSolutions"),(Boolean)params.get("printInitialSolution"), (Boolean)params.get("printLog"), (Boolean)params.get("printImprovement"),0);
	result.setNumberOfVisitedNeighbors(numberOfVisitedNeighbors);
	return result;
	
	}
	
	// Local search within a neighborhood	
		public IStructure localSearch (int neighborhood, IStructure current, INeighborCalculator neighborCalculator, ArrayList<IModifier> modifiers, IGammaCalculator gammaCalculator, long startTime, long stopTime) throws Exception
		  {
			double gammaMejor = Integer.MAX_VALUE;
			IStructure mejor = null;
			int n = current.getOperations().size();
			int r = 2;
			int nPr = (int)permutacion(n, r);
			if(neighSize>nPr)
				neighSize = nPr;	
			ArrayList<PairVO> candidates = neighborCalculator.calculateNeighborhood(current, neighSize); //escoge al azar pareja 
			IModifier modifier = modifiers.get(neighborhood);
			for (int i = 0; i < candidates.size(); i++) {
				numberOfVisitedNeighbors++;
				PairVO actual = candidates.get(i);
				IStructure modify = modifier.performModification(actual, current);
				double gamma = gammaCalculator.calculateGamma(modify);
				if(gamma<gammaMejor){
					mejor = modify.cloneStructure();
					gammaMejor = gamma;
				}
				long actualTime = System.currentTimeMillis();
			    long elapsedTime = actualTime - startTime;
			    
			    if(elapsedTime>=stopTime){
			    	executionResults.setStopCriteria(2);
			    	break;
			    }
			}
			return mejor;
		  }
		
	//Calculates the size of a permutation 
		public static long permutacion(int N, int r){
		    long multi = 1;
		    for (int i = N-r+1; i <= N; i++) {
		        multi = multi * i;
		    }
		    return multi;
		}

	}


