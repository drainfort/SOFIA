package initialSolBuilder.impl;

import gammaCalculator.IGammaCalculator;
import initialSolBuilder.IInitialSolBuilder;
import java.util.ArrayList;
import structure.IStructure;
import structure.factory.impl.VectorFactory;
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
			ArrayList<BetaVO> betas, String structureFactory,
			IGammaCalculator gammaCalculator) throws Exception {
		
		String TFile = problemFiles.get(0);
		String TTFile = problemFiles.get(1);
		return VectorFactory.createNewInstance("structure.factory.impl.VectorFactory").createSolutionStructure(AFSInitialSolutionCreator.getInstance().createInitialSolution("LPT", TFile, TTFile), problemFiles, betas);
	}

	@Override
	public String getName() {
		return name;
	}
}