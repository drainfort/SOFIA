package structure.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import structure.AbstractStructure;
import structure.IOperation;
import structure.IStructure;

import beta.Beta;
import beta.SetupBeta;
import beta.TTBeta;
import beta.TearDownBeta;
import beta.impl.TearDownTravelTime;

import common.types.BetaVO;
import common.types.OperationIndexVO;
import common.types.PairVO;

/**
 * Implementation of a graph
 * 
 * @author Rubby Casallas
 * @author David Mendez-Acuna
 * @author Lindsay çlvarez
 * @author Oriana Cendales
 * @author Jaime Romero
 * @author Juan Pablo Caballero-Villalobos
 */
public class Vector extends AbstractStructure{

	// -----------------------------------------------
	// Attributes
	// -----------------------------------------------

	/**
	 * ArrayList with the permutation vector
	 */
	private ArrayList<IOperation> vector = null;

	/**
	 * Flag that indicates if the C matrix is already calculated or updated.
	 */
	private boolean CCalculated;
	
	/**
	 * A matrix of the current solution
	 */
	private int[][] A;
	
	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------

	/**
	 * Constructor of the class that initializes an empty structure
	 */
	public Vector(int totalJobs, int totalStations){
		super(totalJobs, totalStations);
		
		vector = new ArrayList<IOperation>();
		CCalculated = false;
	}
	
	/**
	 * Constructor of the class
	 */
	public Vector(String processingTimesFile, ArrayList<BetaVO> pBetas) throws Exception {
		super(processingTimesFile, pBetas);
		
		vector = new ArrayList<IOperation>();
		CCalculated = false;
	}

	// -------------------------------------------------
	// Neighbor methods
	// -------------------------------------------------
	
	@Override
	public void exchangeOperations(int initialOperationPosition, int finalOperationPosition) {
		IOperation firstOperation = vector.get(initialOperationPosition);
		IOperation secondOperation = vector.get(finalOperationPosition);
		
		vector.set(initialOperationPosition, secondOperation);
		vector.set(finalOperationPosition, firstOperation);
		CCalculated = false;
	}
	
	@Override
	public void exchangeOperations(OperationIndexVO initialOperationIndex, OperationIndexVO finalOperationIndex) {
		int initialOperationPosition = this.getPositionByOperationIndex(initialOperationIndex);
		int finalOperationPosition = this.getPositionByOperationIndex(finalOperationIndex);
		
		IOperation initialOperation = this.getOperationByOperationIndex(initialOperationIndex);
		IOperation finalOperation = this.getOperationByOperationIndex(finalOperationIndex);
		
		vector.set(initialOperationPosition, finalOperation);
		vector.set(finalOperationPosition, initialOperation);
		
		CCalculated = false;
	}
	
	@Override
	public void insertOperationBefore(int first, int second) {
		IOperation secondOperation = vector.get(second);
		
		if(first < second){
			vector.remove(secondOperation);
			vector.add(first, secondOperation);
		}else{
			vector.add(first, secondOperation);
			vector.remove(secondOperation);
		}
		CCalculated = false;
	}
	
	@Override
	public void insertOperationBefore(OperationIndexVO toInsertOperationIndex, OperationIndexVO successorOperationIndex) {
		int toInsertOperationPosition = this.getPositionByOperationIndex(toInsertOperationIndex);
		int successorOperationPosition = this.getPositionByOperationIndex(successorOperationIndex);
		
		IOperation successorOperation = this.getOperationByOperationIndex(successorOperationIndex);
		
		if(toInsertOperationPosition < successorOperationPosition){
			vector.remove(successorOperationPosition);
			vector.add(toInsertOperationPosition, successorOperation);
		}else{
			vector.add(toInsertOperationPosition, successorOperation);
			vector.remove(successorOperationPosition);
		}
		CCalculated = false;
	}

	@Override
	public void insertOperationAfter(int toInsertOperationPosition, int successorOperationPosition) {
		IOperation secondOperation = vector.get(successorOperationPosition);
		
		if(toInsertOperationPosition > successorOperationPosition){
			vector.remove(secondOperation);
			vector.add(toInsertOperationPosition, secondOperation);
		}else if(toInsertOperationPosition < successorOperationPosition){
			vector.remove(secondOperation);
			if(toInsertOperationPosition == vector.size() - 1){
				vector.add(secondOperation);
			}else{
				vector.add(toInsertOperationPosition + 1,secondOperation);
			}
		}
		CCalculated = false;
	}
	
	@Override
	public void insertOperationAfter(OperationIndexVO toInsertOperationIndex, OperationIndexVO successorOperationIndex) {
		int toInsertOperationPosition = this.getPositionByOperationIndex(toInsertOperationIndex);
		int successorOperationPosition = this.getPositionByOperationIndex(successorOperationIndex);
		
		IOperation successorOperation = this.getOperationByOperationIndex(successorOperationIndex);
		
		if(toInsertOperationPosition > successorOperationPosition){
			vector.remove(successorOperation);
			vector.add(toInsertOperationPosition, successorOperation);
		}else if(toInsertOperationPosition < successorOperationPosition){
			vector.remove(successorOperation);
			if(toInsertOperationPosition == vector.size() - 1){
				vector.add(successorOperation);
			}else{
				vector.add(toInsertOperationPosition + 1, successorOperation);
			}
		}
		CCalculated = false;
	}
	
	// -------------------------------------------------
	// Queries
	// -------------------------------------------------
	
	@Override
	public int getTotalJobs() {
		return totalJobs;
	}

	@Override
	public int getTotalStations() {
		return totalStations;
	}
	
	@Override
	public int getMaxMachinesPerStation() {
		return maxMachinesPerStation;
	}
	
	@Override
	public IOperation getOperationByOperationIndex(OperationIndexVO operationIndex) {
		for (IOperation operation : vector) {
			if(operation.getOperationIndex().equals(operationIndex))
				return operation;
		}
		return null;
	}
	
	@Override
	public int getPositionByOperationIndex(OperationIndexVO operationIndex) {
		int i = 0;
		for (IOperation operation : vector) {
			if(operation.getOperationIndex().equals(operationIndex))
				return i;
			i++;
		}
		return -1;
	}
	
	@Override
	public IOperation getOperationByPosition(int position){
		return vector.get(position);
	}
	
	@Override
	public ArrayList<IOperation> getOperations(){
		return vector;
	}
	
	@Override
	public ArrayList<IOperation> getOperationsByJobAndStation(int jobId,
			int stationId) throws Exception{
		/*ArrayList<IOperation> operations = new ArrayList<IOperation>();
		for (int i = 0; i < totalJobs; i++) {
			for (int j = 0; j < totalStations; j++) {
				for (int k = 0; k <  maxMachinesPerStation; k++) {
					if(i == jobId && j == stationId){
						//operations.add(operations[i][j][k]);
					}
				}
			}
		}
		return null;*/
		throw new Exception("Operation not currently supported");
	}
	
	@Override
	public int[][] calculateCMatrix() {
		if(CCalculated){
			return C;
		}else{
			C = new int[getTotalJobs()][getTotalStations() + 1];
			
			for (int i = 0; i < vector.size(); i++) {
				IOperation Cij = vector.get(i);
				
				int Ciminus1J = getCiminus1J(Cij, i) != null ? getCiminus1J(Cij, i).getFinalTime() : 0;
				int CiJminus1 = getCiJminus1(Cij, i) != null ? getCiJminus1(Cij, i).getFinalTime() : 0;
				
				int sumTTBetas = this.getTTBetas(getCiminus1J(Cij, i), i);
				int sumSetupBetas = this.getSetupBetas(Cij.getOperationIndex().getJobId(), Cij.getOperationIndex().getStationId());
				
				int initialTime = Math.max(Ciminus1J + sumTTBetas, CiJminus1);
				int finalTime = initialTime + Cij.getProcessingTime() + sumSetupBetas;
				
				Cij.setInitialTime(initialTime);
				Cij.setFinalTime(finalTime);
				
				C[Cij.getOperationIndex().getJobId()][Cij
						.getOperationIndex().getStationId()] = finalTime;
				
			}
			
			// Aplying TearDown betas
			int[][] newC = applyTearDownBetas();

			if (newC != null)
				C = newC;
			
			CCalculated = true;
			return C;
		}
	}
	
	@Override
	public int[][] updateCMatrix(PairVO pair){
		if(CCalculated){
			return C;
		}else{
			for (int i = Math.max(pair.getX(), pair.getY()); i < vector.size(); i++) {
				IOperation Cij = vector.get(i);
				
				int Ciminus1J = getCiminus1J(Cij, i) != null ? getCiminus1J(Cij, i).getFinalTime() : 0;
				int CiJminus1 = getCiJminus1(Cij, i) != null ? getCiJminus1(Cij, i).getFinalTime() : 0;
				
				int sumTTBetas = this.getTTBetas(getCiminus1J(Cij, i), i);
				
				int initialTime = Math.max(Ciminus1J + sumTTBetas, CiJminus1);
				int finalTime = initialTime + + Cij.getProcessingTime();
				
				Cij.setInitialTime(initialTime);
				Cij.setFinalTime(finalTime);
				
				C[Cij.getOperationIndex().getJobId()][Cij
						.getOperationIndex().getStationId()] = finalTime;
			}
			
			// Aplying TearDown betas
			int[][] newC = applyTearDownBetas();

			if (newC != null)
				C = newC;
			
			CCalculated = true;
			return C;
		}
	}
	
	@Override
	public int[][] calculateInitialTimesMatrix() {
		int [][] matrix = new int[getTotalJobs()][getTotalStations()];
		int [][] CMatrix = calculateCMatrix();
		for(int i=0; i<matrix.length;i++)
		{
			for(int j=0; j<matrix[i].length;j++)
			{
				matrix[i][j] = CMatrix[i][j] - getOperationByOperationIndex(new OperationIndexVO(i, j)).getProcessingTime();
			}
		}
		return matrix;
	}
	
	@Override
	public int[][] calculateAMatrix(){
		A = new int[this.totalJobs][this.totalStations];
		
		for (int i = 0; i < totalJobs; i++) {
			for (int j = 0; j < totalStations; j++) {
				A[i][j] = calculateRank(new OperationIndexVO(i, j));
			}
		}
		return A;
	}
	
	@Override
	public Collection<Integer> getJobRoute(int jobId) throws Exception{
		throw new Exception("Operation not currently supported");
	}

	@Override
	public Collection<Integer> getStationSequence(int stationId) throws Exception{
		throw new Exception("Operation not currently supported");
	}
	
	@Override
	public int geInitialTime(OperationIndexVO operationIndex) {
		for (int i = 0; i < vector.size(); i++) {
			IOperation operation = vector.get(i);
			if (operation.getOperationIndex().getJobId() == operation
					.getOperationIndex().getStationId()) {
				return operation.getInitialTime();
			}
		}
		return 0;
	}

	@Override
	public int geFinalTime(OperationIndexVO operationIndex) {
		for (int i = 0; i < vector.size(); i++) {
			IOperation operation = vector.get(i);
			if (operation.getOperationIndex().getJobId() == operation
					.getOperationIndex().getStationId()) {
				return operation.getFinalTime();
			}
		}
		return 0;
	}
	
	@Override
	public IOperation getCiminus1J(IOperation Cij, int vectorPos){
		boolean beforeCounted = false;
		for (int j = vectorPos; j >= 0 && !beforeCounted; j--) {
			IOperation operationJ = vector.get(j);
			if (operationJ.getOperationIndex().getJobId() == Cij
					.getOperationIndex().getJobId() && vectorPos != j) {
				return operationJ;
			}
		}
		return null;
	}
	
	@Override
	public IOperation getCiJminus1(IOperation Cij, int vectorPos){
		boolean beforeCounted = false;
		for (int j = vectorPos; j >= 0 && !beforeCounted; j--) {
			IOperation operationJ = vector.get(j);
			if (operationJ.getOperationIndex().getStationId() == Cij
					.getOperationIndex().getStationId() && operationJ.getOperationIndex().getMachineId() == Cij
							.getOperationIndex().getMachineId() && vectorPos != j) {
				return operationJ;
			}
		}
		return null;
	}
	
	@Override
	public IOperation getPosition(int pos) {
		return vector.get(pos);
	}
	
	@Override
	public ArrayList<BetaVO> getBetas() {
		ArrayList<BetaVO> betas = new ArrayList<BetaVO>();
		Iterator<String> it = this.betas.keySet().iterator();
		for (int i = 0; i < this.betas.keySet().size(); i++) {
			String nextKey = (String) it.next();
			String className = this.betas.get(nextKey).clone().getClass()
					.getCanonicalName();
			ArrayList<String> informationFiles = new ArrayList<String>();
			for (int j = 0; j < this.betas.get(nextKey).getInformationFiles()
					.size(); j++) {
				informationFiles.add(this.betas.get(nextKey)
						.getInformationFiles().get(j));
			}
			BetaVO beta = new BetaVO(nextKey, className, informationFiles);
			betas.add(beta);

		}
		return betas;
	}
	
	@Override
	public int getTT(int j, int i) {
		if(this.betas!=null){
			Iterator<Beta> iterator = betas.values().iterator();
			while (iterator.hasNext()) {
				Beta beta = iterator.next();
				if (beta instanceof TTBeta){
					return (int) ((TTBeta) beta).getValue(i,j);
				}
			}
		}
		return 0;
	}
	
	@Override
	public int getTTBetas(IOperation Cij, int predecessor) {
		int sumBetas = 0;
		if(this.betas!=null){
			Iterator<Beta> iterator = betas.values().iterator();
			while (iterator.hasNext()) {
				Beta beta = iterator.next();
				if (beta instanceof TTBeta){
					if(Cij!=null){
						sumBetas += ((TTBeta) beta).getValue(Cij.getOperationIndex().getStationId(), vector.get(predecessor).getOperationIndex().getStationId());
					}else{
						sumBetas += ((TTBeta) beta).getValue(-1, vector.get(predecessor).getOperationIndex().getStationId());
					}
				}
			}
		}
		return sumBetas;
	}
	
	@Override
	public int getTTBetas(IOperation origin, IOperation destination) throws Exception{
		throw new Exception("Operation not currently supported");
	}

	private int getSetupBetas(int i, int j) {
		int sumBetas = 0;
		if(this.betas!=null){
			Iterator<Beta> iterator = betas.values().iterator();
			while (iterator.hasNext()) {
				Beta beta = iterator.next();
				if (beta instanceof SetupBeta){
					sumBetas += ((SetupBeta) beta).getValue(i,j);
				}
			}
		}
		return sumBetas;
	}

	private int getReleaseBetas(IOperation iOperation, IOperation iOperation2) {
		// TODO complete
		return 0;
	}
	
	// -------------------------------------------------
	// Manipulation methods
	// -------------------------------------------------
	
	@Override
	public void scheduleOperation(OperationIndexVO operationIndexVO) {
		vector.add(operationsMatrix[operationIndexVO.getJobId()][operationIndexVO.getStationId()]);
		int maxAmountOfJobs = -1;
		int maxAmountOfStations = -1;
		int maxAmountOfMachinesPerStation = -1;
		
		for (IOperation operation : vector) {
			if((operation.getOperationIndex().getJobId() + 1) > maxAmountOfJobs)
				maxAmountOfJobs = (operation.getOperationIndex().getJobId() + 1);
			if((operation.getOperationIndex().getStationId() + 1) > maxAmountOfStations)
				maxAmountOfStations = (operation.getOperationIndex().getStationId() + 1);
			if((operation.getOperationIndex().getMachineId() + 1) > maxAmountOfMachinesPerStation)
				maxAmountOfMachinesPerStation = (operation.getOperationIndex().getMachineId() + 1);
		}
		
		this.totalJobs = maxAmountOfJobs;
		this.totalStations = maxAmountOfStations;
		this.maxMachinesPerStation = maxAmountOfMachinesPerStation;
		
		//job= null;
		//machine=null;
		CCalculated = false;
	}
	
	@Override
	public void removeOperationFromSchedule(OperationIndexVO operationIndex) {
		vector.remove(this.getOperationByOperationIndex(operationIndex));
		CCalculated = false;
	}
	
	// ----------------------------------------------------------
	// Utilities
	// ----------------------------------------------------------

	/**
	 * Returns a copy of the graph
	 * 
	 * @return a copy of the graph
	 */
	public IStructure cloneStructure() {
		Vector clone = null;
		try{
			clone = new Vector(this.totalJobs, this.totalStations);
			clone.processingTimesFile = this.processingTimesFile;
			clone.totalJobs = this.totalJobs;
			clone.totalStations = this.totalStations;
			clone.maxMachinesPerStation = this.maxMachinesPerStation;

			ArrayList<IOperation> operations = new ArrayList<IOperation>();
			for (IOperation operation : vector) {
				IOperation cloneOperation = operation.clone();
				operations.add(cloneOperation);
			}
			clone.vector = operations;
			
			// Clone betas
			if (this.betas != null) {
				clone.betas = new HashMap<String, Beta>();
				Iterator<String> it = this.betas.keySet().iterator();
				for (int i = 0; i < this.betas.keySet().size(); i++) {
					String nextKey = (String) it.next();
					clone.betas.put(nextKey, this.betas.get(nextKey).clone());
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return clone;
	}

	@Override
	public void clean() {
		CCalculated = false;
	}
	
	// -------------------------------------------------
	// Auxiliary methods
	// -------------------------------------------------
	
	/**
	 * Calculates the rank of an operation identified by the operation index
	 * in the parameter
	 * @param operationIndex OperationIndex that identifies the operation
	 * @return rank
	 */
	private Integer calculateRank(OperationIndexVO operationIndex) {
		int rank = 1;
	
		int comparisonJob = operationIndex.getJobId();
		int comparisonStation = operationIndex.getStationId();
		
		int index = getPositionByOperationIndex(operationIndex);
		for (int i = index; i >= 0; i--) {
			IOperation currentOperation = vector.get(i);
			if(comparisonJob == currentOperation.getOperationIndex().getJobId()
					^ comparisonStation == currentOperation.getOperationIndex().getStationId()){
				rank++;
				comparisonJob = currentOperation.getOperationIndex().getJobId();
				comparisonStation = currentOperation.getOperationIndex().getStationId();
			}
		}
		return rank;
	}
	
	private int[][] applyTearDownBetas() {
		int[][] newC = null;
		if (this.betas != null) {
			Iterator<Beta> i = betas.values().iterator();
			while (i.hasNext()) {
				Beta beta = i.next();

				if (beta instanceof TearDownBeta) {
					 newC = ((TearDownTravelTime) beta).applyBeta(C);
				}

			}
		}
		CCalculated = false;
		return newC == null ? C : newC;
	}
	
	// -------------------------------------------------
	// Getters and setters
	// -------------------------------------------------

	public ArrayList<IOperation> getVector(){
		return vector;
	}
	
	@Override
	public String getProcessingTimesFile() {
		return processingTimesFile;
	}

	@Override
	public void setProcessingTimesFile(String processingTimesFile) {
		this.processingTimesFile = processingTimesFile;
	}

	@Override
	public void setTotalJobs(int totalJobs) {
		this.totalJobs = totalJobs;
		CCalculated = false;
	}

	@Override
	public void setTotalStations(int totaStations) {
		this.totalStations = totaStations;
		CCalculated = false;
	}

	@Override
	public void setMaxMachinesPerStation(int maxMachinesPerStation) {
		this.maxMachinesPerStation = maxMachinesPerStation;
		CCalculated = false;
	}
	
	public ArrayList<IOperation> getOperationsBeforeByStation(OperationIndexVO operation){
		ArrayList<IOperation> operations = new ArrayList<IOperation>();
		for(IOperation op : vector){
			if(op.getOperationIndex().getStationId()==operation.getStationId()){
				if(!op.getOperationIndex().equals(operation)){
					operations.add(op);
				}else{
					return operations;
				}
			}
				
		}
		return operations;
	}
	
	public ArrayList<IOperation> getOperationsBeforeByJob(OperationIndexVO operation){
		ArrayList<IOperation> operations = new ArrayList<IOperation>();
		for(IOperation op : vector){
			if(op.getOperationIndex().getJobId()==operation.getJobId()){
				if(!op.getOperationIndex().equals(operation)){
					operations.add(op);
				}else{
					return operations;
				}
			}
				
		}
		return operations;
	}
	
	public ArrayList<IOperation> getLastOperation(){
		calculateCMatrix();
		ArrayList<IOperation> operations = new ArrayList<IOperation>();
		int lastTime=0;
		for(int i=0; i< vector.size();i++){
			IOperation temp= vector.get(i);
			if(temp.getFinalTime()>lastTime){
				lastTime= temp.getFinalTime();
			}
		}
		
		for(int i=0; i< vector.size();i++){
			IOperation temp= vector.get(i);
			if(temp.getFinalTime()==lastTime){
				operations.add(temp);
			}
		}
		
		return operations;
	}
	
	public ArrayList<CriticalRoute> getLongestRoute(CriticalRoute route){
		
		ArrayList<CriticalRoute> routes = new ArrayList<CriticalRoute>();
		IOperation lastOperation= route.getRoute().get(0);
		ArrayList<IOperation> operationsStation = getOperationsBeforeByStation(lastOperation.getOperationIndex());
		ArrayList<IOperation> operationsJob = getOperationsBeforeByJob(lastOperation.getOperationIndex());
		if(!operationsJob.isEmpty()){
			IOperation operationBeforeByJob = operationsJob.get(operationsJob.size()-1);
			if(!operationsStation.isEmpty()){
				IOperation operationBeforeByStation = operationsStation.get(operationsJob.size()-1);
				if(operationBeforeByJob.getFinalTime()>operationBeforeByStation.getFinalTime()){
					route.addNodeBegin(operationBeforeByJob);
					operationsJob=null;
					operationsStation=null;
					return getLongestRoute(route);
					
				}
				else if(operationBeforeByJob.getFinalTime()==operationBeforeByStation.getFinalTime()){
					CriticalRoute clone = new CriticalRoute();
					for(int j=0; j<route.getRoute().size();j++){
						IOperation node = route.getRoute().get(j);
						clone.getRoute().add(node);
					}
					route.addNodeBegin(operationBeforeByJob);
					clone.addNodeBegin(operationBeforeByStation);
					ArrayList<CriticalRoute> temp1 = getLongestRoute(route);
					
					ArrayList<CriticalRoute> temp2 = getLongestRoute(clone);
					
					temp1.addAll(temp2);
					operationsJob=null;
					operationsStation=null;
					return temp1;
				}
				else{
					route.addNodeBegin(operationBeforeByStation);
					operationsJob=null;
					operationsStation=null;
					return getLongestRoute(route);
					
				}
			}
			else{
				route.addNodeBegin( operationBeforeByJob);
				operationsJob=null;
				operationsStation=null;
				return getLongestRoute(route);
			}
	
		}
		else{
			if(!operationsStation.isEmpty()){
					IOperation operationBeforeByStation = operationsStation.get(operationsStation.size()-1);
					route.addNodeBegin(operationBeforeByStation);
					operationsJob=null;
					operationsStation=null;
					return getLongestRoute(route);
			}
			else{
				routes.add(route);
				operationsJob=null;
				operationsStation=null;
				return routes;	
			}
		}
		
	}
		
		
	
	public ArrayList<CriticalRoute> getLongestRoutes(){
		ArrayList<CriticalRoute> routes = new ArrayList<CriticalRoute>();
		ArrayList<IOperation> finalNodes = getLastOperation();
		
		for(int i=0; i < finalNodes.size();i++ ){
			CriticalRoute temp= new CriticalRoute();
			temp.addNodeBegin(finalNodes.get(i));
			routes.addAll(getLongestRoute(temp));
		}
		
		
		
		return routes;
	}
	
}