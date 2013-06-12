package initialSolBuilder.impl;

import gammaCalculator.IGammaCalculator;
import initialSolBuilder.IInitialSolBuilder;
import java.util.ArrayList;
import structure.IStructure;
import structure.factory.AbstractStructureFactory;
import afs.AFSInitialSolutionCreator;
import common.types.BetaVO;

/**
 * Initial solution creator that uses the dispatching rule called "LPT"
 * implemented in the AFS software component
 * 
 * @author Gonzalo Mejía
 * @author David Méndez Acuña
 */
public class AFSLPTNonDelay implements IInitialSolBuilder{

	// ----------------------------------------------------
	// Constants
	// ----------------------------------------------------
	
	private static final String constructiveAlgorithmName = "AFSLPTNonDelay";
	
	// -----------------------------------------------
	// Atributes
	// -----------------------------------------------
	
	private String name;
	
	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------
	
	public AFSLPTNonDelay(){
		name = constructiveAlgorithmName;
	}
	
	// -----------------------------------------------
	// Methods
	// -----------------------------------------------
	
	@Override
	public IStructure createInitialSolution(ArrayList<String> problemFiles,
			ArrayList<BetaVO> pBetas, String structureFactory,
			IGammaCalculator gammaCalculator) throws Exception {
		
		String TFile = problemFiles.get(0);
		String TTFile = problemFiles.get(1);
		
		IStructure structure = AbstractStructureFactory.createNewInstance(structureFactory).createSolutionStructure(AFSInitialSolutionCreator.getInstance().createInitialSolution("LPT", TFile, TTFile), problemFiles, pBetas);
		structure.calculateCMatrix();
		return structure;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public IStructure createInitialSolution(Integer[][] TMatrix,
			Integer[][] TTMatrix, Integer[][] STMatrix,
			String structureFactory, IGammaCalculator gammaCalculator,
			IStructure structure) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}