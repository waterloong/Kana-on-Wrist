package org.thewilliamzhang.kanaonwrist;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by William on 2017-02-03.
 */
public class Kana {

    public static final String vowels = "aiueo";
    public static final String consonants = "vkstnhmyrw ";

    public static final String[] hiragana = {
            "あいうえお",
            "かきくけこ",
            "さしすせそ",
            "たちつてと",
            "なにぬねの",
            "はひふへほ",
            "まみむめも",
            "や　ゆ　よ",
            "らりるれろ",
            "わ　　　を",
            "ん　　　　"
    };

    public static final String[] katakana = {
            "アイウエオ",
            "カキクケコ",
            "サシスセソ",
            "タチツテト",
            "ナニウネノ",
            "ハヒフヘホ",
            "マミムメモ",
            "ヤ　ユ　ヨ",
            "ラリルレロ",
            "ワ　　　ヲ",
            "ン　　　　"
    }

    private static Map<String, String> converter = new HashMap<>();

    static {
        converter.put("ti", "chi");
        converter.put("tu", "tsu");
        converter.put("si", "shi");
        converter.put("hu", "fu");
        converter.put("wo", "o");
        converter.put(" a", "n");
        for (char c: vowels.toCharArray()) {
            converter.put("v" + c, " " + c);
        }
    }

    public static String getCorrectRomaji(String regular) {
        if (converter.containsKey(regular)) {
            return converter.get(regular);
        }
        return regular;
    }
}
