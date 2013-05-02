package common.utils;

import java.util.ArrayList;

public class MetricsModel{
	
	private ArrayList<MetricsItem> items;
	
	public MetricsModel(){
		items = new ArrayList<MetricsItem>();
	}
	
	public void addItem(Double iterationNumber, Double CMax){
		MetricsItem item = new MetricsItem();
		item.setIterationNumber(iterationNumber);
		item.setCMax(CMax);
		items.add(item);
	}

	public ArrayList<MetricsItem> getItems() {
		return items;
	}

	public void setItems(ArrayList<MetricsItem> items) {
		this.items = items;
	}
}