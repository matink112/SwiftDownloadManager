package DownloadManager.controller;

import javafx.fxml.FXMLLoader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Utils {

    private static String sepChar = File.separator;

    private static Map<String, String> fileLocations = new HashMap<String, String>() {{
        put("base", String.format("%s%s.sdm", System.getProperty("user.home"), sepChar));
        put("setting", String.format("%s%ssetting.properties", get("base"), sepChar));
        put("categories", String.format("%s%scategories.json", get("base"), sepChar));
        put("defaultDownload", String.format("%s%sDownloads", System.getProperty("user.home"), sepChar));
        put("defaultTemp", String.format("%s%stemp", get("base"), sepChar));
    }};

    public static String getLocation(String name) {
        return fileLocations.get(name);
    }

    public static URL getResourceLocation(String type, String name, String format) {
        return Utils.class.getResource(String.format("%s%s%s%s.%s", sepChar, type, sepChar, name, format));
    }

    public static FXMLLoader loadFXMLPage(String pageName) {
        URL location = Utils.getResourceLocation("fxml", pageName, "fxml");
        FXMLLoader loader = new FXMLLoader(location);

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return loader;
    }

}
