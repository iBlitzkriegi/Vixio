package me.iblitzkriegi.vixio.registration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import ch.njol.util.Pair;
import me.iblitzkriegi.vixio.Vixio;

/**
 * Created by Blitz on 7/22/2017.
 */
public final class Documentation {
    public static void generateDocs() {
        try {
            Pair<Gson, JsonObject> jsonData = generateJson();
            Files.write(
                    new File(Vixio.getInstance().getDataFolder(), "syntax.json").toPath(),
                    jsonData.getFirst().toJson(jsonData.getSecond()).getBytes()
            );
        } catch (IOException e) {
            Vixio.getInstance().getLogger().warning("Could not save syntax.json file!");
            e.printStackTrace();
        }
    }

    private static Pair<Gson, JsonObject> generateJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonObject root = new JsonObject();
        root.addProperty("addonVersion", Vixio.getInstance().getDescription().getVersion());
        root.addProperty("jsonVersion", "2"); // increment this if some change is made to the format

        Map<String, JsonArray> categories = new HashMap<>();
        Vixio.getInstance().syntaxElements.forEach(r -> {
            r.update();
            categories
                    .computeIfAbsent(r.getCategory(), k -> new JsonArray())
                    .add(gson.toJsonTree(r));
        });

        JsonArray categoriesArray = new JsonArray();
        categories.forEach((name, syntaxArray) -> {
            JsonObject category = new JsonObject();

            category.addProperty("name", name);
            category.add("syntaxElements", syntaxArray);

            categoriesArray.add(category);
        });
        root.add("categories", categoriesArray);

        return new Pair<>(gson, root);
    }

}
