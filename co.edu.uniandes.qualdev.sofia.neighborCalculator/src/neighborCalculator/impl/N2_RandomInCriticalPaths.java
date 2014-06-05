package neighborCalculator.impl;

import java.util.ArrayList;

import structure.IOperation;
import structure.IStructure;
import structure.impl.CriticalPath;

import common.types.OperationIndexVO;
import common.types.PairVO;
import common.utils.RandomNumber;

import neighborCalculator.INeighborCalculator;


/**
 * Class that is able to calculate a neighbor of a solution using the algorithm
 * RandomInCriticalPath
 * 
 * @author David Mendez Acuna
 */
public class N2_RandomInCriticalPaths implements INeighborCalculator {

	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------

	public N2_RandomInCriticalPaths() {

	}

	// -----------------------------------------------
	// Methods
	// -----------------------------------------------

	@Override
	public PairVO calculateNeighbor(IStructure currentStructure) throws Exception {
		IStructure clone = currentStructure.cloneStructure();
		
		// Obtaining all the critical paths of the current solutions
		ArrayList<CriticalPath> routes = clone.getCriticalPaths();
		
		// Selecting one of the critical paths
		int number = RandomNumber.getInstance().randomNumber(0, routes.size() - 1);
		ArrayList<IOperation> selectedCriticalPath = routes.get(number).getRoute();

		// Selecting an adjacent pair of operations
		int i = RandomNumber.getInstance().randomNumber(0, selectedCriticalPath.size() - 1);
		IOperation initialNode = selectedCriticalPath.get(i);
		int j = RandomNumber.getInstance().randomNumber(0, selectedCriticalPath.size() - 1);
		while(i==j)
			j = RandomNumber.getInstance().randomNumber(0, selectedCriticalPath.size() - 1);
		IOperation finalNode = selectedCriticalPath.get(j);
		OperationIndexVO initialOperationIndex = new OperationIndexVO(initialNode.getOperationIndex().getJobId(), initialNode.getOperationIndex().getStationId());
		OperationIndexVO finalOperationIndex = new OperationIndexVO(finalNode.getOperationIndex().getJobId(), finalNode.getOperationIndex().getStationId());
		
		return new PairVO(initialOperationIndex, finalOperationIndex);
	}

	@Override
	public ArrayList<PairVO> calculateNeighborhood(IStructure currentStructure, long size)
			throws Exception {
	
		int amount = 0;
		int exit=0;
		ArrayList<PairVO> neighborhood = new ArrayList<PairVO>();
		IStructure clone = currentStructure.cloneStructure();
		
		// Obtaining all the critical paths of the current solutions
		ArrayList<CriticalPath> routes = clone.getCriticalPaths();
		while(amount < size){
			
			// Selecting one of the critical paths
			int number = RandomNumber.getInstance().randomNumber(0, routes.size() - 1);
			ArrayList<IOperation> selectedCriticalPath = routes.get(number).getRoute();

			// Selecting an adjacent pair of operations
			int i = RandomNumber.getInstance().randomNumber(0, selectedCriticalPath.size() - 1);
			IOperation initialNode = selectedCriticalPath.get(i);
			int j = RandomNumber.getInstance().randomNumber(0, selectedCriticalPath.size() - 1);
			while(i==j)
				j = RandomNumber.getInstance().randomNumber(0, selectedCriticalPath.size() - 1);
			
			IOperation finalNode = selectedCriticalPath.get(j);
			OperationIndexVO initialOperationIndex = new OperationIndexVO(initialNode.getOperationIndex().getJobId(), initialNode.getOperationIndex().getStationId());
			OperationIndexVO finalOperationIndex = new OperationIndexVO(finalNode.getOperationIndex().getJobId(), finalNode.getOperationIndex().getStationId());
			PairVO temp = new PairVO(initialOperationIndex, finalOperationIndex);
			if(!neighborhood.contains(temp)){
				neighborhood.add(temp);
				exit=0;
				amount++;
			}else{
				exit++;
				if(exit>=100){
					return neighborhood;
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
		for(int j =0; j < routes.size();j++){
			ArrayList<IOperation> selectedCriticalPath = routes.get(j).getRoute();
			
			for (int i = 0; i < selectedCriticalPath.size(); i++) {
				IOperation firstOperation = selectedCriticalPath.get(i);
				
				for (int i2 = 0; i2 < selectedCriticalPath.size(); i2++) {
					IOperation secondOperation = selectedCriticalPath.get(i2);
					if(i!=i2){
						PairVO temp = new PairVO(firstOperation.getOperationIndex(), secondOperation.getOperationIndex());
						neighborhood.add(temp);
					}
				}
			}
		}
		return neighborhood;
	}
	
}