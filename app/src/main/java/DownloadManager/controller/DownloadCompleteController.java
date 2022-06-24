package DownloadManager.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DownloadCompleteController implements Initializable {


    @FXML
    private JFXButton openFolderbtn;

    @FXML
    private JFXCheckBox completePagePermissionCheckBox;

    @FXML
    private SVGPath closebtn;

    @FXML
    private JFXButton openbtn;

    @FXML
    private JFXTextField filePathField;

    @FXML
    private AnchorPane header;

    @FXML
    private JFXButton cancelbtn;

    @FXML
    private JFXTextField fileNameField;


    private Stage stage;

    private double xOffset =0;
    private double yOffset =0;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initbtn();
    }


    public void initPage(String fileName, String folderPath , Stage stage){
        fileNameField.setText(fileName);

        filePathField.setText(folderPath);

        this.stage = stage;
    }


    private void moveStageHandler(){
        header.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        header.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }

    private void initbtn(){

        closebtn.setOnMouseClicked(event -> stage.close());

        cancelbtn.setOnAction(event -> stage.close());


        openFolderbtn.setOnAction(event -> {
            Thread t = new Thread(() -> openFolder(filePathField.getText() ,""));

            t.start();
        });

        openbtn.setOnAction(event -> {
            Thread t = new Thread(() -> openFolder(filePathField.getText() , fileNameField.getText()));

            t.start();
        });

        completePagePermissionCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue)
                StaticData.setShowDownloadCompeletePermission(false);
            else
                StaticData.setShowDownloadCompeletePermission(true);
        });

        moveStageHandler();
    }



    private void openFolder(String path , String name ){


        if (name.equals("")) {
            try {
                Desktop.getDesktop().open(new File(path));
            } catch (IOException ignored) {

            }
        }
        else{
            try {
                Desktop.getDesktop().open(new File(path , name));
            } catch (IOException ignored) {

            }
        }
    }


}
