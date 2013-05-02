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

	/**
	 * Loads the information relative to the beta from a given collection of files.
	 * @param informationFiles. The collection of files that contain the information about the beta.
	 * @throws IOException
	 */
	abstract public void loadBeta(ArrayList<String> informationFiles) throws IOException;

	/**
	 * Returns the collection of files that contain the information about the beta.
	 * @return
	 */
	abstract public ArrayList<String> getInformationFiles();

	/**
	 * Sets the collection of files that contain the information about the beta.
	 * @param informationFiles
	 */
	abstract public void setInformationFiles(ArrayList<String> informationFiles);
	
	/**
	 * Clones the object.
	 */
	abstract public Beta clone();
}
