package common.utils;

import java.util.Hashtable;

/**
 * Class that stores the global parameters of the algorithm
 * @author David Mendez-Acuna
 */
public class AlgorithmParameters {

	// --------------------------------------------
	// Attributes
	// --------------------------------------------
	
	/** Hash table that stores the parameters */
	private Hashtable<String, String> algorithmParameters;
	
	/** Name of the instance that is going to be executed */
	private static AlgorithmParameters instance;
	
	// --------------------------------------------
	// Constructor
	// --------------------------------------------
	
	/**
	 * Default constructor of the class
	 */
	private AlgorithmParameters(){
		algorithmParameters = new Hashtable<String, String>();
	}
	
	/**
	 * Returns the unique instance of the class
	 * @return the unique instance of this class
	 */
	public static AlgorithmParameters getInstance(){
		if(instance==null)
			instance = new AlgorithmParameters();
		return instance;
	}
	
	// --------------------------------------------
	// Methods
	// --------------------------------------------
	
	/**
	 * Adds a new parameter to the has table
	 * @param name - name of the parameter
	 * @param value - value of the parameter
	 * @throws Exception If there exist a parameter with the same name
	 */
	public void addParameter(String name, String value) throws Exception{
		if(algorithmParameters.get(name)!=null)
			throw new Exception("The parameter already exists");
		
		algorithmParameters.put(name, value);
	}
	
	/**
	 * Changes the value of the parameter in the hash table
	 * If the parameter do not exists, it is created
	 * @param name - name of the parameter
	 * @param value - value of the parameter
	 */
	public void setParameter(String name, String value){
		algorithmParameters.put(name, value);
	}

	/**
	 * Returns the value of an algorithm parameter by the given name
	 * @param parameterName. Name of the parameter
	 * @return the value of the parameter with the name that enters by parameter
	 */
	public String getParameterValue(String parameterName) {
		return algorithmParameters.get(parameterName);
	}
}
