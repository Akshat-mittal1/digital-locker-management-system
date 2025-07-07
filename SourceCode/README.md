# 🔐 Digital Locker Management System – Java, Swing, MySQL

A secure and user-friendly Java desktop application to manage personal locker items with password protection. Built using **Java Swing** for the GUI, **MySQL** for data persistence, and standard **Object-Oriented Programming principles**, this project is ideal for users looking for a privacy-focused offline storage system.

---

## 📌 Features

- ✅ Secure user authentication (Sign Up & Login)
- 🔐 Password masking and validation
- 🔑 Strong password generation using `SecureRandom`
- 🗂️ Add, view, and manage digital locker items
- 🧪 Prevents SQL injection via `PreparedStatement`
- 🖥️ Java Swing–based GUI interface
- 💾 Data stored locally using MySQL

---

## 🧰 Tech Stack

| Layer     | Tech                                      |
|-----------|-------------------------------------------|
| Language  | Java (SE 8+)                              |
| UI        | Java Swing                                |
| Database  | MySQL                                     |
| JDBC      | [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/) |
| IDE       | IntelliJ IDEA / Eclipse                   |

---

## 📁 Project Structure

Digital_Locker_Management_System/
├── *.java ← Java source files
├── locker_system_db_setup.sql ← SQL database setup script
├── bin/ ← Compiled class files (auto-created)

---

## 🔧 Setup Instructions

### 🖥️ Run on Windows CMD

```bash
:: Step 1: Navigate to your project folder
cd Digital_Locker_Management_System

:: Step 2: Compile all Java files and output class files to /bin
javac -d bin -cp .;mysql-connector-j-9.3.0.jar *.java

:: Step 3: Run the application
java -cp bin;mysql-connector-j-9.3.0.jar LockerManagementSystem
💡 For macOS/Linux users, replace ; with : in the -cp flag.
```

## 🗄️ MySQL Setup
- Create a MySQL database.

- Run the provided locker_system_db_setup.sql script.

- Ensure the credentials in DatabaseManager.java match your MySQL configuration.

## 🔐 Security Notes

- Passwords are generated using Java’s SecureRandom

- JDBC PreparedStatement prevents SQL injection

- Current version stores passwords in plain text (encryption is planned)

## 🚀 Future Scope

- AES encryption for stored passwords

- Cloud backup and cross-device sync

- Mobile application (Android)

- Biometric authentication (e.g., fingerprint)

- Data export/import for backups

## 👨‍💻 Author: [akshat-mittal1](https://github.com/akshat-mittal1)

