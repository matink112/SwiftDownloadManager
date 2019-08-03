package DownloadManager.controller;


public class FileDownloader extends Thread{

    String name , folderPath , url , category;
    long size;

    public FileDownloader(String name, String folderPath, String url, String category, long size) {
        this.name = name;
        this.folderPath = folderPath;
        this.url = url;
        this.category = category;
        this.size = size;
    }


    public void run(){

    }
}
