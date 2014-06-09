package neighborCalculator.impl;

import java.util.ArrayList;

import neighborCalculator.INeighborCalculator;
import structure.IOperation;
import structure.IStructure;

import common.types.OperationIndexVO;
import common.types.PairVO;
import common.utils.RandomNumber;

public class RandomYu implements INeighborCalculator{

	// -----------------------------------------------
	// Methods
	// -----------------------------------------------
	
	@Override
	public PairVO calculateNeighbor(IStructure currentVector) throws Exception {
		
		
		double random = RandomNumber.getInstance().randomDouble();
		int totalJobs = currentVector.getTotalJobs();
		int totalStations = currentVector.getTotalStations();
		if(random<=0.5){
			int randomA = RandomNumber.getInstance().randomNumber(0, totalJobs);
			int randomB = RandomNumber.getInstance().randomNumber(0, totalStations);
			int randomC = RandomNumber.getInstance().randomNumber(0, totalStations);
			while(randomB==randomC){
				randomC = RandomNumber.getInstance().randomNumber(0, totalStations);
			}
			PairVO pair = new PairVO(new OperationIndexVO(0, randomA, randomB, 0), new OperationIndexVO(0, randomA, randomC, 0));
			return pair;
		}
		else{
			
			int randomA = RandomNumber.getInstance().randomNumber(0, totalStations);
			int randomB = RandomNumber.getInstance().randomNumber(0, totalJobs);
			int randomC = RandomNumber.getInstance().randomNumber(0, totalJobs);
			while(randomB==randomC){
				randomC = RandomNumber.getInstance().randomNumber(0, totalJobs);
			}
			PairVO pair = new PairVO(new OperationIndexVO(0, randomB, randomA), new OperationIndexVO(0, randomC,randomA));
			return pair;
		}
	}

	@Override
	public ArrayList<PairVO> calculateNeighborhood(IStructure currentVector, long size)
			throws Exception {
		ArrayList<PairVO> pairs = new ArrayList<PairVO>();
		int exit=0;
		while(size>0)
		{
			double random = RandomNumber.getInstance().randomDouble();
			int totalJobs = currentVector.getTotalJobs();
			int totalStations = currentVector.getTotalStations();
			PairVO pair = null;
			if(random<=0.5){
				int randomA = RandomNumber.getInstance().randomNumber(0, totalJobs);
				int randomB = RandomNumber.getInstance().randomNumber(0, totalStations);
				int randomC = RandomNumber.getInstance().randomNumber(0, totalStations);
				while(randomB==randomC){
					randomC = RandomNumber.getInstance().randomNumber(0, totalStations);
				}
				pair = new PairVO(new OperationIndexVO(0, randomA, randomB, 0), new OperationIndexVO(0, randomA, randomC, 0));
			}
			else{
				
				int randomA = RandomNumber.getInstance().randomNumber(0, totalStations);
				int randomB = RandomNumber.getInstance().randomNumber(0, totalJobs);
				int randomC = RandomNumber.getInstance().randomNumber(0, totalJobs);
				while(randomB==randomC){
					randomC = RandomNumber.getInstance().randomNumber(0, totalJobs);
				}
				pair = new PairVO(new OperationIndexVO(0, randomB, randomA), new OperationIndexVO(0, randomC,randomA));
			}
			if(!pairs.contains(pair)){
				pairs.add(pair);
				size--;
				exit=0;
			}else{
				exit++;
				if(exit>=50){
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
	

}