package DownloadManager.controller;

import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StaticData {

    private static Stage PrimaryStage;

    private static Stage addUrlStage;

    private static TrayIcon trayIcon=null;

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



    private static String musicIcon="M14,2H6A2,2 0 0,0 4,4V20A2,2 0 0,0 6,22H18A2,2 0 0,0 20,20V8L14,2M13,13H11V18A2,2" +
            " 0 0,1 9,20A2,2 0 0,1 7,18A2,2 0 0,1 9,16C9.4,16 9.7,16.1 10,16.3V11H13V13M13,9V3.5L18.5,9H13Z";


    private static String movieIcon = "M18,9H16V7H18M18,13H16V11H18M18,17H16V15H18M8,9H6V7H8M8,13H6V11H8M8," +
            "17H6V15H8M18,3V5H16V3H8V5H6V3H4V21H6V19H8V21H16V19H18V21H20V3H18Z";


    private static String compressIcon = "M3,3H21V7H3V3M4,8H20V21H4V8M9.5,11A0.5,0.5 0 0,0 9,11.5" +
            "V13H15V11.5A0.5,0.5 0 0,0 14.5,11H9.5Z";


    private static String programIcon = "M2,10.96C1.5,10.68 1.35,10.07 1.63,9.59L3.13,7C3.24,6.8 3.41,6.66" +
            " 3.6,6.58L11.43,2.18C11.59,2.06 11.79,2 12,2C12.21,2 12.41,2.06 12.57,2.18L20.47,6.62C2" +
            "0.66,6.72 20.82,6.88 20.91,7.08L22.36,9.6C22.64,10.08 22.47,10.69 22,10.96L21,11.54V16.5C2" +
            "1,16.88 20.79,17.21 20.47,17.38L12.57,21.82C12.41,21.94 12.21,22 12,22C11.79,22 11.59,21" +
            ".94 11.43,21.82L3.53,17.38C3.21,17.21 3,16.88 3,16.5V10.96C2.7,11.13 2.32,11.14 2,10.96M" +
            "12,4.15V4.15L12,10.85V10.85L17.96,7.5L12,4.15M5,15.91L11,19.29V12.58L5,9.21V15.91M19,15." +
            "91V12.69L14,15.59C13.67,15.77 13.3,15.76 13,15.6V19.29L19,15.91M13.85,13.36L20.13,9.73L19." +
            "55,8.72L13.27,12.35L13.85,13.36Z";


    private static String documentIcon ="M6,2A2,2 0 0,0 4,4V20A2,2 0 0,0 6,22H18A2,2 0 0,0 20,20V8L14," +
            "2H6M6,4H13V9H18V20H6V4M8,12V14H16V12H8M8,16V18H13V16H8Z";


    private static String otherIcon = "M10,18H8V16H10V18M10,14H8V9H10V14M14,2H6A2,2 0 0,0 4,4V20A2,2 0 0," +
            "0 6,22H18A2,2 0 0,0 20,20V8L14,2M18,20H6V4H13V9H18V20Z";







    private static String[] compressFormates = {"7z","ace","s7z","afa"
            ,"alz", "arc","ark","b6z","car","cfs","dd","dgc","pit","rar"
            ,"rk","tar","gz","Z","bz2","lzma","txz","xz"};


    private static String[] musicFormates= { "3gp","aa","aac","m4a","m4b","m4p","mp3","mpc","ogg",
            "oga","wav","wma","wv"};

    private static String[] movieFormates ={"mkv","flv","vob","ogv","ogg","gif","gifv","mng","avi"
            ,"wmv","rm","amv","mp4","mpg","m2v","m4v","3gp","flv","f4v","f4p","f4b"};

    private static String[] applicationFormates = {"apk","appx","deb","ebuild","pisi","pkg","rpm","snap"};


    private String[] documentFormates = {"txt","doc","xml","html","htm","docx","odt","pdf"
            ,"ps","svg","xls","xlsm","pptx","ppt","xps","potx","ppa","jpg","png","odp"};







    static {
        setDownloadFolderPath(System.getProperty("user.home") + File.separator + "Downloads");

        setDownloadTemporaryFolderPath(System.getProperty("user.home") + File.separator + "Downloads" +
                                        File.separator + ".temp");

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
}
