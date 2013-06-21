package graph.testcases;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import structure.IOperation;
import structure.impl.CriticalRoute;
import structure.impl.Graph;
import structure.impl.Node;
import structure.impl.Operation;
import structure.impl.Vector;


public class CriticalRouteTest {

	// -----------------------------------------------
	// Attributes
	// -----------------------------------------------
	
	private static Graph problemVector = new Graph(2,2);
	private static Graph problem1 = new Graph(2,2);
	
	
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
			routes = newVector.getCriticalPaths();
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
			routes = newVector.getCriticalPaths();
			System.out.println(routes);
			assertTrue(routes.size()==4);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


}
