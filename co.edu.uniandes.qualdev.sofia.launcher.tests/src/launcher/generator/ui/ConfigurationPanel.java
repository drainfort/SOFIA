package launcher.generator.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class ConfigurationPanel extends JPanel implements ActionListener{

	// ------------------------------------------------
	// Constants
	// ------------------------------------------------
	
	public final static String BROWSE_ECLIPSE = "browse_eclipse";
	
	public final static String BROWSE_WORKSPACE = "browser_workspace";
	
	// ------------------------------------------------
	// Attributes
	// ------------------------------------------------
	
	private JLabel labEclipsePath;
	
	private JTextField txtEclipsePath;
	
	private JButton btnEclipsePath;
	
	private JLabel labWorkspacePath;
	
	private JTextField txtWorkspacePath;
	
	private JButton btnWorkspacePath;

	// ------------------------------------------------
	// Constructor
	// ------------------------------------------------
	
	public ConfigurationPanel(){
		this.setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 5, 0 ), new TitledBorder( "Configuration" ) ) );
		this.setLayout(new GridLayout(2,3));
	
		labEclipsePath = new JLabel("Eclipse location:");
		this.add(labEclipsePath);
		
		txtEclipsePath = new JTextField();
		txtEclipsePath.setEnabled(false);
		this.add(txtEclipsePath);
		
		btnEclipsePath = new JButton("Browse");
		btnEclipsePath.addActionListener(this);
		btnEclipsePath.setActionCommand(BROWSE_ECLIPSE);
		this.add(btnEclipsePath);
		
		labWorkspacePath = new JLabel("Eclipse location:");
		this.add(labWorkspacePath);
		
		txtWorkspacePath = new JTextField();
		txtWorkspacePath.setEnabled(false);
		this.add(txtWorkspacePath);
		
		btnWorkspacePath = new JButton("Browse");
		btnWorkspacePath.addActionListener(this);
		btnWorkspacePath.setActionCommand(BROWSE_WORKSPACE);
		this.add(btnWorkspacePath);
	}

	// ------------------------------------------------
	// Methods
	// ------------------------------------------------
	
	private String chooseFolder(){
		String answer = null;
		JFileChooser fc = new JFileChooser();
        fc.setDialogTitle( "Choose file" );
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        int selection = fc.showOpenDialog( this );
        if( selection == JFileChooser.APPROVE_OPTION ){
        	answer = fc.getSelectedFile().getAbsolutePath();
        }
        return answer;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		
		if(command.equals(BROWSE_ECLIPSE)){
			String folder = chooseFolder();
			txtEclipsePath.setText(folder);
		}else if(command.equals(BROWSE_WORKSPACE)){
			String folder = chooseFolder();
			txtWorkspacePath.setText(folder);
		}
	}
	
	public String getEclipsePath(){
		return txtEclipsePath.getText();
	}
	
	public String getWorkspacePath(){
		return txtWorkspacePath.getText();
	}
}
