package DownloadManager.controller;


import com.jfoenix.controls.JFXProgressBar;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.shape.Arc;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileDownloader extends Thread{

    String name , folderPath , url , category , sizeInFormat;
    long size;

    Label persentlbl , speedlbl , downloadedlbl;
    Arc arcProgress;

    private long downloadedSize = 0;

    private ArrayList<SegmentDownloader> downloaders;

    private int segMentNumber;

    private long lastDownloadedSize;

    DownloadingPageController controller;

    public FileDownloader(String name, String folderPath, String url,
                          String category, long size , Arc arcProgress , Label persentlbl,
                          Label speedlbl , Label downloadedlbl, DownloadingPageController controller) {

        this.name = name;
        this.folderPath = folderPath;
        this.url = url;
        this.category = category;
        this.size = size;
        this.controller = controller;
        this.arcProgress = arcProgress;
        this.persentlbl = persentlbl;
        this.speedlbl = speedlbl;
        this.downloadedlbl = downloadedlbl;
        lastDownloadedSize = 0;

        sizeInFormat = ConfirmDownloadController.getSizeInFormat(size);

        downloaders = new ArrayList<>();

        segMentNumber = StaticData.getSegmentPartDownload();


    }

    public void addDownloadedBytes(long bytes){
        downloadedSize += bytes;
    }

    public void stopDownload(){
        for (SegmentDownloader a : downloaders)
            a.stop();

        stop();
    }

    public void run(){


        runSegments();

        while (true){

            int i=0;

            calculateSpeed();

            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (SegmentDownloader a : downloaders)
                if(a.isDownloaded())
                    i++;

            if(i==segMentNumber){
                System.out.println("All Done");
                updateUI();
                System.out.println(downloadedSize);
                mergeFiles();
                break;
            }
        }

    }




    public void UpdateDownloadedSize(long size){
        downloadedSize += size;

        updateUI();
    }

    private void calculateSpeed(){
        double speed = (downloadedSize - lastDownloadedSize)* 1.0 / 0.5;

        String a = showSpeedInFormat(speed);

        Platform.runLater(()->speedlbl.setText(a));

        lastDownloadedSize = downloadedSize;
    }


    private String showSpeedInFormat(double speed){

        if (speed < 1024)
            return speed + " Byte/s";
        else if (speed >= 1024 && speed < 1024 * 1024)
            return String.format("%.2f", (speed / (1024 * 1.0))) + " KB/s";
        else if (speed >= 1024 * 1024 && speed < Math.pow(1024, 3))
            return String.format("%.2f", speed / (Math.pow(1024, 2) * 1.0)) + " MB/s";
        else if (speed >= Math.pow(1024,3))
            return  String.format("%.2f", (speed / (Math.pow(1024, 3) * 1.0))) + " GB/s";

        return "-";
    }

    private void updateUI(){

        Platform.runLater( () -> {
            arcProgress.setLength((downloadedSize * 1.0 / size) * 360);
            persentlbl.setText((String.format("%.2f", ((downloadedSize * 1.0 / size) * 100))+"%"));

            String a = ConfirmDownloadController.getSizeInFormat(downloadedSize)+" / "+sizeInFormat;

            downloadedlbl.setText(a);
        });

    }

    private void mergeFiles(){

        createDirectoryIfDoesNotExist(folderPath , name);

        try {
            FileOutputStream fos = new FileOutputStream(new java.io.File(folderPath,
                    name) , true);


            for(SegmentDownloader a : downloaders){
                Path path = Paths.get(a.getSegmentPath(),a.getSegmentName());
                Files.copy( path, fos);
                Files.deleteIfExists(path);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void createDirectoryIfDoesNotExist(String folderPath , String name){
        if(!Files.exists(Paths.get(folderPath,name))) {
            try {
                Files.createFile(Paths.get(folderPath,name));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void runSegments(){

        long start = 0;
        long eachSegmentSize = size / segMentNumber;

        controller.deleteProgressIfExist();

        for(int i=0 ; i< segMentNumber ; i++){

            JFXProgressBar progressBar =  new JFXProgressBar();
            controller.addProgressbar(progressBar);
            progressBar.setProgress(0.3);

            if (i==segMentNumber-1){
                eachSegmentSize = size - start;
            }

            SegmentDownloader segmentDownloader = new SegmentDownloader(this ,name , eachSegmentSize
                    ,start ,i , url ,progressBar);

            downloaders.add(segmentDownloader);

            segmentDownloader.start();

            start += eachSegmentSize;
        }
    }


    public void pauseDownload(){
        for(SegmentDownloader a : downloaders) {
            try {
                a.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
