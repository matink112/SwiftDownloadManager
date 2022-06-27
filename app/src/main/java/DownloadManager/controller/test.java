package DownloadManager.controller;

import DownloadManager.model.Config;

import java.io.IOException;

public class test {


    public static void main(String[] args) throws IOException {

//    Desktop.getDesktop().open(new File("/home/matin",".bashrc"));

//        Files.createFile();
//        Files.createDirectories(Paths.get("~/.SDM/setting", "setting.txt"));
        Config c = Config.getInstance();
        System.out.println(c.getCategories()[0]);
//        c.removeCategory("Music");
        c.addCategory("Music");
        System.out.println(c.getCategories()[0]);

    }
}
