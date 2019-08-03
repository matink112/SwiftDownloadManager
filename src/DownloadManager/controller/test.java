package DownloadManager.controller;

import java.io.IOException;
import java.net.*;
import java.net.Proxy.Type;

public class test {



    public static void main(String[] args) throws IOException {


        String url ="http://dl13.f2m.co/film/Unda.2019.1080p.WEB-DL.x265.HEVC.Film2Movie_WS.mkv";


        URL url1 = new URL(url);

        HttpURLConnection connection = (HttpURLConnection) url1.openConnection();

        connection.addRequestProperty("Range","bytes=0-2000000000");

        System.out.println((connection.getResponseCode()));

        System.out.println(connection.getContentLengthLong());

        connection.disconnect();




    }



}
