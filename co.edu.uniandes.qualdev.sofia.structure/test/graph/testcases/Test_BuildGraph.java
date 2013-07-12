package graph.testcases;

import graph.scenarios.GraphScenariosFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import common.types.OperationIndexVO;

import structure.IOperation;
import structure.impl.Graph;

/**
 * Test cases for the implementation of the graph
 * 
 * @author Rubby Casallas
 * @author David Mendez-Acuna
 */
public class Test_BuildGraph {

	// -----------------------------------------------
	// Attributes
	// -----------------------------------------------

	private Graph problem;

	// -----------------------------------------------
	// Setup scenarios
	// -----------------------------------------------

	@Before
	public void setupScenario1() throws Exception {
		problem = GraphScenariosFactory.buildSimple04x04Problem();
	}

	// -----------------------------------------------
	// Test cases
	// -----------------------------------------------

	@Test
	public void testProecessingTimes(){
		OperationIndexVO[][] problemMatrix = problem.getProblem();
		
		Assert.assertEquals(34, problemMatrix[0][0].getProcessingTime());
		Assert.assertEquals(2, problemMatrix[0][1].getProcessingTime());
		Assert.assertEquals(54, problemMatrix[0][2].getProcessingTime());
		Assert.assertEquals(61, problemMatrix[0][3].getProcessingTime());
		Assert.assertEquals(15, problemMatrix[1][0].getProcessingTime());
		Assert.assertEquals(89, problemMatrix[1][1].getProcessingTime());
		Assert.assertEquals(70, problemMatrix[1][2].getProcessingTime());
		Assert.assertEquals(9, problemMatrix[1][3].getProcessingTime());
		Assert.assertEquals(38, problemMatrix[2][0].getProcessingTime());
		Assert.assertEquals(19, problemMatrix[2][1].getProcessingTime());
		Assert.assertEquals(28, problemMatrix[2][2].getProcessingTime());
		Assert.assertEquals(87, problemMatrix[2][3].getProcessingTime());
		Assert.assertEquals(95, problemMatrix[3][0].getProcessingTime());
		Assert.assertEquals(7, problemMatrix[3][1].getProcessingTime());
		Assert.assertEquals(34, problemMatrix[3][2].getProcessingTime());
		Assert.assertEquals(29, problemMatrix[3][3].getProcessingTime());
	}
}