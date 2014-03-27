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
import control.Control;

import modifier.IModifier;
import neighborCalculator.INeighborCalculator;
import gammaCalculator.IGammaCalculator;

public class SimpleSimulatedAnnealing extends Control {

	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------

	private long startTime;
	private long stopTime;
	private boolean optimalAchieved;
	int numberOfVisitedNeighbors=0;
	
	public SimpleSimulatedAnnealing() {
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
		ExecutionLogger.getInstance().initializeLogger(resultFile, instanceName);
				
		double GammaInitialSolution = gammaCalculator.calculateGamma(initialSolution);
		executionResults = new ExecutionResults();
		executionResults.setInitialCmax(GammaInitialSolution);
		executionResults.setOptimal(optimal);
		startTime = System.currentTimeMillis();
		stopTime = Integer.MAX_VALUE;
		
		if(params.get("maxExecutionTime")!=null){
			if((Integer) params.get("maxExecutionTime")!=-1)
				stopTime = (Integer) params.get("maxExecutionTime") *1000;	
		}
		
		this.So = initialSolution.cloneStructure();
		System.out.println();
		long actualTime = System.currentTimeMillis();
	    long elapsedTime = actualTime - startTime;
		
	    IStructure XBest = simulatedAnnealing(params, initialSolution, gammaCalculator, neighborCalculator, modifier, optimal, isOptimal, executionResults, startTime, stopTime);
	    
		ExecutionResults result = obtainExecutionResults(XBest, gammaCalculator, (Boolean)params.get("printTable"), (Boolean)params.get("printSolutions"),(Boolean)params.get("printInitialSolution"), (Boolean)params.get("printLog"), elapsedTime);
		result.setNumberOfVisitedNeighbors(numberOfVisitedNeighbors);
		
		return result;
	}
	
	public IStructure simulatedAnnealing (Properties params, IStructure initialSolution, IGammaCalculator gammaCalculator, INeighborCalculator neighborCalculator, IModifier modifier, Integer optimal, boolean isOptimal, ExecutionResults executionResults, long startTime, long stopTime) throws Exception{
		
		this.startTime = startTime;
		this.stopTime = stopTime;
		
		Double temperature = (Double) params.get("T0");

		// Provides an initial solution (X) from the problem
		IStructure X = initialSolution;
		double XCMax = gammaCalculator.calculateGamma(X);
		
		// Initializes the best solution (XBest) as the first one (X)
		IStructure XBest = X.cloneStructure();
		double XBestCMax = gammaCalculator.calculateGamma(XBest);
		System.out.println("initial solution (XBestCMax): " + XBestCMax);
		ExecutionLogger.getInstance().printLog("initial solution (XBestCMax): " + XBestCMax);
		
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
		Graphic graphic = new Graphic();
		graphic.addPoint(new Point(0, XBestCMax));
		int x = 0;
		
		while (temperature >= finalTemperature &&  temperatureReductions < nonImprovingOut && !optimalAchieved) {

			Integer k = (Integer) params.get("k");
			Integer nonImprovingIn = (Integer) params.get("non-improving-in");

			if(nonImprovingIn==-1)
				nonImprovingIn= Integer.MAX_VALUE;
			
			/*if(X instanceof Graph){
				((Graph)X).drawGraph3("./results/graph2/grafo"+temperature+".html", true, null);
				ExecutionResults result = obtainExecutionResults(X, gammaCalculator, false, true,false,false, 0);
				result.setNumberOfVisitedNeighbors(0);
				ArrayList<ExecutionResults> results = new ArrayList<ExecutionResults>();
				results.add(result);
				ChartPrinter.getInstance().addResults(results);
				ChartPrinter.getInstance().printGlobalResultsHTML("./results/graph2/grafo-gantt-"+temperature+".html", "./results/graph2/grafoL"+temperature+".html");
				ChartPrinter.getInstance().restart();
			}*/
			
			while (k > 0 && !optimalAchieved && nonImprovingIn>=0){
				
				x++;
				// Obtains a next solution (Y) from the current one (X)
				PairVO YMovement = neighborCalculator.calculateNeighbor(X);
				IStructure Y = modifier.performModification(YMovement, X);

				if(Y ==null){
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
					ExecutionLogger.getInstance().printLog("Improvement: "+XBestCMax);
					graphic.addPoint(new Point(x, XBestCMax));
					if(ExecutionLogger.getInstance().isUseLogger()){
						ExecutionLogger.getInstance().printLog("Vector: "+YMovement);
						ArrayList<CriticalPath> paths = XBest.getCriticalPaths();
						ExecutionLogger.getInstance().printLog("Critical Paths: "+paths);
						ExecutionLogger.getInstance().printLog("Pair X: "+YMovement.getoX());
						ExecutionLogger.getInstance().printLog("Pair X: "+YMovement.getoY());
						boolean contains = false;
						for (int i = 0; i < paths.size() && !contains; i++) {
							contains = paths.get(i).containsOperationIndex(YMovement.getoX());
						}
						boolean containsY = false;
						for (int i = 0; i < paths.size() && !containsY; i++) {
							containsY = paths.get(i).containsOperationIndex(YMovement.getoY());
						}
						ExecutionLogger.getInstance().printLog("In critical Path  X: "+ contains);
						ExecutionLogger.getInstance().printLog("In critical Path  Y: "+ containsY);
					}
					
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
								executionResults.setStopCriteria(3);
					
								System.out.println("Stop Criteria: Max number of improvements");
								ExecutionLogger.getInstance().printLog("Stop Criteria: Max number of improvements");
							}
						}
					}
				}
			    
				timeAccomplished();
			    nonImprovingIn--;
				k--;
				Y.clean();
			}
			
			
			// Temperature reductions
			Double coolingFactor = (Double) params.get("coolingFactor");
			temperature = temperature * (coolingFactor);
			temperatureReductions ++;
			
		}
		if(graphic.getPoints().size()>1)
			executionResults.addGraphic(graphic);
		
		if(temperatureReductions>=nonImprovingOut){
			executionResults.setStopCriteria(1);
		
			System.out.println("Stop Criteria: Non improving");
			ExecutionLogger.getInstance().printLog("Stop Criteria: Non improving");
		}
		
		return XBest;
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
}
