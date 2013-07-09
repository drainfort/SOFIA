package neighborCalculator.tests;

import java.util.ArrayList;

import junit.framework.Assert;
import neighborCalculator.impl.ShiftBlockAdjOnEnds;
import neighborCalculator.impl.ShiftBlockEndStartAnyCriticalRoute;

import org.junit.Before;
import org.junit.Test;

import structure.IOperation;
import structure.impl.CriticalPath;
import structure.impl.Graph;
import structure.impl.Operation;
import structure.impl.Vector;

import common.types.PairVO;

public class Test_N7_ShiftBlockEndStarAnyCriticalRoute {
	// -----------------------------------------------
	// Attributes
	// -----------------------------------------------
	
	private ShiftBlockEndStartAnyCriticalRoute neighborCalulator;
	
	private Graph graphScenario1;
	
	private Vector vectorScenario1;
	
	private Graph graphScenario2;
	
	private Vector vectorScenario2;
	
	// -----------------------------------------------
	// Scenarios setup
	// -----------------------------------------------
	
	@Before
	public void setUpScenario1() throws Exception {
		neighborCalulator = new ShiftBlockEndStartAnyCriticalRoute();
		
		// Loading the scenario 1 for the graph
		graphScenario1 = new Graph(2,2);
		
		IOperation[][] problemGraph = new IOperation[2][2]; 
		problemGraph[0][0] = new Operation(10, 0, 0);
		problemGraph[0][1] = new Operation(20, 0, 1);
		problemGraph[1][0] = new Operation(5, 1, 0);
		problemGraph[1][1] = new Operation(5, 1, 1);
		
		graphScenario1.setProblem(problemGraph);
		graphScenario1.scheduleOperation(problemGraph[0][0].getOperationIndex());
		graphScenario1.scheduleOperation(problemGraph[1][1].getOperationIndex());
		graphScenario1.scheduleOperation(problemGraph[1][0].getOperationIndex());
		graphScenario1.scheduleOperation(problemGraph[0][1].getOperationIndex());
		
		// Loading graph scenario 2
		graphScenario2 = new Graph(4,4);
		
		IOperation[][] problemGraphScenario2 = new IOperation[4][4];
		problemGraphScenario2[0][0] = new Operation(5, 0, 0);
		problemGraphScenario2[0][1] = new Operation(10, 0, 1);
		problemGraphScenario2[0][2] = new Operation(5, 0, 2);
		problemGraphScenario2[0][3] = new Operation(5, 0, 3);
		problemGraphScenario2[1][0] = new Operation(5, 1, 0);
		problemGraphScenario2[1][1] = new Operation(10, 1, 1);
		problemGraphScenario2[1][2] = new Operation(5, 1, 2);
		problemGraphScenario2[1][3] = new Operation(5, 1, 3);
		problemGraphScenario2[2][0] = new Operation(5, 2, 0);
		problemGraphScenario2[2][1] = new Operation(10, 2, 1);
		problemGraphScenario2[2][2] = new Operation(10, 2, 2);
		problemGraphScenario2[2][3] = new Operation(5, 2, 3);
		problemGraphScenario2[3][0] = new Operation(5, 3, 0);
		problemGraphScenario2[3][1] = new Operation(5, 3, 1);
		problemGraphScenario2[3][2] = new Operation(10, 3, 2);
		problemGraphScenario2[3][3] = new Operation(10, 3, 3);
		
		graphScenario2.setProblem(problemGraphScenario2);
		graphScenario2.scheduleOperation(problemGraphScenario2[0][0].getOperationIndex());
		graphScenario2.scheduleOperation(problemGraphScenario2[0][1].getOperationIndex());
		graphScenario2.scheduleOperation(problemGraphScenario2[0][2].getOperationIndex());
		graphScenario2.scheduleOperation(problemGraphScenario2[0][3].getOperationIndex());
		graphScenario2.scheduleOperation(problemGraphScenario2[1][0].getOperationIndex());
		graphScenario2.scheduleOperation(problemGraphScenario2[1][1].getOperationIndex());
		graphScenario2.scheduleOperation(problemGraphScenario2[1][2].getOperationIndex());
		graphScenario2.scheduleOperation(problemGraphScenario2[1][3].getOperationIndex());
		graphScenario2.scheduleOperation(problemGraphScenario2[2][0].getOperationIndex());
		graphScenario2.scheduleOperation(problemGraphScenario2[2][1].getOperationIndex());
		graphScenario2.scheduleOperation(problemGraphScenario2[2][2].getOperationIndex());
		graphScenario2.scheduleOperation(problemGraphScenario2[2][3].getOperationIndex());
		graphScenario2.scheduleOperation(problemGraphScenario2[3][0].getOperationIndex());
		graphScenario2.scheduleOperation(problemGraphScenario2[3][1].getOperationIndex());
		graphScenario2.scheduleOperation(problemGraphScenario2[3][2].getOperationIndex());
		graphScenario2.scheduleOperation(problemGraphScenario2[3][3].getOperationIndex());
		
		// Loading graph scenario 1
		vectorScenario1 = new Vector(2,2);
		
		IOperation[][] problemVector = new IOperation[2][2]; 
		problemVector[0][0] = new Operation(10, 0, 0);
		problemVector[0][1] = new Operation(20, 0, 1);
		problemVector[1][0] = new Operation(5, 1, 0);
		problemVector[1][1] = new Operation(5, 1, 1);
		
		vectorScenario1.setProblem(problemVector);
		vectorScenario1.scheduleOperation(problemVector[0][0].getOperationIndex());
		vectorScenario1.scheduleOperation(problemVector[1][1].getOperationIndex());
		vectorScenario1.scheduleOperation(problemVector[1][0].getOperationIndex());
		vectorScenario1.scheduleOperation(problemVector[0][1].getOperationIndex());
		
		// Loading graph vector 2
		vectorScenario2 = new Vector(4,4);
		
		IOperation[][] problemVectorScenario2 = new IOperation[4][4];
		problemVectorScenario2[0][0] = new Operation(5, 0, 0);
		problemVectorScenario2[0][1] = new Operation(10, 0, 1);
		problemVectorScenario2[0][2] = new Operation(5, 0, 2);
		problemVectorScenario2[0][3] = new Operation(5, 0, 3);
		problemVectorScenario2[1][0] = new Operation(5, 1, 0);
		problemVectorScenario2[1][1] = new Operation(10, 1, 1);
		problemVectorScenario2[1][2] = new Operation(5, 1, 2);
		problemVectorScenario2[1][3] = new Operation(5, 1, 3);
		problemVectorScenario2[2][0] = new Operation(5, 2, 0);
		problemVectorScenario2[2][1] = new Operation(10, 2, 1);
		problemVectorScenario2[2][2] = new Operation(10, 2, 2);
		problemVectorScenario2[2][3] = new Operation(5, 2, 3);
		problemVectorScenario2[3][0] = new Operation(5, 3, 0);
		problemVectorScenario2[3][1] = new Operation(5, 3, 1);
		problemVectorScenario2[3][2] = new Operation(10, 3, 2);
		problemVectorScenario2[3][3] = new Operation(10, 3, 3);
		
		vectorScenario2.setProblem(problemVectorScenario2);
		vectorScenario2.scheduleOperation(problemGraphScenario2[0][0].getOperationIndex());
		vectorScenario2.scheduleOperation(problemGraphScenario2[0][1].getOperationIndex());
		vectorScenario2.scheduleOperation(problemGraphScenario2[0][2].getOperationIndex());
		vectorScenario2.scheduleOperation(problemGraphScenario2[0][3].getOperationIndex());
		vectorScenario2.scheduleOperation(problemGraphScenario2[1][0].getOperationIndex());
		vectorScenario2.scheduleOperation(problemGraphScenario2[1][1].getOperationIndex());
		vectorScenario2.scheduleOperation(problemGraphScenario2[1][2].getOperationIndex());
		vectorScenario2.scheduleOperation(problemGraphScenario2[1][3].getOperationIndex());
		vectorScenario2.scheduleOperation(problemGraphScenario2[2][0].getOperationIndex());
		vectorScenario2.scheduleOperation(problemGraphScenario2[2][1].getOperationIndex());
		vectorScenario2.scheduleOperation(problemGraphScenario2[2][2].getOperationIndex());
		vectorScenario2.scheduleOperation(problemGraphScenario2[2][3].getOperationIndex());
		vectorScenario2.scheduleOperation(problemGraphScenario2[3][0].getOperationIndex());
		vectorScenario2.scheduleOperation(problemGraphScenario2[3][1].getOperationIndex());
		vectorScenario2.scheduleOperation(problemGraphScenario2[3][2].getOperationIndex());
		vectorScenario2.scheduleOperation(problemGraphScenario2[3][3].getOperationIndex());
}
	
	// -----------------------------------------------
	// Test cases
	// -----------------------------------------------
	
	/**
	 * The size of the complete neighborhood in the graph should be criticalPathSize P 2. (P = permutation) For all the critical paths
	 * @throws Exception
	 */
	@Test
	public void testNeighborhoodSizeGraphScenario1() throws Exception{
		ArrayList<PairVO> neighborhood = neighborCalulator.calculateCompleteNeighborhood(graphScenario1);
		
		long totalPairs = 0;
		Graph newGraph = (Graph) graphScenario1.cloneStructure();
		ArrayList<CriticalPath> criticalRoutes = newGraph.getCriticalPaths();
		
		for (CriticalPath criticalRoute : criticalRoutes) {
			int number = criticalRoute.getBlocks().size()*2;
			totalPairs += number;
		}
		
		Assert.assertEquals("The amount of generated neighbor pairs is not correct. ", totalPairs, neighborhood.size());
	}
	
	/**
	 * The size of the complete neighborhood in the vector should be criticalPathSize P 2. (P = permutation) For all the critical paths
	 * @throws Exception
	 */
	@Test
	public void testNeighborhoodSizeVectorScenario1() throws Exception{
		ArrayList<PairVO> neighborhood = neighborCalulator.calculateCompleteNeighborhood(vectorScenario1);
		
		long totalPairs = 0;
		
		ArrayList<CriticalPath> criticalRoutes = vectorScenario1.getCriticalPaths();
		System.out.println("vector " + criticalRoutes.size());
		
		for (CriticalPath criticalRoute : criticalRoutes) {
			int number = criticalRoute.getBlocks().size()*2;
			totalPairs += number;
		}
		Assert.assertEquals("The amount of generated neighbor pairs is not correct. ", totalPairs, neighborhood.size());
	}
	
	@Test
	public void testRandomInCriticalBlockTestScenario2Graph() throws Exception {
		ArrayList<PairVO> neighborhood = neighborCalulator.calculateCompleteNeighborhood(graphScenario2);
		long totalPairs = 0;
		
		Graph newGraph = (Graph) graphScenario2.cloneStructure();
		ArrayList<CriticalPath> criticalRoutes = newGraph.getCriticalPaths();
		
		for (CriticalPath criticalRoute : criticalRoutes) {
			int number = criticalRoute.getBlocks().size()*2;
			totalPairs += number;
		}
		Assert.assertEquals("The amount of generated neighbor pairs is not correct. ", totalPairs, neighborhood.size());
	}
	
	@Test
	public void testRandomInCriticalBlockTestScenario2Vector() throws Exception {
		ArrayList<PairVO> neighborhood = neighborCalulator.calculateCompleteNeighborhood(vectorScenario2);
		long totalPairs = 0;
		Vector newVector = (Vector) vectorScenario2;
		ArrayList<CriticalPath> criticalRoutes = newVector.getCriticalPaths();
		
		for (CriticalPath criticalRoute : criticalRoutes) {
			int number = criticalRoute.getBlocks().size()*2;
			totalPairs += number;
		}

		Assert.assertEquals("The amount of generated neighbor pairs is not correct. ", totalPairs, neighborhood.size());
	}
			
			
}
