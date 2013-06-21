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
 * @author Lindsay �lvarez
 * @author Oriana Cendales
 * @author Jaime Romero
 * @author Juan Pablo Caballero-Villalobos
 */
public class Vector extends AbstractStructure{

	// -----------------------------------------------
	// Attributes
	// -----------------------------------------------

	/** ArrayList with the permutation list  */
	private ArrayList<IOperation> solution = null;
	
	/** ArrayList with the permutation list interpreted according to the defined algorithm */
	private ArrayList<IOperation> interpretation = null;

	/** Flag that indicates if the structure indicators are up to date according to the state of the structure. */
	private boolean synch;
	
	/** A matrix of the current solution. */
	private int[][] A;
	
	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------

	/**
	 * Constructor of the class that initializes an empty structure
	 */
	public Vector(int totalJobs, int totalStations){
		super(totalJobs, totalStations);
		
		solution = new ArrayList<IOperation>();
		interpretation = new ArrayList<IOperation>();
		synch = false;
	}
	
	/**
	 * Constructor of the class
	 */
	public Vector(String processingTimesFile, ArrayList<BetaVO> pBetas) throws Exception {
		super(processingTimesFile, pBetas);
		
		solution = new ArrayList<IOperation>();
		interpretation = new ArrayList<IOperation>();
		synch = false;
	}

	// -------------------------------------------------
	// Neighbor methods
	// -------------------------------------------------
	
	@Override
	public void exchangeOperations(int initialOperationPosition, int finalOperationPosition) {
		IOperation firstOperation = solution.get(initialOperationPosition);
		IOperation secondOperation = solution.get(finalOperationPosition);
		
		solution.set(initialOperationPosition, secondOperation);
		solution.set(finalOperationPosition, firstOperation);
		synch = false;
	}
	
	@Override
	public void exchangeOperations(OperationIndexVO initialOperationIndex, OperationIndexVO finalOperationIndex) {
		int initialOperationPosition = this.getPositionByOperationIndex(initialOperationIndex);
		int finalOperationPosition = this.getPositionByOperationIndex(finalOperationIndex);
		
		IOperation initialOperation = this.getOperationByOperationIndex(initialOperationIndex);
		IOperation finalOperation = this.getOperationByOperationIndex(finalOperationIndex);
		
		solution.set(initialOperationPosition, finalOperation);
		solution.set(finalOperationPosition, initialOperation);
		synch = false;
	}
	
	@Override
	public void insertOperationBefore(int first, int second) {
		IOperation secondOperation = solution.get(second);
		
		if(first < second){
			solution.remove(secondOperation);
			solution.add(first, secondOperation);
		}else{
			solution.add(first, secondOperation);
			solution.remove(secondOperation);
		}
		synch = false;
	}
	
	@Override
	public void insertOperationBefore(OperationIndexVO toInsertOperationIndex, OperationIndexVO successorOperationIndex) {
		int toInsertOperationPosition = this.getPositionByOperationIndex(toInsertOperationIndex);
		int successorOperationPosition = this.getPositionByOperationIndex(successorOperationIndex);
		
		IOperation successorOperation = this.getOperationByOperationIndex(successorOperationIndex);
		if(toInsertOperationPosition < successorOperationPosition){
			solution.remove(successorOperationPosition);
			solution.add(toInsertOperationPosition, successorOperation);
		}else{
			solution.add(toInsertOperationPosition, successorOperation);
			solution.remove(successorOperationPosition);
		}
		synch = false;
	}

	@Override
	public void insertOperationAfter(int toInsertOperationPosition, int successorOperationPosition) {
		IOperation secondOperation = solution.get(successorOperationPosition);
		
		if(toInsertOperationPosition > successorOperationPosition){
			solution.remove(secondOperation);
			solution.add(toInsertOperationPosition, secondOperation);
		}else if(toInsertOperationPosition < successorOperationPosition){
			solution.remove(secondOperation);
			if(toInsertOperationPosition == solution.size() - 1){
				solution.add(secondOperation);
			}else{
				solution.add(toInsertOperationPosition + 1,secondOperation);
			}
		}
		synch = false;
	}
	
	@Override
	public void insertOperationAfter(OperationIndexVO toInsertOperationIndex, OperationIndexVO successorOperationIndex) {
		int toInsertOperationPosition = this.getPositionByOperationIndex(toInsertOperationIndex);
		int successorOperationPosition = this.getPositionByOperationIndex(successorOperationIndex);
		
		IOperation successorOperation = this.getOperationByOperationIndex(successorOperationIndex);
		if(toInsertOperationPosition > successorOperationPosition){
			solution.remove(successorOperation);
			solution.add(toInsertOperationPosition, successorOperation);
		}else if(toInsertOperationPosition < successorOperationPosition){
			solution.remove(successorOperation);
			if(toInsertOperationPosition == solution.size() - 1){
				solution.add(successorOperation);
			}else{
				solution.add(toInsertOperationPosition + 1, successorOperation);
			}
		}
		synch = false;
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
		for (IOperation operation : solution) {
			if(operation.getOperationIndex().equals(operationIndex))
				return operation;
		}
		return null;
	}
	
	@Override
	public int getPositionByOperationIndex(OperationIndexVO operationIndex) {
		int i = 0;
		for (IOperation operation : solution) {
			if(operation.getOperationIndex().equals(operationIndex))
				return i;
			i++;
		}
		return -1;
	}
	
	@Override
	public IOperation getOperationByPosition(int position){
		return solution.get(position);
	}
	
	@Override
	public ArrayList<IOperation> getOperations(){
		return solution;
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
		if(synch){
			return C;
		}else{
			decodeSolution();
			int [][] CSolution = new int[getTotalJobs()][getTotalStations() + 1];
			
			for (int i = 0; i < solution.size(); i++) {
				IOperation Cij = solution.get(i);
				
				int Ciminus1J = getCiminus1J(Cij, i, solution) != null ? getCiminus1J(Cij, i, solution).getFinalTime() : 0;
				int CiJminus1 = getCiJminus1(Cij, i, solution) != null ? getCiJminus1(Cij, i, solution).getFinalTime() : 0;
				
				int sumTTBetas = this.getTTBetas(getCiminus1J(Cij, i, solution), i, solution);
				int sumSetupBetas = this.getSetupBetas(Cij.getOperationIndex().getJobId(), Cij.getOperationIndex().getStationId());
				
				int initialTime = Math.max(Ciminus1J + sumTTBetas, CiJminus1);
				int finalTime = initialTime + Cij.getProcessingTime() + sumSetupBetas;
				
				Cij.setInitialTime(initialTime);
				Cij.setFinalTime(finalTime);
				
				CSolution[Cij.getOperationIndex().getJobId()][Cij.getOperationIndex().getStationId()] = finalTime;
			}
			
			int [][] CIntepretation = new int[getTotalJobs()][getTotalStations() + 1];
			
			for (int i = 0; i < interpretation.size(); i++) {
				IOperation Cij = interpretation.get(i);
				
				int Ciminus1J = getCiminus1J(Cij, i, interpretation) != null ? getCiminus1J(Cij, i, interpretation).getFinalTime() : 0;
				int CiJminus1 = getCiJminus1(Cij, i, interpretation) != null ? getCiJminus1(Cij, i, interpretation).getFinalTime() : 0;
				
				int sumTTBetas = this.getTTBetas(getCiminus1J(Cij, i, interpretation), i, interpretation);
				int sumSetupBetas = this.getSetupBetas(Cij.getOperationIndex().getJobId(), Cij.getOperationIndex().getStationId());
				
				int initialTime = Math.max(Ciminus1J + sumTTBetas, CiJminus1);
				int finalTime = initialTime + Cij.getProcessingTime() + sumSetupBetas;
				
				Cij.setInitialTime(initialTime);
				Cij.setFinalTime(finalTime);
				
				CIntepretation[Cij.getOperationIndex().getJobId()][Cij.getOperationIndex().getStationId()] = finalTime;
			}
			
			//TODO seleccionar la mejor
			C = CIntepretation;
			
			// Aplying TearDown betas
			int[][] newC = applyTearDownBetas();

			if (newC != null)
				C = newC;
			
			synch = true;
			return C;
		}
	}
	
	private void decodeSolution(){
		// Arreglo con las operaciones sin programar
		interpretation = (ArrayList<IOperation>) solution.clone();
		
		for (int i = 0; i < interpretation.size(); i++){
			IOperation iOperation = interpretation.get(i);
			int sumTTBetas = this.getTTBetas(null, i, interpretation);
			iOperation.setInitialTime(sumTTBetas);
			iOperation.setScheduled(false);
		}
		
		int operationsAmount = interpretation.size();
		int index = 0;
		
		// Iteracion del algoritmo constructivo. Por cada iteraci�n, programa una operacion
		while(index < operationsAmount){
			
			// Calcula el menor tiempo de inicio
			int minInitialTime = Integer.MAX_VALUE;
			for (int i = index; i < interpretation.size(); i++) {
				IOperation operation = interpretation.get(i);
				int currentInitialTime = operation.getInitialTime();
				if(currentInitialTime < minInitialTime)
					minInitialTime = currentInitialTime;
			}
			
			// Selecciona la pr�xima operaci�n a programar tomando la primera cuyo tiempo de inicio sea el m�nimo posible. 
			IOperation selectedOperation = null;
			for (int i = index; i < interpretation.size(); i++) {
				IOperation operation = interpretation.get(i);
				if(operation.getInitialTime() == minInitialTime){
					selectedOperation = operation;
					break;
				}
			}
			
			selectedOperation.setScheduled(true);
			
			// Hace los intercambios propios de la interpretaci�n
			if(selectedOperation != interpretation.get(index)){
				IOperation operationAtIndex = interpretation.get(index);
				int indexOfSelected = interpretation.indexOf(selectedOperation);
				
				interpretation.set(index, selectedOperation);
				interpretation.set(indexOfSelected, operationAtIndex);
			}
			
			// Calcula el C de la operaci�n a decodificar.
			int finalTimeToSchedule = selectedOperation.getInitialTime() + selectedOperation.getProcessingTime() ;
			selectedOperation.setFinalTime(finalTimeToSchedule);
			
			// Actualizando los tiempos de inicio de las operaciones que quedan por programar
			for (int i = index + 1; i < interpretation.size(); i++){
				IOperation iOperation = interpretation.get(i);
				
				IOperation lastJobSchedule = getCiminus1J(iOperation, i, interpretation);
				int finalTimeLastJob = lastJobSchedule != null ? lastJobSchedule.getFinalTime() : 0;
				
				IOperation lastStationSchedule = getCiJminus1(iOperation, i, interpretation);
				int finalTimeLastStation = lastStationSchedule != null ? lastStationSchedule.getFinalTime() : 0;
				
				int sumTTBetas = this.getTTBetas(getCiminus1J(iOperation, i, interpretation), i, interpretation);
				
				int initialTime = Math.max(finalTimeLastJob + sumTTBetas, finalTimeLastStation);
				iOperation.setInitialTime(initialTime);
			}
			index++;
		}
	}
	
	private int getLastJobTime(int jobId, int[][] C) {
		int max = -1;
		for (int i = 0; i < C.length; i++) {
			if(max < C[jobId][i]){
				max = C[jobId][i];
			}
		}
		return max;
	}
	
	private int getLastStationTime(int stationId, int[][] C) {
		
		int max = -1;
		for (int i = 0; i < C.length; i++) {
			if(max < C[i][stationId]){
				max = C[i][stationId];
			}
		}
		return max;
	}

	@Override
	public int[][] updateCMatrix(PairVO pair){
		if(synch){
			return C;
		}else{
			for (int i = Math.max(pair.getX(), pair.getY()); i < solution.size(); i++) {
				IOperation Cij = solution.get(i);
				
				int Ciminus1J = getCiminus1J(Cij, i, solution) != null ? getCiminus1J(Cij, i, solution).getFinalTime() : 0;
				int CiJminus1 = getCiJminus1(Cij, i, solution) != null ? getCiJminus1(Cij, i, solution).getFinalTime() : 0;
				
				int sumTTBetas = this.getTTBetas(getCiminus1J(Cij, i, solution), i, solution);
				
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
			
			synch = true;
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
		for (int i = 0; i < interpretation.size(); i++) {
			IOperation operation = interpretation.get(i);
			if (operation.getOperationIndex().getJobId() == operation
					.getOperationIndex().getStationId()) {
				return operation.getInitialTime();
			}
		}
		return 0;
	}

	@Override
	public int geFinalTime(OperationIndexVO operationIndex) {
		for (int i = 0; i < interpretation.size(); i++) {
			IOperation operation = interpretation.get(i);
			if (operation.getOperationIndex().getJobId() == operation
					.getOperationIndex().getStationId()) {
				return operation.getFinalTime();
			}
		}
		return 0;
	}
	
	@Override
	public IOperation getCiminus1J(IOperation Cij, int vectorPos, ArrayList<IOperation> vector){
		boolean beforeCounted = false;
		for (int j = vectorPos; j >= 0 && !beforeCounted; j--) {
			IOperation operationJ = vector.get(j);
			if (operationJ.getOperationIndex().getJobId() == Cij
					.getOperationIndex().getJobId() && vectorPos != j && operationJ.isScheduled()) {
				return operationJ;
			}
		}
		return null;
	}
	
	@Override
	public IOperation getCiJminus1(IOperation Cij, int vectorPos, ArrayList<IOperation> vector){
		boolean beforeCounted = false;
		for (int j = vectorPos; j >= 0 && !beforeCounted; j--) {
			IOperation operationJ = vector.get(j);
			if (operationJ.getOperationIndex().getStationId() == Cij
					.getOperationIndex().getStationId() && operationJ.getOperationIndex().getMachineId() == Cij
							.getOperationIndex().getMachineId() && vectorPos != j && operationJ.isScheduled()) {
				return operationJ;
			}
		}
		return null;
	}
	
	@Override
	public IOperation getPosition(int pos) {
		return solution.get(pos);
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
			BetaVO beta = new BetaVO(nextKey, className, informationFiles, this.betas.get(nextKey).isConsidered());
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
	public int getTTBetas(IOperation Cij, int predecessor, ArrayList<IOperation> vector) {
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
		// TODO getReleaseBetas on Vector
		return 0;
	}
	
	// -------------------------------------------------
	// Manipulation methods
	// -------------------------------------------------
	
	@Override
	public void scheduleOperation(OperationIndexVO operationIndexVO) {
		operationsMatrix[operationIndexVO.getJobId()][operationIndexVO.getStationId()].setScheduled(true);
		solution.add(operationsMatrix[operationIndexVO.getJobId()][operationIndexVO.getStationId()]);
		int maxAmountOfJobs = -1;
		int maxAmountOfStations = -1;
		int maxAmountOfMachinesPerStation = -1;
		
		for (IOperation operation : solution) {
			if((operation.getOperationIndex().getJobId() + 1) > maxAmountOfJobs)
				maxAmountOfJobs = (operation.getOperationIndex().getJobId() + 1);
			if((operation.getOperationIndex().getStationId() + 1) > maxAmountOfStations)
				maxAmountOfStations = (operation.getOperationIndex().getStationId() + 1);
			if((operation.getOperationIndex().getMachineId() + 1) > maxAmountOfMachinesPerStation)
				maxAmountOfMachinesPerStation = (operation.getOperationIndex().getMachineId() + 1);
		}
		
		synch = false;
	}
	
	@Override
	public void removeOperationFromSchedule(OperationIndexVO operationIndex) {
		IOperation toRemove = this.getOperationByOperationIndex(operationIndex);
		toRemove.setScheduled(false);
		solution.remove(toRemove);
		synch = false;
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

			ArrayList<IOperation> clonedSolution = new ArrayList<IOperation>();
			for (IOperation operation : solution) {
				IOperation cloneOperation = operation.clone();
				clonedSolution.add(cloneOperation);
			}
			clone.solution = clonedSolution;
			
			ArrayList<IOperation> clonedInterpretation = new ArrayList<IOperation>();
			for (IOperation operation : interpretation) {
				IOperation cloneOperation = operation.clone();
				clonedInterpretation.add(cloneOperation);
			}
			clone.interpretation = clonedInterpretation;
			
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
		synch = false;
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
			IOperation currentOperation = interpretation.get(i);
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
		synch = false;
		return newC == null ? C : newC;
	}
	
	// -------------------------------------------------
	// Critical paths methods
	// -------------------------------------------------
	
	/**
	 * Returns the collection of critical paths of the current solution
	 * @return criticalPaths. Collection of critical paths of the current solution. 
	 */
	public ArrayList<CriticalRoute> getCriticalPaths(){
		ArrayList<CriticalRoute> routes = new ArrayList<CriticalRoute>();
		ArrayList<IOperation> finalNodes = getLastOperation();
		
		for(int i=0; i < finalNodes.size();i++ ){
			CriticalRoute temp= new CriticalRoute();
			temp.addNodeBegin(finalNodes.get(i));
			routes.addAll(getLongestRoute(temp));
		}		
		return routes;
	}
	
	/**
	 * Returns the collection of operations such that their C value is the biggest one.
	 * @return lastOperations. A collection of IOperations such that its C value is the biggest one.
	 */
	public ArrayList<IOperation> getLastOperation(){
		calculateCMatrix();
		ArrayList<IOperation> operations = new ArrayList<IOperation>();
		int lastTime=0;
		for(int i=0; i< interpretation.size();i++){
			IOperation temp= interpretation.get(i);
			if(temp.getFinalTime()>lastTime){
				lastTime= temp.getFinalTime();
			}
		}
		
		for(int i=0; i< interpretation.size();i++){
			IOperation temp= interpretation.get(i);
			if(temp.getFinalTime()==lastTime){
				operations.add(temp);
			}
		}
		return operations;
	}
	
	/**
	 * Complete the critical path in the parameter according to the initial nodes it contains
	 * @param criticalPath The collection of critical paths associated with the node
	 * @return
	 */
	private ArrayList<CriticalRoute> getLongestRoute(CriticalRoute route){
		ArrayList<CriticalRoute> routes = new ArrayList<CriticalRoute>();
		IOperation lastOperation= route.getRoute().get(0);
		ArrayList<IOperation> operationsStation = getOperationsBeforeByStation(lastOperation.getOperationIndex());
		ArrayList<IOperation> operationsJob = getOperationsBeforeByJob(lastOperation.getOperationIndex());
		if(!operationsJob.isEmpty()){
			IOperation operationBeforeByJob = operationsJob.get(operationsJob.size()-1);
			if(!operationsStation.isEmpty()){
				IOperation operationBeforeByStation = operationsStation.get(operationsStation.size()-1);
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
	
	/**
	 * Returns the set of operations that are in the same station and that are scheduled before the one in the parameter
	 * @param operation Reference operation
	 * @return
	 */
	private ArrayList<IOperation> getOperationsBeforeByStation(OperationIndexVO operation){
		ArrayList<IOperation> operations = new ArrayList<IOperation>();
		for(IOperation op : interpretation){
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
	
	/**
	 * Returns the set of operations that are in the same job and that are scheduled before the one in the parameter
	 * @param operation Reference operation
	 * @return
	 */
	private ArrayList<IOperation> getOperationsBeforeByJob(OperationIndexVO operation){
		ArrayList<IOperation> operations = new ArrayList<IOperation>();
		for(IOperation op : interpretation){
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
	
	// -------------------------------------------------
	// Getters and setters
	// -------------------------------------------------

	public ArrayList<IOperation> getVector(){
		return solution;
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
		synch = false;
	}

	@Override
	public void setTotalStations(int totaStations) {
		this.totalStations = totaStations;
		synch = false;
	}

	@Override
	public void setMaxMachinesPerStation(int maxMachinesPerStation) {
		this.maxMachinesPerStation = maxMachinesPerStation;
		synch = false;
	}
	
	public ArrayList<int[]> getWeightedNodesCriticaRoute(){
		
		return null;
	}
	
	@Override
	public boolean validateStructure() {
		return true;
	}
		
}