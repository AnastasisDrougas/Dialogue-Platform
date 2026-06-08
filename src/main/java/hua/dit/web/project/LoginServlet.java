package hua.dit.web.project;

import java.io.PrintWriter;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    public LoginServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/login.html");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        //Get parameters from HTML fields.
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        if (username == null || password == null || username.trim().isEmpty() || password.isEmpty()) {
            showErrorPage(out, request, "All fields are mandatory!");
            return;
        }
        
        //Hash password for user info privacy.
        String passHash = Util.getHash256(password);
        int userId = projectDB.authenticateUser(username, passHash);
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head><meta charset='UTF-8'><title>Login Result</title></head>");
        out.println("<body>");
        out.println("<h1>Project of Anastasis Drougas</h1>"); // Mandatory Name Header
        
        if (userId != -1) {
            HttpSession session = request.getSession(true); 
            session.setAttribute("userId", userId);
            session.setAttribute("username", username);
            
            out.println("<p style='color:green; font-weight:bold;'>Success! Welcome back, " + username + ".</p>");
            out.println("<br><a href='" + request.getContextPath() + "/main_forum.jsp'>Enter Dialogue Platform</a>");
        } else {
            out.println("<p style='color:red; font-weight:bold;'>Error: Invalid username or password.</p>");
            out.println("<br><a href='" + request.getContextPath() + "/login.html'>Try Again</a>");
        }
        
        out.println("</body></html>");
    }

    private void showErrorPage(PrintWriter out, HttpServletRequest request, String msg) {
        out.println("<!DOCTYPE html><html><body>");
        out.println("<h1>Project of Anastasis Drougas</h1>");
        out.println("<p style='color:red; font-weight:bold;'>Error: " + msg + "</p>");
        out.println("<br><a href='" + request.getContextPath() + "/login.html'>Go Back</a>");
        out.println("</body></html>");
    }

}