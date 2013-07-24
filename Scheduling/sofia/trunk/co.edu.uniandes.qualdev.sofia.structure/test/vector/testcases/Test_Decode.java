package vector.testcases;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import structure.impl.CriticalPath;
import structure.impl.Vector;

import common.types.OperationIndexVO;

public class Test_Decode {
	
	// -----------------------------------------------
		// Attributes
		// -----------------------------------------------
		
		private Vector vectorScenario1;

		
		// -----------------------------------------------
		// Setup scenarios
		// -----------------------------------------------
		
		@Before
		public void setUpScenario() throws Exception {
			
				//Building scenario 1
				vectorScenario1 = new Vector(2,2);
				vectorScenario1.setTotalMachines(4);

			
				OperationIndexVO[][] problem = new OperationIndexVO[2][4]; 
				problem[0][0] = new OperationIndexVO(10, 0, 0, 0);
				problem[0][1] = new OperationIndexVO(10, 0, 0, 1);
				problem[0][2] = new OperationIndexVO(20, 0, 1, 2);
				problem[0][3] = new OperationIndexVO(20, 0, 1, 3);
				problem[1][0] = new OperationIndexVO(5, 1, 0, 0);
				problem[1][1] = new OperationIndexVO(5, 1, 0, 1);
				problem[1][2] = new OperationIndexVO(5, 1, 1, 2);
				problem[1][3] = new OperationIndexVO(5, 1, 1, 3);
				
				vectorScenario1.setProblem(problem);
				vectorScenario1.scheduleOperation(problem[0][0]);
				vectorScenario1.scheduleOperation(problem[0][2]);
				vectorScenario1.scheduleOperation(problem[1][0]);
				vectorScenario1.scheduleOperation(problem[1][2]);
				
		}
		// -----------------------------------------------
		// Test cases
		// -----------------------------------------------
		
		@Test
		public void testCriticalPathScenario1() throws InterruptedException {
			
			vectorScenario1.calculateCMatrix();
			System.out.println(vectorScenario1.getVectorDecodNonDelay());
			
		}

}
