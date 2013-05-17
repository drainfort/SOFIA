package launcher.generator.ui;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import launcher.generator.vos.AlgorithmConfigurationVO;

public class AlgorithmDefinitionPanel extends JPanel {

	// ----------------------------------------------------
	// Constants
	// ----------------------------------------------------
	
	private static final long serialVersionUID = 8150013403300202285L;

	// ----------------------------------------------------
	// Attributes
	// ----------------------------------------------------
	
	private ArrayList<JRadioButton> initialSolutionButtons;
	
	private ArrayList<JRadioButton> metaheuristicsButtons;
	
	private ArrayList<JRadioButton> neighborhoodButtons;
	
	private ArrayList<JRadioButton> modifierButtons;
	
	private ArrayList<JRadioButton> representationButtons;
	
	private ArrayList<JRadioButton> gammaButtons;
	
	// ----------------------------------------------------
	// Constructor
	// ----------------------------------------------------
	
	public AlgorithmDefinitionPanel(){
		this.setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 5, 0 ), new TitledBorder( "Algorithm definition" ) ) );
		this.setLayout(new GridLayout(3,2));
		
		initialSolutionButtons = new ArrayList<JRadioButton>();
		metaheuristicsButtons = new ArrayList<JRadioButton>();
		neighborhoodButtons = new ArrayList<JRadioButton>();
		modifierButtons = new ArrayList<JRadioButton>();
		representationButtons = new ArrayList<JRadioButton>();
		gammaButtons = new ArrayList<JRadioButton>();
		
		JPanel initialSolutionPanel = new JPanel();
		initialSolutionPanel.setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 5, 0 ), new TitledBorder( "Initial solution" ) ) );
		initialSolutionPanel.setLayout( new GridLayout(2,4));
		
		JRadioButton lptRadioButton = new JRadioButton("LPT");
		JRadioButton lrptRadioButton = new JRadioButton("LRPT");
		JRadioButton sptRadioButton = new JRadioButton("SPT");
		JRadioButton srptRadioButton = new JRadioButton("SRPT");
		JRadioButton bestRadioButton = new JRadioButton("Best");
		JRadioButton randomRadioButton = new JRadioButton("Random");
		
		initialSolutionPanel.add(lptRadioButton);
		initialSolutionPanel.add(lrptRadioButton);
		initialSolutionPanel.add(sptRadioButton);
		initialSolutionPanel.add(srptRadioButton);
		initialSolutionPanel.add(bestRadioButton);
		initialSolutionPanel.add(randomRadioButton);

		initialSolutionButtons.add(lptRadioButton);
		initialSolutionButtons.add(lrptRadioButton);
		initialSolutionButtons.add(sptRadioButton);
		initialSolutionButtons.add(srptRadioButton);
		initialSolutionButtons.add(bestRadioButton);
		initialSolutionButtons.add(randomRadioButton);
		
		ButtonGroup initialSolutionGroup = new ButtonGroup();
		initialSolutionGroup.add(lptRadioButton);
		initialSolutionGroup.add(lrptRadioButton);
		initialSolutionGroup.add(sptRadioButton);
		initialSolutionGroup.add(srptRadioButton);
		initialSolutionGroup.add(bestRadioButton);
		initialSolutionGroup.add(randomRadioButton);
		
		this.add(initialSolutionPanel);
		
		JPanel metaheuristicsPanel = new JPanel();
		metaheuristicsPanel.setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 5, 0 ), new TitledBorder( "Metaheuristic" ) ) );
		metaheuristicsPanel.setLayout(new GridLayout(2,2));
		
		JRadioButton tabucn = new JRadioButton(AlgorithmConfigurationVO.TABU_SEARCH_COMPLETE_NEIGHBORHOOD);
		JRadioButton taburn = new JRadioButton(AlgorithmConfigurationVO.TABU_SEARCH_RESTRICTED_NEIGHBORHOOD);
		JRadioButton simulatedAnnealing = new JRadioButton(AlgorithmConfigurationVO.SIMULATED_ANNELING);
		JRadioButton grasp = new JRadioButton(AlgorithmConfigurationVO.GRASP);
		
		metaheuristicsPanel.add(tabucn);
		metaheuristicsPanel.add(taburn);
		metaheuristicsPanel.add(simulatedAnnealing);
		metaheuristicsPanel.add(grasp);
		
		metaheuristicsButtons.add(tabucn);
		metaheuristicsButtons.add(taburn);
		metaheuristicsButtons.add(simulatedAnnealing);
		metaheuristicsButtons.add(grasp);
		
		ButtonGroup metaheuristicGroup = new ButtonGroup();
		metaheuristicGroup.add(tabucn);
		metaheuristicGroup.add(taburn);
		metaheuristicGroup.add(simulatedAnnealing);
		metaheuristicGroup.add(grasp);
		
		this.add(metaheuristicsPanel);
		
		JPanel neighborhoodPanel = new JPanel();
		neighborhoodPanel.setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 5, 0 ), new TitledBorder( "Neighborhood" ) ) );
		neighborhoodPanel.setLayout(new GridLayout(2,2));
		
		JRadioButton random = new JRadioButton(AlgorithmConfigurationVO.RANDOM_NEIGHBORHOOD);
		JRadioButton adjacent = new JRadioButton(AlgorithmConfigurationVO.CRITICAL_ADJACENT);
		JRadioButton adjacentMachine = new JRadioButton(AlgorithmConfigurationVO.CRITICAL_ADJACENT_MACHINES);
		JRadioButton randomInCriticalBlock = new JRadioButton(AlgorithmConfigurationVO.CRITICAL_BLOCK);
		
		neighborhoodPanel.add(random);
		neighborhoodPanel.add(adjacent);
		neighborhoodPanel.add(adjacentMachine);
		neighborhoodPanel.add(randomInCriticalBlock);
		
		neighborhoodButtons.add(random);
		neighborhoodButtons.add(adjacent);
		neighborhoodButtons.add(adjacentMachine);
		neighborhoodButtons.add(randomInCriticalBlock);
		
		ButtonGroup neighGroup = new ButtonGroup();
		neighGroup.add(random);
		neighGroup.add(adjacent);
		neighGroup.add(adjacentMachine);
		neighGroup.add(randomInCriticalBlock);
		
		this.add(neighborhoodPanel);
		
		JPanel ModifierPanel = new JPanel();
		ModifierPanel.setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 5, 0 ), new TitledBorder( "Modifier" ) ) );
		ModifierPanel.setLayout(new GridLayout(2,2));
		
		JRadioButton swap = new JRadioButton(AlgorithmConfigurationVO.SWAP);
		JRadioButton leftInsertion = new JRadioButton(AlgorithmConfigurationVO.LEFT_INSERTION);
		JRadioButton rightInsertion = new JRadioButton(AlgorithmConfigurationVO.RIGHT_INSERTION);
		JRadioButton randomModifier = new JRadioButton(AlgorithmConfigurationVO.RANDOM_MODFIER);
		
		ModifierPanel.add(swap);
		ModifierPanel.add(leftInsertion);
		ModifierPanel.add(rightInsertion);
		ModifierPanel.add(randomModifier);
		
		modifierButtons.add(swap);
		modifierButtons.add(leftInsertion);
		modifierButtons.add(rightInsertion);
		modifierButtons.add(randomModifier);
		
		ButtonGroup modifierGroup = new ButtonGroup();
		modifierGroup.add(swap);
		modifierGroup.add(leftInsertion);
		modifierGroup.add(rightInsertion);
		modifierGroup.add(randomModifier);
		
		this.add(ModifierPanel);
		
		JPanel representationPanel = new JPanel();
		representationPanel.setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 5, 0 ), new TitledBorder( "Representation" ) ) );
		representationPanel.setLayout(new GridLayout(1,2));
		
		JRadioButton vector = new JRadioButton(AlgorithmConfigurationVO.VECTOR);
		JRadioButton graph = new JRadioButton(AlgorithmConfigurationVO.GRAPH);
		
		representationPanel.add(vector);
		representationPanel.add(graph);
		
		representationButtons.add(vector);
		representationButtons.add(graph);
		
		ButtonGroup representationGroup = new ButtonGroup();
		representationGroup.add(vector);
		representationGroup.add(graph);
		
		this.add(representationPanel);
		
		JPanel gammaPanel = new JPanel();
		gammaPanel.setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 5, 0 ), new TitledBorder( "Objective function" ) ) );
		gammaPanel.setLayout(new GridLayout(1,2));
		
		JRadioButton cmax = new JRadioButton("CMax");
		JRadioButton mft = new JRadioButton("Mean flow time");
		
		gammaPanel.add(cmax);
		gammaPanel.add(mft);
		
		gammaButtons.add(cmax);
		gammaButtons.add(mft);
		
		ButtonGroup gammaGroup = new ButtonGroup();
		gammaGroup.add(cmax);
		gammaGroup.add(mft);
		
		this.add(gammaPanel);
	}

	// ----------------------------------------------------
	// Methods
	// ----------------------------------------------------
	
	public AlgorithmConfigurationVO getAlgorithmDefinition() {
		AlgorithmConfigurationVO answer = new AlgorithmConfigurationVO();
		
		for (JRadioButton radioButton : initialSolutionButtons) {
			if(radioButton.isSelected()){
				answer.setInitialSolutionBuilder(radioButton.getText());
				break;
			}
		}
		
		for (JRadioButton radioButton : metaheuristicsButtons) {
			if(radioButton.isSelected()){
				answer.setMetaheuristic(radioButton.getText());
				break;
			}
		}
		
		for (JRadioButton radioButton : neighborhoodButtons) {
			if(radioButton.isSelected()){
				answer.setNeighborhood(radioButton.getText());
				break;
			}
		}
		
		for (JRadioButton radioButton : modifierButtons) {
			if(radioButton.isSelected()){
				answer.setModifier(radioButton.getText());
				break;
			}
		}
		
		for (JRadioButton radioButton : representationButtons) {
			if(radioButton.isSelected()){
				answer.setRepresentation(radioButton.getText());
				break;
			}
		}
		
		for (JRadioButton radioButton : gammaButtons) {
			if(radioButton.isSelected()){
				answer.setObjectiveFunction(radioButton.getText());
				break;
			}
		}
		return answer;
	}

}
