package com.example.admin.karaokesearch.util;

import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 * Created by admin on 4/8/2017.
 */

public class StringUtils {
    public static int SIXWORD = 6;
    public static int FIVEWORD = 5;
    /**
     * Count Word
     * @param s  String input
     * @return   total word in srting
     */
    public static int countWords(String s){
        if (s.isEmpty())
            return 0;
        return s.split("\\s+").length;
    }

    public static String trimSpace(String s){
        return s.trim().replaceAll("\\s+", " ");
    }

    /**
     * @param s = "Trần Trọng Hiến"   chuổi đầu và
     * @return = "Tran Trong Hien"    output
     */
    public static String removeAccents(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }
}
