package vector.testcases;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import structure.IOperation;
import structure.factory.impl.VectorFactory;
import structure.impl.CriticalPath;
import structure.impl.Operation;
import structure.impl.Vector;

import common.types.BetaVO;
import common.types.OperationIndexVO;

public class Test_Decode {
	
	// -----------------------------------------------
		// Attributes
		// -----------------------------------------------
		
		private Vector vectorScenario1;
		private Vector vectorScenario2;

		
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
				
				
				ArrayList<String> problemFiles = new ArrayList<String>();

				String TFile = "./data/CasoEspecial/1-T/T-04x04-01.txt";
				String TTFile = "./data/CasoEspecial/2-TT/TT-04x04-01.txt";
				
				problemFiles.add(TFile);
				problemFiles.add(TTFile);

				ArrayList<BetaVO> betas = new ArrayList<BetaVO>();
				ArrayList<String> informationFiles = new ArrayList<String>();
				informationFiles.add(TTFile);

				BetaVO TTBeta = new BetaVO("TravelTimes", "beta.impl.TravelTimes", informationFiles, true);
				BetaVO TearDownTT = new BetaVO("TearDownTravelTime", "beta.impl.TearDownTravelTime", informationFiles, true);
				betas.add(TTBeta);
				betas.add(TearDownTT);

				vectorScenario2 = (Vector) VectorFactory.createNewInstance(
						"structure.factory.impl.VectorFactory").createSolutionStructure(problemFiles, betas);
				
				vectorScenario2.scheduleOperation(new OperationIndexVO(5, 0, 0, 0));
				vectorScenario2.scheduleOperation(new OperationIndexVO(5, 0, 1, 1));
				vectorScenario2.scheduleOperation(new OperationIndexVO(5, 0, 2, 2));
				vectorScenario2.scheduleOperation(new OperationIndexVO(5, 0, 3, 3));
				vectorScenario2.scheduleOperation(new OperationIndexVO(5, 1, 0, 0));
				vectorScenario2.scheduleOperation(new OperationIndexVO(5, 1, 1, 1));
				vectorScenario2.scheduleOperation(new OperationIndexVO(5, 1, 2, 2));
				vectorScenario2.scheduleOperation(new OperationIndexVO(5, 1, 3, 3));
				vectorScenario2.scheduleOperation(new OperationIndexVO(5, 2, 0, 0));
				vectorScenario2.scheduleOperation(new OperationIndexVO(5, 2, 1, 1));
				vectorScenario2.scheduleOperation(new OperationIndexVO(5, 2, 2, 2));
				vectorScenario2.scheduleOperation(new OperationIndexVO(5, 2, 3, 3));
				vectorScenario2.scheduleOperation(new OperationIndexVO(5, 3, 0, 0));
				vectorScenario2.scheduleOperation(new OperationIndexVO(5, 3, 1, 1));
				vectorScenario2.scheduleOperation(new OperationIndexVO(5, 3, 2, 2));
				vectorScenario2.scheduleOperation(new OperationIndexVO(5, 3, 3, 3));

				
		}
		// -----------------------------------------------
		// Test cases
		// -----------------------------------------------
		
		@Test
		public void testCriticalPathScenario1() throws InterruptedException {
			
			vectorScenario1.calculateCMatrix();
			
			ArrayList<IOperation> vector = vectorScenario1.getVectorDecodNonDelay();
			
			// Aqui hay que tener en cuenta el orden de la cosa. 
			Assert.assertTrue(vector.contains(new Operation(new OperationIndexVO(10, 0, 0, 0))));
			Assert.assertTrue(vector.contains(new Operation(new OperationIndexVO(10, 0, 1, 2))));
			Assert.assertTrue(vector.contains(new Operation(new OperationIndexVO(10, 1, 0, 1))));
			Assert.assertTrue(vector.contains(new Operation(new OperationIndexVO(10, 1, 1, 2))));
		}
		
		@Test
		public void testCriticalPathScenario2() throws InterruptedException {
			
			vectorScenario2.calculateCMatrix();
			System.out.println(vectorScenario2.getVectorDecodNonDelay());
		}
		
		// TODO Necesitamos un caso de prueba más grande. Por ejemplo 4x4x2
}
