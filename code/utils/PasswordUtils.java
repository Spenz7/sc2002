package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtils {

    /**
     * Hashes the input password using SHA-256.
     *
     * @param password the plaintext password.
     * @return the hashed password as a hexadecimal String.
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Password hashing failed: " + e.getMessage());
            return null;
        }
    }

    /**
     * Verifies whether the provided raw password matches the hashed password.
     *
     * @param rawPassword the plaintext password.
     * @param hashedPassword the previously hashed password.
     * @return true if the hashed value of rawPassword matches hashedPassword.
     */
    public static boolean verifyPassword(String rawPassword, String hashedPassword) {
        String hashedAttempt = hashPassword(rawPassword);
        return hashedAttempt != null && hashedAttempt.equals(hashedPassword);
    }
}
