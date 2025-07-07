-- Create the database
CREATE DATABASE IF NOT EXISTS locker_system;
USE locker_system;

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create lockers table
CREATE TABLE IF NOT EXISTS lockers (
    id INT PRIMARY KEY,
    status ENUM('free', 'occupied') DEFAULT 'free',
    user_id INT,
    description TEXT,
    reserved_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL
);

-- Initialize 100 lockers
DELIMITER //
CREATE PROCEDURE initialize_lockers()
BEGIN
    DECLARE i INT DEFAULT 1;
    
    -- Check if lockers already exist
    IF (SELECT COUNT(*) FROM lockers) = 0 THEN
        -- Insert 100 lockers
        WHILE i <= 100 DO
            INSERT INTO lockers (id, status) VALUES (i, 'free');
            SET i = i + 1;
        END WHILE;
    END IF;
END //
DELIMITER ;

-- Call the procedure
CALL initialize_lockers();

-- Drop the procedure
DROP PROCEDURE IF EXISTS initialize_lockers;