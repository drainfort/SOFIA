package launcher.generator.ui;

import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class JarsGenerationPanel extends JPanel {

	public JarsGenerationPanel(){
		setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 5, 0 ), new TitledBorder( "Execution jars generation" ) ) );
	}

}
