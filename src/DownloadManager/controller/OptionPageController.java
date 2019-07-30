package DownloadManager.controller;

import com.jfoenix.controls.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initCheckBoxes();

        initDefaultFolderPath();

        initSegmentSlider();

        initFields();

    }


    private void initDefaultFolderPath(){

        if(!Files.exists(Paths.get(StaticData.getDownloadFolderPath()))) {
            try {
                Files.createDirectories(Paths.get(StaticData.getDownloadFolderPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(!Files.exists(Paths.get(StaticData.getDownloadTemporaryFolderPath()))) {
            try {
                Files.createDirectories(Paths.get(StaticData.getDownloadTemporaryFolderPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        defaultDownloadPathField.setText(StaticData.getDownloadFolderPath());

        temoraryPathField.setText(StaticData.getDownloadTemporaryFolderPath());

        defaultDownloadFolderChangebtn.setOnAction( e -> changeDownloadFolder());

        temporaryPathChangebtn.setOnAction(e -> changeTempFolder());


    }


    private void initFields(){

        proxyField.setText(StaticData.getProxyHost());

        socksField.setText(StaticData.getSocksHost());

        proxyUserField.setText(StaticData.getProxyUserName());

        proxyPasswordField.setText(StaticData.getProxyPass());


        proxyField.setOnAction( e -> StaticData.setProxyHost(proxyField.getText()));

        socksField.setOnAction( e -> StaticData.setSocksHost(socksField.getText()));

        proxyUserField.setOnAction( e -> StaticData.setProxyUserName(proxyUserField.getText()));

        proxyPasswordField.setOnAction( e -> StaticData.setProxyPass(proxyPasswordField.getText()));


        startQueueTimePicker.setOnAction(event -> {
            if(!startQueueTimePicker.getText().matches("^(2[0-3]|[01]?[0-9]):([0-5]?[0-9])$")) {
                startQueueTimePicker.setText("00:00");
            }
        });


        stopQueueTimePicker.setOnAction(event -> {
            if(!stopQueueTimePicker.getText().matches("^(2[0-3]|[01]?[0-9]):([0-5]?[0-9])$")) {
                stopQueueTimePicker.setText("00:00");
            }
        });



    }


    private void initSegmentSlider(){

        segmentPerDownloadSlider.setValue(StaticData.getSegmentPartDownload());


        segmentPerDownloadSlider.valueProperty().addListener((observable, oldValue, newValue)
                -> StaticData.setSegmentPartDownload((Double) newValue));

    }


    private void changeDownloadFolder(){
        DirectoryChooser chooser = new DirectoryChooser();

        chooser.setTitle("Default Download Folder");

        chooser.setInitialDirectory(new File(StaticData.getDownloadFolderPath()));


        File file = null;

        try {
            file = chooser.showDialog(primaryStage);
        }catch (Exception ignored){}

        if(file != null) {
            StaticData.setDownloadFolderPath(file.getPath());

            defaultDownloadPathField.setText(StaticData.getDownloadFolderPath());
        }
    }


    private void changeTempFolder(){
        DirectoryChooser chooser = new DirectoryChooser();

        chooser.setTitle("Default Temp Folder");

        chooser.setInitialDirectory(new File(StaticData.getDownloadTemporaryFolderPath()));

        File file = null;

        try {
            file = chooser.showDialog(primaryStage);
        }catch (Exception ignored){}

        if(file != null) {
            StaticData.setDownloadTemporaryFolderPath(file.getPath());

            temoraryPathField.setText(StaticData.getDownloadTemporaryFolderPath());
        }
    }


    private void initCheckBoxes(){

        showDownloadWindowcheck.selectedProperty().addListener((observable, oldValue, newValue)
                -> StaticData.setShowDownloadWindowPermission(newValue));

        showDownloadCompleteWindowcheck.selectedProperty().addListener((observable, oldValue, newValue)
                -> StaticData.setShowDownloadCompeletePermission(newValue));

        overwriteExistingFileCheck.selectedProperty().addListener((observable, oldValue, newValue)
                -> StaticData.setOverwiteExistFile(newValue));

        DownloadAllToSingleCheck.selectedProperty().addListener((observable, oldValue, newValue)
                -> StaticData.setDownloadToSingleFolder(newValue));

        useProxyCheck.selectedProperty().addListener((observable, oldValue, newValue)
                -> StaticData.setUseProxyServer(newValue));

        useSocksCheck.selectedProperty().addListener((observable, oldValue, newValue)
                -> StaticData.setUseSocksServer(newValue));

        startQueueAtCheck.selectedProperty().addListener((observable, oldValue, newValue)
                -> StaticData.setStartQueueCheck(newValue));

        StopQueueAtCheck.selectedProperty().addListener((observable, oldValue, newValue)
                -> StaticData.setStopQueueCheck(newValue));

        shutDownSystemCheck.selectedProperty().addListener((observable, oldValue, newValue)
                -> StaticData.setShutDownSystem(newValue));

        priventHibernatSleepCheck.selectedProperty().addListener((observable, oldValue, newValue)
                -> StaticData.setPriventSleepHibernate(newValue));

        launchStartUpCheck.selectedProperty().addListener((observable, oldValue, newValue)
                -> StaticData.setLaunchStartUp(newValue));

        showTryIconCheck.selectedProperty().addListener((observable, oldValue, newValue)
                -> StaticData.setShowTryIcon(newValue));

    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
