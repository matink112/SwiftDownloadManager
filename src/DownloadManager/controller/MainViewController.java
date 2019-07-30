package DownloadManager.controller;

import DownloadManager.Main;
import DownloadManager.model.DownloadFile;
import DownloadManager.model.Status;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.WritableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    @FXML
    private SVGPath minimizebtn;

    @FXML
    private JFXTextField searchField;

    @FXML
    private SVGPath closebtn;

    @FXML
    private SVGPath settingbtn;

    @FXML
    private SVGPath facebookbtn;

    @FXML
    private JFXListView<VBox> completeDownloadList;

    @FXML
    private SVGPath pausebtn;

    @FXML
    private JFXListView<VBox> inCompleteDownloadList;

    @FXML
    private SVGPath twiterbtn;

    @FXML
    private JFXComboBox<String> sortComboBox;

    @FXML
    private AnchorPane root;

    @FXML
    private SVGPath instabtn;

    @FXML
    private SVGPath removebtn;

    @FXML
    private SVGPath addUrlbtn;

    @FXML
    private JFXListView<VBox> allDownloadList;

    @FXML
    private SVGPath powerbtn;

    @FXML
    private JFXListView<String> categoryListView;

    @FXML
    private SVGPath queuebtn;

    @FXML
    private SVGPath resumebtn;

    @FXML
    private Group searchGroup;

    @FXML
    private ScrollPane settingLayout;

    @FXML
    private SVGPath settingBackbtn;

    @FXML
    private AnchorPane settingDrawer;



    WritableValue<Double> searchFieldWidth;
    WritableValue<Double> settingDrawerAnchor;
    private Stage primaryStage;
    private ArrayList<DownloadFile> downloadFiles;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        downloadFiles = new ArrayList<>();

        downloadFiles.add(new DownloadFile("http://up.nafismusic.ir/Nafis/Matias%20Puumala%20-%20Last%20Light%20.mp3"
                ,5.9 , Status.Finished ,new Date()));

        initSearchField();

        initListView();

        initComboBox();

        initBtns();

        initDownloadList();

        initSettingDrawer();

    }

    //////////////private functions

    private void initBtns(){

        twiterbtn.setOnMouseEntered(e -> btnHoverHandler(twiterbtn , true));
        twiterbtn.setOnMouseExited(e -> btnHoverHandler(twiterbtn , false));
        twiterbtn.setOnMouseClicked(e -> btnClickHandler(twiterbtn));

         powerbtn.setOnMouseEntered(e -> btnHoverHandler(powerbtn , true));
        powerbtn.setOnMouseExited(e -> btnHoverHandler(powerbtn , false));
        powerbtn.setOnMouseClicked(e -> btnClickHandler(powerbtn));

        facebookbtn.setOnMouseEntered(e -> btnHoverHandler(facebookbtn , true));
        facebookbtn.setOnMouseExited(e -> btnHoverHandler(facebookbtn , false));
        facebookbtn.setOnMouseClicked(e -> btnClickHandler(facebookbtn));

        instabtn.setOnMouseEntered(e -> btnHoverHandler(instabtn , true));
        instabtn.setOnMouseExited(e -> btnHoverHandler(instabtn , false));
        instabtn.setOnMouseClicked(e ->{
            btnClickHandler(instabtn);

            try {
                new ProcessBuilder("x-www-browser", "https://www.instagram.com/_matinkhalili_/").start();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        minimizebtn.setOnMouseEntered(e -> btnHoverHandler(minimizebtn , true));
        minimizebtn.setOnMouseExited(e -> btnHoverHandler(minimizebtn , false));
        minimizebtn.setOnMouseClicked(e -> {
            btnClickHandler(minimizebtn);
            primaryStage.setIconified(true);
        });


        closebtn.setOnMouseEntered(e -> btnHoverHandler(closebtn , true));
        closebtn.setOnMouseExited(e -> btnHoverHandler(closebtn , false));
        closebtn.setOnMouseClicked(e -> {
            btnClickHandler(closebtn);
            Platform.exit();
        });

        addUrlbtn.setOnMouseEntered(e -> btnHoverHandler(addUrlbtn , true));
        addUrlbtn.setOnMouseExited(e -> btnHoverHandler(addUrlbtn , false));
        addUrlbtn.setOnMouseClicked(e -> btnClickHandler(addUrlbtn));

        removebtn.setOnMouseEntered(e -> btnHoverHandler(removebtn , true));
        removebtn.setOnMouseExited(e -> btnHoverHandler(removebtn , false));
        removebtn.setOnMouseClicked(e -> btnClickHandler(removebtn));

        pausebtn.setOnMouseEntered(e -> btnHoverHandler(pausebtn , true));
        pausebtn.setOnMouseExited(e -> btnHoverHandler(pausebtn , false));
        pausebtn.setOnMouseClicked(e -> btnClickHandler(pausebtn));

        resumebtn.setOnMouseEntered(e -> btnHoverHandler(resumebtn , true));
        resumebtn.setOnMouseExited(e -> btnHoverHandler(resumebtn , false));
        resumebtn.setOnMouseClicked(e -> btnClickHandler(resumebtn));

        queuebtn.setOnMouseEntered(e -> btnHoverHandler(queuebtn , true));
        queuebtn.setOnMouseExited(e -> btnHoverHandler(queuebtn , false));
        queuebtn.setOnMouseClicked(e -> btnClickHandler(queuebtn));

        settingbtn.setOnMouseEntered(e -> btnHoverHandler(settingbtn , true));
        settingbtn.setOnMouseExited(e -> btnHoverHandler(settingbtn , false));
        settingbtn.setOnMouseClicked(e ->{
            btnClickHandler(settingbtn);
            openOrCloseSettingDrawer(true);
        });

    }


    private void initComboBox(){
        sortComboBox.getItems().addAll("Date","Size","Name","Type");
    }


    private void initSearchField(){

        searchFieldWidth = new WritableValue<Double>() {
            @Override
            public Double getValue() {
                return searchField.getWidth();
            }

            @Override
            public void setValue(Double value) {
                searchField.setPrefWidth(value);
            }
        };


        searchGroup.setOnMouseEntered( e -> searchHover(true));


        searchGroup.setOnMouseExited(e -> searchHover(false));


        searchGroup.setOnMouseClicked(e -> openOrCloseSearchField(true));


        searchField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if(searchField.getLength()==0 && !searchField.isFocused())
                    openOrCloseSearchField(false);
            }
        });

    }


    private void openOrCloseSearchField(boolean opening){

        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(searchFieldWidth, (opening ? 180.0 : 5.0 ));
        KeyFrame keyFrame = new KeyFrame(Duration.millis(140),keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();

        if(opening){
            searchField.requestFocus();
            searchField.setMouseTransparent(false);
        }else {
            searchField.setMouseTransparent(true);
            searchField.setText("");
            root.requestFocus();
        }
    }


    private void searchHover(boolean isEnter){

        if((isEnter && !searchField.isFocused() && searchField.getLength()==0)
                || (!isEnter &&!searchField.isFocused() && searchField.getLength()==0)) {
            Timeline timeline = new Timeline();
            KeyValue keyValue = new KeyValue(searchFieldWidth, (isEnter ? 50.0 : 5.0));
            KeyFrame keyFrame = new KeyFrame(Duration.millis(100), keyValue);
            timeline.getKeyFrames().add(keyFrame);
            timeline.play();
        }

    }


    private void initListView(){
        categoryListView.getItems().addAll("All Categories" ,"Documents","Music","Program","Movies");
    }


    private void btnHoverHandler(Node node , boolean isEnter){
        double tovalue;

        if(isEnter)
            tovalue = 1.5;
        else
            tovalue = 1.0;

        ScaleTransition transition = new ScaleTransition(Duration.millis(50),node);
        transition.setByX(tovalue);
        transition.setByY(tovalue);
        transition.setToX(tovalue);
        transition.setToY(tovalue);

        transition.play();
    }


    private void btnClickHandler(Node node){
        ScaleTransition transition = new ScaleTransition(Duration.millis(40),node);

        transition.setByX(1);
        transition.setByY(1);
        transition.setToX(1);
        transition.setToY(1);

        transition.setCycleCount(2);
        transition.setAutoReverse(true);

        transition.play();

    }


    private void initDownloadList(){
        for(DownloadFile file : downloadFiles){
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("view"+ File.separator + "DownloadListItem.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            VBox vBox = loader.getRoot();

            AnchorPane anchorPane = (AnchorPane) vBox.getChildren().get(0);

            anchorPane.prefWidthProperty().bind(vBox.widthProperty());

            allDownloadList.getItems().add(vBox);

        }
    }


    private void initSettingDrawer(){

        initSettingLayout();

        settingBackbtn.setOnMouseClicked(e -> openOrCloseSettingDrawer(false));

        settingDrawerAnchor = new WritableValue<Double>() {
            @Override
            public Double getValue() {
                return root.getRightAnchor(settingDrawer);
            }

            @Override
            public void setValue(Double value) {
                root.setRightAnchor(settingDrawer , value);
            }
        };

    }


    private void initSettingLayout(){

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("view" + File.separator + "optionPage.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        settingLayout.setContent(loader.getRoot());
    }


    private void openOrCloseSettingDrawer(boolean isOpenning){

        Timeline timeline = new Timeline();

        KeyValue keyValue = new KeyValue(settingDrawerAnchor, (isOpenning ? 0.0 : -359.0));

        KeyFrame keyFrame = new KeyFrame(Duration.millis(150), keyValue);

        timeline.getKeyFrames().add(keyFrame);

        timeline.play();

    }


    public Stage getPrimaryStage() {
        return primaryStage;
    }


    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
