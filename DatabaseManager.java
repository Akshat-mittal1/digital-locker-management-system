import java.sql.*;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/locker_system";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root"; // Change to your MySQL password
    
    private static Connection connection;
    
    // Get database connection
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            } catch (ClassNotFoundException e) {
                throw new SQLException("MySQL JDBC Driver not found", e);
            }
        }
        return connection;
    }
    
    // Close database connection
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Initialize database (create tables if they don't exist)
    public static void initializeDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            
            // Create users table
            String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "username VARCHAR(50) UNIQUE NOT NULL," +
                    "email VARCHAR(100) UNIQUE NOT NULL," +
                    "password VARCHAR(255) NOT NULL," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")";
            stmt.execute(createUsersTable);
            
            // Create lockers table
            String createLockersTable = "CREATE TABLE IF NOT EXISTS lockers (" +
                    "id INT PRIMARY KEY," +
                    "status ENUM('free', 'occupied') DEFAULT 'free'," +
                    "user_id INT," +
                    "description TEXT," +
                    "reserved_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL" +
                    ")";
            stmt.execute(createLockersTable);
            
            // Initialize 100 lockers if they don't exist
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM lockers");
            rs.next();
            int lockerCount = rs.getInt(1);
            
            if (lockerCount == 0) {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO lockers (id, status) VALUES (?, 'free')");
                for (int i = 1; i <= 100; i++) {
                    ps.setInt(1, i);
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            
            System.out.println("Database initialized successfully");
            
        } catch (SQLException e) {
            System.err.println("Database initialization failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}