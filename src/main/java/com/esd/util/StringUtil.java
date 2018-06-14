package com.esd.util;

/**
 * @author liukai
 */
public class StringUtil {

    /**
     * 判断字符串是否为空
     *
     * @return 为空返回false 不为空返回true
     * @author liukai
     */
    public static boolean isNotEmpty(String... strings) {
        if (strings == null) {
            return false;
        }
        for (String str : strings) {
            if (str == null || str.isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
