package DownloadManager.controller;

import javafx.fxml.FXMLLoader;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Utils {

    private static String sepChar = File.separator;

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

    public static URL getResourceLocation(String type, String name, String format) {
        return Utils.class.getResource(String.format("%s%s%s%s.%s", sepChar, type, sepChar, name, format));
    }

}
