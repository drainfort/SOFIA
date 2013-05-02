package beta.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import beta.Beta;
import beta.TearDownBeta;

public class TearDownTravelTime extends TearDownBeta{

	// --------------------------------------------------------
	// Attributes
	// --------------------------------------------------------

	private int[][] TTMatrix;
	
	private ArrayList<String> informationFiles;

	// --------------------------------------------------------
	// Constructor
	// --------------------------------------------------------
	
	public TearDownTravelTime() {

	}

	// --------------------------------------------------------
	// Methods
	// --------------------------------------------------------
	
	@Override
	public void loadBeta(ArrayList<String> informationFiles) throws IOException {
		BufferedReader reader = null;
		try {
			File file = new File(informationFiles.get(0));
			reader = new BufferedReader(new FileReader(file));

			String matrixHeightString = reader.readLine();
			Integer matrixHeight = Integer.parseInt(matrixHeightString);

			String matrixWidthString = reader.readLine();
			Integer matrixWidth = Integer.parseInt(matrixWidthString);

			TTMatrix = new int[matrixWidth][matrixHeight];

			for (int i = 0; i < matrixHeight; i++) {
				String currentLine = reader.readLine();
				for (int j = 0; j < matrixWidth; j++) {

					int numerito = currentLine.substring(1,
							currentLine.length() - 1).indexOf("|") + 1;
					if (numerito == 0)
						numerito = currentLine.length() - 1;
					String currentNumberString = currentLine.substring(1,
							numerito);
					float currentNumber = 0;
					if(!currentNumberString.isEmpty()){
						if (!currentNumberString.equals(" ")&&!currentNumberString.equals("")) 
							currentNumber = Float.parseFloat(currentNumberString);
					}
					int numFloat = (int)currentNumber;
					TTMatrix[i][j] = numFloat;

					currentLine = currentLine.substring(
							currentLine.substring(1, currentLine.length() - 1)
									.indexOf("|") + 1, currentLine.length());
				}
			}
		} catch (IOException e) {
			throw e;
		} finally {
			if(reader!=null)reader.close();
		}
	}

	@Override
	public Beta clone() {
		TearDownTravelTime clone = new TearDownTravelTime();
		int[][] TTMatrixCloned = new int[TTMatrix.length][TTMatrix[0].length];
		
		for (int i = 0; i < TTMatrix.length; i++) {
			for (int j = 0; j < TTMatrix[0].length; j++) {
				TTMatrixCloned[i][j] = TTMatrix[i][j];
			}
			
		}
		clone.TTMatrix = TTMatrixCloned;
		
		ArrayList<String> clonedInformationFiles = new ArrayList<String>();
		for (int i = 0; i < informationFiles.size(); i++) {
			clonedInformationFiles.add(informationFiles.get(i));
		}
		clone.informationFiles = clonedInformationFiles;
		return clone;
	}

	@Override
	public ArrayList<String> getInformationFiles() {
		return informationFiles;
	}

	@Override
	public void setInformationFiles(ArrayList<String> informationFiles) {
		this.informationFiles = informationFiles;
	}

	@Override
	public int[][] applyBeta(int[][] C) {
		
		int[][] newC = new int[C.length][C[0].length];
		int[] maxs = new int[C.length];
		int[] maxsPos = new int[C.length];
		
		for (int i = 0; i < C.length; i++) {
			int max = 0;
			int maxPos = 0;
			for (int j = 0; j < C[0].length - 1; j++) {
					if(C[i][j] >= max){
						max = C[i][j];
						maxPos = j + 1;
				}
				
			}
			maxs[i] = max;
			maxsPos[i] = maxPos;
		}
		
		for (int i = 0; i < C.length; i++) {
			for (int j = 0; j < C[0].length; j++) {
					newC[i][j] = C[i][j];
			}
			int travel = TTMatrix[(int)maxsPos[i]][0];
			newC[i][C[0].length - 1] = maxs[i] + travel;
		}
		
		return newC;
	}
}
