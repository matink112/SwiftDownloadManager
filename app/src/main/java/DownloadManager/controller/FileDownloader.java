package DownloadManager.controller;


import DownloadManager.App;
import DownloadManager.model.Config;
import DownloadManager.model.FileModel;
import DownloadManager.model.Status;
import com.jfoenix.controls.JFXProgressBar;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.shape.Arc;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

    private FileModel fileModel;

    private Config config;

    public FileDownloader(String name, String folderPath, String url,
                          String category, long size , Arc arcProgress , Label persentlbl,
                          Label speedlbl , Label downloadedlbl, DownloadingPageController controller,
                          FileModel fileModel) {

        config = Config.getInstance();
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
        this.fileModel = fileModel;
        lastDownloadedSize = 0;

        sizeInFormat = ConfirmDownloadController.getSizeInFormat(size);

        downloaders = new ArrayList<>();

        segMentNumber = (int) Double.parseDouble(config.properties().getProperty("segmentPerDownload"));

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

                FileManager.getInstance().updateFileStatus(fileModel.getId(), Status.Finished);

                Platform.runLater(()-> controller.getStage().close());

                if(Boolean.parseBoolean(Config.getInstance().properties().getProperty("showDownloadCompleteWindow")))
                    showCompeleteDownloadPage();

                break;
            }
        }

    }



    private void showCompeleteDownloadPage(){

        FXMLLoader loader = Utils.loadFXMLPage("DownloadCompletePage");

        DownloadCompleteController completeController = loader.getController();

        Platform.runLater(()-> {
            Stage stage = new Stage();

            stage.setScene(new Scene(loader.getRoot()));

            completeController.initPage(name, folderPath, stage);

            stage.initStyle(StageStyle.UNDECORATED);

            stage.show();
        });
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

        fileModel.setDownloadedSize(downloadedSize);

    }

    private void mergeFiles(){

        createDirectoryIfDoesNotExist(folderPath , name);

        try {
            FileOutputStream fos = new FileOutputStream(new File(folderPath,
                    name) , true);


            for(SegmentDownloader a : downloaders){
                Path path = Paths.get(a.getSegmentPath(),a.getSegmentName());
                Files.copy( path, fos);
                Files.deleteIfExists(path);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        fileModel.setStatus(Status.Finished);

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

        String[] segments = FileManager.getInstance().getSegmentNames(fileModel.getId(), fileModel.getSegmentNum());

        for(int i=0 ; i< segMentNumber ; i++){

            JFXProgressBar progressBar =  new JFXProgressBar();
            controller.addProgressbar(progressBar);

            if (i==segMentNumber-1){
                eachSegmentSize = size - start;
            }

            SegmentDownloader segmentDownloader = new SegmentDownloader(this ,segments[i] ,eachSegmentSize
                    ,start ,i , url ,progressBar, config.properties().getProperty("tempDir"));

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
