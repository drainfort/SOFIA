package neighborCalculator.tests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import junit.framework.Assert;
import neighborCalculator.impl.ShiftBlockEndStartAnyCriticalRoute;

import org.junit.Before;
import org.junit.Test;

import structure.impl.CriticalPath;
import structure.impl.Graph;
import structure.impl.Operation;
import structure.impl.Vector;

import common.types.OperationIndexVO;
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
		vectorScenario1 = new Vector(2,2);
		
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
		vectorScenario2 = new Vector(4,4);
		
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
			
			for( int i=0; i<criticalRoute.getBlocks().size();i++){
				totalPairs+= (criticalRoute.getBlocks().get(i).size()-1)*2;
			}

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
			
			for( int i=0; i<criticalRoute.getBlocks().size();i++){
				totalPairs+= (criticalRoute.getBlocks().get(i).size()-1)*2;
			}

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
			
			for( int i=0; i<criticalRoute.getBlocks().size();i++){
				totalPairs+= (criticalRoute.getBlocks().get(i).size()-1)*2;
			}

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
			
			for( int i=0; i<criticalRoute.getBlocks().size();i++){
				totalPairs+= (criticalRoute.getBlocks().get(i).size()-1)*2;
			}

		}

		Assert.assertEquals("The amount of generated neighbor pairs is not correct. ", totalPairs, neighborhood.size());
	}
			
	
	@Test
	public void testClone1() throws InterruptedException {
		
		Graph problemVector = new Graph(2,2);
		
		OperationIndexVO[][] problem = new OperationIndexVO[2][2]; 
		
		OperationIndexVO o1 = new OperationIndexVO(10, 0, 0);
		problem[0][0]= o1;
		
		OperationIndexVO o4 = new OperationIndexVO(5, 1, 1);
		problem[1][1]= o4;
		
		OperationIndexVO o3 = new OperationIndexVO(5, 1, 0);
		problem[1][0]= o3;
		OperationIndexVO o2 = new OperationIndexVO(20, 0, 1);
		problem[0][1]= o2;
		
		problemVector.setProblem(problem);
		problemVector.scheduleOperation(o1);
		problemVector.scheduleOperation(o4);
		problemVector.scheduleOperation(o3);
		problemVector.scheduleOperation(o2);

		
		Graph newVector = (Graph) problemVector.cloneStructure();
		ArrayList<CriticalPath> routes;
		try {
			routes = newVector.getCriticalPaths();
			CriticalPath route = routes.get(0);
			Operation first =(Operation) route.getRoute().get(0);
			Operation last = (Operation) route.getRoute().get(route.getRoute().size()-1);
			assertTrue(first.getOperationIndex().getStationId()==0 && first.getOperationIndex().getJobId()==0);
			assertTrue(last.getOperationIndex().getStationId()==1 && last.getOperationIndex().getJobId()==0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	@Test
	public void testClone2() throws InterruptedException {
		
		Graph problem1 = new Graph(2,2);
		
		OperationIndexVO[][] secondEscenario = new OperationIndexVO[2][2];
		OperationIndexVO o11 = new OperationIndexVO(10, 0, 0);
		secondEscenario[0][0]= o11;
		
		OperationIndexVO o41 = new OperationIndexVO(10, 1, 1);
		secondEscenario[1][1]= o41;
		
		OperationIndexVO o21 = new OperationIndexVO(20, 0, 1);
		secondEscenario[0][1]= o21;
		
		OperationIndexVO o31 = new OperationIndexVO(20, 1, 0);
		secondEscenario[1][0]= o31;
		problem1.setProblem(secondEscenario);
		
		problem1.scheduleOperation(o11);
		problem1.scheduleOperation(o41);
		problem1.scheduleOperation(o31);
		problem1.scheduleOperation(o21);
		
		Graph newVector = (Graph) problem1.cloneStructure();
		ArrayList<CriticalPath> routes;
		try {
			neighborCalulator.calculateCompleteNeighborhood(problem1).contains(new PairVO(o41, o21));
			routes = newVector.getCriticalPaths();
			assertTrue(routes.size()==4);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testClone3() throws InterruptedException {
		Graph problem2 = new Graph(4,4);
		
		OperationIndexVO[][] escenario = new OperationIndexVO[4][4];
		OperationIndexVO o11 = new OperationIndexVO(5, 0, 0);
		escenario[0][0]= o11;
		OperationIndexVO o12 = new OperationIndexVO(10, 0, 1);
		escenario[0][1]= o12;
		OperationIndexVO o13 = new OperationIndexVO(5, 0, 2);
		escenario[0][2]= o13;
		OperationIndexVO o14 = new OperationIndexVO(5, 0, 3);
		escenario[0][3]= o14;
		
		OperationIndexVO o21 = new OperationIndexVO(5, 1, 0);
		escenario[1][0]= o21;
		OperationIndexVO o22 = new OperationIndexVO(10, 1, 1);
		escenario[1][1]= o22;
		OperationIndexVO o23 = new OperationIndexVO(5, 1, 2);
		escenario[1][2]= o23;
		OperationIndexVO o24 = new OperationIndexVO(5, 1, 3);
		escenario[1][3]= o24;
				
		OperationIndexVO o31 = new OperationIndexVO(5, 2, 0);
		escenario[2][0]= o31;
		OperationIndexVO o32 = new OperationIndexVO(10, 2, 1);
		escenario[2][1]= o32;
		OperationIndexVO o33 = new OperationIndexVO(10, 2, 2);
		escenario[2][2]= o33;
		OperationIndexVO o34 = new OperationIndexVO(5, 2, 3);
		escenario[2][3]= o34;
		OperationIndexVO o41 = new OperationIndexVO(5, 3, 0);
		escenario[3][0]= o41;
		OperationIndexVO o42 = new OperationIndexVO(5, 3, 1);
		escenario[3][1]= o42;
		OperationIndexVO o43 = new OperationIndexVO(10, 3, 2);
		escenario[3][2]= o43;
		OperationIndexVO o44 = new OperationIndexVO(10, 3, 3);
		escenario[3][3]= o44;
		
		
		problem2.setProblem(escenario);
		
		problem2.scheduleOperation(o11);
		problem2.scheduleOperation(o12);
		problem2.scheduleOperation(o13);
		problem2.scheduleOperation(o14);
		problem2.scheduleOperation(o21);
		problem2.scheduleOperation(o22);
		problem2.scheduleOperation(o23);
		problem2.scheduleOperation(o24);
		problem2.scheduleOperation(o31);
		problem2.scheduleOperation(o32);
		problem2.scheduleOperation(o33);
		problem2.scheduleOperation(o34);
		problem2.scheduleOperation(o41);
		problem2.scheduleOperation(o42);
		problem2.scheduleOperation(o43);
		problem2.scheduleOperation(o44);
		
		Graph newVector = (Graph) problem2.cloneStructure();
		ArrayList<CriticalPath> routes;
		
		try {
			ArrayList<PairVO> vecinos = neighborCalulator.calculateCompleteNeighborhood(problem2);
			routes = newVector.getCriticalPaths();
			System.out.println(routes);
			System.out.println(vecinos);
			assertTrue(vecinos.contains(new PairVO(o11, o12)));
			assertTrue(vecinos.contains(new PairVO(o12, o22)));
			assertTrue(vecinos.contains(new PairVO(o22, o32))|| vecinos.contains(new PairVO(o32, o22)));
			assertTrue(neighborCalulator.calculateCompleteNeighborhood(problem2).contains(new PairVO(o32, o33)));
			assertTrue(neighborCalulator.calculateCompleteNeighborhood(problem2).contains(new PairVO(o33, o43)));
			assertTrue(neighborCalulator.calculateCompleteNeighborhood(problem2).contains(new PairVO(o43, o44)));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}
	
	@Test
	public void testClone4() throws InterruptedException {
		Graph problem2 = new Graph(4,4);
		
		OperationIndexVO[][] escenario = new OperationIndexVO[4][4];
		OperationIndexVO o11 = new OperationIndexVO(5, 0, 0);
		escenario[0][0]= o11;
		OperationIndexVO o12 = new OperationIndexVO(10, 0, 1);
		escenario[0][1]= o12;
		OperationIndexVO o13 = new OperationIndexVO(5, 0, 2);
		escenario[0][2]= o13;
		OperationIndexVO o14 = new OperationIndexVO(5, 0, 3);
		escenario[0][3]= o14;
		
		OperationIndexVO o21 = new OperationIndexVO(5, 1, 0);
		escenario[1][0]= o21;
		OperationIndexVO o22 = new OperationIndexVO(10, 1, 1);
		escenario[1][1]= o22;
		OperationIndexVO o23 = new OperationIndexVO(10, 1, 2);
		escenario[1][2]= o23;
		OperationIndexVO o24 = new OperationIndexVO(10, 1, 3);
		escenario[1][3]= o24;
				
		OperationIndexVO o31 = new OperationIndexVO(5, 2, 0);
		escenario[2][0]= o31;
		OperationIndexVO o32 = new OperationIndexVO(5, 2, 1);
		escenario[2][1]= o32;
		OperationIndexVO o33 = new OperationIndexVO(5, 2, 2);
		escenario[2][2]= o33;
		OperationIndexVO o34 = new OperationIndexVO(5, 2, 3);
		escenario[2][3]= o34;
		OperationIndexVO o41 = new OperationIndexVO(5, 3, 0);
		escenario[3][0]= o41;
		OperationIndexVO o42 = new OperationIndexVO(5, 3, 1);
		escenario[3][1]= o42;
		OperationIndexVO o43 = new OperationIndexVO(1, 3, 2);
		escenario[3][2]= o43;
		OperationIndexVO o44 = new OperationIndexVO(10, 3, 3);
		escenario[3][3]= o44;
		
		
		problem2.setProblem(escenario);
		
		problem2.scheduleOperation(o11);
		problem2.scheduleOperation(o12);
		problem2.scheduleOperation(o13);
		problem2.scheduleOperation(o14);
		problem2.scheduleOperation(o21);
		problem2.scheduleOperation(o22);
		problem2.scheduleOperation(o23);
		problem2.scheduleOperation(o24);
		problem2.scheduleOperation(o31);
		problem2.scheduleOperation(o32);
		problem2.scheduleOperation(o33);
		problem2.scheduleOperation(o34);
		problem2.scheduleOperation(o41);
		problem2.scheduleOperation(o42);
		problem2.scheduleOperation(o43);
		problem2.scheduleOperation(o44);
		
		Graph newVector = (Graph) problem2.cloneStructure();
		ArrayList<CriticalPath> routes;
		try {
			routes = newVector.getCriticalPaths();
			System.out.println("rutas"+routes);
			System.out.println("vecinos"+neighborCalulator.calculateCompleteNeighborhood(problem2));
			ArrayList<PairVO> vecinos = neighborCalulator.calculateCompleteNeighborhood(problem2);
			assertTrue(vecinos.contains(new PairVO(o11, o12)));
			assertTrue(vecinos.contains(new PairVO(o12, o22)));
			assertTrue(vecinos.contains(new PairVO(o22, o23)));
			assertTrue(vecinos.contains(new PairVO(o22, o24)));
			assertTrue(vecinos.contains(new PairVO(o24, o23)));
			assertTrue(vecinos.contains(new PairVO(o24, o22)));
			assertTrue(vecinos.contains(new PairVO(o24, o34)));
			assertTrue(vecinos.contains(new PairVO(o24, o44)));
			assertTrue(vecinos.contains(new PairVO(o44, o24)));
			assertTrue(vecinos.contains(new PairVO(o44, o34)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

			
}
