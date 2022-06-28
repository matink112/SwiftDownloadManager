package DownloadManager.model;

import DownloadManager.controller.Utils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
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
            in.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String[] getCategories() {
        Object[] catArray = categories.keySet().toArray();
        Arrays.sort(catArray);
        return Arrays.copyOf(catArray, catArray.length, String[].class);
    }

    public String getSvgIcon(String categoryName) {
        return (String)((JSONObject) categories.get(categoryName)).get("iconSvgPath");
    }

    private JSONArray getSupportedFormatJsonArray(String categoryName) {
        return (JSONArray)((JSONObject) categories.get(categoryName)).get("formats");
    }

    public String[] getSupportedFormat(String categoryName) {
        Object[] array = getSupportedFormatJsonArray(categoryName).toArray();
        return Arrays.copyOf(array, array.length, String[].class);
    }

    public void addCategory(String categoryName) {
        JSONObject newCategory = new JSONObject();
        newCategory.put("iconSvgPath", getSvgIcon("Other"));
        newCategory.put("formats", new JSONArray());
        categories.put(categoryName, newCategory);
        saveJson();
    }

    private void saveJson() {
        try {
            FileWriter out = new FileWriter(categoryDir);
            out.write(categories.toJSONString());
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        instance.readJson();
        return instance;
    }

}
