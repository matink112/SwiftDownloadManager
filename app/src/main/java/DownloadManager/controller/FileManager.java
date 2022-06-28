package DownloadManager.controller;

import DownloadManager.model.Config;
import DownloadManager.model.FileModel;
import DownloadManager.model.Status;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.UUID;

public class FileManager {
    public static String sepChar = File.separator;
    private static FileManager instance;

    private String filesDir;

    private JSONArray files;

    private FileManager(){
        filesDir = Utils.getLocation("files");
        try {
            Files.createDirectories(Paths.get(Utils.getLocation("base")));

            if (!Files.exists(Paths.get(Utils.getLocation("base"),"files.json")))
                createEmptyFileInfoJson();

        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    public static FileManager getInstance(){
        if (instance == null)
            instance = new FileManager();
        instance.readJson();
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

    private void createEmptyFileInfoJson() {
        files = new JSONArray();
        saveJson();
    }

    private void readJson() {
        JSONParser parser = new JSONParser();
        try {
            FileReader in = new FileReader(filesDir);
            files = (JSONArray) parser.parse(in);
            in.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void saveJson() {
        try {
            FileWriter out = new FileWriter(filesDir);
            out.write(files.toJSONString());
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void appendFileInfo(FileModel fileModel) {
        JSONObject file = new JSONObject() {{
            put("id", fileModel.getId());
            put("name", fileModel.getFileName());
            put("date", fileModel.getDate().toString());
            put("size", Long.toString(fileModel.getSize()));
            put("url", fileModel.getUrl());
            put("category", fileModel.getCategory());
            put("filePath", fileModel.getFilePath());
            put("status", fileModel.getStatus().toString());
            put("segments", Integer.toString(fileModel.getSegmentNum()));
        }};
        files.add(file);
        saveJson();
    }

}
