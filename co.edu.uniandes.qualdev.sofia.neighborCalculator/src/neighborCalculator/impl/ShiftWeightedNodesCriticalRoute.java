package neighborCalculator.impl;

import java.util.ArrayList;

import structure.IStructure;

import common.types.OperationIndexVO;
import common.types.PairVO;
import common.utils.RandomNumber;

import neighborCalculator.INeighborCalculator;

/**
 * Class that is able to calculate a neighbor of a solution using the algorithm
 * Adjacent Shift on a critical route.
 * 
 * @author Jaime Romero
 */
public class ShiftWeightedNodesCriticalRoute implements INeighborCalculator {


	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------

	public ShiftWeightedNodesCriticalRoute() {

	}

	// -----------------------------------------------
	// Methods
	// -----------------------------------------------

	@Override
	public PairVO calculateNeighbor(IStructure currentGraph) throws Exception {
		IStructure clone = currentGraph.cloneStructure();
		clone.getCriticalPaths();
		ArrayList<int[]>temp = clone.getWeightedNodesCriticaRoute();
		int i = 0;
		int number = RandomNumber.getInstance().randomNumber(0,temp.size()); 
		OperationIndexVO initialOperationIndex = new OperationIndexVO(temp.get(i)[0],temp.get(i)[1]);
		OperationIndexVO finalOperationIndex = new OperationIndexVO(temp.get(number)[0], temp.get(number)[1]);
			
		return new PairVO(initialOperationIndex, finalOperationIndex);
		
	}

	@Override
	public ArrayList<PairVO> calculateNeighborhood(IStructure currentGraph, long size)
			throws Exception {
		
		ArrayList<PairVO> neighborhood = new ArrayList<PairVO>();
		currentGraph.getCriticalPaths();
		ArrayList<int[]>temp = currentGraph.getWeightedNodesCriticaRoute();
		int i = 0;
		int j = 1;
		while(neighborhood.size()<size && i<temp.size()){
			
			if(temp.get(i)[2]>0 && temp.get(j)[2]>0  ){
				OperationIndexVO initialOperationIndex = new OperationIndexVO(temp.get(i)[0],temp.get(i)[1]);
				OperationIndexVO finalOperationIndex = new OperationIndexVO(temp.get(j)[0], temp.get(j)[1]);
				PairVO pair = new PairVO(initialOperationIndex, finalOperationIndex);
				if(!neighborhood.contains(pair)){
					neighborhood.add(pair);
				}
		    }
			
			j++;
			
			if(j == temp.size()){
				i++;
				j=i+1;
			}
		}
		
		return neighborhood;
	}
	
	@Override
	public ArrayList<PairVO> calculateCompleteNeighborhood(
			IStructure currentStructure) throws Exception {
		ArrayList<PairVO> neighborhood = new ArrayList<PairVO>();
		IStructure clone = currentStructure.cloneStructure();
		clone.getCriticalPaths();
		ArrayList<int[]>temp = clone.getWeightedNodesCriticaRoute();
		for(int i = 0; i< temp.size();i++){
			OperationIndexVO initialOperationIndex = new OperationIndexVO(temp.get(i)[0],temp.get(i)[1]);
			
			for(int j = i ; j< temp.size();j++){
				OperationIndexVO finalOperationIndex = new OperationIndexVO(temp.get(j)[0], temp.get(j)[1]);
				PairVO pair = new PairVO(initialOperationIndex, finalOperationIndex);
				if(!neighborhood.contains(pair)&& !initialOperationIndex.equals(finalOperationIndex)){
					neighborhood.add(pair);
				}
			}
		}
		
		
		return neighborhood;
	}
	

}