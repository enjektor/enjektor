package com.github.enjektor.utils;

public class NamingUtils {
    
    public static String beanCase(final String dependencyClassName) {
        final char[] chars = dependencyClassName.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return new String(chars);
    }
}
