package DownloadManager.controller;


import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.shape.Arc;

public class FileDownloader extends Thread{

    String name , folderPath , url , category , sizeInFormat;
    long size;

    Label persentlbl , speedlbl , downloadedlbl;
    Arc arcProgress;

    private long downloadedSize = 0;

    public FileDownloader(String name, String folderPath, String url,
                          String category, long size , Arc arcProgress , Label persentlbl,
                          Label speedlbl , Label downloadedlbl) {
        this.name = name;
        this.folderPath = folderPath;
        this.url = url;
        this.category = category;
        this.size = size;

        this.arcProgress = arcProgress;
        this.persentlbl = persentlbl;
        this.speedlbl = speedlbl;
        this.downloadedlbl = downloadedlbl;
    }


    public void run(){

    }

    public void UpdateDownloadedSize(long size){
        downloadedSize += size;

        updateUI();
    }

    private void updateUI(){

        Platform.runLater( () -> {
            arcProgress.setLength((downloadedSize * 1.0 / size) * 360);
            persentlbl.setText((String.format(".02f", ((downloadedSize * 1.0 / size) * 100))));

            String a = ConfirmDownloadController.getSizeInFormat(downloadedSize)+" / "+sizeInFormat;

            downloadedlbl.setText(a);
        });

    }

}
