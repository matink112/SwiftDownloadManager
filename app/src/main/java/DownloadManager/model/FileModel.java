package DownloadManager.model;

import DownloadManager.App;
import DownloadManager.controller.*;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class FileModel {

    private VBox vBox;

    private String id;
    private String fileName;
    private Date date;
    private long size;
    private String url;
    private String Category;
    private String filePath;

    private String tempDir;
    private Long downloadedSize;
    private Status status;
    private String icon;
    private int segmentNum;

    private boolean isResumable;

    // TODO: two boolean useless because of status
    private boolean completed;
    private boolean isForInCompleteList;

    private DownloadListItemController controller;



    public FileModel(String fileName, Date date, long size, String url, String category,
                     String filePath, String tempDir, long downloadedSize, Status status, String id) {

        segmentNum = (int) Double.parseDouble(Config.getInstance().properties().getProperty("segmentPerDownload"));

        this.id = getId(id);
        this.tempDir = tempDir;
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

    private String getId(String uuid) {
        if (uuid == null)
            return UUID.randomUUID().toString();
        return uuid;
    }

    public static FileModel parseJson(JSONObject obj, FileManager fm) throws IOException, ParseException {
//        SimpleDateFormat formatter = new SimpleDateFormat("dow mon dd hh:mm:ss zzz yyyy");
        String fileName = (String) obj.get("name");
        Date date = new Date();
        long size = Long.parseLong((String) obj.get("size"));
        String url = (String) obj.get("url");
        String category = (String) obj.get("category");
        String filePath = (String) obj.get("filePath");
        String tempDir = (String) obj.get("tempDir");
        String statusStr = (String) obj.get("status");
        String id = (String) obj.get("tempDir");
        int segments = Integer.parseInt((String) obj.get("segments"));
        long downloadSize = calculateDownloadedSize(fm, statusStr, size, fileName, tempDir, segments);
        Status status = (downloadSize == -1)? Status.Corrupted: Status.valueOf(statusStr);

        return new FileModel(
                fileName,
                date,
                size,
                url,
                category,
                filePath,
                tempDir,
                downloadSize,
                status,
                id
        );
    }

    private static long calculateDownloadedSize(FileManager fm, String status, long size, String fileName,
                                                String tempDir, int segments) {
        try {
            return (Status.Finished == Status.valueOf(status)) ? size : fm.incompleteDownloadedSize(fileName, tempDir, segments);
        } catch (IOException e) {
            return -1;
        }
    }
    private void createDownloadList(){

        FXMLLoader loader = Utils.loadFXMLPage("DownloadListItem");

        setController(loader.getController());

        getController().initValues(fileName ,size,downloadedSize, date.toString() , status.toString(),this);

        vBox = loader.getRoot();

        AnchorPane anchorPane = (AnchorPane) vBox.getChildren().get(0);

        anchorPane.prefWidthProperty().bind(vBox.widthProperty());

        Platform.runLater(() -> {
            StaticData.getMainController().getAllDownloadList().getItems().add(vBox);
        });


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

    public int getSegmentNum() {
        return segmentNum;
    }

    public String getId() {
        return id;
    }

    public String getTempDir() {
        return tempDir;
    }

    public boolean isResumable() {
        return isResumable;
    }
}
