package me.skizzme.cc.chunkgen;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.skizzme.cc.CCCore;
import me.skizzme.cc.stats.Statistics;
import me.skizzme.cc.util.ConfigUtils;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ChunkGenerator {

    private static JsonObject config = new JsonObject();
//    private static JsonObject info = new JsonObject();
    public static long TOTAL_PRE_GEN = 0;

    public static void load(MinecraftServer server) {
        try {
            config = ConfigUtils.readFileThrowing("chunkgen.json");
        } catch (IOException e) {
            config.addProperty("base_sleep", 25);
            config.addProperty("sleep_mult", 36);
            config.addProperty("player_mult", 45.4);
            config.addProperty("player_exponential", 0.474);

            JsonArray worlds = new JsonArray();
            for (World w : server.getWorlds()) {
                worlds.add(w.getRegistryKey().getValue().toString());
            }
            config.add("worlds", worlds);

            save(server);
        }
    }

    public static void save(MinecraftServer server) {
        ConfigUtils.writeFile("chunkgen.json", config);
    }

    public static float baseSleep() {
        return config.get("base_sleep").getAsFloat();
    }

    public static float playerMult() {
        return config.get("player_mult").getAsFloat();
    }

    public static float sleepMult() {
        return config.get("sleep_mult").getAsFloat();
    }

    public static float playerExponential() {
        return config.get("player_exponential").getAsFloat();
    }

    public static ArrayList<String> worlds() {
        ArrayList<String> worlds = new ArrayList<>();
        for (JsonElement e : config.get("worlds").getAsJsonArray()) {
            worlds.add(e.getAsString());
        }
        return worlds;
    }

    public static void chunkLoader(MinecraftServer server) {
        server.getWorlds().forEach(w -> {
            double borderSizeChunks = w.getWorldBorder().getSize() / 16;

            boolean validWorld = false;
            for (String wid : worlds()) {
                if (wid.equals(w.getRegistryKey().getValue().toString())) {
                    validWorld = true;
                    break;
                }
            }

            if (!validWorld) return;

            ChunkGenState state = w.getPersistentStateManager().getOrCreate(ChunkGenState.persistentType(), CCCore.MOD_ID);
            state.markDirty();

            Thread t = new Thread(() -> {
                int[][] dirs = {{-1, 0}, {0, -1}, {1, 0}, {0, 1}};
                while (server.isRunning()) {
                    int[] dir = dirs[state.dir];
                    for (int i = 0; i < state.radius * 2; i++) {

                        long sleepTime = (long) (Math.pow(server.getCurrentPlayerCount() * playerMult(), playerExponential()) * sleepMult() + baseSleep());

                        w.getChunk(state.chunkX, state.chunkZ, ChunkStatus.FULL, true);
                        Statistics.updateStat("totalChunkPreGen", 1.0);

                        try {
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        state.chunkX += dir[0];
                        state.chunkZ += dir[1];
                        state.markDirty();
                        if (!server.isRunning() || !state.enabled) break;
                    }

                    state.dir++;
                    if (state.dir >= dirs.length) {
                        if (state.radius > borderSizeChunks + 16) {
                            break;
                        }
                        state.radius++;
                        state.chunkX = state.radius;
                        state.chunkZ = state.radius;
                        state.dir = 0;
                    }

                    if (!server.isRunning() || !state.enabled) break;
                }
            });
            t.setName("chunk-generator-" + w.getRegistryKey().getValue().toString());
            t.start();
        });
    }

    public static class ChunkGenState extends PersistentState {
        public int radius = 1, chunkX = 1, chunkZ = 1;
        public int dir = 0;
        public boolean enabled = true;

        public static PersistentState.Type<ChunkGenState> persistentType() {
            return new Type<>(ChunkGenState::createNew, ChunkGenState::createFromNbt, null);
        }

        public static ChunkGenState createNew() {
            return new ChunkGenState();
        }

        public static ChunkGenState createFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
            ChunkGenState s = new ChunkGenState();

            s.radius = tag.getInt("radius");
            s.chunkX = tag.getInt("chunkX");
            s.chunkZ = tag.getInt("chunkZ");
            s.dir = tag.getInt("dir");
            s.enabled = tag.getBoolean("enabled");

            return s;
        }

        @Override
        public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
            nbt.putInt("radius", this.radius);
            nbt.putInt("chunkX", this.chunkX);
            nbt.putInt("chunkZ", this.chunkZ);
            nbt.putInt("dir", this.dir);
            nbt.putBoolean("enabled", this.enabled);

            return nbt;
        }
    }
}
