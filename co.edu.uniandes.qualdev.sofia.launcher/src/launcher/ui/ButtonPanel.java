package launcher.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import launcher.vos.AlgorithmConfigurationVO;

public class ButtonPanel extends JPanel implements ActionListener{
	
	public static final String GENERATE_JARS = "generate_jars";
	
	public static final String EXECUTE = "execute";
	
	private JButton btnGenerateJars;
	
	private JButton btnExecute;
	
	private LauncherUI launcher;
	
	
	public ButtonPanel(LauncherUI launcher){
		this.launcher = launcher;
		btnGenerateJars = new JButton("Generate jars");
		btnGenerateJars.setActionCommand(GENERATE_JARS);
		btnGenerateJars.addActionListener(this);
		this.add(btnGenerateJars);
		
		btnExecute = new JButton("Execute");
		btnExecute.setActionCommand(EXECUTE);
		btnExecute.addActionListener(this);
		this.add(btnExecute);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		
		if(command.equals(EXECUTE)){
			ArrayList<String> instancesToExecute = launcher.getSelectedInstances();
			AlgorithmConfigurationVO algorithmDefinition = launcher.getAlgorithmDefinition();
			launcher.execute(instancesToExecute, launcher.getSelectedBenchmark(), algorithmDefinition);
		}
	}

}
