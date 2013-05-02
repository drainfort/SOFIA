package gammaCalculator;

import common.types.PairVO;

import structure.IStructure;

public interface IGammaCalculator {

	public int calculateGamma(IStructure graph) throws Exception;

	public int updateGamma(IStructure candidate, PairVO pairCandidate) throws Exception;
}
