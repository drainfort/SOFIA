
package initialSolBuilder.impl;

import gammaCalculator.IGammaCalculator;

import initialSolBuilder.IInitialSolBuilder;

import java.util.ArrayList;

import structure.IStructure;

import common.types.BetaVO;

/**
 * Class that calculates an initial solution (represented in a permutation list)
 * from a given instance. It selects the bestNonDelay constructive algorithm for the particular instance
 * 
 * @author David Mendez-Acuna
 * @author Oriana Cendales
 * @author Jaime Romero
 */
public class BestDispatchingRule implements IInitialSolBuilder{
	
	// -----------------------------------------------
	// Constants
	// -----------------------------------------------
	
	private static final String constructiveAlgorithmName = "BestDispatchingRule";
	
	// -----------------------------------------------
	// Atributes
	// -----------------------------------------------
	
	private String name;
	
	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------
	
	public BestDispatchingRule(){
		name = constructiveAlgorithmName;
	}
	
	// -----------------------------------------------
	// Methods
	// -----------------------------------------------
	
	@Override
	public IStructure createInitialSolution(ArrayList<String> problemFiles,
			ArrayList<BetaVO> betas, String structureFactory, IGammaCalculator gammaCalculator) throws Exception {
		
		LPTNonDelay lpt = new LPTNonDelay();
		LRPTNonDelay lrpt = new LRPTNonDelay();
		SPTNonDelay spt = new SPTNonDelay();
		SRPTNonDelay srpt = new SRPTNonDelay();
		AFSLPTNonDelay afslpt = new AFSLPTNonDelay();
		AFSLRPTNonDelay afslrpt = new AFSLRPTNonDelay();
		AFSSPTNonDelay afsspt = new AFSSPTNonDelay();
		AFSSRPTNonDelay afssrpt = new AFSSRPTNonDelay();
		
		IStructure lptSolution = lpt.createInitialSolution(problemFiles, betas, structureFactory, gammaCalculator);
		IStructure lrptSolution = lrpt.createInitialSolution(problemFiles, betas, structureFactory, gammaCalculator);
		IStructure sptSolution = spt.createInitialSolution(problemFiles, betas, structureFactory, gammaCalculator);
		IStructure srptSolution = srpt.createInitialSolution(problemFiles, betas, structureFactory, gammaCalculator);
		IStructure afslptSolution = afslpt.createInitialSolution(problemFiles, betas, structureFactory, gammaCalculator);
		IStructure afslrptSolution = afslrpt.createInitialSolution(problemFiles, betas, structureFactory, gammaCalculator);
		IStructure afssptSolution = afsspt.createInitialSolution(problemFiles, betas, structureFactory, gammaCalculator);
		IStructure afssrptSolution = afssrpt.createInitialSolution(problemFiles, betas, structureFactory, gammaCalculator);
		
		int gammalptSolution = gammaCalculator.calculateGamma(lptSolution);
		System.out.println("gammalptSolution: " + gammalptSolution);
		
		int gammalrptSolution = gammaCalculator.calculateGamma(lrptSolution);
		System.out.println("gammalrptSolution: " + gammalrptSolution);
		
		int gammasptSolution = gammaCalculator.calculateGamma(sptSolution);
		System.out.println("gammasptSolution: " + gammasptSolution);
		
		int gammasrptSolution = gammaCalculator.calculateGamma(srptSolution);
		System.out.println("gammasrptSolution: " + gammasrptSolution);
		
		int gammaafslptSolution = gammaCalculator.calculateGamma(afslptSolution);
		System.out.println("gammaafslptSolution: " + gammaafslptSolution);
		
		int gammaafslrptSolution = gammaCalculator.calculateGamma(afslrptSolution);
		System.out.println("gammaafslrptSolution: " + gammaafslrptSolution);
		
		int gammaafssrptSolution = gammaCalculator.calculateGamma(afssrptSolution);
		System.out.println("gammaafssrptSolution: " + gammaafssrptSolution);
		
		
		ArrayList<IStructure> solutions = new ArrayList<IStructure>();
		solutions.add(lptSolution);
		solutions.add(lrptSolution);
		solutions.add(sptSolution);
		solutions.add(srptSolution);
		solutions.add(afslptSolution);
		solutions.add(afslrptSolution);
		solutions.add(afssptSolution);
		solutions.add(afssrptSolution);
		
		int menor=Integer.MAX_VALUE;
		
		IStructure bestSolution=null;
		for(int i=0; i< solutions.size();i++ ){
			int solutionGamma = gammaCalculator.calculateGamma(solutions.get(i));
			if(solutionGamma<menor){
				menor = solutionGamma;
				bestSolution = solutions.get(i);
			}
		}
		
		return bestSolution;
	}

	
	@Override
	public String getName() {
		return name;
	}
}