package me.skizzme.cc.util;

import com.google.gson.JsonObject;
import me.skizzme.cc.CCCore;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigUtils {

    public static Path configDir() {
        return FabricLoader.getInstance().getConfigDir().resolve("cc-core");
    }

    public static void writeFile(String path, JsonObject contents) {
        Path p = configDir().resolve(path);
        try {
            if (!Files.exists(p.getParent())) {
                Files.createDirectories(p.getParent());
            }
            Files.writeString(p, CCCore.GSON.toJson(contents));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static JsonObject readFileThrowing(String path) throws IOException {
        Path p = configDir().resolve(path);
        return CCCore.GSON.fromJson(Files.readString(p), JsonObject.class);
    }

    public static JsonObject readFile(String path) {
        Path p = configDir().resolve(path);
        if (!Files.exists(p)) {
            writeFile(path, new JsonObject());
            return new JsonObject();
        }
        try {
            return CCCore.GSON.fromJson(Files.readString(p), JsonObject.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JsonObject();
    }

}
