package beta;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Interface that contains the methods that a Beta must to implement
 * 
 * @author Rubby Casallas
 * @author David Mendez-Acuna
 *
 */
public abstract class Beta {

	// ---------------------------------------------------------------------------------------------
	// Attributes
	// ---------------------------------------------------------------------------------------------
	
	/**
	 * If the beta most be considered in the problem
	 */
	private boolean considered;
	
	// ---------------------------------------------------------------------------------------------
	// Methods
	// ---------------------------------------------------------------------------------------------
	
	// ---------------------------------------------------------------------------------------------
	// Getters and Setters
	// ---------------------------------------------------------------------------------------------
		
	public boolean isConsidered() {
		return considered;
	}

	public void setConsidered(boolean considered) {
		this.considered = considered;
	}
	
	// ---------------------------------------------------------------------------------------------
	// Abstract methods
	// ---------------------------------------------------------------------------------------------
		
	/**
	 * Loads the information relative to the beta from a given collection of files.
	 * @param informationFiles. The collection of files that contain the information about the beta.
	 * @throws IOException - Method error
	 */
	abstract public void loadBeta(ArrayList<String> informationFiles) throws IOException;

	/**
	 * Returns the collection of files that contain the information about the beta.
	 * @return the information files for the specific beta
	 */
	abstract public ArrayList<String> getInformationFiles();

	/**
	 * Sets the collection of files that contain the information about the beta.
	 * @param informationFiles - information files that contains the data of the beta
	 */
	abstract public void setInformationFiles(ArrayList<String> informationFiles);
	
	/**
	 * Clones the object.
	 */
	abstract public Beta clone();
}
