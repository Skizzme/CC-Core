package me.skizzme.cc.util;

import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileUtils {

    public static Path getModFolder() {
        return FabricLoader.getInstance().getGameDir().resolve("mods").resolve(CCCore.MOD_ID);
    }

    public static void writeFile(String path, JsonObject contents) {
        Path p = getModFolder().resolve(path);
        try {
            if (!Files.exists(p.getParent())) {
                Files.createDirectories(p.getParent());
            }
            Files.writeString(p, CCCore.GSON.toJson(contents));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void appendFile(String path, String append) {
        Path p = getModFolder().resolve(path);
        try {
            if (!Files.exists(p.getParent())) {
                Files.createDirectories(p.getParent());
            }
            if (!Files.exists(p)) {
                Files.createFile(p);
            }
            Files.writeString(p, append, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JsonObject readFileThrowing(String path) throws IOException {
        Path p = getModFolder().resolve(path);
        return CCCore.GSON.fromJson(Files.readString(p), JsonObject.class);
    }

    public static JsonObject readFile(String path) {
        Path p = getModFolder().resolve(path);
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
