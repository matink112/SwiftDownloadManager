package DownloadManager.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class test {



    public static void main(String[] args) throws IOException {

        String[] a = "https://docs.oracle.com".split("/");

        if(a.length>3 && a[a.length-1].matches(".*[.].+")) {
            System.out.println("t");
        }




    }


    private static boolean checkUrl(String url){
        try {
            URI url1 = new URL(url).toURI();
        } catch (MalformedURLException e) {
            return false;
        } catch (URISyntaxException e) {
            return false;
        }

        return true;
    }

}
