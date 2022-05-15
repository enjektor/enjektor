package com.github.enjektor.core.util;

public class NamingUtils {

    public static String beanCase(final String dependencyClassName) {
        final char[] chars = dependencyClassName.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return String.valueOf(chars);
    }

    public static String reverseBeanCase(final String beanName) {
        final char[] chars = beanName.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return new StringBuilder().append(chars).append(".class").toString();
    }
}
