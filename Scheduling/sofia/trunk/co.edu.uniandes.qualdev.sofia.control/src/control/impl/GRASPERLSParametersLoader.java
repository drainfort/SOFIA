package control.impl;

import java.util.Properties;

import control.IParametersLoader;

/**
 * Implementation of a parameters loader for the case of GRASP
 * @author David
 *
 */
public class GRASPERLSParametersLoader implements IParametersLoader{

	@Override
	public Properties loadParameters(Properties algorithmConfiguration) {
		Properties params = new Properties();
		params.put("strategyLS", Integer.parseInt((String) algorithmConfiguration
				.getProperty("params.strategyLS")));
		params.put("maxLSDepth", Integer.parseInt((String) algorithmConfiguration
				.getProperty("params.maxLSDepth")));
		params.put("maxNeighbors", Integer.parseInt((String) algorithmConfiguration
				.getProperty("params.maxNeighbors")));		
		params.put("printTable", Boolean.parseBoolean((String) algorithmConfiguration
				.getProperty("report.consolidationTable")));
		
		params.put("printInitialSolution", Boolean.parseBoolean((String) algorithmConfiguration
				.getProperty("report.gantt.initialsolutions")));
		
		params.put("printSolutions", Boolean.parseBoolean((String) algorithmConfiguration
				.getProperty("report.gantt.bestsolutions")));
		return params;
		
	}

}
