package structure.impl;

import java.util.ArrayList;

import structure.IOperation;

public class CriticalRoute {
	
	private ArrayList<IOperation> route;
	
	public CriticalRoute(){
		route = new ArrayList<IOperation>();
	}

	public ArrayList<IOperation> getRoute() {
		return route;
	}

	public void setRoute(ArrayList<IOperation> route) {
		this.route = route;
	}

	public void addNodeBegin(IOperation node)
	{
		route.add(0,node);
	}
	
	public void addPreviusRoute(ArrayList<IOperation> nRoute){
		route.addAll(nRoute);
	}
	
	
	public ArrayList<ArrayList<IOperation>> getBlocks(){
		ArrayList<ArrayList<IOperation>> blocks = new ArrayList<ArrayList<IOperation>>();
		boolean inRoute=false;
		boolean inSequence =false;
		
		ArrayList<IOperation> firstBlock = new ArrayList<IOperation>();
		IOperation first = route.get(0);
		IOperation second = route.get(1);
		firstBlock.add(first);
		firstBlock.add(second);
		if(first.getOperationIndex().getJobId()==second.getOperationIndex().getJobId()){
			inRoute = true;
		}
		else if(first.getOperationIndex().getStationId()==second.getOperationIndex().getStationId()){
			inSequence = true;
		}
		for(int i=2; i<route.size();i++){
			first = route.get(i-1);
			second = route.get(i);
			if(inRoute){
				if(first.getOperationIndex().getJobId()==second.getOperationIndex().getJobId()){
					firstBlock.add(second);
				}
				else{
					blocks.add(firstBlock);
					firstBlock = new ArrayList<IOperation>();
					firstBlock.add(first);
					firstBlock.add(second);
					inRoute = false;
					inSequence = true;
				}
			}
			else if(inSequence){
				if(first.getOperationIndex().getStationId() == second.getOperationIndex().getStationId()){
					firstBlock.add(second);
				}
				else{
					blocks.add(firstBlock);
					firstBlock = new ArrayList<IOperation>();
					firstBlock.add(first);
					firstBlock.add(second);
					inRoute = true;
					inSequence = false;
				}
			}
		}
		
		blocks.add(firstBlock);
		return blocks;
		
	}
	
	public String toString(){
		String respuesta="";
		for(int i=0;i<route.size();i++)
		{
			respuesta+="/"+route.get(i).toString() + " "+route.get(i).getFinalTime()+ " /";
		}
		return respuesta;
	}
}
