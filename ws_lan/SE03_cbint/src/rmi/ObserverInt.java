package rmi;


public interface ObserverInt extends java.rmi.Remote {

  public String eventCallback(String arg) throws java.rmi.RemoteException;


}