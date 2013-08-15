package neighborCalculator.impl;

import java.util.ArrayList;

import org.omg.PortableInterceptor.InterceptorOperations;

import neighborCalculator.INeighborCalculator;
import structure.IOperation;
import structure.IStructure;
import common.types.OperationIndexVO;
import common.types.PairVO;

public class N1_Random implements INeighborCalculator{

	// -----------------------------------------------
	// Methods
	// -----------------------------------------------
	
	@Override
	public PairVO calculateNeighbor(IStructure currentVector) throws Exception {
		
		int totalJobs = currentVector.getTotalJobs();
		int totalStations = currentVector.getTotalStations();
		int total = totalJobs*totalStations;
		
		ArrayList<IOperation>operations = currentVector.getOperations();
		
		int randomA = randomNumber(0, total - 1);
		int randomB = randomNumber(0, total - 1);
		
		OperationIndexVO start = operations.get(randomA).getOperationIndex();
		OperationIndexVO end = operations.get(randomB).getOperationIndex();
		
		PairVO pair = new PairVO(new OperationIndexVO(0, start.getJobId(),start.getStationId(), start.getMachineId()), new OperationIndexVO(0, end.getJobId(),end.getStationId(), end.getMachineId()));
		
		return pair;
		
	}

	@Override
	public ArrayList<PairVO> calculateNeighborhood(IStructure currentVector, long size)
			throws Exception {
		ArrayList<PairVO> pairs = new ArrayList<PairVO>();
		int exit=0;
		while(size>0)
		{
			int totalJobs = currentVector.getTotalJobs();
			int totalStations = currentVector.getTotalStations();
			int total = totalJobs*totalStations;
			
			ArrayList<IOperation>operations = currentVector.getOperations();
			
			int randomA = randomNumber(0, total - 1);
			int randomB = randomNumber(0, total - 1);
			
			OperationIndexVO start = operations.get(randomA).getOperationIndex();
			OperationIndexVO end = operations.get(randomB).getOperationIndex();
			
			PairVO pair = new PairVO(new OperationIndexVO(0, start.getJobId(),start.getStationId(), start.getMachineId()), new OperationIndexVO(0, end.getJobId(),end.getStationId(), end.getMachineId()));		
			if(!pairs.contains(pair)){
				pairs.add(pair);
				size--;
				exit=0;
			}else{
				exit++;
				if(exit>=100){
					return pairs;
				}
			}
			
		}
		return pairs;
	}
	
	@Override
	public ArrayList<PairVO> calculateCompleteNeighborhood(IStructure currentStructure) throws Exception {
		ArrayList<PairVO> pairs = new ArrayList<PairVO>();
		
		ArrayList<IOperation> operations = currentStructure.getOperations();
		for (int i = 0; i < operations.size(); i++) {
			IOperation fistOperation = operations.get(i);
			
			for (int j = 0; j < operations.size(); j++) {
				IOperation secondOperation = operations.get(j);
				
				if(i!=j){
					PairVO pair = new PairVO(fistOperation.getOperationIndex(), secondOperation.getOperationIndex());
					pairs.add(pair);
				}
			}
		}
		
		return pairs;
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