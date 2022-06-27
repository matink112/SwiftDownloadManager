package DownloadManager.controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

public class Category {

    private static Category instance;

    private String categoryDir;
    private JSONObject categories;

    private Category() {
        categoryDir = Utils.getLocation("categories");
        try {
            Files.createDirectories(Paths.get(Utils.getLocation("base")));

            if (!Files.exists(Paths.get(Utils.getLocation("base"),"categories.json"))) {
                copyCategoryFromRecourse();
                System.out.println("copy category from resource");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readJson() {
        JSONParser parser = new JSONParser();
        try {
            FileReader in = new FileReader(categoryDir);
            categories = (JSONObject) parser.parse(in);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String[] getCategories() {
        readJson();
        Object[] catArray = categories.keySet().toArray();
        return Arrays.copyOf(catArray, catArray.length, String[].class);
    }

    private void copyCategoryFromRecourse() {
        try {
            Path s = Paths.get(Utils.getResourceLocation("config", "categories", "json").toURI());
            Path d = Paths.get(Utils.getLocation("base"), "categories.json");
            Files.copy(s,d, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Category getInstance() {
        if (instance == null)
            instance = new Category();
        return instance;
    }

}
