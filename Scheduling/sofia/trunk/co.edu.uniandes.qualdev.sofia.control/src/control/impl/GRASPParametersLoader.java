package control.impl;

import java.util.Properties;

import control.IParametersLoader;

/**
 * Implementation of a parameters loader for the case of GRASP
 * @author David
 *
 */
public class GRASPParametersLoader implements IParametersLoader{

	@Override
	public Properties loadParameters(Properties algorithmConfiguration) {
		Properties params = new Properties();
		params.put("alfa", Double.parseDouble((String) algorithmConfiguration
				.getProperty("params.alfa")));
		
		if(algorithmConfiguration.getProperty("params.maxNumberImprovements")!=null){
			System.out.println("entro");
			params.put("maxNumberImprovements", Integer.parseInt(algorithmConfiguration.getProperty("params.maxNumberImprovements")));
		}
		return params;
		
	}

}
