package me.skizzme.cc.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.lucko.fabric.api.permissions.v0.Permissions;
import me.skizzme.cc.util.TextUtils;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ApplyCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("apply")
                        .requires(Permissions.require(CCCore.PERM_ID + ".apply"))
                        .executes(ApplyCommand::run)
        );
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayerOrThrow();
        String[][] applyLinks = {
                {"https://forms.gle/dA1SLm5XGZdKpyPC9","&eHelper Application"},
                {"https://forms.gle/nbf2cGgsnk8XAkER8","&9Builder Application"}
        };

        player.sendMessage(TextUtils.formatted("&7Select Your Application Below:"));

        for (int i = 0; i < applyLinks.length; i++) {
            player.sendMessage(Text.empty()
                    .append(Text.literal("  ").setStyle(Style.EMPTY))
                    .append(Text.empty()
                            .append(TextUtils.formatted(applyLinks[i][1]))
                            .setStyle(Style.EMPTY
                                    .withColor(Formatting.WHITE)
                                    .withUnderline(true)
                                    .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, applyLinks[i][0]))
                            )
                    )
            );
        }
        return 1;
    }
}