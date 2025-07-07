import javax.swing.*;
import java.awt.*;

public class LockerManagementSystem {
    public static void main(String[] args) {
        try {
            // Set look and feel to system default
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Run the application on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            // Create and show the login screen
            LoginScreen loginScreen = new LoginScreen();
            loginScreen.setVisible(true);
        });
    }
}