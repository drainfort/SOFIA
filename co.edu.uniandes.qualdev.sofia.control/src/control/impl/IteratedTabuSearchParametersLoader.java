package control.impl;

import java.util.Properties;

import control.IParametersLoader;

public class IteratedTabuSearchParametersLoader  implements IParametersLoader{

	@Override
	public Properties loadParameters(Properties algorithmConfiguration) {
		Properties params = new Properties();

		params.put("non-improving", Double.parseDouble((String) algorithmConfiguration
				.getProperty("params.non-improving")));
		
		params.put("tabulist-size", Double.parseDouble((String) algorithmConfiguration
				.getProperty("params.tabulist-size")));
		
		params.put("iterations", Integer.parseInt((String) algorithmConfiguration
				.getProperty("params.iterations")));
		
		params.put("non-improving-out", Integer.parseInt((String) algorithmConfiguration
				.getProperty("params.non-improving-out")));
		
		params.put("neighborhodSize", Integer.parseInt((String) algorithmConfiguration
				.getProperty("params.neighborhodSize")));
			
		
		if(algorithmConfiguration.getProperty("params.maxNumberImprovements")!=null){
			params.put("maxNumberImprovements", Integer.parseInt(algorithmConfiguration.getProperty("params.maxNumberImprovements")));
		}
		
		params.put("printTable", Boolean.parseBoolean((String) algorithmConfiguration
				.getProperty("report.consolidationTable")));
		
		params.put("printInitialSolution", Boolean.parseBoolean((String) algorithmConfiguration
				.getProperty("report.gantt.initialsolutions")));
		
		params.put("printSolutions", Boolean.parseBoolean((String) algorithmConfiguration
				.getProperty("report.gantt.bestsolutions")));
		
		params.put("printLog", Boolean.parseBoolean((String) algorithmConfiguration
				.getProperty("report.gantt.log")));
		return params;
		
	}

}