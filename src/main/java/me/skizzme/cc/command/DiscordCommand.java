package me.skizzme.cc.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.lucko.fabric.api.permissions.v0.Permissions;
import me.skizzme.cc.CCCore;
import me.skizzme.cc.util.TextUtils;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class DiscordCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("discord")
                        .requires(Permissions.require(CCCore.PERM_ID + ".discord"))
                        .executes(DiscordCommand::run)
        );
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayerOrThrow();
        String[] discordLinks = {
                "https://discord.gg/VNhSxR8p7J"
        };

        player.sendMessage(TextUtils.formatted("&7Click Below:"));

        for (int i = 0; i < discordLinks.length; i++) {
            player.sendMessage(Text.empty()
                    .append(Text.literal("  ").setStyle(Style.EMPTY))
                    .append(Text.empty()
                            .append(TextUtils.formatted("Discord Link"))
                            .setStyle(Style.EMPTY
                                    .withColor(Formatting.DARK_PURPLE)
                                    .withUnderline(true)
                                    .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, discordLinks[i]))
                            )
                    )
            );
        }
        return 1;
    }
}