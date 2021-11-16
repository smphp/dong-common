package com.dong.common.core.utils.transform;

import java.util.Locale;

public class NameUtil {
    public static final String UNDERLINE = "_";
    public static final char UNDERLINE_CHARE = '_';

    public NameUtil() {
    }

    public static String toCamel(String filedName) {
        if (filedName != null && !"".equals(filedName.trim())) {
            int len = filedName.length();
            StringBuilder sb = new StringBuilder(len);

            for(int i = 0; i < len; ++i) {
                char c = filedName.charAt(i);
                if (c == '_') {
                    ++i;
                    if (i < len) {
                        sb.append(Character.toUpperCase(filedName.charAt(i)));
                    }
                } else {
                    sb.append(c);
                }
            }

            return sb.toString();
        } else {
            return "";
        }
    }

    public static String toUnderline(String fieldName) {
        if (fieldName != null && !"".equals(fieldName.trim())) {
            int len = fieldName.length();
            StringBuilder sb = new StringBuilder(len);

            for(int i = 0; i < len; ++i) {
                char c = fieldName.charAt(i);
                if (Character.isUpperCase(c)) {
                    sb.append("_");
                    sb.append(Character.toLowerCase(c));
                } else {
                    sb.append(c);
                }
            }

            return sb.toString();
        } else {
            return "";
        }
    }

    public static String toLowStart(String name) {
        return name != null && name.length() != 0 ? name.substring(0, 1).toLowerCase(Locale.ENGLISH) + name.substring(1) : name;
    }

    public static String toUpStart(String name) {
        return name != null && name.length() != 0 ? name.substring(0, 1).toUpperCase(Locale.ENGLISH) + name.substring(1) : name;
    }

    public static String getUpAttribute(String fieldName){
        String upperChar = fieldName.substring(0,1).toUpperCase();
        String anotherStr = fieldName.substring(1);
        String methodName = "get" + upperChar + anotherStr;
        return methodName;
    }

    public static String change(String fieldName) {
        return fieldName.contains("_") ? toCamel(fieldName) : toUnderline(fieldName);
    }
}
