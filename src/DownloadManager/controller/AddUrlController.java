package DownloadManager.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.shape.SVGPath;

import java.net.URL;
import java.util.ResourceBundle;

public class AddUrlController implements Initializable {


    @FXML
    private SVGPath closebtn1;

    @FXML
    private JFXButton cancelbtn;

    @FXML
    private JFXTextField urlField;

    @FXML
    private JFXButton addbtn;

    @FXML
    private Label msglbl;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        closebtn1.setOnMouseEntered(e -> MainViewController.btnHoverHandler(closebtn1,true));
        closebtn1.setOnMouseExited((event -> MainViewController.btnHoverHandler(closebtn1,false)));
        closebtn1.setOnMouseClicked(event -> {
            StaticData.getAddUrlStage().close();
            StaticData.setAddUrlStage(null);
        });


        cancelbtn.setOnAction(event -> {
            StaticData.getAddUrlStage().close();
            StaticData.setAddUrlStage(null);
        });

        addbtn.setOnAction(event -> {
            if(checkUrl())
                showConfirmDownloadPage();
        });

    }


    private boolean checkUrl(){

    }


    private void showConfirmDownloadPage(){

    }
}
