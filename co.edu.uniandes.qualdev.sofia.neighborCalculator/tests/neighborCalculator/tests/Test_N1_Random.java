package neighborCalculator.tests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import neighborCalculator.impl.N1_Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import common.types.BetaVO;
import common.types.OperationIndexVO;
import common.types.PairVO;
import structure.factory.impl.VectorFactory;
import structure.impl.Graph;
import structure.impl.Vector;
import structure.impl.decoding.SequencialDecoding;

/**
 * Test case for the neighborhood calculator algorithm: N1_Random
 * 
 * @author David Mendez-Acuna
 */
public class Test_N1_Random {

	// -----------------------------------------------
	// Attributes
	// -----------------------------------------------
	
	private N1_Random neighborCalulator;
	
	private Graph graphScenario1;
	
	private Vector vectorScenario1;
	
	// -----------------------------------------------
	// Scenarios setup
	// -----------------------------------------------
	
	@Before
	public void setUpScenario1() throws Exception {
		neighborCalulator = new N1_Random();
		
		// Loading the scenario 1 for the graph
		graphScenario1 = new Graph(2,2);
		
		OperationIndexVO[][] problemGraph = new OperationIndexVO[2][2]; 
		problemGraph[0][0] = new OperationIndexVO(10, 0, 0);
		problemGraph[0][1] = new OperationIndexVO(20, 0, 1);
		problemGraph[1][0] = new OperationIndexVO(5, 1, 0);
		problemGraph[1][1] = new OperationIndexVO(5, 1, 1);
		
		graphScenario1.setProblem(problemGraph);
		graphScenario1.scheduleOperation(problemGraph[0][0]);
		graphScenario1.scheduleOperation(problemGraph[1][1]);
		graphScenario1.scheduleOperation(problemGraph[1][0]);
		graphScenario1.scheduleOperation(problemGraph[0][1]);
		
		// Loading the scenario 1 for the vector
		vectorScenario1 = new Vector(2,2, new SequencialDecoding());
		
		OperationIndexVO[][] problemVector = new OperationIndexVO[2][2]; 
		problemVector[0][0] = new OperationIndexVO(10, 0, 0);
		problemVector[0][1] = new OperationIndexVO(20, 0, 1);
		problemVector[1][0] = new OperationIndexVO(5, 1, 0);
		problemVector[1][1] = new OperationIndexVO(5, 1, 1);
		
		vectorScenario1.setProblem(problemVector);
		vectorScenario1.scheduleOperation(problemVector[0][0]);
		vectorScenario1.scheduleOperation(problemVector[1][1]);
		vectorScenario1.scheduleOperation(problemVector[1][0]);
		vectorScenario1.scheduleOperation(problemVector[0][1]);
	}
	
	
	// -----------------------------------------------
	// Test cases
	// -----------------------------------------------
	
	/**
	 * The size of the complete neighborhood in the graph should be vectorSize P 2. P = permutation.
	 * @throws Exception - Functionality error
	 */
	@Test
	public void testNeighborhoodSizeGraphScenario1() throws Exception{
		ArrayList<PairVO> neighborhood = neighborCalulator.calculateCompleteNeighborhood(graphScenario1);
		
		int n = graphScenario1.getOperations().size();
		int r = 2;
		
		long nPr = (factorial(n))/factorial(n-r);
		long permutacion = permutacion(n, r);
		
		
		
		Assert.assertEquals("The amount of generated neighbor pairs is not correct. ", nPr, neighborhood.size());
		Assert.assertEquals("The amount of generated neighbor pairs is not correct. ", permutacion, neighborhood.size());
	}
	
	/**
	 * The size of the complete neighborhood in the vector should be vectorSize P 2. P = permutation.
	 * @throws Exception - Functionality error
	 */
	@Test
	public void testNeighborhoodSizeVectorScenario1() throws Exception{
		ArrayList<PairVO> neighborhood = neighborCalulator.calculateCompleteNeighborhood(vectorScenario1);
		
		int n = vectorScenario1.getOperations().size();
		int r = 2;
		
		long nCr = (factorial(n))/factorial(n-r);
		
		Assert.assertEquals("The amount of generated neighbor pairs is not correct. ", nCr, neighborhood.size());
		Assert.assertEquals("The amount of generated neighbor pairs is not correct. ", permutacion(n, r), neighborhood.size());
		
	}
	
	
	
	@Test
	public void testEscenario() throws InterruptedException {
		Graph problem2 = new Graph(4,4);
		
		OperationIndexVO[][] escenario = new OperationIndexVO[4][4];
		OperationIndexVO o11 = new OperationIndexVO(5, 0, 0);
		escenario[0][0]= o11;
		OperationIndexVO o12 = new OperationIndexVO(10, 0, 1);
		escenario[0][1]= o12;
		OperationIndexVO o13 = new OperationIndexVO(5, 0, 2);
		escenario[0][2]= o13;
		OperationIndexVO o14 = new OperationIndexVO(5, 0, 3);
		escenario[0][3]= o14;
		
		OperationIndexVO o21 = new OperationIndexVO(5, 1, 0);
		escenario[1][0]= o21;
		OperationIndexVO o22 = new OperationIndexVO(10, 1, 1);
		escenario[1][1]= o22;
		OperationIndexVO o23 = new OperationIndexVO(5, 1, 2);
		escenario[1][2]= o23;
		OperationIndexVO o24 = new OperationIndexVO(5, 1, 3);
		escenario[1][3]= o24;
				
		OperationIndexVO o31 = new OperationIndexVO(5, 2, 0);
		escenario[2][0]= o31;
		OperationIndexVO o32 = new OperationIndexVO(10, 2, 1);
		escenario[2][1]= o32;
		OperationIndexVO o33 = new OperationIndexVO(10, 2, 2);
		escenario[2][2]= o33;
		OperationIndexVO o34 = new OperationIndexVO(5, 2, 3);
		escenario[2][3]= o34;
		OperationIndexVO o41 = new OperationIndexVO(5, 3, 0);
		escenario[3][0]= o41;
		OperationIndexVO o42 = new OperationIndexVO(5, 3, 1);
		escenario[3][1]= o42;
		OperationIndexVO o43 = new OperationIndexVO(10, 3, 2);
		escenario[3][2]= o43;
		OperationIndexVO o44 = new OperationIndexVO(10, 3, 3);
		escenario[3][3]= o44;
		
		
		problem2.setProblem(escenario);
		
		problem2.scheduleOperation(o11);
		problem2.scheduleOperation(o12);
		problem2.scheduleOperation(o13);
		problem2.scheduleOperation(o14);
		problem2.scheduleOperation(o21);
		problem2.scheduleOperation(o22);
		problem2.scheduleOperation(o23);
		problem2.scheduleOperation(o24);
		problem2.scheduleOperation(o31);
		problem2.scheduleOperation(o32);
		problem2.scheduleOperation(o33);
		problem2.scheduleOperation(o34);
		problem2.scheduleOperation(o41);
		problem2.scheduleOperation(o42);
		problem2.scheduleOperation(o43);
		problem2.scheduleOperation(o44);
		
		try {
			ArrayList<PairVO> vecinos = neighborCalulator.calculateCompleteNeighborhood(problem2);
			System.out.println(new PairVO(o11, o12));
			assertTrue(vecinos.contains(new PairVO(o11, o12)));
			assertTrue(vecinos.contains(new PairVO(o11, o13)));
			assertTrue(vecinos.contains(new PairVO(o11, o14)));
			assertTrue(vecinos.contains(new PairVO(o41, o12)));
			assertTrue(vecinos.contains(new PairVO(o41, o13)));
			assertTrue(vecinos.contains(new PairVO(o41, o14)));
			assertTrue(vecinos.contains(new PairVO(o23, o32)));
			assertTrue(neighborCalulator.calculateCompleteNeighborhood(problem2).contains(new PairVO(o32, o33)));
			assertTrue(neighborCalulator.calculateCompleteNeighborhood(problem2).contains(new PairVO(o33, o43)));
			assertTrue(neighborCalulator.calculateCompleteNeighborhood(problem2).contains(new PairVO(o43, o44)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}
	
	@Test
	public void testEscenario3() throws Exception {
		ArrayList<String> problemFiles = new ArrayList<String>();

		String TFile = "./data/04x04x02/1-T/T-04x04x02-01.txt";
		String TTFile = "./data/04x04x02/2-TT/TT-04x04x02-01.txt";
		String MFile = "./data/04x04x02/4-M/M-04x04x02-01.txt";
		
		problemFiles.add(TFile);
		problemFiles.add(TTFile);
		problemFiles.add(MFile);

		ArrayList<BetaVO> betas = new ArrayList<BetaVO>();
		ArrayList<String> informationFiles = new ArrayList<String>();
		informationFiles.add(TTFile);

		BetaVO TTBeta = new BetaVO("TravelTimes", "beta.impl.TravelTimes", informationFiles, true);
		BetaVO TearDownTT = new BetaVO("TearDownTravelTime", "beta.impl.TearDownTravelTime", informationFiles, true);
		betas.add(TTBeta);
		betas.add(TearDownTT);
		
		Vector vector = (Vector) VectorFactory.createNewInstance(
				"structure.factory.impl.VectorFactory").createSolutionStructure(problemFiles, betas, new SequencialDecoding());
		
		vector.scheduleOperation(new OperationIndexVO(0, 0, 0, 0));
		vector.scheduleOperation(new OperationIndexVO(0, 0, 1, 3));
		vector.scheduleOperation(new OperationIndexVO(0, 0, 2, 4));
		vector.scheduleOperation(new OperationIndexVO(0, 0, 3, 6));
		
		vector.scheduleOperation(new OperationIndexVO(0, 1, 0, 1));
		vector.scheduleOperation(new OperationIndexVO(0, 1, 1, 2));
		vector.scheduleOperation(new OperationIndexVO(0, 1, 2, 5));
		vector.scheduleOperation(new OperationIndexVO(0, 1, 3, 7));
		
		vector.scheduleOperation(new OperationIndexVO(0, 2, 0, 0));
		vector.scheduleOperation(new OperationIndexVO(0, 2, 1, 3));
		vector.scheduleOperation(new OperationIndexVO(0, 2, 2, 4));
		vector.scheduleOperation(new OperationIndexVO(0, 2, 3, 6));

		vector.scheduleOperation(new OperationIndexVO(0, 3, 0, 1));
		vector.scheduleOperation(new OperationIndexVO(0, 3, 1, 2));
		vector.scheduleOperation(new OperationIndexVO(0, 3, 2, 5));
		vector.scheduleOperation(new OperationIndexVO(0, 3, 3, 7));
		
		ArrayList<PairVO> vecinos = neighborCalulator.calculateCompleteNeighborhood(vector);
		assertTrue(vecinos.contains(new PairVO(new OperationIndexVO(0, 0, 0, 0), new OperationIndexVO(0, 0, 1, 3))));
		assertTrue(vecinos.contains(new PairVO(new OperationIndexVO(0, 0, 0, 0), new OperationIndexVO(0, 0, 2, 4))));
		assertTrue(vecinos.contains(new PairVO(new OperationIndexVO(0, 0, 0, 0), new OperationIndexVO(0, 0, 3, 6))));
		
		assertTrue(vecinos.contains(new PairVO(new OperationIndexVO(0, 1, 0, 1), new OperationIndexVO(0, 0, 1, 3))));
		assertTrue(vecinos.contains(new PairVO(new OperationIndexVO(0, 1, 0, 1), new OperationIndexVO(0, 0, 2, 4))));
		assertTrue(vecinos.contains(new PairVO(new OperationIndexVO(0, 1, 0, 1), new OperationIndexVO(0, 0, 3, 6))));
		
		assertTrue(vecinos.contains(new PairVO(new OperationIndexVO(0, 2, 2, 4), new OperationIndexVO(0, 0, 1, 3))));
		assertTrue(vecinos.contains(new PairVO(new OperationIndexVO(0, 2, 2, 4), new OperationIndexVO(0, 0, 2, 4))));
		assertTrue(vecinos.contains(new PairVO(new OperationIndexVO(0, 2, 2, 4), new OperationIndexVO(0, 0, 3, 6))));
		
		assertTrue(vecinos.contains(new PairVO(new OperationIndexVO(0, 3, 2, 5), new OperationIndexVO(0, 0, 1, 3))));
		assertTrue(vecinos.contains(new PairVO(new OperationIndexVO(0, 3, 2, 5), new OperationIndexVO(0, 0, 2, 4))));
		assertTrue(vecinos.contains(new PairVO(new OperationIndexVO(0, 3, 2, 5), new OperationIndexVO(0, 0, 3, 6))));
		
	}
	
	
	@Test
	public void testEscenario4() throws Exception {
		ArrayList<String> problemFiles = new ArrayList<String>();

		String TFile = "./data/15x15/1-T/T-15x15-01.txt";
		String TTFile = "./data/15x15/2-TT/TT-15x15-01.txt";
		
		problemFiles.add(TFile);
		problemFiles.add(TTFile);

		ArrayList<BetaVO> betas = new ArrayList<BetaVO>();
		ArrayList<String> informationFiles = new ArrayList<String>();
		informationFiles.add(TTFile);

		BetaVO TTBeta = new BetaVO("TravelTimes", "beta.impl.TravelTimes", informationFiles, true);
		BetaVO TearDownTT = new BetaVO("TearDownTravelTime", "beta.impl.TearDownTravelTime", informationFiles, true);
		betas.add(TTBeta);
		betas.add(TearDownTT);
		
		Vector vector = (Vector) VectorFactory.createNewInstance(
				"structure.factory.impl.VectorFactory").createSolutionStructure(problemFiles, betas, new SequencialDecoding());
		
		for(int i=0; i<15;i++){
			for(int j=0; j<15;j++){
				vector.scheduleOperation(new OperationIndexVO(0, i, j, j));
			}
		}
		
		ArrayList<PairVO> neighborhood = neighborCalulator.calculateCompleteNeighborhood(vector);
		
		int n = vector.getOperations().size();
		int r = 2;

		long permutacion = permutacion(n, r);
		
		Assert.assertEquals("The amount of generated neighbor pairs is not correct. ", permutacion, neighborhood.size());
		
		
	}
	
	// -----------------------------------------------
	// Utilities
	// -----------------------------------------------
	
	public static long factorial(int N)
    {
        long multi = 1;
        for (int i = 1; i <= N; i++) {
            multi = multi * i;
        }
        return multi;
    }
	
	public static long permutacion(int N, int r)
    {
        long multi = 1;
        for (int i = N-r+1; i <= N; i++) {
            multi = multi * i;
        }
        return multi;
    }
}