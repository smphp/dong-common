package com.dong.common.core.utils.sign;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCPEUtils {
    private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String encode(String rawPWD) {
        String hashedPassword = passwordEncoder.encode(rawPWD);
        return hashedPassword;
    }

    public static boolean matches(String rawPWD, String encodePWD) {
        boolean result = passwordEncoder.matches(rawPWD, encodePWD);
        return result;
    }
}

