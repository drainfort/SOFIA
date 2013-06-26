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
		//int[][] C = vector.calculateCMatrix();
		//printMatrix(C);
		int cmax = calculateGammaMatrix(vector.calculateCMatrix(), vector.getTotalJobs(), vector.getTotalStations());
		
		if(vector.getClass().equals(Vector.class)){
			int[][] CVector = ((Vector)vector).getCIntepretation();
			//printMatrix(CVector);
			
			int cmax2 = calculateGammaMatrix(CVector, vector.getTotalJobs(), vector.getTotalStations());;
			if(cmax2<cmax){
			 //return cmax2;
			}
		}
			
		return cmax;
	}
	
	public int calculateGammaMatrix(int[][] C, int jobs, int stations){
		int cmax = -1;
		for (int i = 0; i < jobs; i++) {
			if (C[i][stations] > cmax) {
				cmax = C[i][stations];
			}
		}
		return cmax;
	}
	
	public void printMatrix(int[][] matrixToPrint) {
		String matrix = "";
		for (int i = 0; i < matrixToPrint.length; i++) {
			int[] integers = matrixToPrint[i];
			for (int j = 0; j < integers.length; j++) {
					matrix += "|" + integers[j];
			}
			matrix += "|\n";
		}
		System.out.println(matrix);
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
