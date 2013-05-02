package graph.scenarios;

import java.util.ArrayList;

import common.types.BetaVO;
import common.types.OperationIndexVO;
import common.utils.MatrixUtils;

import structure.factory.impl.GraphFactory;
import structure.impl.Graph;

/**
 * Class that is able to build simple scenarios for testing the graph structure
 * @author Rubby Casallas
 * @author David Mendez-Acuna
 * @author Jaime Romero
 */
public class GraphScenariosFactory {

	// -----------------------------------------------------------
	// Constants
	// -----------------------------------------------------------
	
	private final static String TMatrixFile = "./data/TMatrix.matrix";
	
	private final static String AMatrixFile = "./data/A-04x04-01.txt";
	
	// -----------------------------------------------------------
	// Methods
	// -----------------------------------------------------------
	
	/**
	 * Returns a simple graph with a simple scenario for testing the
	 * functionality of the graph
	 * 
	 * @param n Desired amount of jobs
	 * @param m Desired amount of stations
	 * 
	 * @return graph
	 */
	public static Graph buildDummySolution(int n, int m) {
		Graph graph = new Graph(n, m);
		
		int numJobs = graph.getTotalJobs();
		for (int i = 0; i < numJobs; i++) {
			graph.setInitialJobNode(i, graph.getNode(i, 0));
		}

		int numMachines = graph.getTotalStations();
		for (int j = 0; j < numMachines; j++) {
			graph.setInitialStationNode(j, graph.getNode(0, j));
		}

		for (int i = 0; i < numJobs; i++) {
			for (int j = 0; j < numMachines; j++) {
				if (j + 1 < numMachines)
					graph.createRouteArc(new OperationIndexVO(i, j),
							new OperationIndexVO(i, j + 1));
				if (i + 1 < numJobs)
					graph.createSequenceArc(new OperationIndexVO(i, j),
							new OperationIndexVO(i + 1, j));
			}
		}
		
		return graph;
	}
	
	/**
	 * Returns an initial problem obtained from the file in the parameter.
	 * @return
	 * @throws Exception
	 */
	public static Graph buildSimple04x04Problem() throws Exception{
		ArrayList<String> problemFiles = new ArrayList<String>();
		problemFiles.add(TMatrixFile);
		ArrayList<BetaVO> betas = new ArrayList<BetaVO>();
		
		Graph problem = (Graph) GraphFactory.createNewInstance("structure.factory.impl.GraphFactory").createSolutionStructure(problemFiles, betas);
		
		return problem;
	}
	
	public static Graph buildSimple04x04Solution() throws Exception{
		ArrayList<String> problemFiles = new ArrayList<String>();
		problemFiles.add(TMatrixFile);
		
		Integer[][] A = MatrixUtils.loadMatrix(AMatrixFile);
		ArrayList<BetaVO> betas = new ArrayList<BetaVO>();
		Graph problem = (Graph) GraphFactory.createNewInstance("structure.factory.impl.GraphFactory").createSolutionStructure(A, problemFiles, betas);
		
		return problem;
	}
}