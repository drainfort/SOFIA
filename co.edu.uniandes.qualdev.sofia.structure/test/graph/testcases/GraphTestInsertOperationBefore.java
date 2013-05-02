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
 * Specifically, for the functionality of inserting an operation before a given successor
 * 
 * @author Rubby Casallas
 * @author David Mendez-Acuna
 */
public class GraphTestInsertOperationBefore {

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
	public void testInsertOperationBeforeInRoute1() throws InterruptedException {

		// same job. Exchange in the route
		OperationIndexVO toInsertOperationIndex = new OperationIndexVO(0, 1);
		OperationIndexVO successorOperationIndex = new OperationIndexVO(0, 0);
		problem.insertOperationBefore(toInsertOperationIndex, successorOperationIndex);

		// 1. test initial Job Operation
		Node n = problem.getInitialJobNode(0);

		assertTrue(" Initial Job Operation is not correct ", n.getOperation().getOperationIndex().getStationId() == 1);
		assertTrue(" for Initial Job previous must be null", n.getPreviousRouteNode() == null);
		
		n = problem.getNode(0, 1).getNextRouteNode();
		assertTrue("Next is not correct ", n.getOperation().getOperationIndex().getStationId() == 0);
		
		n = problem.getNode(0, 0).getNextRouteNode();
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getStationId() == 2);
		
		n = problem.getNode(0, 0).getPreviousRouteNode();
		assertTrue("Next is not correct ", n.getOperation().getOperationIndex().getStationId() == 1);
		
		n = problem.getNode(0, 2).getNextRouteNode();
		assertTrue("Next of 2 ", n.getOperation().getOperationIndex().getStationId() == 3);
		
		n = problem.getNode(0, 2).getPreviousRouteNode();
		assertTrue("Next is not correct ", n.getOperation().getOperationIndex().getStationId() == 0);
		
		n = problem.getNode(0, 3).getNextRouteNode();
		assertTrue("Next of 0 ", n == null);
		
		n = problem.getNode(0, 3).getPreviousRouteNode();
		assertTrue("Next of 0 ", n.getOperation().getOperationIndex().getStationId() == 2);
	}
	
	@Test
	public void testInsertOperationBeforeInRoute2() throws InterruptedException {
		// same job. Exchange in the route
		OperationIndexVO toInsertOperationIndex = new OperationIndexVO(3, 3);
		OperationIndexVO successorOperationIndex = new OperationIndexVO(3, 2);

		problem.insertOperationBefore(toInsertOperationIndex, successorOperationIndex);

		// 1. test initial Job Operation
		Node n = problem.getInitialJobNode(3);

		assertTrue(" Initial Job Operation is not correct ", n.getOperation().getOperationIndex().getStationId() == 0);
		assertTrue(" for Initial Job previous must be null", n.getPreviousRouteNode() == null);
		
		n = problem.getNode(3, 0).getNextRouteNode();
		assertTrue("Next is not correct ", n.getOperation().getOperationIndex().getStationId() == 1);
		
		n = problem.getNode(3, 1).getNextRouteNode();
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getStationId() == 3);
		
		n = problem.getNode(3, 1).getPreviousRouteNode();
		assertTrue("Next is not correct ", n.getOperation().getOperationIndex().getStationId() == 0);
		
		n = problem.getNode(3, 3).getNextRouteNode();
		assertTrue("Next of 2 ", n.getOperation().getOperationIndex().getStationId() == 2);
		
		n = problem.getNode(3, 3).getPreviousRouteNode();
		assertTrue("Next is not correct ", n.getOperation().getOperationIndex().getStationId() == 1);
		
		n = problem.getNode(3, 2).getNextRouteNode();
		assertTrue("Next of 0 ", n == null);
		n = problem.getNode(3, 2).getPreviousRouteNode();
		assertTrue("Next of 0 ", n.getOperation().getOperationIndex().getStationId() == 3);
	}
	
	@Test
	public void testInsertOperationBeforeInSequence1() throws InterruptedException {
		// same job. Exchange in the route
		OperationIndexVO toInsertOperationIndex = new OperationIndexVO(2, 0);
		OperationIndexVO successorOperationIndex = new OperationIndexVO(1, 0);

		problem.insertOperationBefore(toInsertOperationIndex, successorOperationIndex);

		// 1. test initial Job Operation
		Node n = problem.getInitialStationNode(0);

		assertTrue(" Initial Machine Operation is not correct ", n.getOperation().getOperationIndex().getJobId() == 0);
		assertTrue(" for Initial Machine previous must be null", n.getPreviousSequenceNode() == null);
		
		n = problem.getNode(0, 0).getNextSequenceNode();
		assertTrue("Next is not correct ", n.getOperation().getOperationIndex().getJobId() == 2);
		
		n = problem.getNode(2, 0).getNextSequenceNode();
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getJobId() == 1);
		
		n = problem.getNode(2, 0).getPreviousSequenceNode();
		assertTrue("Next is not correct ", n.getOperation().getOperationIndex().getJobId() == 0);
		
		n = problem.getNode(1, 0).getNextSequenceNode();
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getJobId() == 3);
		
		n = problem.getNode(1, 0).getPreviousSequenceNode();
		assertTrue("Next is not correct ", n.getOperation().getOperationIndex().getJobId() == 2);
		
		n = problem.getNode(3, 0).getNextSequenceNode();
		assertTrue("Previous is not correct ", n == null);
		
		n = problem.getNode(3, 0).getPreviousSequenceNode();
		assertTrue("Next is not correct ", n.getOperation().getOperationIndex().getJobId() == 1);
	}
	
	@Test
	public void testInsertOperationBeforeInSequence2() throws Exception {
		OperationIndexVO toInsertOperationIndex = new OperationIndexVO(0, 2);
		OperationIndexVO successorOperationIndex = new OperationIndexVO(0, 0);
		problem.insertOperationBefore(toInsertOperationIndex, successorOperationIndex);

	}
	
	@Test
	public void testInsertOperationBeforeInSequence3() throws Exception {
		OperationIndexVO toInsertOperationIndex = new OperationIndexVO(0, 0);
		OperationIndexVO successorOperationIndex = new OperationIndexVO(0, 2);
		problem.insertOperationBefore(toInsertOperationIndex, successorOperationIndex);
	}
	
	@Test
	public void testInsertOperationBeforeInSequence4() throws Exception {
		OperationIndexVO toInsertOperationIndex = new OperationIndexVO(0, 1);
		OperationIndexVO successorOperationIndex = new OperationIndexVO(2, 1);
		problem.insertOperationBefore(toInsertOperationIndex, successorOperationIndex);
		problem.insertOperationBefore(toInsertOperationIndex, successorOperationIndex);
	}
}