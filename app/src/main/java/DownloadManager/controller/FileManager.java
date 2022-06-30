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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
            if (isNameAlreadyTaken(fileName, category, config)) {
                int i = 0;
                String[] splitFileName = fileName.split("\\.", 2);
                String newFileName;
                do {
                    i++;
                    newFileName = String.format("%s (%d).%s", splitFileName[0], i, splitFileName[1]);
                } while (isNameAlreadyTaken(newFileName, category, config));
                fileName = newFileName;
            }
        }
        return fileName;
    }

    public boolean isNameAlreadyTaken(String fileName, String category, Config config){
        return Files.exists(Paths.get(getSavePathBaseCategory(category, config),fileName))
                || isFileExistsInJson(fileName);
    }

    private boolean isFileExistsInJson(String fileName) {
        for (Object o: files.toArray()) {
            JSONObject obj = (JSONObject) o;
            if (((String) obj.get("name")).equals(fileName))
                return true;
        }
        return false;
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
            put("tempDir", fileModel.getTempDir());
            put("status", fileModel.getStatus().toString());
            put("segments", Integer.toString(fileModel.getSegmentNum()));
        }};
        files.add(file);
        saveJson();
    }

    public ArrayList<FileModel> getFilesModel() {
        ArrayList<FileModel> fileModels = new ArrayList<>();
        for (Object o: files.toArray()){
            try {
                fileModels.add(FileModel.parseJson((JSONObject) o, this));
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return fileModels;
    }

    public void updateFileStatus(String id, Status status) {
        for (Object o : files.toArray()){
            JSONObject obj = (JSONObject) o;
            if (((String) obj.get("id")).equals(id)){
                obj.put("status", status.toString());
                saveJson();
                break;
            }
        }
    }

    public long incompleteDownloadedSize(String fileName, String tempDir, int segmentNumber) throws IOException {
        System.out.println("calc");
        long total = 0;
        for (String name: getSegmentNames(fileName, segmentNumber))
            total += Files.size(Paths.get(tempDir, name));
        return total;
    }

    public String[] getSegmentNames(String uuid, int segmentNumber) {
        String[] temps = new String[segmentNumber];
        for (int i=0; i < segmentNumber; i++)
            temps[i] = String.format("%s(%d).cache", uuid, i);
        return temps;
    }

}
