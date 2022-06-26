package DownloadManager.controller;

import DownloadManager.model.Config;
import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.ResourceBundle;

public class OptionPageController implements Initializable {

    @FXML
    private JFXListView<?> filesInQueueList;

    @FXML
    private JFXCheckBox startQueueAtCheck;

    @FXML
    private JFXTextField proxyField;

    @FXML
    private JFXCheckBox showDownloadCompleteWindowcheck;

    @FXML
    private JFXButton moveDownbtn;

    @FXML
    private JFXTextField startQueueTimePicker;

    @FXML
    private JFXCheckBox StopQueueAtCheck;

    @FXML
    private JFXCheckBox overwriteExistingFileCheck;

    @FXML
    private JFXTextField socksField;

    @FXML
    private JFXTextField temoraryPathField;

    @FXML
    private JFXCheckBox useSocksCheck;

    @FXML
    private JFXCheckBox shutDownSystemCheck;

    @FXML
    private JFXListView<?> queuesList;

    @FXML
    private JFXButton moveupbtn;

    @FXML
    private JFXButton newQueuebtn;

    @FXML
    private JFXTextField stopQueueTimePicker;

    @FXML
    private JFXTextField proxyPasswordField;

    @FXML
    private JFXTextField defaultDownloadPathField;

    @FXML
    private JFXCheckBox priventHibernatSleepCheck;

    @FXML
    private JFXCheckBox showDownloadWindowcheck;

    @FXML
    private JFXCheckBox useProxyCheck;

    @FXML
    private JFXButton moveTobtn;

    @FXML
    private JFXCheckBox DownloadAllToSingleCheck;

    @FXML
    private JFXCheckBox launchStartUpCheck;

    @FXML
    private JFXSlider segmentPerDownloadSlider;

    @FXML
    private JFXCheckBox showTryIconCheck;

    @FXML
    private JFXButton deletQueuebtn;

    @FXML
    private JFXButton defaultDownloadFolderChangebtn;

    @FXML
    private JFXButton temporaryPathChangebtn;

    @FXML
    private JFXTextField proxyUserField;


    private Stage primaryStage;

    private Config config;
    private Properties settings;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        config = Config.getInstance();
        settings = config.properties();

        initCheckBoxes();

        initDefaultFolderPath();

        initSegmentSlider();

        initFields();

    }


    private void initDefaultFolderPath(){

        Path downloadPath = Paths.get(settings.getProperty("downloadDir"));
        if(!Files.exists(downloadPath)) {
            try {
                Files.createDirectories(downloadPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Path tempPath = Paths.get(settings.getProperty("tempDir"));
        if(!Files.exists(tempPath)) {
            try {
                Files.createDirectories(tempPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        defaultDownloadPathField.setText(settings.getProperty("downloadDir"));

        temoraryPathField.setText(settings.getProperty("tempDir"));

        defaultDownloadFolderChangebtn.setOnAction( e -> changeDownloadFolder());

        temporaryPathChangebtn.setOnAction(e -> changeTempFolder());


    }


    private void initFields(){

        proxyField.setText(settings.getProperty("proxyAddress"));

        socksField.setText(settings.getProperty("socksAddress"));

        proxyUserField.setText(settings.getProperty("proxyUsername"));

        proxyPasswordField.setText(settings.getProperty("proxyPassword"));

        startQueueTimePicker.setText(settings.getProperty("schedulerStartAtTime"));

        stopQueueTimePicker.setText(settings.getProperty("schedulerStopAtTime"));

        proxyField.textProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue.matches(".+:[\\d]+")){
                    config.setAndSaveProperty("proxyAddress", newValue);
                    proxyField.setFocusColor(Color.web("#4059a9"));

                }else {
                    proxyField.setFocusColor(Color.web("#ef153e"));
                    config.setAndSaveProperty("proxyAddress", "");
                }

        });

        socksField.textProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue.matches(".+:[\\d]+")){
                    config.setAndSaveProperty("socksAddress", newValue);
                    socksField.setFocusColor(Color.web("#4059a9"));

                }else {
                    socksField.setFocusColor(Color.web("#ef153e"));
                    config.setAndSaveProperty("socksAddress", "");
                }

        });

        startQueueTimePicker.setOnAction(event -> {
            if(!startQueueTimePicker.getText().matches("^(2[0-3]|[01]?[0-9]):([0-5]?[0-9])$")) {
                startQueueTimePicker.setText(settings.getProperty("schedulerStartAtTime"));
            } else {
                config.setAndSaveProperty("schedulerStartAtTime", startQueueTimePicker.getText());
            }
        });


        stopQueueTimePicker.setOnAction(event -> {
            if(!stopQueueTimePicker.getText().matches("^(2[0-3]|[01]?[0-9]):([0-5]?[0-9])$")) {
                stopQueueTimePicker.setText(settings.getProperty("schedulerStopAtTime"));
            } else {
                config.setAndSaveProperty("schedulerStopAtTime", stopQueueTimePicker.getText());
            }
        });

        proxyUserField.setOnAction(e -> config.setAndSaveProperty("proxyUsername", proxyUserField.getText()));
        proxyPasswordField.setOnAction(e -> config.setAndSaveProperty("proxyPassword", proxyPasswordField.getText()));


    }


    private void initSegmentSlider(){

        segmentPerDownloadSlider.setValue(Double.parseDouble(settings.getProperty("segmentPerDownload")));


        segmentPerDownloadSlider.valueProperty().addListener((observable, oldValue, newValue)
                -> config.setAndSaveProperty("segmentPerDownload", newValue.toString()));

    }


    private void changeDownloadFolder(){
        DirectoryChooser chooser = new DirectoryChooser();

        chooser.setTitle("Default Download Folder");

        chooser.setInitialDirectory(new File(settings.getProperty("downloadDir")));


        File file = null;

        try {
            file = chooser.showDialog(primaryStage);
        }catch (Exception ignored){}

        if(file != null) {
            config.setAndSaveProperty("downloadDir", file.getPath());
            defaultDownloadPathField.setText(file.getPath());
        }
    }


    private void changeTempFolder(){
        DirectoryChooser chooser = new DirectoryChooser();

        chooser.setTitle("Default Temp Folder");

        chooser.setInitialDirectory(new File(settings.getProperty("tempDir")));

        File file = null;

        try {
            file = chooser.showDialog(primaryStage);
        }catch (Exception ignored){}

        if(file != null) {
            config.setAndSaveProperty("tempDir", file.getPath());
            temoraryPathField.setText(file.getPath());
        }
    }


    private void initCheckBoxes(){

        showDownloadWindowcheck.setSelected(Boolean.parseBoolean(settings.getProperty("showDownloadProgressWindow")));
        showDownloadWindowcheck.selectedProperty().addListener((observable, oldValue, newValue)
                -> config.setAndSaveProperty("showDownloadProgressWindow", newValue.toString()));

        showDownloadCompleteWindowcheck.setSelected(Boolean.parseBoolean(settings.getProperty("showDownloadCompleteWindow")));
        showDownloadCompleteWindowcheck.selectedProperty().addListener((observable, oldValue, newValue)
                -> config.setAndSaveProperty("showDownloadCompleteWindow", newValue.toString()));

        overwriteExistingFileCheck.setSelected(Boolean.parseBoolean(settings.getProperty("overwriteExistingFile")));
        overwriteExistingFileCheck.selectedProperty().addListener((observable, oldValue, newValue)
                -> config.setAndSaveProperty("overwriteExistingFile", newValue.toString()));

        DownloadAllToSingleCheck.setSelected(Boolean.parseBoolean(settings.getProperty("downloadAllFileToSingleDir")));
        DownloadAllToSingleCheck.selectedProperty().addListener((observable, oldValue, newValue)
                -> config.setAndSaveProperty("downloadAllFileToSingleDir", newValue.toString()));

        useProxyCheck.setSelected(Boolean.parseBoolean(settings.getProperty("useProxy")));
        useProxyCheck.selectedProperty().addListener((observable, oldValue, newValue)
                -> config.setAndSaveProperty("useProxy", newValue.toString()));

        useSocksCheck.setSelected(Boolean.parseBoolean(settings.getProperty("useSocks")));
        useSocksCheck.selectedProperty().addListener((observable, oldValue, newValue)
                -> config.setAndSaveProperty("useSocks", newValue.toString()));

        startQueueAtCheck.setSelected(Boolean.parseBoolean(settings.getProperty("startQueueAt")));
        startQueueAtCheck.selectedProperty().addListener((observable, oldValue, newValue)
                -> config.setAndSaveProperty("startQueueAt", newValue.toString()));

        StopQueueAtCheck.setSelected(Boolean.parseBoolean(settings.getProperty("stopQueueAt")));
        StopQueueAtCheck.selectedProperty().addListener((observable, oldValue, newValue)
                -> config.setAndSaveProperty("stopQueueAt", newValue.toString()));

        shutDownSystemCheck.setSelected(Boolean.parseBoolean(settings.getProperty("shutdownAfterDownload")));
        shutDownSystemCheck.selectedProperty().addListener((observable, oldValue, newValue)
                -> config.setAndSaveProperty("shutdownAfterDownload", newValue.toString()));

        priventHibernatSleepCheck.setSelected(Boolean.parseBoolean(settings.getProperty("preventSleep")));
        priventHibernatSleepCheck.selectedProperty().addListener((observable, oldValue, newValue)
                -> config.setAndSaveProperty("preventSleep", newValue.toString()));

        launchStartUpCheck.setSelected(Boolean.parseBoolean(settings.getProperty("launchAtStartup")));
        launchStartUpCheck.selectedProperty().addListener((observable, oldValue, newValue)
                -> config.setAndSaveProperty("launchAtStartup", newValue.toString()));

        showTryIconCheck.setSelected(Boolean.parseBoolean(settings.getProperty("showTrayIcon")));
        showTryIconCheck.selectedProperty().addListener((observable, oldValue, newValue)
                -> config.setAndSaveProperty("showTrayIcon", newValue.toString()));


    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
