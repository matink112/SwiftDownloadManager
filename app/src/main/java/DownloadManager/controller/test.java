package DownloadManager.controller;

import DownloadManager.model.Config;

import java.io.IOException;

public class test {


    public static void main(String[] args) throws IOException {

//    Desktop.getDesktop().open(new File("/home/matin",".bashrc"));

//        Files.createFile();
//        Files.createDirectories(Paths.get("~/.SDM/setting", "setting.txt"));
        Config c = Config.getInstance();
        String s = c.properties().getProperty("downloadDir");
        System.out.println(s);
        c.setAndSaveProperty("useProxyServer", "true");
        System.out.println(c.properties().getProperty("useProxyServer"));
        c.setAndSaveProperty("segmentPerDownload", "7");
        System.out.println(c.properties().getProperty("segmentPerDownload"));

    }
}
