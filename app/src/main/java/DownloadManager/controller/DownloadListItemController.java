package DownloadManager.controller;

import DownloadManager.model.Category;
import DownloadManager.model.Config;
import DownloadManager.model.FileModel;
import DownloadManager.model.Status;
import javafx.application.Platform;
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

    private FileModel fileModel;
    private String size;
    private String downloadedSize;


    public void initValues(String fileName , long size , long downloadedSize, String Date, String Status , FileModel fileModel){
        namelbl.setText(fileName);
        statuslbl.setText(Status);
        this.fileModel = fileModel;

        this.size = ConfirmDownloadController.getSizeInFormat(size);
        datelbl.setText(Date);

        this.downloadedSize = ConfirmDownloadController.getSizeInFormat(downloadedSize);

        updateStatusLable(Status , this.downloadedSize);

        this.icon.setContent(Category.getInstance().getSvgIcon(fileModel.getCategory()));
    }

    public void updateStatusLable(String status , String downloadedSize ){
        String aa = status+"  "+downloadedSize+" / "+size;
        Platform.runLater(()->statuslbl.setText(aa));

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
