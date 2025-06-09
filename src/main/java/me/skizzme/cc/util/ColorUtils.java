package me.skizzme.cc.util;

import net.minecraft.util.Colors;

import java.awt.*;
import java.util.Arrays;

public class ColorUtils {

    public static Color lerpColorHSB(Color start, Color end, float v) {
        float[] start_hsb = Color.RGBtoHSB(start.getRed(), start.getGreen(), start.getBlue(), null);
        float[] end_hsb = Color.RGBtoHSB(end.getRed(), end.getGreen(), end.getBlue(), null);

        for (int i = 0; i < start_hsb.length; i++) {
            start_hsb[i] = start_hsb[i] + (end_hsb[i]-start_hsb[i]) * v;
        }
        return Color.getHSBColor(start_hsb[0], start_hsb[1], start_hsb[2]);
    }

    public static Color lerpColorRGB(Color start, Color end, float v) {
        return new Color((int) (start.getRed() + (end.getRed() - start.getRed()) * v),
                (int) (start.getGreen() + (end.getGreen() - start.getGreen()) * v),
                (int) (start.getBlue() + (end.getBlue() - start.getBlue()) * v));
    }

}
