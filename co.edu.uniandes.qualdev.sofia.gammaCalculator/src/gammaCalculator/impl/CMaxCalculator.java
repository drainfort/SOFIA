package gammaCalculator.impl;

import gammaCalculator.IGammaCalculator;
import common.types.PairVO;
import structure.IStructure;
import structure.impl.Vector;

public class CMaxCalculator implements IGammaCalculator {

	// -----------------------------------------
	// Methods
	// -----------------------------------------

	@Override
	public int calculateGamma(IStructure vector) throws Exception {
		int[][] C = vector.calculateCMatrix();
		
		int cmax = -1;
		for (int i = 0; i < vector.getTotalJobs(); i++) {
			if (C[i][vector.getTotalStations()] > cmax) {
				cmax = C[i][vector.getTotalStations()];
			}
		}
		
		if(vector.getClass().equals(Vector.class)){
			int[][] CVector = ((Vector)vector).getCinterpretation();
			int cmax2 = -1;
			for (int i = 0; i < vector.getTotalJobs(); i++) {
				if (CVector[i][vector.getTotalStations()] > cmax) {
					cmax2 = C[i][vector.getTotalStations()];
				}
			}
			if(cmax2>cmax){
				return cmax2;
			}
		}
		
				
		return cmax;
	}

	@Override
	public int updateGamma(IStructure initialVector, PairVO pair) throws Exception {
		System.out.println("Entro gamma update");
		int[][] C = initialVector.calculateCMatrix();

		int cmax = -1;
		for (int i = 0; i < initialVector.getTotalJobs(); i++) {
			if (C[i][initialVector.getTotalStations()] > cmax) {
				cmax = C[i][initialVector.getTotalStations()];
			}
		}

		return cmax;
	}
}
