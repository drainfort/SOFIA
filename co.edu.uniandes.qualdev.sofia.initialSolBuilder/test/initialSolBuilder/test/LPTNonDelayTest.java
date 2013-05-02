package initialSolBuilder.test;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import common.types.BetaVO;

import initialSolBuilder.impl.LPTNonDelay;

/**
 * Test cases for the LPTNonDelay constructive algorithm
 * 
 * @author David Mendez-Acuna
 * @author Oriana Cendales
 *
 */
public class LPTNonDelayTest {

	// ------------------------------------------
	// Attributes
	// ------------------------------------------
	
	private LPTNonDelay testRule;
	
	private int[][] constructiveInitialSolution;
	
	// ------------------------------------------
	// Scenarios
	// ------------------------------------------
	
	@Before
	public void setupScenario() throws Exception{
		testRule = new LPTNonDelay();
		
		ArrayList<String> problemFiles = new ArrayList<String>();
		problemFiles.add("./data/T-04x04-01.txt");
		problemFiles.add("./data/TT-04x04-01.txt");
		
		ArrayList<BetaVO> betas = new ArrayList<BetaVO>();
		
		ArrayList<String> travelTimeFiles = new ArrayList<String>();
		travelTimeFiles.add("./data/TT-04x04-01.txt");
		BetaVO travelTimes = new BetaVO("TravelTimes", "beta.TravelTimes", travelTimeFiles);
		betas.add(travelTimes);
		
		ArrayList<String> tearDownTravelTimeFiles = new ArrayList<String>();
		tearDownTravelTimeFiles.add("./data/TT-04x04-01.txt");
		BetaVO tearDownTravelTimes = new BetaVO("TearDownTravelTime", "beta.TearDownTravelTime", tearDownTravelTimeFiles);
		betas.add(tearDownTravelTimes);
		
		constructiveInitialSolution = testRule.createInitialSolution(problemFiles, betas , "vector.factory.VectorFactory", null).calculateAMatrix();
	}
	
	// ------------------------------------------
	// Test cases
	// ------------------------------------------
	
	@Test
	public void testLPTNonDelay04x04_1() throws Exception{
		
		int[][] A = constructiveInitialSolution;
		
		// Asserts
		Assert.assertEquals("Rank A[0][0] is not correct", A[0][0], 2);
		Assert.assertEquals("Rank A[0][1] is not correct", A[0][1], 1);
		Assert.assertEquals("Rank A[0][2] is not correct", A[0][2], 3);
		Assert.assertEquals("Rank A[0][3] is not correct", A[0][3], 4);
		
		Assert.assertEquals("Rank A[1][0] is not correct", A[1][0], 4);
		Assert.assertEquals("Rank A[1][1] is not correct", A[1][1], 3);
		Assert.assertEquals("Rank A[1][2] is not correct", A[1][2], 1);
		Assert.assertEquals("Rank A[1][3] is not correct", A[1][3], 2);
		
		Assert.assertEquals("Rank A[2][0] is not correct", A[2][0], 3);
		Assert.assertEquals("Rank A[2][1] is not correct", A[2][1], 4);
		Assert.assertEquals("Rank A[2][2] is not correct", A[2][2], 2);
		Assert.assertEquals("Rank A[2][3] is not correct", A[2][3], 1);
		
		Assert.assertEquals("Rank A[0][0] is not correct", A[3][0], 1);
		Assert.assertEquals("Rank A[0][0] is not correct", A[3][1], 2);
		Assert.assertEquals("Rank A[0][0] is not correct", A[3][2], 4);
		Assert.assertEquals("Rank A[0][0] is not correct", A[3][3], 3);
	}
}