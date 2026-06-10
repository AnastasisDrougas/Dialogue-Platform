package hua.dit.web.project;

import jakarta.servlet.ServletException;
import java.io.IOException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class SendMessageServlet
 */
@WebServlet("/SendMessageServlet")
public class SendMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public SendMessageServlet() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		//session Check.
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/html/loginPage.html");
            return;
        }
        
        //Get the parameters from mainForum jsp and the user 	session.
        String topicIdParam = request.getParameter("topicId");
        String messageText  = request.getParameter("messageText");
        int userId = (Integer) session.getAttribute("userId");
        
        //Validate parameters.
        if (topicIdParam == null || topicIdParam.trim().isEmpty() || 
            messageText == null || messageText.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/mainForum.jsp?error=missingfields");
            return;
        }
        
        try {
            int topicId = Integer.parseInt(topicIdParam);

            //insert the message to the db, and count rows for this specific topic.
            int totalMessages = projectDB.insertMessageAndCount(topicId, userId, messageText);

            
            if (totalMessages != -1) {
            	//Success
                response.sendRedirect(request.getContextPath() + "/mainForum.jsp?status=success&msgCount=" + totalMessages);
                
            } else {
            	//Failed
                response.sendRedirect(request.getContextPath() + "/mainForum.jsp?error=fail");
            }

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/mainForum.jsp?error=invalidtopic");
        }
	}

}
