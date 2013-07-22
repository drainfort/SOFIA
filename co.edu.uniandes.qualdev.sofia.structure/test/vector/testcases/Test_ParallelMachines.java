package vector.testcases;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import structure.factory.impl.VectorFactory;
import structure.impl.Vector;

import common.types.BetaVO;
import common.types.OperationIndexVO;
import common.utils.MatrixUtils;

public class Test_ParallelMachines {

	// ----------------------------------------
		// Attributes
		// ----------------------------------------

		private Vector vector;

		// ----------------------------------------
		// Setup scenarios
		// ----------------------------------------

		@Before
		public void setupScenario() throws Exception {

			ArrayList<String> problemFiles = new ArrayList<String>();

			String TFile = "./data/04x04x02/1-T/T-04x04x02-01.txt";
			String TTFile = "./data/04x04x02/2-TT/TT-04x04x02-01.txt";
			String MFile = "./data/04x04x02/4-M/M-04x04x02-01.txt";
			
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

			vector = (Vector) VectorFactory.createNewInstance(
					"structure.factory.impl.VectorFactory").createSolutionStructure(problemFiles, betas);
			
			vector.scheduleOperation(new OperationIndexVO(0, 0, 0, 0));
			vector.scheduleOperation(new OperationIndexVO(0, 0, 0, 1));
			vector.scheduleOperation(new OperationIndexVO(0, 0, 1, 3));
			vector.scheduleOperation(new OperationIndexVO(0, 0, 1, 2));
			vector.scheduleOperation(new OperationIndexVO(0, 0, 2, 4));
			vector.scheduleOperation(new OperationIndexVO(0, 0, 2, 5));
			vector.scheduleOperation(new OperationIndexVO(0, 0, 3, 6));
			vector.scheduleOperation(new OperationIndexVO(0, 0, 3, 7));
			
			vector.scheduleOperation(new OperationIndexVO(0, 1, 0, 1));
			vector.scheduleOperation(new OperationIndexVO(0, 1, 1, 2));
			vector.scheduleOperation(new OperationIndexVO(0, 1, 2, 5));
			vector.scheduleOperation(new OperationIndexVO(0, 1, 3, 7));
			
			vector.scheduleOperation(new OperationIndexVO(0, 2, 0, 0));
			vector.scheduleOperation(new OperationIndexVO(0, 2, 1, 3));
			vector.scheduleOperation(new OperationIndexVO(0, 2, 2, 4));
			vector.scheduleOperation(new OperationIndexVO(0, 2, 3, 6));

			vector.scheduleOperation(new OperationIndexVO(0, 3, 0, 1));
			vector.scheduleOperation(new OperationIndexVO(0, 3, 1, 2));
			vector.scheduleOperation(new OperationIndexVO(0, 3, 2, 5));
			vector.scheduleOperation(new OperationIndexVO(0, 3, 3, 7));
			
			System.out.println(vector.getVector());
			MatrixUtils.printMatrix(vector.calculateCMatrix());
			MatrixUtils.printMatrix(vector.getCIntepretation());
		}
		
		@Test
		public void testClone(){
			Vector clone = (Vector)vector.cloneStructure();
			
			Assert.assertEquals(clone.getTotalJobs(), vector.getTotalJobs());
			Assert.assertEquals(clone.getTotalStations(), vector.getTotalStations());
			Assert.assertEquals(clone.getTotalMachines(), vector.getTotalMachines());
		}
	
}
