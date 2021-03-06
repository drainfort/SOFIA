package neighborCalculator;

import java.util.ArrayList;
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
	 * Calculates a neighbor from a given solution
	 * 
	 * @param currentStructure
	 * 			The structure that represents the current solution
	 * @return pair
	 * 			The movement that should be performed to create a neighbor
	 * 			The calculate neighbor
	 * @throws Exception - Problem in method
	 */
	public PairVO calculateNeighbor(IStructure currentStructure) throws Exception;
	
	/**
	 * Calculates a neighborhood of the given size from a given solution
	 * 
	 * @param currentStructure
	 * 			The structure that represents the current solution
	 * @param size
	 * 			Max size of the neighborhood
	 * @return pairs
	 * 			The movements that should be performed to create a neighbor
	 * @throws Exception - Problem in method
	 */
	public ArrayList<PairVO> calculateNeighborhood(IStructure currentStructure, long size) throws Exception;

	/**
	 * Calculates the complete neighborhood from the given solution
	 * 
	 * @param currentStructure
	 * 			The structure that represents the current solution
	 * 
	 * @return pairs
	 * 			The movements that should be performed to create a neighbor
	 * @throws Exception - Problem in method
	 */
	public ArrayList<PairVO> calculateCompleteNeighborhood(IStructure currentStructure) throws Exception;
}
