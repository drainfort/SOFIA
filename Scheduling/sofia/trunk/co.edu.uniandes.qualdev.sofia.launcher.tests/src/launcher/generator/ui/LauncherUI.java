package launcher.generator.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

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
	
	private ButtonPanel buttons;
	
	private JarGenerator jarsGenerator;
	

	
	// ----------------------------------------------------
	// Constructor
	// ----------------------------------------------------
	
	public LauncherUI() {
		this.setLayout(new BorderLayout());
		this.setSize(790, 830);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane();
		configurationPanel = new ConfigurationPanel();
		executionPanel = new ExecutionPanel();
		buttons = new ButtonPanel(this);
		
		
		JPanel problem = new JPanel();
		problem.setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 5, 0 ), new TitledBorder( "Execution" ) ) );
		problem.setLayout(new BorderLayout());
		
		ProblemPanel problemPanel = new ProblemPanel(true);
		ProblemPanel problemPanel1 = new ProblemPanel(false);
		
		problem.add(configurationPanel, BorderLayout.NORTH);
		
		JPanel problem1 = new JPanel();
		problem1.setLayout(new GridLayout(2, 1));
		problem1.add(problemPanel);
		problem1.add(problemPanel1);
		problem.add(problem1, BorderLayout.CENTER);
		
		tabbedPane.addTab("Problem", problem);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
         
        tabbedPane.addTab("Algorythm", executionPanel);
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
        
        

		
		
		
		this.add(buttons, BorderLayout.SOUTH);
        
		this.add(tabbedPane, BorderLayout.CENTER);
		
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
		
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
