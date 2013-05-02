package control.impl;

import java.util.Properties;

import control.IParametersLoader;

public class TabuSearchParametersLoader  implements IParametersLoader{

	@Override
	public Properties loadParameters(Properties algorithmConfiguration) {
		Properties params = new Properties();

		params.put("non-improving", Double.parseDouble((String) algorithmConfiguration
				.getProperty("params.non-improving")));

		params.put("percent", Double.parseDouble((String) algorithmConfiguration
				.getProperty("params.percent")));
		
		if(algorithmConfiguration.getProperty("params.maxNumberImprovements")!=null){
			params.put("maxNumberImprovements", Integer.parseInt(algorithmConfiguration.getProperty("params.maxNumberImprovements")));
		}
		return params;
		
	}

}