package launcher.generator.ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class LauncherUI extends JFrame{

	// ----------------------------------------------------
	// Attributes
	// ----------------------------------------------------
	
	private ConfigurationPanel configurationPanel;
	
	private ExecutionPanel executionPanel;
	
	private JarsGenerationPanel jarsGenerationPanel;
	
	// ----------------------------------------------------
	// Constructor
	// ----------------------------------------------------
	
	public LauncherUI(){
		this.setLayout(new BorderLayout());
		this.setSize(500, 500);
		
		configurationPanel = new ConfigurationPanel();
		this.add(configurationPanel, BorderLayout.NORTH);
		
		executionPanel = new ExecutionPanel();
		this.add(executionPanel, BorderLayout.CENTER);
		
		jarsGenerationPanel = new JarsGenerationPanel();
		this.add(jarsGenerationPanel, BorderLayout.SOUTH);
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
