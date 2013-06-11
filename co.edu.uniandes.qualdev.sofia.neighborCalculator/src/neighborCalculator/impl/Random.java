package neighborCalculator.impl;

import java.util.ArrayList;

import neighborCalculator.INeighborCalculator;
import structure.IStructure;
import common.types.OperationIndexVO;
import common.types.PairVO;

public class Random implements INeighborCalculator{

	// -----------------------------------------------
	// Methods
	// -----------------------------------------------
	
	@Override
	public PairVO calculateNeighbor(IStructure currentVector) throws Exception {
		
		int totalJobs = currentVector.getTotalJobs();
		int totalStations = currentVector.getTotalStations();
		
		int randomA = randomNumber(0, totalStations - 1);
		int randomB = randomNumber(0, totalJobs - 1);
		int randomC = randomNumber(0, totalJobs - 1);
		int randomD = randomNumber(0, totalStations - 1);

		double randomX = Math.random();
		
		// Provides a pair with the same station
		if(randomX > 0.5){
			return new PairVO(new OperationIndexVO(randomB, randomA), new OperationIndexVO(randomC, randomA));
		}
		// Provides a pair with the same job
		else{
			return new PairVO(new OperationIndexVO(randomB, randomA), new OperationIndexVO(randomB, randomD));
		}
		
	}

	@Override
	public ArrayList<PairVO> calculateNeighborhood(IStructure currentVector, int size)
			throws Exception {
		ArrayList<PairVO> pairs = new ArrayList<PairVO>();

		while(size>0)
		{
			int totalJobs = currentVector.getTotalJobs();
			int totalStations = currentVector.getTotalStations();
			
			int randomA = randomNumber(0, totalStations - 1);
			int randomB = randomNumber(0, totalJobs - 1);
			int randomC = randomNumber(0, totalJobs - 1);
			int randomD = randomNumber(0, totalStations - 1);

			double randomX = Math.random();
			
			PairVO pair = null;
			// Provides a pair with the same station
			if(randomX > 0.5){
				pair = new PairVO(new OperationIndexVO(randomB, randomA), new OperationIndexVO(randomC, randomA));
			}
			// Provides a pair with the same job
			else{
				pair = new PairVO(new OperationIndexVO(randomB, randomA), new OperationIndexVO(randomB, randomD));
			}
			
			if(!pairs.contains(pair)){
				pairs.add(pair);
				size--;
			}
		}
		return pairs;
	}
	
	@Override
	public ArrayList<PairVO> calculateCompleteNeighborhood(
			IStructure currentStructure) throws Exception {
		ArrayList<PairVO> pairs = new ArrayList<PairVO>();
		int totalJobs = currentStructure.getTotalJobs();
		int totalStations = currentStructure.getTotalStations();
		for(int i=0; i<totalJobs;i++){
			for(int j=0; j<totalStations;j++){
				OperationIndexVO first =new OperationIndexVO(i, j);
				for(int z=0; z<totalJobs;z++){
					for(int jj=0; jj<totalStations;jj++){
						OperationIndexVO second =new OperationIndexVO(z, jj);
						PairVO pair = new PairVO(first, second);
						if(!pairs.contains(pair)){
							pairs.add(pair);
			            }
					}
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