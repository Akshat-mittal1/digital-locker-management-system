import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class SignupScreen extends JFrame {
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton generatePasswordButton;
    private JButton signupButton;
    private JButton backButton;
    private JButton eyeButton;
    private JProgressBar strengthMeter;
    private JLabel strengthLabel;
    private JComboBox<String> passwordStrengthCombo;
    private boolean passwordVisible = false;
    
    public SignupScreen() {
        // Set up the frame
        setTitle("Locker Management System - Sign Up");
        setSize(500, 400);
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
        JLabel titleLabel = new JLabel("Create an Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(102, 51, 153)); // Purple color
        headerPanel.add(titleLabel);
        
        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(7, 2, 10, 10));
        
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        
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
        
        JLabel generateLabel = new JLabel("Generate Password:");
        JPanel generatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        passwordStrengthCombo = new JComboBox<>(new String[]{"Weak", "Medium", "Strong"});
        generatePasswordButton = new JButton("Generate");
        generatePanel.add(passwordStrengthCombo);
        generatePanel.add(Box.createHorizontalStrut(10));
        generatePanel.add(generatePasswordButton);
        
        JLabel strengthMeterLabel = new JLabel("Password Strength:");
        JPanel strengthPanel = new JPanel(new BorderLayout(5, 0));
        strengthMeter = new JProgressBar(0, 100);
        strengthMeter.setStringPainted(false);
        strengthLabel = new JLabel("Weak");
        strengthPanel.add(strengthMeter, BorderLayout.CENTER);
        strengthPanel.add(strengthLabel, BorderLayout.EAST);
        
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        
        // Make signup button more visible
        signupButton = new JButton("Sign Up");
        signupButton.setBackground(new Color(102, 51, 153)); // Purple color
        signupButton.setForeground(Color.BLACK);
        signupButton.setFont(new Font("Arial", Font.BOLD, 14)); // Bold font
        signupButton.setFocusPainted(false);
        
        backButton = new JButton("Back to Login");
        backButton.setBackground(new Color(220, 220, 220));
        backButton.setForeground(Color.BLACK);
        backButton.setFont(new Font("Arial", Font.BOLD, 12));
        backButton.setFocusPainted(false);
        
        buttonPanel.add(signupButton);
        buttonPanel.add(backButton);
        
        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordPanel);
        formPanel.add(generateLabel);
        formPanel.add(generatePanel);
        formPanel.add(strengthMeterLabel);
        formPanel.add(strengthPanel);
        formPanel.add(new JLabel("")); // Empty space
        formPanel.add(buttonPanel);
        
        // Add components to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        // Add main panel to frame
        add(mainPanel);
        
        // Add action listeners
        generatePasswordButton.addActionListener(e -> generatePassword());
        signupButton.addActionListener(e -> handleSignup());
        backButton.addActionListener(e -> goBackToLogin());
        
        // Add document listener to password field
        passwordField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updatePasswordStrength();
            }
            
            @Override
            public void removeUpdate(DocumentEvent e) {
                updatePasswordStrength();
            }
            
            @Override
            public void changedUpdate(DocumentEvent e) {
                updatePasswordStrength();
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
    
    private void generatePassword() {
        String strength = (String) passwordStrengthCombo.getSelectedItem();
        String password = PasswordGenerator.generatePassword(strength.toLowerCase());
        passwordField.setText(password);
        updatePasswordStrength();
    }
    
    private void updatePasswordStrength() {
        String password = new String(passwordField.getPassword());
        int strength = PasswordGenerator.calculatePasswordStrength(password);
        
        strengthMeter.setValue(strength);
        
        if (strength < 30) {
            strengthMeter.setForeground(Color.RED);
            strengthLabel.setText("Weak");
            strengthLabel.setForeground(Color.RED);
        } else if (strength < 70) {
            strengthMeter.setForeground(Color.YELLOW);
            strengthLabel.setText("Medium");
            strengthLabel.setForeground(Color.ORANGE);
        } else {
            strengthMeter.setForeground(Color.GREEN);
            strengthLabel.setText("Strong");
            strengthLabel.setForeground(Color.GREEN);
        }
    }
    
    private void handleSignup() {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        
        // Validate input
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill in all fields",
                    "Signup Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validate email format
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid email address",
                    "Signup Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check if username already exists
        if (User.usernameExists(username)) {
            JOptionPane.showMessageDialog(this,
                    "Username already exists. Please choose another one.",
                    "Signup Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check if email already exists
        if (User.emailExists(email)) {
            JOptionPane.showMessageDialog(this,
                    "Email already exists. Please use another email address.",
                    "Signup Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Create and save user
        User user = new User(username, email, password);
        boolean success = user.save();
        
        if (success) {
            JOptionPane.showMessageDialog(this,
                    "Account created successfully!",
                    "Signup Success",
                    JOptionPane.INFORMATION_MESSAGE);
            
            // Go back to login screen
            goBackToLogin();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Failed to create account. Please try again.",
                    "Signup Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void goBackToLogin() {
        this.dispose();
        LoginScreen loginScreen = new LoginScreen();
        loginScreen.setVisible(true);
    }
}