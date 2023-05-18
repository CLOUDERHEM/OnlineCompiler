package com.lc;

/**
 * @author Aaron Yeung
 * @date 7/8/2022 5:31 PM
 */
public class StringUtils {
    public static String[] concat(String[]... param) {
        if (param == null) {
            return null;
        }
        int arrayLength = 0;
        for (String[] strings : param) {
            if (strings == null) {
                continue;
            }
            for (String s : strings) {
                if (s != null && !s.isEmpty()) {
                    arrayLength++;
                }
            }
        }

        String[] result = new String[arrayLength];

        int index = 0;
        for (String[] strings : param) {
            if (strings == null) {
                continue;
            }
            for (String s : strings) {
                if (s != null && !s.isEmpty()) {
                    result[index++] = s;
                }
            }
        }

        return result;
    }
}
