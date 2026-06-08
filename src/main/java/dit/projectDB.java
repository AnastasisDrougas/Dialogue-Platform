package dit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
            
            // If rowsAffected > 0, the change was successfully written into the database
            return rowsAffected > 0;
            
        } catch (Throwable t) {
            throw new RuntimeException("Error during updatePassword calculation", t);
        }
    }
		
}
