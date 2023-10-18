package rmi;


public interface TriggerInt extends java.rmi.Remote {

  public String addListener(ObserverInt observer) throws java.rmi.RemoteException;
  public boolean removeListener(String observerId) throws java.rmi.RemoteException;

}