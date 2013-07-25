package initialSolBuilder.test;

import initialSolBuilder.impl.LRPTNonDelay;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import structure.impl.Operation;
import structure.impl.Vector;

import common.types.BetaVO;
import common.types.OperationIndexVO;

/**
 * Test cases for the LRPTNonDelay constructive algorithm
 * 
 * @author David Mendez-Acuna
 * @author Oriana Cendales
 * @author Jaime Romero
 */
public class LRPTNonDelayTest {

	// ------------------------------------------
	// Attributes
	// ------------------------------------------
	
	private LRPTNonDelay testRule;
	
	private int[][] constructiveInitialSolution;
	
	// ------------------------------------------
	// Scenarios
	// ------------------------------------------
	
	@Before
	public void setupScenario() throws Exception{
		testRule = new LRPTNonDelay();
		
		ArrayList<String> problemFiles = new ArrayList<String>();
		problemFiles.add("./data/T-04x04-01.txt");
		problemFiles.add("./data/TT-04x04-01.txt");
		
		ArrayList<BetaVO> betas = new ArrayList<BetaVO>();
		
		ArrayList<String> travelTimeFiles = new ArrayList<String>();
		travelTimeFiles.add("./data/TT-04x04-01.txt");
		BetaVO travelTimes = new BetaVO("TravelTimes", "beta.impl.TravelTimes", travelTimeFiles, true);
		betas.add(travelTimes);
		
		ArrayList<String> tearDownTravelTimeFiles = new ArrayList<String>();
		tearDownTravelTimeFiles.add("./data/TT-04x04-01.txt");
		BetaVO tearDownTravelTimes = new BetaVO("TearDownTravelTime", "beta.impl.TearDownTravelTime", tearDownTravelTimeFiles, true);
		betas.add(tearDownTravelTimes);
		
		constructiveInitialSolution = testRule.createInitialSolution(problemFiles, betas , "structure.factory.impl.VectorFactory", null).calculateAMatrix();
		
	}
	
	// ------------------------------------------
	// Test cases
	// ------------------------------------------
	
	@Test
	public void testLRPTNonDelay04x04_1() throws Exception{
		
		int[][] A = constructiveInitialSolution;
		
		// Asserts
		Assert.assertEquals("Rank A[0][0] is not correct", A[0][0], 3);
		Assert.assertEquals("Rank A[0][1] is not correct", A[0][1], 4);
		Assert.assertEquals("Rank A[0][2] is not correct", A[0][2], 2);
		Assert.assertEquals("Rank A[0][3] is not correct", A[0][3], 1);
		
		Assert.assertEquals("Rank A[1][0] is not correct", A[1][0], 2);
		Assert.assertEquals("Rank A[1][1] is not correct", A[1][1], 1);
		Assert.assertEquals("Rank A[1][2] is not correct", A[1][2], 3);
		Assert.assertEquals("Rank A[1][3] is not correct", A[1][3], 4);
		
		Assert.assertEquals("Rank A[2][0] is not correct", A[2][0], 4);
		Assert.assertEquals("Rank A[2][1] is not correct", A[2][1], 3);
		Assert.assertEquals("Rank A[2][2] is not correct", A[2][2], 1);
		Assert.assertEquals("Rank A[2][3] is not correct", A[2][3], 2);
		
		Assert.assertEquals("Rank A[0][0] is not correct", A[3][0], 1);
		Assert.assertEquals("Rank A[0][0] is not correct", A[3][1], 2);
		Assert.assertEquals("Rank A[0][0] is not correct", A[3][2], 4);
		Assert.assertEquals("Rank A[0][0] is not correct", A[3][3], 3);
	}
	
	@Test
	public void testLRPTNonDelay04x04x02_1() throws Exception{
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

		Vector vector= (Vector) testRule.createInitialSolution(problemFiles, betas , "structure.factory.impl.VectorFactory", null);
		
		// Validando el tamaño de la lista
		Assert.assertEquals("El vector deberia tener un tamaño de 16", vector.getOperations().size(), 16);
		
		// Validando el orden de las operaciones y sus tiempos de inicio y fin
		Assert.assertEquals("La primer posicion deberia ser las <1,3,6>",vector.getOperations().get(0), new Operation(new OperationIndexVO(0, 1, 3, 6)));
		Assert.assertEquals("La primer posicion deberia comenzar en 2 y terminar en 10",vector.getOperations().get(0).getInitialTime()==2 && vector.getOperations().get(0).getFinalTime()==10, true );
		
		Assert.assertEquals("La segunda posicion deberia ser las <2,3,7>",vector.getOperations().get(1), new Operation(new OperationIndexVO(0, 2, 3, 7)));
		Assert.assertEquals("La segunda posicion deberia comenzar en 2 y terminar en 16",vector.getOperations().get(1).getInitialTime()==2 && vector.getOperations().get(1).getFinalTime()==16, true );
		
		Assert.assertEquals("La tercera posicion deberia ser las <3,1,2>",vector.getOperations().get(2), new Operation(new OperationIndexVO(0, 3, 1, 2)));
		Assert.assertEquals("La tercera posicion deberia comenzar en 5 y terminar en 13",vector.getOperations().get(2).getInitialTime()==5 && vector.getOperations().get(2).getFinalTime()==13, true );
		
		Assert.assertEquals("La cuarta posicion deberia ser las <0,1,3>",vector.getOperations().get(3), new Operation(new OperationIndexVO(0, 0, 1, 3)));
		Assert.assertEquals("La cuarta posicion deberia comenzar en 5 y terminar en 13",vector.getOperations().get(3).getInitialTime()==5 && vector.getOperations().get(3).getFinalTime()==13, true );
		
		Assert.assertEquals("La quinta posicion deberia ser las <1,2,4>",vector.getOperations().get(4), new Operation(new OperationIndexVO(0, 1, 2, 4)));
		Assert.assertEquals("La qunita posicion deberia comenzar en 13 y terminar en 25",vector.getOperations().get(4).getInitialTime()==13 && vector.getOperations().get(4).getFinalTime()==25, true );
		
		Assert.assertEquals("La sexta posicion deberia ser las <0,2,4>",vector.getOperations().get(5), new Operation(new OperationIndexVO(0, 3, 0, 0)));
		Assert.assertEquals("La sexta posicion deberia comenzar en 17 y terminar en 28",vector.getOperations().get(5).getInitialTime()==15 && vector.getOperations().get(5).getFinalTime()==29, true );
		
		Assert.assertEquals("La septima posicion deberia ser las <0,0,1>",vector.getOperations().get(6), new Operation(new OperationIndexVO(0, 0, 0, 1)));
		Assert.assertEquals("La septima posicion deberia comenzar en 15 y terminar en 25",vector.getOperations().get(6).getInitialTime()==15 && vector.getOperations().get(6).getFinalTime()==25, true );
		
		Assert.assertEquals("La octava posicion deberia ser las <2,2,5>",vector.getOperations().get(7), new Operation(new OperationIndexVO(0, 2, 2, 5)));
		Assert.assertEquals("La octava posicion deberia comenzar en 19 y terminar en 28",vector.getOperations().get(7).getInitialTime()==19 && vector.getOperations().get(7).getFinalTime()==28, true );
		
		Assert.assertEquals("La novena posicion deberia ser las <0,2,4>",vector.getOperations().get(8), new Operation(new OperationIndexVO(0, 0, 2, 4)));
		Assert.assertEquals("La novena posicion deberia comenzar en 28 y terminar en 39",vector.getOperations().get(8).getInitialTime()==28 && vector.getOperations().get(8).getFinalTime()==39, true );
		
		Assert.assertEquals("La decima posicion deberia ser las <1,0,1>",vector.getOperations().get(9), new Operation(new OperationIndexVO(0, 1, 0, 1)));
		Assert.assertEquals("La decima posicion deberia comenzar en 28 y terminar en 37",vector.getOperations().get(9).getInitialTime()==28 && vector.getOperations().get(9).getFinalTime()==37, true );
		
		Assert.assertEquals("La undecima posicion deberia ser las <2,0,0>",vector.getOperations().get(10), new Operation(new OperationIndexVO(0, 2, 0, 0)));
		Assert.assertEquals("La undecima posicion deberia comenzar en 32 y terminar en 42",vector.getOperations().get(10).getInitialTime()==31 && vector.getOperations().get(10).getFinalTime()==41, true );
		
		Assert.assertEquals("La doceava posicion deberia ser las <1,2,5>",vector.getOperations().get(11), new Operation(new OperationIndexVO(0, 3, 2, 5)));
		Assert.assertEquals("La doceava posicion deberia comenzar en 33 y terminar en 45",vector.getOperations().get(11).getInitialTime()==32 && vector.getOperations().get(11).getFinalTime()==42, true );
	}
}