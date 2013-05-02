package control.impl;

import java.util.Properties;

import control.IParametersLoader;

public class RandomWalkParametersLoader  implements IParametersLoader{

	@Override
	public Properties loadParameters(Properties algorithmConfiguration) {
		Properties params = new Properties();


		params.put("k", Integer.parseInt((String) algorithmConfiguration
				.getProperty("params.k")));

		return params;
		
	}

}