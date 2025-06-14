package me.skizzme.cc.util;

import java.security.SecureRandom;
import java.util.Random;

public final class RandomUtils {

    public static final Random RANDOM = new Random();
    public static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public static char random() {
        return StringUtils.DICTIONARY.charAt(RANDOM.nextInt(StringUtils.DICTIONARY.length()));
    }

    public static int random(final int min, final int max) {
        return min >= max ? max : (int) (SECURE_RANDOM.nextDouble() * (max - min) + min);
    }

    public static long random(final long min, final long max) {
        return min >= max ? max : (long) (SECURE_RANDOM.nextDouble() * (max - min) + min);
    }

    public static float random(final float min, final float max) {
        return min >= max ? max : (float) (SECURE_RANDOM.nextDouble() * (max - min) + min);
    }

    public static double random(final double min, final double max) {
        return min >= max ? max : SECURE_RANDOM.nextDouble() * (max - min) + min;
    }

    public static int fastRandom(final int min, final int max) {
        return min >= max ? max : (int) (RANDOM.nextDouble() * (max - min) + min);
    }

    public static long fastRandom(final long min, final long max) {
        return min >= max ? max : (long) (RANDOM.nextDouble() * (max - min) + min);
    }

    public static float fastRandom(final float min, final float max) {
        return min >= max ? max : (float) (RANDOM.nextDouble() * (max - min) + min);
    }

    public static double fastRandom(final double min, final double max) {
        return min >= max ? max : RANDOM.nextDouble() * (max - min) + min;
    }

}