package me.skizzme.cc;

import dev.architectury.event.events.common.TickEvent;
import me.skizzme.cc.util.TextUtils;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class Advertiser {

    public static final Text[] messages = {
            Text.empty()
                    .append(TextUtils.formatted("&7Support the server by doing "))
                    .append(
                            Text.literal("/vote")
                                    .setStyle(
                                            Style.EMPTY
                                                    .withColor(Formatting.BLUE)
                                                    .withBold(true)
                                                    .withUnderline(true)
                                                    .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/vote"))
                                    )
            )
    };

    private static int index = 0;
    private static long intervalSeconds = 300;
    private static long lastAd = System.currentTimeMillis();

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(Advertiser::tick);
    }

    public static void tick(MinecraftServer s) {
        if (System.currentTimeMillis() - lastAd >= intervalSeconds * 1000) {
            CCCore.broadcast(Text.empty().append(CCCore.PREFIX).append(messages[index]));
            index++;
            if (index >= messages.length) index = 0;
            lastAd = System.currentTimeMillis();
        }
    }

}
