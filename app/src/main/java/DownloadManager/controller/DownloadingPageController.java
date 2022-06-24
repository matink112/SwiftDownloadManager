package DownloadManager.controller;

import DownloadManager.model.FileModel;
import DownloadManager.model.Status;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Arc;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class DownloadingPageController implements Initializable {

    private double xOffset =0;
    private double yOffset =0;

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
    private String url , filePath , category;
    private long size;
    private Stage stage;
    private boolean donwloadCompelete;

    private FileDownloader fileDownloader;
    private FileModel fileModel;




    public void initPage(String url , String name , String category ,
                         String filePath , long size , Stage stage , FileModel fileModel){

        this.name = name;

        this.url = url;
        this.filePath = filePath;
        this.category = category;
        this.size = size;
        this.fileModel = fileModel;


        fileNamelbl.setText(name);

        fileDownloader = new FileDownloader(name , filePath , url , category , size , arcProgress ,
                progressPersentlbl , speedlbl,downloadedlbl , this, fileModel);
        fileDownloader.start();

        this.setStage(stage);

    }

    public void addProgressbar(JFXProgressBar progressBar){

        Platform.runLater(() -> {
            progressBar.setPrefHeight(6);
            partProgressLayout.getChildren().add(progressBar);
        });

    }


    public void initbtns(){

        closebtn.setOnMouseClicked(event -> getStage().close());

        minimizebtn.setOnMouseClicked(e-> getStage().setIconified(true));

        closebtn.setOnMouseEntered(event -> MainViewController.btnHoverHandler(closebtn,true));
        closebtn.setOnMouseExited(event -> MainViewController.btnHoverHandler(closebtn,false));

        minimizebtn.setOnMouseEntered(event -> MainViewController.btnHoverHandler(minimizebtn , true));
        minimizebtn.setOnMouseExited(event -> MainViewController.btnHoverHandler(minimizebtn,false));

        cancelbtn.setOnAction(event -> cancelbtnHandler());

        pausebtn.setOnAction(event -> pausBtnHandler());
    }

    private void cancelbtnHandler(){
        fileDownloader.stopDownload();

        fileDownloader = null;

        System.gc();

        getStage().close();
    }


    private void pausBtnHandler(){
        fileDownloader.stopDownload();

        pausebtn.setText("Resume");

        pausebtn.setOnAction(event ->  resumeBtnHandler());

        fileModel.setStatus(Status.Pause);
    }

    private void resumeBtnHandler(){
        fileDownloader = new FileDownloader(name , filePath , url , category , size , arcProgress ,
                progressPersentlbl , speedlbl,downloadedlbl , this,fileModel);

        fileDownloader.start();

        pausebtn.setText("Pause");

        pausebtn.setOnAction(event -> pausBtnHandler());

        fileModel.setStatus(Status.Downloading);
    }


    public void deleteProgressIfExist(){
        if(partProgressLayout.getChildren().size()>0)
            Platform.runLater(()->partProgressLayout.getChildren().clear());

    }


    private void moveStageHandler(){
        header.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        header.setOnMouseDragged(event -> {
            getStage().setX(event.getScreenX() - xOffset);
            getStage().setY(event.getScreenY() - yOffset);
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initbtns();

        moveStageHandler();
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
