package me.skizzme.cc.util;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.server.command.ServerCommandSource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CommandUtils {

    public static Suggests suggestions(String[] list) {
        return new Suggests(list);
    }

    public static class Suggests {

        private final ArrayList<String> suggestions = new ArrayList<>();

        public Suggests(String[] list) {
            add(list);
        }

        public void add(String[] list) {
            suggestions.addAll(List.of(list));
        }

        public CompletableFuture<Suggestions> build(CommandContext<ServerCommandSource> ctx, SuggestionsBuilder builder) {
            for (String s : suggestions) {
                builder.suggest(s);
            }
            return builder.buildFuture();
        }

    }

}
