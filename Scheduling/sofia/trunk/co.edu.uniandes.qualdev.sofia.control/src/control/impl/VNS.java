package control.impl;

import gammaCalculator.IGammaCalculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
	
	int numberOfVisitedNeighbors=0;
	int neighSize =0; // size of the neighborhood to perform the local search
	int bestNeighborsSize;
	boolean optimalAchieved = false;
	
	
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
				maxIt= (Integer) params.get("maxTotalIterations"); //este número lo defino yo, es el máximo número permitido de iteraciones en toda la heurística
		}
		if(params.get("maxItsWOImprovement")!=null){
			if((Integer)params.get("maxItsWOImprovement")!=-1)
				maxItsWOImprovement= (Integer) params.get("maxItsWOImprovement"); //este número lo defino yo, es el máximo número permitido de iteraciones sin mejora
		}	
		if(params.get("neighSize")!=null){
			if((Integer)params.get("neighSize")!=-1)
				neighSize= (Integer) params.get("neighSize"); //este número lo defino yo, tamaño de vecinos a explorar en local search
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
					
				//Local search starting from the shakedSol as initial sol.
					//PONER QUE SE PUEDA ELEGIR COMO PARAMETRO EN CONFIGURACION SI EL LOCAL S. SE HACE CON LS O CON SIM.ANNEALING
				
				IStructure candidate = null;
				if (ls==0){
					candidate = simulatedAnnealing(neighborCalculator, modifier, startTime, stopTime, params, gammaCalculator, shakedSol, optimal, isOptimal);
				}
				else if(ls==1){
					candidate = tabuSearch(shakedSol, neighborCalculator, modifier, gammaCalculator, params, optimal, isOptimal, startTime, stopTime);
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
                 //  System.out.println("moved to next neighborhood "+ neighborhood);
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
	ExecutionResults result = obtainExecutionResults(best, gammaCalculator, (Boolean)params.get("printTable"), (Boolean)params.get("printSolutions"),(Boolean)params.get("printInitialSolution"), (Boolean)params.get("printLog"), 0);
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
			//System.out.println("neighSize se define en" +neighSize+" mientras que nPe es "+ nPr);
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
		
		
		public IStructure simulatedAnnealing(INeighborCalculator neighborCalculator, IModifier modifier, long startTime, long stopTime, Properties params, IGammaCalculator gammaCalculator, IStructure initialSolution, Integer optimal, boolean isOptimal) throws Exception{
			
			Double temperature = (Double) params.get("T0");

			// Provides an initial solution (X) from the problem
			IStructure X = initialSolution;
			double XCMax = gammaCalculator.calculateGamma(X);
			
			// Initializes the best solution (XBest) as the first one (X)
			IStructure XBest = X.cloneStructure();
			double XBestCMax = gammaCalculator.calculateGamma(XBest);
			
			// Obtaining the parameters from the algorithm configuration.
			Integer nonImprovingOut = (Integer) params.get("non-improving-out");
			
			if(nonImprovingOut==-1)
				nonImprovingOut= Integer.MAX_VALUE;

			Double boltzmann = (Double) params.get("boltzmann");
			Double finalTemperature = (Double) params.get("Tf");
			
			int maxNumberImprovements = -1;
			if(params.get("maxNumberImprovements")!=null){
				maxNumberImprovements = (Integer)params.get("maxNumberImprovements");
			}
			
			if(optimal.intValue() >= XBestCMax){
				if(isOptimal){
					System.out.println("optimal found!");
					System.out.println();
					optimalAchieved = true;
				}
			}
			
			int temperatureReductions = 0;
			
			while (temperature >= finalTemperature &&  temperatureReductions < nonImprovingOut && !optimalAchieved) {

				Integer k = (Integer) params.get("k");
				Integer nonImprovingIn = (Integer) params.get("non-improving-in");

				if(nonImprovingIn==-1)
					nonImprovingIn= Integer.MAX_VALUE;
				
				while (k > 0 && !optimalAchieved && nonImprovingIn>=0){
					// Obtains a next solution (Y) from the current one (X)
					PairVO YMovement = neighborCalculator.calculateNeighbor(X);
					IStructure Y = modifier.performModification(YMovement, X);
					if(Y ==null)
						continue;
					
					numberOfVisitedNeighbors++;

					double YCMax = gammaCalculator.calculateGamma(Y);
					double deltaXY = YCMax - XCMax;

					if(deltaXY > 0){
						double acceptaceValue = Math.pow(Math.E, (-deltaXY/(boltzmann*temperature)));
						double acceptanceRandom = Math.random();
						
						if(acceptanceRandom <= acceptaceValue){
							X = Y.cloneStructure();
							XCMax = gammaCalculator.calculateGamma(X);
							nonImprovingIn = (Integer) params.get("non-improving-in");
						
							if(nonImprovingIn<=0)
								nonImprovingIn=Integer.MAX_VALUE;
						}
					}else{
						X = Y.cloneStructure();
						XCMax = gammaCalculator.calculateGamma(X);
						nonImprovingIn = (Integer) params.get("non-improving-in");
					
						if(nonImprovingIn<=0)
							nonImprovingIn=Integer.MAX_VALUE;
					}
					
					if(XCMax < XBestCMax){
						XBest = X.cloneStructure();
						XBestCMax = gammaCalculator.calculateGamma(XBest);
						temperatureReductions = 0;
						nonImprovingIn = (Integer) params.get("non-improving-in");
						
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
								
								if(maxNumberImprovements==0){
									optimalAchieved = true;
									executionResults.setStopCriteria(7);
						
									System.out.println("Stop Criteria: Max number of improvements");
								}
							}
						}
					}
				    
					long actualTime = System.currentTimeMillis();
				    long elapsedTime = actualTime - startTime;

					if(elapsedTime>=stopTime){
				    	optimalAchieved = true;
				    	executionResults.setStopCriteria(2);

				    	System.out.println("Stop Criteria: Max execution time");
				    	ExecutionLogger.getInstance().printLog("Stop Criteria: Max execution time");
				    }
				    nonImprovingIn--;
					k--;
					Y.clean();
				}
				
				
				// Temperature reductions
				Double coolingFactor = (Double) params.get("coolingFactor");
				temperature = temperature * (coolingFactor);
				temperatureReductions ++;
				
			}
			
			if(temperatureReductions>=nonImprovingOut){
				executionResults.setStopCriteria(8);
			
				System.out.println("Stop Criteria: Non improving");
				ExecutionLogger.getInstance().printLog("Stop Criteria: Non improving");
			}
			
			return XBest;
			
		}
		
		
		public IStructure tabuSearch(IStructure initialSolution,
				INeighborCalculator neighborCalculator, IModifier modifier,
					IGammaCalculator gammaCalculator, Properties params, Integer optimal, 
						boolean isOptimal, long startTime, long stopTime) throws Exception {
			
			int numberOfVisitedNeighbors=0;
			double GammaInitialSolution = gammaCalculator.calculateGamma(initialSolution);
			
			int tabuSize = (Integer) params.get("tabulist-size");
			bestNeighborsSize = tabuSize+1;
			IStructure current = initialSolution.cloneStructure();

			// Initializes the best solution (XBest) as the first one (X)
			IStructure best = current.cloneStructure();
			double bestGamma = gammaCalculator.calculateGamma(best);

			executionResults.setOptimal(optimal);

			int maxNumberImprovements = -1;
			if(params.get("maxNumberImprovements")!=null){
				maxNumberImprovements = (Integer)params.get("maxNumberImprovements");
			}
			

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
			Graphic graphic = new Graphic();
			graphic.addPoint(new Point(0, GammaInitialSolution));
			
			while (iterations >= 0 && nonImprovingOut >= 0 && !optimalAchieved) {
				
				ArrayList<PairVO> bestCandidates = new ArrayList<PairVO>();
								
				int nonImprovingIn = (Integer) params.get("non-improving-in");
				if(nonImprovingIn<0)
					nonImprovingIn= Integer.MAX_VALUE;
				
				numberOfVisitedNeighbors+=arrayNeighbors.size();
				getBestCandidates(startTime, stopTime, bestCandidates, arrayNeighbors, nonImprovingIn, current, modifier, numberOfVisitedNeighbors, iterations, gammaCalculator);

				PairVO bestPair = bestCandidates.get(bestCandidates.size()-1);
				if (bestPair != null) {
					if (bestPair.getGamma() < bestGamma) {
						System.out.println("Improvement " +bestPair.getGamma());
						best = modifier.performModification(bestPair,current);					
						bestGamma = gammaCalculator.calculateGamma(best);
						current = best.cloneStructure();
						nonImprovingOut = (Integer) params.get("non-improving-out");
						
						if(nonImprovingOut <= 0)
							nonImprovingOut = Integer.MAX_VALUE;
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
									executionResults.setStopCriteria(7);
									System.out.println("Stop Criteria: Max number improvements");
								}
							}
						}
					}
					else{
						boolean found = false;
						for(int i = bestCandidates.size()-1; i>-1 && !found;i--){
							PairVO pair = bestCandidates.get(i); 
							PairVO pairI =  new PairVO(pair.getoY(), pair.getoX());
							if(!arrayTabu.contains(pair) && !arrayTabu.contains(pairI)){
								current = modifier.performModification(pair,current);
								found = true;
								if (tabuIndex > tabuSize) {
									arrayTabu.remove(0);
									tabuIndex--;
								}
								arrayTabu.add(pair);
								tabuIndex++;
							}
						}
					}
										
				}
				
				// Avance while
				iterations--;
				nonImprovingOut--;
				arrayNeighbors = neighborCalculator.calculateNeighborhood(current, neighborhodSize);
				timeAccomplished(startTime,stopTime);
				
			}

			if(executionResults.getStopCriteria() == 2){
				System.out.println("Stop Criteria: Max execution time");
		    	ExecutionLogger.getInstance().printLog("Stop Criteria: Max execution time");
			}
			
			if(nonImprovingOut<=0){
				executionResults.setStopCriteria(1);
				System.out.println("Stop Criteria: Non Improving");
				ExecutionLogger.getInstance().printLog("Stop Criteria: Non Improving");
			}

			return best;
		}
		
		
		
		private void addToBestCandidates(ArrayList<PairVO> bestCandidates,
				PairVO pairCandidate, int maxSize) {
			
			if(bestCandidates.size()<maxSize){
				bestCandidates.add(pairCandidate);
				Collections.sort(bestCandidates, new Comparator<PairVO>() {
				    public int compare(PairVO a, PairVO b) {
				        return (int)(b.getGamma()-a.getGamma());
				    }
				});
			}
			else{
				if(pairCandidate.getGamma()<bestCandidates.get(0).getGamma()){
					bestCandidates.remove(0);
					bestCandidates.add(pairCandidate);
					Collections.sort(bestCandidates, new Comparator<PairVO>() {
					    public int compare(PairVO a, PairVO b) {
					        return (int)(b.getGamma()-a.getGamma());
					    }
					});
				}
			}
			
		}
		
		private void getBestCandidates(long startTime, long stopTime,ArrayList<PairVO> bestCandidates, ArrayList<PairVO> arrayNeighbors, int  nonImprovingIn, IStructure current, IModifier modifier, int numberOfVisitedNeighbors, int iterations, IGammaCalculator gammaCalculator) throws Exception{
			for (int index = 0; index < arrayNeighbors.size() && !optimalAchieved && nonImprovingIn>=0; index++) {
				PairVO pairCandidate = arrayNeighbors.get(index);
				IStructure candidate = modifier.performModification(pairCandidate,current);
				if(candidate ==null){
					timeAccomplished(startTime,stopTime);
					continue;
				}
				numberOfVisitedNeighbors++;
				double gammaCandidate = gammaCalculator.calculateGamma(candidate);
				pairCandidate.setGamma(gammaCandidate);
				
				addToBestCandidates(bestCandidates,pairCandidate, bestNeighborsSize);
				candidate.clean();
				nonImprovingIn--;
				
				timeAccomplished(startTime,stopTime);
			}
		}
		
		private void timeAccomplished(long startTime, long stopTime){
			long actualTime = System.currentTimeMillis();
		    long elapsedTime = actualTime - startTime;
		    
		    if(elapsedTime>=stopTime){
		    	System.out.println("TIME!!!");
		    	optimalAchieved = true;
		    	executionResults.setStopCriteria(2);
		    }
		}
		
		public static long permutacion(int N, int r){
		    long multi = 1;
		    for (int i = N-r+1; i <= N; i++) {
		        multi = multi * i;
		    }
		    return multi;
		}

	}


