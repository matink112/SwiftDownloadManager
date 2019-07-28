package DownloadManager.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;

import java.net.URL;
import java.util.ResourceBundle;

public class DownloadListItemController implements Initializable{

    @FXML
    private SVGPath icon;

    @FXML
    private Label namelbl;

    @FXML
    private Label statuslbl;

    @FXML
    private Label datelbl;


    public void initValues(String fileName , String Date, String Status){
        namelbl.setText(fileName);
        datelbl.setText(Date);
        statuslbl.setText(Status);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }


}
