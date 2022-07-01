package DownloadManager.controller;

import DownloadManager.model.Category;
import DownloadManager.model.Config;
import DownloadManager.model.FileModel;
import DownloadManager.model.Status;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    private FileModel fileModel;
    private String size;
    private String downloadedSize;


    public void initValues(String fileName , long size , long downloadedSize, Date date, String Status , FileModel fileModel){
        namelbl.setText(fileName);
        statuslbl.setText(Status);
        this.fileModel = fileModel;

//        statuslbl.textProperty().bind(fileModel.getDownloadedSize());
//
//        SimpleIntegerProperty a = new SimpleIntegerProperty(10);
//        a.addListener();

        this.size = ConfirmDownloadController.getSizeInFormat(size);

        setDate(date);

        this.downloadedSize = ConfirmDownloadController.getSizeInFormat(downloadedSize);

        updateStatusLable(Status , this.downloadedSize);

        this.icon.setContent(Category.getInstance().getSvgIcon(fileModel.getCategory()));
    }

    private void setDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss MM/dd/yyyy");
        datelbl.setText(formatter.format(date));
    }

    public void updateStatusLable(String status , String downloadedSize ){
        Platform.runLater(()->statuslbl.setText(String.format("%s  %s / %s", status, downloadedSize, size)));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
