package maleda.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption {

    // Program in Java for calculating SHA hash value 
    public static String hashData(String hashInput) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = messageDigest.digest(hashInput.getBytes());

            StringBuilder hashedPassword = new StringBuilder();
            for (byte hashByte : hashBytes) {
                hashedPassword.append(Integer.toString((hashByte & 0xff) + 0x100, 16).substring(1));
            }
            return hashedPassword.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(" Exception thrown for incorrect algorithm : " + e);
            return null;
        }
    }
}
