package neighborCalculator.impl;

import java.util.ArrayList;

import neighborCalculator.INeighborCalculator;
import structure.IStructure;
import common.types.PairVO;

public class Complete implements INeighborCalculator{

	// -----------------------------------------------
	// Methods
	// -----------------------------------------------
	
	@Override
	public PairVO calculateNeighbor(IStructure currentVector) throws Exception {
		
		return null;
	}


	@Override
	public ArrayList<PairVO> calculateNeighborhood(IStructure currentVector, int size)
			throws Exception {
		ArrayList<PairVO> pairs = new ArrayList<PairVO>();
		int num = 0;

//		for(int i = start; i < currentVector.getVector().size() && num < end; i++){
//			for(int j = i + 1; j<currentVector.getVector().size(); j++ ){
//				Pair pair = new Pair(i, j);
//				pairs.add(pair);
//				num++;
//			}
//		}
		
		for(int i = 0; i < size; i++){
			for(int j = i + 1; j <= size; j++ ){
				PairVO pair = new PairVO(i, j);
				pairs.add(pair);
			}
		}
		
		return pairs;
	}
	
}