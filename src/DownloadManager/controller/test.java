package DownloadManager.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.*;
import java.net.Proxy.Type;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class test {


    public static void main(String[] args) throws IOException {


        String url = "http://dl13.f2m.co/film/Unda.2019.1080p.WEB-DL.x265.HEVC.Film2Movie_WS.mkv";


        URL url1 = new URL(url);

        HttpURLConnection connection = (HttpURLConnection) url1.openConnection();


        ReadableByteChannel rbc = Channels.newChannel(connection.getInputStream());

        FileOutputStream fos = new FileOutputStream(new File("ss.txt"), true);

        long byteRead = 0;


        while (byteRead >= 0) {

            byteRead = fos.getChannel().transferFrom(rbc, 0, 1024);

            System.out.println(byteRead);
            if (byteRead == -1 || byteRead == 0) {
                break;
            }


        }


    }
}
