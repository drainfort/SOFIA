package graph.testcases;

import static org.junit.Assert.*;
import graph.scenarios.GraphScenariosFactory;

import org.junit.Before;
import org.junit.Test;

import structure.impl.Graph;

public class GraphTestClone {

	// -----------------------------------------------
	// Attributes
	// -----------------------------------------------
	
	private Graph problem;

	// -----------------------------------------------
	// Setup scenarios
	// -----------------------------------------------
	
	@Before
	public void setUp() throws Exception {
		try {
			problem = GraphScenariosFactory.buildDummySolution(4, 4);
		} catch (Exception e) {
			fail("Fail loading the input processing times file ");
		}
	}

	// -----------------------------------------------
	// Test clases
	// -----------------------------------------------
	
	@Test
	public void testClone1() throws InterruptedException {
		//Scenario.drawGraphPredecessor(problem);
		Graph newGraph = (Graph)problem.cloneStructure();
		
		//TODO Complete test clone graph
	}
}
