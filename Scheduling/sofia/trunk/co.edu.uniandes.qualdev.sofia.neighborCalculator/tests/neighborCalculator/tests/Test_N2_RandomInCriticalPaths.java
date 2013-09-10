package neighborCalculator.tests;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import common.types.OperationIndexVO;
import common.types.PairVO;
import neighborCalculator.impl.N2_RandomInCriticalPaths;
import structure.IOperation;
import structure.impl.CriticalPath;
import structure.impl.Graph;
import structure.impl.Vector;
import structure.impl.decoding.SequencialDecoding;

/**
 * Test case for the neighborhood calculator algorithm: N2_RandomInCriticalPath
 * 
 * @author David Mendez-Acuna
 */
public class Test_N2_RandomInCriticalPaths {
	
	// -----------------------------------------------
	// Attributes
	// -----------------------------------------------
	
	private N2_RandomInCriticalPaths neighborCalulator;
	
	private Graph graphScenario1;
	
	private Vector vectorScenario1;
	
	// -----------------------------------------------
	// Scenarios setup
	// -----------------------------------------------
	
	@Before
	public void setUpScenario1() throws Exception {
		neighborCalulator = new N2_RandomInCriticalPaths();
		
		// Loading the scenario 1 for the graph
		graphScenario1 = new Graph(2,2);
		
		OperationIndexVO[][] problemGraph = new OperationIndexVO[2][2]; 
		problemGraph[0][0] = new OperationIndexVO(10, 0, 0);
		problemGraph[0][1] = new OperationIndexVO(20, 0, 1);
		problemGraph[1][0] = new OperationIndexVO(5, 1, 0);
		problemGraph[1][1] = new OperationIndexVO(5, 1, 1);
		
		graphScenario1.setProblem(problemGraph);
		graphScenario1.scheduleOperation(problemGraph[0][0]);
		graphScenario1.scheduleOperation(problemGraph[1][1]);
		graphScenario1.scheduleOperation(problemGraph[1][0]);
		graphScenario1.scheduleOperation(problemGraph[0][1]);
		
		// Loading the scenario 1 for the vector
		vectorScenario1 = new Vector(2,2, new SequencialDecoding());
		
		OperationIndexVO[][] problemVector = new OperationIndexVO[2][2]; 
		problemVector[0][0] = new OperationIndexVO(10, 0, 0);
		problemVector[0][1] = new OperationIndexVO(20, 0, 1);
		problemVector[1][0] = new OperationIndexVO(5, 1, 0);
		problemVector[1][1] = new OperationIndexVO(5, 1, 1);
		
		vectorScenario1.setProblem(problemVector);
		vectorScenario1.scheduleOperation(problemVector[0][0]);
		vectorScenario1.scheduleOperation(problemVector[1][1]);
		vectorScenario1.scheduleOperation(problemVector[1][0]);
		vectorScenario1.scheduleOperation(problemVector[0][1]);
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
			ArrayList<IOperation> currentRoute = criticalRoute.getRoute();
			
			int n = currentRoute.size();
			int r = 2;
			long nCr = (factorial(n))/factorial(n-r);

			totalPairs += nCr;
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
			ArrayList<IOperation> currentRoute = criticalRoute.getRoute();
			
			int n = currentRoute.size();
			int r = 2;
			long nCr = (factorial(n))/factorial(n-r);

			totalPairs += nCr;
		}
		
		Assert.assertEquals("The amount of generated neighbor pairs is not correct. ", totalPairs, neighborhood.size());
	}
	
	// -----------------------------------------------
	// Utilities
	// -----------------------------------------------
	
	public static long factorial(int N) {
        long multi = 1;
        for (int i = 1; i <= N; i++) {
            multi = multi * i;
        }
        return multi;
    }
}