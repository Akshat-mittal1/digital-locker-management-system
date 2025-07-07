import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Locker {
    private int id;
    private String status; // "free" or "occupied"
    private Integer userId;
    private String description;
    
    // Constructor
    public Locker(int id, String status, Integer userId, String description) {
        this.id = id;
        this.status = status;
        this.userId = userId;
        this.description = description;
    }
    
    // Getters
    public int getId() {
        return id;
    }
    
    public String getStatus() {
        return status;
    }
    
    public Integer getUserId() {
        return userId;
    }
    
    public String getDescription() {
        return description;
    }
    
    // Get all lockers
    public static List<Locker> getAllLockers() {
        List<Locker> lockers = new ArrayList<>();
        
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM lockers ORDER BY id")) {
            
            while (rs.next()) {
                Integer userId = rs.getObject("user_id") != null ? rs.getInt("user_id") : null;
                
                lockers.add(new Locker(
                        rs.getInt("id"),
                        rs.getString("status"),
                        userId,
                        rs.getString("description")
                ));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return lockers;
    }
    
    // Get lockers by user ID
    public static List<Locker> getLockersByUserId(int userId) {
        List<Locker> lockers = new ArrayList<>();
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT * FROM lockers WHERE user_id = ? ORDER BY id")) {
            
            ps.setInt(1, userId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lockers.add(new Locker(
                            rs.getInt("id"),
                            rs.getString("status"),
                            userId,
                            rs.getString("description")
                    ));
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return lockers;
    }
    
    // Count lockers by user ID
    public static int countLockersByUserId(int userId) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT COUNT(*) FROM lockers WHERE user_id = ?")) {
            
            ps.setInt(1, userId);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return 0;
    }
    
    // Reserve a locker
    public static boolean reserveLocker(int lockerId, int userId, String description) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "UPDATE lockers SET status = 'occupied', user_id = ?, description = ?, " +
                     "reserved_at = CURRENT_TIMESTAMP WHERE id = ? AND status = 'free'")) {
            
            ps.setInt(1, userId);
            ps.setString(2, description);
            ps.setInt(3, lockerId);
            
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Release a locker
    public static boolean releaseLocker(int lockerId, int userId) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "UPDATE lockers SET status = 'free', user_id = NULL, description = NULL " +
                     "WHERE id = ? AND user_id = ?")) {
            
            ps.setInt(1, lockerId);
            ps.setInt(2, userId);
            
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}