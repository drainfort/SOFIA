package control.impl;

import java.util.Properties;

import control.IParametersLoader;

public class TabuSearchRNParametersLoader  implements IParametersLoader{

	@Override
	public Properties loadParameters(Properties algorithmConfiguration) {
		Properties params = new Properties();

		params.put("percent", Double.parseDouble((String) algorithmConfiguration
				.getProperty("params.percent")));
		
		params.put("non-improving", Double.parseDouble((String) algorithmConfiguration
				.getProperty("params.non-improving")));
		
		params.put("tabulist-size", Integer.parseInt((String) algorithmConfiguration
				.getProperty("params.tabulist-size")));

		if(algorithmConfiguration.getProperty("params.maxNumberImprovements")!=null){
			params.put("maxNumberImprovements", Integer.parseInt(algorithmConfiguration.getProperty("params.maxNumberImprovements")));
		}
		
		params.put("printTable", Boolean.parseBoolean((String) algorithmConfiguration
				.getProperty("report.consolidationTable")));
		
		params.put("printInitialSolution", Boolean.parseBoolean((String) algorithmConfiguration
				.getProperty("report.gantt.initialsolutions")));
		
		params.put("printSolutions", Boolean.parseBoolean((String) algorithmConfiguration
				.getProperty("report.gantt.bestsolutions")));
		return params;
		
	}

}