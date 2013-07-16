package structure.factory.impl;

import java.util.ArrayList;

import structure.IOperation;
import structure.IStructure;
import structure.factory.AbstractStructureFactory;
import structure.impl.Operation;
import structure.impl.Vector;

import common.types.BetaVO;
import common.types.OperationIndexVO;

/**
 * Factory that is able to create a graph
 * 
 * @author David Mendez-Acuna
 * 
 */
public class VectorFactory extends AbstractStructureFactory {

	// ---------------------------------------------
	// Methods
	// ---------------------------------------------
	
	@Override
	public IStructure createSolutionStructure(Integer[][] A,
			ArrayList<String> problemFiles, ArrayList<BetaVO> betas)
			throws Exception {
			
		IStructure solutionVector = new Vector(problemFiles.get(0), betas);
		
		int jobsNumber = A.length;
		int totalStations = A[0].length;
		
		int currentRank = 1;
		int operationsAmount = 0;
		boolean matrixCompleted = false;

		while (!matrixCompleted) {
			for (int i = 0; i < jobsNumber; i++) {
				for (int j = 0; j < totalStations; j++) {
					if (A[i][j].intValue() == currentRank) {
						solutionVector.scheduleOperation(new OperationIndexVO(i,j));
						operationsAmount++;

						if (operationsAmount >= jobsNumber * totalStations) {
							matrixCompleted = true;
						}
					}
				}
			}
			currentRank++;
		}
		

		return solutionVector;
	
		
	}

	@Override
	public IStructure createSolutionStructure(ArrayList<String> problemFiles, ArrayList<BetaVO> betas) throws Exception {
		if(problemFiles.size()<3){
			IStructure solutionVector = new Vector(problemFiles.get(0), betas);
			return solutionVector;
		}
		else{
			System.out.println("entro");
			IStructure solutionVector = new Vector(problemFiles.get(0), problemFiles.get(2) , betas);
			
			return null;
		}
	}
	
	@Override
	public IOperation createIOperation() {
		return new Operation();
	}
}