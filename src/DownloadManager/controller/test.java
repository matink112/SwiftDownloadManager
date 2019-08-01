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
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;

public class test {



    public static void main(String[] args) throws IOException {

        TrayIconSystem trayIconSystem = new TrayIconSystem();

        trayIconSystem.addDownloderToTryIcon(null , "test");

    }

}
