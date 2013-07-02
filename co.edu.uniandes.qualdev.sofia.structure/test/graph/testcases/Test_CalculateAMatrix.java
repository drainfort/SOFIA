package graph.testcases;

import static org.junit.Assert.*;
import graph.scenarios.GraphScenariosFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import structure.impl.Graph;

/**
 * Test cases for the computation of c
 * @author Rubby Casallas
 * @author David Mendez-Acuna
 */
public class Test_CalculateAMatrix {

	// -----------------------------------------------
	// Attributes
	// -----------------------------------------------

	private Graph problem;

	// -----------------------------------------------
	// Setup scenarios
	// -----------------------------------------------

	@Before
	public void setupScenarios() throws Exception {
		problem = GraphScenariosFactory.buildSimple04x04Solution();
	}

	// -----------------------------------------------
	// Setup scenarios
	// -----------------------------------------------
	
	/**
	 * Test of the matrix C calculation for the instance of the scenario
	 * @throws Exception 
	 */
	@Test
	public void testCalculateAMatrix() throws Exception {
		int[][] A = problem.calculateAMatrix();
		
		Assert.assertTrue("Invalid value of A for the operation <0, 0>", A[0][0] == 2);
		Assert.assertTrue("Invalid value of A for the operation <0, 1>", A[0][1] == 1);
		Assert.assertTrue("Invalid value of A for the operation <0, 2>", A[0][2] == 3);
		Assert.assertTrue("Invalid value of A for the operation <0, 3>", A[0][3] == 4);
		
		Assert.assertTrue("Invalid value of A for the operation <1, 0>", A[1][0] == 4);
		Assert.assertTrue("Invalid value of A for the operation <1, 1>", A[1][1] == 3);
		Assert.assertTrue("Invalid value of A for the operation <1, 2>", A[1][2] == 1);
		Assert.assertTrue("Invalid value of A for the operation <1, 3>", A[1][3] == 2);
		
		Assert.assertTrue("Invalid value of A for the operation <2, 0>", A[2][0] == 3);
		Assert.assertTrue("Invalid value of A for the operation <2, 1>", A[2][1] == 4);
		Assert.assertTrue("Invalid value of A for the operation <2, 2>", A[2][2] == 2);
		Assert.assertTrue("Invalid value of A for the operation <2, 3>", A[2][3] == 1);
		
		Assert.assertTrue("Invalid value of A for the operation <0, 0>", A[3][0] == 1);
		Assert.assertTrue("Invalid value of A for the operation <0, 1>", A[3][1] == 2);
		Assert.assertTrue("Invalid value of A for the operation <0, 2>", A[3][2] == 4);
		Assert.assertTrue("Invalid value of A for the operation <0, 3>", A[3][3] == 3);
	}
}