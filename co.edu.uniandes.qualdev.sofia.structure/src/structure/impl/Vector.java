package structure.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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
 * @author Lindsay Alvarez
 * @author Oriana Cendales
 * @author Jaime Romero
 * @author Juan Pablo Caballero-Villalobos
 */
public class Vector extends AbstractStructure{

	// -----------------------------------------------
	// Attributes
	// -----------------------------------------------
	
	/** ArrayList with the permutation list interpreted according to the defined algorithm */
	private ArrayList<IOperation> vectorDecodNonDelay = null;

	private ArrayList<IOperation> vectorDecodSimple = null;
	
	private ArrayList<IOperation> vectorDecodActiveSchedule = null;
	
	/** Flag that indicates if the structure indicators are up to date according to the state of the structure. */
	private boolean synch;
	
	/** A matrix of the current solution. */
	private int[][] A;
	
	private int [][] CIntepretation;
	
	private int [][] CActiveSchedule;
	
	private boolean nonDelayActive = false;
	
	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------

	/**
	 * Constructor of the class that initializes an empty structure
	 */
	public Vector(int totalJobs, int totalStations){
		super(totalJobs, totalStations);
		//TODO en algun lado  hay que armar las IOperation apuntando a los operationIndex de la matrix de STRUCTURE
		vectorDecodNonDelay = new ArrayList<IOperation>();
		vectorDecodSimple = new ArrayList<IOperation>();
		synch = false;
	}
	
	/**
	 * Constructor of the class
	 */
	public Vector(String processingTimesFile, ArrayList<BetaVO> pBetas) throws Exception {
		super(processingTimesFile, pBetas);
		
		vectorDecodNonDelay = new ArrayList<IOperation>();
		vectorDecodSimple = new ArrayList<IOperation>();
		synch = false;
	}
	
	/**
	 * Constructor of the class
	 */
	public Vector(String processingTimesFile, String mVector, ArrayList<BetaVO> pBetas) throws Exception {
		super(processingTimesFile, mVector, pBetas);
		
		vectorDecodNonDelay = new ArrayList<IOperation>();
		vectorDecodSimple = new ArrayList<IOperation>();
		synch = false;
	}

	// -------------------------------------------------
	// Neighbor methods
	// -------------------------------------------------
	
	@Override
	public void exchangeOperations(int initialOperationPosition, int finalOperationPosition) {
		IOperation firstOperation = getOperations().get(initialOperationPosition);
		IOperation secondOperation = getOperations().get(finalOperationPosition);
				
		getOperations().set(initialOperationPosition, secondOperation);
		getOperations().set(finalOperationPosition, firstOperation);
		synch = false;
	}
	
	@Override
	public void exchangeOperations(OperationIndexVO initialOperationIndex, OperationIndexVO finalOperationIndex) {
		int initialOperationPosition = this.getPositionByOperationIndex(initialOperationIndex);
		int finalOperationPosition = this.getPositionByOperationIndex(finalOperationIndex);
		
		IOperation initialOperation = this.getOperationByOperationIndex(initialOperationIndex);
		IOperation finalOperation = this.getOperationByOperationIndex(finalOperationIndex);
		
		getOperations().set(initialOperationPosition, finalOperation);
		getOperations().set(finalOperationPosition, initialOperation);
		
		synch = false;
	}
	
	@Override
	public void insertOperationBefore(int first, int second) {
		IOperation secondOperation = getOperations().get(second);
		
		if(first < second){
			getOperations().remove(secondOperation);
			getOperations().add(first, secondOperation);
		}else{
			getOperations().add(first, secondOperation);
			getOperations().remove(secondOperation);
		}
		synch = false;
	}
	
	@Override
	public void insertOperationBefore(OperationIndexVO toInsertOperationIndex, OperationIndexVO successorOperationIndex) {
		int toInsertOperationPosition = this.getPositionByOperationIndex(toInsertOperationIndex);
		int successorOperationPosition = this.getPositionByOperationIndex(successorOperationIndex);
		
		IOperation successorOperation = this.getOperationByOperationIndex(successorOperationIndex);
		if(toInsertOperationPosition < successorOperationPosition){
			getOperations().remove(successorOperationPosition);
			getOperations().add(toInsertOperationPosition, successorOperation);
		}else{
			getOperations().add(toInsertOperationPosition, successorOperation);
			getOperations().remove(successorOperationPosition);
		}
		synch = false;
	}

	@Override
	public void insertOperationAfter(int toInsertOperationPosition, int successorOperationPosition) {
		IOperation secondOperation = getOperations().get(successorOperationPosition);
		
		if(toInsertOperationPosition > successorOperationPosition){
			getOperations().remove(secondOperation);
			getOperations().add(toInsertOperationPosition, secondOperation);
		}else if(toInsertOperationPosition < successorOperationPosition){
			getOperations().remove(secondOperation);
			if(toInsertOperationPosition == getOperations().size() - 1){
				getOperations().add(secondOperation);
			}else{
				getOperations().add(toInsertOperationPosition + 1,secondOperation);
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
			getOperations().remove(successorOperation);
			getOperations().add(toInsertOperationPosition, successorOperation);
		}else if(toInsertOperationPosition < successorOperationPosition){
			getOperations().remove(successorOperation);
			if(toInsertOperationPosition == getOperations().size() - 1){
				getOperations().add(successorOperation);
			}else{
				getOperations().add(toInsertOperationPosition + 1, successorOperation);
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
		for (IOperation operation : getOperations()) {
			if(operation.getOperationIndex().equals(operationIndex))
				return operation;
		}
		return null;
	}
	
	@Override
	public int getPositionByOperationIndex(OperationIndexVO operationIndex) {
		int i = 0;
		for (IOperation operation : getOperations()) {
			if(operation.getOperationIndex().equals(operationIndex))
				return i;
			i++;
		}
		return -1;
	}
	
	@Override
	public IOperation getOperationByPosition(int position){
		return getOperations().get(position);
	}
	
	@Override
	public ArrayList<IOperation> getOperations(){
		return vectorDecodSimple;
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
			// Decodificaci�n simple en la que la lista de permutaci�n representa el orden en el que las operaciones deben ser programadas. 
			int [][] CSolution = new int[getTotalJobs()][getTotalStations() + 1];
			
			for (int i = 0; i < vectorDecodSimple.size(); i++) {
				IOperation Cij = vectorDecodSimple.get(i);
				
				int Ciminus1J = getCiminus1J(Cij, i, vectorDecodSimple) != null ? getCiminus1J(Cij, i, vectorDecodSimple).getFinalTime() : 0;
				int CiJminus1 = getCiJminus1(Cij, i, vectorDecodSimple) != null ? getCiJminus1(Cij, i, vectorDecodSimple).getFinalTime() : 0;
				
				int sumTTBetas = this.getTTBetas(getCiminus1J(Cij, i, vectorDecodSimple), i, vectorDecodSimple);
				int sumSetupBetas = this.getSetupBetas(Cij.getOperationIndex().getJobId(), Cij.getOperationIndex().getStationId());
				
				int initialTime = Math.max(Ciminus1J + sumTTBetas, CiJminus1);
				int finalTime = initialTime + Cij.getOperationIndex().getProcessingTime() + sumSetupBetas;
				
				Cij.setInitialTime(initialTime);
				Cij.setFinalTime(finalTime);
				
				CSolution[Cij.getOperationIndex().getJobId()][Cij.getOperationIndex().getStationId()] = finalTime;
			}
			
			// Decodificaci�n non-delay en la que la lista de permutaci�n representa un criterior de desempate para la programaci�n de las operaciones
			// que se hace como una regla de despacho. Es decir, se toman las que menor tiempo de inicio y se selecciona la que primero aparezca en la lista. 
			decodeSolution();
			
			C = CSolution;
			int[][] newC = applyTearDownBetas(C);
			if (newC != null)
				C = newC;
		
			synch = true;
			return C;
		}
	}
	
	public void decodeSolutionActiveSchedule(){
		
		int sizeList = vectorDecodSimple.size();
		ArrayList<IOperation> copia = new ArrayList<IOperation>();
		for (int i = 0; i < sizeList; i++){
			IOperation iOperations = new Operation (vectorDecodSimple.get(i).getOperationIndex());
			copia.add(iOperations);
		}
		
		vectorDecodSimple = new ArrayList<IOperation>();
		int actualSize = 0;
		ArrayList<OperationIndexVO> unschedulledOperations = new ArrayList<OperationIndexVO>();
		for(int j =0; j<getProblem().length;j++){
			for(int z=0; z<getProblem()[j].length;z++){	
				unschedulledOperations.add(getProblem()[j][z]);
			}
		}
		
		while (actualSize<sizeList){
			
			// Selecciona la operaci�n (de la lista de operaciones no programadas) termina lo antes posible. La guarda en la veriable operationIndexMinFinalTime
			int minFinalTime = Integer.MAX_VALUE;
			OperationIndexVO operationIndexMinFinalTime = null;
			for(int j =0; j<unschedulledOperations.size();j++){
				OperationIndexVO temp = unschedulledOperations.get(j);
				boolean canSchedulled = scheduleOperation(temp);
				if (canSchedulled){
					calculateCMatrix();
					int finalTime = vectorDecodSimple.get(actualSize).getFinalTime();
					if (finalTime < minFinalTime ) {
						minFinalTime = finalTime;
						operationIndexMinFinalTime = temp;
					}
					vectorDecodSimple.remove(actualSize);
				}
				else{
					unschedulledOperations.remove(j);
				}
			}
			
			// Arreglo de candidatas. 
			ArrayList<OperationIndexVO> activeCandidates = new ArrayList<OperationIndexVO>();
			activeCandidates.add(operationIndexMinFinalTime);
			
			// Obtendiendo todas las oepraciones que se pueden programar en la misma m�quina de la misma estaci�n.
			//Filtrando las operaciones para que queden solamente las que resultan en un programa activo
			for(int i=0; i<getProblem().length; i++){
				OperationIndexVO temp = getProblem()[i][operationIndexMinFinalTime.getMachineId()];
				if(temp.getJobId()!=operationIndexMinFinalTime.getJobId()){
					boolean canSchedulled = scheduleOperation(temp);
					if (canSchedulled){
						calculateCMatrix();
						int startTimeTested = vectorDecodSimple.get(actualSize).getInitialTime();
						if (startTimeTested < minFinalTime) {
							activeCandidates.add(temp);
						}
						vectorDecodSimple.remove(actualSize);
					}
				}
			}
						
			//Desempate: Selecciona la primera de las operaciones que est� en la lista de permutaci�n. 
			boolean schedulled = false;
			OperationIndexVO chosen = null;
			int minIndex = copia.size();
			for(int i=0; i< activeCandidates.size() && !schedulled;i++){
				OperationIndexVO temp =  activeCandidates.get(i);
				for(int j=0; j< copia.size()&& !schedulled;j++){
					OperationIndexVO opIndex = copia.get(j).getOperationIndex(); 
					if(temp.getJobId()==opIndex.getJobId() && temp.getStationId()== opIndex.getStationId()&& j<minIndex){
						chosen = temp;
						minIndex =j;
					}	
				}
			}
			
			schedulled = scheduleOperation(chosen);
			if (schedulled){
				actualSize++;
				unschedulledOperations.remove(chosen);
			}
			
		}
		
		vectorDecodActiveSchedule = vectorDecodSimple;
		vectorDecodSimple = new ArrayList<IOperation>();
				
		CActiveSchedule = new int[getTotalJobs()][getTotalStations() + 1];
		
		for(int i=0; i< C.length;i++){
			for(int j=0; j<C[i].length;j++){
				CActiveSchedule[i][j] = C[i][j];
			}
		}
	
		for (int i = 0; i < sizeList; i++){
			scheduleOperation(copia.get(i).getOperationIndex());
		}
		calculateCMatrix();
		/*
		
		calculateCMatrix();
		ArrayList<IOperation> copia = new ArrayList<IOperation>();
		for (int i = 0; i < vectorDecodSimple.size(); i++){
			IOperation iOperations = vectorDecodSimple.get(i);
			copia.add(iOperations);
		}
		vectorDecodSimple = new ArrayList<IOperation>();
		int contador = 0;
		for (int i = 0; i < copia.size(); i++) {
			OperationIndexVO candidate = copia.get(i).getOperationIndex();
			ArrayList<OperationIndexVO> machinesCandidates = new ArrayList<OperationIndexVO>();
			
			for(int j=0; j<getProblem()[candidate.getJobId()].length; j++){
				OperationIndexVO temp = getProblem()[candidate.getJobId()][j];
				if(temp.getStationId()==candidate.getStationId()&& temp.getMachineId()!=candidate.getMachineId())
					machinesCandidates.add(temp);
			}
			
			int startTimeTested = 0;
			int minStartTime = copia.get(i).getInitialTime();
			
			for (int z = 0; z < machinesCandidates.size(); z++) {
				boolean canSchedulled = scheduleOperation(machinesCandidates.get(z));
				if (canSchedulled){
					calculateCMatrix();
					startTimeTested = vectorDecodSimple.get(contador).getInitialTime();
					if (startTimeTested < minStartTime) {
						minStartTime = startTimeTested;
						candidate = machinesCandidates.get(z);
					}
					vectorDecodSimple.remove(contador);
				}
			}
			
			machinesCandidates.clear();
			
			int minInitialTime = 0;
			int minFinalTime = Integer.MAX_VALUE;
			OperationIndexVO operationIndexInitialTime = null;
			for(int j =0; j<getProblem().length;j++){
				for(int z=0; z<getProblem()[j].length;z++){
					boolean canSchedulled = scheduleOperation(getProblem()[j][z]);
					if (canSchedulled){
						calculateCMatrix();
						int finalTime = vectorDecodSimple.get(contador).getFinalTime();
						if (finalTime < minFinalTime) {
							minInitialTime = vectorDecodSimple.get(contador).getInitialTime();
							minFinalTime = finalTime;
							operationIndexInitialTime = getProblem()[j][z];
						}
						vectorDecodSimple.remove(contador);
					}
				}
			}
			
			boolean schedulled = false;
			if(operationIndexInitialTime != null){
				if(operationIndexInitialTime.getJobId() == candidate.getJobId()&& minFinalTime<minStartTime-getTT(candidate.getStationId(), operationIndexInitialTime.getStationId()) ){
					schedulled = scheduleOperation(operationIndexInitialTime);
					i--;
				}
				else if(operationIndexInitialTime.getJobId() != candidate.getJobId()&& minInitialTime<minStartTime ){
					schedulled = scheduleOperation(operationIndexInitialTime);
					i--;
				}
				else{
					schedulled = scheduleOperation(candidate);
				}
			}
			else{
				schedulled = scheduleOperation(candidate);
			}
			if(schedulled)
				contador++;


		}
		*/
	}
	
    private void decodeSolution(){
		
		// Arreglo con todas las operaciones sin programar en el orden en el que aparece en la lista de permutaci�n.
		vectorDecodNonDelay = new ArrayList<IOperation>();
		for (int i = 0; i < vectorDecodSimple.size(); i++){
			ArrayList<IOperation> iOperations = getOperationsbyJobAndStation(vectorDecodSimple.get(i).getOperationIndex());
			vectorDecodNonDelay.addAll(iOperations);
		}
		
		// Actualiza los tiempos de inicio.
		for (int i = 0; i < vectorDecodNonDelay.size(); i++){
			IOperation iOperation = vectorDecodNonDelay.get(i);
			int sumTTBetas = this.getTTBetas(null, i, vectorDecodNonDelay);
			iOperation.setInitialTime(sumTTBetas);
			iOperation.setScheduled(false);
		}
		
		int operationsAmount = vectorDecodNonDelay.size();
		int index = 0;
		
		// CICLO PRINCIPAL:  Iteracion del algoritmo constructivo. Por cada iteraci�n, programa una operacion
		while(index < operationsAmount){
			
			// Calcula el menor tiempo de inicio
			int minInitialTime = Integer.MAX_VALUE;
			for (int i = index; i < vectorDecodNonDelay.size(); i++) {
				IOperation operation = vectorDecodNonDelay.get(i);
				int currentInitialTime = operation.getInitialTime();
				if(currentInitialTime < minInitialTime)
					minInitialTime = currentInitialTime;
			}
			
			// Selecciona la pr�xima operaci�n a programar tomando la primera cuyo tiempo de inicio sea el m�nimo posible. 
			IOperation selectedOperation = null;
			for (int i = index; i < vectorDecodNonDelay.size(); i++) {
				IOperation operation = vectorDecodNonDelay.get(i);
				if(operation.getInitialTime() == minInitialTime){
					selectedOperation = operation;
					break;
				}
			}
			
			if(selectedOperation!=null){
				selectedOperation.setScheduled(true);
				removeAll(vectorDecodNonDelay, selectedOperation);
				operationsAmount = vectorDecodNonDelay.size();
			}
			
			// Hace los intercambios propios de la interpretaci�n
			if(selectedOperation != vectorDecodNonDelay.get(index)){
				IOperation operationAtIndex = vectorDecodNonDelay.get(index);
				int indexOfSelected = vectorDecodNonDelay.indexOf(selectedOperation);
				
				vectorDecodNonDelay.set(index, selectedOperation);
				vectorDecodNonDelay.set(indexOfSelected, operationAtIndex);
			}
			
			// Calcula el C de la operaci�n a decodificar.
			int finalTimeToSchedule = selectedOperation.getInitialTime() + selectedOperation.getOperationIndex().getProcessingTime() ;
			selectedOperation.setFinalTime(finalTimeToSchedule);
			
			// Actualizando los tiempos de inicio de las operaciones que quedan por programar
			for (int i = index + 1; i < vectorDecodNonDelay.size(); i++){
				IOperation iOperation = vectorDecodNonDelay.get(i);
				
				IOperation lastJobSchedule = getCiminus1J(iOperation, i, vectorDecodNonDelay);
				int finalTimeLastJob = lastJobSchedule != null ? lastJobSchedule.getFinalTime() : 0;
				
				IOperation lastStationSchedule = getCiJminus1(iOperation, i, vectorDecodNonDelay);
				int finalTimeLastStation = lastStationSchedule != null ? lastStationSchedule.getFinalTime() : 0;
				
				int sumTTBetas = this.getTTBetas(getCiminus1J(iOperation, i, vectorDecodNonDelay), i, vectorDecodNonDelay);
				
				int initialTime = Math.max(finalTimeLastJob + sumTTBetas, finalTimeLastStation);
				iOperation.setInitialTime(initialTime);
			}
			index++;
		}
		
		// Actualiza la matriz c
		CIntepretation = new int[getTotalJobs()][getTotalStations() + 1];
		
		for (int i = 0; i < vectorDecodNonDelay.size(); i++) {
			IOperation Cij = vectorDecodNonDelay.get(i);
			
			int Ciminus1J = getCiminus1J(Cij, i, vectorDecodNonDelay) != null ? getCiminus1J(Cij, i, vectorDecodNonDelay).getFinalTime() : 0;
			int CiJminus1 = getCiJminus1(Cij, i, vectorDecodNonDelay) != null ? getCiJminus1(Cij, i, vectorDecodNonDelay).getFinalTime() : 0;
			
			int sumTTBetas = this.getTTBetas(getCiminus1J(Cij, i, vectorDecodNonDelay), i, vectorDecodNonDelay);
			int sumSetupBetas = this.getSetupBetas(Cij.getOperationIndex().getJobId(), Cij.getOperationIndex().getStationId());
			
			int initialTime = Math.max(Ciminus1J + sumTTBetas, CiJminus1);
			int finalTime = initialTime + Cij.getOperationIndex().getProcessingTime() + sumSetupBetas;
			
			Cij.setInitialTime(initialTime);
			Cij.setFinalTime(finalTime);
			
			CIntepretation[Cij.getOperationIndex().getJobId()][Cij.getOperationIndex().getStationId()] = finalTime;
		}
		
		int[][] newC2 = applyTearDownBetas(CIntepretation);
		if (newC2 != null)
			CIntepretation = newC2;
		
		
	}
	
	private void removeAll(ArrayList<IOperation> operations,
			IOperation selectedOperation) {
		
		int job = selectedOperation.getOperationIndex().getJobId();
		int station = selectedOperation.getOperationIndex().getStationId();
		int machine = selectedOperation.getOperationIndex().getMachineId();
		
		for(int i=0; i< operations.size();i++){
			OperationIndexVO temp = operations.get(i).getOperationIndex();
			
			if(temp.getJobId()==job && temp.getStationId()==station && temp.getMachineId()!=machine){
				operations.remove(i);
				i--;
			}
		}
		
	}
	
	private ArrayList<IOperation> getOperationsbyJobAndStation(OperationIndexVO operationIndex) {
		int jobId = operationIndex.getJobId();
		int stationId = operationIndex.getStationId();
		ArrayList <IOperation> operations = new ArrayList<IOperation>();
		for(int i =0; i< getProblem().length;i++){
			
			for(int j=0; j< getProblem()[i].length;j++){
				OperationIndexVO temp = getProblem()[i][j];
				if(temp.getJobId()==jobId && stationId == temp.getStationId()){
					operations.add(new Operation(temp));
				}
			}
			
		}
		return operations;
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
			for (int i = Math.max(pair.getX(), pair.getY()); i < vectorDecodSimple.size(); i++) {
				IOperation Cij = vectorDecodSimple.get(i);
				
				int Ciminus1J = getCiminus1J(Cij, i, vectorDecodSimple) != null ? getCiminus1J(Cij, i, vectorDecodSimple).getFinalTime() : 0;
				int CiJminus1 = getCiJminus1(Cij, i, vectorDecodSimple) != null ? getCiJminus1(Cij, i, vectorDecodSimple).getFinalTime() : 0;
				
				int sumTTBetas = this.getTTBetas(getCiminus1J(Cij, i, vectorDecodSimple), i, vectorDecodSimple);
				
				int initialTime = Math.max(Ciminus1J + sumTTBetas, CiJminus1);
				int finalTime = initialTime + + Cij.getOperationIndex().getProcessingTime();
				
				Cij.setInitialTime(initialTime);
				Cij.setFinalTime(finalTime);
				
				C[Cij.getOperationIndex().getJobId()][Cij
						.getOperationIndex().getStationId()] = finalTime;
			}
			
			// Aplying TearDown betas
			int[][] newC = applyTearDownBetas(C);

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
				matrix[i][j] = CMatrix[i][j] - getOperationByOperationIndex(new OperationIndexVO(0,i, j)).getOperationIndex().getProcessingTime();
			}
		}
		return matrix;
	}
	
	@Override
	public int[][] calculateAMatrix(){
		A = new int[this.totalJobs][this.totalStations];
		
		for (int i = 0; i < totalJobs; i++) {
			for (int j = 0; j < totalStations; j++) {
				A[i][j] = calculateRank(new OperationIndexVO(0,i, j));
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
		for (int i = 0; i < getOperations().size(); i++) {
			IOperation operation = getOperations().get(i);
			if (operation.getOperationIndex().getJobId() == operation
					.getOperationIndex().getStationId()) {
				return operation.getInitialTime();
			}
		}
		return 0;
	}

	@Override
	public int geFinalTime(OperationIndexVO operationIndex) {
		for (int i = 0; i < getOperations().size(); i++) {
			IOperation operation = getOperations().get(i);
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
		return getOperations().get(pos);
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
	public boolean scheduleOperation(OperationIndexVO operationIndexVO) {
		OperationIndexVO newOperationIndex = operationsMatrix[operationIndexVO.getJobId()][operationIndexVO.getMachineId()];
		Operation newOperation = new Operation(newOperationIndex);
		boolean schedule = canSchedule(operationIndexVO);
		if(schedule){
			newOperation.setScheduled(true);
			getOperations().add(newOperation);
			int maxAmountOfJobs = -1;
			int maxAmountOfStations = -1;
			int maxAmountOfMachinesPerStation = -1;
			for (IOperation operation : getOperations()) {
				if((operation.getOperationIndex().getJobId() + 1) > maxAmountOfJobs)
					maxAmountOfJobs = (operation.getOperationIndex().getJobId() + 1);
				if((operation.getOperationIndex().getStationId() + 1) > maxAmountOfStations)
					maxAmountOfStations = (operation.getOperationIndex().getStationId() + 1);
				if((operation.getOperationIndex().getMachineId() + 1) > maxAmountOfMachinesPerStation)
					maxAmountOfMachinesPerStation = (operation.getOperationIndex().getMachineId() + 1);
			}
			
		}
		
		synch = false;
		return schedule;
	}
	
	private boolean canSchedule(OperationIndexVO operationIndexVO) {
		for(int i=0; i<getOperations().size();i++){
			OperationIndexVO temp = getOperations().get(i).getOperationIndex();
			if(temp.getJobId()== operationIndexVO.getJobId() && operationIndexVO.getStationId()== temp.getStationId())
				return false;
		}
		return true;
	}

	@Override
	public void removeOperationFromSchedule(OperationIndexVO operationIndex) {
		IOperation toRemove = this.getOperationByOperationIndex(operationIndex);
		toRemove.setScheduled(false);
		getOperations().remove(toRemove);
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
			clone.operationsMatrix = this.operationsMatrix;
			clone.processingTimesFile = this.processingTimesFile;
			clone.totalJobs = this.totalJobs;
			clone.totalStations = this.totalStations;
			clone.maxMachinesPerStation = this.maxMachinesPerStation;
			clone.totalMachines = this.totalMachines;
			clone.nonDelayActive = this.nonDelayActive;
			
			ArrayList<IOperation> clonedVectorDecSimple = new ArrayList<IOperation>();
			for (IOperation operation : vectorDecodSimple) {
				IOperation cloneOperation = operation.clone();
				clonedVectorDecSimple.add(cloneOperation);
			}
			
			clone.vectorDecodSimple = clonedVectorDecSimple;
			
			ArrayList<IOperation> clonedVectorDecNonDelay= new ArrayList<IOperation>();
			for (IOperation operation : vectorDecodNonDelay) {
				IOperation cloneOperation = operation.clone();
				clonedVectorDecNonDelay.add(cloneOperation);
			}
			
			clone.vectorDecodNonDelay = clonedVectorDecNonDelay;
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
			IOperation currentOperation = getOperations().get(i);
			if(comparisonJob == currentOperation.getOperationIndex().getJobId()
					^ comparisonStation == currentOperation.getOperationIndex().getStationId()){
				rank++;
				comparisonJob = currentOperation.getOperationIndex().getJobId();
				comparisonStation = currentOperation.getOperationIndex().getStationId();
			}
		}
		return rank;
	}
	
	private int[][] applyTearDownBetas(int [][] matrix) {
		int[][] newC = null;
		if (this.betas != null) {
			Iterator<Beta> i = betas.values().iterator();
			while (i.hasNext()) {
				Beta beta = i.next();

				if (beta instanceof TearDownBeta) {
					 newC = ((TearDownTravelTime) beta).applyBeta(matrix);
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
	public ArrayList<CriticalPath> getCriticalPaths(){
		synch=false;
		calculateCMatrix();
		int [][] cmatrix = C;
		if(isNonDelayActive())
			cmatrix = CIntepretation;
		
		ArrayList<CriticalPath> routes = new ArrayList<CriticalPath>();
		ArrayList<IOperation> finalNodes = getLastOperation(cmatrix);
		
		for(int i=0; i < finalNodes.size();i++ ){
			//CriticalPath temp= new CriticalPath();
			//temp.addNodeBegin(finalNodes.get(i));
			//routes.addAll(getLongestRoute(temp, solution));
			
			CriticalPath temp1= new CriticalPath();
			temp1.addNodeBegin(finalNodes.get(i));
			routes.addAll(getCriticalRoute(cmatrix, temp1));
		}	
		return routes;
	}
	
	/**
	 * Returns the collection of operations such that their C value is the biggest one.
	 * @return lastOperations. A collection of IOperations such that its C value is the biggest one.
	 */
	public ArrayList<IOperation> getLastOperation(int[][]CMatrix){
		
		ArrayList<IOperation> operations = new ArrayList<IOperation>();
		
		int lastTime=0;
		for(int i=0; i< totalJobs;i++){
			
			for(int j=0; j<totalStations;j++){
				if(CMatrix[i][j]>lastTime){
					lastTime=CMatrix[i][j];
				}
			}
		}
		
		for(int i=0; i< totalJobs;i++){
			
			for(int j=0; j<totalStations;j++){
				if(CMatrix[i][j]==lastTime){
					IOperation temp= new Operation(new OperationIndexVO(i, j));
					temp.setFinalTime(lastTime);
					operations.add(temp);
				}
			}
		}
		return operations;
	}
		
	/**
	 * Complete the critical path in the parameter according to the initial nodes it contains
	 * @param criticalPath The collection of critical paths associated with the node
	 * @return
	 */
	private ArrayList<CriticalPath> getLongestRoute(CriticalPath route, ArrayList<IOperation> solution){
		ArrayList<CriticalPath> routes = new ArrayList<CriticalPath>();
		IOperation lastOperation= route.getRoute().get(0);
		ArrayList<IOperation> operationsStation = getOperationsBeforeByStation(lastOperation.getOperationIndex(), solution);
		ArrayList<IOperation> operationsJob = getOperationsBeforeByJob(lastOperation.getOperationIndex(), solution);
		if(!operationsJob.isEmpty()){
			IOperation operationBeforeByJob = operationsJob.get(operationsJob.size()-1);
			if(!operationsStation.isEmpty()){
				IOperation operationBeforeByStation = operationsStation.get(operationsStation.size()-1);
				if(operationBeforeByJob.getFinalTime()>operationBeforeByStation.getFinalTime()){
					route.addNodeBegin(operationBeforeByJob);
					operationsJob=null;
					operationsStation=null;
					return getLongestRoute(route, solution);
					
				}
				else if(operationBeforeByJob.getFinalTime()==operationBeforeByStation.getFinalTime()){
					CriticalPath clone = new CriticalPath();
					for(int j=0; j<route.getRoute().size();j++){
						IOperation node = route.getRoute().get(j);
						clone.getRoute().add(node);
					}
					route.addNodeBegin(operationBeforeByJob);
					clone.addNodeBegin(operationBeforeByStation);
					ArrayList<CriticalPath> temp1 = getLongestRoute(route, solution);
					
					ArrayList<CriticalPath> temp2 = getLongestRoute(clone, solution);
					
					temp1.addAll(temp2);
					operationsJob=null;
					operationsStation=null;
					return temp1;
				}
				else{
					route.addNodeBegin(operationBeforeByStation);
					operationsJob=null;
					operationsStation=null;
					return getLongestRoute(route, solution);
					
				}
			}
			else{
				route.addNodeBegin( operationBeforeByJob);
				operationsJob=null;
				operationsStation=null;
				return getLongestRoute(route, solution);
			}
		}
		else{
			if(!operationsStation.isEmpty()){
					IOperation operationBeforeByStation = operationsStation.get(operationsStation.size()-1);
					route.addNodeBegin(operationBeforeByStation);
					operationsJob=null;
					operationsStation=null;
					return getLongestRoute(route, solution);
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
	private ArrayList<IOperation> getOperationsBeforeByStation(OperationIndexVO operation, ArrayList<IOperation> solution){
		ArrayList<IOperation> operations = new ArrayList<IOperation>();
		for(IOperation op : solution){
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
	private ArrayList<IOperation> getOperationsBeforeByJob(OperationIndexVO operation, ArrayList<IOperation> solution){
		ArrayList<IOperation> operations = new ArrayList<IOperation>();
		for(IOperation op : solution){
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
	
	public void sortArray(ArrayList<IOperation> vector){
		Collections.sort(vector, new Comparator<IOperation>() {
			@Override
			public int compare(IOperation o1, IOperation o2) {
				int a = o1.getFinalTime();
				int b = o2.getFinalTime();
				return (a>b ? 1 : (a==b ? 0 : -1));

			}
		});
	}
	
	public ArrayList<CriticalPath> getCriticalRoute(int [][] matrizC, CriticalPath route){
		ArrayList<CriticalPath> routes = new ArrayList<CriticalPath>();
		IOperation lastOperation = route.getRoute().get(0);
		
		ArrayList<IOperation> operationsStation = getOperationsBeforeByStation(matrizC, lastOperation);
		ArrayList<IOperation> operationsJob = getOperationsBeforeByJob(matrizC, lastOperation);
		if(!operationsJob.isEmpty()){
			IOperation operationBeforeByJob = operationsJob.get(operationsJob.size()-1);
			if(!operationsStation.isEmpty()){
				IOperation operationBeforeByStation = operationsStation.get(operationsStation.size()-1);
				if(operationBeforeByJob.getFinalTime()>operationBeforeByStation.getFinalTime()){
					route.addNodeBegin(operationBeforeByJob);
					operationsJob=null;
					operationsStation=null;
					return getCriticalRoute(matrizC, route);
					
				}
				else if(operationBeforeByJob.getFinalTime()==operationBeforeByStation.getFinalTime()){
					CriticalPath clone = new CriticalPath();
					for(int j=0; j<route.getRoute().size();j++){
						IOperation node = route.getRoute().get(j);
						clone.getRoute().add(node);
					}
					route.addNodeBegin(operationBeforeByJob);
					clone.addNodeBegin(operationBeforeByStation);
					ArrayList<CriticalPath> temp1 = getCriticalRoute(matrizC, route);
					
					ArrayList<CriticalPath> temp2 = getCriticalRoute(matrizC, clone);
					
					temp1.addAll(temp2);
					operationsJob=null;
					operationsStation=null;
					return temp1;
				}
				else{
					route.addNodeBegin(operationBeforeByStation);
					operationsJob=null;
					operationsStation=null;
					return getCriticalRoute(matrizC, route);
					
				}
			}
			else{
				route.addNodeBegin( operationBeforeByJob);
				operationsJob=null;
				operationsStation=null;
				return getCriticalRoute(matrizC, route);
			}
		}
		else{
			if(!operationsStation.isEmpty()){
					IOperation operationBeforeByStation = operationsStation.get(operationsStation.size()-1);
					route.addNodeBegin(operationBeforeByStation);
					operationsJob=null;
					operationsStation=null;
					return getCriticalRoute(matrizC, route);
			}
			else{
				routes.add(route);
				operationsJob=null;
				operationsStation=null;
				return routes;	
			}
		}
		
	}
	
	public ArrayList<IOperation> getOperationsBeforeByJob(int [][] matrizC, IOperation operation){
	
		ArrayList<IOperation> operations = new ArrayList<IOperation>();
		int jobid = operation.getOperationIndex().getJobId();
		int finalTime = operation.getFinalTime();
		
		for(int i=0; i < this.totalStations; i++){
			int cValue= matrizC[jobid][i];
			if(cValue < finalTime){
				IOperation temp = new Operation(new OperationIndexVO(jobid, i));
				temp.setFinalTime(cValue);
				operations.add(temp);
			}
		}
		sortArray(operations);
		return operations;
	}
	
	public ArrayList<IOperation> getOperationsBeforeByStation(int [][] matrizC, IOperation operation){
		
		ArrayList<IOperation> operations = new ArrayList<IOperation>();
		int stationid = operation.getOperationIndex().getStationId();
		int finalTime = operation.getFinalTime();
		
		for(int i=0; i < this.totalJobs; i++){
			int cValue= matrizC[i][stationid];
			if(cValue < finalTime){
				IOperation temp = new Operation(new OperationIndexVO(i, stationid));
				temp.setFinalTime(cValue);
				operations.add(temp);
			}
		}
		sortArray(operations);
		return operations;
	}
	
	// -------------------------------------------------
	// Getters and setters
	// -------------------------------------------------

	public ArrayList<IOperation> getVector(){
		return vectorDecodSimple;
	}
	
	@Override
	public int getTotalMachines() {
		return this.totalMachines;
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
	public void setTotalMachines(int totalMachines) {
		this.totalMachines = totalMachines;
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

	public ArrayList<IOperation> getVectorDecodNonDelay() {
		return vectorDecodNonDelay;
	}

	public void setVectorDecodNonDelay(ArrayList<IOperation> vectorDecodNonDelay) {
		this.vectorDecodNonDelay = vectorDecodNonDelay;
	}

	public ArrayList<IOperation> getVectorDecodSimple() {
		return vectorDecodSimple;
	}

	public void setVectorDecodSimple(ArrayList<IOperation> vectorDecodSimple) {
		this.vectorDecodSimple = vectorDecodSimple;
	}

	public boolean isNonDelayActive() {
		return nonDelayActive;
	}

	public void setNonDelayActive(boolean nonDelayActive) {
		this.nonDelayActive = nonDelayActive;
	}
	

	public int[][] getCIntepretation() {
		return CIntepretation;
	}

	public int[][] getCActiveSchedule() {
		return CActiveSchedule;
	}

	public void setCActiveSchedule(int[][] cActiveSchedule) {
		CActiveSchedule = cActiveSchedule;
	}

	public void setCIntepretation(int[][] cIntepretation) {
		CIntepretation = cIntepretation;
	}
		
}