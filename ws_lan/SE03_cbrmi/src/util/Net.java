/*
 *  Net.java
 *
 *  @author Jose Simo. (c) ai2-DISCA-UPV Creative Commons.
 *  
 *  Rev: 2017
 */
package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Properties;


/**
 * Network utilities.
 * The main purpose of this singleton is to provide a controlled way to
 * select network interfaces and get the local IP.
 * This singleton also includes a "read" routine that blocks until read a number 
 * of bytes.
 * See the "main" entry to get help.
 * 
 * @author Jose Simo (c) ai2-UPV 2016
 */
public class Net {

	static String interfaceName;
	static String interfaceAddress;
	static boolean interfaceSelected;
	
	/**
	 * Simple method to choose the network interface (operaing IP addr) 
	 * by user interaction. This method does not modify the internal state 
	 * of the singleton
	 * @return Chosen IP address as string
	 */
	public static String chooseOperatingIPAddr() {
		
		ArrayList<String> retval = new ArrayList<String>();
	    Enumeration<NetworkInterface> n;
		try {
			n = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e1) {
			return "localhost";
		}
	    for (; n.hasMoreElements();)
	    {
	        NetworkInterface e = n.nextElement();
	        Enumeration<InetAddress> a = e.getInetAddresses();
	        for (; a.hasMoreElements();)
	        {	
	            InetAddress addr = a.nextElement();
	            if (addr instanceof Inet6Address) {
				    continue;
				} else {
					if (addr.isLoopbackAddress()) {
						continue;
					} else {
						retval.add(addr.getHostAddress());
					}
				}
	        }
	    }
        if (retval.size() > 1) {
        	System.out.println("Choose a operating IP address");
        	int i = 0;
        	for (String addr : retval) {
        		System.out.println("  (" + i + ") " + addr);
        		i++;
        	}
        	BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        	int option = -1;
        	while ((option < 0) || (option > retval.size()-1)) {
				try {
					option = Integer.parseInt(stdin.readLine());
				} catch (Exception e) {	
					option = -1;
				} 
        	}
        	return retval.get(option);
        	//System.out.println(retval.get(option));	
        } else {
        	if (retval.size() == 1) {
        		return retval.get(0);
        		//System.out.println(retval.get(0));
        	} else {
        		return "localhost";
        		//System.out.println("localhost");
        	}
        }
	} 
	
	//////////////////////////////////////////////////////////////////////////////////
	
	/** 
	 * Check if there are more than one network interfaces. If so, presents a menu
	 * that allow the uses select one. If there is only one network interface, 
	 * returns it without user interaction.
	 *
	 */
	public static String menuUserSelectNetworkInterface() {
		
		String netInterface = "";
		LinkedHashMap<String,String> interfaces = null;
		try {
			interfaces = util.Net.getInterfaces();	
		} catch (SocketException e) {
			e.printStackTrace();
			System.exit(0);
		}
    	// check if there are more than one interface and select one.
		if ((interfaces.size() > 1)) {
			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
			int selection = -1;
			do {
				System.out.println("\nSelect the desired network interface from this list:");
				int i = 0;
				for (String intName: interfaces.keySet()) {
					System.out.println("("+i+") " + "Interface : " + intName + " (IP:" + interfaces.get(intName) + ") ");
					i++;
				}
				System.out.print("> ");
				try {
					selection = Integer.parseInt(stdin.readLine());
				} catch (Exception e) {
					selection = -1;
				}
			} while ((selection < 0) || (selection >= interfaces.size()));
			
			String intName = (String) interfaces.keySet().toArray()[selection];
			String intIP = interfaces.get(intName);
			System.out.println("Your selection is InterfaceName=" + intName + " (IP:" + intIP + ")");
			//Select the interface into the MultiPeer configuration
			netInterface = intName;
			
		} else {
			if (interfaces.size() == 1) {
				netInterface = (String) interfaces.keySet().toArray()[0];
			} else {
				System.err.println("Net::menuUserSelectNetworkInterface(): No Internet interfaces detected. Check your networking hardware.");
			}
		}
		return netInterface;
	}
	
	/**
	 * Returns a string with the IP address of the selected interface.
	 * If there is not interface selected, selects the favorite one.
	 */
	public static String getLocalHostIP() {
		
		String addr = null;
		if (interfaceSelected == false) {
			addr = getFavouriteLocalIP();
		} else {
			addr = interfaceAddress;
		}
		return addr;
	}
	
	/**
	 * Iterate over available network interfaces and selects one (the first in the list)
	 * If no found interfaces selects the loopback one.
	 * Returns a string with the IP address of the selected interface (dot-separated notation).
	 */
	public static String getFavouriteLocalIP() {	

		LinkedHashMap<String, String> interfaces;
		try {
			interfaces = getInterfaces();
			if (interfaces.size() > 0) {
				interfaceName = (String) interfaces.keySet().toArray()[0];
				interfaceAddress = interfaces.get(interfaceName);
				interfaceSelected = true;
			}
		} catch (SocketException e) {
			System.err.println("Net::getFavouriteLocalIP(): No Internet interfaces detected. Check your networking hardware.");
		}
		return interfaceAddress;
	}
	
	/**
	 * Returns a map (InterfaceName vs IPAddress) of available network intefaces.
	 * This function is intended to allow the user to select the network interface
	 * to link the MultiPeer object (via MultiPeerConf object).
	 */
	public static LinkedHashMap<String,String> getInterfaces() throws SocketException{
		
		//Set system properties to avoid choosing a default IPv6 interface.
		////Alternatively you can launch the JVM with the option:
		//// -Djava.net.preferIPv4Stack=true
		Properties props = System.getProperties();
		props.setProperty("java.net.preferIPv4Stack", "true");
		//
		//Variables to store the loopback properties.
		String loopback_addr = "";
		String loopback_name = "";
		//Declare the map to return
		LinkedHashMap<String,String> retval = new LinkedHashMap<String,String>();
		//Iterate interfaces
		Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
		while (interfaces.hasMoreElements()) {
		  NetworkInterface interface_ = interfaces.nextElement();
		  // we shouldn't care about loopback addresses
		  if (interface_.isLoopback()) { 
			  loopback_name = interface_.getDisplayName();
			  loopback_name = (interface_.getInetAddresses().hasMoreElements()) ? 
					  		  interface_.getInetAddresses().nextElement().getHostAddress() : ""; 
			  continue;
		  }
		  //Only consider Up interfaces
		  if (!interface_.isUp()) { continue; }
		  //Search interface addresses
		  Enumeration<InetAddress> addresses = interface_.getInetAddresses();
		  while (addresses.hasMoreElements()) {
			  	InetAddress address = addresses.nextElement();
			  	//Do not consider IPv6 addresses
				if (address instanceof Inet6Address) {
				    continue;
				} else {
					String intName = interface_.getDisplayName();
					String intIP = address.getHostAddress();
					//System.out.println("Interface: " + intName +" Addr: " + intIP);
					//Insert at the beginning of the map in order to be the same ordering than in POSIX.
					LinkedHashMap<String,String> auxmap = new LinkedHashMap<String,String>();
					auxmap.putAll(retval);
					retval.clear();
					retval.put(intName, intIP);
					retval.putAll(auxmap);
				}
		  }
		}
		if (retval.size() == 0) {
			retval.put(loopback_name, loopback_addr);
		}
		return retval;
	}
	
	/**
	 * Returns a Long representation of the IP Address
	 * 
	 */
	public static long ipAddrAsLong(InetAddress addr) {
		//Compose the values in the "network format" order:
		//(right value in the string is the most significant byte.)
		byte[] addrBuff = addr.getAddress();
		byte[] longBuff = new byte[8];
		longBuff[0] = addrBuff[0];
		longBuff[1] = addrBuff[1];
		longBuff[2] = addrBuff[2];
		longBuff[3] = addrBuff[3];
		longBuff[4] = 0; longBuff[5] = 0; longBuff[6] = 0; longBuff[7] = 0;	
		long retval = util.Bin.qWordToLong(longBuff);
		return retval;
	}
	
	/**
	 * 
	 */
	public static boolean ipValidIPv4String(String addrStr) {
	    if (addrStr.isEmpty()) {
	        return false;
	    }
	    try {
	        Object res = InetAddress.getByName(addrStr);
	        return res instanceof java.net.Inet4Address;
	    } catch (Exception ex) {
	        return false;
	    }
	}

	/**
	 * Instrumented read function. Propagate exception "IOException"
	 */
	public static int read(InputStream is, byte[] buff, int nbytes) throws IOException  {
		int nRead = 0;
		while (nRead < nbytes) {
			int count = is.read(buff, nRead, nbytes-nRead );
			if (count <= 0) {
				//On error or end-of-file (connection closed) returns with exception.
				throw new IOException();
			}
			nRead += count;
		}
		return nRead;
	}
	
	/**
	 * Example of how to use these routines.
	 * 
	 */
	 public static void main(String[] args) {
		 
		 String netInterface;
		 netInterface = menuUserSelectNetworkInterface();
		 System.out.println("The selected interface is: " + netInterface);
		 //
		 System.out.println("The local host IP is: " + getLocalHostIP());
		 //
		 System.out.println("The favourite local host IP is: " +getFavouriteLocalIP());
		 //
		 try {
			LinkedHashMap<String,String> interfacesMap = getInterfaces();
			for (String name: interfacesMap.keySet()) {
				System.out.println("Detected interface: " + name + "("+interfacesMap.get(name) + ")");
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} 
	 }
	
}
