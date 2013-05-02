package graph.testcases;

import static org.junit.Assert.*;
import graph.scenarios.GraphScenariosFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import common.types.OperationIndexVO;
import common.utils.MatrixUtils;

import structure.impl.Graph;

/**
 * Test cases for the computation of c
 * @author Rubby Casallas
 * @author David Mendez-Acuna
 */
public class GraphTestCalculateMatrixC {

	// -----------------------------------------------
	// Attributes
	// -----------------------------------------------

	private Graph problem;

	// -----------------------------------------------
	// Setup scenarios
	// -----------------------------------------------

	@Before
	public void setupScenario1() {
		try {
			problem = GraphScenariosFactory.buildSimple04x04Solution();
		} catch (Exception e) {
			e.printStackTrace();
			fail("Fail loading the input processing times file ");
		}
	}

	// -----------------------------------------------
	// Setup scenarios
	// -----------------------------------------------
	
	/**
	 * Test of the matrix C calculation for the instance of the schenario
	 * @throws Exception 
	 */
	@Test
	public void testCalculateCMatrix() throws Exception {
		// Initial route for the Job 0: EMPTY
		int[][] c = problem.calculateCMatrix();
		MatrixUtils.printMatrix(c);
		
		Assert.assertTrue("Invalid value of C for the operation <0, 0>", 
				problem.geFinalTime(new OperationIndexVO(0, 0)) == 129);
		
		Assert.assertTrue("Invalid value of C for the operation <0, 1>", 
				problem.geFinalTime(new OperationIndexVO(0, 1)) == 2);
		
		Assert.assertTrue("Invalid value of C for the operation <0, 2>", 
				problem.geFinalTime(new OperationIndexVO(0, 2)) == 183);
		
		Assert.assertTrue("Invalid value of C for the operation <0, 3>", 
				problem.geFinalTime(new OperationIndexVO(0, 3)) == 244);
		
		Assert.assertTrue("Invalid value of C for the operation <1, 0>", 
				problem.geFinalTime(new OperationIndexVO(1, 0)) == 206);
		
		Assert.assertTrue("Invalid value of C for the operation <1, 1>", 
				problem.geFinalTime(new OperationIndexVO(1, 1)) == 191);
		
		Assert.assertTrue("Invalid value of C for the operation <1, 2>", 
				problem.geFinalTime(new OperationIndexVO(1, 2)) == 70);
		
		Assert.assertTrue("Invalid value of C for the operation <1, 3>", 
				problem.geFinalTime(new OperationIndexVO(1, 3)) == 96);
		
		Assert.assertTrue("Invalid value of C for the operation <2, 0>", 
				problem.geFinalTime(new OperationIndexVO(2, 0)) == 167);
		
		Assert.assertTrue("Invalid value of C for the operation <2, 1>", 
				problem.geFinalTime(new OperationIndexVO(2, 1)) == 210);
		
		Assert.assertTrue("Invalid value of C for the operation <2, 2>", 
				problem.geFinalTime(new OperationIndexVO(2, 2)) == 115);
		
		Assert.assertTrue("Invalid value of C for the operation <2, 3>", 
				problem.geFinalTime(new OperationIndexVO(2, 3)) == 87);
		
		Assert.assertTrue("Invalid value of C for the operation <3, 0>", 
				problem.geFinalTime(new OperationIndexVO(3, 0)) == 95);

		Assert.assertTrue("Invalid value of C for the operation <3, 1>", 
				problem.geFinalTime(new OperationIndexVO(3, 1)) == 102);
		
		Assert.assertTrue("Invalid value of C for the operation <3, 2>", 
				problem.geFinalTime(new OperationIndexVO(3, 2)) == 217);
		
		Assert.assertTrue("Invalid value of C for the operation <3, 3>", 
				problem.geFinalTime(new OperationIndexVO(3, 3)) == 131);
	}
}