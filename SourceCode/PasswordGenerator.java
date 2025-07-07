import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PasswordGenerator {
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()_+-=[]{}|;:,.<>?";
    
    private static final Random RANDOM = new SecureRandom();
    
    // Generate password based on strength
    public static String generatePassword(String strength) {
        String chars;
        int length;
        
        switch (strength.toLowerCase()) {
            case "weak":
                chars = LOWERCASE;
                length = 6;
                break;
            case "medium":
                chars = LOWERCASE + UPPERCASE + DIGITS;
                length = 10;
                break;
            case "strong":
                chars = LOWERCASE + UPPERCASE + DIGITS + SYMBOLS;
                length = 16;
                break;
            default:
                chars = LOWERCASE;
                length = 8;
        }
        
        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = RANDOM.nextInt(chars.length());
            password.append(chars.charAt(randomIndex));
        }
        
        // For medium and strong passwords, ensure at least one character from each required set
        if (strength.equalsIgnoreCase("medium") || strength.equalsIgnoreCase("strong")) {
            ensureCharacterPresence(password, LOWERCASE);
            ensureCharacterPresence(password, UPPERCASE);
            ensureCharacterPresence(password, DIGITS);
        }
        
        // For strong passwords, ensure at least one symbol
        if (strength.equalsIgnoreCase("strong")) {
            ensureCharacterPresence(password, SYMBOLS);
        }
        
        return password.toString();
    }
    
    // Ensure a character from the given set is present in the password
    private static void ensureCharacterPresence(StringBuilder password, String charSet) {
        boolean hasChar = false;
        for (int i = 0; i < password.length(); i++) {
            if (charSet.indexOf(password.charAt(i)) >= 0) {
                hasChar = true;
                break;
            }
        }
        
        if (!hasChar) {
            int randomPosition = RANDOM.nextInt(password.length());
            int randomCharIndex = RANDOM.nextInt(charSet.length());
            password.setCharAt(randomPosition, charSet.charAt(randomCharIndex));
        }
    }
    
    // Calculate password strength (0-100)
    public static int calculatePasswordStrength(String password) {
        int strength = 0;
        
        // Length check
        if (password.length() >= 8) strength += 1;
        if (password.length() >= 16) strength += 1;
        
        // Character type checks
        if (password.matches(".*[a-z].*")) strength += 1;
        if (password.matches(".*[A-Z].*")) strength += 1;
        if (password.matches(".*[0-9].*")) strength += 1;
        if (password.matches(".*[^a-zA-Z0-9].*")) strength += 1;
        
        // Normalize to 0-100
        return Math.min(100, (int) Math.round((strength / 6.0) * 100));
    }
}