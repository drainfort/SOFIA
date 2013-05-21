package launcher.generator.ui;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class ProblemPanel extends JPanel{

	// ----------------------------------------------------
	// Constants
	// ----------------------------------------------------
	
	private static final long serialVersionUID = 1L;

	// ----------------------------------------------------
	// Attributes
	// ----------------------------------------------------
	
	private ArrayList<JCheckBox> boxes4;
	
	private ArrayList<JCheckBox> boxes5;
	
	private ArrayList<JCheckBox> boxes7;
	
	private ArrayList<JCheckBox> boxes10;
	
	private ArrayList<JCheckBox> boxes15;
	
	private ArrayList<JCheckBox> boxes20;
	
	// ----------------------------------------------------
	// Constructor
	// ----------------------------------------------------
	
	public ProblemPanel(){
		this.setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 5, 0 ), new TitledBorder( "Problem" ) ) );
		this.setLayout(new GridLayout(1,6));
		
		JPanel p4 = new JPanel();
		p4.setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 5, 0 ), new TitledBorder( "04x04" ) ) );
		p4.setLayout(new GridLayout(10, 1));
		
		boxes4 = new ArrayList<JCheckBox>();
		for (int i = 1; i <= 10; i++) {
			if(i < 10){
				boxes4.add(new JCheckBox("04x04_0" + i));
			}else{
				boxes4.add(new JCheckBox("04x04_" + i));
			}
			p4.add(boxes4.get(i-1));
		}
		
		this.add(p4);
		
		JPanel p5 = new JPanel();
		p5.setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 5, 0 ), new TitledBorder( "05x05" ) ) );
		p5.setLayout(new GridLayout(10, 1));
		
		boxes5 = new ArrayList<JCheckBox>();
		for (int i = 1; i <= 10; i++) {
			if(i < 10){
				boxes5.add(new JCheckBox("05x05_0" + i));
			}else{
				boxes5.add(new JCheckBox("05x05_" + i));
			}
			
			p5.add(boxes5.get(i-1));
		}
		
		this.add(p5);
		
		JPanel p7 = new JPanel();
		p7.setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 5, 0 ), new TitledBorder( "07x07" ) ) );
		p7.setLayout(new GridLayout(10, 1));
		
		boxes7 = new ArrayList<JCheckBox>();
		for (int i = 1; i <= 10; i++) {
			if(i < 10){
				boxes7.add(new JCheckBox("07x07_0" + i));
			}else{
				boxes7.add(new JCheckBox("07x07_" + i));
			}
			
			p7.add(boxes7.get(i-1));
		}
		
		this.add(p7);
		
		JPanel p10 = new JPanel();
		p10.setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 5, 0 ), new TitledBorder( "10x10" ) ) );
		p10.setLayout(new GridLayout(10, 1));
		
		boxes10 = new ArrayList<JCheckBox>();
		for (int i = 1; i <= 10; i++) {
			if(i < 10){
				boxes10.add(new JCheckBox("10x10_0" + i));
			}else{
				boxes10.add(new JCheckBox("10x10_" + i));
			}
			
			p10.add(boxes10.get(i-1));
		}
		this.add(p10);
		
		JPanel p15 = new JPanel();
		p15.setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 5, 0 ), new TitledBorder( "15x15" ) ) );
		p15.setLayout(new GridLayout(10, 1));
		
		boxes15 = new ArrayList<JCheckBox>();
		for (int i = 1; i <= 10; i++) {
			if(i < 10){
				boxes15.add(new JCheckBox("15x15_0" + i));
			}else{
				boxes15.add(new JCheckBox("15x15_" + i));
			}
			
			p15.add(boxes15.get(i-1));
		}
		this.add(p15);
		
		JPanel p20 = new JPanel();
		p20.setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 5, 0 ), new TitledBorder( "20x20" ) ) );
		p20.setLayout(new GridLayout(10, 1));
		
		boxes20 = new ArrayList<JCheckBox>();
		for (int i = 1; i <= 10; i++) {
			if(i < 10){
				boxes20.add(new JCheckBox("20x20_0" + i));
			}else{
				boxes20.add(new JCheckBox("20x20_" + i));
			}
			p20.add(boxes20.get(i-1));
		}
		this.add(p20);
	}

	// ----------------------------------------------------
	// Methods
	// ----------------------------------------------------
	
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
