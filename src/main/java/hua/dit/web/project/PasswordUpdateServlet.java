package hua.dit.web.project;

import java.io.PrintWriter;
import java.io.IOException;
import java.security.MessageDigest;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PasswordUpdateServlet
 */
@WebServlet("/PasswordUpdateServlet")
public class PasswordUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public PasswordUpdateServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
	        throws ServletException, IOException {
	    
	    response.setContentType("text/html;charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    
	    // 1. Stick to getParameter because the HTML is sending standard form fields
	    String username = request.getParameter("username");
	    String oldPass = request.getParameter("oldPassword");
	    String newPass = request.getParameter("newPassword");
	    
	    // 2. FIX: Use your 'Util' class for hashing instead of the old internal method
	    String oldPassHash = Util.getHash256(oldPass);
	    String newPassHash = Util.getHash256(newPass);
	    
	    // 3. Match case exactly with "projectDB" class name
	    boolean isSuccess = projectDB.updatePassword(username, oldPassHash, newPassHash);
	    
	    // 4. Output the HTML response
	    out.println("<html><body>");
	    out.println("<h1>Project of Anastasis Drougas</h1>");
	    if (isSuccess) {
	        out.println("<p style='color:green; font-weight:bold;'>Success! Your password has been securely updated.</p>");
	    } else {
	        out.println("<p style='color:red; font-weight:bold;'>Error: The username or existing password was incorrect.</p>");
	    }
	    out.println("<br><a href='html/passwordUpdate.html'>Go Back</a>");
	    out.println("</body></html>");
	}
}