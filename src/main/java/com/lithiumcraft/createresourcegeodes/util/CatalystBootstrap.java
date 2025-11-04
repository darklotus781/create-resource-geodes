package com.lithiumcraft.createresourcegeodes.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.lithiumcraft.createresourcegeodes.CreateResourceGeodes;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Handles creation and loading of the catalysts_bootstrap.json configuration.
 * This file lives in: config/createresourcegeodes/catalysts_bootstrap.json
 * and defines which catalyst blocks should be registered.
 */
public final class CatalystBootstrap {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String FILE_NAME = "catalysts_bootstrap.json";

    private CatalystBootstrap() {}

    /** Loads or creates the bootstrap list of catalyst IDs. */
    public static Set<String> blockIds(Path configDir) {
        Path file = configDir.resolve(CreateResourceGeodes.MOD_ID).resolve(FILE_NAME);

        // Default catalyst IDs — this list populates the file if missing or invalid
        List<String> defaults = List.of(
                "generic_catalyst_1", "generic_catalyst_2", "generic_catalyst_3",
                "generic_catalyst_4", "generic_catalyst_5", "generic_catalyst_6",
                "generic_catalyst_7", "generic_catalyst_8", "generic_catalyst_9",
                "generic_catalyst_10", "generic_catalyst_11", "generic_catalyst_12",
                "asurine_catalyst", "crimsite_catalyst", "ochrum_catalyst",
                "veridium_catalyst", "sky_stone_catalyst"
        );

        try {
            Files.createDirectories(file.getParent());

            if (!Files.exists(file)) {
                writeDefaults(file, defaults);
                CreateResourceGeodes.LOGGER.info("Created default catalyst bootstrap config: {}", file);
                return new LinkedHashSet<>(defaults);
            }

            try (Reader reader = Files.newBufferedReader(file)) {
                JsonElement root = GSON.fromJson(reader, JsonElement.class);
                if (root == null || !root.isJsonArray()) {
                    CreateResourceGeodes.LOGGER.warn("Catalyst bootstrap file invalid; rewriting defaults.");
                    writeDefaults(file, defaults);
                    return new LinkedHashSet<>(defaults);
                }

                Set<String> result = new LinkedHashSet<>();
                for (JsonElement e : root.getAsJsonArray()) {
                    if (e.isJsonPrimitive() && e.getAsJsonPrimitive().isString()) {
                        String id = e.getAsString().trim();
                        if (!id.isEmpty()) result.add(id);
                    }
                }

                if (result.isEmpty()) {
                    CreateResourceGeodes.LOGGER.warn("Catalyst bootstrap list empty; rewriting defaults.");
                    writeDefaults(file, defaults);
                    result.addAll(defaults);
                }

                return result;
            }

        } catch (IOException e) {
            CreateResourceGeodes.LOGGER.error("Failed to load catalyst bootstrap file: {}", e.toString());
            return new LinkedHashSet<>(defaults);
        }
    }

    private static void writeDefaults(Path file, List<String> defaults) throws IOException {
        JsonArray arr = new JsonArray();
        defaults.forEach(arr::add);
        try (Writer writer = Files.newBufferedWriter(file)) {
            GSON.toJson(arr, writer);
        }
    }
}
