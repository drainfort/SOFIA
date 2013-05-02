package structure.factory;

import java.util.ArrayList;

import structure.IOperation;
import structure.IStructure;

import common.types.BetaVO;


public abstract class AbstractStructureFactory {

	/**
	 * Creates a new instance of the factory according to the name of the
	 * concrete factory
	 * 
	 * @param className
	 * @return factory The corresponding factory
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
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
	 * @param arrayList 
	 * @param problem 
	 * 
	 * @param processingTimesFile
	 *            File that contains the processing times of the problem
	 * @param betas
	 * @return vector Solution vector
	 * @throws Exception
	 */
	public abstract IStructure createSolutionStructure(Integer[][] A,
			ArrayList<String> problemFiles, ArrayList<BetaVO> betas)
					throws Exception;
	
	/**
	 * Creates a new empty solution vector
	 * 
	 * @return vector Empty solution vector
	 * @throws Exception
	 */
	public abstract IStructure createSolutionStructure(ArrayList<String> problemFiles, ArrayList<BetaVO> betas)
					throws Exception;
	
	/**
	 * Creates an empty iOperation
	 * @return operation. Emtpy IOperation
	 */
	public abstract IOperation createIOperation();
}
