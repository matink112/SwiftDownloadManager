package DownloadManager.controller;

import DownloadManager.Main;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.html.ImageView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;

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
