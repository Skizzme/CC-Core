package me.skizzme.cc.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.lucko.fabric.api.permissions.v0.Permissions;
import me.skizzme.cc.CCCore;
import me.skizzme.cc.shop.Shop;
import me.skizzme.cc.util.TextUtils;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class VoteCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        System.out.println("VOTE LINKS");
//        dispatcher.register(
//                CommandManager.literal("vote")
//                        .requires(Permissions.require(CCCore.PERM_ID + ".vote"))
//                        .executes(VoteCommand::run)
//        );

        dispatcher.register(
                CommandManager.literal("votelinks")
                        .requires(Permissions.require(CCCore.PERM_ID + ".vote"))
                        .executes(VoteCommand::run)
        );
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayerOrThrow();
        String[] voteLinks = {
                "https://minecraftservers.org/vote/674797",
                "https://minecraft-mp.com/server/344992/vote/",
                "https://top-mc-servers.net/server/503",
                "https://www.curseforge.com/servers/minecraft/game/cobblemon-cove/vote"
        };

        player.sendMessage(TextUtils.formatted("&7Vote using all links below:"));

        for (int i = 0; i < voteLinks.length; i++) {
            player.sendMessage(Text.empty()
                    .append(Text.literal("  ").setStyle(Style.EMPTY))
                    .append(Text.empty()
                            .append(TextUtils.formatted("Vote Platform " + (i + 1)))
                            .setStyle(Style.EMPTY
                                    .withColor(Formatting.BLUE)
                                    .withUnderline(true)
                                    .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, voteLinks[i]))
                            )
                    )
            );
        }
        return 1;
    }
}
