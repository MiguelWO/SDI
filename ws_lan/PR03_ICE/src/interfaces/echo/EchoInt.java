package interfaces.echo;

import java.rmi.Remote;
import com.zeroc.Ice.Current;
import sdi.echoIce.Echo;
import server.EchoObject;

//public interface EchoInt extends Remote{
//	String echo(String a) throws java.rmi.RemoteException;
//}

public class EchoInt  implements Echo{

	EchoObject servObject = new EchoObject();
	
	@Override
	public String serviceEcho(String input, Current current) {
		// TODO Auto-generated method stub
		return servObject.echo(input);
	}

}


