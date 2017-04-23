package com.example.admin.karaokesearch.util;

import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 * Created by admin on 4/8/2017.
 */

public class StringUtils {
        public static String removeAccent(String s) {
            String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            return pattern.matcher(temp).replaceAll("");
        }
}
