package vector.testcases;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import structure.factory.impl.VectorFactory;
import structure.impl.Vector;

import common.types.BetaVO;
import common.utils.MatrixUtils;

/**
 * Test for the functionality of create a ranks matrix from a permutation list
 * 
 * @author David Mendez-Acuna
 */
public class CreateAMatrixFromVectorTest {

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

		String AFile = "./data/A-04x04-01.txt";
		String TFile = "./data/T-04x04-01.txt";
		String TTFile = "./data/TT-04x04-01.txt";
		problemFiles.add(TFile);
		problemFiles.add(TTFile);

		Integer[][] A = MatrixUtils.loadMatrix(AFile);

		ArrayList<BetaVO> betas = new ArrayList<BetaVO>();
		ArrayList<String> informationFiles = new ArrayList<String>();
		informationFiles.add(TTFile);

		BetaVO TT = new BetaVO("TravelTimes", "beta.impl.TravelTimes",
				informationFiles);
		BetaVO TearDownTT = new BetaVO("TearDownTravelTime",
				"beta.impl.TearDownTravelTime", informationFiles);
		betas.add(TT);
		betas.add(TearDownTT);

		vector = (Vector) VectorFactory.createNewInstance(
				"structure.factory.impl.VectorFactory").createSolutionStructure(A,
				problemFiles, betas);
	}

	// ----------------------------------------
	// Test cases
	// ----------------------------------------
	
	@Test
	public void testCreateAMatrixFromVector04x04_1(){
		vector.calculateAMatrix();
		
		int[][] A = vector.calculateAMatrix();
		
		Assert.assertEquals("Rank A[0][0] incorrect", A[0][0], 2);
		Assert.assertEquals("Rank A[0][1] incorrect", A[0][1], 1);
		Assert.assertEquals("Rank A[0][2] incorrect", A[0][2], 3);
		Assert.assertEquals("Rank A[0][3] incorrect", A[0][3], 4);
		
		Assert.assertEquals("Rank A[1][0] incorrect", A[1][0], 4);
		Assert.assertEquals("Rank A[1][1] incorrect", A[1][1], 3);
		Assert.assertEquals("Rank A[1][2] incorrect", A[1][2], 1);
		Assert.assertEquals("Rank A[1][3] incorrect", A[1][3], 2);
		
		Assert.assertEquals("Rank A[2][0] incorrect", A[2][0], 3);
		Assert.assertEquals("Rank A[2][1] incorrect", A[2][1], 4);
		Assert.assertEquals("Rank A[2][2] incorrect", A[2][2], 2);
		Assert.assertEquals("Rank A[2][3] incorrect", A[2][3], 1);
		
		Assert.assertEquals("Rank A[3][0] incorrect", A[3][0], 1);
		Assert.assertEquals("Rank A[3][1] incorrect", A[3][1], 2);
		Assert.assertEquals("Rank A[3][2] incorrect", A[3][2], 4);
		Assert.assertEquals("Rank A[3][3] incorrect", A[3][3], 3);
	}
}