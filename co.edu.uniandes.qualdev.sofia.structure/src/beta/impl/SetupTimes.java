package beta.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import beta.Beta;
import beta.SetupBeta;


/**
 * Class that represents the beta for managing the travel times concept
 * 
 * @author Rubby Casallas
 * @author David Mendez-Acuna
 * 
 */
public class SetupTimes extends SetupBeta {

	// --------------------------------------------------------
	// Attributes
	// --------------------------------------------------------

	private Float[][] SMatrix;
	
	private ArrayList<String> informationFiles;

	// --------------------------------------------------------
	// Constructor
	// --------------------------------------------------------

	public SetupTimes() {

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

			SMatrix = new Float[matrixWidth][matrixHeight];

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
					if (!currentNumberString.equals(" ")) {
						currentNumber = Float.parseFloat(currentNumberString);
					}
					Float numFloat = new Float(currentNumber);
					SMatrix[i][j] = numFloat;

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
	public ArrayList<String> getInformationFiles() {
		return informationFiles;
	}

	@Override
	public void setInformationFiles(ArrayList<String> informationFiles) {
		this.informationFiles = informationFiles;
		
	}

	@Override
	public Beta clone() {
		SetupTimes clone = new SetupTimes();
		Float[][] SMatrixCloned = new Float[SMatrix.length][SMatrix[0].length];
		
		for (int i = 0; i < SMatrix.length; i++) {
			for (int j = 0; j < SMatrix[0].length; j++) {
				SMatrixCloned[i][j] = new Float(SMatrix[i][j].floatValue());
			}
			
		}
		clone.SMatrix = SMatrixCloned;
		
		ArrayList<String> clionedInformationFiles = new ArrayList<String>();
		for (int i = 0; i < informationFiles.size(); i++) {
			clionedInformationFiles.add(informationFiles.get(i));
		}
		clone.informationFiles = clionedInformationFiles;
		return clone;
	}

	@Override
	public float getValue(int job, int station) {
		return SMatrix[job][station];
	}
}