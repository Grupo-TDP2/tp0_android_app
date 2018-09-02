package com.tdp2.tp0.weweather.utils;

public class StringUtils
{
    /**
     * returns the string, the first char uppercase
     *
     * @param target The string to modify
     * @return The string with the first letter uppercased
     */
    public static String asUpperCaseFirstChar(final String target)
    {
        if ((target == null) || (target.length() == 0)) {
            return target; // You could omit this check and simply live with an
            // exception if you like
        }
        return Character.toUpperCase(target.charAt(0))
                + (target.length() > 1 ? target.substring(1) : "");
    }
}
