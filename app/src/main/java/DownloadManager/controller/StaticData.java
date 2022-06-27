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
        setDownloadFolderPath(System.getProperty("user.home") + File.separator + "Downloads");

        setDownloadTemporaryFolderPath(System.getProperty("user.home") + File.separator + "Downloads" +
                                        File.separator + ".temp");


        setFileModels(new ArrayList<>());
    }





    public static boolean isShowDownloadWindowPermission() {
        return showDownloadWindowPermission;
    }

    public static void setShowDownloadWindowPermission(boolean showDownloadWindowPermission) {
        StaticData.showDownloadWindowPermission = showDownloadWindowPermission;
    }

    public static boolean isShowDownloadCompeletePermission() {
        return showDownloadCompeletePermission;
    }

    public static void setShowDownloadCompeletePermission(boolean showDownloadCompeletePermission) {
        StaticData.showDownloadCompeletePermission = showDownloadCompeletePermission;
    }

    public static boolean isOverwiteExistFile() {
        return overwiteExistFile;
    }

    public static void setOverwiteExistFile(boolean overwiteExistFile) {
        StaticData.overwiteExistFile = overwiteExistFile;
    }

    public static boolean isDownloadToSingleFolder() {
        return downloadToSingleFolder;
    }

    public static void setDownloadToSingleFolder(boolean downloadToSingleFolder) {
        StaticData.downloadToSingleFolder = downloadToSingleFolder;
    }

    public static boolean isUseProxyServer() {
        return useProxyServer;
    }

    public static void setUseProxyServer(boolean useProxyServer) {
        StaticData.useProxyServer = useProxyServer;
    }

    public static boolean isUseSocksServer() {
        return useSocksServer;
    }

    public static void setUseSocksServer(boolean useSocksServer) {
        StaticData.useSocksServer = useSocksServer;
    }

    public static boolean isStartQueueCheck() {
        return startQueueCheck;
    }

    public static void setStartQueueCheck(boolean startQueueCheck) {
        StaticData.startQueueCheck = startQueueCheck;
    }

    public static boolean isStopQueueCheck() {
        return stopQueueCheck;
    }

    public static void setStopQueueCheck(boolean stopQueueCheck) {
        StaticData.stopQueueCheck = stopQueueCheck;
    }

    public static boolean isShutDownSystem() {
        return shutDownSystem;
    }

    public static void setShutDownSystem(boolean shutDownSystem) {
        StaticData.shutDownSystem = shutDownSystem;
    }

    public static boolean isPriventSleepHibernate() {
        return priventSleepHibernate;
    }

    public static void setPriventSleepHibernate(boolean priventSleepHibernate) {
        StaticData.priventSleepHibernate = priventSleepHibernate;
    }

    public static boolean isLaunchStartUp() {
        return launchStartUp;
    }

    public static void setLaunchStartUp(boolean launchStartUp) {
        StaticData.launchStartUp = launchStartUp;
    }

    public static boolean isShowTryIcon() {
        return showTryIcon;
    }

    public static void setShowTryIcon(boolean showTryIcon) {
        StaticData.showTryIcon = showTryIcon;
    }

    public static String getDownloadFolderPath() {
        return downloadFolderPath;
    }

    public static void setDownloadFolderPath(String downloadFolderPath) {
        StaticData.downloadFolderPath = downloadFolderPath;
    }

    public static String getDownloadTemporaryFolderPath() {
        return downloadTemporaryFolderPath;
    }

    public static void setDownloadTemporaryFolderPath(String downloadTemporaryFolderPath) {

        String ss;
        String[] a = downloadTemporaryFolderPath.split(File.separator);

        if(!a[a.length-1].equals(".temp")) {
            ss = downloadTemporaryFolderPath + File.separator + ".temp";
        }else
            ss = downloadTemporaryFolderPath;

        try {
            if(!Files.exists(Paths.get(ss)))
                Files.createDirectories(Paths.get(ss));
        } catch (IOException e) {
            e.printStackTrace();
        }

        StaticData.downloadTemporaryFolderPath = ss;
    }

    public static int getSegmentPartDownload() {
        return segmentPartDownload;
    }

    public static void setSegmentPartDownload(Double segmentPartDownload) {

        try {
            StaticData.segmentPartDownload = segmentPartDownload.intValue();
        }catch (Exception ignored){}
    }

    public static String getProxyHost() {
        return proxyHost;
    }

    public static void setProxyHost(String proxyHost) {
        StaticData.proxyHost = proxyHost;
    }

    public static String getSocksHost() {
        return socksHost;
    }

    public static void setSocksHost(String socksHost) {
        StaticData.socksHost = socksHost;
    }

    public static String getProxyUserName() {
        return proxyUserName;
    }

    public static void setProxyUserName(String proxyUserName) {
        StaticData.proxyUserName = proxyUserName;
    }

    public static String getProxyPass() {
        return proxyPass;
    }

    public static void setProxyPass(String proxyPass) {
        StaticData.proxyPass = proxyPass;
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
