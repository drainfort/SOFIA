package vector.testcases;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import structure.IOperation;
import structure.factory.impl.VectorFactory;
import structure.impl.Operation;
import structure.impl.Vector;

import common.types.BetaVO;
import common.types.OperationIndexVO;

public class Test_Decode_Active {

// -----------------------------------------------
		// Attributes
		// -----------------------------------------------

		private Vector vectorScenario2;

		
		// -----------------------------------------------
		// Setup scenarios
		// -----------------------------------------------
		
		@Before
		public void setUpScenario() throws Exception {
				
			ArrayList<String> problemFiles = new ArrayList<String>();

			String TFile = "./data/04x04x02/1-T/T-04x04x02-02.txt";
			String TTFile = "./data/04x04x02/2-TT/TT-04x04x02-02.txt";
			String MFile = "./data/04x04x02/4-M/M-04x04x02-02.txt";
			
			problemFiles.add(TFile);
			problemFiles.add(TTFile);
			problemFiles.add(MFile);
			
			ArrayList<BetaVO> betas = new ArrayList<BetaVO>();
			ArrayList<String> informationFiles = new ArrayList<String>();
			informationFiles.add(TTFile);

			BetaVO TTBeta = new BetaVO("TravelTimes", "beta.impl.TravelTimes", informationFiles, true);
			BetaVO TearDownTT = new BetaVO("TearDownTravelTime", "beta.impl.TearDownTravelTime", informationFiles, true);
			betas.add(TTBeta);
			betas.add(TearDownTT);

			vectorScenario2 = (Vector) VectorFactory.createNewInstance(
					"structure.factory.impl.VectorFactory").createSolutionStructure(problemFiles, betas);
			
			vectorScenario2.scheduleOperation(new OperationIndexVO(0, 0, 0, 0));
			vectorScenario2.scheduleOperation(new OperationIndexVO(0, 0, 1, 1));
			vectorScenario2.scheduleOperation(new OperationIndexVO(0, 0, 2, 2));
			vectorScenario2.scheduleOperation(new OperationIndexVO(0, 0, 3, 3));
			vectorScenario2.scheduleOperation(new OperationIndexVO(0, 1, 0, 0));
			vectorScenario2.scheduleOperation(new OperationIndexVO(0, 1, 1, 1));
			vectorScenario2.scheduleOperation(new OperationIndexVO(0, 1, 2, 2));
			vectorScenario2.scheduleOperation(new OperationIndexVO(0, 1, 3, 3));
			vectorScenario2.scheduleOperation(new OperationIndexVO(0, 2, 0, 0));
			vectorScenario2.scheduleOperation(new OperationIndexVO(0, 2, 1, 1));
			vectorScenario2.scheduleOperation(new OperationIndexVO(0, 2, 2, 2));
			vectorScenario2.scheduleOperation(new OperationIndexVO(0, 2, 3, 3));
			vectorScenario2.scheduleOperation(new OperationIndexVO(0, 3, 0, 0));
			vectorScenario2.scheduleOperation(new OperationIndexVO(0, 3, 1, 1));
			vectorScenario2.scheduleOperation(new OperationIndexVO(0, 3, 2, 2));
			vectorScenario2.scheduleOperation(new OperationIndexVO(0, 3, 3, 3));

				
		}
		// -----------------------------------------------
		// Test cases
		// -----------------------------------------------
		
		@Test
		public void testDecode() throws InterruptedException {
			
			vectorScenario2.decodeSolutionActiveSchedule();
			vectorScenario2.calculateCMatrix();
			System.out.println(vectorScenario2.getOperations());
			
			
		}
		

		
}
