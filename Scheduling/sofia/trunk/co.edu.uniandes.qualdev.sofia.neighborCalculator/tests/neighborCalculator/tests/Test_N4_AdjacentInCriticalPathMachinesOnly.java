package neighborCalculator.tests;

import java.util.ArrayList;

import junit.framework.Assert;

import neighborCalculator.impl.N4_AdjacentInCriticalPathMachinesOnly;

import org.junit.Before;
import org.junit.Test;

import common.types.OperationIndexVO;
import common.types.PairVO;

import structure.IOperation;
import structure.impl.CriticalPath;
import structure.impl.Graph;
import structure.impl.Vector;

/**
 * Test case for the neighborhood calculator algorithm: AjacentInCriticalPathMachinesOnly
 * 
 * @author Jaime Romero
 * @author David Mendez-Acuna
 */
public class Test_N4_AdjacentInCriticalPathMachinesOnly {

	// -----------------------------------------------
	// Attributes
	// -----------------------------------------------
	
	private Graph graphScenario1;
	
	private Graph graphScenario2;
	
	private Vector vectorScenario1;
	
	private Vector vectorScenario2;
	
	private N4_AdjacentInCriticalPathMachinesOnly neighbor;
	
	// -----------------------------------------------
	// Setup scenarios
	// -----------------------------------------------
	
	@Before
	public void setUp() throws Exception {
		 neighbor = new N4_AdjacentInCriticalPathMachinesOnly();
		
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
			
			vectorScenario2.setProblem(problemGraphScenario2);
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
	public void testAdjacentInCriticaPathMachinesOnlyTestScenario1Graph() throws Exception {
		ArrayList<PairVO> neighborhood = neighbor.calculateCompleteNeighborhood(graphScenario1);
		long totalPairs = 0;
		
		Graph newGraph = (Graph) graphScenario1.cloneStructure();
		ArrayList<CriticalPath> criticalRoutes = newGraph.getCriticalPaths();
		
		for (CriticalPath criticalRoute : criticalRoutes) {
			ArrayList<IOperation> currentRoute = criticalRoute.getRoute();
			ArrayList<ArrayList<IOperation>> setOfSameMachines = new ArrayList<ArrayList<IOperation>>();
			
			for (int i = 0; i < currentRoute.size(); i++) {
				IOperation operationI = currentRoute.get(i);
				
				if(!alreadyConsidered(setOfSameMachines, operationI)){
					ArrayList<IOperation> machineInI = new ArrayList<IOperation>();
					machineInI.add(operationI);
					
					boolean finish = false;
					for (int j = i + 1; j < currentRoute.size() && !finish; j++) {
						IOperation operationJ = currentRoute.get(j);
						
						if(operationI.getOperationIndex().getStationId() == operationJ.getOperationIndex().getStationId()){
							machineInI.add(operationJ);
						}else{
							finish = true;
						}
					}
					setOfSameMachines.add(machineInI);
				}
			}
			
			int n = 0;
			for (int i = 0; i < setOfSameMachines.size(); i++) {
				ArrayList<IOperation> currentArray = setOfSameMachines.get(i);
				if(currentArray.size() > 1){
					for (int j = 0; j < currentArray.size(); j++) {
						n += currentArray.size() - 1;
					}
				}
			}
			totalPairs += n;
		}
		Assert.assertEquals("The amount of generated neighbor pairs is not correct. ", totalPairs, neighborhood.size());
	}
	
	@Test
	public void testAdjacentInCriticaPathMachinesOnlyTestScenario1Vector() throws Exception {
		ArrayList<PairVO> neighborhood = neighbor.calculateCompleteNeighborhood(vectorScenario1);
		long totalPairs = 0;
		
		Vector newVector = (Vector) vectorScenario1;
		ArrayList<CriticalPath> criticalRoutes = newVector.getCriticalPaths();
		
		for (CriticalPath criticalRoute : criticalRoutes) {
			ArrayList<IOperation> currentRoute = criticalRoute.getRoute();
			ArrayList<ArrayList<IOperation>> setOfSameMachines = new ArrayList<ArrayList<IOperation>>();
			
			for (int i = 0; i < currentRoute.size(); i++) {
				IOperation operationI = currentRoute.get(i);
				
				if(!alreadyConsidered(setOfSameMachines, operationI)){
					ArrayList<IOperation> machineInI = new ArrayList<IOperation>();
					machineInI.add(operationI);
					
					boolean finish = false;
					for (int j = i + 1; j < currentRoute.size() && !finish; j++) {
						IOperation operationJ = currentRoute.get(j);
						
						if(operationI.getOperationIndex().getStationId() == operationJ.getOperationIndex().getStationId()){
							machineInI.add(operationJ);
						}else{
							finish = true;
						}
					}
					setOfSameMachines.add(machineInI);
				}
			}
			
			int n = 0;
			for (int i = 0; i < setOfSameMachines.size(); i++) {
				ArrayList<IOperation> currentArray = setOfSameMachines.get(i);
				if(currentArray.size() > 1){
					for (int j = 0; j < currentArray.size(); j++) {
						n += currentArray.size() - 1;
					}
				}
			}
			totalPairs += n;
		}
		Assert.assertEquals("The amount of generated neighbor pairs is not correct. ", totalPairs, neighborhood.size());
	}

	@Test
	public void testAdjacentInCriticaPathMachinesOnlyTestScenario2Graph() throws Exception {
		ArrayList<PairVO> neighborhood = neighbor.calculateCompleteNeighborhood(graphScenario2);
		long totalPairs = 0;
		
		Graph newGraph = (Graph) graphScenario2.cloneStructure();
		ArrayList<CriticalPath> criticalRoutes = newGraph.getCriticalPaths();
		
		for (CriticalPath criticalRoute : criticalRoutes) {
			ArrayList<IOperation> currentRoute = criticalRoute.getRoute();
			ArrayList<ArrayList<IOperation>> setOfSameMachines = new ArrayList<ArrayList<IOperation>>();
			
			for (int i = 0; i < currentRoute.size(); i++) {
				IOperation operationI = currentRoute.get(i);
				
				if(!alreadyConsidered(setOfSameMachines, operationI)){
					ArrayList<IOperation> machineInI = new ArrayList<IOperation>();
					machineInI.add(operationI);
					
					boolean finish = false;
					for (int j = i + 1; j < currentRoute.size() && !finish; j++) {
						IOperation operationJ = currentRoute.get(j);
						
						if(operationI.getOperationIndex().getStationId() == operationJ.getOperationIndex().getStationId()){
							machineInI.add(operationJ);
						}else{
							finish = true;
						}
					}
					setOfSameMachines.add(machineInI);
				}
			}
			
			int n = 0;
			for (int i = 0; i < setOfSameMachines.size(); i++) {
				ArrayList<IOperation> currentArray = setOfSameMachines.get(i);
				if(currentArray.size() > 1){
					n += currentArray.size() - 1;
				}
			}
			totalPairs += n;
		}
		Assert.assertEquals("The amount of generated neighbor pairs is not correct. ", totalPairs, neighborhood.size());
	}
	
	@Test
	public void testAdjacentInCriticaPathMachinesOnlyTestScenario2Vector() throws Exception {
		ArrayList<PairVO> neighborhood = neighbor.calculateCompleteNeighborhood(vectorScenario2);
		long totalPairs = 0;
	
		Vector newVector = (Vector) vectorScenario2;
		ArrayList<CriticalPath> criticalRoutes = newVector.getCriticalPaths();
		
		for (CriticalPath criticalRoute : criticalRoutes) {
			ArrayList<IOperation> currentRoute = criticalRoute.getRoute();
			ArrayList<ArrayList<IOperation>> setOfSameMachines = new ArrayList<ArrayList<IOperation>>();
			
			for (int i = 0; i < currentRoute.size(); i++) {
				IOperation operationI = currentRoute.get(i);
				
				if(!alreadyConsidered(setOfSameMachines, operationI)){
					ArrayList<IOperation> machineInI = new ArrayList<IOperation>();
					machineInI.add(operationI);
					
					boolean finish = false;
					for (int j = i + 1; j < currentRoute.size() && !finish; j++) {
						IOperation operationJ = currentRoute.get(j);
						
						if(operationI.getOperationIndex().getStationId() == operationJ.getOperationIndex().getStationId()){
							machineInI.add(operationJ);
						}else{
							finish = true;
						}
					}
					setOfSameMachines.add(machineInI);
				}
			}
			
			int n = 0;
			for (int i = 0; i < setOfSameMachines.size(); i++) {
				ArrayList<IOperation> currentArray = setOfSameMachines.get(i);
				if(currentArray.size() > 1){
						n += currentArray.size() - 1;
				}
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
}