package algorithm;

import initialSolBuilder.IInitialSolBuilder;

/**
 * Represents a constructive algorithm for the construction
 * of an initial solution for a given problem. 
 * 
 * @author David Mendez-Acuna
 * @author Oriana Cendales
 */
public class ConstructiveAlgorithm {

	// -----------------------------------------------
	// Attributes
	// -----------------------------------------------
	
	private IInitialSolBuilder initialSolutionBuilder;
	
	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------
	
	/**
	 * Creates a constructive algorithm from the parameters
	 * 
	 * @param initialSolutionBuilderClassName
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public ConstructiveAlgorithm(String initialSolutionBuilderClassName) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		System.out.println("initialSolutionBuilderClassName: " + initialSolutionBuilderClassName);
		initialSolutionBuilder = (IInitialSolBuilder) getClass()
				.getClassLoader().loadClass(initialSolutionBuilderClassName).newInstance();
	}
	
	// -----------------------------------------------
	// Methods
	// -----------------------------------------------
	
	public IInitialSolBuilder getInitialSolutionBuilder() {
		return initialSolutionBuilder;
	}

	public void setInitialSolutionBuilder(IInitialSolBuilder initialSolutionBuilder) {
		this.initialSolutionBuilder = initialSolutionBuilder;
	}
}