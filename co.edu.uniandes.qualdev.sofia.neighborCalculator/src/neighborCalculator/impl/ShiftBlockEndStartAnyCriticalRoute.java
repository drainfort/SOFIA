package neighborCalculator.impl;

import java.util.ArrayList;

import structure.IOperation;
import structure.IStructure;
import structure.impl.CriticalRoute;

import common.types.OperationIndexVO;
import common.types.PairVO;

import neighborCalculator.INeighborCalculator;

/**
 * Class that is able to calculate a neighbor of a solution using the algorithm
 * Adjacent Shift on a critical route.
 * 
 * @author Jaime Romero
 */
public class ShiftBlockEndStartAnyCriticalRoute implements INeighborCalculator {

	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------

	public ShiftBlockEndStartAnyCriticalRoute() {

	}

	// -----------------------------------------------
	// Methods
	// -----------------------------------------------

	
	@Override
	public PairVO calculateNeighbor(IStructure currentGraph) throws Exception {
		IStructure clone = currentGraph.cloneStructure();
		ArrayList<CriticalRoute> routes = clone.getLongestRoutes();
		
		int number = randomNumber(0, routes.size() - 1);
		ArrayList<ArrayList<IOperation>> blocks = routes.get(number).getBlocks();
			
		number = randomNumber(0, blocks.size() - 1);
	    ArrayList<IOperation> selectedBlock = blocks.get(number);
		
		int j = randomNumber(0, 1);
		IOperation initialNode = selectedBlock.get(j);
		
		if(j==1)
			initialNode= selectedBlock.get(selectedBlock.size()-1);
		
		int i = randomNumber(selectedBlock.size()/2 - selectedBlock.size()/8, selectedBlock.size()/2 + selectedBlock.size()/8);
		IOperation finalNode = selectedBlock.get(i);

		OperationIndexVO initialOperationIndex = new OperationIndexVO(initialNode.getOperationIndex().getJobId(), initialNode.getOperationIndex().getStationId());
		OperationIndexVO finalOperationIndex = new OperationIndexVO(finalNode.getOperationIndex().getJobId(), finalNode.getOperationIndex().getStationId());
		
		return new PairVO(initialOperationIndex,finalOperationIndex);
		
	}

	@Override
	public ArrayList<PairVO> calculateNeighborhood(IStructure currentGraph, int size)
			throws Exception {
		
		ArrayList<PairVO> neighborhood = new ArrayList<PairVO>();
		IStructure clone = currentGraph.cloneStructure();
		ArrayList<CriticalRoute> routes = clone.getLongestRoutes();
		int salida=0;
		int number = randomNumber(0, routes.size() - 1);
		ArrayList<ArrayList<IOperation>> blocks = routes.get(number).getBlocks();
		
		while(neighborhood.size()<size){
			
			number = randomNumber(0, blocks.size() - 1);
		    ArrayList<IOperation> selectedBlock = blocks.get(number);
			
			int j = randomNumber(0, selectedBlock.size()-2);
			IOperation initialNode = selectedBlock.get(j);
			int i = j+1;
			IOperation finalNode = selectedBlock.get(i);

			OperationIndexVO initialOperationIndex = new OperationIndexVO(initialNode.getOperationIndex().getJobId(), initialNode.getOperationIndex().getStationId());
			OperationIndexVO finalOperationIndex = new OperationIndexVO(finalNode.getOperationIndex().getJobId(), finalNode.getOperationIndex().getStationId());
			
			PairVO temp = new PairVO(initialOperationIndex, finalOperationIndex);
			if(!neighborhood.contains(temp)){
				neighborhood.add(temp);
				salida=0;
			}else{
				salida++;
				if(salida>=100)
					return neighborhood;
			}
		}
		
		return neighborhood;
	}
	
	@Override
	public ArrayList<PairVO> calculateCompleteNeighborhood(
			IStructure currentStructure) throws Exception {
		// TODO calculateCompleteNeighborhood for ShiftBlockEndStartAnyCriticalRoute
		return null;
	}
	
	// -----------------------------------------------
	// Utilities
	// -----------------------------------------------
	
	private static int randomNumber(int min, int max) {
		return (int) Math.round((Math.random() * (max - min)) + min);
	}
}