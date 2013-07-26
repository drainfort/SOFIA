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

	// ------------------------------------------------------------------------------------------------------------------------------
	// Methods: Configuration invocation of the corresponding methods
	// ------------------------------------------------------------------------------------------------------------------------------
	
	//TODO Pedir configuracion por consola y poner esto a generar la instancia en archivos de texto que sigan el formato adecuado.
	/**
	 * Generates and prints in the console an instance according to the given size and the time and machine seeds
	 * @param timeSeed
	 * @param machineSeed
	 * @param parallelMachinesInterval 
	 */
	public void generateAndPrintInstance(int timeSeed, int machineSeed, Interval processingTimeInterval, Interval visitTimeInterval, Interval parallelMachinesInterval){
		
		// PASO 1: Generaci�n de los tiempos de proceso
		tempSeed = new Integer(timeSeed);
		Integer[][] matrixTimeSeed = generateTimeSeedMatrix(jobs, processingTimeInterval);

		tempSeed = new Integer(machineSeed);
		Integer[][] matrixMachineSeed = generateMachineSeedMatrix(machines);	

		Integer[][] matrixFramework = generateProcessingTimesMatrix(matrixMachineSeed, matrixTimeSeed);
		System.out.println("T");
		printMatrix(matrixFramework);
		
		// PASO 2: Generaci�n de los tiempos de viaje
		tempSeed = new Integer(timeSeed);
		Integer[][] matrixVisitTimeSeed = generateTravelTimesMatrix(machines+1, visitTimeInterval);
		
		System.out.println("TT");
		printMatrix(matrixVisitTimeSeed);
		
		// PASO 3: Generaci�n del vector de las m�quinas en paralelo
		tempSeed = new Integer(machineSeed);
		Integer[] parallelMachinesVector = generateParallelMachinesVector(machines, parallelMachinesInterval);
		
		System.out.println("M");
		printVector(parallelMachinesVector);
	}

	// ------------------------------------------------------------------------------------------------------------------------------
	// Methods: Generation of time and machine seed matrix
	// ------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Generates the time seed matrix needed for computing the processing times of the instance.
	 * @param size Instance size
	 * @return timeSeedMatrix The time seed matrix
	 */
	private Integer[][] generateTimeSeedMatrix(int size, Interval processingTimeInterval) {
		Integer[][] cMatrix = new Integer[size][size];

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				cMatrix[j][i] = unif(processingTimeInterval);
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
				swap(cMatrix, unif(new Interval(j, size - 1)), i, j);
			}
		}
		return cMatrix;
	}
	
	// ------------------------------------------------------------------------------------------------------------------------------
	// Methods: Generation of processing times
	// ------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Generates and prints an instance according to the given size and the machine/time seed matrixes. 
	 * @param matrixMachineSeed The machine seed matrix
	 * @param matrixTimeSeed The time seed matrix
	 * @return
	 */
	public Integer[][] generateProcessingTimesMatrix(Integer[][] matrixMachineSeed, Integer[][] matrixTimeSeed) {

		Integer[][] matrix = new Integer[matrixMachineSeed.length][matrixMachineSeed.length];
		for (int i = 0; i < matrixMachineSeed.length; i++){
			for (int j = 0; j < matrixMachineSeed.length; j++) {
				matrix[i][j] = findProcessingTime(i,j+1, matrixMachineSeed, matrixTimeSeed);
			}
		}
		return matrix;
	}

	/**
	 * Finds the processing time of the given operation (identified by the job-machine pair) within the time and machine seed matrixes
	 * @param job Job of the operation of interest
	 * @param machine Machine of the operation of interest
	 * @param matrixMachineSeed Machine seed matrix
	 * @param matrixTimeSeed Time seed matrix
	 * 
	 * @return processingTime Processing time of the operation of interest
	 */
	private Integer findProcessingTime(int job, int machine, Integer[][] matrixMachineSeed, Integer[][] matrixTimeSeed) {
		
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

	/**
	 * Swaps the values of two given positions in a given matrix 
	 * @param cMatrix Matrix where the swap should be peformed
	 * @param randomNumber
	 * @param i
	 * @param j
	 */
	private void swap(Integer[][] cMatrix, int randomNumber, int i, int j) {
		Integer temp = cMatrix[j][i];
		Integer temp0 = cMatrix[randomNumber][i];

		cMatrix[j][i] = temp0;
		cMatrix[randomNumber][i] = temp;

		temp = new Integer(0);
		temp0 = new Integer(0);
	}
	
	// ------------------------------------------------------------------------------------------------------------------------------
	// Methods: Generation of travel times
	// ------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Generates the visit seed matrix needed for computing the travel times of the instance.
	 * @param size Instance size
	 * @return visitSeedMatrix The visit seed matrix
	 */
	private Integer[][] generateTravelTimesMatrix(int size, Interval travelTimesInterval){
		Integer[][] cMatrix = new Integer[size][size];
		cMatrix[0][0]=0;
		for (int i = 1; i < size; i++) {
			int random=unif(travelTimesInterval);
				
			cMatrix[0][i]=random;
			cMatrix[i][0]=cMatrix[0][i];
			cMatrix[i][i]=0;
			}
		
		for (int i = 1; i < size-1; i++) {
			for (int j = i+1; j < size; j++) {
				int random=unif(travelTimesInterval);
				cMatrix[j][i] = random;
				cMatrix[i][j] = cMatrix[j][i];
			}
		}
		return cMatrix;
	}
	
	// ------------------------------------------------------------------------------------------------------------------------------
	// Methods: Generation of parallel machines vector
	// ------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Generates a vector with the amount of parallel machines for each workstation
	 * @param size The amount of workstations in the system
	 * @param parallelMachinesInterval The interval within the random numbers should be generated
	 * @return parallelsMachineVector The vector with the information of the amount of machines for each workstation
	 */
	public Integer[] generateParallelMachinesVector(int size, Interval parallelMachinesInterval){
		Integer[] parallelsMachineVector = new Integer[size];
		
		for (int i = 0; i < size; i++) {
			parallelsMachineVector[i] = unif(parallelMachinesInterval);
		}
		return parallelsMachineVector;
	}

	// ------------------------------------------------------------------------------------------------------------------------------
	// Methods: Generation of random numbers from a given seed
	// ------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Generates a random number using the current temp seed and within the given interval
	 * @param interval Interval for creating the random number
	 * @return random Random integer
	 */
	private int unif(Interval interval) {
		Integer k = tempSeed / b;
		tempSeed = (a * (tempSeed % b) - k * c);

		if (tempSeed < 0) {
			tempSeed = tempSeed + m;
		}

		Float value_0_1 = new Float((float)tempSeed.intValue() / (float) m.intValue());
		return  interval.getLowerBound() + ((int) (value_0_1 * (interval.getUpperBound() - interval.getLowerBound() + 1)));
	}

	// ------------------------------------------------------
	// Utilities
	// ------------------------------------------------------

	/**
	 * Prints in the console the matrix given in the parameter.
	 * @param matrixToPrint The matrix that is going to be printed in the console.
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

	/**
	 * Prints in the console the vector given in the parameter.
	 * @param vectorToPrint The matrix that is going to be printed in the console.
	 */
	private void printVector(Integer[] vectorToPrint) {
		System.out.print("|");
		
		for (Integer integer : vectorToPrint) {
			System.out.print(integer + "|");
		}
	}
	
	//TODO Integrar al c�digo
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
		
		// Parameters for each instance
		
		// -------------------------------
		// 4x4
		// -------------------------------
		
		int timeSeed =  1166510396; int machineSeed = 164000672;
//		int timeSeed =  1624514147; int machineSeed = 1076870026;
//		int timeSeed =  1116611914; int machineSeed = 1729673136;
//		int timeSeed =  410579806; int machineSeed = 1453014524;
//		int timeSeed =  1036100146; int machineSeed = 375655500;
		
//		int timeSeed =  597897640; int machineSeed = 322140729;
//		int timeSeed =  1268670769; int machineSeed = 556009645;
//		int timeSeed =  307928077; int machineSeed = 421384574;
//		int timeSeed =  667545295; int machineSeed = 485515899;
//		int timeSeed =  35780816; int machineSeed = 492238933;
		
		// -------------------------------
		// 5x5
		// -------------------------------
		
//		int timeSeed =  527556884; int machineSeed = 1343124817;
//		int timeSeed =  1046824493; int machineSeed = 1973406531;
//		int timeSeed =  1165033492; int machineSeed = 86711717;
//		int timeSeed =  476292817; int machineSeed = 24463110;
//		int timeSeed =  1181363416; int machineSeed = 606981348;
//		int timeSeed =  897739730; int machineSeed = 513119113;
//		int timeSeed =  577107303; int machineSeed = 2046387124;
//		int timeSeed =  1714191910; int machineSeed = 1928475945;
//		int timeSeed =  1813128617; int machineSeed = 2091141708;
//		int timeSeed =  808919936; int machineSeed = 183753764;
		
		// -------------------------------
		// 7x7
		// -------------------------------
				
//		int timeSeed =  1840686215; int machineSeed = 1827454623;
//		int timeSeed =  1026771938; int machineSeed = 1312166461;
//		int timeSeed =  609471574; int machineSeed = 670843185;
//		int timeSeed =  1022295947; int machineSeed = 398226875;
//		int timeSeed =  1513073047; int machineSeed = 1250759651;
//		int timeSeed =  1612211197; int machineSeed = 95606345;
//		int timeSeed =  435024109; int machineSeed = 1118234860;
//		int timeSeed =  1760865440; int machineSeed = 1099909092;
//		int timeSeed =  122574075; int machineSeed = 10979313;
//		int timeSeed =  248031774; int machineSeed = 1685251301;
		
		// -------------------------------
		// 10x10
		// -------------------------------
				
//		int timeSeed =  ; int machineSeed = ;
//		int timeSeed =  ; int machineSeed = ;
//		int timeSeed =  ; int machineSeed = ;
//		int timeSeed =  ; int machineSeed = ;
//		int timeSeed =  ; int machineSeed = ;
//		int timeSeed =  ; int machineSeed = ;
//		int timeSeed =  ; int machineSeed = ;
//		int timeSeed =  ; int machineSeed = ;
//		int timeSeed =  ; int machineSeed = ;
//		int timeSeed =  ; int machineSeed = ;
		
		// -------------------------------
		// 15x15
		// -------------------------------
				
//		int timeSeed =  ; int machineSeed = ;
//		int timeSeed =  ; int machineSeed = ;
//		int timeSeed =  ; int machineSeed = ;
//		int timeSeed =  ; int machineSeed = ;
//		int timeSeed =  ; int machineSeed = ;
//		int timeSeed =  ; int machineSeed = ;
//		int timeSeed =  ; int machineSeed = ;
//		int timeSeed =  ; int machineSeed = ;
//		int timeSeed =  ; int machineSeed = ;
//		int timeSeed =  ; int machineSeed = ;
		
		// -------------------------------
		// 20x20
		// -------------------------------
				
//		int timeSeed =  ; int machineSeed = ;
//		int timeSeed =  ; int machineSeed = ;
//		int timeSeed =  ; int machineSeed = ;
//		int timeSeed =  ; int machineSeed = ;
//		int timeSeed =  ; int machineSeed = ;
//		int timeSeed =  ; int machineSeed = ;
//		int timeSeed =  ; int machineSeed = ;
//		int timeSeed =  ; int machineSeed = ;
//		int timeSeed =  ; int machineSeed = ;
//		int timeSeed =  ; int machineSeed = ;
		
		// Parameters for all the instances
		Interval processingTimeInterval = new Interval(8, 14);
		Interval travelTimeInterval = new Interval(2, 10);
		
		// TODO Este intervalo cambia dependiendo del tama�o de la instancia: 
		// 4x4 a 10x10 -> [1,2];  y de tama�o 15x15 a 20x20 -> [1,3].  
		Interval parallelMachinesInterval = new Interval(1, 2);
		//Interval parallelMachinesInterval = new Interval(1, 3);
		
		// Generation
		gen.generateAndPrintInstance(timeSeed, machineSeed, processingTimeInterval, travelTimeInterval, parallelMachinesInterval);
	}
}