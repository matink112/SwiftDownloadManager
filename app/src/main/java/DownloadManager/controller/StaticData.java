package DownloadManager.controller;

import DownloadManager.model.Category;
import DownloadManager.model.FileModel;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class StaticData {

    private static Stage PrimaryStage;

    private static Stage addUrlStage;

    private static TrayIcon trayIcon=null;

    public static String configDir;

    private static boolean showDownloadWindowPermission = true;

    private static boolean showDownloadCompeletePermission = true;

    private static boolean overwiteExistFile = false;

    private static boolean downloadToSingleFolder = false;

    private static boolean useProxyServer = false;

    private static boolean useSocksServer = false;

    private static boolean startQueueCheck = false;

    private static boolean stopQueueCheck = false;

    private static boolean shutDownSystem = false;

    private static boolean priventSleepHibernate = false;

    private static boolean launchStartUp = true;

    private static boolean showTryIcon = true;

    private static String downloadFolderPath;

    private static String downloadTemporaryFolderPath;

    private static int segmentPartDownload = 4;

    private static String proxyHost;

    private static String socksHost;

    private static String proxyUserName;

    private static String proxyPass;

    private static MainViewController mainController;

    private static ArrayList<FileModel> fileModels;


    static {
        setFileModels(new ArrayList<>());
    }

    public static boolean isShowTryIcon() {
        return showTryIcon;
    }

    public static Stage getPrimaryStage() {
        return PrimaryStage;
    }

    public static void setPrimaryStage(Stage primaryStage) {
        PrimaryStage = primaryStage;
    }

    public static TrayIcon getTrayIcon() {
        return trayIcon;
    }

    public static void setTrayIcon(TrayIcon trayIcon) {
        StaticData.trayIcon = trayIcon;
    }

    public static Stage getAddUrlStage() {
        return addUrlStage;
    }

    public static void setAddUrlStage(Stage addUrlStage) {
        StaticData.addUrlStage = addUrlStage;
    }

    public static MainViewController getMainController() {
        return mainController;
    }

    public static void setMainController(MainViewController mainController) {
        StaticData.mainController = mainController;
    }

    public static ArrayList<FileModel> getFileModels() {
        return fileModels;
    }

    public static void setFileModels(ArrayList<FileModel> fileModels) {
        StaticData.fileModels = fileModels;
    }
}
