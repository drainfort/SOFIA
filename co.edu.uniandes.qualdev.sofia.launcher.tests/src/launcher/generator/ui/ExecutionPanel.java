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

public class ExecutionPanel extends JPanel {

	// -----------------------------------------------------
	// Constants
	// -----------------------------------------------------
	
	private static final long serialVersionUID = 7784995463210611021L;
	
	// -----------------------------------------------------
	// Attributes
	// -----------------------------------------------------
	
	private AlgorithmDefinitionPanel algorithmDefinitionPanel;
	
	// -----------------------------------------------------
	// Constructor
	// -----------------------------------------------------
	
	public ExecutionPanel(){
		this.setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 5, 0 ), new TitledBorder( "Execution" ) ) );
		this.setLayout(new BorderLayout());
				
		algorithmDefinitionPanel = new AlgorithmDefinitionPanel();
		this.add(algorithmDefinitionPanel, BorderLayout.CENTER);
		
	}


}
