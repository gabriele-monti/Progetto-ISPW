package it.foodmood.utils.security;

import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordHasher {
    
    public String hash(char[] password){
        try{
            // Genero un salt
            byte[] salt = new byte[16];
            new SecureRandom().nextBytes(salt);

            // Calcolo hash
            PBEKeySpec specification = new PBEKeySpec(password, salt, 65536, 256);
            byte[] hash = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(specification).getEncoded();

            // Ritorno "salt:hash" in base 64
            return Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hash);
        } catch (Exception _) {
            throw new RuntimeException("Errore nel calcolo dell'hash della password.");
        }
    }

    public boolean verify(char[] password, String storedHash){
        try {
            // estrazione salt e hash originale
            String[] parts = storedHash.split(":");
            byte[] salt = Base64.getDecoder().decode(parts[0]);
            byte[] originalHash = Base64.getDecoder().decode(parts[1]);

            // ricalcolo hash con password fornita
            PBEKeySpec specification = new PBEKeySpec(password, salt, 65536, 256);
            byte[] hash = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(specification).getEncoded();

            // confronto
            if(originalHash.length != hash.length) {
                return false;
            }
            int hashCompare = 0;
            for(int i = 0; i < originalHash.length; i++){
                hashCompare |= originalHash[i]^hash[i];
            }

            return hashCompare == 0;
            
        } catch (Exception _) {
            return false;
        }
    }
}
