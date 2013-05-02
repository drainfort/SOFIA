package common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MatrixUtils {

	/**
	 * Loads an integer matrix from a file given in the parameter
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static Integer[][] loadMatrix(String filePath) throws IOException {
		Integer[][] answer = null;
		BufferedReader reader = null;
		try{
			File file = new File(filePath);
			reader = new BufferedReader(new FileReader(file));

			String matrixHeightString = reader.readLine();
			Integer matrixHeight = Integer.parseInt(matrixHeightString);

			String matrixWidthString = reader.readLine();
			Integer matrixWidth = Integer.parseInt(matrixWidthString);

			answer = new Integer[matrixHeight][matrixWidth];

			for (int i = 0; i < matrixHeight; i++) {
				String currentLine = reader.readLine();
				for (int j = 0; j < matrixWidth; j++) {
					try {
						int numerito = currentLine.substring(1,
								currentLine.length() - 1).indexOf("|") + 1;
						if (numerito == 0)
							numerito = currentLine.length() - 1;
						String currentNumberString = currentLine.substring(1,
								numerito);
						Integer currentNumber = 0;
						if(!currentNumberString.isEmpty()){
							if (!currentNumberString.equals(" ") && !currentNumberString.equals("")){ 
								currentNumber = Integer.parseInt(currentNumberString);
							}
						}
						answer[i][j] = currentNumber;
						currentLine = currentLine.substring(
								currentLine.substring(1, currentLine.length() - 1)
										.indexOf("|") + 1, currentLine.length());
					} catch (NumberFormatException e) {
						throw e;
					}
				}
			}
		}catch(IOException e){
			e.printStackTrace();
			throw e;
		}finally{
			reader.close();
		}
		

		return answer;
	}

	/**
	 * Prints in the console the matrix given in the parameter.
	 * 
	 * @param matrixToPrint
	 *            . The matrix that is gonna be printed in the console.
	 */
	public static void printMatrix(Integer[][] matrixToPrint) {
		String matrix = "";
		for (int i = 0; i < matrixToPrint.length; i++) {
			Integer[] integers = matrixToPrint[i];
			for (int j = 0; j < integers.length; j++) {
				if (integers[j] != null)
					matrix += "|" + integers[j];
				else
					matrix += "| ";
			}
			matrix += "|\n";
		}
		System.out.println(matrix);
	}
	/**
	 * Prints in the console the matrix given in the parameter.
	 * 
	 * @param matrixToPrint
	 *            . The matrix that is gonna be printed in the console.
	 */
	public static void printMatrix(int[][][] matrixToPrint) {
		String matrix = "";
		for (int i = 0; i < matrixToPrint.length; i++) {
			int[] integers = matrixToPrint[i][0];
			for (int j = 0; j < integers.length; j++) {
					matrix += "|" + integers[j];
			}
			matrix += "|\n";
		}
		System.out.println(matrix);
	}
	
	/**
	 * Prints in the console the matrix given in the parameter.
	 * 
	 * @param matrixToPrint
	 *            . The matrix that is gonna be printed in the console.
	 */
	public static void printMatrix(int[][] matrixToPrint) {
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
	
	public static void printMatrix(float[][] matrixToPrint) {
		String matrix = "";
		for (int i = 0; i < matrixToPrint.length; i++) {
			float[] integers = matrixToPrint[i];
			for (int j = 0; j < integers.length; j++) {
					matrix += "|" + integers[j];
			}
			matrix += "|\n";
		}
		System.out.println(matrix);
	}
	
	public static void printMatrix(Float[][] matrixToPrint) {
		String matrix = "";
		for (int i = 0; i < matrixToPrint.length; i++) {
			Float[] integers = matrixToPrint[i];
			for (int j = 0; j < integers.length; j++) {
					matrix += "|" + integers[j];
			}
			matrix += "|\n";
		}
		System.out.println(matrix);
	}
}
