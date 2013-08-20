package launcher.generator.ui;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import common.utils.ExecutionLogger;

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
	
	private ArrayList<JCheckBox> betaBoxes;
	
	private ArrayList<JCheckBox> reportBoxes;
	
	// ----------------------------------------------------
	// Constructor
	// ----------------------------------------------------
	
	public AlgorithmDefinitionPanel(){
		this.setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 5, 0 ), new TitledBorder( "Algorithm definition" ) ) );
		this.setLayout(new GridLayout(4,2));
		
		initialSolutionButtons = new ArrayList<JRadioButton>();
		metaheuristicsButtons = new ArrayList<JRadioButton>();
		neighborhoodButtons = new ArrayList<JRadioButton>();
		modifierButtons = new ArrayList<JRadioButton>();
		representationButtons = new ArrayList<JRadioButton>();
		gammaButtons = new ArrayList<JRadioButton>();
		betaBoxes = new ArrayList<JCheckBox>();
		reportBoxes = new ArrayList<JCheckBox>();
		
		JPanel initialSolutionPanel = new JPanel();
		initialSolutionPanel.setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 5, 0 ), new TitledBorder( "Initial solution" ) ) );
		initialSolutionPanel.setLayout( new GridLayout(3,4));
		
		JRadioButton lptRadioButton = new JRadioButton(AlgorithmConfigurationVO.LPTNonDelay);
		JRadioButton lrptRadioButton = new JRadioButton(AlgorithmConfigurationVO.LRPTNonDelay);
		JRadioButton sptRadioButton = new JRadioButton(AlgorithmConfigurationVO.SPTNonDelay);
		JRadioButton srptRadioButton = new JRadioButton(AlgorithmConfigurationVO.SRPTNonDelay);
		JRadioButton bestRadioButton = new JRadioButton(AlgorithmConfigurationVO.BestDispatchingRule);
		bestRadioButton.setSelected(true);
		JRadioButton randomRadioButton = new JRadioButton(AlgorithmConfigurationVO.RandomDispatchingRule);
		JRadioButton stochasticERM = new JRadioButton(AlgorithmConfigurationVO.StochasticERM);
		JRadioButton stochasticLPT = new JRadioButton(AlgorithmConfigurationVO.StochasticLPTNonDelay);
		JRadioButton stochasticSPT = new JRadioButton(AlgorithmConfigurationVO.StochasticSPTNonDelay);
		
		initialSolutionPanel.add(lptRadioButton);
		initialSolutionPanel.add(lrptRadioButton);
		initialSolutionPanel.add(sptRadioButton);
		initialSolutionPanel.add(srptRadioButton);
		initialSolutionPanel.add(bestRadioButton);
		initialSolutionPanel.add(randomRadioButton);
		initialSolutionPanel.add(stochasticERM);
		initialSolutionPanel.add(stochasticLPT);
		initialSolutionPanel.add(stochasticSPT);

		initialSolutionButtons.add(lptRadioButton);
		initialSolutionButtons.add(lrptRadioButton);
		initialSolutionButtons.add(sptRadioButton);
		initialSolutionButtons.add(srptRadioButton);
		initialSolutionButtons.add(bestRadioButton);
		initialSolutionButtons.add(randomRadioButton);
		initialSolutionButtons.add(stochasticERM);
		initialSolutionButtons.add(stochasticLPT);
		initialSolutionButtons.add(stochasticSPT);
		
		ButtonGroup initialSolutionGroup = new ButtonGroup();
		initialSolutionGroup.add(lptRadioButton);
		initialSolutionGroup.add(lrptRadioButton);
		initialSolutionGroup.add(sptRadioButton);
		initialSolutionGroup.add(srptRadioButton);
		initialSolutionGroup.add(bestRadioButton);
		initialSolutionGroup.add(randomRadioButton);
		initialSolutionGroup.add(stochasticERM);
		initialSolutionGroup.add(stochasticLPT);
		initialSolutionGroup.add(stochasticSPT);
		
		this.add(initialSolutionPanel);
		
		JPanel metaheuristicsPanel = new JPanel();
		metaheuristicsPanel.setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 5, 0 ), new TitledBorder( "Metaheuristic" ) ) );
		metaheuristicsPanel.setLayout(new GridLayout(2,2));
		
		JRadioButton tabucn = new JRadioButton(AlgorithmConfigurationVO.TABU_SEARCH_COMPLETE_NEIGHBORHOOD);
		JRadioButton taburn = new JRadioButton(AlgorithmConfigurationVO.TABU_SEARCH_RESTRICTED_NEIGHBORHOOD);
		taburn.setSelected(true);
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
		neighborhoodPanel.setLayout(new GridLayout(4,2));
		
		JRadioButton btnRandomNeighborhood = new JRadioButton(AlgorithmConfigurationVO.RANDOM_NEIGHBORHOOD);
		JRadioButton btnApiNeighborhood = new JRadioButton(AlgorithmConfigurationVO.API_NEIGHBORHOOD);
		JRadioButton btnCRAdjacentNeighborhood = new JRadioButton(AlgorithmConfigurationVO.CRITICAL_ROUTE_ADJACENT_NEIGHBORHOOD);
		JRadioButton btnCRAdjacentMachineNeighborhood = new JRadioButton(AlgorithmConfigurationVO.CRITICAL_ROUTE_ADJACENT_MACHINES_NEIGHBORHOOD);
		JRadioButton btnCRBlockRandomNeighborhood = new JRadioButton(AlgorithmConfigurationVO.CRITICAL_BLOCK_RANDOM_NEIGHBORHOOD);
		JRadioButton btnCRBlockAdjacentOnEndNeighborhood = new JRadioButton(AlgorithmConfigurationVO.CRITICAL_BLOCK_ADJACENT_ON_END_NEIGHBORHOOD);
		JRadioButton btnCRBlockEndStartAnyNeighborhood = new JRadioButton(AlgorithmConfigurationVO.CRITICAL_BLOCK_END_START_ANY_NEIGHBORHOOD);
		JRadioButton btnCRWeightedNodesNeighborhood = new JRadioButton(AlgorithmConfigurationVO.CRITICAL_WEIGHTED_NODES_NEIGHBORHOOD);
		
		btnRandomNeighborhood.setSelected(true);
		
		neighborhoodPanel.add(btnRandomNeighborhood);
		neighborhoodPanel.add(btnApiNeighborhood);
		neighborhoodPanel.add(btnCRAdjacentNeighborhood);
		neighborhoodPanel.add(btnCRAdjacentMachineNeighborhood);
		neighborhoodPanel.add(btnCRBlockRandomNeighborhood);
		neighborhoodPanel.add(btnCRBlockAdjacentOnEndNeighborhood);
		neighborhoodPanel.add(btnCRBlockEndStartAnyNeighborhood);
		neighborhoodPanel.add(btnCRWeightedNodesNeighborhood);
		
		neighborhoodButtons.add(btnRandomNeighborhood);
		neighborhoodButtons.add(btnApiNeighborhood);
		neighborhoodButtons.add(btnCRAdjacentNeighborhood);
		neighborhoodButtons.add(btnCRAdjacentMachineNeighborhood);
		neighborhoodButtons.add(btnCRBlockRandomNeighborhood);
		neighborhoodButtons.add(btnCRBlockAdjacentOnEndNeighborhood);
		neighborhoodButtons.add(btnCRBlockEndStartAnyNeighborhood);
		neighborhoodButtons.add(btnCRWeightedNodesNeighborhood);
		
		ButtonGroup neighGroup = new ButtonGroup();
		
		neighGroup.add(btnRandomNeighborhood);
		neighGroup.add(btnApiNeighborhood);
		neighGroup.add(btnCRAdjacentNeighborhood);
		neighGroup.add(btnCRAdjacentMachineNeighborhood);
		neighGroup.add(btnCRBlockRandomNeighborhood);
		neighGroup.add(btnCRBlockAdjacentOnEndNeighborhood);
		neighGroup.add(btnCRBlockEndStartAnyNeighborhood);
		neighGroup.add(btnCRWeightedNodesNeighborhood);
		
		this.add(neighborhoodPanel);
		
		JPanel ModifierPanel = new JPanel();
		ModifierPanel.setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 5, 0 ), new TitledBorder( "Modifier" ) ) );
		ModifierPanel.setLayout(new GridLayout(2,2));
		
		JRadioButton swap = new JRadioButton(AlgorithmConfigurationVO.SWAP);
		swap.setSelected(true);
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
		vector.setSelected(true);
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
		
		JRadioButton cmax = new JRadioButton(AlgorithmConfigurationVO.CMAX);
		cmax.setSelected(true);
		JRadioButton mft = new JRadioButton(AlgorithmConfigurationVO.MEAN_FLOW_TIME);
		
		gammaPanel.add(cmax);
		gammaPanel.add(mft);
		
		gammaButtons.add(cmax);
		gammaButtons.add(mft);
		
		ButtonGroup gammaGroup = new ButtonGroup();
		gammaGroup.add(cmax);
		gammaGroup.add(mft);
		
		this.add(gammaPanel);
		
		JPanel betasPanel = new JPanel();
		betasPanel.setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 5, 0 ), new TitledBorder( "Betas" ) ) );
		betasPanel.setLayout(new GridLayout(1,1));
		
		JCheckBox travelTimes = new JCheckBox(AlgorithmConfigurationVO.TRAVEL_TIMES);
		travelTimes.setSelected(true);
		JCheckBox setupTimes = new JCheckBox(AlgorithmConfigurationVO.SETUP_TIMES);
		
		betaBoxes.add(travelTimes);
		betaBoxes.add(setupTimes);
		
		betasPanel.add(travelTimes);
		betasPanel.add(setupTimes);
		
		this.add(betasPanel);
		
		JPanel reportPanel = new JPanel();
		reportPanel.setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 5, 0 ), new TitledBorder( "Report configuration" ) ) );
		reportPanel.setLayout(new GridLayout(2,1));
		
		JCheckBox consolidationTable = new JCheckBox(AlgorithmConfigurationVO.CONSOLIDATION_TABLE);
		consolidationTable.setSelected(true);
		JCheckBox initialSolitions = new JCheckBox(AlgorithmConfigurationVO.INITIAL_SOLUTIONS);
		JCheckBox finalSolitions = new JCheckBox(AlgorithmConfigurationVO.FINAL_SOLUTIONS);
		JCheckBox log = new JCheckBox(AlgorithmConfigurationVO.LOG);
		
		reportBoxes.add(consolidationTable);
		reportBoxes.add(initialSolitions);
		reportBoxes.add(finalSolitions);
		reportBoxes.add(log);
		
		reportPanel.add(consolidationTable);
		reportPanel.add(initialSolitions);
		reportPanel.add(finalSolitions);
		reportPanel.add(log);
		
		this.add(reportPanel);
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
		
		for (JCheckBox checkBox : betaBoxes) {
			if(checkBox.isSelected()){
				answer.getSelectedBetas().add(checkBox.getText());
			}
		}
		
		for (int i=0; i< reportBoxes.size(); i++) {
			JCheckBox checkBox =reportBoxes.get(i);
			if(checkBox.isSelected()){
				if(i!=reportBoxes.size()-1){
					answer.getReportConfiguration().add(checkBox.getText());
				}
				else{
					answer.getReportConfiguration().add(checkBox.getText());
					ExecutionLogger.getInstance().setUseLogger(true);
				}
			}
			ExecutionLogger.getInstance().setUseLogger(false);
		}
		return answer;
	}

}
