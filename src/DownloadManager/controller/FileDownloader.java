package DownloadManager.controller;


import com.jfoenix.controls.JFXProgressBar;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.shape.Arc;

import java.util.ArrayList;

public class FileDownloader extends Thread{

    String name , folderPath , url , category , sizeInFormat;
    long size;

    Label persentlbl , speedlbl , downloadedlbl;
    Arc arcProgress;

    private long downloadedSize = 0;

    private ArrayList<SegmentDownloader> downloaders;

    private int segMentNumber;

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

        downloaders = new ArrayList<>();

        segMentNumber = StaticData.getSegmentPartDownload();
    }


    public void run(){
        runSegments();

    }

    public void UpdateDownloadedSize(long size){
        downloadedSize += size;

        updateUI();
    }

    private void updateUI(){

        Platform.runLater( () -> {
            arcProgress.setLength((downloadedSize * 1.0 / size) * 360);
            persentlbl.setText((String.format("%.2f", ((downloadedSize * 1.0 / size) * 100))+"%"));

            String a = ConfirmDownloadController.getSizeInFormat(downloadedSize)+" / "+sizeInFormat;

            downloadedlbl.setText(a);
        });

    }

    private void runSegments(){


        long start = 0;
        long eachSegmentSize = size / segMentNumber;

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

}
