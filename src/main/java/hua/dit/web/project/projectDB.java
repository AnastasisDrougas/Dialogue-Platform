package hua.dit.web.project;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class projectDB {
	
	// FOR MARIA DB
	private static final String USER = "root";
	private static final String PASS = "";
	private static final String URL= "jdbc:mariadb://localhost:3306/project2025-2026";
	static {
        try {
        	Class.forName("org.mariadb.jdbc.Driver");
        } catch (Throwable t) {
			throw new RuntimeException("Cannot find Maria DB JDBC driver", t);
		}
	}
	
	
	public static synchronized boolean updatePassword(String username, String oldPassHash, String newPassHash) {
	    String sql = "UPDATE users SET upasshash = ? WHERE id IN (SELECT id FROM users WHERE uname = ? and upasshash = ?)";
	    
	    try {
	        if (username == null || oldPassHash == null || newPassHash == null) return false;
	        
	        final Connection conn = DriverManager.getConnection(URL, USER, PASS);
	        final PreparedStatement pst = conn.prepareStatement(sql);
	        
	        
	        pst.setString(1, newPassHash);
	        pst.setString(2, username);
	        pst.setString(3, oldPassHash);
	        
	        int rowsAffected = pst.executeUpdate();
	        
	        pst.close();
	        conn.close();
	        
	        //If rowsAffected > 0, the change was successfully written into the database.
	        return rowsAffected > 0;
	        
	    } catch (Throwable t) {
	        throw new RuntimeException("Error during updatePassword calculation", t);
	    }
	}
	    
	    
	
	public static synchronized int authenticateUser(String username, String passHash) {
	    //Look up the ID matching both the unique username and password hash.
	    String sql = "SELECT id FROM users WHERE uname = ? AND upasshash = ?";
	    
	    Connection conn = null;
	    PreparedStatement pst = null;
	    ResultSet rs = null;
	    
	    try {
	        if (username == null || passHash == null) return -1;
	        
	        conn = DriverManager.getConnection(URL, USER, PASS);
	        pst = conn.prepareStatement(sql);
	        
	        pst.setString(1, username);
	        pst.setString(2, passHash);
	        
	        rs = pst.executeQuery();
	        
	        //If the database returns a matching record, return primary ID key.
	        if (rs.next()) {
	            return rs.getInt("id"); 
	        }
	        
	        return -1; 
	        
	    } catch (Throwable t) {
	        t.printStackTrace();
	        return -1;
	    } finally {
	        try { if (rs != null) rs.close(); } catch (Exception e) {}
	        try { if (pst != null) pst.close(); } catch (Exception e) {}
	        try { if (conn != null) conn.close(); } catch (Exception e) {}
	    }
	}
	
	
	
	public static List<Map<String, Object>> getTopics(){
		 List<Map<String, Object>> topicsList = new ArrayList<>();
		 String sql = "SELECT * FROM topics ORDER BY id";
		 
		 Connection conn = null;
	     PreparedStatement pst = null;
	     ResultSet rs = null;
	     
	     try {
	    	 conn = DriverManager.getConnection(URL, USER, PASS);
	         pst = conn.prepareStatement(sql);
	         rs = pst.executeQuery();
	         
	         while (rs.next()) {
	        	 Map<String, Object> topic = new HashMap<>();
	        	 topic.put("id", rs.getInt("ID"));
	        	 topic.put("name", rs.getString("NAME"));
	        	 topic.put("description", rs.getString("DESCRIPTION"));
	        	 
	        	 topicsList.add(topic);
	        }
	        
	    } catch (Exception e) {
	         e.printStackTrace();
	         
	     } finally {
	         try { if (rs != null) rs.close(); } catch (Exception e) {}
	         try { if (pst != null) pst.close(); } catch (Exception e) {}
	         try { if (conn != null) conn.close(); } catch (Exception e) {}
	     }
	     
		 return topicsList;
	}
	
	
	public static synchronized int insertMessageAndCount(int topicID, int userID, String messageText) {
		
		String sqlInsert = "INSERT INTO messages (TOPIC_ID, USER_ID, MSG) VALUES (?, ?, ?)";
		String sqlCount = "SELECT count(*) FROM messages WHERE topic_id = ?";
		
		Connection conn = null;
	    PreparedStatement pstInsert = null;
	    PreparedStatement pstCount  = null;
	    ResultSet rs = null;
	    
	    
	    try {
	        //Check if the message is not null
	    	if (messageText == null || messageText.trim().isEmpty()) {
	            return -1;
	        }
	    	
	    	conn = DriverManager.getConnection(URL, USER, PASS);
	    	
	    	//Insert the message to the db
	        pstInsert = conn.prepareStatement(sqlInsert);
	        
	        pstInsert.setInt(1, topicID);
	        pstInsert.setInt(2, userID);
	        pstInsert.setString(3, messageText);
	        
	        int rowsAffected = pstInsert.executeUpdate();
	        
	        //Execute count.
	        if (rowsAffected > 0) {
	        	pstCount = conn.prepareStatement(sqlCount);
	        	pstCount.setInt(1, topicID);
	        	
	        	rs = pstCount.executeQuery();
	        	if (rs.next()) {
	                //return the total count from the COUNT(*) column 
	                return rs.getInt(1); 
	            }
	        }
	        return -1; //failed to count/	
	       
	    }catch(Throwable t) {
	    	t.printStackTrace();
	        return -1;
	    }finally {
	    	try { if (rs != null) rs.close(); } catch (Exception e) {}
	        try { if (pstCount != null) pstCount.close(); } catch (Exception e) {}
	        try { if (pstInsert != null) pstInsert.close(); } catch (Exception e) {}
	        try { if (conn != null) conn.close(); } catch (Exception e) {}
	    }
	}
	
	
    public static boolean addTopic(Topic topic) {

    	String sql = "INSERT INTO topics VALUES (null, ?, ?)";
    	Connection conn = null;
    	PreparedStatement pst = null;
		try {
			//execute sql.
			conn = DriverManager.getConnection(URL, USER, PASS);
			pst = conn.prepareStatement(sql);
			pst.setString(1, topic.getTitle());
			pst.setString(2, topic.getDescription());
			
			
		
			return pst.executeUpdate() > 0;
		
		} catch(Exception e) {
		e.printStackTrace();
		return false;
		}finally {
	        try { if (pst != null) pst.close(); } catch (Exception e) {}
	        try { if (conn != null) conn.close(); } catch (Exception e) {}
		}
	}

}