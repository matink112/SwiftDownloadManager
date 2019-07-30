package DownloadManager.controller;

import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
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
    private JFXDatePicker startQueueTimePicker;

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
    private JFXDatePicker stopQueueTimePicker;

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


}
