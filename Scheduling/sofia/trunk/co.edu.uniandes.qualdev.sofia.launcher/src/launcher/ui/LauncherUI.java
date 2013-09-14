package launcher.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import launcher.vos.AlgorithmConfigurationVO;

public class LauncherUI extends JFrame{

	// ----------------------------------------------------
	// Constants
	// ----------------------------------------------------
	
	private static final long serialVersionUID = 1L;

	// ----------------------------------------------------
	// Attributes
	// ----------------------------------------------------
	
	private ConfigurationPanel configurationPanel;
	
	private AlgorithmDefinitionPanel algorithmDefinitionPanel;
	
	private ButtonPanel buttons;
	
	private ProblemPanel problemPanel;
	
	private ProblemPanel problemPanel1;
	
	// ----------------------------------------------------
	// Constructor
	// ----------------------------------------------------
	
	public LauncherUI() {
		this.setLayout(new BorderLayout());
		this.setSize(800, 830);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane();
		configurationPanel = new ConfigurationPanel();
		algorithmDefinitionPanel = new AlgorithmDefinitionPanel();
		buttons = new ButtonPanel(this);
		
		
		JPanel problem = new JPanel();
		problem.setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 5, 0 ), new TitledBorder( "Execution" ) ) );
		problem.setLayout(new BorderLayout());
		
		problemPanel = new ProblemPanel(true);
		problemPanel1 = new ProblemPanel(false);
		
		problem.add(configurationPanel, BorderLayout.NORTH);
		
		JPanel problem1 = new JPanel();
		problem1.setLayout(new GridLayout(2, 1));
		problem1.add(problemPanel);
		problem1.add(problemPanel1);
		problem.add(problem1, BorderLayout.CENTER);
		
		tabbedPane.addTab("Problem", problem);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
         
        tabbedPane.addTab("Solution method", algorithmDefinitionPanel);
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
        		
		this.add(buttons, BorderLayout.SOUTH);
		this.add(tabbedPane, BorderLayout.CENTER);
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}
	
	// ----------------------------------------------------
	// Methods
	// ----------------------------------------------------
	
	public void execute(ArrayList<String> instancesToExecute, String instancesType, AlgorithmConfigurationVO algorithmDefinition){
		ParametersDialog dialog = new ParametersDialog(this, instancesToExecute, instancesType, algorithmDefinition, true);
		dialog.setLocationRelativeTo(this);
		dialog.setVisible(true);
	}
	
	public ArrayList<String> getSelectedInstances() {
		
		String a= problemPanel.getSelectedBenchmark();
		if(!a.equals("")){
			return problemPanel.getSelectedInstances();
		}
		return problemPanel1.getSelectedInstances();
	}

	public String getSelectedBenchmark() {
		System.out.println();
		String a= problemPanel.getSelectedBenchmark();
		String b= problemPanel1.getSelectedBenchmark();
		if(!a.equals("")){
			return a;
		}
		return b;
	}

	public AlgorithmConfigurationVO getAlgorithmDefinition() {
		return algorithmDefinitionPanel.getAlgorithmDefinition();
	}
}