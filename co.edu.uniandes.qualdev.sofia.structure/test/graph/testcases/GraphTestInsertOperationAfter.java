package graph.testcases;

import static org.junit.Assert.*;
import graph.scenarios.GraphScenariosFactory;

import org.junit.Before;
import org.junit.Test;

import common.types.OperationIndexVO;

import structure.impl.Graph;
import structure.impl.Node;

/**
 * Test cases for the implementation of the graph
 * Specifically, for the functionality of inserting an operation after a given predecessor
 * 
 * @author Rubby Casallas
 * @author David Mendez-Acuna
 */
public class GraphTestInsertOperationAfter {

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
			problem = GraphScenariosFactory.buildDummySolution(4, 4);
		} catch (Exception e) {
			fail("Fail loading the input processing times file ");
		}
	}

	// -----------------------------------------------
	// Setup scenarios
	// -----------------------------------------------

	@Test
	public void testInsertOperationAfterInRoute1() throws InterruptedException {
		// same job. Exchange in the route
		OperationIndexVO toInsertOperationIndex = new OperationIndexVO(0, 1);
		OperationIndexVO predecessorOperationIndex = new OperationIndexVO(0, 3);

		problem.insertOperationAfter(toInsertOperationIndex, predecessorOperationIndex);

		// 1. test initial Job Operation
		Node n = problem.getInitialJobNode(0);
		assertTrue(" Initial Job Operation is not correct ", n.getOperation().getOperationIndex().getStationId() == 0);
		assertTrue(" for Initial Job previous must be null", n.getPreviousRouteNode() == null);
		
		n = problem.getNode(0, 0).getNextRouteNode();
		assertTrue("Next is not correct ", n.getOperation().getOperationIndex().getStationId() == 2);
		
		n = problem.getNode(0, 2).getNextRouteNode();
		assertTrue("Next is not correct ", n.getOperation().getOperationIndex().getStationId() == 3);
		
		n = problem.getNode(0, 2).getPreviousRouteNode();
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getStationId() == 0);
		
		n = problem.getNode(0, 3).getNextRouteNode();
		assertTrue("Next of 2 ", n.getOperation().getOperationIndex().getStationId() == 1);
		
		n = problem.getNode(0, 3).getPreviousRouteNode();
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getStationId() == 2);
		
		n = problem.getNode(0, 1).getNextRouteNode();
		assertTrue("Next of 0 ", n == null);
		
		n = problem.getNode(0, 1).getPreviousRouteNode();
		assertTrue("Previous of 0 ", n.getOperation().getOperationIndex().getStationId() == 3);
	}
	
	@Test
	public void testInsertOperationAfterInRoute2() throws InterruptedException {
		// same job. Exchange in the route
		OperationIndexVO toInsertOperationIndex = new OperationIndexVO(0, 1);
		OperationIndexVO predecessorOperationIndex = new OperationIndexVO(0, 0);

		problem.insertOperationAfter(toInsertOperationIndex, predecessorOperationIndex);

		// 1. test initial Job Operation
		Node n = problem.getInitialJobNode(0);

		assertTrue(" Initial Job Operation is not correct ", n.getOperation().getOperationIndex().getStationId() == 0);
		assertTrue(" for Initial Job previous must be null", n.getPreviousRouteNode() == null);
		
		n = problem.getNode(0, 0).getNextRouteNode();
		assertTrue("Next is not correct ", n.getOperation().getOperationIndex().getStationId() == 1);
		
		n = problem.getNode(0, 1).getNextRouteNode();
		assertTrue("Next is not correct ", n.getOperation().getOperationIndex().getStationId() == 2);
		
		n = problem.getNode(0, 1).getPreviousRouteNode();
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getStationId() == 0);
		
		n = problem.getNode(0, 2).getNextRouteNode();
		assertTrue("Next of 2 ", n.getOperation().getOperationIndex().getStationId() == 3);
		
		n = problem.getNode(0, 2).getPreviousRouteNode();
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getStationId() == 1);
		
		n = problem.getNode(0, 3).getNextRouteNode();
		assertTrue("Next of 0 ", n == null);
		
		n = problem.getNode(0, 3).getPreviousRouteNode();
		assertTrue("Previous of 0 ", n.getOperation().getOperationIndex().getStationId() == 2);
	}
	
	@Test
	public void testInsertOperationAfterInRoute3() throws InterruptedException {
		// same job. Exchange in the route
		OperationIndexVO toInsertOperationIndex = new OperationIndexVO(0, 2);
		OperationIndexVO predecessorOperationIndex = new OperationIndexVO(0, 0);

		problem.insertOperationAfter(toInsertOperationIndex, predecessorOperationIndex);

		// 1. test initial Job Operation
		Node n = problem.getInitialJobNode(0);

		assertTrue(" Initial Job Operation is not correct ", n.getOperation().getOperationIndex().getStationId() == 0);
		assertTrue(" for Initial Job previous must be null", n.getPreviousRouteNode() == null);
		
		n = problem.getNode(0, 0).getNextRouteNode();
		assertTrue("Next is not correct ", n.getOperation().getOperationIndex().getStationId() == 2);
		
		n = problem.getNode(0, 2).getNextRouteNode();
		assertTrue("Next is not correct ", n.getOperation().getOperationIndex().getStationId() == 1);
		
		n = problem.getNode(0, 2).getPreviousRouteNode();
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getStationId() == 0);
		
		n = problem.getNode(0, 1).getNextRouteNode();
		assertTrue("Next of 2 ", n.getOperation().getOperationIndex().getStationId() == 3);
		
		n = problem.getNode(0, 1).getPreviousRouteNode();
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getStationId() == 2);
		
		n = problem.getNode(0, 3).getNextRouteNode();
		assertTrue("Next of 0 ", n == null);
		
		n = problem.getNode(0, 3).getPreviousRouteNode();
		assertTrue("Previous of 0 ", n.getOperation().getOperationIndex().getStationId() == 1);
	}
	
	@Test
	public void testInsertOperationAfterInSequence1() throws InterruptedException {
		// same machine. Exchange in the sequence
		OperationIndexVO toInsertOperationIndex = new OperationIndexVO(1, 0);
		OperationIndexVO predecessorOperationIndex = new OperationIndexVO(3, 0);

		problem.insertOperationAfter(toInsertOperationIndex, predecessorOperationIndex);

		// 1. test initial Machine Operation
		Node n = problem.getInitialStationNode(0);

		assertTrue(" Initial Machine Operation is not correct ", n.getOperation().getOperationIndex().getJobId() == 0);
		assertTrue(" for Initial Machine previous must be null", n.getPreviousSequenceNode() == null);
		
		n = problem.getNode(0, 0).getNextSequenceNode();
		assertTrue("Next is not correct ", n.getOperation().getOperationIndex().getJobId() == 2);
		
		n = problem.getNode(2, 0).getNextSequenceNode();
		assertTrue("Next is not correct ", n.getOperation().getOperationIndex().getJobId() == 3);
		
		n = problem.getNode(2, 0).getPreviousSequenceNode();
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getJobId() == 0);
		
		n = problem.getNode(3, 0).getNextSequenceNode();
		assertTrue("Next is not correct ", n.getOperation().getOperationIndex().getJobId() == 1);
		
		n = problem.getNode(3, 0).getPreviousSequenceNode();
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getJobId() == 2);
		
		n = problem.getNode(1, 0).getNextSequenceNode();
		assertTrue("Next is not correct ", n == null);
		
		n = problem.getNode(1, 0).getPreviousSequenceNode();
		assertTrue("Previous is not correct", n.getOperation().getOperationIndex().getJobId() == 3);
	}
	
	@Test
	public void testInsertOperationAfterInSequence2() throws InterruptedException {
		// same job. Exchange in the route
		OperationIndexVO toInsertOperationIndex = new OperationIndexVO(1, 0);
		OperationIndexVO predecessorOperationIndex = new OperationIndexVO(0, 0);

		problem.insertOperationAfter(toInsertOperationIndex, predecessorOperationIndex);

		// 1. test initial Machine Operation
		Node n = problem.getInitialStationNode(0);
		assertTrue(" Initial Machine Operation is not correct ", n.getOperation().getOperationIndex().getJobId() == 0);
		assertTrue(" for Initial Machine previous must be null", n.getPreviousSequenceNode() == null);
		
		n = problem.getNode(0, 0).getNextSequenceNode();
		assertTrue("Next is not correct ", n.getOperation().getOperationIndex().getJobId() == 1);
		
		n = problem.getNode(1, 0).getNextSequenceNode();
		assertTrue("Next is not correct ", n.getOperation().getOperationIndex().getJobId() == 2);
		
		n = problem.getNode(1, 0).getPreviousSequenceNode();
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getJobId() == 0);
		
		n = problem.getNode(2, 0).getNextSequenceNode();
		assertTrue("Next is not correct ", n.getOperation().getOperationIndex().getJobId() == 3);
		
		n = problem.getNode(2, 0).getPreviousSequenceNode();
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getJobId() == 1);
		
		n = problem.getNode(3, 0).getNextSequenceNode();
		assertTrue("Next is not correct ", n == null);
		
		n = problem.getNode(3, 0).getPreviousSequenceNode();
		assertTrue("Previous is not correct", n.getOperation().getOperationIndex().getJobId() == 2);
	}
	
	@Test
	public void testInsertOperationAfterInSequence3() throws InterruptedException {
		// same job. Exchange in the route
		OperationIndexVO toInsertOperationIndex = new OperationIndexVO(2, 0);
		OperationIndexVO predecessorOperationIndex = new OperationIndexVO(0, 0);

		problem.insertOperationAfter(toInsertOperationIndex, predecessorOperationIndex);

		// 1. test initial Machine Operation
		Node n = problem.getInitialStationNode(0);

		assertTrue(" Initial Machine Operation is not correct ", n.getOperation().getOperationIndex().getJobId() == 0);
		assertTrue(" for Initial Machine previous must be null", n.getPreviousSequenceNode() == null);
		
		n = problem.getNode(0, 0).getNextSequenceNode();
		assertTrue("Next is not correct ", n.getOperation().getOperationIndex().getJobId() == 2);
		
		n = problem.getNode(2, 0).getNextSequenceNode();
		assertTrue("Next is not correct ", n.getOperation().getOperationIndex().getJobId() == 1);
		
		n = problem.getNode(2, 0).getPreviousSequenceNode();
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getJobId() == 0);
		
		n = problem.getNode(1, 0).getNextSequenceNode();
		assertTrue("Next is not correct ", n.getOperation().getOperationIndex().getJobId() == 3);
		
		n = problem.getNode(1, 0).getPreviousSequenceNode();
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getJobId() == 2);
		
		n = problem.getNode(3, 0).getNextSequenceNode();
		assertTrue("Next is not correct ", n == null);
		
		n = problem.getNode(3, 0).getPreviousSequenceNode();
		assertTrue("Previous is not correct", n.getOperation().getOperationIndex().getJobId() == 1);
	}
}