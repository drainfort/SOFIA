package vector.testcases;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import structure.IOperation;
import structure.factory.impl.VectorFactory;
import structure.impl.Vector;
import structure.impl.decoding.NonDelayDecoding;
import common.types.BetaVO;
import common.types.OperationIndexVO;

public class Test_DecodeNonDelay {

	// -----------------------------------------------
	// Attributes
	// -----------------------------------------------

	private Vector vectorScenario;

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

		vectorScenario = (Vector) VectorFactory.createNewInstance(
				"structure.factory.impl.VectorFactory")
				.createSolutionStructure(problemFiles, betas, new NonDelayDecoding());

		vectorScenario.scheduleOperation(new OperationIndexVO(0, 2, 0, 0));
		vectorScenario.scheduleOperation(new OperationIndexVO(0, 0, 0, 1));
		vectorScenario.scheduleOperation(new OperationIndexVO(0, 1, 2, 3));
		vectorScenario.scheduleOperation(new OperationIndexVO(0, 3, 2, 4));
		vectorScenario.scheduleOperation(new OperationIndexVO(0, 2, 1, 2));
		vectorScenario.scheduleOperation(new OperationIndexVO(0, 0, 3, 5));
		vectorScenario.scheduleOperation(new OperationIndexVO(0, 1, 1, 2));
		vectorScenario.scheduleOperation(new OperationIndexVO(0, 3, 0, 0));
		vectorScenario.scheduleOperation(new OperationIndexVO(0, 2, 3, 5));
		vectorScenario.scheduleOperation(new OperationIndexVO(0, 0, 2, 3));
		vectorScenario.scheduleOperation(new OperationIndexVO(0, 1, 0, 0));
		vectorScenario.scheduleOperation(new OperationIndexVO(0, 3, 1, 2));
		vectorScenario.scheduleOperation(new OperationIndexVO(0, 2, 2, 3));
		vectorScenario.scheduleOperation(new OperationIndexVO(0, 0, 1, 2));
		vectorScenario.scheduleOperation(new OperationIndexVO(0, 3, 3, 5));
		vectorScenario.scheduleOperation(new OperationIndexVO(0, 1, 3, 5));

	}

	// -----------------------------------------------
	// Test cases
	// -----------------------------------------------

	@Test
	public void testNonDelayDecodingOperationsOrder() throws InterruptedException {

		// Calling the decoding method
		vectorScenario.decodeSolution();
		
		// Checking the order of the operations
		ArrayList<IOperation> decode = vectorScenario.getOperations();
		
		Assert.assertEquals(new OperationIndexVO(0, 2, 0, 0), decode.get(0).getOperationIndex());
		Assert.assertEquals(new OperationIndexVO(0, 0, 0, 1), decode.get(1).getOperationIndex());
		Assert.assertEquals(new OperationIndexVO(0, 1, 2, 3), decode.get(2).getOperationIndex());
		Assert.assertEquals(new OperationIndexVO(0, 3, 2, 4), decode.get(3).getOperationIndex());
		Assert.assertEquals(new OperationIndexVO(0, 2, 1, 2), decode.get(4).getOperationIndex());
		Assert.assertEquals(new OperationIndexVO(0, 0, 3, 5), decode.get(5).getOperationIndex());
		Assert.assertEquals(new OperationIndexVO(0, 1, 1, 2), decode.get(6).getOperationIndex());
		Assert.assertEquals(new OperationIndexVO(0, 3, 0, 0), decode.get(7).getOperationIndex());
		Assert.assertEquals(new OperationIndexVO(0, 2, 3, 5), decode.get(8).getOperationIndex());
		Assert.assertEquals(new OperationIndexVO(0, 0, 2, 3), decode.get(9).getOperationIndex());
		Assert.assertEquals(new OperationIndexVO(0, 1, 0, 0), decode.get(10).getOperationIndex());
		Assert.assertEquals(new OperationIndexVO(0, 3, 1, 2), decode.get(11).getOperationIndex());
		Assert.assertEquals(new OperationIndexVO(0, 2, 2, 3), decode.get(12).getOperationIndex());
		Assert.assertEquals(new OperationIndexVO(0, 0, 1, 2), decode.get(13).getOperationIndex());
		Assert.assertEquals(new OperationIndexVO(0, 3, 3, 5), decode.get(14).getOperationIndex());
		Assert.assertEquals(new OperationIndexVO(0, 1, 3, 5), decode.get(15).getOperationIndex());
	}
	
	@Test
	public void testNonDelayDecodingSchedule(){
		// Calling the decoding method
		vectorScenario.decodeSolution();
		
		// Checking the order of the operations
		ArrayList<IOperation> decode = vectorScenario.getOperations();
		
		Assert.assertEquals(12, decode.get(0).getFinalTime());
		Assert.assertEquals(13, decode.get(1).getFinalTime());
		Assert.assertEquals(18, decode.get(2).getFinalTime());
		Assert.assertEquals(18, decode.get(3).getFinalTime());
		Assert.assertEquals(27, decode.get(4).getFinalTime());
		Assert.assertEquals(28, decode.get(5).getFinalTime());
		Assert.assertEquals(38, decode.get(6).getFinalTime());
		Assert.assertEquals(40, decode.get(7).getFinalTime());
		Assert.assertEquals(43, decode.get(8).getFinalTime());
		Assert.assertEquals(47, decode.get(9).getFinalTime());
		Assert.assertEquals(55, decode.get(10).getFinalTime());
		Assert.assertEquals(55, decode.get(11).getFinalTime());
		Assert.assertEquals(60, decode.get(12).getFinalTime());
		Assert.assertEquals(68, decode.get(13).getFinalTime());
		Assert.assertEquals(69, decode.get(14).getFinalTime());
		Assert.assertEquals(78, decode.get(15).getFinalTime());
	}
}
