package gammaCalculator;

import common.types.PairVO;

import structure.IStructure;

public interface IGammaCalculator {

	public double calculateGamma(IStructure graph) throws Exception;

	public double updateGamma(IStructure candidate, PairVO pairCandidate) throws Exception;
}
