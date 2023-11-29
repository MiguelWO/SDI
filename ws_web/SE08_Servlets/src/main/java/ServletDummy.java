
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class ServletDummy
 */
@WebServlet("/ServletDummy")
public class ServletDummy extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletDummy() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
//	}
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String op1 = request.getParameter("op1");
		    if (op1 == null) {
		    	op1 = "1.0";
		    	}
		    String op2 = request.getParameter("op2");
		    if (op2 == null) {
		    	op2 = "1.0";
		    	}
		    float n1 = Float.parseFloat(op1);
		    float n2 = Float.parseFloat(op2);
		    response.setContentType("text/html");
		    PrintWriter out = new PrintWriter (response.getWriter());
		    out.println("<html>");
		    out.println("<head><title>Multiplicacion</title></head>");
		    out.println("<body>");
		    out.println("<h1>" + op1 +" * "+op2+" = "+ n1*n2 +"</h1>");
		    out.println("</body></html>");
		    out.close();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		doGet(request, response);
//	}
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mensaje = "";
		ServletInputStream in = request.getInputStream();
		ServletOutputStream out = response.getOutputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        PrintWriter writer = new PrintWriter(out);
        
        while((mensaje = reader.readLine()) != null) {
        	writer.println(mensaje+" Ep�logo.");
        	writer.flush();
        }
	}

}
