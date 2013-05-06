package neighborCalculator.tests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import neighborCalculator.impl.AdjacentShiftOnCriticalRoutes;

import org.junit.Before;
import org.junit.Test;


//TODO Repensar
public class TestAdjacentShiftOnCriticalRoute {

	// -----------------------------------------------
	// Attributes
	// -----------------------------------------------
	
	private IGraph graph;
	
	private static String inputFileTMatrix = "./data/TMatrix.matrix";
	
	private AdjacentShiftOnCriticalRoutes shift = new AdjacentShiftOnCriticalRoutes();
	
	// -----------------------------------------------
	// Setup scenarios
	// -----------------------------------------------
	
	@Before
	public void setupScenario() throws Exception{
		ArrayList<String> problemFiles = new ArrayList<String>();
		problemFiles.add(inputFileTMatrix);
		
		graph =  AbstractGraphFactory.createNewInstance(
				"graph.factory.GraphFactory").createSolutionGraph(
						problemFiles, null);
		Scenario.buildDummySolution(graph);
		
	}
	
	// -----------------------------------------------
	// Test cases
	// -----------------------------------------------
	
	@Test
	public void testCalculateSwapAtRandomVectorNeighbor(){
		
		try {
			String salida = graph.getLongestRoutes().get(0).toString();
			IGraph test = shift.calculateNeighbor(graph, null);
		    String ruta = test.getLongestRoutes().get(0).toString();
		    assertTrue("Intercambio mal", ruta.equals("/<1,0> 15 //<0,0> 49 //<0,1> 51 //<1,1> 140 //<1,2> 210 //<2,2> 238 /"));
		    IOperation nodo = test.getOperation(0, 0).getNextSequenceNode();
		    assertTrue("vecino queda bn", nodo.getOperationIndex().getJobId()==2 && nodo.getOperationIndex().getStationId()==0);
		    nodo = test.getOperation(1, 0).getNextSequenceNode();
		    assertTrue("vecino queda bn", nodo.getOperationIndex().getJobId()==0 && nodo.getOperationIndex().getStationId()==0);
		    test = shift.calculateNeighbor(test, null);
		    ruta = test.getLongestRoutes().get(0).toString();
		    assertTrue("Deben ser iguales al devolver el shift", salida.equals(ruta));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
