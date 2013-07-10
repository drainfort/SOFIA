package graph.testcases;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import common.types.OperationIndexVO;

import structure.IOperation;
import structure.impl.CriticalPath;
import structure.impl.Graph;
import structure.impl.Operation;

/**
 * Test cases for the functionality of calculating the blocks on a critical path
 * 
 * @author Jaime Romero
 */
public class Test_CalculateBlocks {

	// -----------------------------------------------
	// Attributes
	// -----------------------------------------------
	
	private Graph graphScenario1 = new Graph(2,2);
	private Graph graphScenario2 = new Graph(2,2);
	private Graph graphScenario3;
	
	// -----------------------------------------------
	// Setup scenarios
	// -----------------------------------------------
	
	@Before
	public void setUpScenarios() throws Exception {
		
		//Building the graphScenario1
		graphScenario1 = new Graph(2,2);
		
		IOperation[][] problem = new IOperation[2][2]; 
		problem[0][0] = new Operation(10, 0, 0);
		problem[0][1] = new Operation(20, 0, 1);
		problem[1][0] = new Operation(5, 1, 0);
		problem[1][1] = new Operation(5, 1, 1);
		
		graphScenario1.setProblem(problem);
		graphScenario1.scheduleOperation(problem[0][0].getOperationIndex());
		graphScenario1.scheduleOperation(problem[1][1].getOperationIndex());
		graphScenario1.scheduleOperation(problem[1][0].getOperationIndex());
		graphScenario1.scheduleOperation(problem[0][1].getOperationIndex());
		
		//Building the graphScenario2
		graphScenario2 = new Graph(2,2);
		
		IOperation[][] secondEscenario = new IOperation[2][2];
		secondEscenario[0][0] = new Operation(10, 0, 0);
		secondEscenario[1][1] = new Operation(10, 1, 1);
		secondEscenario[0][1] = new Operation(20, 0, 1);
		secondEscenario[1][0] = new Operation(20, 1, 0);
		
		graphScenario2.setProblem(secondEscenario);
		graphScenario2.scheduleOperation(secondEscenario[0][0].getOperationIndex());
		graphScenario2.scheduleOperation(secondEscenario[1][1].getOperationIndex());
		graphScenario2.scheduleOperation(secondEscenario[1][0].getOperationIndex());
		graphScenario2.scheduleOperation(secondEscenario[0][1].getOperationIndex());
		
		graphScenario3 = new Graph(4,4);
		
		IOperation[][] problemGraphScenario3 = new IOperation[4][4];
		problemGraphScenario3[0][0] = new Operation(5, 0, 0);
		problemGraphScenario3[0][1] = new Operation(10, 0, 1);
		problemGraphScenario3[0][2] = new Operation(5, 0, 2);
		problemGraphScenario3[0][3] = new Operation(5, 0, 3);
		problemGraphScenario3[1][0] = new Operation(5, 1, 0);
		problemGraphScenario3[1][1] = new Operation(10, 1, 1);
		problemGraphScenario3[1][2] = new Operation(5, 1, 2);
		problemGraphScenario3[1][3] = new Operation(5, 1, 3);
		problemGraphScenario3[2][0] = new Operation(5, 2, 0);
		problemGraphScenario3[2][1] = new Operation(10, 2, 1);
		problemGraphScenario3[2][2] = new Operation(10, 2, 2);
		problemGraphScenario3[2][3] = new Operation(5, 2, 3);
		problemGraphScenario3[3][0] = new Operation(5, 3, 0);
		problemGraphScenario3[3][1] = new Operation(5, 3, 1);
		problemGraphScenario3[3][2] = new Operation(10, 3, 2);
		problemGraphScenario3[3][3] = new Operation(10, 3, 3);
		
		graphScenario3.setProblem(problemGraphScenario3);
		graphScenario3.scheduleOperation(problemGraphScenario3[0][0].getOperationIndex());
		graphScenario3.scheduleOperation(problemGraphScenario3[0][1].getOperationIndex());
		graphScenario3.scheduleOperation(problemGraphScenario3[0][2].getOperationIndex());
		graphScenario3.scheduleOperation(problemGraphScenario3[0][3].getOperationIndex());
		graphScenario3.scheduleOperation(problemGraphScenario3[1][0].getOperationIndex());
		graphScenario3.scheduleOperation(problemGraphScenario3[1][1].getOperationIndex());
		graphScenario3.scheduleOperation(problemGraphScenario3[1][2].getOperationIndex());
		graphScenario3.scheduleOperation(problemGraphScenario3[1][3].getOperationIndex());
		graphScenario3.scheduleOperation(problemGraphScenario3[2][0].getOperationIndex());
		graphScenario3.scheduleOperation(problemGraphScenario3[2][1].getOperationIndex());
		graphScenario3.scheduleOperation(problemGraphScenario3[2][2].getOperationIndex());
		graphScenario3.scheduleOperation(problemGraphScenario3[2][3].getOperationIndex());
		graphScenario3.scheduleOperation(problemGraphScenario3[3][0].getOperationIndex());
		graphScenario3.scheduleOperation(problemGraphScenario3[3][1].getOperationIndex());
		graphScenario3.scheduleOperation(problemGraphScenario3[3][2].getOperationIndex());
		graphScenario3.scheduleOperation(problemGraphScenario3[3][3].getOperationIndex());
	}

	// -----------------------------------------------
	// Test cases
	// -----------------------------------------------
	
	@Test
	public void testBlocksScenario1() throws Exception {
		Graph newGraph = (Graph) graphScenario1.cloneStructure();
		ArrayList<CriticalPath> routes = newGraph.getCriticalPaths();
		ArrayList<ArrayList<IOperation>> blocks = new ArrayList<ArrayList<IOperation>>();
		for(int i=0; i<routes.size();i++){
			blocks.addAll(routes.get(i).getBlocks());
		}
		Assert.assertEquals("The amount of blocks should be 1", 1, blocks.size());
		
		ArrayList<IOperation> operations1 = new ArrayList<IOperation>();
		operations1.add(new Operation(10,0,0));
		operations1.add(new Operation(30,0,1));
		
		Assert.assertTrue("Most have the block 0,0 - 0,1",blocks.contains(operations1));
		
	}
	
	@Test
	public void testBlocksScenario2() throws Exception {
		
		Graph newGraph = (Graph) graphScenario2.cloneStructure();
		ArrayList<CriticalPath> routes = newGraph.getCriticalPaths();
		ArrayList<ArrayList<IOperation>> blocks = new ArrayList<ArrayList<IOperation>>();
		for(int i=0; i<routes.size();i++){
			blocks.addAll(routes.get(i).getBlocks());
		}
		
		//Validation: The amount of critical paths is 1
		Assert.assertEquals("The amount of blocks should be 4", 4, blocks.size());
		
		//Validation: The given critical paths are correct
		ArrayList<IOperation> operations1 = new ArrayList<IOperation>();
		operations1.add(new Operation(10,1,1));
		operations1.add(new Operation(30,0,1));
		Assert.assertTrue("Most have the block 1,1 - 0,1",blocks.contains(operations1));
		
		ArrayList<IOperation> operations2 = new ArrayList<IOperation>();
		operations2.add(new Operation(10,0,0));
		operations2.add(new Operation(30,1,0));
		Assert.assertTrue("Most have the block 0,0 - 0,1",blocks.contains(operations2));
		
		ArrayList<IOperation> operations3 = new ArrayList<IOperation>();
		operations3.add(new Operation(10,0,0));
		operations3.add(new Operation(30,1,0));
		Assert.assertTrue("Most have the block 0,0 - 1,0",blocks.contains(operations3));
		
		ArrayList<IOperation> operations4 = new ArrayList<IOperation>();
		operations4.add(new Operation(10,1,1));
		operations4.add(new Operation(30,1,0));
		Assert.assertTrue("Most have the block 1,1 - 1,0",blocks.contains(operations3));
		
	}
	
	@Test
	public void testBlocksScenario3() throws Exception {
		
		Graph newGraph = (Graph) graphScenario3.cloneStructure();
		ArrayList<CriticalPath> routes = newGraph.getCriticalPaths();
		ArrayList<ArrayList<IOperation>> blocks = new ArrayList<ArrayList<IOperation>>();
		for(int i=0; i<routes.size();i++){
			blocks.addAll(routes.get(i).getBlocks());
		}
		
		//Validation: The amount of critical paths is 1
		Assert.assertEquals("The amount of blocks should be 5", 5, blocks.size());
		
		//Validation: The given critical paths are correct
		ArrayList<IOperation> operations1 = new ArrayList<IOperation>();
		operations1.add(new Operation(10,0,0));
		operations1.add(new Operation(30,0,1));
		Assert.assertTrue("Most have the block 0,0 - 0,1",blocks.contains(operations1));
		
		ArrayList<IOperation> operations2 = new ArrayList<IOperation>();
		operations2.add(new Operation(10,0,1));
		operations2.add(new Operation(30,1,1));
		operations2.add(new Operation(30,2,1));
		Assert.assertTrue("Most have the block 0,1 - 1,1 - 2,1",blocks.contains(operations2));
		
		ArrayList<IOperation> operations3 = new ArrayList<IOperation>();
		operations3.add(new Operation(10,2,1));
		operations3.add(new Operation(30,2,2));
		Assert.assertTrue("Most have the block 2,1 - 2,2",blocks.contains(operations3));
		
		ArrayList<IOperation> operations4 = new ArrayList<IOperation>();
		operations4.add(new Operation(10,2,2));
		operations4.add(new Operation(30,3,2));
		Assert.assertTrue("Most have the block 2,2 - 3,2",blocks.contains(operations3));
		
		ArrayList<IOperation> operations5 = new ArrayList<IOperation>();
		operations5.add(new Operation(10,3,2));
		operations5.add(new Operation(30,3,3));
		Assert.assertTrue("Most have the block 3,2 - 3,3",blocks.contains(operations5));
		
	}
	
}



