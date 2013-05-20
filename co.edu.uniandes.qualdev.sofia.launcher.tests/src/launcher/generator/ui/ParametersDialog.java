package launcher.generator.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import launcher.ProgrammaticLauncher;
import launcher.generator.vos.AlgorithmConfigurationVO;
import launcher.generator.vos.ParameterVO;

public class ParametersDialog extends JDialog implements ActionListener{

	// ------------------------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------------------------
	
	public static final String ACCEPT = "accept";
	
	public static final String CANCEL = "cancel";
	
	// ------------------------------------------------------------------------------------------
	// Attributes
	// ------------------------------------------------------------------------------------------
	
	private LauncherUI launcherUI;
	
	private AlgorithmConfigurationVO algorithmConfiguration;
	
	private ArrayList<String> instancesToExecute;
	
	private ArrayList<ParamComponent> parameters;
	
	private JButton acceptButton;
	
	private JButton cancelButton;
	
	// ------------------------------------------------------------------------------------------
	// Constructor
	// ------------------------------------------------------------------------------------------
	
	public ParametersDialog(LauncherUI launcherUI, ArrayList<String> instancesToExecute, AlgorithmConfigurationVO algorithmConfiguration){
		super( launcherUI, true );
		this.launcherUI = launcherUI;
		this.algorithmConfiguration = algorithmConfiguration;
		this.instancesToExecute = instancesToExecute;
		parameters = new ArrayList<ParamComponent>();
		
		this.setResizable(true);
		this.setLayout(new BorderLayout());
		this.setSize(300,180);
		
		// Case 1: The selected metaheuristic is TabuSearch -> complete neighborhood
		if((this.algorithmConfiguration.getMetaheuristic().equals(AlgorithmConfigurationVO.TABU_SEARCH_COMPLETE_NEIGHBORHOOD))){
			this.setTitle("Metaheuristic parameters: Tabu search complete neighborhood");
			JPanel panelParameters = new JPanel();
			panelParameters.setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 5, 0 ), new TitledBorder( "Parameters" ) ) );
			panelParameters.setLayout(new GridLayout(3,2));
			
			//Non-improving
			JLabel labNonImproving = new JLabel("non-improving");
			JTextField txtNonImproving = new JTextField();
			panelParameters.add(labNonImproving);
			panelParameters.add(txtNonImproving);
			
			ParamComponent paramNonImproving = new ParamComponent(labNonImproving, txtNonImproving);
			parameters.add(paramNonImproving);
			
			//Restarts
			JLabel labRestarts = new JLabel("restarts");
			JTextField txtRestarts = new JTextField();
			panelParameters.add(labRestarts);
			panelParameters.add(txtRestarts);
			
			ParamComponent paramRestarts = new ParamComponent(labRestarts, txtRestarts);
			parameters.add(paramRestarts);
			
			//Max number of improvements
			JLabel labMaxImprovements = new JLabel("maxNumberImprovements");
			JTextField txtMaxImprovements = new JTextField();
			panelParameters.add(labMaxImprovements);
			panelParameters.add(txtMaxImprovements);
			
			ParamComponent paramMaxImprovements = new ParamComponent(labMaxImprovements, txtMaxImprovements);
			parameters.add(paramMaxImprovements);
			
			this.add(panelParameters, BorderLayout.CENTER);
		}
		// Case 2:  The selected metaheuristic is TabuSearch -> restricted neighborhood
		else if(this.algorithmConfiguration.getMetaheuristic().equals(AlgorithmConfigurationVO.TABU_SEARCH_RESTRICTED_NEIGHBORHOOD)){
			this.setTitle("Metaheuristic parameters: Tabu search restricted neighborhood");
			JPanel panelParameters = new JPanel();
			panelParameters.setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 5, 0 ), new TitledBorder( "Parameters" ) ) );
			panelParameters.setLayout(new GridLayout(2,2));
			
			//Percent
			JLabel labPercent = new JLabel("percent");
			JTextField txtPercent = new JTextField();
			panelParameters.add(labPercent);
			panelParameters.add(txtPercent);
			
			ParamComponent paramPercent = new ParamComponent(labPercent, txtPercent);
			parameters.add(paramPercent);
			
			//Non-improving
			JLabel labNonImproving = new JLabel("non-improving");
			JTextField txtNonImproving = new JTextField();
			panelParameters.add(labNonImproving);
			panelParameters.add(txtNonImproving);
			
			ParamComponent paramNonImproving = new ParamComponent(labNonImproving, txtNonImproving);
			parameters.add(paramNonImproving);
			
			//Restarts
			JLabel labRestarts = new JLabel("restarts");
			JTextField txtRestarts = new JTextField();
			panelParameters.add(labRestarts);
			panelParameters.add(txtRestarts);
			
			ParamComponent paramRestarts = new ParamComponent(labRestarts, txtRestarts);
			parameters.add(paramRestarts);
			
			//Max number of improvements
			JLabel labMaxImprovements = new JLabel("maxNumberImprovements");
			JTextField txtMaxImprovements = new JTextField();
			panelParameters.add(labMaxImprovements);
			panelParameters.add(txtMaxImprovements);
			
			ParamComponent paramMaxImprovements = new ParamComponent(labMaxImprovements, txtMaxImprovements);
			parameters.add(paramMaxImprovements);
			
			this.add(panelParameters, BorderLayout.CENTER);
		}
		// Case 3: The selected metaheuristic is SimulatedAnnealing
		else if(this.algorithmConfiguration.getMetaheuristic().equals(AlgorithmConfigurationVO.SIMULATED_ANNELING)){
			this.setTitle("Metaheuristic parameters: Simulated annealing");
		}
		// Case 4: The selected metaheuristic is GRASP
		else if(this.algorithmConfiguration.getMetaheuristic().equals(AlgorithmConfigurationVO.GRASP)){
			this.setTitle("Metaheuristic parameters: GRASP");
		}
		
		JPanel buttons = new JPanel();
		
		acceptButton = new JButton("Accept");
		acceptButton.setActionCommand(ACCEPT);
		acceptButton.addActionListener(this);
		buttons.add(acceptButton);
		
		cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand(CANCEL);
		cancelButton.addActionListener(this);
		buttons.add(cancelButton);
		
		this.add(buttons, BorderLayout.SOUTH);
	}
	
	// ------------------------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------------------------

	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		
		if(command.equals(ACCEPT)){
			for (ParamComponent param : parameters) {
				ParameterVO paramVO = new ParameterVO();
				paramVO.setName(param.getLabel().getText());
				paramVO.setValue(param.getText().getText());
			}
			
			ProgrammaticLauncher launcher = new ProgrammaticLauncher();
			launcher.launch(instancesToExecute, algorithmConfiguration);
		}else if(command.equals(CANCEL)){
			this.dispose();
		}
	}
}
