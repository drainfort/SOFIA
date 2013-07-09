package neighborCalculator.tests;


import java.util.ArrayList;

import junit.framework.Assert;
import neighborCalculator.impl.N5_RandomInCriticalBlock;

import org.junit.Before;
import org.junit.Test;

import common.types.PairVO;

import structure.IOperation;
import structure.impl.CriticalPath;
import structure.impl.Graph;
import structure.impl.Operation;
import structure.impl.Vector;

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
			
			IOperation[][] problemGraphScenario1 = new IOperation[2][2]; 
			problemGraphScenario1[0][0] = new Operation(10, 0, 0);
			problemGraphScenario1[0][1] = new Operation(20, 0, 1);
			problemGraphScenario1[1][0] = new Operation(5, 1, 0);
			problemGraphScenario1[1][1] = new Operation(5, 1, 1);
			
			graphScenario1.setProblem(problemGraphScenario1);
			graphScenario1.scheduleOperation(problemGraphScenario1[0][0].getOperationIndex());
			graphScenario1.scheduleOperation(problemGraphScenario1[1][1].getOperationIndex());
			graphScenario1.scheduleOperation(problemGraphScenario1[1][0].getOperationIndex());
			graphScenario1.scheduleOperation(problemGraphScenario1[0][1].getOperationIndex());
			
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
	
	@Test
	public void testRandomInCriticalBlockTestScenario1Graph() throws Exception {
		ArrayList<PairVO> neighborhood = neighbor.calculateCompleteNeighborhood(graphScenario1);
		long totalPairs = 0;
		System.out.println(neighborhood);
		Graph newGraph = (Graph) graphScenario1.cloneStructure();
		ArrayList<CriticalPath> criticalRoutes = newGraph.getCriticalPaths();
		for (CriticalPath criticalRoute : criticalRoutes) {
			ArrayList<IOperation> currentRoute = criticalRoute.getRoute();
			ArrayList<ArrayList<IOperation>> blocks = new ArrayList<ArrayList<IOperation>>();
			
			for (int i = 0; i < currentRoute.size(); i++) {
				IOperation operationI = currentRoute.get(i);
				
				if(!alreadyConsidered(blocks, operationI)){
					ArrayList<IOperation> Block = new ArrayList<IOperation>();
					Block.add(operationI);
					
					// Including the machine blocks
					boolean finish = false;
					for (int j = i + 1; j < currentRoute.size() && !finish; j++) {
						IOperation operationJ = currentRoute.get(j);
						
						if((operationI.getOperationIndex().getStationId() == operationJ.getOperationIndex().getStationId())){
							Block.add(operationJ);
						}else{
							finish = true;
						}
					}
					
					// Including the job blocks
					finish = false;
					for (int j = i + 1; j < currentRoute.size() && !finish; j++) {
						IOperation operationJ = currentRoute.get(j);
						
						if((operationI.getOperationIndex().getJobId() == operationJ.getOperationIndex().getJobId())){
							Block.add(operationJ);
						}else{
							finish = true;
						}
					}
					blocks.add(Block);
				}
			}
			
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
			ArrayList<IOperation> currentRoute = criticalRoute.getRoute();
			ArrayList<ArrayList<IOperation>> blocks = new ArrayList<ArrayList<IOperation>>();
			ArrayList<ArrayList<IOperation>> blocks1 = new ArrayList<ArrayList<IOperation>>();
			blocks1.addAll(criticalRoute.getBlocks());
			for (int i = 0; i < currentRoute.size(); i++) {
				IOperation operationI = currentRoute.get(i);
				
				if(!alreadyConsidered(blocks, operationI)){
					ArrayList<IOperation> Block = new ArrayList<IOperation>();
					Block.add(operationI);
					
					// Including the machine blocks
					boolean finish = false;
					for (int j = i + 1; j < currentRoute.size() && !finish; j++) {
						IOperation operationJ = currentRoute.get(j);
						
						if((operationI.getOperationIndex().getStationId() == operationJ.getOperationIndex().getStationId())){
							Block.add(operationJ);
						}else{
							finish = true;
						}
					}
					
					// Including the job blocks
					finish = false;
					for (int j = i + 1; j < currentRoute.size() && !finish; j++) {
						IOperation operationJ = currentRoute.get(j);
						
						if((operationI.getOperationIndex().getJobId() == operationJ.getOperationIndex().getJobId())){
							Block.add(operationJ);
						}else{
							finish = true;
						}
					}
					blocks.add(Block);
				}
			}
			System.out.println(criticalRoute);
			System.out.println(blocks);
			System.out.println(blocks1);
			int n = 0;
			for (int i = 0; i < blocks.size(); i++) {
				ArrayList<IOperation> currentArray = blocks.get(i);
				long nPr = (factorial(currentArray.size()))/factorial(currentArray.size()-2);
				System.out.println(nPr);
				n += nPr;
			}
			totalPairs += n;
			System.out.println(totalPairs);
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
			ArrayList<IOperation> currentRoute = criticalRoute.getRoute();
			ArrayList<ArrayList<IOperation>> blocks = new ArrayList<ArrayList<IOperation>>();
			
			for (int i = 0; i < currentRoute.size(); i++) {
				IOperation operationI = currentRoute.get(i);
				
				if(!alreadyConsidered(blocks, operationI)){
					ArrayList<IOperation> Block = new ArrayList<IOperation>();
					Block.add(operationI);
					
					// Including the machine blocks
					boolean finish = false;
					for (int j = i + 1; j < currentRoute.size() && !finish; j++) {
						IOperation operationJ = currentRoute.get(j);
						
						if((operationI.getOperationIndex().getStationId() == operationJ.getOperationIndex().getStationId())){
							Block.add(operationJ);
						}else{
							finish = true;
						}
					}
					
					// Including the job blocks
					finish = false;
					for (int j = i + 1; j < currentRoute.size() && !finish; j++) {
						IOperation operationJ = currentRoute.get(j);
						
						if((operationI.getOperationIndex().getJobId() == operationJ.getOperationIndex().getJobId())){
							Block.add(operationJ);
						}else{
							finish = true;
						}
					}
					blocks.add(Block);
				}
			}
			
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
			ArrayList<IOperation> currentRoute = criticalRoute.getRoute();
			ArrayList<ArrayList<IOperation>> blocks = new ArrayList<ArrayList<IOperation>>();
			
			for (int i = 0; i < currentRoute.size(); i++) {
				IOperation operationI = currentRoute.get(i);
				
				if(!alreadyConsidered(blocks, operationI)){
					ArrayList<IOperation> Block = new ArrayList<IOperation>();
					Block.add(operationI);
					
					// Including the machine blocks
					boolean finish = false;
					for (int j = i + 1; j < currentRoute.size() && !finish; j++) {
						IOperation operationJ = currentRoute.get(j);
						
						if((operationI.getOperationIndex().getStationId() == operationJ.getOperationIndex().getStationId())){
							Block.add(operationJ);
						}else{
							finish = true;
						}
					}
					
					// Including the job blocks
					finish = false;
					for (int j = i + 1; j < currentRoute.size() && !finish; j++) {
						IOperation operationJ = currentRoute.get(j);
						
						if((operationI.getOperationIndex().getJobId() == operationJ.getOperationIndex().getJobId())){
							Block.add(operationJ);
						}else{
							finish = true;
						}
					}
					blocks.add(Block);
				}
			}
			
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
	
	// -----------------------------------------------
	// Utilities
	// -----------------------------------------------
	
	private boolean alreadyConsidered(ArrayList<ArrayList<IOperation>> array, IOperation operation) {
		for (int i = 0; i < array.size(); i++) {
			ArrayList<IOperation> currentSubArray = array.get(i);
			for (int j = 0; j < currentSubArray.size(); j++) {
				IOperation currentOperation = currentSubArray.get(j);
				if(operation.getOperationIndex().equals(currentOperation.getOperationIndex())){
					return true;
				}
			}
		}
		return false;
	}
	
	public static long factorial(int N){
        long multi = 1;
        for (int i = 1; i <= N; i++) {
            multi = multi * i;
        }
        return multi;
    }
		
//	@Test
//	public void testClone1() throws InterruptedException {
//		
//		IOperation[][] problem = new IOperation[2][2]; 
//		
//		Operation o1 = new Operation(10, 0, 0);
//		problem[0][0]= o1;
//		
//		Operation o4 = new Operation(5, 1, 1);
//		problem[1][1]= o4;
//		
//		Operation o3 = new Operation(5, 1, 0);
//		problem[1][0]= o3;
//		
//		Operation o2 = new Operation(20, 0, 1);
//		problem[0][1]= o2;
//		
//		problemVector.setProblem(problem);
//		problemVector.scheduleOperation(o1.getOperationIndex());
//		problemVector.scheduleOperation(o4.getOperationIndex());
//		problemVector.scheduleOperation(o3.getOperationIndex());
//		problemVector.scheduleOperation(o2.getOperationIndex());
//
//		
//		Graph newVector = (Graph) problemVector.cloneStructure();
//		ArrayList<CriticalPath> routes;
//		try {
//			routes = newVector.getCriticalPaths();
//			CriticalPath route = routes.get(0);
//			Operation first =(Operation) route.getRoute().get(0);
//			Operation last = (Operation) route.getRoute().get(route.getRoute().size()-1);
//			assertTrue(first.getOperationIndex().getStationId()==0 && first.getOperationIndex().getJobId()==0);
//			assertTrue(last.getOperationIndex().getStationId()==1 && last.getOperationIndex().getJobId()==0);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		
//		
//	}
//	@Test
//	public void testClone2() throws InterruptedException {
//		
//		IOperation[][] secondEscenario = new IOperation[2][2];
//		Operation o11 = new Operation(10, 0, 0);
//		secondEscenario[0][0]= o11;
//		
//		Operation o41 = new Operation(10, 1, 1);
//		secondEscenario[1][1]= o41;
//		
//		Operation o21 = new Operation(20, 0, 1);
//		secondEscenario[0][1]= o21;
//		
//		Operation o31 = new Operation(20, 1, 0);
//		secondEscenario[1][0]= o31;
//		problem1.setProblem(secondEscenario);
//		
//		problem1.scheduleOperation(o11.getOperationIndex());
//		problem1.scheduleOperation(o41.getOperationIndex());
//		problem1.scheduleOperation(o31.getOperationIndex());
//		problem1.scheduleOperation(o21.getOperationIndex());
//		
//		Graph newVector = (Graph) problem1.cloneStructure();
//		ArrayList<CriticalPath> routes;
//		try {
//			neighbor.calculateCompleteNeighborhood(problem1).contains(new PairVO(o41.getOperationIndex(), o21.getOperationIndex()));
//			routes = newVector.getCriticalPaths();
//			assertTrue(routes.size()==4);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//	}
//	
//	@Test
//	public void testClone3() throws InterruptedException {
//		Graph problem2 = new Graph(4,4);
//		
//		IOperation[][] escenario = new IOperation[4][4];
//		Operation o11 = new Operation(5, 0, 0);
//		escenario[0][0]= o11;
//		Operation o12 = new Operation(10, 0, 1);
//		escenario[0][1]= o12;
//		Operation o13 = new Operation(5, 0, 2);
//		escenario[0][2]= o13;
//		Operation o14 = new Operation(5, 0, 3);
//		escenario[0][3]= o14;
//		
//		Operation o21 = new Operation(5, 1, 0);
//		escenario[1][0]= o21;
//		Operation o22 = new Operation(10, 1, 1);
//		escenario[1][1]= o22;
//		Operation o23 = new Operation(5, 1, 2);
//		escenario[1][2]= o23;
//		Operation o24 = new Operation(5, 1, 3);
//		escenario[1][3]= o24;
//				
//		Operation o31 = new Operation(5, 2, 0);
//		escenario[2][0]= o31;
//		Operation o32 = new Operation(10, 2, 1);
//		escenario[2][1]= o32;
//		Operation o33 = new Operation(10, 2, 2);
//		escenario[2][2]= o33;
//		Operation o34 = new Operation(5, 2, 3);
//		escenario[2][3]= o34;
//		Operation o41 = new Operation(5, 3, 0);
//		escenario[3][0]= o41;
//		Operation o42 = new Operation(5, 3, 1);
//		escenario[3][1]= o42;
//		Operation o43 = new Operation(10, 3, 2);
//		escenario[3][2]= o43;
//		Operation o44 = new Operation(10, 3, 3);
//		escenario[3][3]= o44;
//		
//		
//		problem2.setProblem(escenario);
//		
//		problem2.scheduleOperation(o11.getOperationIndex());
//		problem2.scheduleOperation(o12.getOperationIndex());
//		problem2.scheduleOperation(o13.getOperationIndex());
//		problem2.scheduleOperation(o14.getOperationIndex());
//		problem2.scheduleOperation(o21.getOperationIndex());
//		problem2.scheduleOperation(o22.getOperationIndex());
//		problem2.scheduleOperation(o23.getOperationIndex());
//		problem2.scheduleOperation(o24.getOperationIndex());
//		problem2.scheduleOperation(o31.getOperationIndex());
//		problem2.scheduleOperation(o32.getOperationIndex());
//		problem2.scheduleOperation(o33.getOperationIndex());
//		problem2.scheduleOperation(o34.getOperationIndex());
//		problem2.scheduleOperation(o41.getOperationIndex());
//		problem2.scheduleOperation(o42.getOperationIndex());
//		problem2.scheduleOperation(o43.getOperationIndex());
//		problem2.scheduleOperation(o44.getOperationIndex());
//		
//		Graph newVector = (Graph) problem2.cloneStructure();
//		ArrayList<CriticalPath> routes;
//		
//		try {
//			ArrayList<PairVO> vecinos = neighbor.calculateCompleteNeighborhood(problem2);
//			routes = newVector.getCriticalPaths();
//			System.out.println(routes);
//			System.out.println(vecinos);
//			assertTrue(vecinos.contains(new PairVO(o11.getOperationIndex(), o12.getOperationIndex())));
//			assertTrue(vecinos.contains(new PairVO(o12.getOperationIndex(), o22.getOperationIndex())));
//			assertTrue(vecinos.contains(new PairVO(o22.getOperationIndex(), o32.getOperationIndex()))|| vecinos.contains(new PairVO(o32.getOperationIndex(), o22.getOperationIndex())));
//			assertTrue(neighbor.calculateCompleteNeighborhood(problem2).contains(new PairVO(o32.getOperationIndex(), o33.getOperationIndex())));
//			assertTrue(neighbor.calculateCompleteNeighborhood(problem2).contains(new PairVO(o33.getOperationIndex(), o43.getOperationIndex())));
//			assertTrue(neighbor.calculateCompleteNeighborhood(problem2).contains(new PairVO(o43.getOperationIndex(), o44.getOperationIndex())));
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//
//	}
//	
//	@Test
//	public void testClone4() throws InterruptedException {
//		Graph problem2 = new Graph(4,4);
//		
//		IOperation[][] escenario = new IOperation[4][4];
//		Operation o11 = new Operation(5, 0, 0);
//		escenario[0][0]= o11;
//		Operation o12 = new Operation(10, 0, 1);
//		escenario[0][1]= o12;
//		Operation o13 = new Operation(5, 0, 2);
//		escenario[0][2]= o13;
//		Operation o14 = new Operation(5, 0, 3);
//		escenario[0][3]= o14;
//		
//		Operation o21 = new Operation(5, 1, 0);
//		escenario[1][0]= o21;
//		Operation o22 = new Operation(10, 1, 1);
//		escenario[1][1]= o22;
//		Operation o23 = new Operation(10, 1, 2);
//		escenario[1][2]= o23;
//		Operation o24 = new Operation(10, 1, 3);
//		escenario[1][3]= o24;
//				
//		Operation o31 = new Operation(5, 2, 0);
//		escenario[2][0]= o31;
//		Operation o32 = new Operation(5, 2, 1);
//		escenario[2][1]= o32;
//		Operation o33 = new Operation(5, 2, 2);
//		escenario[2][2]= o33;
//		Operation o34 = new Operation(5, 2, 3);
//		escenario[2][3]= o34;
//		Operation o41 = new Operation(5, 3, 0);
//		escenario[3][0]= o41;
//		Operation o42 = new Operation(5, 3, 1);
//		escenario[3][1]= o42;
//		Operation o43 = new Operation(1, 3, 2);
//		escenario[3][2]= o43;
//		Operation o44 = new Operation(10, 3, 3);
//		escenario[3][3]= o44;
//		
//		
//		problem2.setProblem(escenario);
//		
//		problem2.scheduleOperation(o11.getOperationIndex());
//		problem2.scheduleOperation(o12.getOperationIndex());
//		problem2.scheduleOperation(o13.getOperationIndex());
//		problem2.scheduleOperation(o14.getOperationIndex());
//		problem2.scheduleOperation(o21.getOperationIndex());
//		problem2.scheduleOperation(o22.getOperationIndex());
//		problem2.scheduleOperation(o23.getOperationIndex());
//		problem2.scheduleOperation(o24.getOperationIndex());
//		problem2.scheduleOperation(o31.getOperationIndex());
//		problem2.scheduleOperation(o32.getOperationIndex());
//		problem2.scheduleOperation(o33.getOperationIndex());
//		problem2.scheduleOperation(o34.getOperationIndex());
//		problem2.scheduleOperation(o41.getOperationIndex());
//		problem2.scheduleOperation(o42.getOperationIndex());
//		problem2.scheduleOperation(o43.getOperationIndex());
//		problem2.scheduleOperation(o44.getOperationIndex());
//		
//		Graph newVector = (Graph) problem2.cloneStructure();
//		ArrayList<CriticalPath> routes;
//		try {
//			routes = newVector.getCriticalPaths();
//			System.out.println("rutas"+routes);
//			System.out.println("vecinos"+neighbor.calculateCompleteNeighborhood(problem2));
//			ArrayList<PairVO> vecinos = neighbor.calculateCompleteNeighborhood(problem2);
//			assertTrue(vecinos.contains(new PairVO(o11.getOperationIndex(), o12.getOperationIndex())));
//			assertTrue(vecinos.contains(new PairVO(o12.getOperationIndex(), o22.getOperationIndex())));
//			assertTrue(vecinos.contains(new PairVO(o22.getOperationIndex(), o23.getOperationIndex())));
//			assertTrue(vecinos.contains(new PairVO(o22.getOperationIndex(), o24.getOperationIndex())));
//			assertTrue(vecinos.contains(new PairVO(o23.getOperationIndex(), o24.getOperationIndex())));
//			assertTrue(vecinos.contains(new PairVO(o24.getOperationIndex(), o34.getOperationIndex())));
//			assertTrue(vecinos.contains(new PairVO(o24.getOperationIndex(), o44.getOperationIndex())));
//			assertTrue(vecinos.contains(new PairVO(o34.getOperationIndex(), o44.getOperationIndex())));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//
//	}


}
