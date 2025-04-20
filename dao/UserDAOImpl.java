package dao;

import model.User;
import exceptions.*;
import java.sql.*;
import util.DBUtil;

public class UserDAOImpl implements UserDAO {
    
    public void addUser(User user) throws UserAlreadyExistsException {
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword()); 
            stmt.setString(3, user.getRole());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                throw new UserAlreadyExistsException(user.getUsername());
            }
            throw new RuntimeException("Database error", e);
        }
    }

    public User getUserByUsername(String username) throws UserNotFoundException {
        String sql = "SELECT * FROM users WHERE username = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                return user;
            } else {
                throw new UserNotFoundException(username);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error", e);
        }
    }
	
    public boolean validateUser(String username, String password) {
        String sql = "SELECT 1 FROM users WHERE username = ? AND password = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            stmt.setString(2, password); // Note: In production, use hashed passwords!
            
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Returns true if user exists with these credentials
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error during validation", e);
        }
    }
}  // This is the ONLY closing brace that should be at file end