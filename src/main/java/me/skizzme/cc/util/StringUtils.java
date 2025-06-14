package me.skizzme.cc.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class StringUtils {
    private final static TreeMap<Integer, String> ROMAN_MAPPINGS = new TreeMap<>();
    private final static Map<Integer, String> ROMAN_NUMERALS = new HashMap<>();
    public static final String DICTIONARY = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

    public static String getTimeDefault() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }

    public static String randomString(final int length) {
        final char[] buffer = new char[length];
        int i = 0;
        while (i < length) {
            buffer[i] = DICTIONARY.charAt(RandomUtils.fastRandom(0, DICTIONARY.length() - 1));
            i++;
        }
        return new String(buffer);
    }

    public static String formatBytes(long bytes) {
        String[] formattings = { "B", "KB", "MB", "GB", "TB"};
        for (int i = 0; i < formattings.length; i++) {
            double value = bytes / Math.pow(1024, i);
            if (value < 1000 || i == formattings.length-1) {
                return Math.round(value*100) / 100f + formattings[i];
            }
        }
        return bytes + formattings[0];
    }

    public static String formatElapsed(final long elapsedTotal, long[] formatsElapsed, String[] formatsDisplay, boolean displayZero, boolean rollOverLargest) {
        StringBuilder formatted = new StringBuilder();

        for (int i = 0; i < formatsElapsed.length-1; i++) {
            long disp = !rollOverLargest && i == 0 ? elapsedTotal : (elapsedTotal % formatsElapsed[i]);
            int elapsed = (int) Math.floor((float) disp / formatsElapsed[i+1]);

//            System.out.println(elapsed + ", " + disp + ", cont? " + (!displayZero && elapsed == 0) + ", " + elapsedTotal % formatsElapsed[i] + ", " + formatsElapsed[i]);

            if (!displayZero && elapsed == 0) {
                continue;
            }

            if (formatted.length() != 0) {
                formatted.append(" ");
            }
            formatted.append(elapsed).append(formatsDisplay[i]);
        }
        return formatted.toString();
    }

    public static String formatElapsed(final long elapsedMs) {
        return formatElapsed(
                elapsedMs,
                new long[] { 24*60*60*1000, 60*60*1000, 60*1000, 1000 },
                new String[] {"h", "m", "s"},
                false,
                false
        );
    }

    public static String calculateRomanNumeral(final int i) {
        int l = ROMAN_MAPPINGS.floorKey(i);
        if (i == l) {
            return ROMAN_MAPPINGS.get(i);
        }
        return ROMAN_MAPPINGS.get(l) + toRomanNumeral(i - l);
    }

    public static String toRomanNumeral(final int i) {
        if (!ROMAN_NUMERALS.containsKey(i)) {
            final String numeral = calculateRomanNumeral(i);
            ROMAN_NUMERALS.put(i, numeral);
            return numeral;
        }
        return ROMAN_NUMERALS.get(i);
    }

    static {
        ROMAN_MAPPINGS.put(1000, "M");
        ROMAN_MAPPINGS.put(900, "CM");
        ROMAN_MAPPINGS.put(500, "D");
        ROMAN_MAPPINGS.put(400, "CD");
        ROMAN_MAPPINGS.put(100, "C");
        ROMAN_MAPPINGS.put(90, "XC");
        ROMAN_MAPPINGS.put(50, "L");
        ROMAN_MAPPINGS.put(40, "XL");
        ROMAN_MAPPINGS.put(10, "X");
        ROMAN_MAPPINGS.put(9, "IX");
        ROMAN_MAPPINGS.put(5, "V");
        ROMAN_MAPPINGS.put(4, "IV");
        ROMAN_MAPPINGS.put(1, "I");
    }

}
