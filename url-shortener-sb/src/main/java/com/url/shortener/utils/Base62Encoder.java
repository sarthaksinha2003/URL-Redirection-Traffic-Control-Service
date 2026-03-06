package com.url.shortener.utils;

public class Base62Encoder {
    // 62 characters total: 26 uppercase + 26 lowercase + 10 digits
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int BASE = 62;
    public static String encode(long id) {
        if (id == 0) return String.valueOf(CHARACTERS.charAt(0));
        StringBuilder result = new StringBuilder();
        while (id > 0) {
            result.append(CHARACTERS.charAt((int)(id % BASE)));
            id /= BASE;
        }
        return result.reverse().toString(); // reverse because we built it backwards
    }

    public static long decode(String shortUrl) {
        long id = 0;
        for (char c : shortUrl.toCharArray()) {
            id = id * BASE + CHARACTERS.indexOf(c);
        }
        return id;
    }
}
