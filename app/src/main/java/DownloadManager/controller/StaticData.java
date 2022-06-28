package DownloadManager.controller;

import DownloadManager.model.FileModel;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;

public class StaticData {

    private static Stage PrimaryStage;

    private static Stage addUrlStage;

    private static TrayIcon trayIcon=null;

    private static boolean showTryIcon = true;

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
