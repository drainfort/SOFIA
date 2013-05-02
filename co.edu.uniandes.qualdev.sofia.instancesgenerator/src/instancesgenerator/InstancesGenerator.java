package instancesgenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Class that is able to generate an instance according to a time seed and a machine seed
 * 
 * @author Lindsay Alvarez
 * @author David Mendez-Acuna
 * @author Oriana Cendales
 */
public class InstancesGenerator {

	// ------------------------------------------------------
	// Constants
	// ------------------------------------------------------

	public final static Double m = new Double(2147483647);

	public final static Integer a = new Integer(16807);

	public final static Integer b = new Integer(127773);

	public final static Integer c = new Integer(2836);

	private Integer tempSeed;

	// ------------------------------------------------------
	// Constructor
	// ------------------------------------------------------
	
	/**
	 * Constructor of the class
	 */
	public InstancesGenerator() {
		int jobs = 4;
		int machines = 4;

		int timeSeed =  1166510396; int machineSeed = 164000672;

		
		//Generacion de los tiempos de proceso
		
		tempSeed = new Integer(timeSeed);// esta es la semilla TimeSeed del
											// OrLibrary
		Integer[][] matrixTimeSeed = generateTimeSeedMatrix(jobs);// este es el
																// tamaño de la
																// instancia

		tempSeed = new Integer(machineSeed);// esta es la semilla MachineSeed del
											// OrLibrary
		
		Integer[][] matrixMachineSeed = generateMachineSeedMatrix(machines);// este es
																		// el
																		// tamaño
																		// de la
																		// instancia
		Integer[][] matrixFramework = generadorParaFramework(matrixMachineSeed,
				matrixTimeSeed);
		System.out.println("T");
		printMatrix(matrixFramework);
		
		
		//Generacion de los tiempos de viaje
		tempSeed = new Integer(timeSeed);
		Integer[][] matrixVisitTimeSeed = generateVisitTimeSeedMatrix(machines+1);
		System.out.println("TT");
		printMatrix(matrixVisitTimeSeed);
	}

	// ------------------------------------------------------
	// Methods
	// ------------------------------------------------------

	private Integer[][] generateTimeSeedMatrix(int size) {
		Integer[][] cMatrix = new Integer[size][size];

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				cMatrix[j][i] = getRandomNumber(8, 14);
			}
		}
		return cMatrix;
	}

	private Integer[][] generateMachineSeedMatrix(int size) {
		Integer[][] cMatrix = new Integer[size][size];

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				cMatrix[i][j] = i + 1;
			}
		}

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				swap(cMatrix, getRandomNumber(j, size - 1), i, j);
			}
		}
		return cMatrix;
	}
	
	private Integer[][] generateVisitTimeSeedMatrix(int size){
		Integer[][] cMatrix = new Integer[size][size];
		cMatrix[0][0]=0;
		for (int i = 1; i < size; i++) {
			int random=getRandomNumber(2, 10);
				
			cMatrix[0][i]=random;
			cMatrix[i][0]=cMatrix[0][i];
			cMatrix[i][i]=0;
			}
		
		for (int i = 1; i < size-1; i++) {
			for (int j = i+1; j < size; j++) {
				int random=getRandomNumber(2, 10);
				cMatrix[j][i] = random;
				cMatrix[i][j] = cMatrix[j][i];
			}
		}
		return cMatrix;
	}

	// método para sacar los datos de tiempos de proceso en el formato de
	// express
	public String[][] generadorParaExpress(Integer[][] matrixMachineSeed,
			Integer[][] matrixTimeSeed) {
		int fila = (matrixMachineSeed.length) * (matrixMachineSeed.length);
		
		String dato;

		String[][] matrixExpres2 = new String[matrixMachineSeed.length][matrixMachineSeed.length];

		for (int j = 0; j < matrixMachineSeed.length; j++) {
			for (int K = 0; K < matrixMachineSeed.length; K++) {
				dato = "(" + (j + 1) + "," + matrixMachineSeed[K][j] + ")"
						+ matrixTimeSeed[K][j];
				matrixExpres2[K][j] = dato;
			}

		}

		int x = -1;
		String[][] matrixExpres = new String[fila][1];
		for (int j = 0; j < matrixExpres2.length; j++) {
			for (int K = 0; K < matrixExpres2.length; K++) {
				x = x + 1;
				matrixExpres[x][0] = matrixExpres2[K][j];
			}
		}

		return matrixExpres;

	}

	// método para sacar los datos de tiempos de proceso en el formato de
	// express
	private Integer[][] generadorParaFramework(Integer[][] matrixMachineSeed,
			Integer[][] matrixTimeSeed) {

		Integer[][] matrix = new Integer[matrixMachineSeed.length][matrixMachineSeed.length];

		for (int i = 0; i < matrixMachineSeed.length; i++){
			for (int j = 0; j < matrixMachineSeed.length; j++) {
				matrix[i][j] = buscarTiempoProceso(i,j+1, matrixMachineSeed, matrixTimeSeed);
			}

		}

		return matrix;

	}

	private Integer buscarTiempoProceso(int job, int machine, Integer[][] matrixMachineSeed,
			Integer[][] matrixTimeSeed) {
		
		int posMachine = -1;
		for (int i = 0; i < matrixTimeSeed.length; i++) {
			if(matrixMachineSeed[i][job] == machine){
				posMachine = i;
			}
		}
		
		if(posMachine==-1)
			return 0;
		else
			return matrixTimeSeed[posMachine][job];
	}

	private void swap(Integer[][] cMatrix, int randomNumber, int i, int j) {
		Integer temp = cMatrix[j][i];
		Integer temp0 = cMatrix[randomNumber][i];

		cMatrix[j][i] = temp0;
		cMatrix[randomNumber][i] = temp;

		temp = new Integer(0);
		temp0 = new Integer(0);
	}

	private int getRandomNumber(int lowerValue, int upperValue) {
		int k = tempSeed / b;

		tempSeed = (a * (tempSeed % b) - k * c);

		if (tempSeed < 0) {
			tempSeed = (int) (tempSeed + m);
		}

		Double value_0_1 = new Double(tempSeed / m);
		return (int) (lowerValue + (value_0_1 * (upperValue - lowerValue + 1)));
	}


	
	// ------------------------------------------------------
	// Utilities
	// ------------------------------------------------------

	/**
	 * Print in the console the matrix given in the parameter.
	 * 
	 * @param matrixToPrint
	 *            . The matrix that is gonna be printed in the console.
	 */
	private String printMatrix(Integer[][] matrixToPrint) {
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
		return matrix;
	}

	// para imprimir la matriz con tiempos de proceso

	public String printMatrixString(String[][] matrixToPrint) {
		String matrix = "t:[";
		for (int i = 0; i < matrixToPrint.length; i++) {
			String[] integers = matrixToPrint[i];
			for (int j = 0; j < integers.length; j++) {
				if (integers[j] != null)
					matrix += "" + integers[j];
				else
					matrix += " ";
			}
			matrix += "\f\n";
		}
		matrix += "]";
		System.out.println(matrix);
		return matrix;
	}

	public void createFile(String text, String fileName) throws IOException {
		File file = new File(fileName);
		if (!file.exists())
			file.createNewFile();

		PrintWriter out = new PrintWriter(new FileOutputStream(file));
		out.println(text);
		out.close();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		InstancesGenerator gen = new InstancesGenerator();

	}
}