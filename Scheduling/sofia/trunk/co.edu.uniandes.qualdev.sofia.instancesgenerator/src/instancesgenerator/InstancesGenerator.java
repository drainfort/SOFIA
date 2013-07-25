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
 * @author Juan Guillermo Amortegui
 */
public class InstancesGenerator {

	// ------------------------------------------------------
	// Constants
	// ------------------------------------------------------

	public final static Integer m = new Integer(2147483647);

	public final static Integer a = new Integer(16807);

	public final static Integer b = new Integer(127773);

	public final static Integer c = new Integer(2836);

	private Integer tempSeed;

	private int jobs = 4;
	
	private int machines = 4;

	// ------------------------------------------------------
	// Constructor
	// ------------------------------------------------------
	
	/**
	 * Constructor of the class
	 */
	public InstancesGenerator(int jobs, int machines) {
		this.jobs = jobs;
		this.machines = machines;
	}

	// ------------------------------------------------------
	// Methods
	// ------------------------------------------------------
	
	//TODO Poner esto a generar la instancia en archivos de texto que sigan el formato adecuado.
	/**
	 * Generates and prints in the console an instance according to the given size and the time and machine seeds
	 * @param timeSeed
	 * @param machineSeed
	 */
	public void generateAndPrintInstances(int timeSeed, int machineSeed){
		
		// PASO 1: Generación de los tiempos de proceso
		tempSeed = new Integer(timeSeed);
		Integer[][] matrixTimeSeed = generateTimeSeedMatrix(jobs);

		tempSeed = new Integer(machineSeed);
		Integer[][] matrixMachineSeed = generateMachineSeedMatrix(machines);	

		Integer[][] matrixFramework = generateInstance(matrixMachineSeed, matrixTimeSeed);
		System.out.println("T");
		printMatrix(matrixFramework);
		
		// PASO 2: Generación de los tiempos de viaje
		tempSeed = new Integer(timeSeed);
		Integer[][] matrixVisitTimeSeed = generateVisitTimeSeedMatrix(machines+1);
		
		System.out.println("TT");
		printMatrix(matrixVisitTimeSeed);
	}

	/**
	 * Generates the time seed matrix needed for computing the processing times of the instance.
	 * @param size Instance size
	 * @return timeSeedMatrix The time seed matrix
	 */
	private Integer[][] generateTimeSeedMatrix(int size) {
		Integer[][] cMatrix = new Integer[size][size];

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				cMatrix[j][i] = unif(8,14);
			}
		}
		return cMatrix;
	}

	/**
	 * Generates the machine seed matrix needed for computing the processing times of the instance.
	 * @param size Instance size
	 * @return machineSeedMatrix The machine seed matrix
	 */
	private Integer[][] generateMachineSeedMatrix(int size) {
		Integer[][] cMatrix = new Integer[size][size];

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				cMatrix[i][j] = i + 1;
			}
		}

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				swap(cMatrix, unif(j, size - 1), i, j);
			}
		}
		return cMatrix;
	}
	
	/**
	 * Generates the visit seed matrix needed for computing the travel times of the instance.
	 * @param size Instance size
	 * @return visitSeedMatrix The visit seed matrix
	 */
	private Integer[][] generateVisitTimeSeedMatrix(int size){
		Integer[][] cMatrix = new Integer[size][size];
		cMatrix[0][0]=0;
		for (int i = 1; i < size; i++) {
			int random=unif(2, 10);
				
			cMatrix[0][i]=random;
			cMatrix[i][0]=cMatrix[0][i];
			cMatrix[i][i]=0;
			}
		
		for (int i = 1; i < size-1; i++) {
			for (int j = i+1; j < size; j++) {
				int random=unif(2, 10);
				cMatrix[j][i] = random;
				cMatrix[i][j] = cMatrix[j][i];
			}
		}
		return cMatrix;
	}

	/**
	 * Generates and prints an instance according to the given size and the machine/time seed matrixes. 
	 * @param matrixMachineSeed The machine seed matrix
	 * @param matrixTimeSeed The time seed matrix
	 * @return
	 */
	private Integer[][] generateInstance(Integer[][] matrixMachineSeed,
			Integer[][] matrixTimeSeed) {

		Integer[][] matrix = new Integer[matrixMachineSeed.length][matrixMachineSeed.length];
		for (int i = 0; i < matrixMachineSeed.length; i++){
			for (int j = 0; j < matrixMachineSeed.length; j++) {
				matrix[i][j] = findProcessingTime(i,j+1, matrixMachineSeed, matrixTimeSeed);
			}
		}
		return matrix;
	}

	private Integer findProcessingTime(int job, int machine, Integer[][] matrixMachineSeed,
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

	private int unif(int lowerValue, int upperValue) {
		Integer k = tempSeed / b;
		tempSeed = (a * (tempSeed % b) - k * c);

		if (tempSeed < 0) {
			tempSeed = tempSeed + m;
		}

		Float value_0_1 = new Float((float)tempSeed.intValue() / (float) m.intValue());
		return  lowerValue + ((int) (value_0_1 * (upperValue - lowerValue + 1)));
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

	// ------------------------------------------------------
	// Main
	// ------------------------------------------------------
	
	public static void main(String[] args) {
		InstancesGenerator gen = new InstancesGenerator(4, 4);
		
		int timeSeed =  1166510396; int machineSeed = 164000672;
		gen.generateAndPrintInstances(timeSeed, machineSeed);
	}
}