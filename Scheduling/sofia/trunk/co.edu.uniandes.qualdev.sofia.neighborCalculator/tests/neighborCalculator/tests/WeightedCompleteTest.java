package neighborCalculator.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import neighborCalculator.impl.AdjacentShiftOnCriticalRoutes;
import neighborCalculator.impl.Random;

import org.junit.Before;
import org.junit.Test;

import common.types.PairVO;

import structure.IOperation;
import structure.impl.CriticalRoute;
import structure.impl.Graph;
import structure.impl.Node;
import structure.impl.Operation;
import structure.impl.Vector;


public class WeightedCompleteTest {

	// -----------------------------------------------
	// Attributes
	// -----------------------------------------------
	
	private static Graph problemVector = new Graph(2,2);
	private static Graph problem1 = new Graph(2,2);
	private static Random neighbor = new Random();
	
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
		
		IOperation[][] problem = new IOperation[2][2]; 
		
		Operation o1 = new Operation(10, 0, 0);
		problem[0][0]= o1;
		
		Operation o4 = new Operation(5, 1, 1);
		problem[1][1]= o4;
		
		Operation o3 = new Operation(5, 1, 0);
		problem[1][0]= o3;
		
		Operation o2 = new Operation(20, 0, 1);
		problem[0][1]= o2;
		
		problemVector.setProblem(problem);
		problemVector.scheduleOperation(o1.getOperationIndex());
		problemVector.scheduleOperation(o4.getOperationIndex());
		problemVector.scheduleOperation(o3.getOperationIndex());
		problemVector.scheduleOperation(o2.getOperationIndex());

		
		Graph newVector = (Graph) problemVector.cloneStructure();
		ArrayList<CriticalRoute> routes;
		try {
			System.out.println("vecinos"+neighbor.calculateCompleteNeighborhood(problemVector));
			routes = newVector.getLongestRoutes();
			CriticalRoute route = routes.get(0);
			System.out.println(routes);
			Operation first =(Operation) route.getRoute().get(0);
			Operation last = (Operation) route.getRoute().get(route.getRoute().size()-1);
			System.out.println(first.getOperationIndex().getMachineId());
			assertTrue(first.getOperationIndex().getStationId()==0 && first.getOperationIndex().getJobId()==0);
			assertTrue(last.getOperationIndex().getStationId()==1 && last.getOperationIndex().getJobId()==0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	@Test
	public void testClone2() throws InterruptedException {
		
		IOperation[][] secondEscenario = new IOperation[2][2];
		Operation o11 = new Operation(10, 0, 0);
		secondEscenario[0][0]= o11;
		
		Operation o41 = new Operation(10, 1, 1);
		secondEscenario[1][1]= o41;
		
		Operation o21 = new Operation(20, 0, 1);
		secondEscenario[0][1]= o21;
		
		Operation o31 = new Operation(20, 1, 0);
		secondEscenario[1][0]= o31;
		problem1.setProblem(secondEscenario);
		
		problem1.scheduleOperation(o11.getOperationIndex());
		problem1.scheduleOperation(o41.getOperationIndex());
		problem1.scheduleOperation(o31.getOperationIndex());
		problem1.scheduleOperation(o21.getOperationIndex());
		
		Graph newVector = (Graph) problem1.cloneStructure();
		ArrayList<CriticalRoute> routes;
		try {
			System.out.println("vecinos"+neighbor.calculateCompleteNeighborhood(problem1));
			neighbor.calculateCompleteNeighborhood(problem1).contains(new PairVO(o41.getOperationIndex(), o21.getOperationIndex()));
			routes = newVector.getLongestRoutes();
			System.out.println(routes);
			assertTrue(routes.size()==4);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testClone3() throws InterruptedException {
		Graph problem2 = new Graph(4,4);
		
		IOperation[][] escenario = new IOperation[4][4];
		Operation o11 = new Operation(5, 0, 0);
		escenario[0][0]= o11;
		Operation o12 = new Operation(10, 0, 1);
		escenario[0][1]= o12;
		Operation o13 = new Operation(5, 0, 2);
		escenario[0][2]= o13;
		Operation o14 = new Operation(5, 0, 3);
		escenario[0][3]= o14;
		
		Operation o21 = new Operation(5, 1, 0);
		escenario[1][0]= o21;
		Operation o22 = new Operation(10, 1, 1);
		escenario[1][1]= o22;
		Operation o23 = new Operation(5, 1, 2);
		escenario[1][2]= o23;
		Operation o24 = new Operation(5, 1, 3);
		escenario[1][3]= o24;
				
		Operation o31 = new Operation(5, 2, 0);
		escenario[2][0]= o31;
		Operation o32 = new Operation(10, 2, 1);
		escenario[2][1]= o32;
		Operation o33 = new Operation(10, 2, 2);
		escenario[2][2]= o33;
		Operation o34 = new Operation(5, 2, 3);
		escenario[2][3]= o34;
		Operation o41 = new Operation(5, 3, 0);
		escenario[3][0]= o41;
		Operation o42 = new Operation(5, 3, 1);
		escenario[3][1]= o42;
		Operation o43 = new Operation(10, 3, 2);
		escenario[3][2]= o43;
		Operation o44 = new Operation(10, 3, 3);
		escenario[3][3]= o44;
		
		
		problem2.setProblem(escenario);
		
		problem2.scheduleOperation(o11.getOperationIndex());
		problem2.scheduleOperation(o12.getOperationIndex());
		problem2.scheduleOperation(o13.getOperationIndex());
		problem2.scheduleOperation(o14.getOperationIndex());
		problem2.scheduleOperation(o21.getOperationIndex());
		problem2.scheduleOperation(o22.getOperationIndex());
		problem2.scheduleOperation(o23.getOperationIndex());
		problem2.scheduleOperation(o24.getOperationIndex());
		problem2.scheduleOperation(o31.getOperationIndex());
		problem2.scheduleOperation(o32.getOperationIndex());
		problem2.scheduleOperation(o33.getOperationIndex());
		problem2.scheduleOperation(o34.getOperationIndex());
		problem2.scheduleOperation(o41.getOperationIndex());
		problem2.scheduleOperation(o42.getOperationIndex());
		problem2.scheduleOperation(o43.getOperationIndex());
		problem2.scheduleOperation(o44.getOperationIndex());
		
		Graph newVector = (Graph) problem2.cloneStructure();
		ArrayList<CriticalRoute> routes;
		try {
			System.out.println("vecinos"+neighbor.calculateCompleteNeighborhood(problem2));
			ArrayList<PairVO> vecinos = neighbor.calculateCompleteNeighborhood(problem2);
			assertTrue(vecinos.contains(new PairVO(o11.getOperationIndex(), o12.getOperationIndex())));
			assertTrue(vecinos.contains(new PairVO(o11.getOperationIndex(), o41.getOperationIndex())));
			assertTrue(vecinos.contains(new PairVO(o11.getOperationIndex(), o13.getOperationIndex())));
			assertTrue(vecinos.contains(new PairVO(o11.getOperationIndex(), o14.getOperationIndex())));
			assertTrue(vecinos.contains(new PairVO(o41.getOperationIndex(), o12.getOperationIndex())));
			assertTrue(vecinos.contains(new PairVO(o41.getOperationIndex(), o13.getOperationIndex())));
			assertTrue(vecinos.contains(new PairVO(o41.getOperationIndex(), o14.getOperationIndex())));
			assertTrue(vecinos.contains(new PairVO(o23.getOperationIndex(), o32.getOperationIndex())));
			assertTrue(neighbor.calculateCompleteNeighborhood(problem2).contains(new PairVO(o32.getOperationIndex(), o33.getOperationIndex())));
			assertTrue(neighbor.calculateCompleteNeighborhood(problem2).contains(new PairVO(o33.getOperationIndex(), o43.getOperationIndex())));
			assertTrue(neighbor.calculateCompleteNeighborhood(problem2).contains(new PairVO(o43.getOperationIndex(), o44.getOperationIndex())));
			routes = newVector.getLongestRoutes();
			System.out.println("rutas"+routes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}


}
