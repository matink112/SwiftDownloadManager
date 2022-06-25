package DownloadManager.model;

import DownloadManager.App;
import DownloadManager.controller.ConfirmDownloadController;
import DownloadManager.controller.DownloadListItemController;
import DownloadManager.controller.StaticData;
import DownloadManager.controller.Utils;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class FileModel {

    private VBox vBox;
    private String fileName;
    private Date date;
    private long size;
    private String url;
    private String Category;
    private String filePath;
    private Long downloadedSize;
    private Status status;
    private String icon;
    private int segmentNum;
    private boolean isForCompleteList;
    private boolean isForInCompleteList;

    private DownloadListItemController controller;



    public FileModel(String fileName, Date date, long size, String url, String category,
                     String filePath, long downloadedSize, Status status, String icon) {

        segmentNum = StaticData.getSegmentPartDownload();

        this.setFileName(fileName);
        this.setDate(date);
        this.setSize(size);
        this.setUrl(url);
        setCategory(category);
        this.setFilePath(filePath);
        this.setDownloadedSize(downloadedSize);
        this.setStatus(status);
        this.setIcon(icon);
        StaticData.getFileModels().add(this);

        createDownloadList();
    }




    private void createDownloadList(){

        FXMLLoader loader = Utils.loadFXMLPage("DownloadListItem");

        setController(loader.getController());

        getController().initValues(fileName ,size,downloadedSize, date.toString() , status.toString(),this);

        vBox = loader.getRoot();

        AnchorPane anchorPane = (AnchorPane) vBox.getChildren().get(0);

        anchorPane.prefWidthProperty().bind(vBox.widthProperty());

        StaticData.getMainController().getAllDownloadList().getItems().add(vBox);

    }


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getDownloadedSize() {
        return downloadedSize;
    }

    public void setDownloadedSize(long downloadedSize) {
        this.downloadedSize = downloadedSize;
        if(status != null && controller != null) {
            controller.updateStatusLable(status.toString(), ConfirmDownloadController.getSizeInFormat(downloadedSize));
            copySceneToOtherList(status);
        }
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
        if(downloadedSize != null && controller != null) {
            controller.updateStatusLable(status.toString() ,
                    ConfirmDownloadController.getSizeInFormat(downloadedSize));

            copySceneToOtherList(status);
        }
    }

    private void copySceneToOtherList(Status status){

        if(!isForInCompleteList) {


            isForInCompleteList = true;
        }
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


    public DownloadListItemController getController() {
        return controller;
    }

    public void setController(DownloadListItemController controller) {
        this.controller = controller;
    }
}
