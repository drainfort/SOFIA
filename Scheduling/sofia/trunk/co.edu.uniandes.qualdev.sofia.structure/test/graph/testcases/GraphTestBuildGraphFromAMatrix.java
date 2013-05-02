package graph.testcases;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Vector;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import common.types.BetaVO;
import common.utils.MatrixUtils;

import structure.factory.impl.GraphFactory;
import structure.impl.Graph;

/**
 * Test cases for the implementation of the graph
 * 
 * @author Rubby Casallas
 * @author David Mendez-Acuna
 */
public class GraphTestBuildGraphFromAMatrix {

	// -----------------------------------------------
	// Attributes
	// -----------------------------------------------

	private Graph problem;

	private String TMatrixFile = "./data/TMatrix.matrix";

	private String AMatrixFile = "./data/A-04x04-01.txt";
	
	// -----------------------------------------------
	// Setup scenarios
	// -----------------------------------------------

	@Before
	public void setupScenario1() {
		try {
			Integer[][] A = MatrixUtils.loadMatrix(AMatrixFile);
			ArrayList<String> problemFiles = new ArrayList<String>();
			problemFiles.add(TMatrixFile);
			ArrayList<BetaVO> betas = new ArrayList<BetaVO>();
			
 			problem = (Graph) GraphFactory.createNewInstance("structure.factory.impl.GraphFactory").createSolutionStructure(A, problemFiles, betas);
			
		} catch (Exception e) {
			e.printStackTrace();
			fail("Fail loading the input processing times file ");
		}
	}

	// -----------------------------------------------
	// Test cases
	// -----------------------------------------------

	@Test
	public void testRoutes(){
		Vector<Integer> route = (Vector<Integer>) problem.getJobRoute(0);
		
		Assert.assertEquals("Station id incorrect.", 1, route.get(0).intValue());
		Assert.assertEquals("Station id incorrect.", 0, route.get(1).intValue());
		Assert.assertEquals("Station id incorrect.", 2, route.get(2).intValue());
		Assert.assertEquals("Station id incorrect.", 3, route.get(3).intValue());
		
		route = (Vector<Integer>) problem.getJobRoute(1);
		
		Assert.assertEquals("Station id incorrect.", 2, route.get(0).intValue());
		Assert.assertEquals("Station id incorrect.", 3, route.get(1).intValue());
		Assert.assertEquals("Station id incorrect.", 1, route.get(2).intValue());
		Assert.assertEquals("Station id incorrect.", 0, route.get(3).intValue());
		
		route = (Vector<Integer>) problem.getJobRoute(2);
		
		Assert.assertEquals("Station id incorrect.", 3, route.get(0).intValue());
		Assert.assertEquals("Station id incorrect.", 2, route.get(1).intValue());
		Assert.assertEquals("Station id incorrect.", 0, route.get(2).intValue());
		Assert.assertEquals("Station id incorrect.", 1, route.get(3).intValue());
	
		route = (Vector<Integer>) problem.getJobRoute(3);
		
		Assert.assertEquals("Station id incorrect.", 0, route.get(0).intValue());
		Assert.assertEquals("Station id incorrect.", 1, route.get(1).intValue());
		Assert.assertEquals("Station id incorrect.", 3, route.get(2).intValue());
		Assert.assertEquals("Station id incorrect.", 2, route.get(3).intValue());
	}
	
	@Test
	public void testSequences(){
		Vector<Integer> sequence = (Vector<Integer>) problem.getStationSequence(0);
		
		Assert.assertEquals("Job id incorrect.", 3, sequence.get(0).intValue());
		Assert.assertEquals("Job id incorrect.", 0, sequence.get(1).intValue());
		Assert.assertEquals("Job id incorrect.", 2, sequence.get(2).intValue());
		Assert.assertEquals("Job id incorrect.", 1, sequence.get(3).intValue());
		
		sequence = (Vector<Integer>) problem.getStationSequence(1);
		
		Assert.assertEquals("Job id incorrect.", 0, sequence.get(0).intValue());
		Assert.assertEquals("Job id incorrect.", 3, sequence.get(1).intValue());
		Assert.assertEquals("Job id incorrect.", 1, sequence.get(2).intValue());
		Assert.assertEquals("Job id incorrect.", 2, sequence.get(3).intValue());
		
		sequence = (Vector<Integer>) problem.getStationSequence(2);
		
		Assert.assertEquals("Job id incorrect.", 1, sequence.get(0).intValue());
		Assert.assertEquals("Job id incorrect.", 2, sequence.get(1).intValue());
		Assert.assertEquals("Job id incorrect.", 0, sequence.get(2).intValue());
		Assert.assertEquals("Job id incorrect.", 3, sequence.get(3).intValue());
		
		sequence = (Vector<Integer>) problem.getStationSequence(3);
		
		Assert.assertEquals("Job id incorrect.", 2, sequence.get(0).intValue());
		Assert.assertEquals("Job id incorrect.", 1, sequence.get(1).intValue());
		Assert.assertEquals("Job id incorrect.", 3, sequence.get(2).intValue());
		Assert.assertEquals("Job id incorrect.", 0, sequence.get(3).intValue());
	}
	
}