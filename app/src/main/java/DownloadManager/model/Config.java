package DownloadManager.model;

import DownloadManager.controller.Utils;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class Config {

    private static Config instance = null;

    private Properties settingProperties;

    private String settingDir;

    private Config() {
        settingProperties = new Properties();
        settingDir = Utils.getSettingLocation();
        try {
            Files.createDirectories(Paths.get(Utils.getBaseDirectoryPath()));

            if (!Files.exists(Paths.get(Utils.getBaseDirectoryPath(),"setting.properties"))) {
                saveSettingFromResource(settingDir);
                System.out.println("save setting from resource");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Config getInstance() {
        if (instance == null)
            instance = new Config();
        return instance;
    }

    public Properties properties() {
        try {
            FileInputStream in = new FileInputStream(settingDir);
            settingProperties.load(in);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return settingProperties;
    }

    public void setAndSaveProperty(String key, String value) {
        try {
            FileOutputStream out = new FileOutputStream(settingDir);
            settingProperties.setProperty(key, value);
            settingProperties.store(out, "Updated setting");
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void saveSettingFromResource(String path) {
        Properties setting = new Properties();
        URL settingLocation = Utils.getResourceLocation("config", "setting", "properties");

        try{
            FileInputStream in = new FileInputStream(settingLocation.getFile());
            FileOutputStream out = new FileOutputStream(path);

            setting.load(in);

            setting.setProperty("downloadDir", Utils.getDefaultDownloadDir());
            setting.setProperty("tempDir", Utils.getDefaultTempDir());

            setting.store(out, "default Settings");

            in.close();
            out.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
