package DownloadManager.controller;

import javafx.fxml.FXMLLoader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

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

    public static String getBaseDirectoryPath() {
        return String.format("%s%s.sdm", System.getProperty("user.home"), sepChar);
    }

    public static void createBaseFileIfDoesNotExists() {

        try {
            Files.createDirectories(Paths.get(getBaseDirectoryPath()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getSettingLocation() {
        return String.format("%s%ssetting.properties", getBaseDirectoryPath(), sepChar);
    }

    public static String getDefaultDownloadDir() {
        return String.format("%s%sDownloads", System.getProperty("user.home"), sepChar);
    }

    public static String getDefaultTempDir() {
        return String.format("%s%stemp", getBaseDirectoryPath(), sepChar);
    }



}
