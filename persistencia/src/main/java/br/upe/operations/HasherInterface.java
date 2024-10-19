package br.upe.operations;

import java.security.MessageDigest;

import java.util.logging.Level;
import java.util.logging.Logger;

public interface HasherInterface {
   Logger logger = Logger.getLogger(HasherInterface.class.getName());
    static String hash(String message) {
        String messageHash = "";
        try {
            // Create MessageDigest instance for SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Add password bytes to digest
            byte[] bytes = md.digest(message.getBytes());

            // Convert bytes to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }

            // Return the hashed password
            messageHash = sb.toString();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error hashing message", e);
        }

        return messageHash;
    }
}
