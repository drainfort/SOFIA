package neighborCalculator.tests;

import java.util.ArrayList;

import junit.framework.Assert;

import neighborCalculator.impl.N1_Random;

import org.junit.Before;
import org.junit.Test;

import common.types.PairVO;

import structure.IOperation;
import structure.impl.Graph;
import structure.impl.Operation;
import structure.impl.Vector;

/**
 * Test case for the neighborhood calculator algorithm: N1_Random
 * 
 * @author David Mendez-Acuna
 */
public class Test_N1_Random {

	// -----------------------------------------------
	// Attributes
	// -----------------------------------------------
	
	private N1_Random neighborCalulator;
	
	private Graph graphScenario1;
	
	private Vector vectorScenario1;
	
	// -----------------------------------------------
	// Scenarios setup
	// -----------------------------------------------
	
	@Before
	public void setUpScenario1() throws Exception {
		neighborCalulator = new N1_Random();
		
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
		
		// Loading the scenario 1 for the vector
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
	}
	
	
	// -----------------------------------------------
	// Test cases
	// -----------------------------------------------
	
	/**
	 * The size of the complete neighborhood in the graph should be vectorSize P 2. P = permutation.
	 * @throws Exception
	 */
	@Test
	public void testNeighborhoodSizeGraphScenario1() throws Exception{
		ArrayList<PairVO> neighborhood = neighborCalulator.calculateCompleteNeighborhood(graphScenario1);
		
		int n = graphScenario1.getOperations().size();
		int r = 2;
		
		long nPr = (factorial(n))/factorial(n-r);
		
		Assert.assertEquals("The amount of generated neighbor pairs is not correct. ", nPr, neighborhood.size());
	}
	
	/**
	 * The size of the complete neighborhood in the vector should be vectorSize P 2. P = permutation.
	 * @throws Exception
	 */
	@Test
	public void testNeighborhoodSizeVectorScenario1() throws Exception{
		ArrayList<PairVO> neighborhood = neighborCalulator.calculateCompleteNeighborhood(vectorScenario1);
		
		int n = vectorScenario1.getOperations().size();
		int r = 2;
		
		long nCr = (factorial(n))/factorial(n-r);
		
		Assert.assertEquals("The amount of generated neighbor pairs is not correct. ", nCr, neighborhood.size());
	}
	
	// -----------------------------------------------
	// Utilities
	// -----------------------------------------------
	
	public static long factorial(int N)
    {
        long multi = 1;
        for (int i = 1; i <= N; i++) {
            multi = multi * i;
        }
        return multi;
    }
}