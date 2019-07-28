package DownloadManager.model;

import java.util.Date;

public class DownloadFile {

    private String url;
    private String format;
    private String name;
    private double byteSize;
    private double MBSize;
    private Status status;
    private Date date;


    public DownloadFile(String url, double MBSize, Status status, Date date) {

        this.url = url;
        this.MBSize = MBSize;
        this.status = status;
        this.date = date;

    }
}
