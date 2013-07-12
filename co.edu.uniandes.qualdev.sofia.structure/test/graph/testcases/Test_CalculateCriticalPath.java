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
 * Test cases for the functionality of calculating the critical paths on the graph
 * 
 * @author Jaime Romero
 * @author David Mendez-Acuna
 */
public class Test_CalculateCriticalPath {

	// -----------------------------------------------
	// Attributes
	// -----------------------------------------------
	
	private Graph graphScenario1 = new Graph(2,2);
	private Graph graphScenario2 = new Graph(2,2);
	
	// -----------------------------------------------
	// Setup scenarios
	// -----------------------------------------------
	
	@Before
	public void setUpScenarios() throws Exception {
		
		//Building the graphScenario1
		graphScenario1 = new Graph(2,2);
		
		OperationIndexVO[][] problem = new OperationIndexVO[2][2]; 
		problem[0][0] = new OperationIndexVO(10, 0, 0);
		problem[0][1] = new OperationIndexVO(20, 0, 1);
		problem[1][0] = new OperationIndexVO(5, 1, 0);
		problem[1][1] = new OperationIndexVO(5, 1, 1);
		
		graphScenario1.setProblem(problem);
		graphScenario1.scheduleOperation(problem[0][0]);
		graphScenario1.scheduleOperation(problem[1][1]);
		graphScenario1.scheduleOperation(problem[1][0]);
		graphScenario1.scheduleOperation(problem[0][1]);
		
		//Building the graphScenario2
		graphScenario2 = new Graph(2,2);
		
		OperationIndexVO[][] secondEscenario = new OperationIndexVO[2][2];
		secondEscenario[0][0] = new OperationIndexVO(10, 0, 0);
		secondEscenario[1][1] = new OperationIndexVO(10, 1, 1);
		secondEscenario[0][1] = new OperationIndexVO(20, 0, 1);
		secondEscenario[1][0] = new OperationIndexVO(20, 1, 0);
		
		graphScenario2.setProblem(secondEscenario);
		graphScenario2.scheduleOperation(secondEscenario[0][0]);
		graphScenario2.scheduleOperation(secondEscenario[1][1]);
		graphScenario2.scheduleOperation(secondEscenario[1][0]);
		graphScenario2.scheduleOperation(secondEscenario[0][1]);
	}

	// -----------------------------------------------
	// Test cases
	// -----------------------------------------------
	
	@Test
	public void testCriticalPathScenario1() throws Exception {
		Graph newGraph = (Graph) graphScenario1.cloneStructure();
		ArrayList<CriticalPath> routes = newGraph.getCriticalPaths();
		//System.out.println(routes);
		//Validation: The amount of critical paths is 1
		Assert.assertEquals("The amount of criticap paths should be 1", 1, routes.size());
		
		//Validation: The only critical path is correct
		CriticalPath criticalPath = routes.get(0);
		Assert.assertEquals("The size of the critical path should be 2", 2, criticalPath.getRoute().size());
		Assert.assertEquals("The first operation of the critical path should be (0,0)", new OperationIndexVO(0, 0), criticalPath.getRoute().get(0).getOperationIndex());
		Assert.assertEquals("The second operation of the critical path should be (0,1)", new OperationIndexVO(0, 1), criticalPath.getRoute().get(1).getOperationIndex());
	}
	
	@Test
	public void testCriticalPathScenario2() throws Exception {
		
		Graph newGraph = (Graph) graphScenario2.cloneStructure();
		ArrayList<CriticalPath> routes = newGraph.getCriticalPaths();
		
		//Validation: The amount of critical paths is 1
		Assert.assertEquals("The amount of criticap paths should be 4", 4, routes.size());
		
		//Validation: The given critical paths are correct
		ArrayList<OperationIndexVO> operationIndexesPath1 = new  ArrayList<OperationIndexVO>();
		operationIndexesPath1.add(new OperationIndexVO(0,0));
		operationIndexesPath1.add(new OperationIndexVO(0,1));
		Assert.assertEquals("The critical path (0,0), (0,1) should exist ", true, searchCriticalPath(routes, operationIndexesPath1));
		
		ArrayList<OperationIndexVO> operationIndexesPath2 = new  ArrayList<OperationIndexVO>();
		operationIndexesPath2.add(new OperationIndexVO(1,1));
		operationIndexesPath2.add(new OperationIndexVO(0,1));
		Assert.assertEquals("The critical path (1,1), (0,1) should exist ", true, searchCriticalPath(routes, operationIndexesPath2));
		
		ArrayList<OperationIndexVO> operationIndexesPath3 = new  ArrayList<OperationIndexVO>();
		operationIndexesPath3.add(new OperationIndexVO(0,0));
		operationIndexesPath3.add(new OperationIndexVO(1,0));
		Assert.assertEquals("The critical path (0,0), (1,0) should exist ", true, searchCriticalPath(routes, operationIndexesPath3));
		
		ArrayList<OperationIndexVO> operationIndexesPath4 = new  ArrayList<OperationIndexVO>();
		operationIndexesPath4.add(new OperationIndexVO(1,1));
		operationIndexesPath4.add(new OperationIndexVO(1,0));
		Assert.assertEquals("The critical path (1,1), (1,0) should exist ", true, searchCriticalPath(routes, operationIndexesPath4));
		
		ArrayList<OperationIndexVO> operationIndexesPath5 = new  ArrayList<OperationIndexVO>();
		operationIndexesPath5.add(new OperationIndexVO(1,1));
		operationIndexesPath5.add(new OperationIndexVO(0,0));
		Assert.assertEquals("The critical path (1,1), (0,0) should NOT exist ", false, searchCriticalPath(routes, operationIndexesPath5));
		
		ArrayList<OperationIndexVO> operationIndexesPath6 = new  ArrayList<OperationIndexVO>();
		operationIndexesPath6.add(new OperationIndexVO(1,1));
		operationIndexesPath6.add(new OperationIndexVO(0,0));
		Assert.assertEquals("The critical path (0,0), (1,1) should NOT exist ", false, searchCriticalPath(routes, operationIndexesPath6));
		
		ArrayList<OperationIndexVO> operationIndexesPath7 = new  ArrayList<OperationIndexVO>();
		operationIndexesPath7.add(new OperationIndexVO(1,1));
		operationIndexesPath7.add(new OperationIndexVO(0,0));
		Assert.assertEquals("The critical path (0,1), (1,1) should NOT exist ", false, searchCriticalPath(routes, operationIndexesPath7));
	}

	// -----------------------------------------------
	// Utilities
	// -----------------------------------------------
	
	/**
	 * Search for a critical path in a critical paths array. 
	 * @param routes
	 * @param operationIndexes
	 * @return
	 */
	private boolean searchCriticalPath(ArrayList<CriticalPath> routes, ArrayList<OperationIndexVO> operationIndexes) {
		for (CriticalPath criticalPath : routes) {
			
			ArrayList<IOperation> route = criticalPath.getRoute();
			if(route.size() != operationIndexes.size()){
				continue;
			}else{
				boolean routesComparison = true;
				for (int i = 0; i < route.size(); i++) {
					OperationIndexVO operationInRoute = route.get(i).getOperationIndex();
					OperationIndexVO operationInIndexes = operationIndexes.get(i);
					routesComparison = routesComparison && operationInRoute.equals(operationInIndexes);
				}
				if(routesComparison == true)
					return true;
			}
		}
		return false;
	}
}