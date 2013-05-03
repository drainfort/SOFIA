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
		
		int randomA = randomNumber(0, (totalJobs * totalStations) - 1);
		int randomB = randomNumber(0, (totalJobs * totalStations) - 1);
		int randomC = randomNumber(0, (totalJobs * totalStations) - 1);

		double randomX = Math.random();
		
		if(randomX > 0.5){
			return new PairVO(new OperationIndexVO(randomB, randomA), new OperationIndexVO(randomC, randomA));
		}
		else{
			return new PairVO(new OperationIndexVO(randomA, randomB), new OperationIndexVO(randomA, randomC));
		}
		
	}

	//TODO arreglar porque esto aun no es aleatorio.. pensar que hacemos
	@Override
	public ArrayList<PairVO> calculateNeighborhood(IStructure currentVector, int size)
			throws Exception {
		ArrayList<PairVO> pairs = new ArrayList<PairVO>();

		while(size>0)
		{
			int totalJobs = currentVector.getTotalJobs();
			int totalStations = currentVector.getTotalStations();
			
			int randomA = randomNumber(0, (totalJobs * totalStations) - 1);
			int randomB = randomNumber(0, (totalJobs * totalStations) - 1);
			PairVO pair = new PairVO(randomA, randomB);
			if(!pairs.contains(pair)){
				pairs.add(pair);
				size--;
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