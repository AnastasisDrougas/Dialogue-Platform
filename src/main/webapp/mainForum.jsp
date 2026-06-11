<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="hua.dit.web.project.projectDB" %>

<!DOCTYPE html>	
<html>
<head>
	<meta charset="UTF-8">
	<title>Choose Topic And Send Message</title>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/pagestyle.css">

</head>
<body>
	<%
		//add safety guard
		Integer userId = (Integer) session.getAttribute("userId");
		String username = (String) session.getAttribute("username");
		
		if (userId == null){
			response.sendRedirect(request.getContextPath() + "/html/loginPage.html");
			return;
		}
	
	%>
	
	<%
		//handle status and errors after the user presses submit message.
		String status = request.getParameter("status");
		String error = request.getParameter("error");
		String msgCount = request.getParameter("msgCount");	
		
		
		if ("success".equals(status)){
	%>
		<div class="area">
	            <h2>Your message has been posted successfully!</h2>
	            <br>
	            <h2>
	                There are now <strong><%= msgCount %></strong> total messages written about this specific topic.
	            </h2>
	        </div>
	<%
	    } else if ("missingfields".equals(error)) {
	%>
		<h2>
	            Please fill out all required fields before submitting.
	    </h2>
	<%
    	} else if ("dbfail".equals(error)) {
	%>
        <h2>
           Database error: Could not process your request!
        </h2>
	<%
	    }
	%>
	<h2>Project of Anastasis Drougas</h2>
	<br>
	<h1>What would you like to talk about today?</h1>
	<div>
		<h2>Send a Message</h2>
		<br>
		<h2>Logged in as: <strong><%= username %></strong></h2>
		
		<form id="messageForm" action="<%= request.getContextPath() %>/SendMessageServlet" method="POST">
            <input type="hidden" name="userId" value="<%= userId %>">

            <div>
                <label for="topic">Select a Topic:</label>
                <select name="topicId" id="topic" required>
                    <option value="">-- Choose a Topic --</option>
                    
                    <%
                        
                        List<Map<String, Object>> topics = projectDB.getTopics();
                        
                        if (topics != null) {
                            for (Map<String, Object> topic : topics) {
                                int topicId = (Integer) topic.get("id");
                                String topicName = (String) topic.get("name");
                    %>
                                <option value="<%= topicId %>"><%= topicName %></option>
                    <%
                            }
                        }
                    %>
                    
                </select>
            </div>
			<br>
			<div>
                <label for="messageText">Your Message:</label>
                <textarea name="messageText" id="messageText" required></textarea>
            </div>

            <input type="submit" value="Submit Message">
        </form>
        <a href="<%= request.getContextPath() %>/mainMenu.jsp" class="btnlink">
            Back to Main Menu
        </a>
    </div>
    <script src="<%= request.getContextPath() %>/js/validate_message.js"></script>
</body>
</html>