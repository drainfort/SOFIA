package initialSolBuilder;

import gammaCalculator.IGammaCalculator;

import java.util.ArrayList;

import common.types.BetaVO;

import structure.IStructure;

/**
 * Interface for the construction of an initial solution
 * 
 * @author David Mendez-Acuna
 * @author Oriana Cendales
 */
public interface IInitialSolBuilder {

	/**
	 * Creates an initial solution according to the information in the parameter
	 * 
	 * @param problemFiles
	 * 			Array with the files that contain the information of the problem.
	 * @param betas
	 * 			Array with the betas associated to the problem.
	 * @param structureFactory
	 * 			Factory that is able to create a solution structure
	 * @param gammaCalculator
	 * 			Component that is able to calculate the gamma value of a given solution
	 * @return vector
	 * 			Vector representing the initial solution provided by the component.
	 * @throws Exception
	 */
	public IStructure createInitialSolution(ArrayList<String> problemFiles,
			ArrayList<BetaVO> betas, String structureFactory, IGammaCalculator gammaCalculator) throws Exception;
	
	/**
	 * Returns the name of the constructive algorithm responsible for the creation of
	 * the initial solution
	 * .
	 * @return Name of the constructive algorithm
	 */
	public String getName();
}
