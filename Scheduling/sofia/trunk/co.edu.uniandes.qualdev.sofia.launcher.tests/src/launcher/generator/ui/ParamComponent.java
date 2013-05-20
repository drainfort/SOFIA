package launcher.generator.ui;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class ParamComponent {

	// ------------------------------------------------------------------------------------------
	// Attributes
	// ------------------------------------------------------------------------------------------
	
	private JLabel label;
	
	private JTextField text;
	
	// ------------------------------------------------------------------------------------------
	// Constructor
	// ------------------------------------------------------------------------------------------
	
	public ParamComponent(JLabel label, JTextField text){
		this.label = label;
		this.text = text;
	}

	// ------------------------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------------------------
	
	public JLabel getLabel() {
		return label;
	}

	public void setLabel(JLabel label) {
		this.label = label;
	}

	public JTextField getText() {
		return text;
	}

	public void setText(JTextField text) {
		this.text = text;
	}
}