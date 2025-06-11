package me.skizzme.cc.chunkgen;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.skizzme.cc.CCCore;
import me.skizzme.cc.util.ConfigUtils;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkStatus;

import java.io.IOException;
import java.util.ArrayList;

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
                    int chunkX = state.radius, chunkZ = state.radius;
                    for (int[] dir : dirs) {
                        for (int i = 0; i < state.radius * 2; i++) {

                            int finalChunkX = chunkX;
                            int finalChunkZ = chunkZ;
                            long sleepTime = (long) (Math.pow(server.getCurrentPlayerCount() * playerMult(), playerExponential()) * sleepMult() + baseSleep());

//							server.execute(() -> {
                            w.getChunk(finalChunkX, finalChunkZ, ChunkStatus.FULL, true);
//                            System.out.println("Get chunk at " + finalChunkX + ", " + finalChunkZ + " (" + sleepTime + ")");
                            TOTAL_PRE_GEN++;
//							});

                            try {
                                Thread.sleep(sleepTime);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            chunkX += dir[0];
                            chunkZ += dir[1];
                            if (!server.isRunning()) break;
                        }
                        if (!server.isRunning()) break;
                    }
                    if (state.radius > borderSizeChunks + 16) {
                        break;
                    }
                    state.radius++;
                    state.markDirty();
                }
            });
            t.setName("chunk-generator-" + w.getRegistryKey().getValue().toString());
            t.start();
        });
    }

    public static class ChunkGenState extends PersistentState {
        public int radius = 1;

        public static PersistentState.Type<ChunkGenState> persistentType() {
            return new Type<>(ChunkGenState::createNew, ChunkGenState::createFromNbt, null);
        }

        public static ChunkGenState createNew() {
            System.out.println("created nbt radius");
            return new ChunkGenState();
        }

        public static ChunkGenState createFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
            ChunkGenState s = new ChunkGenState();
            s.radius = tag.getInt("radius");
            System.out.println("loaded nbt radius " + s.radius);
            return s;
        }

        @Override
        public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
            System.out.println("saved nbt radius " + this.radius);
            nbt.putInt("radius", this.radius);
            return nbt;
        }
    }
}
