package me.skizzme.cc.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StringUtils {

    public static String getTimeDefault() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
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

}
