package com.aryanda.rms.util;

public class Util {

    private Util() {

    }

    public static boolean isEmpty(Object o) {
        return o == null || String.valueOf(o).trim().length() == 0;
    }

    public static boolean isNotEmpty(String s) {
        return s != null && s.trim().length() > 0;
    }
}
