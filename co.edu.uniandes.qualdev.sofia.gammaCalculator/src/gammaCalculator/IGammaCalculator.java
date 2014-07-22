package gammaCalculator;

import common.types.PairVO;

import structure.IStructure;

public interface IGammaCalculator {

	/**
	 * Calculate the gamma function by means of a data structure
	 * @param structure the current structure
	 * @return double - gamma value
	 * @throws Exception - Functionality error
	 */
	public double calculateGamma(IStructure structure) throws Exception;

	/**
	 * Updates the gamma value by means of the change in the structure 
	 * @param candidate- the current structure
	 * @param pairCandidate - the pair of operations to be changed.
	 * @return double - gamma value
	 * @throws Exception - Functionality error
	 */
	public double updateGamma(IStructure candidate, PairVO pairCandidate) throws Exception;
}
