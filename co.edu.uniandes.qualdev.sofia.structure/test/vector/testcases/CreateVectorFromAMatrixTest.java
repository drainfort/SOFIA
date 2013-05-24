package vector.testcases;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import common.types.BetaVO;
import common.utils.MatrixUtils;

import structure.IOperation;
import structure.factory.impl.VectorFactory;
import structure.impl.Vector;

/**
 * Test for the functionality of create a permutation list from
 * a ranks matrix
 * 
 * @author David Mendez-Acuna
 */
public class CreateVectorFromAMatrixTest {

	// ----------------------------------------
	// Attributes
	// ----------------------------------------
	
	private Vector vector;
	
	// ----------------------------------------
	// Setup scenarios
	// ----------------------------------------
	
	@Before
	public void setupScenario() throws Exception{
		
		ArrayList<String> problemFiles = new ArrayList<String>();
		
		String AFile = "./data/A-04x04-01.txt";
		String TFile = "./data/T-04x04-01.txt";
		String TTFile = "./data/TT-04x04-01.txt";
		problemFiles.add(TFile);
		problemFiles.add(TTFile);
		
		Integer[][] A = MatrixUtils.loadMatrix(AFile);

		ArrayList<BetaVO> betas = new ArrayList<BetaVO>();
		ArrayList<String> informationFiles = new ArrayList<String>();
		informationFiles.add(TTFile);
		
		BetaVO TT = new BetaVO("TravelTimes", "beta.impl.TravelTimes", informationFiles, true);
		BetaVO TearDownTT = new BetaVO("TearDownTravelTime", "beta.impl.TearDownTravelTime", informationFiles, true);
		betas.add(TT);
		betas.add(TearDownTT);
		
		vector = (Vector) VectorFactory.createNewInstance("structure.factory.impl.VectorFactory").createSolutionStructure(
				A, problemFiles, betas);
	}
	
	// ----------------------------------------
	// Test cases
	// ----------------------------------------
	
	/**
	 * The vector, for the scenario 1, should be:
	 * (0,1)(1,2)(2,3)(3,0)(0,0)(1,3)(2,2,)(3,1)(0,2)(1,1)(2,0)(3,3)(0,3)(1,0)(2,1)(3,2)
	 */
	@Test
	public void testCreateVectorFromMatrix(){
		ArrayList<IOperation> arrayList = vector.getVector();
		
		Assert.assertEquals("The job should be 0", arrayList.get(0).getOperationIndex().getJobId(), 0);
		Assert.assertEquals("The station should be 1", arrayList.get(0).getOperationIndex().getStationId(), 1);
		
		Assert.assertEquals("The job should be 0", arrayList.get(1).getOperationIndex().getJobId(), 1);
		Assert.assertEquals("The station should be 1", arrayList.get(1).getOperationIndex().getStationId(), 2);
		
		Assert.assertEquals("The job should be 0", arrayList.get(2).getOperationIndex().getJobId(), 2);
		Assert.assertEquals("The station should be 1", arrayList.get(2).getOperationIndex().getStationId(), 3);
		
		Assert.assertEquals("The job should be 0", arrayList.get(3).getOperationIndex().getJobId(), 3);
		Assert.assertEquals("The station should be 1", arrayList.get(3).getOperationIndex().getStationId(), 0);
		
		Assert.assertEquals("The job should be 0", arrayList.get(4).getOperationIndex().getJobId(), 0);
		Assert.assertEquals("The station should be 1", arrayList.get(4).getOperationIndex().getStationId(), 0);
		
		Assert.assertEquals("The job should be 0", arrayList.get(5).getOperationIndex().getJobId(), 1);
		Assert.assertEquals("The station should be 1", arrayList.get(5).getOperationIndex().getStationId(), 3);
		
		Assert.assertEquals("The job should be 0", arrayList.get(6).getOperationIndex().getJobId(), 2);
		Assert.assertEquals("The station should be 1", arrayList.get(6).getOperationIndex().getStationId(), 2);
		
		Assert.assertEquals("The job should be 0", arrayList.get(7).getOperationIndex().getJobId(), 3);
		Assert.assertEquals("The station should be 1", arrayList.get(7).getOperationIndex().getStationId(), 1);
		
		Assert.assertEquals("The job should be 0", arrayList.get(8).getOperationIndex().getJobId(), 0);
		Assert.assertEquals("The station should be 1", arrayList.get(8).getOperationIndex().getStationId(), 2);
		
		Assert.assertEquals("The job should be 0", arrayList.get(9).getOperationIndex().getJobId(), 1);
		Assert.assertEquals("The station should be 1", arrayList.get(9).getOperationIndex().getStationId(), 1);
		
		Assert.assertEquals("The job should be 0", arrayList.get(10).getOperationIndex().getJobId(), 2);
		Assert.assertEquals("The station should be 1", arrayList.get(10).getOperationIndex().getStationId(), 0);
		
		Assert.assertEquals("The job should be 0", arrayList.get(11).getOperationIndex().getJobId(), 3);
		Assert.assertEquals("The station should be 1", arrayList.get(11).getOperationIndex().getStationId(), 3);
		
		Assert.assertEquals("The job should be 0", arrayList.get(12).getOperationIndex().getJobId(), 0);
		Assert.assertEquals("The station should be 1", arrayList.get(12).getOperationIndex().getStationId(), 3);
		
		Assert.assertEquals("The job should be 0", arrayList.get(13).getOperationIndex().getJobId(), 1);
		Assert.assertEquals("The station should be 1", arrayList.get(13).getOperationIndex().getStationId(), 0);
		
		Assert.assertEquals("The job should be 0", arrayList.get(14).getOperationIndex().getJobId(), 2);
		Assert.assertEquals("The station should be 1", arrayList.get(14).getOperationIndex().getStationId(), 1);
		
		Assert.assertEquals("The job should be 0", arrayList.get(15).getOperationIndex().getJobId(), 3);
		Assert.assertEquals("The station should be 1", arrayList.get(15).getOperationIndex().getStationId(), 2);
	}
}
