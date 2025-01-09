package application;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

//convert the password into a string of unreadable character
public class passwordHash{
	private String oriPassword;
	
	public passwordHash() {
	}
	
	//generate and verify hashed salted password
	public String getSalt() {
		SecureRandom sr = new SecureRandom();
		byte[]salt = new byte[16];
		sr.nextBytes(salt);
		return Base64.getEncoder().encodeToString(salt);
	}
	
	//accept password and salt to convert into hashed password
    public String hashPassword(String password,String salt){
        try{
        	//use SHA-256 as secure algorithm
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for(byte b : hashedBytes){
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        }catch(NoSuchAlgorithmException e){
            throw new RuntimeException("No such algorithm exception", e);
        }
    }

	public String getOriPassword() {
		return oriPassword;
	}

	public void setOriPassword(String oriPassword) {
		this.oriPassword = oriPassword;
	}
}

