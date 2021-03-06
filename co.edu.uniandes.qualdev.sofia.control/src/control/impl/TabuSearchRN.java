package control.impl;

import java.util.ArrayList;
import java.util.Properties;

import chart.printer.ChartPrinter;

import structure.IStructure;
import structure.impl.CriticalPath;
import structure.impl.Graph;

import common.types.PairVO;
import common.utils.ExecutionLogger;
import common.utils.ExecutionResults;
import common.utils.Graphic;
import common.utils.Point;
import common.utils.RandomNumber;
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
			INeighborCalculator neighborCalculator, ArrayList<IModifier> modifiers,
				IGammaCalculator gammaCalculator, Properties params, Integer optimal, 
					boolean isOptimal) throws Exception {
				
		IModifier modifier = modifiers.get(0);
		ExecutionLogger.getInstance().initializeLogger(resultFile, instanceName);
		
		/*if(initialSolution instanceof Graph)
			((Graph)initialSolution).drawGraph3("./results/graph/prueba.html", true, null);*/
		
		long startTime = System.currentTimeMillis();
		long stopTime = Integer.MAX_VALUE;
		
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

		Graphic graphic = new Graphic();
		graphic.addPoint(new Point(0, GammaInitialSolution));
		int x= 0;
		
		while (iterations >= 0 && nonImprovingOut >= 0 && !optimalAchieved) {
			
			IStructure bestCandidate = null;
			double gammaBestCandidate = Integer.MAX_VALUE;
			PairVO bestPairCandidate = null;
			
			int nonImprovingIn = (Integer) params.get("non-improving-in");
			if(nonImprovingIn<0)
				nonImprovingIn= Integer.MAX_VALUE;

			for (int index = 0; index < arrayNeighbors.size() && !optimalAchieved && nonImprovingIn>=0; index++) {
				x++;
				PairVO pairCandidate = arrayNeighbors.get(index);
				IStructure candidate = modifier.performModification(pairCandidate,current);
				if(candidate ==null){
					long actualTime = System.currentTimeMillis();
				    long elapsedTime = actualTime - startTime;
				    
				    if(elapsedTime>=stopTime){
				    	System.out.println("TIME!!!");
				    	optimalAchieved = true;
				    	executionResults.setStopCriteria(2);
				    }
					continue;
				}
				numberOfVisitedNeighbors++;
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
						double acceptaceValue = RandomNumber.getInstance().randomDouble();
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
					long actualTime = System.currentTimeMillis();
				    long elapsedTime = actualTime - startTime;
				    if(!graphic.getPoints().isEmpty())
				    	graphic.addPoint(new Point(elapsedTime, graphic.getPoints().get(graphic.getPoints().size()-1).y));
					graphic.addPoint(new Point(elapsedTime, bestGamma));
					if(ExecutionLogger.getInstance().isUseLogger()){
						ExecutionLogger.getInstance().printLog("Vector: "+best.getOperations());
						ArrayList<CriticalPath> paths = best.getCriticalPaths();
						ExecutionLogger.getInstance().printLog("Critical Paths: "+paths);
						ExecutionLogger.getInstance().printLog("Pair X: "+bestPairCandidate.getoX());
						ExecutionLogger.getInstance().printLog("Pair Y: "+bestPairCandidate.getoY());
						boolean contains = false;
						for (int i = 0; i < paths.size() && !contains; i++) {
							contains = paths.get(i).containsOperationIndex(bestPairCandidate.getoX());
						}
						boolean containsY = false;
						for (int i = 0; i < paths.size() && !containsY; i++) {
							containsY = paths.get(i).containsOperationIndex(bestPairCandidate.getoY());
						}
						ExecutionLogger.getInstance().printLog("In critical Path  X: "+ contains);
						ExecutionLogger.getInstance().printLog("In critical Path  Y: "+ containsY);
					}

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
		ExecutionResults result = obtainExecutionResults(best, gammaCalculator, (Boolean)params.get("printTable"), (Boolean)params.get("printSolutions"),(Boolean)params.get("printInitialSolution"), (Boolean)params.get("printLog"), (Boolean)params.get("printImprovement"), elapsedTime);
		result.setNumberOfVisitedNeighbors(numberOfVisitedNeighbors);
		return result;
	}
	
	public static long permutacion(int N, int r){
        long multi = 1;
        for (int i = N-r+1; i <= N; i++) {
            multi = multi * i;
        }
        return multi;
    }
}