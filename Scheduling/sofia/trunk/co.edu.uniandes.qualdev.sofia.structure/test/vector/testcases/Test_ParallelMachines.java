package vector.testcases;

import java.util.ArrayList;

import org.junit.Before;

import structure.factory.impl.VectorFactory;
import structure.impl.Vector;

import common.types.BetaVO;
import common.types.OperationIndexVO;

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
			
			vector.scheduleOperation(new OperationIndexVO(0, 1));
			vector.scheduleOperation(new OperationIndexVO(1, 2));
			vector.scheduleOperation(new OperationIndexVO(2, 3));
			vector.scheduleOperation(new OperationIndexVO(3, 2));
			vector.scheduleOperation(new OperationIndexVO(2, 1));
			vector.scheduleOperation(new OperationIndexVO(1, 0));
			vector.scheduleOperation(new OperationIndexVO(2, 0));
			vector.scheduleOperation(new OperationIndexVO(0, 2));
			vector.scheduleOperation(new OperationIndexVO(0, 3));
			vector.scheduleOperation(new OperationIndexVO(3, 0));
			vector.scheduleOperation(new OperationIndexVO(0, 0));
			vector.scheduleOperation(new OperationIndexVO(1, 1));
			vector.scheduleOperation(new OperationIndexVO(2, 2));
			vector.scheduleOperation(new OperationIndexVO(3, 3));
			vector.scheduleOperation(new OperationIndexVO(1, 3));
			vector.scheduleOperation(new OperationIndexVO(3, 1));
		}
	
}
