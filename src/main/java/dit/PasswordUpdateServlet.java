package dit;

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
	    
	    //Gather parameters from HTML fields on update password.
	    String username = request.getParameter("username");
	    String oldPass = request.getParameter("oldPassword");
	    String newPass = request.getParameter("newPassword");
	    
	    //Hash passwords for user info safety.
	    String oldPassHash = hashPassword(oldPass);
	    String newPassHash = hashPassword(newPass);
	    
	    // 3. Match case exactly with "projectDB" class name
	    boolean isSuccess = projectDB.updatePassword(username, oldPassHash, newPassHash);
	    
	    // 4. Print your required project header and feedback message directly to the screen
	    out.println("<html><body>");
	    out.println("<h1>Project of Anastasis Drougas</h1>"); // Mandatory Name Header
	    if (isSuccess) {
	        out.println("<p style='color:green; font-weight:bold;'>Success! Your password has been securely updated.</p>");
	    } else {
	        out.println("<p style='color:red; font-weight:bold;'>Error: The username or existing password was incorrect.</p>");
	    }
	    out.println("<br><a href='html/passwordUpdate.html'>Go Back</a>");
	    out.println("</body></html>");
	}

	
	private String hashPassword(String password) {
		try {
			if (password == null) return null;
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(password.getBytes("UTF-8"));
			StringBuilder hexString = new StringBuilder();
			for (byte b : hash) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (Exception e) {
			throw new RuntimeException("Hashing failed", e);
		}
	}
}