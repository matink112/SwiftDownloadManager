package DownloadManager.controller;

import DownloadManager.model.Category;
import DownloadManager.model.Config;
import DownloadManager.model.FileModel;
import DownloadManager.model.Status;
import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.SVGPath;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.File;
import java.io.IOException;
import java.net.*;
import java.net.Proxy.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Properties;

public class ConfirmDownloadController{


    private double xOffset =0;
    private double yOffset =0;

    String category;

    @FXML
    private AnchorPane headerTab;

    @FXML
    private JFXButton browsebtn;

    @FXML
    private JFXButton addCatbtn;

    @FXML
    private SVGPath minimizebtn;

    @FXML
    private JFXComboBox<String> categoryCombo;

    @FXML
    private Label sizelbl;

    @FXML
    private SVGPath closebtn;

    @FXML
    private JFXButton downlaterbtn;

    @FXML
    private JFXButton cancelbtn;

    @FXML
    private JFXTextField pathField;

    @FXML
    private JFXProgressBar progressbar;

    @FXML
    private JFXTextField fileNameField;

    @FXML
    private SVGPath fileIcon;


    @FXML
    private JFXButton startDownbtn;


    @FXML
    private JFXTextField urlField;


    private Stage confirmStage;


    private long sizeFile=-1;

    private String sepChar = File.separator;

    private Config config;
    private Properties setting;
    private Category catInstance;


    public void initPage(String url , Stage confirmStage){

        config = Config.getInstance();
        setting = config.properties();
        catInstance = Category.getInstance();

        progressbar.setProgress(JFXProgressBar.INDETERMINATE_PROGRESS);

        this.confirmStage = confirmStage;

        initbtn();

        movieStageHandler();

        initComponent(url);
        
    }


    private void initComponent(String url){
        urlField.setText(url);

        category = determineCategory(url);

        fileName(url, category);

        categoryCombo.setPromptText(category);

        changeFolderPath(getSavePath(category));

        setFileIcon(category);

        Thread size = new Thread(() -> {

            String s = getSizeFile(url);

            Platform.runLater(()->{
                sizelbl.setText(s);
                progressbar.setVisible(false);
                if(!sizelbl.getText().equals("Unknown")) {
                    startDownbtn.setDisable(false);
                }
            });
        });

        size.start();

        initComboBox();

    }


    private void fileName(String url, String category){
        String fileName = FileManager.getInstance().getFileNameFromUrl(url, category, config);
        fileNameField.setText(fileName);
    }


    private void setFileIcon(String category){
        fileIcon.setContent(catInstance.getSvgIcon(category));
    }


    private String getSavePath(String category){
        return FileManager.getInstance().getSavePathBaseCategory(category, config);
    }


    private String determineCategory(String url) {

        String[] splitUrl = url.split("[.]");
        String format = splitUrl[splitUrl.length - 1];

        for (String a : catInstance.getSupportedFormat("Music"))
            if (format.equals(a))
                return "Music";


        for (String a : catInstance.getSupportedFormat("Movie"))
            if (a.equals(format))
                return "Movie";

        for (String a : catInstance.getSupportedFormat("Program"))
            if (a.equals(format))
                return "Program";

        for (String a : catInstance.getSupportedFormat("Document"))
            if (a.equals(format))
                return "Document";

        for(String a : catInstance.getSupportedFormat("Compress"))
            if (a.equals(format))
                return "Compress";

        return "Other";

    }


    private String getSizeFile(String url){


        URL fileUrl = null;
        int response;
        HttpURLConnection connection = null;
        Proxy proxy= null;

        try {
            fileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {

            proxy = getProxy();

            if(proxy!=null)
                connection = (HttpURLConnection) fileUrl.openConnection((proxy));
            else
                connection = (HttpURLConnection) fileUrl.openConnection();

            response = connection.getResponseCode();

            if(response >= 200 && response <300)
                sizeFile = connection.getContentLengthLong();


            System.out.println(sizeFile);
        } catch (IOException ignored) {

        }

        return getSizeInFormat(sizeFile);
    }


    private void initComboBox(){
        for (String c : catInstance.getCategories())
            categoryCombo.getItems().add(c);


        categoryCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            changeFolderPath(String.format("%s%s%s", setting.getProperty("downloadDir"), sepChar, newValue));
        });
    }


    public static String getSizeInFormat(long s){

        if (s==-1)
            return "Unknown";
        else if (s < 1024)
            return s + " Byte";
        else if (s >= 1024 && s < 1024 * 1024)
            return String.format("%.2f", (s / (1024 * 1.0))) + " KB";
        else if (s >= 1024 * 1024 && s < Math.pow(1024, 3))
            return String.format("%.2f", s / (Math.pow(1024, 2) * 1.0)) + " MB";
        else if (s >= Math.pow(1024,3))
            return  String.format("%.2f", (s / (Math.pow(1024, 3) * 1.0))) + " GB";

        return "...";
    }


    private void initbtn(){

        minimizebtn.setOnMouseEntered(e -> MainViewController.btnHoverHandler(minimizebtn , true));
        minimizebtn.setOnMouseExited(e -> MainViewController.btnHoverHandler(minimizebtn , false));
        minimizebtn.setOnMouseClicked(e -> {
            confirmStage.setIconified(true);
        });


        closebtn.setOnMouseEntered(e -> MainViewController.btnHoverHandler(closebtn , true));
        closebtn.setOnMouseExited(e -> MainViewController.btnHoverHandler(closebtn , false));
        closebtn.setOnMouseClicked(e -> confirmStage.close());

        cancelbtn.setOnAction(event -> confirmStage.close());

        startDownbtn.setOnAction(event -> startDownload());

        downlaterbtn.setOnAction(event -> downloadLaterHandler());

        addCatbtn.setOnAction(event -> addCategoryHandler());

        browsebtn.setOnAction(event -> browsebtnHandler());
    }

    public static Proxy getProxy(){
        int port;
        Config config = Config.getInstance();
        boolean proxyPerm = Boolean.parseBoolean(config.properties().getProperty("useSocks"));
        boolean socksPerm = Boolean.parseBoolean(config.properties().getProperty("useProxy"));
        if(proxyPerm || socksPerm){
            if(proxyPerm) {
                String[] hp = config.properties().getProperty("proxyAddress").split(":");

                try {
                    port = Integer.parseInt(hp[1]);
                } catch (NumberFormatException e) {
                    return null;
                }

                return new Proxy(Type.HTTP, new InetSocketAddress(hp[0],port));
            }else {
                String[] hp = config.properties().getProperty("socksAddress").split(":");

                try {
                    port = Integer.parseInt(hp[1]);
                } catch (Exception e) {
                    return null;
                }

                return new Proxy(Type.SOCKS , new InetSocketAddress(hp[0],port));
            }
        }
        return null;
    }

    private void browsebtnHandler(){
        DirectoryChooser chooser = new DirectoryChooser();

        chooser.setTitle("Choose a path");


        chooser.setInitialDirectory(new File(pathField.getText()));

        File file = null;

        try {
            file = chooser.showDialog(confirmStage);
        }catch (Exception ignored){
        }

        if(file!=null)
            pathField.setText(file.toString());
    }


    private void addCategoryHandler(){

        FXMLLoader loader = Utils.loadFXMLPage("AddCategory");

        AddCategoryController controller = loader.getController();

        JFXPopup popup = new JFXPopup(loader.getRoot());

        controller.init(categoryCombo , popup);

        popup.show(addCatbtn);

    }


    private void startDownload(){

        FXMLLoader loader = Utils.loadFXMLPage("DownloadingPage");

        DownloadingPageController controller = loader.getController();

        Stage stage = new Stage();

        stage.initStyle(StageStyle.UNDECORATED);

        stage.setScene(new Scene(loader.getRoot()));

        confirmStage.close();

        String category = getselectedCategory();

        FileModel fileModel = getFileModel();

        FileManager.getInstance().appendFileInfo(fileModel);

        controller.initPage(urlField.getText() , fileNameField.getText(),
                 category,pathField.getText(),sizeFile, stage, fileModel);

        stage.show();
    }

    private String getselectedCategory(){
        if(categoryCombo.getSelectionModel().getSelectedItem() == null)
            return categoryCombo.getPromptText();
        else
            return categoryCombo.getSelectionModel().getSelectedItem();
    }


    private void downloadLaterHandler(){
        
    }


    private void movieStageHandler(){
        headerTab.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        headerTab.setOnMouseDragged(event -> {
            confirmStage.setX(event.getScreenX() - xOffset);
            confirmStage.setY(event.getScreenY() - yOffset);
        });
    }

    // TODO: create file model may have bug
    private FileModel getFileModel(){
        String filePath = String.format("%s%s%s", pathField.getText(), FileManager.sepChar, fileNameField.getText());
        return new FileModel(fileNameField.getText() , new Date() ,sizeFile , urlField.getText(),getselectedCategory()
                ,filePath, setting.getProperty("tempDir") ,0,
                Status.Downloading, null);
    }


    private boolean changeFolderPath(String path){

        if(!Files.exists(Paths.get(path))) {
            try {
                Files.createDirectories(Paths.get(path));
            } catch (IOException e) {
                return false;
            }
        }

        pathField.setText(path);
        return true;
    }


}
