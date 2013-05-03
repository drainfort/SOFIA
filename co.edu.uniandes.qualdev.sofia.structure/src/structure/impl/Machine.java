package structure.impl;

import java.util.ArrayList;

public class Machine {
	
	private int id;
	private ArrayList<Attribute> atributes;
	private String nameClass;
	
	public Machine(int nId, String nameClass){
		id= nId;
		this.nameClass = nameClass;		
		atributes = new ArrayList<Attribute>();
	}
	
	

	public String getNameClass() {
		return nameClass;
	}



	public void setNameClass(String nameClass) {
		this.nameClass = nameClass;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<Attribute> getAtributes() {
		return atributes;
	}

	public void setAtributes(ArrayList<Attribute> atributes) {
		this.atributes = atributes;
	}

}
