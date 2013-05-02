package afs.model;
/*
 * AFS (Algorithms for Scheduling) - Copyright (C) 2005-2008 Gonzalo Mejía
 * This file is part of AFS
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 *
 * You should have received a copy of the GNU Library General Public
 * License along with this library; if not, write to the Free
 * Software Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */
/**
 * <p>Title: Class Machine</p>
 *
 * <p>Description: Machine defines the methods and atributes of a machine.<br>
 * The class atributes are:<br>
 * String machineId: Machine identifier <br>
 * int availableDate: availability date of the machine.<br>
 * char machineStatus: machine initial status.<br>
 * int machineNumber: machine number nested in a workcenter.<br>
 * int workcenterNumber: workcenter number in which the machine is nested. <br>
 * The static atribute is: numberOfMachines. </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Pylo.Uniandes.edu.co</p>
 * @author Gonzalo Mejia
 * @version 2.0
 */
public class Machine {

    /**
     * machineId defines the machine identifier with a string of characters
     */
    private String machineID;
    
    /**
     * availableDate defines the availability date of the machine
     */
    private int availableDate;

    /**
     * machineStatus defines the initial status of the machine. Must be a capital letter (A, B, C, etc)<br>
     *See Lekin documentation for definition of status
     */
    private char machineStatus;

    /**
     * machineNumber is the machine number within in a workstation
     */
    private int machineNumber;


    /**
     * workcenterNumber corresponds to the workcenter number where the Machine
     * belongs to*/
    private int workcenterNumber;

    /**
    * MAXMACHINES is the maximum number of allowed machines 
    */
    private static final int MAXMACHINES = 100;
    
    
    /**
     * numberOfMachines is the total number of machines in the machine park
     */
    private static int totalNumberOfMachines;

 //Set methods
  /**
   * setMachineId sets the Identifier of the machine
   *
   * @param id String
   */
  public void setMachineID(String id){
    machineID = id;
  }

  /**
   * setAvailableDate sets the availability date of the machine
   *
   * @param release int
   */
  public void setAvailableDate(int release){
    availableDate = release;
  }

  /**
   * setStatus sets initial status (type) of the machine
   *
   * @param status char must be a character A to Z
   */
  public void setStatus(char status){
    machineStatus = status;
  }

  /**
   * setMachineNumber sets the number of a machine
   *
   * @param mchNumber must be a valid integer
   */
  public void setMachineNumber(int mchNumber){
    machineNumber = mchNumber;
  }

  /**
   * setWorkcenterNumber sets the number of a workcenter
   *
   * @param wkcNumber must be a valid integer
   */
  public void setWorkcenterNumber(int wkcNumber){
    workcenterNumber = wkcNumber;
  }

   // Get methods

  /**
   * getNumberOfMachines returns the total number of machines in the system
   *
   * @return int
   */
  public static int getTotalNumberOfMachines(){
    return totalNumberOfMachines;
  }


  /**
   * getMachineId returns the machine identifier
   *
   * @return String
   */
  public String getMachineID(){
    return machineID;
  }

  /**
   * getAvailableDate returns the availability date
   *
   * @return int
   */
  public int getAvailableDate(){
    return availableDate;
  }

  /**
   * getStatus returns the initial status of the machine A to Z
   *
   * @return char
   */
  public char getStatus(){
    return machineStatus;
  }

  /**
   * getMachineNumber returns the machine number relative to a workcenter
   *
   * @return int
   */
  public int getMachineNumber(){
      return machineNumber;
  }

  /**
   * getMAXMACHINES returns the maximum allowable number of machines
   *
   * @return int
   */
  public static int getMAXMACHINES(){
      return MAXMACHINES;
  }

  /**
   * Machine default constructor
   */
  public Machine () {

    totalNumberOfMachines++;

  }


}
