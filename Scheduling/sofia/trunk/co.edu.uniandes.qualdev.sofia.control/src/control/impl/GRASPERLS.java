package control.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import structure.IStructure;

import common.types.PairVO;
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
public class GRASPERLS extends Control {

	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------

	public GRASPERLS() {
		super();
	}

	// -----------------------------------------------
	// Methods
	// -----------------------------------------------

	@Override
	public ExecutionResults execute(IStructure initialSolution,
			INeighborCalculator neighborCalculator, IModifier modifier,
			IGammaCalculator gammaCalculator, Properties params, Integer optimal, int yuSolution)
			throws Exception {

		executionResults = new ExecutionResults();
		int numberOfNeighbors[]=new int[1];  numberOfNeighbors[0]=0;
		int GammaInitialSolution = gammaCalculator.calculateGamma(initialSolution);
		executionResults.setInitialCmax(GammaInitialSolution);
		this.So = initialSolution.cloneStructure();
		
		IStructure best = initialSolution.cloneStructure();
		int bestGamma = gammaCalculator.calculateGamma(best);
		
		// Obtaining the parameters from the algorithm configuration.
		executionResults.setOptimal(optimal);
		
		
		if(optimal.intValue() >= bestGamma){
			System.out.println("****Optimal CMax found! constructive stage");
		}else{
			System.out.print("Iniciando fase de busqueda local.....");
			boolean continua[]=new boolean[1];   continua[0]=true;
			int gammaBestSolution[]=new int[1];  gammaBestSolution[0]=bestGamma;
			Set<String> controlExplorados[] = new HashSet[1];  
			controlExplorados[0]=new HashSet<String>();
			
			//Keep the minimum values obtained at each depth
			int bounds[]=new int[(Integer) params.get("maxLSDepth")];			
			for (int i=0; i<bounds.length;i++)  
				bounds[i]=Integer.MAX_VALUE;			
			
			System.out.println(gammaBestSolution[0]);
			ArrayList<PairVO> arrayNeighbors = neighborCalculator.calculateNeighborhood(initialSolution, (initialSolution.getTotalStations() * initialSolution.getTotalJobs()) - 1);
			
			//Fase de busqueda local exhaustiva
			best = ERLS(initialSolution, modifier,
						gammaCalculator, arrayNeighbors, optimal.intValue(),
						continua, 0, gammaBestSolution, (Integer) params.get("maxLSDepth"),
						(Integer) params.get("strategyLS"), bounds, numberOfNeighbors, 
						(Integer) params.get("maxNeighbors"), (HashSet<String>[]) controlExplorados);
			
			bestGamma=gammaCalculator.calculateGamma(best);
			System.out.println(" Neighbors processed: "+numberOfNeighbors[0]);
			if(optimal.intValue() >= bestGamma){
				if(optimal.intValue() == bestGamma)
					System.out.println("****Optimal CMax found! local search stage");
				else
					System.out.println("****NEW OPTIMAL CMax found! local search stage");
			}	
		}
		ExecutionResults result = obtainExecutionResults(best, gammaCalculator, (Boolean)params.get("printTable"), (Boolean)params.get("printSolutions"),(Boolean)params.get("printInitialSolution"));
		result.setNumberOfVisitedNeighbors(numberOfNeighbors[0]);
		result.setBestCmax(bestGamma);

		return result;
	}
	
	/**
	 * ERLS:  Exhaustive Recursive Local Search
	 * @author Juan Pablo Caballero
	 * @return IVector of the Best solution found in the neighborhood of a neighbor of the current solution 
	 * @param  strategyLS 0 Complete 1 Best at each instance of ERLS 2 Bounded used at each depth.
	 */
	private IStructure ERLS(IStructure currentSolution,
					 IModifier modifier,
					 IGammaCalculator gammaCalculator,
					 ArrayList<PairVO> arrayNeighbors,
					 int optimal,
					 boolean continua[], 
					 int depth,
					 int gammaBestSolution[],
					 int maxLSDepth,
					 int strategyLS,
					 int bounds[],
					 int numberOfNeighbors[],
					 int maxNeighbors,
					 HashSet<String> ctrlExp[])throws Exception{
		
		
		depth++;
		IStructure best=currentSolution.cloneStructure();
		
		if((depth<maxLSDepth) && (numberOfNeighbors[0]<maxNeighbors)){
			//gamma of the best solution
			int gammaCurrentSolution=gammaCalculator.calculateGamma(currentSolution);

			ArrayList<PairVO> neighborsImproving = new ArrayList<PairVO>();
			ArrayList<PairVO> worstNeighborsImproving = new ArrayList<PairVO>();
			ArrayList<PairVO> bestNeighborsImproving = new ArrayList<PairVO>();
			
			
			//Local search phase: List of movements that generate the best improvement to the current solution.
			int minGamma=gammaCurrentSolution;   int maxGamma=-1;
			for (PairVO pair : arrayNeighbors) {
				IStructure candidate = modifier.performModification(pair, currentSolution);
				if(!ctrlExp[0].contains(candidate.toString())){
					int gammaCandidate = gammaCalculator.calculateGamma(candidate);
			
					//Considers only the pairs that generate improvement in the current solution
					if(gammaCurrentSolution > gammaCandidate){ 
						switch (strategyLS){
							case 0: //Exhaustive search, it will explore all the choices
								neighborsImproving.add(pair);
								break;
							case 1: //Only the best improvements are considered at each instance of ERLS
								if(minGamma==gammaCandidate)	
									neighborsImproving.add(pair);
								if(minGamma>gammaCandidate){
									//Clear the list
									neighborsImproving.clear();
									//Add the pair to the list of next current solution
									neighborsImproving.add(pair);
									minGamma=gammaCandidate;
								}
							break;
							case 2: //Uses the best value obtained at each depth to prune the candidates to explore 
								if(bounds[depth]==gammaCandidate){
									neighborsImproving.add(pair);
									minGamma=gammaCandidate;
								}	
								if(bounds[depth]>gammaCandidate){
									//Clear the list
									neighborsImproving.clear();
									//Add the pair to the list of next current solution
									neighborsImproving.add(pair);
									minGamma=gammaCandidate;
									bounds[depth]=gammaCandidate;
								}
								break;
							case 3: //the worst and best candidates are considered to be explored					
								if(minGamma==gammaCandidate)	
									bestNeighborsImproving.add(pair);
								if(minGamma>gammaCandidate){
									//Clear the list
									bestNeighborsImproving.clear();
									//Add the pair to the list of next current solution
									bestNeighborsImproving.add(pair);
									minGamma=gammaCandidate;
								}							
							
								if(maxGamma==gammaCandidate)	
									worstNeighborsImproving.add(pair);
								if(maxGamma<gammaCandidate){
									//Clear the list
									worstNeighborsImproving.clear();
									//Add the pair to the list of next current solution
									worstNeighborsImproving.add(pair);
									maxGamma=gammaCandidate;
								}
								break;
							case 4: //just the worst candidates are considered to be explored					
								if(maxGamma==gammaCandidate)	
									neighborsImproving.add(pair);
								if(maxGamma<gammaCandidate){
									//Clear the list
									neighborsImproving.clear();
									//Add the pair to the list of next current solution
									neighborsImproving.add(pair);
									maxGamma=gammaCandidate;
								}
								break;
							case 5: //the worst and best candidates are considered to be explored					
								if(minGamma==gammaCandidate)	
									bestNeighborsImproving.add(pair);
								if(minGamma>gammaCandidate){
									//Clear the list
									bestNeighborsImproving.clear();
									//Add the pair to the list of next current solution
									bestNeighborsImproving.add(pair);
									minGamma=gammaCandidate;
								}							
								neighborsImproving.add(pair);
								break;							
						};
					}
					candidate.clean();
				}
			}
			//
			if(strategyLS==3){
				neighborsImproving.addAll(worstNeighborsImproving);
				//remove from the list, the pairs contained into worstlist
				neighborsImproving.removeAll(bestNeighborsImproving);
				neighborsImproving.addAll(bestNeighborsImproving);
			}
			
			if(strategyLS==5){
				if(neighborsImproving.size()>bestNeighborsImproving.size())
					neighborsImproving.removeAll(bestNeighborsImproving);
			}
			
			
			//Local search phase: Exhaustive search of Neighbors
			int k=0;    int neighborsImprovingSize=neighborsImproving.size();
			while ( k < neighborsImprovingSize && continua[0] ) {
				numberOfNeighbors[0]++;  //Incrementar numero de vecinos procesados
			//	System.out.println("(bound:"+bounds[depth]+" depth:"+depth+" processed neighbors:"+numberOfNeighbors[0]+") ");
				
				PairVO pair=neighborsImproving.get(k);
				IStructure candidate = modifier.performModification(pair, currentSolution);
				
				ctrlExp[0].add(candidate.toString());
				
				IStructure improvedSolution = ERLS(candidate,	modifier,
												gammaCalculator, arrayNeighbors, optimal,
												continua, depth, gammaBestSolution,
												maxLSDepth, strategyLS, bounds,
												numberOfNeighbors,maxNeighbors,
												ctrlExp);
			
				int gammaImprovedSolution=gammaCalculator.calculateGamma(improvedSolution);
			
				if(gammaImprovedSolution < gammaCurrentSolution){
					gammaCurrentSolution=gammaImprovedSolution;
					best=improvedSolution;
				
					if(optimal>=gammaCurrentSolution)
						continua[0]=false;
					
					if(gammaCurrentSolution<gammaBestSolution[0]){
						gammaBestSolution[0]=gammaCurrentSolution;
						System.out.print(" ("+gammaBestSolution[0]+",n"+numberOfNeighbors[0]+",d"+depth+")");
					}	
				}
				k++;
			}
		}
		return best;
	}
}
