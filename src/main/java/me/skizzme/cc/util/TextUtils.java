package me.skizzme.cc.util;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.awt.*;

public class TextUtils {

    public static Text gradientTextRGB(Color start, Color end, String text) {
        MutableText out = Text.empty();
        for (int i = 0; i < text.length(); i++) {
            int lerpedColor = ColorUtils.lerpColorRGB(start, end, ((float) i) / ((float) text.length()-1)).getRGB();
            out.append(Text.literal(String.valueOf(text.charAt(i))).withColor(lerpedColor));

        }
        return out;
    }

    public static Text gradientTextHSB(Color start, Color end, String text) {
        MutableText out = Text.empty();
        for (int i = 0; i < text.length(); i++) {
            int lerpedColor = ColorUtils.lerpColorHSB(start, end, ((float) i) / ((float) text.length()-1)).getRGB();
            out.append(Text.literal(String.valueOf(text.charAt(i))).withColor(lerpedColor));

        }
        return out;
    }

}
