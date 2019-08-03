package DownloadManager.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Arc;
import javafx.scene.shape.SVGPath;

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



    public void initPage(String url ,String name , String category ,
                         String filePath , long size){
        this.name = name;

        fileNamelbl.setText(name);

    }


}
