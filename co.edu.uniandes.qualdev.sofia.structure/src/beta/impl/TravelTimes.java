package beta.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import beta.Beta;
import beta.TTBeta;


/**
 * Class that represents the beta for managing the travel times concept
 * 
 * @author Rubby Casallas
 * @author David Mendez-Acuna
 * 
 */
public class TravelTimes extends TTBeta {

	// --------------------------------------------------------
	// Attributes
	// --------------------------------------------------------

	private Float[][] TTMatrix;
	
	private ArrayList<String> informationFiles;

	// --------------------------------------------------------
	// Constructor
	// --------------------------------------------------------

	public TravelTimes() {

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

			TTMatrix = new Float[matrixWidth][matrixHeight];

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
					Float numFloat = new Float(currentNumber);
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
	public float getValue(int initialStationId, int finalStationId) {
		return TTMatrix[initialStationId + 1][finalStationId + 1];
	}
	
	@Override
	public Beta clone() {
		TravelTimes clone = new TravelTimes();
		Float[][] TTMatrixCloned = new Float[TTMatrix.length][TTMatrix[0].length];
		
		for (int i = 0; i < TTMatrix.length; i++) {
			for (int j = 0; j < TTMatrix[0].length; j++) {
				TTMatrixCloned[i][j] = new Float(TTMatrix[i][j].floatValue());
			}
			
		}
		clone.TTMatrix = TTMatrixCloned;
		
		ArrayList<String> clionedInformationFiles = new ArrayList<String>();
		for (int i = 0; i < informationFiles.size(); i++) {
			clionedInformationFiles.add(informationFiles.get(i));
		}
		clone.informationFiles = clionedInformationFiles;
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

	public Float[][] getTTMatrix() {
		return TTMatrix;
	}

	public void setTTMatrix(Float[][] tTMatrix) {
		TTMatrix = tTMatrix;
	}
	
	
	
}