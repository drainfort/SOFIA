package vector.testcases;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import structure.factory.impl.VectorFactory;
import structure.impl.Vector;

import common.types.BetaVO;
import common.types.OperationIndexVO;

public class Test_DecodeActive {

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
				"structure.factory.impl.VectorFactory")
				.createSolutionStructure(problemFiles, betas);

		for(int i=0; i< vectorScenario2.getProblem().length;i++){
			for(int j=0; j< vectorScenario2.getProblem()[i].length;j++){
				vectorScenario2.scheduleOperation(vectorScenario2.getProblem()[i][j]);
			}
		}
		vectorScenario2.calculateCMatrix();
	}

	// -----------------------------------------------
	// Test cases
	// -----------------------------------------------

	@Test
	public void testDecode() throws InterruptedException {
		System.out.println(vectorScenario2.getOperations());
		vectorScenario2.decodeSolutionActiveSchedule();
		System.out.println(vectorScenario2.getVectorDecodActiveSchedule());
	}
}