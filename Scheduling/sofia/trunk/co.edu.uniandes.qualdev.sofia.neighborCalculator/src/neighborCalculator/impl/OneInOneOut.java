package neighborCalculator.impl;

import java.util.ArrayList;

import neighborCalculator.INeighborCalculator;
import structure.IOperation;
import structure.IStructure;
import structure.impl.CriticalPath;

import common.types.OperationIndexVO;
import common.types.PairVO;

public class OneInOneOut implements INeighborCalculator{

	// -----------------------------------------------
	// Methods
	// -----------------------------------------------
	
	@Override
	public PairVO calculateNeighbor(IStructure currentStructure) throws Exception {
		IStructure clone = currentStructure.cloneStructure();
		
		// Obtaining all the critical paths of the current solutions
		ArrayList<CriticalPath> routes = clone.getCriticalPaths();
		
		// Selecting one of the critical paths
		int number = randomNumber(0, routes.size() - 1);
		ArrayList<IOperation> selectedCriticalPath = routes.get(number).getRoute();

		int i = randomNumber(0, selectedCriticalPath.size() - 2);
		IOperation initialNode = selectedCriticalPath.get(i);
		
		IOperation finalNode = null;
		
		while (finalNode!=null){
			int totalJobs = currentStructure.getTotalJobs();
			int totalStations = currentStructure.getTotalStations();
			int total = totalJobs*totalStations;
			
			ArrayList<IOperation>operations = currentStructure.getOperations();
			
			int randomA = randomNumber(0, total - 1);
			IOperation temp = operations.get(randomA);
			if(!selectedCriticalPath.contains(temp)){
				finalNode = temp;
			}
			
		}
		OperationIndexVO initialOperationIndex = new OperationIndexVO(initialNode.getOperationIndex().getJobId(), initialNode.getOperationIndex().getStationId());
		OperationIndexVO finalOperationIndex = new OperationIndexVO(finalNode.getOperationIndex().getJobId(), finalNode.getOperationIndex().getStationId());
		
		return new PairVO(initialOperationIndex, finalOperationIndex);
	}

	@Override
	public ArrayList<PairVO> calculateNeighborhood(IStructure currentStructure, long size)
			throws Exception {
	
		int amount = 0;
		ArrayList<PairVO> neighborhood = new ArrayList<PairVO>();
		IStructure clone = currentStructure.cloneStructure();
		
		// Obtaining all the critical paths of the current solutions
		ArrayList<CriticalPath> routes = clone.getCriticalPaths();
		ArrayList<IOperation>operations = currentStructure.getOperations();
		for(int j =0; j < routes.size()  && amount < size;j++){
			ArrayList<IOperation> selectedCriticalPath = routes.get(j).getRoute();
			
			for (int i = 0; i < selectedCriticalPath.size()  && amount < size; i++) {
				IOperation firstOperation = selectedCriticalPath.get(i);
				
				for (int i2 = 0; i2 < operations.size() && amount < size; i2++) {
					IOperation secondOperation = operations.get(i2);
					if(!selectedCriticalPath.contains(secondOperation)){
						PairVO temp = new PairVO(firstOperation.getOperationIndex(), secondOperation.getOperationIndex());
						neighborhood.add(temp);
						amount++;
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
	// -----------------------------------------------
	// Utilities
	// -----------------------------------------------
	
	/**
	 * Returns a random number in the interval between the min and the max
	 * parameters
	 * 
	 * @param min
	 *            . Lower value of the interval
	 * @param max
	 * @return
	 */
	private static int randomNumber(int min, int max) {
		return (int) Math.round((Math.random() * (max - min)) + min);
	}
}
