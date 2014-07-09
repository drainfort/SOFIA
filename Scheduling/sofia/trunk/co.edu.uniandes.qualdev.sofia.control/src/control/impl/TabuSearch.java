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
	
		// -----------------------------------------------
		// Attributes
		// -----------------------------------------------
	
		/**
		 * Attribute that save the start time of the control execution
		 */
		private long startTime;
		
		/**
		 * Attribute that save the max time of the control execution
		 */
		private long stopTime;
		
		/**
		 * The Number of the size of the neighbors
		 */
		int bestNeighborsSize;
		
		/**
		 * The Number of visited neighbors during the execution of the control
		 */
		int numberOfVisitedNeighbors;
		
		/**
		 * Attribute that is used to stop the execution of the control
		 */
		private boolean optimalAchieved;
		
		// -----------------------------------------------
		// Constructor
		// -----------------------------------------------
		/**
		 * Constructor of the class
		 */
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
					
			IModifier modifier = modifiers.get(0);
			ExecutionLogger.getInstance().initializeLogger(resultFile, instanceName);
			
			/*if(initialSolution instanceof Graph){
				((Graph)initialSolution).drawGraph3("./results/graph1/grafo.html", true, null);
			}*/	
			
			double GammaInitialSolution = gammaCalculator.calculateGamma(initialSolution);
			executionResults = new ExecutionResults();
			executionResults.setInitialCmax(GammaInitialSolution);
			executionResults.setOptimal(optimal);
			
			startTime = System.currentTimeMillis();
			stopTime = Integer.MAX_VALUE;
			
			if(params.get("maxExecutionTime")!=null ){
				if((Integer) params.get("maxExecutionTime")!=-1)
					stopTime = (Integer) params.get("maxExecutionTime") * 1000;	
			}
		
			this.So = initialSolution.cloneStructure();
			
			IStructure best = tabuSearch(initialSolution, neighborCalculator, modifier, gammaCalculator, params, optimal, isOptimal, GammaInitialSolution, executionResults, startTime, stopTime);
			
			System.out.println();
			long actualTime = System.currentTimeMillis();
		    long elapsedTime = actualTime - startTime;
			ExecutionResults result = obtainExecutionResults(best, gammaCalculator, (Boolean)params.get("printTable"), (Boolean)params.get("printSolutions"),(Boolean)params.get("printInitialSolution"), (Boolean)params.get("printLog"), (Boolean)params.get("printImprovement"),elapsedTime);
			result.setNumberOfVisitedNeighbors(numberOfVisitedNeighbors);
			return result;
		}
		
		/**
		 * Executes the tabu search metaheuristic
		 * @param params - Parameters of the algorithm
		 * @param initialSolution - Initial solution
		 * @param gammaCalculator - Component that calculates the objective function
		 * @param neighborCalculator - Component that calculates the neighbors
		 * @param modifier - Component that modifies the structure 
		 * @param optimal - The best solution or the optimal solution
		 * @param isOptimal - If the instance has an optimal solution
		 * @param executionResults - The consolidated results
		 * @param startTime - Initial time
		 * @param stopTime - Max Time
		 * @param GammaInitialSolution - gamma of the initial solution
		 * @return IStructure - The best solution. 
		 * @throws Exception
		 */
		public IStructure tabuSearch(IStructure initialSolution,
				INeighborCalculator neighborCalculator, IModifier modifier,
				IGammaCalculator gammaCalculator, Properties params, Integer optimal, 
					boolean isOptimal, double GammaInitialSolution, ExecutionResults executionResults, long startTime, long stopTime) throws Exception{
			
			optimalAchieved = false;
			this.startTime = startTime;
			this.stopTime = stopTime;
			
			
			int tabuSize = (Integer) params.get("tabulist-size");
			bestNeighborsSize = tabuSize+1;
			IStructure current = initialSolution.cloneStructure();

			// Initializes the best solution (XBest) as the first one (X)
			IStructure best = current.cloneStructure();
			double bestGamma = gammaCalculator.calculateGamma(best);
			System.out.println("initial solution: " + bestGamma);
			ExecutionLogger.getInstance().printLog("initial solution: " + bestGamma);
			
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
				getBestCandidates(bestCandidates, arrayNeighbors, nonImprovingIn, current, modifier, gammaCalculator,executionResults);

				PairVO bestPair = bestCandidates.get(bestCandidates.size()-1);
				if (bestPair != null) {
					if (bestPair.getGamma() < bestGamma) {
						System.out.println("Improvement " +bestPair.getGamma());
						best = modifier.performModification(bestPair,current);					
						bestGamma = gammaCalculator.calculateGamma(best);
						current = best.cloneStructure();
						nonImprovingOut = (Integer) params.get("non-improving-out");
						
						long actualTime = System.currentTimeMillis();
					    long elapsedTime = actualTime - startTime;
					    if(!graphic.getPoints().isEmpty())
					    	graphic.addPoint(new Point(elapsedTime, graphic.getPoints().get(graphic.getPoints().size()-1).y));
						graphic.addPoint(new Point(elapsedTime, bestPair.getGamma()));
						
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
				timeAccomplished(executionResults);
				
			}
			
			long actualTime = System.currentTimeMillis();
		    long elapsedTime = actualTime - startTime;
			graphic.addPoint(new Point(elapsedTime, bestGamma));
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
			
			return best;
		}
		
		/**
		 * Add candidate to a fix size array ordered with the best candidates
		 * @param bestCandidates - the array of best candidates
		 * @param pairCandidate - new candidate
		 * @param maxSize - max size of the array
		 */
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
		
		/**
		 * Evaluate a neighbors and create a list with the best ones.
		 * @param bestCandidates - list of best candidates
		 * @param arrayNeighbors - list of neighbors to evaluate
		 * @param nonImprovingIn - non improving 
		 * @param current - current solution
		 * @param modifier - modifier of the structure
		 * @param gammaCalculator - component that calculate the gamma function
		 * @param executionResults - consolidated results
		 * @throws Exception
		 */
		private void getBestCandidates(ArrayList<PairVO> bestCandidates, ArrayList<PairVO> arrayNeighbors, int nonImprovingIn, IStructure current, IModifier modifier, IGammaCalculator gammaCalculator,ExecutionResults executionResults) throws Exception{
			
			for (int index = 0; index < arrayNeighbors.size() && !optimalAchieved && nonImprovingIn>=0; index++) {
				PairVO pairCandidate = arrayNeighbors.get(index);
				//if(current instanceof Graph){
				//	((Graph)current).drawGraph3("./results/graph/grafo"+iterations+index+".html", true, pairCandidate);
				//}
				IStructure candidate = modifier.performModification(pairCandidate,current);
				if(candidate ==null){
					timeAccomplished(executionResults);
					continue;
				}
				//if(current instanceof Graph){
				//	((Graph)candidate).drawGraph3("./results/graph/grafo"+iterations+index+"a.html", true, pairCandidate);
				//}
				numberOfVisitedNeighbors++;
				double gammaCandidate = gammaCalculator.calculateGamma(candidate);
				pairCandidate.setGamma(gammaCandidate);
				
				addToBestCandidates(bestCandidates,pairCandidate, bestNeighborsSize);
				candidate.clean();
				nonImprovingIn--;
				
				timeAccomplished(executionResults);
			}
		}
		
		private void timeAccomplished(ExecutionResults executionResults){
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
