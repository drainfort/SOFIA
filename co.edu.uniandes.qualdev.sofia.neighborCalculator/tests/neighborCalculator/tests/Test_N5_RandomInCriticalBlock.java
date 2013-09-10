package neighborCalculator.tests;

import java.util.ArrayList;

import neighborCalculator.impl.N5_RandomInCriticalBlock;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import common.types.OperationIndexVO;
import common.types.PairVO;
import structure.IOperation;
import structure.impl.CriticalPath;
import structure.impl.Graph;
import structure.impl.Vector;
import structure.impl.decoding.SequencialDecoding;

/**
 * Test case for the neighborhood calculator algorithm: RandomInCriticalBlock
 * 
 * @author Jaime Romero
 * @author David Mendez-Acuna
 */
public class Test_N5_RandomInCriticalBlock {

	// -----------------------------------------------
	// Attributes
	// -----------------------------------------------
	
	private Graph graphScenario1;
	
	private Vector vectorScenario1;
	
	private Graph graphScenario2;
	
	private Vector vectorScenario2;
	
	private N5_RandomInCriticalBlock neighbor;
	
	// -----------------------------------------------
	// Setup scenarios
	// -----------------------------------------------
	
	@Before
	public void setUp() throws Exception {
		 neighbor = new N5_RandomInCriticalBlock();
		 
		// Loading graph scenario 1
			graphScenario1 = new Graph(2,2);
			
			OperationIndexVO[][] problemGraphScenario1 = new OperationIndexVO[2][2]; 
			problemGraphScenario1[0][0] = new OperationIndexVO(10, 0, 0);
			problemGraphScenario1[0][1] = new OperationIndexVO(20, 0, 1);
			problemGraphScenario1[1][0] = new OperationIndexVO(5, 1, 0);
			problemGraphScenario1[1][1] = new OperationIndexVO(5, 1, 1);
			
			graphScenario1.setProblem(problemGraphScenario1);
			graphScenario1.scheduleOperation(problemGraphScenario1[0][0]);
			graphScenario1.scheduleOperation(problemGraphScenario1[1][1]);
			graphScenario1.scheduleOperation(problemGraphScenario1[1][0]);
			graphScenario1.scheduleOperation(problemGraphScenario1[0][1]);
			
			// Loading graph scenario 2
			graphScenario2 = new Graph(4,4);
			
			OperationIndexVO[][] problemGraphScenario2 = new OperationIndexVO[4][4];
			problemGraphScenario2[0][0] = new OperationIndexVO(5, 0, 0);
			problemGraphScenario2[0][1] = new OperationIndexVO(10, 0, 1);
			problemGraphScenario2[0][2] = new OperationIndexVO(5, 0, 2);
			problemGraphScenario2[0][3] = new OperationIndexVO(5, 0, 3);
			problemGraphScenario2[1][0] = new OperationIndexVO(5, 1, 0);
			problemGraphScenario2[1][1] = new OperationIndexVO(10, 1, 1);
			problemGraphScenario2[1][2] = new OperationIndexVO(5, 1, 2);
			problemGraphScenario2[1][3] = new OperationIndexVO(5, 1, 3);
			problemGraphScenario2[2][0] = new OperationIndexVO(5, 2, 0);
			problemGraphScenario2[2][1] = new OperationIndexVO(10, 2, 1);
			problemGraphScenario2[2][2] = new OperationIndexVO(10, 2, 2);
			problemGraphScenario2[2][3] = new OperationIndexVO(5, 2, 3);
			problemGraphScenario2[3][0] = new OperationIndexVO(5, 3, 0);
			problemGraphScenario2[3][1] = new OperationIndexVO(5, 3, 1);
			problemGraphScenario2[3][2] = new OperationIndexVO(10, 3, 2);
			problemGraphScenario2[3][3] = new OperationIndexVO(10, 3, 3);
			
			graphScenario2.setProblem(problemGraphScenario2);
			graphScenario2.scheduleOperation(problemGraphScenario2[0][0]);
			graphScenario2.scheduleOperation(problemGraphScenario2[0][1]);
			graphScenario2.scheduleOperation(problemGraphScenario2[0][2]);
			graphScenario2.scheduleOperation(problemGraphScenario2[0][3]);
			graphScenario2.scheduleOperation(problemGraphScenario2[1][0]);
			graphScenario2.scheduleOperation(problemGraphScenario2[1][1]);
			graphScenario2.scheduleOperation(problemGraphScenario2[1][2]);
			graphScenario2.scheduleOperation(problemGraphScenario2[1][3]);
			graphScenario2.scheduleOperation(problemGraphScenario2[2][0]);
			graphScenario2.scheduleOperation(problemGraphScenario2[2][1]);
			graphScenario2.scheduleOperation(problemGraphScenario2[2][2]);
			graphScenario2.scheduleOperation(problemGraphScenario2[2][3]);
			graphScenario2.scheduleOperation(problemGraphScenario2[3][0]);
			graphScenario2.scheduleOperation(problemGraphScenario2[3][1]);
			graphScenario2.scheduleOperation(problemGraphScenario2[3][2]);
			graphScenario2.scheduleOperation(problemGraphScenario2[3][3]);
			
			// Loading graph scenario 1
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
			
			// Loading graph vector 2
			vectorScenario2 = new Vector(4,4, new SequencialDecoding());
			
			OperationIndexVO[][] problemVectorScenario2 = new OperationIndexVO[4][4];
			problemVectorScenario2[0][0] = new OperationIndexVO(5, 0, 0);
			problemVectorScenario2[0][1] = new OperationIndexVO(10, 0, 1);
			problemVectorScenario2[0][2] = new OperationIndexVO(5, 0, 2);
			problemVectorScenario2[0][3] = new OperationIndexVO(5, 0, 3);
			problemVectorScenario2[1][0] = new OperationIndexVO(5, 1, 0);
			problemVectorScenario2[1][1] = new OperationIndexVO(10, 1, 1);
			problemVectorScenario2[1][2] = new OperationIndexVO(5, 1, 2);
			problemVectorScenario2[1][3] = new OperationIndexVO(5, 1, 3);
			problemVectorScenario2[2][0] = new OperationIndexVO(5, 2, 0);
			problemVectorScenario2[2][1] = new OperationIndexVO(10, 2, 1);
			problemVectorScenario2[2][2] = new OperationIndexVO(10, 2, 2);
			problemVectorScenario2[2][3] = new OperationIndexVO(5, 2, 3);
			problemVectorScenario2[3][0] = new OperationIndexVO(5, 3, 0);
			problemVectorScenario2[3][1] = new OperationIndexVO(5, 3, 1);
			problemVectorScenario2[3][2] = new OperationIndexVO(10, 3, 2);
			problemVectorScenario2[3][3] = new OperationIndexVO(10, 3, 3);
			
			vectorScenario2.setProblem(problemVectorScenario2);
			vectorScenario2.scheduleOperation(problemGraphScenario2[0][0]);
			vectorScenario2.scheduleOperation(problemGraphScenario2[0][1]);
			vectorScenario2.scheduleOperation(problemGraphScenario2[0][2]);
			vectorScenario2.scheduleOperation(problemGraphScenario2[0][3]);
			vectorScenario2.scheduleOperation(problemGraphScenario2[1][0]);
			vectorScenario2.scheduleOperation(problemGraphScenario2[1][1]);
			vectorScenario2.scheduleOperation(problemGraphScenario2[1][2]);
			vectorScenario2.scheduleOperation(problemGraphScenario2[1][3]);
			vectorScenario2.scheduleOperation(problemGraphScenario2[2][0]);
			vectorScenario2.scheduleOperation(problemGraphScenario2[2][1]);
			vectorScenario2.scheduleOperation(problemGraphScenario2[2][2]);
			vectorScenario2.scheduleOperation(problemGraphScenario2[2][3]);
			vectorScenario2.scheduleOperation(problemGraphScenario2[3][0]);
			vectorScenario2.scheduleOperation(problemGraphScenario2[3][1]);
			vectorScenario2.scheduleOperation(problemGraphScenario2[3][2]);
			vectorScenario2.scheduleOperation(problemGraphScenario2[3][3]);
	}

	// -----------------------------------------------
	// Test cases
	// -----------------------------------------------
	
	@Test
	public void testRandomInCriticalBlockTestScenario1Graph() throws Exception {
		ArrayList<PairVO> neighborhood = neighbor.calculateCompleteNeighborhood(graphScenario1);
		long totalPairs = 0;
		Graph newGraph = (Graph) graphScenario1.cloneStructure();
		ArrayList<CriticalPath> criticalRoutes = newGraph.getCriticalPaths();
		for (CriticalPath criticalRoute : criticalRoutes) {
			ArrayList<ArrayList<IOperation>> blocks = new ArrayList<ArrayList<IOperation>>();
			blocks.addAll(criticalRoute.getBlocks());
			
			int n = 0;
			for (int i = 0; i < blocks.size(); i++) {
				ArrayList<IOperation> currentArray = blocks.get(i);
				long nPr = (factorial(currentArray.size()))/factorial(currentArray.size()-2);
				n += nPr;
			}
			totalPairs += n;
		}
		Assert.assertEquals("The amount of generated neighbor pairs is not correct. ", totalPairs, neighborhood.size());
	}
	
	@Test
	public void testRandomInCriticalBlockTestScenario1Vector() throws Exception {
		ArrayList<PairVO> neighborhood = neighbor.calculateCompleteNeighborhood(vectorScenario1);
		long totalPairs = 0;
		
		Vector newVector = (Vector) vectorScenario1;
		ArrayList<CriticalPath> criticalRoutes = newVector.getCriticalPaths();
		
		for (CriticalPath criticalRoute : criticalRoutes) {
			ArrayList<ArrayList<IOperation>> blocks = new ArrayList<ArrayList<IOperation>>();
			blocks.addAll(criticalRoute.getBlocks());
			int n = 0;
			for (int i = 0; i < blocks.size(); i++) {
				ArrayList<IOperation> currentArray = blocks.get(i);
				long nPr = (factorial(currentArray.size()))/factorial(currentArray.size()-2);
				n += nPr;
			}
			totalPairs += n;
		}
		Assert.assertEquals("The amount of generated neighbor pairs is not correct. ", totalPairs, neighborhood.size());
	}
	
	@Test
	public void testRandomInCriticalBlockTestScenario2Graph() throws Exception {
		ArrayList<PairVO> neighborhood = neighbor.calculateCompleteNeighborhood(graphScenario2);
		long totalPairs = 0;
		
		Graph newGraph = (Graph) graphScenario2.cloneStructure();
		ArrayList<CriticalPath> criticalRoutes = newGraph.getCriticalPaths();
		
		for (CriticalPath criticalRoute : criticalRoutes) {
			
			ArrayList<ArrayList<IOperation>> blocks = new ArrayList<ArrayList<IOperation>>();
			blocks.addAll(criticalRoute.getBlocks());
			int n = 0;
			for (int i = 0; i < blocks.size(); i++) {
				ArrayList<IOperation> currentArray = blocks.get(i);
				long nPr = (factorial(currentArray.size()))/factorial(currentArray.size()-2);
				n += nPr;
			}
			totalPairs += n;
		}
		Assert.assertEquals("The amount of generated neighbor pairs is not correct. ", totalPairs, neighborhood.size());
	}
	
	@Test
	public void testRandomInCriticalBlockTestScenario2Vector() throws Exception {
		ArrayList<PairVO> neighborhood = neighbor.calculateCompleteNeighborhood(vectorScenario2);
		long totalPairs = 0;
		Vector newVector = (Vector) vectorScenario2;
		ArrayList<CriticalPath> criticalRoutes = newVector.getCriticalPaths();
		
		for (CriticalPath criticalRoute : criticalRoutes) {
			ArrayList<ArrayList<IOperation>> blocks = new ArrayList<ArrayList<IOperation>>();
			blocks.addAll(criticalRoute.getBlocks());
			int n = 0;
			for (int i = 0; i < blocks.size(); i++) {
				ArrayList<IOperation> currentArray = blocks.get(i);
				long nPr = (factorial(currentArray.size()))/factorial(currentArray.size()-2);
				n += nPr;
			}
			totalPairs += n;
		}

		Assert.assertEquals("The amount of generated neighbor pairs is not correct. ", totalPairs, neighborhood.size());
	}
	
	
	public static long factorial(int N){
        long multi = 1;
        for (int i = 1; i <= N; i++) {
            multi = multi * i;
        }
        return multi;
    }		
}
