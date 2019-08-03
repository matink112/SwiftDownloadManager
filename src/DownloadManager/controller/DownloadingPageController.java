package DownloadManager.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Arc;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

public class DownloadingPageController {

    @FXML
    private Label fileNamelbl;

    @FXML
    private HBox partProgressLayout;

    @FXML
    private SVGPath minimizebtn;

    @FXML
    private Label speedlbl;

    @FXML
    private SVGPath closebtn;

    @FXML
    private AnchorPane header;

    @FXML
    private JFXButton cancelbtn;

    @FXML
    private Label downloadedlbl;

    @FXML
    private JFXButton pausebtn;

    @FXML
    private Arc arcProgress;

    @FXML
    private Label statuslbl;

    @FXML
    private Label progressPersentlbl;


    private String name;
    private String url;
    private Stage stage;

    private FileDownloader fileDownloader;



    public void initPage(String url ,String name , String category ,
                         String filePath , long size , Stage stage){
        this.name = name;

        fileNamelbl.setText(name);

        fileDownloader = new FileDownloader(name , filePath , url , category , size , arcProgress ,
                progressPersentlbl , speedlbl,downloadedlbl , this);

        fileDownloader.start();

        this.stage = stage;

    }

    public void addProgressbar(JFXProgressBar progressBar){

        Platform.runLater(() -> {
            progressBar.setPrefHeight(6);
            partProgressLayout.getChildren().add(progressBar);
        });

    }


    public void initbtns(){

        closebtn.setOnMouseClicked(event -> stage.close());

    }

}
