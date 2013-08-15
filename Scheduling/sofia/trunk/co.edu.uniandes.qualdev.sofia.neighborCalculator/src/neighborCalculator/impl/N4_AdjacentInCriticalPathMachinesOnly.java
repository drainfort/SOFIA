package neighborCalculator.impl;

import java.util.ArrayList;

import structure.IOperation;
import structure.IStructure;
import structure.impl.CriticalPath;

import common.types.OperationIndexVO;
import common.types.PairVO;

import neighborCalculator.INeighborCalculator;


/**
 * Class that is able to calculate a neighbor of a solution using the algorithm
 * Adjacent Shift on a critical route described by Van Laarhoven et al. in 
 * Van Laarhoven PJM, Aarts EHL, Lenstra JK. Job shop scheduling by simulated annealing. Operations Research 1992;40(1):113–25.
 * 
 * @author Jaime Romero
 * @author David Mendez Acuna
 */
public class N4_AdjacentInCriticalPathMachinesOnly implements INeighborCalculator {

	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------

	public N4_AdjacentInCriticalPathMachinesOnly() {

	}

	// -----------------------------------------------
	// Methods
	// -----------------------------------------------

	@Override
	public PairVO calculateNeighbor(IStructure currentStructure) throws Exception {
		IStructure clone = currentStructure.cloneStructure();
		OperationIndexVO initialOperationIndex = null;
		OperationIndexVO finalOperationIndex = null;
		boolean found = false;
		
		while(!found){
			// Obtaining all the critical paths of the current solutions
			ArrayList<CriticalPath> routes = clone.getCriticalPaths();
			
			// Selecting one of the critical paths
			int number = randomNumber(0, routes.size() - 1);
			ArrayList<IOperation> selectedCriticalPath = routes.get(number).getRoute();

			// Selecting an adjacent pair of operations
			int i = randomNumber(0, selectedCriticalPath.size() - 2);
			IOperation initialNode = selectedCriticalPath.get(i);
			IOperation finalNode = selectedCriticalPath.get(i + 1);
			
			if(initialNode.getOperationIndex().getStationId() == finalNode.getOperationIndex().getStationId()){
				found = true;
				initialOperationIndex = new OperationIndexVO(initialNode.getOperationIndex().getJobId(), initialNode.getOperationIndex().getStationId());
				finalOperationIndex = new OperationIndexVO(finalNode.getOperationIndex().getJobId(), finalNode.getOperationIndex().getStationId());
			}
		}
		return new PairVO(initialOperationIndex, finalOperationIndex);
	}

	@Override
	public ArrayList<PairVO> calculateNeighborhood(IStructure currentStructure, long size)
			throws Exception {
		ArrayList<PairVO> neighborhood = new ArrayList<PairVO>();
		IStructure clone = currentStructure.cloneStructure();
		
		// Obtaining all the critical paths of the current solutions
		ArrayList<CriticalPath> routes = clone.getCriticalPaths();
		
		// Selecting one of the critical paths
		int number = randomNumber(0, routes.size() - 1);
		ArrayList<IOperation> selectedCriticalPath = routes.get(number).getRoute();

		int exit = 0;
		while(neighborhood.size() < size){
			OperationIndexVO initialOperationIndex = null;
			OperationIndexVO finalOperationIndex = null;
			boolean found = false;
			
			while(!found){
				// Selecting an adjacent pair of operations
				int i = randomNumber(0, selectedCriticalPath.size() - 2);
				IOperation initialNode = selectedCriticalPath.get(i);
				IOperation finalNode = selectedCriticalPath.get(i + 1);
				
				if(initialNode.getOperationIndex().getStationId() == finalNode.getOperationIndex().getStationId()){
					found = true;
					initialOperationIndex = new OperationIndexVO(initialNode.getOperationIndex().getJobId(), initialNode.getOperationIndex().getStationId());
					finalOperationIndex = new OperationIndexVO(finalNode.getOperationIndex().getJobId(), finalNode.getOperationIndex().getStationId());
				
					// Adding the new neighbor to the array if it is not previously considered
					PairVO temp = new PairVO(initialOperationIndex, finalOperationIndex);
					if(!neighborhood.contains(temp)){
						neighborhood.add(temp);
						exit=0;
					}else{
						exit++;
						if(exit>=100){
							return neighborhood;
						}
					}
				}
			}
		}
		return neighborhood;
	}
	
	@Override
	public ArrayList<PairVO> calculateCompleteNeighborhood(
			IStructure currentStructure) throws Exception {
		
		ArrayList<PairVO> neighborhood = new ArrayList<PairVO>();
		IStructure clone = currentStructure.cloneStructure();
		
		// Obtaining all the critical paths of the current solutions
		ArrayList<CriticalPath> routes = clone.getCriticalPaths();
		
		// Selecting one of the critical paths
		for(int j =0; j < routes.size();j++){
			ArrayList<IOperation> selectedCriticalPath = routes.get(j).getRoute();		
			for (int i = 0; i < selectedCriticalPath.size() - 1; i++) {
				IOperation initialNode = selectedCriticalPath.get(i);
				IOperation finalNode = selectedCriticalPath.get(i + 1);
			
				if(initialNode.getOperationIndex().getStationId() == finalNode.getOperationIndex().getStationId()){
					OperationIndexVO initialOperationIndex = new OperationIndexVO(initialNode.getOperationIndex().getJobId(), initialNode.getOperationIndex().getStationId());
					OperationIndexVO finalOperationIndex = new OperationIndexVO(finalNode.getOperationIndex().getJobId(), finalNode.getOperationIndex().getStationId());
				
					PairVO temp = new PairVO(initialOperationIndex, finalOperationIndex);
					neighborhood.add(temp);
				}
			}
		}
		
		return neighborhood;
	}
	
	// -----------------------------------------------
	// Utilities
	// -----------------------------------------------
	
	private static int randomNumber(int min, int max) {
		return (int) Math.round((Math.random() * (max - min)) + min);
	}
}