package org.hui.tdd.junit4;

/**
 * @author Hui.Liu
 * @since 2021-11-16 13:18
 */
public class StringUtil {
    public static boolean isNotBlank(String str) {
        int length;
        if (str != null && (length = str.length()) != 0) {
            for (int i = 0; i < length; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }
}
