package hua.dit.web.project;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class TopicsServlet
 */
@WebServlet("/TopicsServlet")
public class TopicsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public TopicsServlet() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
	
	    Topic topic = ServletUtil.getRequestData(Topic.class,request);
	    
	    boolean success = projectDB.addTopic(topic);

	    ResponseData reply = new ResponseData(success,success? "Topic stored successfully": "Failed to store topic");
	    response.setContentType("application/json");
	    ServletUtil.sendResponseData(reply,response);
	}

}
