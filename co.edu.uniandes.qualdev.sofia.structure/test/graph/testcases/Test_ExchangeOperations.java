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
 * 
 * @author Rubby Casallas
 * @author David Mendez-Acuna
 */
public class Test_ExchangeOperations {

	// -----------------------------------------------
	// Attributes
	// -----------------------------------------------

	private Graph problem;

	// -----------------------------------------------
	// Setup scenarios
	// -----------------------------------------------

	@Before
	public void setupScenarios() {
		problem = GraphScenariosFactory.buildDummySolution(4, 4);
	}

	// -----------------------------------------------
	// Setup scenarios
	// -----------------------------------------------

	@Test
	public void testExchangeOperationsRouteSucessor()
			throws InterruptedException {
		
		// same job. Exchange in the route
		OperationIndexVO start = new OperationIndexVO(0, 0);
		OperationIndexVO end = new OperationIndexVO(0, 2);

		problem.exchangeOperations(start, end);

		// 1. test initial Job Operation
		Node n = problem.getInitialJobNode(0);
		assertTrue(" Initial Job Operation is not correct ", n.getOperation().getOperationIndex().getJobId() == 0);
		assertTrue(" Initial Job Operation is not correct ", n.getOperation().getOperationIndex().getStationId() == 2);

		// 2. test next end node
		n = problem.getNode(0, 2).getNextRouteNode();
		assertTrue("Next is not correct ", n.getOperation().getOperationIndex().getJobId() == 0);
		assertTrue("Next is not correct ", n.getOperation().getOperationIndex().getStationId() == 1);

		n = problem.getNode(0, 2).getPreviousRouteNode();
		assertTrue("Next is not correct ", n == null);
		assertTrue("Next is not correct ", n == null);

		n = problem.getNode(0, 1).getNextRouteNode();
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getJobId() == 0);
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getStationId() == 0);

		n = problem.getNode(0, 1).getPreviousRouteNode();
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getJobId() == 0);
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getStationId() == 2);

		n = problem.getNode(0, 0).getNextRouteNode();
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getJobId() == 0);
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getStationId() == 3);

		n = problem.getNode(0, 0).getPreviousRouteNode();
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getJobId() == 0);
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getStationId() == 1);

		n = problem.getNode(0, 3).getNextRouteNode();
		assertTrue("Previous is not correct ", n == null);
		assertTrue("Previous is not correct ", n == null);

		n = problem.getNode(0, 3).getPreviousRouteNode();
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getJobId() == 0);
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getStationId() == 0);
		
		
		OperationIndexVO start1 = new OperationIndexVO(0, 2);
		OperationIndexVO end1 = new OperationIndexVO(0, 3);

		problem.exchangeOperations(start1, end1);

		// 1. test initial Job Operation
		n = problem.getInitialJobNode(0);
		assertTrue(" Initial Job Operation is not correct ", n.getOperation().getOperationIndex().getJobId() == 0);
		assertTrue(" Initial Job Operation is not correct ", n.getOperation().getOperationIndex().getStationId() == 3);

		// 2. test next end node
		n = problem.getNode(0, 3).getNextRouteNode();
		assertTrue("Next is not correct ", n.getOperation().getOperationIndex().getJobId() == 0);
		assertTrue("Next is not correct ", n.getOperation().getOperationIndex().getStationId() == 1);

		n = problem.getNode(0, 3).getPreviousRouteNode();
		assertTrue("Next is not correct ", n == null);
		assertTrue("Next is not correct ", n == null);

		n = problem.getNode(0, 1).getNextRouteNode();
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getJobId() == 0);
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getStationId() == 0);

		n = problem.getNode(0, 1).getPreviousRouteNode();
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getJobId() == 0);
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getStationId() == 3);

		n = problem.getNode(0, 0).getNextRouteNode();
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getJobId() == 0);
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getStationId() == 2);

		n = problem.getNode(0, 0).getPreviousRouteNode();
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getJobId() == 0);
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getStationId() == 1);

		n = problem.getNode(0, 2).getNextRouteNode();
		assertTrue("Previous is not correct ", n == null);
		assertTrue("Previous is not correct ", n == null);

		n = problem.getNode(0, 2).getPreviousRouteNode();
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getJobId() == 0);
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getStationId() == 0);
		
		OperationIndexVO start2 = new OperationIndexVO(0, 1);
		OperationIndexVO end3 = new OperationIndexVO(0, 3);

		problem.exchangeOperations(start2, end3);

		// 1. test initial Job Operation
		n = problem.getInitialJobNode(0);
		assertTrue(" Initial Job Operation is not correct ", n.getOperation().getOperationIndex().getJobId() == 0);
		assertTrue(" Initial Job Operation is not correct ", n.getOperation().getOperationIndex().getStationId() == 1);

		// 2. test next end node
		n = problem.getNode(0, 1).getNextRouteNode();
		assertTrue("Next is not correct ", n.getOperation().getOperationIndex().getJobId() == 0);
		assertTrue("Next is not correct ", n.getOperation().getOperationIndex().getStationId() == 3);

		n = problem.getNode(0, 1).getPreviousRouteNode();
		assertTrue("Next is not correct ", n == null);
		assertTrue("Next is not correct ", n == null);

		n = problem.getNode(0, 3).getNextRouteNode();
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getJobId() == 0);
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getStationId() == 0);

		n = problem.getNode(0, 3).getPreviousRouteNode();
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getJobId() == 0);
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getStationId() == 1);

		n = problem.getNode(0, 0).getNextRouteNode();
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getJobId() == 0);
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getStationId() == 2);

		n = problem.getNode(0, 0).getPreviousRouteNode();
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getJobId() == 0);
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getStationId() == 3);

		n = problem.getNode(0, 2).getNextRouteNode();
		assertTrue("Previous is not correct ", n == null);
		assertTrue("Previous is not correct ", n == null);

		n = problem.getNode(0, 2).getPreviousRouteNode();
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getJobId() == 0);
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getStationId() == 0);
	}

	@Test
	public void testExchangeOperationsRoutePredecessor()
			throws InterruptedException {
		// same job. Exchange in the route
		OperationIndexVO start = new OperationIndexVO(0, 1);
		OperationIndexVO end = new OperationIndexVO(0, 0);

		problem.exchangeOperations(start, end);

		// 1. test initial Job Operation
		Node n = problem.getInitialJobNode(0);
		assertTrue(" Initial Job Operation is not correct ", n.getOperation().getOperationIndex().getJobId() == 0);
		assertTrue(" Initial Job Operation is not correct ", n.getOperation().getOperationIndex().getStationId() == 1);
		assertTrue(" for Initial Job previous must be null", n.getPreviousRouteNode() == null);

		// 2. test next end node
		n = problem.getNode(0, 1).getNextRouteNode();
		assertTrue("Next is not correct ", n.getOperation().getOperationIndex().getJobId() == 0);
		assertTrue("Next is not correct ", n.getOperation().getOperationIndex().getStationId() == 0);

		n = problem.getNode(0, 1).getPreviousRouteNode();
		assertTrue("Next is not correct ", n == null);
		assertTrue("Next is not correct ", n == null);

		n = problem.getNode(0, 0).getNextRouteNode();
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getJobId() == 0);
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getStationId() == 2);

		n = problem.getNode(0, 0).getPreviousRouteNode();
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getJobId() == 0);
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getStationId() == 1);

		n = problem.getNode(0, 2).getNextRouteNode();
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getJobId() == 0);
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getStationId() == 3);

		n = problem.getNode(0, 2).getPreviousRouteNode();
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getJobId() == 0);
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getStationId() == 0);

		n = problem.getNode(0, 3).getNextRouteNode();
		assertTrue("Previous is not correct ", n == null);
		assertTrue("Previous is not correct ", n == null);

		n = problem.getNode(0, 3).getPreviousRouteNode();
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getJobId() == 0);
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getStationId() == 2);
	}

	@Test
	public void testExchangeOperationsRouteSucessor2()
			throws InterruptedException {
		// same job. Exchange in the route
		OperationIndexVO start = new OperationIndexVO(0, 1);
		OperationIndexVO end = new OperationIndexVO(0, 3);

		problem.exchangeOperations(start, end);

		// 1. test initial Job Operation
		Node n = problem.getInitialJobNode(0);
		assertTrue(" Initial Job Operation is not correct ", n.getOperation().getOperationIndex().getJobId() == 0);
		assertTrue(" Initial Job Operation is not correct ", n.getOperation().getOperationIndex().getStationId() == 0);

		// 2. test next end node
		n = problem.getNode(0, 0).getNextRouteNode();
		assertTrue("Next is not correct ", n.getOperation().getOperationIndex().getJobId() == 0);
		assertTrue("Next is not correct ", n.getOperation().getOperationIndex().getStationId() == 3);
		
		n = problem.getNode(0, 0).getPreviousRouteNode();
		assertTrue("Next is not correct ", n == null);
		assertTrue("Next is not correct ", n == null);

		n = problem.getNode(0, 3).getNextRouteNode();
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getJobId() == 0);
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getStationId() == 2);
		
		n = problem.getNode(0, 3).getPreviousRouteNode();
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getJobId() == 0);
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getStationId() == 0);

		n = problem.getNode(0, 2).getNextRouteNode();
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getJobId() == 0);
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getStationId() == 1);
		
		n = problem.getNode(0, 2).getPreviousRouteNode();
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getJobId() == 0);
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getStationId() == 3);

		n = problem.getNode(0, 1).getNextRouteNode();
		assertTrue("Previous is not correct ", n == null);
		assertTrue("Previous is not correct ", n == null);
		
		n = problem.getNode(0, 1).getPreviousRouteNode();
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getJobId() == 0);
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getStationId() == 2);
	}

	@Test
	public void testExchangeOperationsSequence() throws InterruptedException {
		// same job. Exchange in the route
		OperationIndexVO start = new OperationIndexVO(0, 0);
		OperationIndexVO end = new OperationIndexVO(1, 0);

		problem.exchangeOperations(start, end);

		// 1. test initial Machine Operation
		Node n = problem.getInitialStationNode(0);

		assertTrue(" Initial Machine Operation is not correct ", n.getOperation().getOperationIndex().getJobId() == 1);
		assertTrue(" Initial Machine Operation is not correct ", n.getOperation().getOperationIndex().getStationId() == 0);
		assertTrue(" for Initial Machine previous must be null", n.getPreviousSequenceNode() == null);

		// 2. test next end node
		n = problem.getNode(1, 0).getNextSequenceNode();
		assertTrue("Next is not correct ", n.getOperation().getOperationIndex().getJobId() == 0);
		assertTrue("Next is not correct ", n.getOperation().getOperationIndex().getStationId() == 0);

		n = problem.getNode(0, 0).getNextSequenceNode();
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getJobId() == 2);
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getStationId() == 0);

		n = problem.getNode(2, 0).getNextSequenceNode();
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getJobId() == 3);
		assertTrue("Previous is not correct ", n.getOperation().getOperationIndex().getStationId() == 0);

		n = problem.getNode(3, 0).getNextSequenceNode();
		assertTrue("Previous is not correct ", n == null);
		assertTrue("Previous is not correct ", n == null);
	}

	@Test
	public void testExchangeOperationsMixed() throws InterruptedException {
		OperationIndexVO start = new OperationIndexVO(1, 2);
		OperationIndexVO end = new OperationIndexVO(2, 2);
		problem.exchangeOperations(start, end);

		start = new OperationIndexVO(1, 3);
		end = new OperationIndexVO(1, 2);
		problem.exchangeOperations(start, end);
	}
}