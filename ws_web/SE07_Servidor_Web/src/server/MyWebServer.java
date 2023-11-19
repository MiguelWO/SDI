/*
 *  @author Jose Simo. 
 *  (c) ai2-UPV Creative Commons.
 *  Rev: 2022
 */
package server;

import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.sun.net.httpserver.*;

public class MyWebServer
{
   public static void main (String [] args) throws IOException
   {
      HttpServer server = HttpServer.create (new InetSocketAddress(8008), 0);
      server.setExecutor(new ThreadPoolExecutor(5,10,Long.MAX_VALUE,TimeUnit.NANOSECONDS,new SynchronousQueue<Runnable>()));
      //server.setExecutor(new MultithreadExecutor());
      //server.setExecutor(new MonothreadExecutor());
      server.createContext ("/data", new HandlerDate ());
      System.out.println("Web server started in contetext /date");
      server.start ();
   } 
}

class HandlerDate implements HttpHandler
{
   @Override
   public void handle (HttpExchange xchg) throws IOException
   {
      Headers headers = xchg.getRequestHeaders ();
      Set<Map.Entry<String, List<String>>> entries = headers.entrySet ();
      
      StringBuffer response = new StringBuffer ();
      DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
      Date date = new Date();
      response.append("<html>");
      response.append("<body>");
      response.append("<h1>");
      response.append("Servidor web minimo");
      response.append("</h1>");
      response.append("Respondiendo a una llamada: " + xchg.getRequestMethod());
      response.append("<br>");
      response.append(" realizada desde: " + xchg.getRemoteAddress());
      response.append("<br>");
      response.append(" contra el contexto: " + xchg.getHttpContext().getPath());
      response.append("<br>");
      response.append("Con los parametros: " + xchg.getRequestURI().getQuery());
      response.append("<br>");
      response.append("La fecha de este servidor es: " + dateFormat.format(date));
      response.append("<h2>");
      response.append("Los datos de la cabecera de la llamada son:");
      response.append("</h2>");
      for (Map.Entry<String, List<String>> entry: entries) {
            response.append(entry.toString ());
            response.append("<br>");
      }
      response.append("</body>");
      response.append("</html>");
      xchg.sendResponseHeaders (201, response.toString().length());
      OutputStream os = xchg.getResponseBody();
      os.write(response.toString().getBytes());
      os.close ();
      
   }
 
}

class MultithreadExecutor implements Executor {

	@Override
	public void execute(Runnable command) {
		new Thread(command).start();
		
	}
	
}

class MonothreadExecutor implements Executor {

	@Override
	public void execute(Runnable command) {
		command.run();
		
	}
	
}



