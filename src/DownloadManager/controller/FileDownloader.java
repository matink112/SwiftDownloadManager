package DownloadManager.controller;


import javafx.scene.control.Label;
import javafx.scene.shape.Arc;

public class FileDownloader extends Thread{

    String name , folderPath , url , category;
    long size;

    Label persentlbl , speedlbl;
    Arc arcProgress;

    private long downloadedSize = 0;

    public FileDownloader(String name, String folderPath, String url,
                          String category, long size , Arc arcProgress , Label persentlbl, Label speedlbl) {
        this.name = name;
        this.folderPath = folderPath;
        this.url = url;
        this.category = category;
        this.size = size;

        this.arcProgress = arcProgress;
        this.persentlbl = persentlbl;
        this.speedlbl = speedlbl;
    }


    public void run(){

    }

    public void UpdateDownloadedSize(long size){
        downloadedSize += size;

        updateUI();
    }

    private void updateUI(){



    }

}
