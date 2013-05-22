package initialSolBuilder.impl;

import gammaCalculator.IGammaCalculator;
import initialSolBuilder.IInitialSolBuilder;
import java.util.ArrayList;

import structure.IStructure;
import common.types.BetaVO;

/**
 * Class that calculates returns the best dispatching rule for the current
 * instance
 * 
 * @author Jaime Romero
 * @author David Mendez-Acuna
 */
public class RandomDispatchingRule implements IInitialSolBuilder{

	// -----------------------------------------------
	// Constants
	// -----------------------------------------------
	
	private static final String constructiveAlgorithmName = "BestDispatchingRule";
	
	// -----------------------------------------------
	// Attributes
	// -----------------------------------------------
	
	private String name;
	
	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------
	
	public RandomDispatchingRule(){
		name = constructiveAlgorithmName;
	}
	
	// -----------------------------------------------
	// Methods
	// -----------------------------------------------
	
	@Override
	public IStructure createInitialSolution(ArrayList<String> problemFiles, ArrayList<BetaVO> betas, String structureFactory, IGammaCalculator gammaCalculator) throws Exception {

		ArrayList<IInitialSolBuilder> arrayClasses = new ArrayList<IInitialSolBuilder>();
		
		IInitialSolBuilder lpt = new LPTNonDelay();
		IInitialSolBuilder lrpt = new LRPTNonDelay();
		IInitialSolBuilder spt = new SPTNonDelay();
		IInitialSolBuilder srpt = new SRPTNonDelay();
//		IInitialSolBuilder afslpt = new AFSLPTNonDelay();
//		IInitialSolBuilder afslrpt = new AFSLRPTNonDelay();
//		IInitialSolBuilder afsspt = new AFSSPTNonDelay();
//		IInitialSolBuilder afssrpt = new AFSSRPTNonDelay();
		
		arrayClasses.add(lpt);
		arrayClasses.add(lrpt);
		arrayClasses.add(spt);
		arrayClasses.add(srpt);
//		arrayClasses.add(afslpt);
//		arrayClasses.add(afslrpt);
//		arrayClasses.add(afsspt);
//		arrayClasses.add(afssrpt);
		
		int number = randomNumber(0, arrayClasses.size()-1);
		return arrayClasses.get(number).createInitialSolution(problemFiles, betas, structureFactory, gammaCalculator);
	}

	@Override
	public String getName() {
		return name;
	}
	
	// -----------------------------------------------
	// Utilities
	// -----------------------------------------------
		
		/**
		 * Returns a random number in the interval between the min and the max
		 * parameters
		 * 
		 * @param min
		 *            . Lower value of the interval
		 * @param max
		 * @return
		 */
		private static int randomNumber(int min, int max) {
			return (int) Math.round((Math.random() * (max - min)) + min);
		}


}