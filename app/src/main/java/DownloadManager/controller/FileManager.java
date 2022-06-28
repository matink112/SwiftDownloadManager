package DownloadManager.controller;

import DownloadManager.model.Config;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class FileManager {
    public static String sepChar = File.separator;
    private static FileManager instance;

    private FileManager(){};

    public static FileManager getInstance(){
        if (instance == null)
            instance = new FileManager();
        return instance;
    }

    public String getFileNameFromUrl(String url, String category, Config config) {
        String[] splitUrl = url.split( "/");
        String fileName =  splitUrl[splitUrl.length-1].replaceAll("%20|%30|%40|%10" ," ");

        if (!Boolean.parseBoolean(config.properties().getProperty("overwriteExistingFile"))){
            if (Files.exists(Paths.get(getSavePathBaseCategory(category, config),fileName))) {
                int i = 0;
                String[] splitFileName = fileName.split("\\.", 2);
                String newFileName;
                do {
                    i++;
                    newFileName = String.format("%s (%d).%s", splitFileName[0], i, splitFileName[1]);
                } while (Files.exists(Paths.get(getSavePathBaseCategory(category, config),newFileName)));
                fileName = newFileName;
            }
        }
        return fileName;
    }

    public String getSavePathBaseCategory(String category, Config config) {
        Properties setting = config.properties();
        if (Boolean.parseBoolean(setting.getProperty("downloadAllFileToSingleDir")))
            category = "SDM";
        return String.format("%s%s%s", setting.getProperty("downloadDir"), sepChar, category);
    }

}
