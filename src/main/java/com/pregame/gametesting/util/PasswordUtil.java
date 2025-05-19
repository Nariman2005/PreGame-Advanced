package com.pregame.gametesting.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Utility class for password hashing and verification
 */
public class PasswordUtil {
    private static final int SALT_LENGTH = 16;
    
    /**
     * Generate a random salt for password hashing
     * @return Base64 encoded salt string
     */
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
    
    /**
     * Hash a password with a given salt using SHA-256
     * @param password The password to hash
     * @param salt The salt to use for hashing
     * @return Hashed password
     */
    public static String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(Base64.getDecoder().decode(salt));
            byte[] hashedPassword = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
    
    /**
     * Verify that a password matches a stored hash
     * @param password The password to verify
     * @param storedHash The stored hash to compare against
     * @param storedSalt The salt used to create the stored hash
     * @return true if the password matches, false otherwise
     */
    public static boolean verifyPassword(String password, String storedHash, String storedSalt) {
        String computedHash = hashPassword(password, storedSalt);
        return computedHash.equals(storedHash);
    }
    
    /**
     * Generate a secure password hash with a random salt
     * @param password The password to hash
     * @return String array with [0] = hash, [1] = salt
     */
    public static String[] generatePasswordHash(String password) {
        String salt = generateSalt();
        String hash = hashPassword(password, salt);
        return new String[] { hash, salt };
    }
}
