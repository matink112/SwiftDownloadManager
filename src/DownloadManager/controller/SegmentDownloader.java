package DownloadManager.controller;

import com.jfoenix.controls.JFXProgressBar;
import javafx.application.Platform;

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
    private String name;
    private JFXProgressBar progressBar;
    private double speed;

    private int tryCunt;

    public SegmentDownloader(FileDownloader fileDownloader, String name, long segmentSize, long startByte
            , int segmentNumber, String url, JFXProgressBar progressBar) {
        this.fileDownloader = fileDownloader;
        this.segmentSize = segmentSize;
        this.startByte = startByte;
        this.segmentNumber = segmentNumber;
        this.url = url;
        this.name = name + "(" + segmentNumber + ").cache";
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

            FileOutputStream fos = new FileOutputStream(new File(segmentPath, name), true);

            long byteRead = 0;


            while (byteRead >= 0) {

                byteRead = fos.getChannel().transferFrom(rbc, 0, 1024);

                if (byteRead == -1 || byteRead == 0) {
                    break;
                }

                updateReadByte(byteRead);

            }

            if(downloaded == segmentSize)
                setDownloaded(true);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void createSegmentFile() {

        if (!Files.exists(Paths.get(segmentPath, name))) {
            try {
                Files.createDirectories(Paths.get(segmentPath, name));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {

            try {
                FileChannel file = FileChannel.open(Paths.get(segmentPath , name));
                downloaded = file.size();
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

        Platform.runLater( ()-> progressBar.setProgress( (downloaded * 1.0 / segmentSize) ));

        fileDownloader.UpdateDownloadedSize(bytes);
    }

    public boolean isDownloaded() {
        return isDownloaded;
    }

    public void setDownloaded(boolean downloaded) {
        isDownloaded = downloaded;
    }
}
