package launcher.generator.ui;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;

import launcher.generator.JarGenerator;
import launcher.generator.vos.AlgorithmConfigurationVO;

public class LauncherUI extends JFrame{

	// ----------------------------------------------------
	// Constants
	// ----------------------------------------------------
	
	private static final long serialVersionUID = 1L;

	// ----------------------------------------------------
	// Attributes
	// ----------------------------------------------------
	
	private ConfigurationPanel configurationPanel;
	
	private ExecutionPanel executionPanel;
	
	private JarGenerator jarsGenerator;
	
	// ----------------------------------------------------
	// Constructor
	// ----------------------------------------------------
	
	public LauncherUI(){
		this.setLayout(new BorderLayout());
		this.setSize(790, 830);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		configurationPanel = new ConfigurationPanel();
		this.add(configurationPanel, BorderLayout.NORTH);
		
		executionPanel = new ExecutionPanel(this);
		this.add(executionPanel, BorderLayout.CENTER);
	}
	
	// ----------------------------------------------------
	// Methods
	// ----------------------------------------------------
	
	public void generateJars(ArrayList<String> instancesToExecute,String instancesType, AlgorithmConfigurationVO algorithmDefinition){
		String workspacePath = configurationPanel.getWorkspacePath();
		String eclipsePath = configurationPanel.getEclipsePath();
		jarsGenerator = new JarGenerator(workspacePath, eclipsePath);
		
		ParametersDialog dialog = new ParametersDialog(this, instancesToExecute, instancesType, algorithmDefinition, false);
		dialog.setLocationRelativeTo(this);
		dialog.setVisible(true);
		
		
		
		
	}
	
	public void generateJars2(ArrayList<String> instancesToExecute, AlgorithmConfigurationVO algorithmDefinition){
		jarsGenerator.generateJavaFiles(instancesToExecute, algorithmDefinition);
	}
	
	public void execute(ArrayList<String> instancesToExecute, String instancesType, AlgorithmConfigurationVO algorithmDefinition){
		ParametersDialog dialog = new ParametersDialog(this, instancesToExecute, instancesType, algorithmDefinition, true);
		dialog.setLocationRelativeTo(this);
		dialog.setVisible(true);
	}
	
	/**
	 * Main method. Execution start
	 * @param args. Execution arguments
	 */
	public static void main(String[] args) {
		LauncherUI launcher = new LauncherUI();
		launcher.setVisible(true);
	}
}
