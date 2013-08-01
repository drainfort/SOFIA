package algorithm;

import java.util.Properties;

import structure.IStructure;
import common.utils.ExecutionResults;
import control.Control;
import control.IParametersLoader;
import modifier.IModifier;
import neighborCalculator.INeighborCalculator;
import gammaCalculator.IGammaCalculator;

/**
 * Represents a solution algorithm
 * 
 * @author David Mendez-Acuna
 * 
 */
public class IterativeAlgorithm {

	// -----------------------------------------------
	// Attributes
	// -----------------------------------------------

	/**
	 * Component that defines the control of the meta-heuristic
	 */
	private Control control;

	/**
	 * Component that loads the parameters of the meta-heuristic.
	 */
	private IParametersLoader parametersLoader;
	
	/**
	 * Component that is creates a neighbor from a given solution
	 */
	private INeighborCalculator neighborCalculator;

	private IModifier modifier;
	
	/**
	 * Component that calculates the corresponding gamma for a given solution
	 */
	private IGammaCalculator gammaCalculator;

	/**
	 * The file that contains the matrix A of the initial solution
	 */
	private String initialSolutionFile;

	/**
	 * The file with the information about the configuration of the algorithm 
	 */
	private Properties algorithmConfiguration;

	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------

	public IterativeAlgorithm(Properties algorithmConfiguration)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		this.algorithmConfiguration = algorithmConfiguration;

		control = (Control) getClass()
				.getClassLoader()
				.loadClass(
						algorithmConfiguration
								.getProperty("scheduling.control"))
				.newInstance();
		neighborCalculator = (INeighborCalculator) getClass()
				.getClassLoader()
				.loadClass(
						algorithmConfiguration
								.getProperty("scheduling.neighborCalculator"))
				.newInstance();
		modifier = (IModifier) getClass()
				.getClassLoader()
				.loadClass(
						algorithmConfiguration
								.getProperty("scheduling.modifier"))
				.newInstance();
		gammaCalculator = (IGammaCalculator) getClass()
				.getClassLoader()
				.loadClass(
						algorithmConfiguration
								.getProperty("scheduling.gammaCalculator"))
				.newInstance();
		parametersLoader = (IParametersLoader) getClass()
				.getClassLoader()
				.loadClass(
						algorithmConfiguration
								.getProperty("scheduling.parametersLoader"))
				.newInstance();
		initialSolutionFile = (String) algorithmConfiguration
				.getProperty("scheduling.initialSolutionFile");
	}

	// -----------------------------------------------
	// Methods
	// -----------------------------------------------

	public ExecutionResults execute(SchedulingProblem problem, IStructure initialSolution) throws Exception {
		//TODO fix the true always
		control.setInstanceName(problem.getInstanceName());
		control.setResultFile(problem.getResultFile());

		return control.execute(initialSolution, neighborCalculator, modifier, gammaCalculator,
				parametersLoader.loadParameters(this.algorithmConfiguration),problem.getCurrentBksValue(), problem.isHasOptimal());
	}

	// -----------------------------------------------
	// Getters and setters
	// -----------------------------------------------

	public Control getControl() {
		return control;
	}

	public void setControl(Control control) {
		this.control = control;
	}

	public INeighborCalculator getNeighborCalculator() {
		return neighborCalculator;
	}

	public void setNeighborCalculator(INeighborCalculator neighborCalculator) {
		this.neighborCalculator = neighborCalculator;
	}

	public IGammaCalculator getGammaCalculator() {
		return gammaCalculator;
	}

	public void setGammaCalculator(IGammaCalculator gammaCalculator) {
		this.gammaCalculator = gammaCalculator;
	}

	public IParametersLoader getParametersLoader() {
		return parametersLoader;
	}

	public void setParametersLoader(IParametersLoader parametersLoader) {
		this.parametersLoader = parametersLoader;
	}

	public String getInitialSolutionFile() {
		return initialSolutionFile;
	}

	public void setInitialSolutionFile(String initialSolutionFile) {
		this.initialSolutionFile = initialSolutionFile;
	}
}