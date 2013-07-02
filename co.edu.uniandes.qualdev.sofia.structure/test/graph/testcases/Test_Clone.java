package graph.testcases;

import static org.junit.Assert.*;
import graph.scenarios.GraphScenariosFactory;

import org.junit.Before;
import org.junit.Test;

import structure.impl.Graph;

public class Test_Clone {

	// -----------------------------------------------
	// Attributes
	// -----------------------------------------------
	
	private Graph problem;

	// -----------------------------------------------
	// Setup scenarios
	// -----------------------------------------------
	
	@Before
	public void setUpScenarios() throws Exception {
		problem = GraphScenariosFactory.buildDummySolution(4, 4);
	}

	// -----------------------------------------------
	// Test clases
	// -----------------------------------------------
	
	@Test
	public void testClone() throws InterruptedException {
		//Scenario.drawGraphPredecessor(problem);
		Graph newGraph = (Graph)problem.cloneStructure();
		
		//TODO Complete test clone graph
	}
}
