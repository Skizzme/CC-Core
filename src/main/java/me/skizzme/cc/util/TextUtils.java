package me.skizzme.cc.util;

import net.kyori.adventure.text.event.HoverEventSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;

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

    public static Text formatted(String text) {
        return Text.literal(text.replace("&", "§").replace("\\§", "\\&"));
    }

    public static Text teleportableName(ServerPlayerEntity player, Formatting... formattings) {
        return Text.empty()
                .append(player.getGameProfile().getName())
                .setStyle(Style.EMPTY
                        .withHoverEvent(new HoverEvent(
                                HoverEvent.Action.SHOW_TEXT,
                                TextUtils.formatted("&7Click to teleport")
                        ))
                        .withClickEvent(new ClickEvent(
                                ClickEvent.Action.RUN_COMMAND,
                                "/tp " + player.getGameProfile().getName()
                        ))
                        .withFormatting(formattings)
                );
    }

}
