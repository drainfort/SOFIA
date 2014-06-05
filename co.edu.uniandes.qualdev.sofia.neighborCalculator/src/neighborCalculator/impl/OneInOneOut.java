package neighborCalculator.impl;

import java.util.ArrayList;

import neighborCalculator.INeighborCalculator;
import structure.IOperation;
import structure.IStructure;
import structure.impl.CriticalPath;

import common.types.OperationIndexVO;
import common.types.PairVO;
import common.utils.RandomNumber;

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
		int number = RandomNumber.getInstance().randomNumber(0, routes.size() - 1);
		ArrayList<IOperation> selectedCriticalPath = routes.get(number).getRoute();

		int i = RandomNumber.getInstance().randomNumber(0, selectedCriticalPath.size() - 2);
		IOperation initialNode = selectedCriticalPath.get(i);
		
		IOperation finalNode = null;
		
		while (finalNode==null){
			int totalJobs = currentStructure.getTotalJobs();
			int totalStations = currentStructure.getTotalStations();
			int total = totalJobs*totalStations;
			
			ArrayList<IOperation>operations = currentStructure.getOperations();
			
			int randomA = RandomNumber.getInstance().randomNumber(0, total - 1);
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
		int exit = 0;
		ArrayList<PairVO> neighborhood = new ArrayList<PairVO>();
		IStructure clone = currentStructure.cloneStructure();
		
		// Obtaining all the critical paths of the current solutions
		ArrayList<CriticalPath> routes = clone.getCriticalPaths();
		ArrayList<IOperation>operations = currentStructure.getOperations();
		
		while(amount < size){
			int number = RandomNumber.getInstance().randomNumber(0, routes.size() - 1);
			ArrayList<IOperation> selectedCriticalPath = routes.get(number).getRoute();

			int i = RandomNumber.getInstance().randomNumber(0, selectedCriticalPath.size() - 2);
			IOperation initialNode = selectedCriticalPath.get(i);
			
			IOperation finalNode = null;
			
			while (finalNode==null){
				int totalJobs = currentStructure.getTotalJobs();
				int totalStations = currentStructure.getTotalStations();
				int total = totalJobs*totalStations;
				
				
				int randomA = RandomNumber.getInstance().randomNumber(0, total - 1);
				IOperation temp = operations.get(randomA);
				if(!selectedCriticalPath.contains(temp)){
					finalNode = temp;
				}
				
			}
			OperationIndexVO initialOperationIndex = new OperationIndexVO(initialNode.getOperationIndex().getJobId(), initialNode.getOperationIndex().getStationId());
			OperationIndexVO finalOperationIndex = new OperationIndexVO(finalNode.getOperationIndex().getJobId(), finalNode.getOperationIndex().getStationId());
			PairVO temp = new PairVO(initialOperationIndex,finalOperationIndex);
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
