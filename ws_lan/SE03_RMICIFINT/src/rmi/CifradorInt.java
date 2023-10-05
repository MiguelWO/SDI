/*
 *  CifradorInt.java
 *
 *  @author Jose Simo. (c) ai2-DISCA-UPV Creative Commons.
 *  
 *  Rev: 2017
 */
package rmi;


public interface CifradorInt extends java.rmi.Remote {

  public String cifra(String input, String clave) throws java.rmi.RemoteException;
  public String descifra(String input, String clave) throws java.rmi.RemoteException;

}