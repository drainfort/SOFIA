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

import launcher.GUILauncher;
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
	private boolean execute=true;
	
	private LauncherUI launcher=null;
	// ------------------------------------------------------------------------------------------
	// Constructor
	// ------------------------------------------------------------------------------------------
	
	public ParametersDialog(LauncherUI launcherUI, ArrayList<String> instancesToExecute, String instancesType, AlgorithmConfigurationVO algorithmConfiguration, boolean execute){
		super( launcherUI, true );
		launcher=launcherUI;
		this.execute=execute;
		this.algorithmConfiguration = algorithmConfiguration;
		this.instancesToExecute = instancesToExecute;
		this.instancesType = instancesType;
		parameters = new ArrayList<ParamComponent>();
		
		//TODO ParametersDialog: Reorganizar para evitar código redundante
		this.setResizable(true);
		this.setLayout(new BorderLayout());
		this.setSize(450,180);
		
		// Case 1: The selected meta-heuristic is TabuSearch -> complete neighborhood
		if((this.algorithmConfiguration.getMetaheuristic().equals(AlgorithmConfigurationVO.TABU_SEARCH_COMPLETE_NEIGHBORHOOD))){
			this.setTitle("Metaheuristic parameters: Tabu search complete neighborhood");
			JPanel panelParameters = new JPanel();
			panelParameters.setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 5, 0 ), new TitledBorder( "Parameters" ) ) );
			panelParameters.setLayout(new GridLayout(6,2));
			this.setSize(450,200);
			
			//tabulist-size
			JLabel labTabuListSize = new JLabel("tabulist-size");
			JTextField txtTabuListSize = new JTextField("10");
			panelParameters.add(labTabuListSize);
			panelParameters.add(txtTabuListSize);
			
			ParamComponent paramTabuListSize = new ParamComponent(labTabuListSize, txtTabuListSize);
			parameters.add(paramTabuListSize);
			
			//Non-improving-in
			JLabel labNonImprovingIn = new JLabel("non-improving-in");
			JTextField txtNonImprovingIn = new JTextField("50");
			panelParameters.add(labNonImprovingIn);
			panelParameters.add(txtNonImprovingIn);
			
			ParamComponent paramNonImprovingIn = new ParamComponent(labNonImprovingIn, txtNonImprovingIn);
			parameters.add(paramNonImprovingIn);
			
			//Non-improving-out
			JLabel labNonImprovingOut = new JLabel("non-improving-out");
			JTextField txtNonImprovingOut = new JTextField("50");
			panelParameters.add(labNonImprovingOut);
			panelParameters.add(txtNonImprovingOut);
			
			ParamComponent paramNonImprovingOut = new ParamComponent(labNonImprovingOut, txtNonImprovingOut);
			parameters.add(paramNonImprovingOut);
			
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
			this.setSize(480,280);
			JPanel panelParameters = new JPanel();
			panelParameters.setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 5, 0 ), new TitledBorder( "Parameters" ) ) );
			panelParameters.setLayout(new GridLayout(9,2));
			
			//Iterations
			JLabel labPercent = new JLabel("iterations");
			JTextField txtPercent = new JTextField("390");
			panelParameters.add(labPercent);
			panelParameters.add(txtPercent);
			
			ParamComponent paramPercent = new ParamComponent(labPercent, txtPercent);
			parameters.add(paramPercent);
			
			//Neighborhod
			JLabel labNeighborhod = new JLabel("neighborhodSize");
			JTextField txtNeighborhod  = new JTextField("100");
			panelParameters.add(labNeighborhod);
			panelParameters.add(txtNeighborhod);
			
			ParamComponent paramNeighborhod = new ParamComponent(labNeighborhod, txtNeighborhod);
			parameters.add(paramNeighborhod);
			
			//tabulist-size
			JLabel labTabuListSize = new JLabel("tabulist-size");
			JTextField txtTabuListSize = new JTextField("10");
			panelParameters.add(labTabuListSize);
			panelParameters.add(txtTabuListSize);
			
			ParamComponent paramTabuListSize = new ParamComponent(labTabuListSize, txtTabuListSize);
			parameters.add(paramTabuListSize);
			
			//Non-improving-in
			JLabel labNonImprovingIn = new JLabel("non-improving-in");
			JTextField txtNonImprovingIn = new JTextField("50");
			panelParameters.add(labNonImprovingIn);
			panelParameters.add(txtNonImprovingIn);
			
			ParamComponent paramNonImprovingIn = new ParamComponent(labNonImprovingIn, txtNonImprovingIn);
			parameters.add(paramNonImprovingIn);
			
			//Non-improving-out
			JLabel labNonImprovingOut = new JLabel("non-improving-out");
			JTextField txtNonImprovingOut = new JTextField("50");
			panelParameters.add(labNonImprovingOut);
			panelParameters.add(txtNonImprovingOut);
			
			ParamComponent paramNonImprovingOut = new ParamComponent(labNonImprovingOut, txtNonImprovingOut);
			parameters.add(paramNonImprovingOut);
			
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
			
			JLabel labMaxTime= new JLabel("maxExecutionTime");
			JTextField txtmaxExecutionTime = new JTextField("1000");
			panelParameters.add(labMaxTime);
			panelParameters.add(txtmaxExecutionTime);
			
			ParamComponent paramMaxTime = new ParamComponent(labMaxTime, txtmaxExecutionTime);
			parameters.add(paramMaxTime);
			
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
			panelParameters.setLayout(new GridLayout(11,2));
			this.setSize(450,290);
			
			//T0
			JLabel labT0 = new JLabel("T0");
			JTextField txtT0 = new JTextField("50");
			panelParameters.add(labT0);
			panelParameters.add(txtT0);
			
			ParamComponent paramT0 = new ParamComponent(labT0, txtT0);
			parameters.add(paramT0);
			
			//Tf
			JLabel labTf = new JLabel("Tf");
			JTextField txtTf = new JTextField("1");
			panelParameters.add(labTf);
			panelParameters.add(txtTf);
			
			ParamComponent paramTf = new ParamComponent(labTf, txtTf);
			parameters.add(paramTf);
			
			//boltzmann
			JLabel labBoltzmann = new JLabel("boltzmann");
			JTextField txtBoltzmann = new JTextField("10");
			panelParameters.add(labBoltzmann);
			panelParameters.add(txtBoltzmann);
			
			ParamComponent paramBoltzmann = new ParamComponent(labBoltzmann, txtBoltzmann);
			parameters.add(paramBoltzmann);
			
			//k
			JLabel labK = new JLabel("k");
			JTextField txtK = new JTextField("1000");
			panelParameters.add(labK);
			panelParameters.add(txtK);
			
			ParamComponent paramK= new ParamComponent(labK, txtK);
			parameters.add(paramK);
			
			//coolingFactor
			JLabel labCoolingFactor = new JLabel("coolingFactor");
			JTextField txtcoolingFactor = new JTextField("0.975");
			panelParameters.add(labCoolingFactor);
			panelParameters.add(txtcoolingFactor);
			
			ParamComponent paramCoolingFactor= new ParamComponent(labCoolingFactor, txtcoolingFactor);
			parameters.add(paramCoolingFactor);
			
			//Non-improving-in
			JLabel labNonImprovingIn = new JLabel("non-improving-in");
			JTextField txtNonImprovingIn = new JTextField("50");
			panelParameters.add(labNonImprovingIn);
			panelParameters.add(txtNonImprovingIn);
			
			ParamComponent paramNonImprovingIn = new ParamComponent(labNonImprovingIn, txtNonImprovingIn);
			parameters.add(paramNonImprovingIn);
			
			//Non-improving-out
			JLabel labNonImprovingOut = new JLabel("non-improving-out");
			JTextField txtNonImprovingOut = new JTextField("50");
			panelParameters.add(labNonImprovingOut);
			panelParameters.add(txtNonImprovingOut);
			
			ParamComponent paramNonImprovingOut = new ParamComponent(labNonImprovingOut, txtNonImprovingOut);
			parameters.add(paramNonImprovingOut);
			
			//Restarts
			JLabel labRestarts = new JLabel("restarts");
			JTextField txtRestarts = new JTextField("0");
			panelParameters.add(labRestarts);
			panelParameters.add(txtRestarts);
			
			ParamComponent paramRestarts = new ParamComponent(labRestarts, txtRestarts);
			parameters.add(paramRestarts);
			
			JLabel labMaxImprovements = new JLabel("maxNumberImprovements");
			JTextField txtMaxImprovements = new JTextField("1");
			panelParameters.add(labMaxImprovements);
			panelParameters.add(txtMaxImprovements);
			
			ParamComponent paramMaxImprovements = new ParamComponent(labMaxImprovements, txtMaxImprovements);
			parameters.add(paramMaxImprovements);
			
			JLabel labMaxTime= new JLabel("maxExecutionTime");
			JTextField txtmaxExecutionTime = new JTextField("1000");
			panelParameters.add(labMaxTime);
			panelParameters.add(txtmaxExecutionTime);
			
			ParamComponent paramMaxTime = new ParamComponent(labMaxTime, txtmaxExecutionTime);
			parameters.add(paramMaxTime);
			
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
			if(execute){
			
				GUILauncher launcher = new GUILauncher();
				try {
					String fileName =  ""+System.currentTimeMillis() ;

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
				
			}
			else{
				this.launcher.generateJars2(instancesToExecute, algorithmConfiguration);
				this.dispose();
			}
		}else if(command.equals(CANCEL)){
			this.dispose();
		}
	}
}