package gammaCalculator.impl;

import gammaCalculator.IGammaCalculator;

import java.util.ArrayList;

import common.types.OperationIndexVO;
import common.types.PairVO;
import common.utils.MatrixUtils;

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
		int meanFlowTime = 0;
		for (int i = 0; i < C.length; i++) {
			meanFlowTime += C[i][vector.getTotalStations()];
		}		
		
		if(vector.getClass().equals(Vector.class)){
			((Vector) vector).decodeSolutionActiveSchedule();
			int C3[][] = ((Vector)vector).getCActiveSchedule();
			int meanFlowTime3 = 0;
			for (int i = 0; i < C3.length; i++) {
				meanFlowTime3 += C3[i][vector.getTotalStations()];
			}
			//System.out.println("Normal: "+meanFlowTime);
			//System.out.println("Active: "+meanFlowTime3);
			int C2[][] = ((Vector)vector).getCIntepretation();
			int meanFlowTime2 = 0;
			for (int i = 0; i < C2.length; i++) {
				meanFlowTime2 += C2[i][vector.getTotalStations()];
			}
			//System.out.println("Nodelay: "+meanFlowTime2);
			int solution = meanFlowTime;
			if(meanFlowTime2<solution)
				solution = meanFlowTime2;
			if(meanFlowTime3<solution)
				solution = meanFlowTime3;
			return solution;
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