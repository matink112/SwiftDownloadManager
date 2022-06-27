package DownloadManager.controller;

import DownloadManager.model.Config;
import com.jfoenix.controls.JFXProgressBar;
import javafx.application.Platform;

import javax.net.ssl.SSLException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SegmentDownloader extends Thread {

    private FileDownloader fileDownloader;
    private long segmentSize;
    private long downloaded = 0;
    private long startByte;
    private int segmentNumber;
    private String url;
    private boolean isDownloaded = false;
    private String segmentPath;
    private String segmentName;

    private JFXProgressBar progressBar;
    private double speed;

    private boolean isStopDownload = false;

    private int tryCunt;

    public SegmentDownloader(FileDownloader fileDownloader, String name, long segmentSize, long startByte
            , int segmentNumber, String url, JFXProgressBar progressBar, String tempPath) {
        segmentPath = tempPath;
        this.fileDownloader = fileDownloader;
        this.segmentSize = segmentSize;
        this.startByte = startByte;
        this.segmentNumber = segmentNumber;
        this.url = url;
        this.setSegmentName(name + "(" + segmentNumber + ").cache");
        this.progressBar = progressBar;
    }


    public void run() {

        while (true) {
            createSegmentFile();

            downloadSegment();

            if (isDownloaded()) {
                System.out.println("done");
                break;
            }
            tryCunt++;

            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (tryCunt >= 10)
                break;
        }

    }

    private void downloadSegment() {
        URL urlConnection;
        HttpURLConnection connection;

        long start = startByte + downloaded;
        long end = startByte + segmentSize - 1;

        try {
            urlConnection = new URL(this.url);
        } catch (MalformedURLException e) {
            return;
        }

        Proxy proxy = ConfirmDownloadController.getProxy();

        try {
            if (proxy != null)
                connection = (HttpURLConnection) urlConnection.openConnection(proxy);
            else
                connection = (HttpURLConnection) urlConnection.openConnection();


            connection.addRequestProperty("RANGE", "bytes=" + start + "-" + end);

            System.out.println(connection.getResponseCode());

            ReadableByteChannel rbc = Channels.newChannel(connection.getInputStream());

            FileOutputStream fos = new FileOutputStream(new File(segmentPath, getSegmentName()), true);

            long byteRead = 0;


            while (byteRead >= 0) {

                if(downloaded == segmentSize)
                    break;

                byteRead = fos.getChannel().transferFrom(rbc, 0, 1024);



                if (byteRead == -1 || byteRead == 0) {
                    break;
                }

                //System.out.println(segmentNumber);

                updateReadByte(byteRead);

            }

            //updateUI(0);

            if(downloaded == segmentSize)
                setDownloaded(true);

        }catch (SSLException ignored){

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void createSegmentFile() {

        if (!Files.exists(Paths.get(segmentPath, getSegmentName()))) {
            try {
                Files.createFile(Paths.get(segmentPath, getSegmentName()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {

            try {
                FileChannel file = FileChannel.open(Paths.get(segmentPath , getSegmentName()));
                downloaded = file.size();
                fileDownloader.addDownloadedBytes(downloaded);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void updateReadByte(long readByte){

        downloaded+=readByte;

        updateUI(readByte);
    }


    private void updateUI(long bytes){

        Platform.runLater( ()-> getProgressBar().setProgress( (getDownloaded() * 1.0 / getSegmentSize()) ));

        getFileDownloader().UpdateDownloadedSize(bytes);
    }

    public boolean isDownloaded() {
        return isDownloaded;
    }

    public void setDownloaded(boolean downloaded) {
        isDownloaded = downloaded;
    }

    public FileDownloader getFileDownloader() {
        return fileDownloader;
    }

    public void setFileDownloader(FileDownloader fileDownloader) {
        this.fileDownloader = fileDownloader;
    }

    public long getSegmentSize() {
        return segmentSize;
    }

    public void setSegmentSize(long segmentSize) {
        this.segmentSize = segmentSize;
    }

    public long getDownloaded() {
        return downloaded;
    }

    public void setDownloaded(long downloaded) {
        this.downloaded = downloaded;
    }

    public long getStartByte() {
        return startByte;
    }

    public void setStartByte(long startByte) {
        this.startByte = startByte;
    }

    public int getSegmentNumber() {
        return segmentNumber;
    }

    public void setSegmentNumber(int segmentNumber) {
        this.segmentNumber = segmentNumber;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSegmentPath() {
        return segmentPath;
    }

    public void setSegmentPath(String segmentPath) {
        this.segmentPath = segmentPath;
    }


    public JFXProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(JFXProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getTryCunt() {
        return tryCunt;
    }

    public void setTryCunt(int tryCunt) {
        this.tryCunt = tryCunt;
    }

    public String getSegmentName() {
        return segmentName;
    }

    public void setSegmentName(String segmentName) {
        this.segmentName = segmentName;
    }

    public boolean isStopDownload() {
        return isStopDownload;
    }

    public void setStopDownload(boolean stopDownload) {
        isStopDownload = stopDownload;
    }
}
