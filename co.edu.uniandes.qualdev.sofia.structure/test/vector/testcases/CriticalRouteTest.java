package vector.testcases;

import java.util.ArrayList;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import common.types.OperationIndexVO;

import structure.IOperation;
import structure.impl.CriticalRoute;
import structure.impl.Operation;
import structure.impl.Vector;

/**
 * Test cases for the functionality of calculating the critical paths on the vector
 * 
 * @author Jaime Romero
 * @author David Mendez-Acuna
 */
public class CriticalRouteTest {

	// -----------------------------------------------
	// Attributes
	// -----------------------------------------------
	
	private static Vector vectorScenario1 = new Vector(2,2);
	private static Vector vectorScenario2 = new Vector(2,2);
	
	// -----------------------------------------------
	// Setup scenarios
	// -----------------------------------------------
	
	@Before
	public void setUp() throws Exception {
		
			//Building scenario 1
			IOperation[][] problem = new IOperation[2][2]; 
			problem[0][0] = new Operation(10, 0, 0);
			problem[0][1] = new Operation(20, 0, 1);
			problem[1][0] = new Operation(5, 1, 0);
			problem[1][1] = new Operation(5, 1, 1);
			
			vectorScenario1.setProblem(problem);
			vectorScenario1.scheduleOperation(problem[0][0].getOperationIndex());
			vectorScenario1.scheduleOperation(problem[1][1].getOperationIndex());
			vectorScenario1.scheduleOperation(problem[1][0].getOperationIndex());
			vectorScenario1.scheduleOperation(problem[0][1].getOperationIndex());
			
			//Building scenario 2
			IOperation[][] secondEscenario = new IOperation[2][2];
			secondEscenario[0][0] = new Operation(10, 0, 0);
			secondEscenario[1][1] = new Operation(10, 1, 1);
			secondEscenario[0][1] = new Operation(20, 0, 1);
			secondEscenario[1][0] = new Operation(20, 1, 0);
			
			vectorScenario2.setProblem(secondEscenario);
			vectorScenario2.scheduleOperation(secondEscenario[0][0].getOperationIndex());
			vectorScenario2.scheduleOperation(secondEscenario[1][1].getOperationIndex());
			vectorScenario2.scheduleOperation(secondEscenario[1][0].getOperationIndex());
			vectorScenario2.scheduleOperation(secondEscenario[0][1].getOperationIndex());
	}

	// -----------------------------------------------
	// Test cases
	// -----------------------------------------------
	
	@Test
	public void testCriticalPathScenario1() throws InterruptedException {
		
		Vector newVector = (Vector) vectorScenario1.cloneStructure();
		ArrayList<CriticalRoute> routes = newVector.getCriticalPaths();
		
		//Validation: The amount of critical paths is 1
		Assert.assertEquals("The amount of criticap paths should be 1", 1, routes.size());
		
		//Validation: The only critical path is correct
		CriticalRoute criticalPath = routes.get(0);
		Assert.assertEquals("The size of the critical path should be 2", 2, criticalPath.getRoute().size());
		Assert.assertEquals("The first operation of the critical path should be (0,0)", new OperationIndexVO(0, 0), criticalPath.getRoute().get(0).getOperationIndex());
		Assert.assertEquals("The second operation of the critical path should be (0,1)", new OperationIndexVO(0, 1), criticalPath.getRoute().get(1).getOperationIndex());
	}
	
	@Test
	public void testCriticalPathScenario2() throws InterruptedException {
		
		Vector newVector = (Vector) vectorScenario2.cloneStructure();
		ArrayList<CriticalRoute> routes = newVector.getCriticalPaths();
		
		//Validation: The amount of critical paths is 4
		Assert.assertEquals("The amount of criticap paths should be 4", 4, routes.size());
	}
}