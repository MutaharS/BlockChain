import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringUtil{
    
    /* Applies Sha256 to a string and returns a hash. */
    public static String applySha256(String input){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            /* Applies sha256 to our input */
            byte[] hash;    // array of bytes called hash

            // Takes String input and turns it into bytes. Then using those bytes apply sha256. 
            hash = digest.digest(input.getBytes("UTF-8")); // Assign hash with the created sha256 hash;
            StringBuilder hexString = new StringBuilder();  // Object to create a string
            for (byte elem: hash) { // for every byte in hash (byte array)
                String hex = Integer.toHexString(0xff & elem); //0xff = 255
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}//end class
