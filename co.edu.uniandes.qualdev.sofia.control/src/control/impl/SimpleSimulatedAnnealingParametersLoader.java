package control.impl;

import java.util.Properties;

import control.IParametersLoader;

/**
 * Implementation of a parameters loader for the case of the simulated annealing
 * @author David
 *
 */
public class SimpleSimulatedAnnealingParametersLoader implements IParametersLoader{

	@Override
	public Properties loadParameters(Properties algorithmConfiguration) {
		Properties params = new Properties();
		params.put("T0", Double.parseDouble((String) algorithmConfiguration
				.getProperty("params.T0")));
		params.put("k", Integer.parseInt((String) algorithmConfiguration
				.getProperty("params.k")));
		params.put("Tf", Double.parseDouble((String) algorithmConfiguration
				.getProperty("params.Tf")));
		params.put("coolingFactor", Double.parseDouble((String) algorithmConfiguration
				.getProperty("params.coolingFactor")));
		params.put("non-improving-in", Integer.parseInt((String) algorithmConfiguration
				.getProperty("params.non-improving-in")));
		params.put("non-improving-out", Integer.parseInt((String) algorithmConfiguration
				.getProperty("params.non-improving-out")));
		params.put("restarts", Integer.parseInt((String) algorithmConfiguration
				.getProperty("params.restarts")));
		params.put("boltzmann", Double.parseDouble((String) algorithmConfiguration
				.getProperty("params.boltzmann")));
		
		if(algorithmConfiguration.getProperty("params.maxNumberImprovements")!=null){
			System.out.println("entro");
			params.put("maxNumberImprovements", Integer.parseInt(algorithmConfiguration.getProperty("params.maxNumberImprovements")));
		}
		
		if(algorithmConfiguration.getProperty("params.maxExecutionTime")!=null){
			params.put("maxExecutionTime", Integer.parseInt(algorithmConfiguration.getProperty("params.maxExecutionTime")));
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
