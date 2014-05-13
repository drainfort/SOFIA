package control.impl;

import java.util.Properties;

import control.IParametersLoader;

/**
 * Implementation of a parameters loader for the case of the simulated annealing
 * @author David
 *
 */
public class EnrichedSimulatedAnnealingParametersLoader implements IParametersLoader{

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
		params.put("non-improving", Integer.parseInt((String) algorithmConfiguration
				.getProperty("params.non-improving")));
		params.put("restarts", Integer.parseInt((String) algorithmConfiguration
				.getProperty("params.restarts")));
		params.put("boltzmann", Double.parseDouble((String) algorithmConfiguration
				.getProperty("params.boltzmann")));
		
		
		params.put("printTable", Boolean.parseBoolean((String) algorithmConfiguration
				.getProperty("report.consolidationTable")));
		
		params.put("printInitialSolution", Boolean.parseBoolean((String) algorithmConfiguration
				.getProperty("report.gantt.initialsolutions")));
		
		params.put("printSolutions", Boolean.parseBoolean((String) algorithmConfiguration
				.getProperty("report.gantt.bestsolutions")));
		
		params.put("printLog", Boolean.parseBoolean((String) algorithmConfiguration
				.getProperty("report.gantt.log")));
		
		params.put("printImprovement", Boolean.parseBoolean((String) algorithmConfiguration
				.getProperty("report.gantt.improvement")));
		
		if(algorithmConfiguration.getProperty("params.maxNumberImprovements")!=null){
			System.out.println("entro");
			params.put("maxNumberImprovements", Integer.parseInt(algorithmConfiguration.getProperty("params.maxNumberImprovements")));
		}
		return params;
		
	}

}
