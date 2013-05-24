package launcher.generator.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
	
	private static final long serialVersionUID = 1L;

	public static final String ACCEPT = "accept";
	
	public static final String CANCEL = "cancel";
	
	// ------------------------------------------------------------------------------------------
	// Attributes
	// ------------------------------------------------------------------------------------------
	
	private AlgorithmConfigurationVO algorithmConfiguration;
	
	private ArrayList<String> instancesToExecute;
	
	private String instancesType;
	
	private ArrayList<ParamComponent> parameters;
	
	private JButton acceptButton;
	
	private JButton cancelButton;
	
	// ------------------------------------------------------------------------------------------
	// Constructor
	// ------------------------------------------------------------------------------------------
	
	public ParametersDialog(LauncherUI launcherUI, ArrayList<String> instancesToExecute, String instancesType, AlgorithmConfigurationVO algorithmConfiguration){
		super( launcherUI, true );
		this.algorithmConfiguration = algorithmConfiguration;
		this.instancesToExecute = instancesToExecute;
		this.instancesType = instancesType;
		parameters = new ArrayList<ParamComponent>();
		
		//TODO ParametersDialog: Reorganizar para evitar c�digo redundante
		this.setResizable(true);
		this.setLayout(new BorderLayout());
		this.setSize(450,180);
		
		// Case 1: The selected meta-heuristic is TabuSearch -> complete neighborhood
		if((this.algorithmConfiguration.getMetaheuristic().equals(AlgorithmConfigurationVO.TABU_SEARCH_COMPLETE_NEIGHBORHOOD))){
			this.setTitle("Metaheuristic parameters: Tabu search complete neighborhood");
			JPanel panelParameters = new JPanel();
			panelParameters.setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 5, 0 ), new TitledBorder( "Parameters" ) ) );
			panelParameters.setLayout(new GridLayout(4,2));
			this.setSize(450,200);
			
			//Non-improving
			JLabel labNonImproving = new JLabel("non-improving");
			JTextField txtNonImproving = new JTextField("0.6");
			panelParameters.add(labNonImproving);
			panelParameters.add(txtNonImproving);
			
			ParamComponent paramNonImproving = new ParamComponent(labNonImproving, txtNonImproving);
			parameters.add(paramNonImproving);
			
			//Restarts
			JLabel labRestarts = new JLabel("restarts");
			JTextField txtRestarts = new JTextField("0");
			panelParameters.add(labRestarts);
			panelParameters.add(txtRestarts);
			
			ParamComponent paramRestarts = new ParamComponent(labRestarts, txtRestarts);
			parameters.add(paramRestarts);
			
			//Max number of improvements
			JLabel labMaxImprovements = new JLabel("maxNumberImprovements");
			JTextField txtMaxImprovements = new JTextField("1");
			panelParameters.add(labMaxImprovements);
			panelParameters.add(txtMaxImprovements);
			
			ParamComponent paramMaxImprovements = new ParamComponent(labMaxImprovements, txtMaxImprovements);
			parameters.add(paramMaxImprovements);
			
			// Amount of executions by instance
			JLabel labAmountExecutionsByInstance = new JLabel("amountOfExecutionsPerInstance");
			JTextField txtAmountExecutionsByInstance = new JTextField("1");
			panelParameters.add(labAmountExecutionsByInstance);
			panelParameters.add(txtAmountExecutionsByInstance);
			
			ParamComponent paramAmountExecutionsByInstance = new ParamComponent(labAmountExecutionsByInstance, txtAmountExecutionsByInstance);
			parameters.add(paramAmountExecutionsByInstance);
			
			this.add(panelParameters, BorderLayout.CENTER);
		}
		// Case 2:  The selected meta-heuristic is TabuSearch -> restricted neighborhood
		else if(this.algorithmConfiguration.getMetaheuristic().equals(AlgorithmConfigurationVO.TABU_SEARCH_RESTRICTED_NEIGHBORHOOD)){
			this.setTitle("Metaheuristic parameters: Tabu search restricted neighborhood");
			this.setSize(450,220);
			JPanel panelParameters = new JPanel();
			panelParameters.setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 5, 0 ), new TitledBorder( "Parameters" ) ) );
			panelParameters.setLayout(new GridLayout(5,2));
			
			//Percent
			JLabel labPercent = new JLabel("percent");
			JTextField txtPercent = new JTextField("0.7");
			panelParameters.add(labPercent);
			panelParameters.add(txtPercent);
			
			ParamComponent paramPercent = new ParamComponent(labPercent, txtPercent);
			parameters.add(paramPercent);
			
			//Non-improving
			JLabel labNonImproving = new JLabel("non-improving");
			JTextField txtNonImproving = new JTextField("0.6");
			panelParameters.add(labNonImproving);
			panelParameters.add(txtNonImproving);
			
			ParamComponent paramNonImproving = new ParamComponent(labNonImproving, txtNonImproving);
			parameters.add(paramNonImproving);
			
			//Restarts
			JLabel labRestarts = new JLabel("restarts");
			JTextField txtRestarts = new JTextField("0");
			panelParameters.add(labRestarts);
			panelParameters.add(txtRestarts);
			
			ParamComponent paramRestarts = new ParamComponent(labRestarts, txtRestarts);
			parameters.add(paramRestarts);
			
			//Max number of improvements
			JLabel labMaxImprovements = new JLabel("maxNumberImprovements");
			JTextField txtMaxImprovements = new JTextField("1");
			panelParameters.add(labMaxImprovements);
			panelParameters.add(txtMaxImprovements);
			
			ParamComponent paramMaxImprovements = new ParamComponent(labMaxImprovements, txtMaxImprovements);
			parameters.add(paramMaxImprovements);
			
			// Amount of executions by instance
			JLabel labAmountExecutionsByInstance = new JLabel("amountOfExecutionsPerInstance");
			JTextField txtAmountExecutionsByInstance = new JTextField("1");
			panelParameters.add(labAmountExecutionsByInstance);
			panelParameters.add(txtAmountExecutionsByInstance);
			
			ParamComponent paramAmountExecutionsByInstance = new ParamComponent(labAmountExecutionsByInstance, txtAmountExecutionsByInstance);
			parameters.add(paramAmountExecutionsByInstance);
			
			this.add(panelParameters, BorderLayout.CENTER);
		}
		// Case 3: The selected meta-heuristic is SimulatedAnnealing
		else if(this.algorithmConfiguration.getMetaheuristic().equals(AlgorithmConfigurationVO.SIMULATED_ANNELING)){
			this.setTitle("Metaheuristic parameters: Simulated annealing");
			JPanel panelParameters = new JPanel();
			panelParameters.setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 5, 0 ), new TitledBorder( "Parameters" ) ) );
			panelParameters.setLayout(new GridLayout(8,2));
			this.setSize(450,290);
			
			//T0
			JLabel labT0 = new JLabel("T0");
			JTextField txtT0 = new JTextField();
			panelParameters.add(labT0);
			panelParameters.add(txtT0);
			
			ParamComponent paramT0 = new ParamComponent(labT0, txtT0);
			parameters.add(paramT0);
			
			//Tf
			JLabel labTf = new JLabel("Tf");
			JTextField txtTf = new JTextField();
			panelParameters.add(labTf);
			panelParameters.add(txtTf);
			
			ParamComponent paramTf = new ParamComponent(labTf, txtTf);
			parameters.add(paramTf);
			
			//boltzmann
			JLabel labBoltzmann = new JLabel("boltzmann");
			JTextField txtBoltzmann = new JTextField();
			panelParameters.add(labBoltzmann);
			panelParameters.add(txtBoltzmann);
			
			ParamComponent paramBoltzmann = new ParamComponent(labBoltzmann, txtBoltzmann);
			parameters.add(paramBoltzmann);
			
			//k
			JLabel labK = new JLabel("k");
			JTextField txtK = new JTextField();
			panelParameters.add(labK);
			panelParameters.add(txtK);
			
			ParamComponent paramK= new ParamComponent(labK, txtK);
			parameters.add(paramK);
			
			//coolingFactor
			JLabel labCoolingFactor = new JLabel("coolingFactor");
			JTextField txtcoolingFactor = new JTextField();
			panelParameters.add(labCoolingFactor);
			panelParameters.add(txtcoolingFactor);
			
			ParamComponent paramCoolingFactor= new ParamComponent(labCoolingFactor, txtcoolingFactor);
			parameters.add(paramCoolingFactor);
			
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
			
			// Amount of executions by instance
			JLabel labAmountExecutionsByInstance = new JLabel("amountOfExecutionsPerInstance");
			JTextField txtAmountExecutionsByInstance = new JTextField("1");
			panelParameters.add(labAmountExecutionsByInstance);
			panelParameters.add(txtAmountExecutionsByInstance);
			
			ParamComponent paramAmountExecutionsByInstance = new ParamComponent(labAmountExecutionsByInstance, txtAmountExecutionsByInstance);
			parameters.add(paramAmountExecutionsByInstance);
			
			this.add(panelParameters, BorderLayout.CENTER);
		}
		// Case 4: The selected meta-heuristic is GRASP
		else if(this.algorithmConfiguration.getMetaheuristic().equals(AlgorithmConfigurationVO.GRASP)){
			this.setTitle("Metaheuristic parameters: GRASP");
			JPanel panelParameters = new JPanel();
			panelParameters.setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 5, 0 ), new TitledBorder( "Parameters" ) ) );
			panelParameters.setLayout(new GridLayout(4,2));
			this.setSize(450,200);
			
			//strategyLS
			JLabel labStrategyLS = new JLabel("strategyLS");
			JTextField txtStrategyLS = new JTextField("0");
			panelParameters.add(labStrategyLS);
			panelParameters.add(txtStrategyLS);
			
			ParamComponent paramStrategyLS = new ParamComponent(labStrategyLS, txtStrategyLS);
			parameters.add(paramStrategyLS);
			
			//maxLSDepth
			JLabel labMaxLSDepth = new JLabel("maxLSDepth");
			JTextField txtMaxLSDepth = new JTextField("999");
			panelParameters.add(labMaxLSDepth);
			panelParameters.add(txtMaxLSDepth);
			
			ParamComponent paramMaxLSDepth = new ParamComponent(labMaxLSDepth, txtMaxLSDepth);
			parameters.add(paramMaxLSDepth);
			
			//maxLSDepth
			JLabel labMaxNeighbors = new JLabel("maxNeighbors");
			JTextField txtMaxNeighbors = new JTextField("2000");
			panelParameters.add(labMaxNeighbors);
			panelParameters.add(txtMaxNeighbors);
			
			ParamComponent paramMaxNeighbors = new ParamComponent(labMaxNeighbors, txtMaxNeighbors);
			parameters.add(paramMaxNeighbors);
			
			// Amount of executions by instance
			JLabel labAmountExecutionsByInstance = new JLabel("amountOfExecutionsPerInstance");
			JTextField txtAmountExecutionsByInstance = new JTextField("1");
//			panelParameters.add(labAmountExecutionsByInstance);
//			panelParameters.add(txtAmountExecutionsByInstance);
			
			ParamComponent paramAmountExecutionsByInstance = new ParamComponent(labAmountExecutionsByInstance, txtAmountExecutionsByInstance);
			parameters.add(paramAmountExecutionsByInstance);
			
			this.add(panelParameters, BorderLayout.CENTER);
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
				paramVO.setName("params." + param.getLabel().getText());
				paramVO.setValue(param.getText().getText());
				algorithmConfiguration.getMetaheuristicParams().add(paramVO);
				
				if(param.getLabel().getText().equals("amountOfExecutionsPerInstance")){
					int amountOfExecutionsPerInstance = Integer.parseInt(param.getText().getText());
					algorithmConfiguration.setAmountOfExecutionsPerInstance(amountOfExecutionsPerInstance);
				}
			}
			
			ProgrammaticLauncher launcher = new ProgrammaticLauncher();
			try {
				String fileName =  "./results/Om_TT/experiment-results-" + System.currentTimeMillis() + ".html";
				launcher.launch(instancesToExecute, instancesType, algorithmConfiguration, fileName);
				JOptionPane.showMessageDialog(this, "Results saved in: " + fileName);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.dispose();
		}else if(command.equals(CANCEL)){
			this.dispose();
		}
	}
}