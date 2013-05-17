package launcher.generator.ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

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
		this.setSize(700, 700);
		
		configurationPanel = new ConfigurationPanel();
		this.add(configurationPanel, BorderLayout.NORTH);
		
		executionPanel = new ExecutionPanel();
		this.add(executionPanel, BorderLayout.CENTER);
	}
	
	// ----------------------------------------------------
	// Methods
	// ----------------------------------------------------
	
	/**
	 * Main method. Execution start
	 * @param args. Execution arguments
	 */
	public static void main(String[] args) {
		LauncherUI launcher = new LauncherUI();
		launcher.setVisible(true);
	}

}
