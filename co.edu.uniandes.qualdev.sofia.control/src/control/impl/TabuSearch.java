package control.impl;

import gammaCalculator.IGammaCalculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Properties;

import chart.printer.ChartPrinter;

import modifier.IModifier;
import neighborCalculator.INeighborCalculator;
import structure.IStructure;
import structure.impl.CriticalPath;
import structure.impl.Graph;

import common.types.PairVO;
import common.utils.ExecutionLogger;
import common.utils.ExecutionResults;
import common.utils.Graphic;
import common.utils.Point;
import control.Control;

public class TabuSearch extends Control{
	
		private long startTime;
		private long stopTime;
		int bestNeighborsSize;
		IGammaCalculator gammaCalculator;
		private boolean optimalAchieved;
		
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
				INeighborCalculator neighborCalculator, ArrayList<IModifier> modifiers,
					IGammaCalculator gammaCalculator, Properties params, Integer optimal, 
						boolean isOptimal) throws Exception {
					
			this.gammaCalculator = gammaCalculator;
			IModifier modifier = modifiers.get(0);
			ExecutionLogger.getInstance().initializeLogger(resultFile, instanceName);
			
			/*if(initialSolution instanceof Graph){
				((Graph)initialSolution).drawGraph3("./results/graph1/grafo.html", true, null);
			}*/
			
			startTime = System.currentTimeMillis();
			stopTime = Integer.MAX_VALUE;
			
			if(params.get("maxExecutionTime")!=null ){
				if((Integer) params.get("maxExecutionTime")!=-1)
					stopTime = (Integer) params.get("maxExecutionTime") * 1000;	
			}
			
			int numberOfVisitedNeighbors=0;
			double GammaInitialSolution = gammaCalculator.calculateGamma(initialSolution);
			
			executionResults = new ExecutionResults();
			executionResults.setInitialCmax(GammaInitialSolution);
			
			this.So = initialSolution.cloneStructure();
			int tabuSize = (Integer) params.get("tabulist-size");
			bestNeighborsSize = tabuSize+1;
			IStructure current = initialSolution.cloneStructure();

			// Initializes the best solution (XBest) as the first one (X)
			IStructure best = current.cloneStructure();
			double bestGamma = gammaCalculator.calculateGamma(best);
			System.out.println("initial solution: " + bestGamma);
			ExecutionLogger.getInstance().printLog("initial solution: " + bestGamma);

			executionResults.setOptimal(optimal);

			int maxNumberImprovements = -1;
			if(params.get("maxNumberImprovements")!=null){
				maxNumberImprovements = (Integer)params.get("maxNumberImprovements");
			}
			
			optimalAchieved = false;

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
			//ArrayList<PairVO> arrayNeighbors = neighborCalculator.calculateCompleteNeighborhood(current);

			Graphic graphic = new Graphic();
			graphic.addPoint(new Point(0, GammaInitialSolution));
			
			while (iterations >= 0 && nonImprovingOut >= 0 && !optimalAchieved) {
				/*if(current instanceof Graph){
					((Graph)current).drawGraph3("./results/graph4/grafo"+iterations+".html", true, null);
					ExecutionResults result = obtainExecutionResults(current, gammaCalculator,false,true,false,false, 0);
					result.setNumberOfVisitedNeighbors(0);
					ArrayList<ExecutionResults> results = new ArrayList<ExecutionResults>();
					results.add(result);
					ChartPrinter.getInstance().addResults(results);
					ChartPrinter.getInstance().printGlobalResultsHTML("./results/graph4/grafo-gantt-"+iterations+".html", "./results/graph1/grafoL"+iterations+".html");
					ChartPrinter.getInstance().restart();
				}*/
				
				ArrayList<PairVO> bestCandidates = new ArrayList<PairVO>();
								
				int nonImprovingIn = (Integer) params.get("non-improving-in");
				if(nonImprovingIn<0)
					nonImprovingIn= Integer.MAX_VALUE;
				
				numberOfVisitedNeighbors+=arrayNeighbors.size();
				getBestCandidates(bestCandidates, arrayNeighbors, optimalAchieved, nonImprovingIn, current, modifier, numberOfVisitedNeighbors, iterations);

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
									executionResults.setStopCriteria(3);
									System.out.println("Stop Criteria: Max number improvements");
									ExecutionLogger.getInstance().printLog("Stop Criteria: Max number improvements");
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
				//arrayNeighbors = neighborCalculator.calculateCompleteNeighborhood(current);
				arrayNeighbors = neighborCalculator.calculateNeighborhood(current, neighborhodSize);
				timeAccomplished();
				
			}
			if(graphic.getPoints().size()>1)
				executionResults.addGraphic(graphic);

			if(executionResults.getStopCriteria() == 2){
				System.out.println("Stop Criteria: Max execution time");
		    	ExecutionLogger.getInstance().printLog("Stop Criteria: Max execution time");
			}
			
			if(nonImprovingOut<=0){
				executionResults.setStopCriteria(1);
				System.out.println("Stop Criteria: Non Improving");
				ExecutionLogger.getInstance().printLog("Stop Criteria: Non Improving");
			}

			System.out.println();
			long actualTime = System.currentTimeMillis();
		    long elapsedTime = actualTime - startTime;
			ExecutionResults result = obtainExecutionResults(best, gammaCalculator, (Boolean)params.get("printTable"), (Boolean)params.get("printSolutions"),(Boolean)params.get("printInitialSolution"), (Boolean)params.get("printLog"), elapsedTime);
			result.setNumberOfVisitedNeighbors(numberOfVisitedNeighbors);
			return result;
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
		
		private void getBestCandidates(ArrayList<PairVO> bestCandidates, ArrayList<PairVO> arrayNeighbors, boolean optimalAchieved, int  nonImprovingIn, IStructure current, IModifier modifier, int numberOfVisitedNeighbors, int iterations) throws Exception{
			for (int index = 0; index < arrayNeighbors.size() && !optimalAchieved && nonImprovingIn>=0; index++) {
				PairVO pairCandidate = arrayNeighbors.get(index);
				if(current instanceof Graph){
				//	((Graph)current).drawGraph3("./results/graph/grafo"+iterations+index+".html", true, pairCandidate);
				}
				IStructure candidate = modifier.performModification(pairCandidate,current);
				if(candidate ==null){
					timeAccomplished();
					continue;
				}
				if(current instanceof Graph){
				//	((Graph)candidate).drawGraph3("./results/graph/grafo"+iterations+index+"a.html", true, pairCandidate);
				}
				numberOfVisitedNeighbors++;
				double gammaCandidate = gammaCalculator.calculateGamma(candidate);
				pairCandidate.setGamma(gammaCandidate);
				
				addToBestCandidates(bestCandidates,pairCandidate, bestNeighborsSize);
				candidate.clean();
				nonImprovingIn--;
				
				timeAccomplished();
			}
		}
		
		private void timeAccomplished(){
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
