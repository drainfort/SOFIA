package gammaCalculator.impl;

import gammaCalculator.IGammaCalculator;
import common.types.PairVO;
import structure.IStructure;

public class CMaxCalculator implements IGammaCalculator {

	// -----------------------------------------
	// Methods
	// -----------------------------------------

	@Override
	public double calculateGamma(IStructure vector) throws Exception {
		vector.decodeSolution();
		double cmax = calculateGammaMatrix(vector.calculateCMatrix(0), vector.getTotalJobs(), vector.getTotalStations());
		return cmax;
	}
	
	private double calculateGammaMatrix(int[][] C, int jobs, int stations){
		double cmax = -1;
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
	public double updateGamma(IStructure initialVector, PairVO pair) throws Exception {
		System.out.println("Entro gamma update");
		int[][] C = initialVector.calculateCMatrix(0);

		double cmax = -1;
		for (int i = 0; i < initialVector.getTotalJobs(); i++) {
			if (C[i][initialVector.getTotalStations()] > cmax) {
				cmax = C[i][initialVector.getTotalStations()];
			}
		}

		return cmax;
	}
}
