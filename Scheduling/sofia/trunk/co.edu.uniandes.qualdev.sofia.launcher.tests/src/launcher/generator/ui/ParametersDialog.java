package launcher.generator.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
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
		this.setSize(350,180);
		
		// Case 1: The selected metaheuristic is TabuSearch -> complete neighborhood
		if((this.algorithmConfiguration.getMetaheuristic().equals(AlgorithmConfigurationVO.TABU_SEARCH_COMPLETE_NEIGHBORHOOD))){
			this.setTitle("Metaheuristic parameters: Tabu search complete neighborhood");
			JPanel panelParameters = new JPanel();
			panelParameters.setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 5, 0 ), new TitledBorder( "Parameters" ) ) );
			panelParameters.setLayout(new GridLayout(3,2));
			
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
			
			this.add(panelParameters, BorderLayout.CENTER);
		}
		// Case 2:  The selected metaheuristic is TabuSearch -> restricted neighborhood
		else if(this.algorithmConfiguration.getMetaheuristic().equals(AlgorithmConfigurationVO.TABU_SEARCH_RESTRICTED_NEIGHBORHOOD)){
			this.setTitle("Metaheuristic parameters: Tabu search restricted neighborhood");
			this.setSize(350,200);
			JPanel panelParameters = new JPanel();
			panelParameters.setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 5, 0 ), new TitledBorder( "Parameters" ) ) );
			panelParameters.setLayout(new GridLayout(4,2));
			
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
			
			this.add(panelParameters, BorderLayout.CENTER);
		}
		// Case 3: The selected metaheuristic is SimulatedAnnealing
		else if(this.algorithmConfiguration.getMetaheuristic().equals(AlgorithmConfigurationVO.SIMULATED_ANNELING)){
			this.setTitle("Metaheuristic parameters: Simulated annealing");
			JPanel panelParameters = new JPanel();
			panelParameters.setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 5, 0 ), new TitledBorder( "Parameters" ) ) );
			panelParameters.setLayout(new GridLayout(7,2));
			this.setSize(350,220);
			
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
			
			this.add(panelParameters, BorderLayout.CENTER);
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
				paramVO.setName("params." + param.getLabel().getText());
				paramVO.setValue(param.getText().getText());
				algorithmConfiguration.getMetaheuristicParams().add(paramVO);
			}
			
			ProgrammaticLauncher launcher = new ProgrammaticLauncher();
			//TODO File chooser or something
			try {
				
				String fileName =  "./results/Om_TT/results/" + System.currentTimeMillis() + ".html";
				launcher.launch(instancesToExecute, algorithmConfiguration, fileName);
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
		}else if(command.equals(CANCEL)){
			this.dispose();
		}
	}
}
