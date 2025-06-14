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
                    .append(TextUtils.formatted("&7Don't forget to do "))
                    .append(
                            Text.literal("/votelinks")
                                    .setStyle(
                                            Style.EMPTY
                                                    .withColor(Formatting.AQUA)
                                                    .withBold(true)
                                                    .withUnderline(true)
                                                    .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/vote"))
                                    )
                    .append(TextUtils.formatted("&7 and get free Vote Crate keys!"))
            ),

            Text.empty()
                    .append(TextUtils.formatted("&7Support the server by using "))
                    .append(
                    Text.literal("/buy")
                            .setStyle(
                                    Style.EMPTY
                                            .withColor(Formatting.GOLD)
                                            .withBold(true)
                                            .withUnderline(true)
                                            .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/buy"))
                            )
            ),

            Text.empty()
                    .append(TextUtils.formatted("&7Don't forget to join our discord by doing "))
                    .append(
                    Text.literal("/discord")
                            .setStyle(
                                    Style.EMPTY
                                            .withColor(Formatting.BLUE)
                                            .withBold(true)
                                            .withUnderline(true)
                                            .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/discord"))
                            )
            ),

            Text.empty()
                    .append(TextUtils.formatted("&7We're currently hiring staff. Type "))
                    .append(
                    Text.literal("/apply")
                            .setStyle(
                                    Style.EMPTY
                                            .withColor(Formatting.GREEN)
                                            .withBold(true)
                                            .withUnderline(true)
                                            .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/apply"))
                            )
                            .append(TextUtils.formatted("&7 to apply"))
            ),

    };

    private static int index = 0;
    private static long intervalSeconds = 900;
    private static long lastAd = System.currentTimeMillis();
    public static boolean enabled = true;

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(Advertiser::tick);
    }

    public static void tick(MinecraftServer s) {
        if (System.currentTimeMillis() - lastAd >= intervalSeconds * 1000 && enabled) {
            runAd(s);
        }
    }

    public static void runAd(MinecraftServer s) {
        CCCore.broadcast(Text.empty().append(CCCore.PREFIX).append(messages[index]));
        index++;
        if (index >= messages.length) index = 0;
        lastAd = System.currentTimeMillis();
    }

}
