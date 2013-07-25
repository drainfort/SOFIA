package launcher.generator.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import launcher.generator.vos.AlgorithmConfigurationVO;

public class ProblemPanel extends JPanel{

	// ----------------------------------------------------
	// Constants
	// ----------------------------------------------------
	
	private static final long serialVersionUID = 1L;

	// ----------------------------------------------------
	// Attributes
	// ----------------------------------------------------
	
	private ArrayList<JRadioButton> buttonsInstancesTypes;
	
	private ArrayList<JCheckBox> boxes4;
	
	private ArrayList<JCheckBox> boxes5;
	
	private ArrayList<JCheckBox> boxes7;
	
	private ArrayList<JCheckBox> boxes10;
	
	private ArrayList<JCheckBox> boxes15;
	
	private ArrayList<JCheckBox> boxes20;
	
	private boolean parallel;
	
	// ----------------------------------------------------
	// Constructor
	// ----------------------------------------------------
	
	public ProblemPanel(Boolean parallel){
		this.parallel = parallel;
		this.setLayout(new BorderLayout());
		
		JPanel instanceTypePanel = new JPanel();
		instanceTypePanel.setLayout(new GridLayout(2,1));
		instanceTypePanel.setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 5, 0 ), new TitledBorder( "Benchmark" ) ) );
		this.add(instanceTypePanel, BorderLayout.WEST);
		
		buttonsInstancesTypes = new ArrayList<JRadioButton>();
		ButtonGroup instancesTypeGroup = new ButtonGroup();
		
		if(!parallel){
			JRadioButton taillardInstances = new JRadioButton("Taillard");
			instanceTypePanel.add(taillardInstances);
			buttonsInstancesTypes.add(taillardInstances);
			instancesTypeGroup.add(taillardInstances);
			
			JRadioButton yuInstances = new JRadioButton("Yu");
			yuInstances.setSelected(true);
			instanceTypePanel.add(yuInstances);
			buttonsInstancesTypes.add(yuInstances);
			instancesTypeGroup.add(yuInstances);
		}
		else{
			JRadioButton parallelInstances = new JRadioButton("Parallel");
			instanceTypePanel.add(parallelInstances);
			buttonsInstancesTypes.add(parallelInstances);
			instancesTypeGroup.add(parallelInstances);
		}
		
		JPanel instancesPanel = new JPanel();
		instancesPanel.setLayout(new GridLayout(1,6));
		instancesPanel.setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 5, 0 ), new TitledBorder( "Instances" ) ) );
		
		JPanel p4 = new JPanel();
		p4.setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 5, 0 ), new TitledBorder( "04x04" ) ) );
		p4.setLayout(new GridLayout(10, 1));
		
		boxes4 = new ArrayList<JCheckBox>();
		String instanceParallel ="";
		if(parallel){
			instanceParallel="x02";
		}
		for (int i = 1; i <= 10; i++) {
			if(i < 10){
				boxes4.add(new JCheckBox("04x04"+instanceParallel+"_0" + i));
			}else{
				boxes4.add(new JCheckBox("04x04"+instanceParallel+"_" + i));
			}
			p4.add(boxes4.get(i-1));
		}
		
		instancesPanel.add(p4);
		
		JPanel p5 = new JPanel();
		p5.setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 5, 0 ), new TitledBorder( "05x05" ) ) );
		p5.setLayout(new GridLayout(10, 1));
		
		boxes5 = new ArrayList<JCheckBox>();
		for (int i = 1; i <= 10; i++) {
			if(i < 10){
				boxes5.add(new JCheckBox("05x05"+instanceParallel+"_0" + i));
			}else{
				boxes5.add(new JCheckBox("05x05"+instanceParallel+"_" + i));
			}
			
			p5.add(boxes5.get(i-1));
		}
		
		instancesPanel.add(p5);
		
		JPanel p7 = new JPanel();
		p7.setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 5, 0 ), new TitledBorder( "07x07" ) ) );
		p7.setLayout(new GridLayout(10, 1));
		
		boxes7 = new ArrayList<JCheckBox>();
		for (int i = 1; i <= 10; i++) {
			if(i < 10){
				boxes7.add(new JCheckBox("07x07"+instanceParallel+"_0" + i));
			}else{
				boxes7.add(new JCheckBox("07x07"+instanceParallel+"_" + i));
			}
			
			p7.add(boxes7.get(i-1));
		}
		
		instancesPanel.add(p7);
		
		JPanel p10 = new JPanel();
		p10.setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 5, 0 ), new TitledBorder( "10x10" ) ) );
		p10.setLayout(new GridLayout(10, 1));
		
		boxes10 = new ArrayList<JCheckBox>();
		for (int i = 1; i <= 10; i++) {
			if(i < 10){
				boxes10.add(new JCheckBox("10x10"+instanceParallel+"_0" + i));
			}else{
				boxes10.add(new JCheckBox("10x10"+instanceParallel+"_" + i));
			}
			
			p10.add(boxes10.get(i-1));
		}
		instancesPanel.add(p10);
		
		JPanel p15 = new JPanel();
		p15.setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 5, 0 ), new TitledBorder( "15x15" ) ) );
		p15.setLayout(new GridLayout(10, 1));
		
		boxes15 = new ArrayList<JCheckBox>();
		for (int i = 1; i <= 10; i++) {
			if(i < 10){
				boxes15.add(new JCheckBox("15x15"+instanceParallel+"_0" + i));
			}else{
				boxes15.add(new JCheckBox("15x15"+instanceParallel+"_" + i));
			}
			
			p15.add(boxes15.get(i-1));
		}
		instancesPanel.add(p15);
		
		JPanel p20 = new JPanel();
		p20.setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 5, 0 ), new TitledBorder( "20x20" ) ) );
		p20.setLayout(new GridLayout(10, 1));
		
		boxes20 = new ArrayList<JCheckBox>();
		for (int i = 1; i <= 10; i++) {
			if(i < 10){
				boxes20.add(new JCheckBox("20x20"+instanceParallel+"_0" + i));
			}else{
				boxes20.add(new JCheckBox("20x20"+instanceParallel+"_" + i));
			}
			p20.add(boxes20.get(i-1));
		}
		instancesPanel.add(p20);
		
		this.add(instancesPanel, BorderLayout.CENTER);
	}

	// ----------------------------------------------------
	// Methods
	// ----------------------------------------------------
	
	public String getSelectedBenchmark(){
		if(buttonsInstancesTypes.get(0).isSelected()){
			if(!parallel)
				return AlgorithmConfigurationVO.TAILLARD;
			else
				return AlgorithmConfigurationVO.PARALLEL;
		}else{
			return AlgorithmConfigurationVO.YU;
		}
	}
	
	public ArrayList<String> getSelectedInstances() {
		ArrayList<String> answer = new ArrayList<String>();
		
		for (JCheckBox checkBox : boxes4) {
			if(checkBox.isSelected()){
				answer.add(checkBox.getText());
			}
		}
		
		for (JCheckBox checkBox : boxes5) {
			if(checkBox.isSelected()){
				answer.add(checkBox.getText());
			}
		}
		
		for (JCheckBox checkBox : boxes7) {
			if(checkBox.isSelected()){
				answer.add(checkBox.getText());
			}
		}
		
		for (JCheckBox checkBox : boxes10) {
			if(checkBox.isSelected()){
				answer.add(checkBox.getText());
			}
		}
		
		for (JCheckBox checkBox : boxes15) {
			if(checkBox.isSelected()){
				answer.add(checkBox.getText());
			}
		}
		
		for (JCheckBox checkBox : boxes20) {
			if(checkBox.isSelected()){
				answer.add(checkBox.getText());
			}
		}
		return answer;
	}
}
