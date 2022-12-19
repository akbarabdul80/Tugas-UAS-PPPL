package org.example.utils;

import java.util.Random;

public class StringUtils {
    public static java.lang.String getSaltString() {
        java.lang.String random = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * random.length());
            salt.append(random.charAt(index));
        }
        java.lang.String saltStr = salt.toString();
        return saltStr;
    }
}
