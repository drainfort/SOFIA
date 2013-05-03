package vector.testcases;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import structure.impl.CriticalRoute;
import structure.impl.Operation;
import structure.impl.Vector;


public class CriticalRouteTest {

	// -----------------------------------------------
	// Attributes
	// -----------------------------------------------
	
	private static Vector problem = new Vector(2,2);
	private static Vector problem1 = new Vector(2,2);


	
	
	// -----------------------------------------------
	// Setup scenarios
	// -----------------------------------------------
	
	@Before
	public void setUp() throws Exception {
		try {
			
			//Escenario 1
			
			Operation o1 = new Operation(10, 0, 0);
			/*problem.addOperation(o1);
			
			Operation o4 = new Operation(5, 1, 1);
			problem.addOperation(o4);
			
			Operation o3 = new Operation(5, 1, 0);
			problem.addOperation(o3);
			
			Operation o2 = new Operation(20, 0, 1);
			problem.addOperation(o2);
			
			
			//Escenario 2
			
			Operation o11 = new Operation(10, 0, 0);
			problem1.addOperation(o11);
			
			Operation o41 = new Operation(10, 1, 1);
			problem1.addOperation(o41);
			
			Operation o21 = new Operation(20, 0, 1);
			problem1.addOperation(o21);
			
			Operation o31 = new Operation(20, 1, 0);
			problem1.addOperation(o31);*/
			
			
			
			
		} catch (Exception e) {
			fail("Fail loading the input processing times file ");
		}
	}

	// -----------------------------------------------
	// Test clases
	// -----------------------------------------------
	
	@Test
	public void testClone1() throws InterruptedException {
		
		Vector newVector = (Vector) problem.cloneStructure();
		ArrayList<CriticalRoute> routes = newVector.getLongestRoutes();
		CriticalRoute route = routes.get(0);
		
		System.out.println(routes);
		Operation first =(Operation) route.getRoute().get(0);
		Operation last = (Operation) route.getRoute().get(route.getRoute().size()-1);
		System.out.println(first.getOperationIndex().getMachineId());
		
		assertTrue(first.getOperationIndex().getStationId()==0 && first.getOperationIndex().getJobId()==0);
		assertTrue(last.getOperationIndex().getStationId()==1 && last.getOperationIndex().getJobId()==0);
	}
	@Test
	public void testClone2() throws InterruptedException {
		
		Vector newVector = (Vector) problem1.cloneStructure();
		ArrayList<CriticalRoute> routes = newVector.getLongestRoutes();
		System.out.println(routes);
		assertTrue(routes.size()==4);
	}


}
