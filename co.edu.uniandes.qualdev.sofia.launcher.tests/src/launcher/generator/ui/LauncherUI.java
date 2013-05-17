package launcher.generator.ui;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;

import launcher.generator.JarGenerator;
import launcher.generator.vos.AlgorithmConfigurationVO;

public class LauncherUI extends JFrame{

	// ----------------------------------------------------
	// Attributes
	// ----------------------------------------------------
	
	private ConfigurationPanel configurationPanel;
	
	private ExecutionPanel executionPanel;
	
	// ----------------------------------------------------
	// Constructor
	// ----------------------------------------------------
	
	public LauncherUI(){
		this.setLayout(new BorderLayout());
		this.setSize(790, 720);
		
		configurationPanel = new ConfigurationPanel();
		this.add(configurationPanel, BorderLayout.NORTH);
		
		executionPanel = new ExecutionPanel(this);
		this.add(executionPanel, BorderLayout.CENTER);
	}
	
	// ----------------------------------------------------
	// Methods
	// ----------------------------------------------------
	
	public void generateJars(ArrayList<String> instancesToExecute, AlgorithmConfigurationVO algorithmDefinition){
		String workspacePath = configurationPanel.getWorkspacePath();
		String eclipsePath = configurationPanel.getEclipsePath();
		
		JarGenerator jarsGenerator = new JarGenerator(workspacePath, eclipsePath);
		
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
