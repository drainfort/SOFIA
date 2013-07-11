package neighborCalculator.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import neighborCalculator.impl.N3_AdjacentInCriticalPaths;
import neighborCalculator.impl.ShiftBlockAdjOnEnds;

import org.junit.Before;
import org.junit.Test;

import common.types.OperationIndexVO;
import common.types.PairVO;

import structure.IOperation;
import structure.impl.CriticalPath;
import structure.impl.Graph;
import structure.impl.Node;
import structure.impl.Operation;
import structure.impl.Vector;


public class _AdjacentOnEndsBlockCriticalRouteTest {

	// -----------------------------------------------
	// Attributes
	// -----------------------------------------------
	
	private static Graph problemVector = new Graph(2,2);
	private static Graph problem1 = new Graph(2,2);
	private static ShiftBlockAdjOnEnds neighbor = new ShiftBlockAdjOnEnds();
	
	// -----------------------------------------------
	// Setup scenarios
	// -----------------------------------------------
	
	@Before
	public void setUp() throws Exception {
		try {
						
			
		} catch (Exception e) {
			fail("Fail loading the input processing times file ");
		}
	}

	// -----------------------------------------------
	// Test clases
	// -----------------------------------------------
	
	@Test
	public void testClone1() throws InterruptedException {
		
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
			neighbor.calculateCompleteNeighborhood(problem1).contains(new PairVO(o41, o21));
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
			ArrayList<PairVO> vecinos = neighbor.calculateCompleteNeighborhood(problem2);
			assertTrue(vecinos.contains(new PairVO(o11, o12)));
			assertTrue(vecinos.contains(new PairVO(o12, o22)));
			assertTrue(vecinos.contains(new PairVO(o22, o32)));
			assertTrue(neighbor.calculateCompleteNeighborhood(problem2).contains(new PairVO(o32, o33)));
			assertTrue(neighbor.calculateCompleteNeighborhood(problem2).contains(new PairVO(o33, o43)));
			assertTrue(neighbor.calculateCompleteNeighborhood(problem2).contains(new PairVO(o43, o44)));
			routes = newVector.getCriticalPaths();
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
			System.out.println("vecinos"+neighbor.calculateCompleteNeighborhood(problem2));
			ArrayList<PairVO> vecinos = neighbor.calculateCompleteNeighborhood(problem2);
			assertTrue(vecinos.contains(new PairVO(o11, o12)));
			assertTrue(vecinos.contains(new PairVO(o12, o22)));
			assertTrue(vecinos.contains(new PairVO(o22, o23)));
			assertTrue(!vecinos.contains(new PairVO(o22, o24)));
			assertTrue(vecinos.contains(new PairVO(o23, o24)));
			assertTrue(vecinos.contains(new PairVO(o24, o34)));
			assertTrue(vecinos.contains(new PairVO(o34, o44)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}


}
