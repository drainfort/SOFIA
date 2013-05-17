package launcher.generator.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import launcher.generator.vos.AlgorithmConfigurationVO;

public class ExecutionPanel extends JPanel implements ActionListener {

	// -----------------------------------------------------
	// Constants
	// -----------------------------------------------------
	
	public static final String GENERATE_JARS = "generate_jars";
	
	public static final String EXECUTE = "execute";
	
	// -----------------------------------------------------
	// Attributes
	// -----------------------------------------------------
	
	private ProblemPanel problemPanel;
	
	private AlgorithmDefinitionPanel algorithmDefinitionPanel;
	
	private JButton btnGenerateJars;
	
	private JButton btnExecute;
	
	// -----------------------------------------------------
	// Constructor
	// -----------------------------------------------------
	
	public ExecutionPanel(){
		this.setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 5, 0 ), new TitledBorder( "Execution" ) ) );
		this.setLayout(new BorderLayout());
		
		problemPanel = new ProblemPanel();
		this.add(problemPanel, BorderLayout.NORTH);
		
		algorithmDefinitionPanel = new AlgorithmDefinitionPanel();
		this.add(algorithmDefinitionPanel, BorderLayout.CENTER);
		
		JPanel panelButtons = new JPanel();
		
		btnGenerateJars = new JButton("Generate jars");
		btnGenerateJars.setActionCommand(GENERATE_JARS);
		btnGenerateJars.addActionListener(this);
		panelButtons.add(btnGenerateJars);
		
		btnExecute = new JButton("Generate jars and execute in parallel");
		btnExecute.setActionCommand(EXECUTE);
		btnExecute.addActionListener(this);
		panelButtons.add(btnExecute);
		
		this.add(panelButtons, BorderLayout.SOUTH);
	}

	// -----------------------------------------------------
	// Methods
	// -----------------------------------------------------

	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		
		if(command.equals(GENERATE_JARS)){
			ArrayList<String> instancesToExecute = problemPanel.getSelectedInstances();
			System.out.println(instancesToExecute);
			AlgorithmConfigurationVO algorithmDefinition = algorithmDefinitionPanel.getAlgorithmDefinition();
			System.out.println(algorithmDefinition);
		}else if(command.equals(EXECUTE)){
			ArrayList<String> instancesToExecute = problemPanel.getSelectedInstances();
			System.out.println(instancesToExecute);
			AlgorithmConfigurationVO algorithmDefinition = algorithmDefinitionPanel.getAlgorithmDefinition();
			System.out.println(algorithmDefinition);
		}
	}
}
