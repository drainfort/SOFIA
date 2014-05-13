package control.impl;

import java.util.Properties;

import control.IParametersLoader;

public class VNSParametersLoader implements IParametersLoader{

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
		
		params.put("iterations", Integer.parseInt((String) algorithmConfiguration
				.getProperty("params.iterations")));
		
		params.put("non-improving-in", Integer.parseInt((String) algorithmConfiguration
				.getProperty("params.non-improving-in")));
		
		params.put("non-improving-out", Integer.parseInt((String) algorithmConfiguration
				.getProperty("params.non-improving-out")));
		
		params.put("tabulist-size", Integer.parseInt((String) algorithmConfiguration
				.getProperty("params.tabulist-size")));
		
		params.put("neighborhodSize", Integer.parseInt((String) algorithmConfiguration
				.getProperty("params.neighborhodSize")));
		
		if(algorithmConfiguration.getProperty("params.maxNumberImprovements")!=null){
			params.put("maxNumberImprovements", Integer.parseInt(algorithmConfiguration.getProperty("params.maxNumberImprovements")));
		}
		
		if(algorithmConfiguration.getProperty("params.maxExecutionTime")!=null){
			params.put("maxExecutionTime", Integer.parseInt(algorithmConfiguration.getProperty("params.maxExecutionTime")));
		}
		if(algorithmConfiguration.getProperty("params.maxTotalIterations")!=null){
			params.put("maxTotalIterations", Integer.parseInt(algorithmConfiguration.getProperty("params.maxTotalIterations")));
		}
		if(algorithmConfiguration.getProperty("params.maxItsWOImprovement")!=null){
			params.put("maxItsWOImprovement", Integer.parseInt(algorithmConfiguration.getProperty("params.maxItsWOImprovement")));
		}
		if(algorithmConfiguration.getProperty("params.neighSize")!=null){
			params.put("neighSize", Integer.parseInt(algorithmConfiguration.getProperty("params.neighSize")));
		}
		
		if(algorithmConfiguration.getProperty("params.ls")!=null){
			params.put("ls", Integer.parseInt(algorithmConfiguration.getProperty("params.ls")));
		}
		
		
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
		
		return params;
	}

}
