import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class LockerDashboard extends JFrame {
    private User currentUser;
    private List<Locker> allLockers;
    private JPanel lockerGridPanel;
    private JPanel userLockersPanel;
    private JLabel userInfoLabel;
    private JLabel lockerCountLabel;
    
    public LockerDashboard(User user) {
        this.currentUser = user;
        
        // Set up the frame
        setTitle("Locker Management System - Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Create components
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(102, 51, 153)); // Purple color
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        
        JLabel titleLabel = new JLabel("Locker Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        
        userInfoLabel = new JLabel("Welcome, " + currentUser.getUsername());
        userInfoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        userInfoLabel.setForeground(Color.WHITE);
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(220, 220, 220));
        logoutButton.setFocusPainted(false);
        
        JPanel headerLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerLeftPanel.setOpaque(false);
        headerLeftPanel.add(titleLabel);
        
        JPanel headerRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        headerRightPanel.setOpaque(false);
        headerRightPanel.add(userInfoLabel);
        headerRightPanel.add(Box.createHorizontalStrut(20));
        headerRightPanel.add(logoutButton);
        
        headerPanel.add(headerLeftPanel, BorderLayout.WEST);
        headerPanel.add(headerRightPanel, BorderLayout.EAST);
        
        // User lockers panel
        JPanel userLockersContainer = new JPanel(new BorderLayout());
        userLockersContainer.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Your Lockers", TitledBorder.LEFT, TitledBorder.TOP));
        
        lockerCountLabel = new JLabel("You have reserved 0 out of 2 available lockers.");
        lockerCountLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 0));
        
        userLockersPanel = new JPanel();
        userLockersPanel.setLayout(new BoxLayout(userLockersPanel, BoxLayout.Y_AXIS));
        
        userLockersContainer.add(lockerCountLabel, BorderLayout.NORTH);
        userLockersContainer.add(new JScrollPane(userLockersPanel), BorderLayout.CENTER);
        
        // All lockers panel
        JPanel allLockersContainer = new JPanel(new BorderLayout());
        allLockersContainer.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "All Lockers", TitledBorder.LEFT, TitledBorder.TOP));
        
        JLabel instructionLabel = new JLabel("Click on an available locker to reserve it.");
        instructionLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 0));
        
        lockerGridPanel = new JPanel(new GridLayout(10, 10, 5, 5));
        
        allLockersContainer.add(instructionLabel, BorderLayout.NORTH);
        allLockersContainer.add(new JScrollPane(lockerGridPanel), BorderLayout.CENTER);
        
        // Legend panel with more visible colors
        JPanel legendPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        legendPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Legend", TitledBorder.LEFT, TitledBorder.TOP));
        
        addLegendItem(legendPanel, new Color(144, 238, 144), "Available"); // Light Green
        addLegendItem(legendPanel, new Color(135, 206, 250), "Your Locker"); // Light Sky Blue
        addLegendItem(legendPanel, new Color(255, 160, 160), "Occupied by Others"); // Light Coral
        
        // Add components to main panel
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.add(userLockersContainer, BorderLayout.NORTH);
        centerPanel.add(allLockersContainer, BorderLayout.CENTER);
        centerPanel.add(legendPanel, BorderLayout.SOUTH);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        // Add main panel to frame
        add(mainPanel);
        
        // Add action listeners
        logoutButton.addActionListener(e -> logout());
        
        // Load lockers
        loadLockers();
    }
    
    private void addLegendItem(JPanel panel, Color color, String text) {
        JPanel colorBox = new JPanel();
        colorBox.setBackground(color);
        colorBox.setPreferredSize(new Dimension(20, 20));
        colorBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        panel.add(colorBox);
        panel.add(new JLabel(text));
    }
    
    private void loadLockers() {
        // Clear panels
        userLockersPanel.removeAll();
        lockerGridPanel.removeAll();
        
        // Get all lockers
        allLockers = Locker.getAllLockers();
        
        // Count user's lockers
        int userLockerCount = 0;
        for (Locker locker : allLockers) {
            if (locker.getUserId() != null && locker.getUserId() == currentUser.getId()) {
                userLockerCount++;
                
                // Add to user lockers panel
                JPanel lockerPanel = createUserLockerPanel(locker);
                userLockersPanel.add(lockerPanel);
                userLockersPanel.add(Box.createVerticalStrut(10));
            }
        }
        
        // Update locker count label
        lockerCountLabel.setText("You have reserved " + userLockerCount + " out of 2 available lockers.");
        
        // Add all lockers to grid
        for (Locker locker : allLockers) {
            JButton lockerButton = createLockerButton(locker);
            lockerGridPanel.add(lockerButton);
        }
        
        // Refresh UI
        userLockersPanel.revalidate();
        userLockersPanel.repaint();
        lockerGridPanel.revalidate();
        lockerGridPanel.repaint();
    }
    
    private JPanel createUserLockerPanel(Locker locker) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(135, 206, 250), 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        
        JLabel lockerIdLabel = new JLabel("Locker #" + locker.getId());
        lockerIdLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        JTextArea descriptionArea = new JTextArea(locker.getDescription());
        descriptionArea.setEditable(false);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setBackground(new Color(240, 248, 255));
        descriptionArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        JButton releaseButton = new JButton("Release Locker");
        releaseButton.setBackground(new Color(255, 160, 160));
        releaseButton.setFocusPainted(false);
        releaseButton.addActionListener(e -> releaseLocker(locker.getId()));
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(lockerIdLabel, BorderLayout.WEST);
        topPanel.add(releaseButton, BorderLayout.EAST);
        
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(descriptionArea), BorderLayout.CENTER);
        
        return panel;
    }
    
    private JButton createLockerButton(Locker locker) {
        JButton button = new JButton(String.valueOf(locker.getId()));
        button.setMargin(new Insets(2, 2, 2, 2));
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 10));
        
        if (locker.getStatus().equals("free")) {
            button.setBackground(new Color(144, 238, 144)); // Light Green - more visible
            button.setForeground(new Color(0, 100, 0)); // Dark Green text
            button.addActionListener(e -> reserveLocker(locker.getId()));
        } else if (locker.getUserId() != null && locker.getUserId() == currentUser.getId()) {
            button.setBackground(new Color(135, 206, 250)); // Light Sky Blue - more visible
            button.setForeground(new Color(0, 0, 139)); // Dark Blue text
            button.addActionListener(e -> releaseLocker(locker.getId()));
        } else {
            button.setBackground(new Color(255, 160, 160)); // Light Coral - more visible
            button.setForeground(new Color(139, 0, 0)); // Dark Red text
            button.setEnabled(false);
        }
        
        return button;
    }
    
    private void reserveLocker(int lockerId) {
        // Check if user already has 2 lockers
        int userLockerCount = Locker.countLockersByUserId(currentUser.getId());
        if (userLockerCount >= 2) {
            JOptionPane.showMessageDialog(this,
                    "You can only reserve up to 2 lockers. Please release a locker first.",
                    "Reservation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Show dialog to enter description
        String description = JOptionPane.showInputDialog(this,
                "Enter a description of the items you plan to store:",
                "Reserve Locker #" + lockerId,
                JOptionPane.QUESTION_MESSAGE);
        
        if (description != null) {
            boolean success = Locker.reserveLocker(lockerId, currentUser.getId(), description);
            
            if (success) {
                JOptionPane.showMessageDialog(this,
                        "Locker #" + lockerId + " has been reserved successfully.",
                        "Reservation Success",
                        JOptionPane.INFORMATION_MESSAGE);
                
                // Reload lockers
                loadLockers();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Failed to reserve locker. It may have been taken by another user.",
                        "Reservation Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void releaseLocker(int lockerId) {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to release Locker #" + lockerId + "?",
                "Release Locker",
                JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = Locker.releaseLocker(lockerId, currentUser.getId());
            
            if (success) {
                JOptionPane.showMessageDialog(this,
                        "Locker #" + lockerId + " has been released successfully.",
                        "Release Success",
                        JOptionPane.INFORMATION_MESSAGE);
                
                // Reload lockers
                loadLockers();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Failed to release locker.",
                        "Release Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void logout() {
        this.dispose();
        LoginScreen loginScreen = new LoginScreen();
        loginScreen.setVisible(true);
    }
}