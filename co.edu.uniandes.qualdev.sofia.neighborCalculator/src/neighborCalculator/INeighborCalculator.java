package neighborCalculator;

import java.util.ArrayList;
import java.util.Properties;

import common.types.NeighborInformation;
import common.types.PairVO;

import structure.IStructure;


/**
 * Component that is able to calculate a neighbor from a given graph
 * 
 * @author David Mendez-Acuna
 * @author Jaime Romero
 */
public interface INeighborCalculator {

	/**
	 * Calculates a neighbor from a given vector
	 * 
	 * @param currentVector
	 * 			The vector that represents the current solution
	 * @return pair
	 * 			The movement that should be performed to create a neighbor
	 * 			The calculate neighbor
	 * @throws Exception
	 */
	public PairVO calculateNeighbor(IStructure currentVector) throws Exception;
	
	/**
	 * Calculates a neighborhood from a given vector
	 * 
	 * @param currentVector
	 * 			The vector that represents the current solution
	 * @return pairs
	 * 			The movements that should be performed to create a neighbor
	 * 			The calculate neighbor
	 * @throws Exception
	 */
	public ArrayList<PairVO> calculateNeighborhood(IStructure currentVector, int start, int end) throws Exception;
}
