package structure.factory.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import common.types.BetaVO;
import common.types.OperationIndexVO;

import structure.IOperation;
import structure.IStructure;
import structure.factory.AbstractStructureFactory;
import structure.impl.Graph;
import structure.impl.Operation;

/**
 * Factory that is able to create a graph
 * @author David Mendez-Acuna
 *
 */
public class GraphFactory extends AbstractStructureFactory {

	@Override
	public IStructure createSolutionStructure(Integer[][] A,
			ArrayList<String> problemFiles, ArrayList<BetaVO> betas)
			throws Exception {
		IStructure graph = createSolutionGraph(A, problemFiles.get(0), betas);
		return graph;
	}

	@Override
	public IStructure createSolutionStructure(ArrayList<String> problemFiles,
			ArrayList<BetaVO> betas) throws Exception {
		return new Graph(problemFiles.get(0), betas);
	}

	@Override
	public IOperation createIOperation() {
		return new Operation();
	}

	public IStructure createSolutionGraph(Integer[][] A,
			String problemMatrix, ArrayList<BetaVO> betas) throws Exception{

		int jobsAmount = A.length;
		int stationsAmount = A[0].length;

		IStructure graph = new Graph(problemMatrix, betas);
		graph.setTotalJobs(jobsAmount);
		graph.setTotalStations(stationsAmount);

		// Creating the routes
		for (int i = 0; i < jobsAmount; i++) {
			// Creating the route array
			ArrayList<Integer> routeArrayInA = new ArrayList<Integer>();
			for (int j = 0; j < stationsAmount; j++) {
				routeArrayInA.add(A[i][j]);
			}

			// Sorting the array
			for (int index = routeArrayInA.size(); index > 0; index--) {
				for (int index2 = 0; index2 < index - 1; index2++) {
					Integer p1 = routeArrayInA.get(index2);
					Integer p2 = routeArrayInA.get(index2 + 1);

					if (p1.intValue() > p2.intValue()) {
						routeArrayInA.set(index2, p2);
						routeArrayInA.set(index2 + 1, p1);
					}
				}
			}

			// Obtaining the route order
			ArrayList<Integer> realRouteArray = new ArrayList<Integer>();
			for (int j = 0; j < stationsAmount; j++) {
				realRouteArray.add(routeArrayInA.indexOf(A[i][j]));
			}

			ArrayList<Integer> finalRouteArray = new ArrayList<Integer>();
			for (int j = 0; j < stationsAmount; j++) {
				finalRouteArray.add(realRouteArray.indexOf(j));
			}

			Integer[] routearray = (Integer[]) finalRouteArray
					.toArray(new Integer[finalRouteArray.size()]);

			// Building the route
			((Graph)graph).setInitialJobNode(i, ((Graph)graph).getNode(i, routearray[0]));
			
			
			for (int k = 0; k < (routearray.length - 1); k++) {
				OperationIndexVO start = new OperationIndexVO(i, routearray[k]);
				OperationIndexVO end = new OperationIndexVO(i, routearray[k + 1]);
				
				((Graph)graph).createRouteArc(start, end);
			}

			((Graph)graph).getNode(i, routearray[routearray.length - 1])
					.setNextRouteNode(null);
		}

		// Creating the sequences
		for (int i = 0; i < stationsAmount; i++) {
			// Creating the sequence array
			ArrayList<Integer> sequenceArrayInA = new ArrayList<Integer>();
			for (int j = 0; j < jobsAmount; j++) {
				sequenceArrayInA.add(A[j][i]);
			}

			// Sorting the array
			for (int index = sequenceArrayInA.size(); index > 0; index--) {
				for (int index2 = 0; index2 < index - 1; index2++) {
					Integer p1 = sequenceArrayInA.get(index2);
					Integer p2 = sequenceArrayInA.get(index2 + 1);

					if (p1.intValue() > p2.intValue()) {
						sequenceArrayInA.set(index2, p2);
						sequenceArrayInA.set(index2 + 1, p1);
					}
				}
			}

			// Obtaining the sequence order
			ArrayList<Integer> realSequenceArray = new ArrayList<Integer>();
			for (int j = 0; j < jobsAmount; j++) {
				realSequenceArray.add(sequenceArrayInA.indexOf(A[j][i]));
			}

			ArrayList<Integer> finalSequenceArray = new ArrayList<Integer>();
			for (int j = 0; j < jobsAmount; j++) {
				finalSequenceArray.add(realSequenceArray.indexOf(j));
			}

			Integer[] sequencearray = (Integer[]) finalSequenceArray
					.toArray(new Integer[finalSequenceArray.size()]);

			// Building the sequence
			((Graph)graph).setInitialStationNode(i,
					((Graph)graph).getNode(sequencearray[0], i));

			for (int k = 0; k < (sequencearray.length - 1); k++) {
				OperationIndexVO start = new OperationIndexVO(sequencearray[k], i);
				OperationIndexVO end = new OperationIndexVO(sequencearray[k + 1], i);
				((Graph)graph).createSequenceArc(start, end);
			}

			((Graph)graph).getNode(sequencearray[sequencearray.length - 1], i)
					.setNextSequenceNode(null);
		}
		
		return graph;
	}
}
