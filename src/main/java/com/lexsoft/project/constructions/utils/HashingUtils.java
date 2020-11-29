package com.lexsoft.project.constructions.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@UtilityClass
public class HashingUtils {

    @SneakyThrows
    public String hashPassword(String password) {
        final MessageDigest digest = MessageDigest.getInstance("SHA3-256");
        final byte[] hashbytes = digest.digest(
                password.getBytes(StandardCharsets.UTF_8));
        String hashed = bytesToHex(hashbytes);
        return hashed;
    }


    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
