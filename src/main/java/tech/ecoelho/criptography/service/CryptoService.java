package tech.ecoelho.criptography.service;

import org.jasypt.util.text.StrongTextEncryptor;

public class CryptoService {

    private static final StrongTextEncryptor encryptor;

    static {
        encryptor = new StrongTextEncryptor();
        encryptor.setPassword(System.getenv("APP_KEY"));
    }

    public static String encryptPassword(String text){
        return encryptor.encrypt(text);
    }

    public static String decryptPassword(String text){
        return encryptor.decrypt(text);
    }
}
