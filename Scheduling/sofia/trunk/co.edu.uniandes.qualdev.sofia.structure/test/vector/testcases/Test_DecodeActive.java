package vector.testcases;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import structure.IOperation;
import structure.factory.impl.VectorFactory;
import structure.impl.Vector;
import structure.impl.decoding.ActiveDecoding;
import common.types.BetaVO;
import common.types.OperationIndexVO;

/**
 * Test class for the active decoding of the permutation list
 * 
 * @author Jaime Romero
 * @author David Mendez-Acuna
 */
public class Test_DecodeActive {

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
				.createSolutionStructure(problemFiles, betas, new ActiveDecoding());

		for(int i=0; i< vectorScenario.getProblem().length;i++){
			for(int j=0; j< vectorScenario.getProblem()[i].length;j++){
				vectorScenario.scheduleOperation(vectorScenario.getProblem()[i][j]);
			}
		}
		vectorScenario.calculateCMatrix();
	}

	// -----------------------------------------------
	// Test cases
	// -----------------------------------------------

	@Test
	public void testActiveDecodingOperationsOrder() {
		
		// Calling the decoding method
		vectorScenario.decodeSolution();
		
		// Checking the order of the operations
		ArrayList<IOperation> decode = vectorScenario.getOperations();
		
		Assert.assertEquals(new OperationIndexVO(0,0,0,0), decode.get(0).getOperationIndex());
		Assert.assertEquals(new OperationIndexVO(0,1,0,1), decode.get(1).getOperationIndex());
		Assert.assertEquals(new OperationIndexVO(0,2,2,3), decode.get(2).getOperationIndex());
		Assert.assertEquals(new OperationIndexVO(0,3,2,4), decode.get(3).getOperationIndex());
		Assert.assertEquals(new OperationIndexVO(0,0,3,5), decode.get(4).getOperationIndex());
		Assert.assertEquals(new OperationIndexVO(0,1,1,2), decode.get(5).getOperationIndex());
		Assert.assertEquals(new OperationIndexVO(0,2,0,0), decode.get(6).getOperationIndex());
		Assert.assertEquals(new OperationIndexVO(0,1,3,5), decode.get(7).getOperationIndex());
		Assert.assertEquals(new OperationIndexVO(0,3,0,1), decode.get(8).getOperationIndex());
		Assert.assertEquals(new OperationIndexVO(0,0,1,2), decode.get(9).getOperationIndex());
		Assert.assertEquals(new OperationIndexVO(0,2,3,5), decode.get(10).getOperationIndex());
		Assert.assertEquals(new OperationIndexVO(0,3,1,2), decode.get(11).getOperationIndex());
		Assert.assertEquals(new OperationIndexVO(0,0,2,3), decode.get(12).getOperationIndex());
		Assert.assertEquals(new OperationIndexVO(0,1,2,4), decode.get(13).getOperationIndex());
		Assert.assertEquals(new OperationIndexVO(0,2,1,2), decode.get(14).getOperationIndex());
		Assert.assertEquals(new OperationIndexVO(0,3,3,5), decode.get(15).getOperationIndex());
	}
	
	@Test
	public void testActiveDecodingSchedule() {
		// Calling the decoding method
		vectorScenario.decodeSolution();
		
		// Checking the order of the operations
		ArrayList<IOperation> decode = vectorScenario.getOperations();
		
		Assert.assertEquals(decode.get(0).getInitialTime(), 2);
		Assert.assertEquals(decode.get(0).getFinalTime(), 13);
		
		Assert.assertEquals(decode.get(1).getInitialTime(), 2);
		Assert.assertEquals(decode.get(1).getFinalTime(), 15);
		
		Assert.assertEquals(decode.get(2).getInitialTime(), 6);
		Assert.assertEquals(decode.get(2).getFinalTime(), 16);
		
		Assert.assertEquals(decode.get(3).getInitialTime(), 6);
		Assert.assertEquals(decode.get(3).getFinalTime(), 18);
		
		Assert.assertEquals(decode.get(4).getInitialTime(), 20);
		Assert.assertEquals(decode.get(4).getFinalTime(), 28);
		
		Assert.assertEquals(decode.get(5).getInitialTime(), 19);
		Assert.assertEquals(decode.get(5).getFinalTime(), 30);
		
		Assert.assertEquals(decode.get(6).getInitialTime(), 25);
		Assert.assertEquals(decode.get(6).getFinalTime(), 35);
		
		Assert.assertEquals(decode.get(7).getInitialTime(), 34);
		Assert.assertEquals(decode.get(7).getFinalTime(), 43);
		
		Assert.assertEquals(decode.get(8).getInitialTime(), 27);
		Assert.assertEquals(decode.get(8).getFinalTime(), 40);
		
		Assert.assertEquals(decode.get(9).getInitialTime(), 32);
		Assert.assertEquals(decode.get(9).getFinalTime(), 45);
		
		Assert.assertEquals(decode.get(10).getInitialTime(), 43);
		Assert.assertEquals(decode.get(10).getFinalTime(), 55);
		
		Assert.assertEquals(decode.get(11).getInitialTime(), 45);
		Assert.assertEquals(decode.get(11).getFinalTime(), 56);
		
		Assert.assertEquals(decode.get(12).getInitialTime(), 51);
		Assert.assertEquals(decode.get(12).getFinalTime(), 63);
		
		Assert.assertEquals(decode.get(13).getInitialTime(), 50);
		Assert.assertEquals(decode.get(13).getFinalTime(), 62);
		
		Assert.assertEquals(decode.get(14).getInitialTime(), 59);
		Assert.assertEquals(decode.get(14).getFinalTime(), 70);
		
		Assert.assertEquals(decode.get(15).getInitialTime(), 60);
		Assert.assertEquals(decode.get(15).getFinalTime(), 70);
	}
}