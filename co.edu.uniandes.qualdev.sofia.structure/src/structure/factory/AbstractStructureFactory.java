package structure.factory;

import java.util.ArrayList;

import structure.IOperation;
import structure.IStructure;
import structure.impl.decoding.Decoding;
import common.types.BetaVO;


public abstract class AbstractStructureFactory {

	// -------------------------------------------------
	// Methods
	// -------------------------------------------------
	/**
	 * Creates a new instance of the factory according to the name of the
	 * concrete factory
	 * 
	 * @param className - name of the class that implement the structure
	 * @return factory The corresponding factory
	 * @throws InstantiationException - Method error
	 * @throws IllegalAccessException - Method error
	 * @throws ClassNotFoundException - Method error
	 */
	public static AbstractStructureFactory createNewInstance(String className)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		AbstractStructureFactory factory = (AbstractStructureFactory) AbstractStructureFactory.class
				.getClassLoader().loadClass(className).newInstance();

		return factory;
	}

	/**
	 * Creates a new solution vector from a file that contains the corresponding
	 * processing times and a rank matrix
	 * @param A - matrix of rankings 
	 * @param problemFiles - name of the files with the data of the problem
	 * @param decondingStrategy - component that performs the decoding strategy
	 * @param betas - Restrictions of the problem
	 * @return vector Solution vector
	 * @throws Exception - Method error
	 */
	public abstract IStructure createSolutionStructure(Integer[][] A,
			ArrayList<String> problemFiles, ArrayList<BetaVO> betas, Decoding decondingStrategy)
					throws Exception;
	
	/**
	 * Creates a new empty solution vector
	 * 
	 * @param problemFiles - name of the files with the data of the problem
	 * @param betas - array of betas included in the problem
	 * @param decondingStrategy - component that performs the decoding strategy
	 * @return vector Empty solution vector
	 * @throws Exception - Method error
	 */
	public abstract IStructure createSolutionStructure(ArrayList<String> problemFiles, ArrayList<BetaVO> betas, Decoding decondingStrategy)
					throws Exception;
	
	/**
	 * Creates an empty iOperation
	 * @return operation. Emtpy IOperation
	 */
	public abstract IOperation createIOperation();
}
