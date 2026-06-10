<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Main Menu</title>
<link href="<%= request.getContextPath() %>/css/pagestyle.css" rel="stylesheet">
</head>
<body>
	<div class="area">
        
        <h2>Successful Login!<h2>
        
    </div>  
    
    <div class="area">
    
        <h1>Welcome back, <strong><%= session.getAttribute("username") %></strong>.</h1>
        <h2>What would you like to do now?</h2>
        
  	</div>
  	
        <br>
        
    <div class="area"> 
     
        <a href="<%= request.getContextPath() %>/mainForum.jsp" class="btnlink">
            Enter Dialogue Platform.
        </a>
        
        <br>
        <br>
        
         <a href="<%= request.getContextPath() %>/html/passwordUpdate.html" class="btnlink">
            Update Your Password.
        </a>
        
    </div>   
    
        
    
</body>
</html>