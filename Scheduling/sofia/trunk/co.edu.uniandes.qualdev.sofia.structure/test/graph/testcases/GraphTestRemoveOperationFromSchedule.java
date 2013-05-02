package graph.testcases;

import static org.junit.Assert.*;
import graph.scenarios.GraphScenariosFactory;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import structure.impl.Graph;

import common.types.OperationIndexVO;
import common.utils.MatrixUtils;

public class GraphTestRemoveOperationFromSchedule {

	// -----------------------------------------------
	// Attributes
	// -----------------------------------------------
	
	private Graph problem;

	// -----------------------------------------------
	// Setup scenarios
	// -----------------------------------------------
	
	@Before
	public void setUp() throws Exception {
		try {
			problem = GraphScenariosFactory.buildSimple04x04Problem();
		} catch (Exception e) {
			fail("Fail loading the input processing times file ");
		}
	}

	// -----------------------------------------------
	// Test clases
	// -----------------------------------------------
	
	@Test
	public void testScheduleInitialState() {
		Assert.assertEquals("Initial state incorrect", 0, problem.getJobRoute(0).size());
		Assert.assertEquals("Initial state incorrect", 0, problem.getJobRoute(1).size());
		Assert.assertEquals("Initial state incorrect", 0, problem.getJobRoute(2).size());
		
		Assert.assertEquals("Initial state incorrect", 0, problem.getStationSequence(0).size());
		Assert.assertEquals("Initial state incorrect", 0, problem.getStationSequence(1).size());
		Assert.assertEquals("Initial state incorrect", 0, problem.getStationSequence(2).size());
	}
	
	@Test
	public void testScheduleInitialOperation() {
		problem.scheduleOperation(new OperationIndexVO(0, 0));
		Assert.assertEquals("Initial state incorrect", 1, problem.getStationSequence(0).size());
		Assert.assertEquals("Initial state incorrect", 1, problem.getJobRoute(0).size());
		
		problem.scheduleOperation(new OperationIndexVO(1, 1));
		Assert.assertEquals("Initial state incorrect", 1, problem.getStationSequence(1).size());
		Assert.assertEquals("Initial state incorrect", 1, problem.getJobRoute(1).size());
		
		problem.scheduleOperation(new OperationIndexVO(2, 2));
		Assert.assertEquals("Initial state incorrect", 1, problem.getStationSequence(2).size());
		Assert.assertEquals("Initial state incorrect", 1, problem.getJobRoute(2).size());
		
		problem.removeOperationFromSchedule(new OperationIndexVO(2, 2));
		Assert.assertEquals("Initial state incorrect", 1, problem.getStationSequence(1).size());
		Assert.assertEquals("Initial state incorrect", 1, problem.getJobRoute(1).size());
		
		problem.removeOperationFromSchedule(new OperationIndexVO(1, 1));
		Assert.assertEquals("Initial state incorrect", 1, problem.getStationSequence(0).size());
		Assert.assertEquals("Initial state incorrect", 1, problem.getJobRoute(0).size());
		
		problem.removeOperationFromSchedule(new OperationIndexVO(0, 0));
		Assert.assertEquals("Initial state incorrect", 0, problem.getJobRoute(0).size());
		Assert.assertEquals("Initial state incorrect", 0, problem.getJobRoute(1).size());
		Assert.assertEquals("Initial state incorrect", 0, problem.getJobRoute(2).size());
		
		Assert.assertEquals("Initial state incorrect", 0, problem.getStationSequence(0).size());
		Assert.assertEquals("Initial state incorrect", 0, problem.getStationSequence(1).size());
		Assert.assertEquals("Initial state incorrect", 0, problem.getStationSequence(2).size());
	}
	
	@Test
	public void testScheduleMiddleOperation() {
		
		//Scheduling
		problem.scheduleOperation(new OperationIndexVO(0, 0));
		Assert.assertEquals("Initial state incorrect", problem.getStationSequence(0).size(), 1);
		Assert.assertEquals("Initial state incorrect", problem.getJobRoute(0).size(), 1);
		
		problem.scheduleOperation(new OperationIndexVO(0, 1));
		Assert.assertEquals("Initial state incorrect", problem.getJobRoute(0).size(), 2);
		Assert.assertEquals("Initial state incorrect", problem.getStationSequence(1).size(), 1);
		
		problem.scheduleOperation(new OperationIndexVO(0, 2));
		Assert.assertEquals("Initial state incorrect", problem.getJobRoute(0).size(), 3);
		Assert.assertEquals("Initial state incorrect", problem.getStationSequence(1).size(), 1);
		Assert.assertEquals("Initial state incorrect", problem.getStationSequence(2).size(), 1);
		
		problem.scheduleOperation(new OperationIndexVO(1, 2));
		Assert.assertEquals("Initial state incorrect", problem.getJobRoute(1).size(), 1);
		Assert.assertEquals("Initial state incorrect", problem.getStationSequence(1).size(), 1);
		Assert.assertEquals("Initial state incorrect", problem.getStationSequence(2).size(), 2);
		
		problem.scheduleOperation(new OperationIndexVO(1, 1));
		Assert.assertEquals("Initial state incorrect", problem.getJobRoute(1).size(), 2);
		Assert.assertEquals("Initial state incorrect", problem.getStationSequence(1).size(), 2);
		Assert.assertEquals("Initial state incorrect", problem.getStationSequence(2).size(), 2);
		
		problem.scheduleOperation(new OperationIndexVO(1, 0));
		Assert.assertEquals("Initial state incorrect", problem.getJobRoute(1).size(), 3);
		Assert.assertEquals("Initial state incorrect", problem.getStationSequence(0).size(), 2);
		Assert.assertEquals("Initial state incorrect", problem.getStationSequence(1).size(), 2);
		Assert.assertEquals("Initial state incorrect", problem.getStationSequence(2).size(), 2);
		
		problem.scheduleOperation(new OperationIndexVO(2, 1));
		Assert.assertEquals("Initial state incorrect", problem.getJobRoute(2).size(), 1);
		Assert.assertEquals("Initial state incorrect", problem.getStationSequence(0).size(), 2);
		Assert.assertEquals("Initial state incorrect", problem.getStationSequence(1).size(), 3);
		Assert.assertEquals("Initial state incorrect", problem.getStationSequence(2).size(), 2);
		
		problem.scheduleOperation(new OperationIndexVO(2, 2));
		Assert.assertEquals("Initial state incorrect", problem.getJobRoute(2).size(), 2);
		Assert.assertEquals("Initial state incorrect", problem.getStationSequence(0).size(), 2);
		Assert.assertEquals("Initial state incorrect", problem.getStationSequence(1).size(), 3);
		Assert.assertEquals("Initial state incorrect", problem.getStationSequence(2).size(), 3);
		
		problem.scheduleOperation(new OperationIndexVO(2, 0));
		Assert.assertEquals("Initial state incorrect", problem.getJobRoute(2).size(), 3);
		Assert.assertEquals("Initial state incorrect", problem.getStationSequence(0).size(), 3);
		Assert.assertEquals("Initial state incorrect", problem.getStationSequence(1).size(), 3);
		Assert.assertEquals("Initial state incorrect", problem.getStationSequence(2).size(), 3);
		
		//Undo the schedule
		problem.removeOperationFromSchedule(new OperationIndexVO(2, 0));
		Assert.assertEquals("Initial state incorrect", problem.getJobRoute(2).size(), 2);
		Assert.assertEquals("Initial state incorrect", problem.getStationSequence(0).size(), 2);
		Assert.assertEquals("Initial state incorrect", problem.getStationSequence(1).size(), 3);
		Assert.assertEquals("Initial state incorrect", problem.getStationSequence(2).size(), 3);
		
		problem.removeOperationFromSchedule(new OperationIndexVO(2, 2));
		Assert.assertEquals("Initial state incorrect", problem.getJobRoute(2).size(), 1);
		Assert.assertEquals("Initial state incorrect", problem.getStationSequence(0).size(), 2);
		Assert.assertEquals("Initial state incorrect", problem.getStationSequence(1).size(), 3);
		Assert.assertEquals("Initial state incorrect", problem.getStationSequence(2).size(), 2);
		
		problem.removeOperationFromSchedule(new OperationIndexVO(2, 1));
		Assert.assertEquals("Initial state incorrect", problem.getJobRoute(1).size(), 3);
		Assert.assertEquals("Initial state incorrect", problem.getStationSequence(0).size(), 2);
		Assert.assertEquals("Initial state incorrect", problem.getStationSequence(1).size(), 2);
		Assert.assertEquals("Initial state incorrect", problem.getStationSequence(2).size(), 2);
		
		problem.removeOperationFromSchedule(new OperationIndexVO(1, 0));
		Assert.assertEquals("Initial state incorrect", problem.getJobRoute(1).size(), 2);
		Assert.assertEquals("Initial state incorrect", problem.getStationSequence(1).size(), 2);
		Assert.assertEquals("Initial state incorrect", problem.getStationSequence(2).size(), 2);
		
		problem.removeOperationFromSchedule(new OperationIndexVO(1, 1));
		Assert.assertEquals("Initial state incorrect", problem.getJobRoute(1).size(), 1);
		Assert.assertEquals("Initial state incorrect", problem.getStationSequence(1).size(), 1);
		Assert.assertEquals("Initial state incorrect", problem.getStationSequence(2).size(), 2);
		
		problem.removeOperationFromSchedule(new OperationIndexVO(1, 2));
		Assert.assertEquals("Initial state incorrect", problem.getJobRoute(0).size(), 3);
		Assert.assertEquals("Initial state incorrect", problem.getStationSequence(1).size(), 1);
		Assert.assertEquals("Initial state incorrect", problem.getStationSequence(2).size(), 1);
		
		problem.removeOperationFromSchedule(new OperationIndexVO(0, 2));
		Assert.assertEquals("Initial state incorrect", problem.getJobRoute(0).size(), 2);
		Assert.assertEquals("Initial state incorrect", problem.getStationSequence(1).size(), 1);
		
		problem.removeOperationFromSchedule(new OperationIndexVO(0, 1));
		Assert.assertEquals("Initial state incorrect", problem.getStationSequence(0).size(), 1);
		Assert.assertEquals("Initial state incorrect", problem.getJobRoute(0).size(), 1);
		
		problem.removeOperationFromSchedule(new OperationIndexVO(0, 0));
		Assert.assertEquals("Initial state incorrect", 0, problem.getJobRoute(0).size());
		Assert.assertEquals("Initial state incorrect", 0, problem.getJobRoute(1).size());
		Assert.assertEquals("Initial state incorrect", 0, problem.getJobRoute(2).size());
		
		Assert.assertEquals("Initial state incorrect", 0, problem.getStationSequence(0).size());
		Assert.assertEquals("Initial state incorrect", 0, problem.getStationSequence(1).size());
		Assert.assertEquals("Initial state incorrect", 0, problem.getStationSequence(2).size());
	}
}
