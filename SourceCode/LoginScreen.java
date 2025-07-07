import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signupButton;
    private JButton eyeButton;
    private boolean passwordVisible = false;
    
    public LoginScreen() {
        // Initialize database
        DatabaseManager.initializeDatabase();
        
        // Set up the frame
        setTitle("Locker Management System - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Create components
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("Locker Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(102, 51, 153)); // Purple color
        headerPanel.add(titleLabel);
        
        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(3, 2, 10, 10));
        
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        
        JLabel passwordLabel = new JLabel("Password:");
        
        // Password panel with eye button
        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordField = new JPasswordField();
        
        // Create eye button with text instead of emoji
        eyeButton = new JButton("Show");
        eyeButton.setPreferredSize(new Dimension(60, 25));
        eyeButton.setFont(new Font("Arial", Font.PLAIN, 10));
        eyeButton.setFocusPainted(false);
        eyeButton.setBorder(BorderFactory.createEtchedBorder());
        eyeButton.setBackground(new Color(240, 240, 240));
        eyeButton.addActionListener(e -> togglePasswordVisibility());
        
        passwordPanel.add(passwordField, BorderLayout.CENTER);
        passwordPanel.add(eyeButton, BorderLayout.EAST);
        
        // Make login button more visible
        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(102, 51, 153)); // Purple color
        loginButton.setForeground(Color.BLACK);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14)); // Bold font
        loginButton.setFocusPainted(false);
        
        signupButton = new JButton("Sign Up");
        signupButton.setBackground(new Color(220, 220, 220));
        signupButton.setForeground(Color.BLACK);
        signupButton.setFont(new Font("Arial", Font.BOLD, 12));
        signupButton.setFocusPainted(false);
        
        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordPanel);
        formPanel.add(loginButton);
        formPanel.add(signupButton);
        
        // Add components to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        // Add main panel to frame
        add(mainPanel);
        
        // Add action listeners
        loginButton.addActionListener(e -> handleLogin());
        signupButton.addActionListener(e -> openSignupScreen());
        
        // Add key listener for Enter key
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleLogin();
                }
            }
        });
    }
    
    private void togglePasswordVisibility() {
        if (passwordVisible) {
            // Hide password
            passwordField.setEchoChar('*');
            eyeButton.setText("Show");
            passwordVisible = false;
        } else {
            // Show password
            passwordField.setEchoChar((char) 0);
            eyeButton.setText("Hide");
            passwordVisible = true;
        }
    }
    
    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter both username and password",
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        User user = User.authenticate(username, password);
        
        if (user != null) {
            // Login successful
            this.dispose();
            LockerDashboard dashboard = new LockerDashboard(user);
            dashboard.setVisible(true);
        } else {
            // Login failed
            JOptionPane.showMessageDialog(this,
                    "Invalid username or password",
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void openSignupScreen() {
        this.dispose();
        SignupScreen signupScreen = new SignupScreen();
        signupScreen.setVisible(true);
    }
}