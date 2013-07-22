package gammaCalculator.impl;

import gammaCalculator.IGammaCalculator;

import java.util.ArrayList;

import common.types.OperationIndexVO;
import common.types.PairVO;

import structure.IStructure;
import structure.impl.Vector;

public class MeanFlowTimeCalculator implements IGammaCalculator {

	// -----------------------------------------
	// Attributes 
	// -----------------------------------------
	
	private int[][] C;
	
	// -----------------------------------------
	// Methods 
	// -----------------------------------------
	
	@Override
	public int calculateGamma(IStructure vector) throws Exception {
		C = vector.calculateCMatrix();

		ArrayList<OperationIndexVO> maxs = new ArrayList<OperationIndexVO>();
		for (int i = 0; i < C.length; i++) {
			int currentCMax = -1;
			OperationIndexVO maxOperationIndex = new OperationIndexVO(0, i, -1, -1);
			for (int j = 0; j < C[0].length; j++) {
					if (currentCMax < C[i][j]){
						currentCMax = C[i][j];
						maxOperationIndex.setStationId(j);
				}
			}
			maxs.add(maxOperationIndex);
		}
		
		int meanFlowTime = 0;
		for (OperationIndexVO max : maxs) {
			meanFlowTime += C[max.getJobId()][max.getStationId()];
		}
		
		
		if(vector.getClass().equals(Vector.class)){
			
			int C2[][] = ((Vector)vector).getCIntepretation();

			ArrayList<OperationIndexVO> maxs2 = new ArrayList<OperationIndexVO>();
			for (int i = 0; i < C2.length; i++) {
				int currentCMax = -1;
				OperationIndexVO maxOperationIndex = new OperationIndexVO(0, i, -1, -1);
				for (int j = 0; j < C2[0].length; j++) {
						if (currentCMax < C2[i][j]){
							currentCMax = C2[i][j];
							maxOperationIndex.setStationId(j);
					}
				}
				maxs2.add(maxOperationIndex);
			}
			
			int meanFlowTime2 = 0;
			for (OperationIndexVO max : maxs2) {
				meanFlowTime2 += C[max.getJobId()][max.getStationId()];
			}
			if(meanFlowTime2<meanFlowTime)
				return meanFlowTime2;
		}
		
		
		return meanFlowTime;
	}

	@Override
	public int updateGamma(IStructure initialVector, PairVO pair) throws Exception {
		C = initialVector.updateCMatrix(pair);
		
		ArrayList<OperationIndexVO> maxs = new ArrayList<OperationIndexVO>();
		for (int i = 0; i < C.length; i++) {
			int currentCMax = -1;
			OperationIndexVO maxOperationIndex = new OperationIndexVO(i, -1, -1);
			for (int j = 0; j < C[0].length; j++) {
					if (currentCMax < C[i][j]){
						currentCMax = C[i][j];
						maxOperationIndex.setStationId(j);
				}
			}
			maxs.add(maxOperationIndex);
		}
		
		int meanFlowTime = 0;
		for (OperationIndexVO max : maxs) {
			meanFlowTime += C[max.getJobId()][max.getStationId()];
		}
		
		return meanFlowTime;
	}
}