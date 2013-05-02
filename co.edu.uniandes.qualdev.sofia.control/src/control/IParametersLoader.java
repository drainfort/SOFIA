package control;

import java.util.Properties;

/**
 * Interface the defines the responsabilities of a parameters loader
 * 
 * @author David Mendez-Acuna
 *
 */
public interface IParametersLoader {

	/**
	 * Loads the corresponding parameters from a properties file
	 * @param config
	 * 			properties that contains the parameters that should be loaded
	 */
	public Properties loadParameters(Properties config);

}
