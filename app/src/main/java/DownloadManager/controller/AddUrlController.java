package DownloadManager.controller;

import DownloadManager.App;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
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

    @FXML private AnchorPane root;

    private double xOffset =0;
    private double yOffset =0;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        closebtn1.setOnMouseEntered(e -> MainViewController.btnHoverHandler(closebtn1,true));
        closebtn1.setOnMouseExited((event -> MainViewController.btnHoverHandler(closebtn1,false)));
        closebtn1.setOnMouseClicked(event -> {
            StaticData.getAddUrlStage().close();
            StaticData.setAddUrlStage(null);
        });


        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            StaticData.getAddUrlStage().setX(event.getScreenX() - xOffset);
            StaticData.getAddUrlStage().setY(event.getScreenY() - yOffset);
        });


        cancelbtn.setOnAction(event -> {
            StaticData.getAddUrlStage().close();
            StaticData.setAddUrlStage(null);
        });

        addbtn.setOnAction(event -> {
            msglbl.setText("");

            if(checkUrl(urlField.getText()))
                showConfirmDownloadPage();
            else
                msglbl.setText("This is not a valid url");
        });

        getContentFromClipBoard();

    }


    private boolean checkUrl(String url){

        try {
            URI url1 = new URL(url).toURI();
        } catch (MalformedURLException e) {
            return false;
        } catch (URISyntaxException e) {
            return false;
        }
        String[] a = url.split("/");

        return a.length > 3 && a[a.length - 1].matches(".*[.].+");
    }



    private void showConfirmDownloadPage(){
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/view"+ File.separator+
                "ConfirmDownload.fxml"));


        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setScene(new Scene(loader.getRoot()));

        stage.initStyle(StageStyle.UNDECORATED);

        ConfirmDownloadController controller = loader.getController();
        controller.initPage(urlField.getText() , stage);

        stage.show();

        StaticData.getAddUrlStage().close();
        StaticData.setAddUrlStage(null);
    }


    private void getContentFromClipBoard(){

        Clipboard clipboard = Clipboard.getSystemClipboard();

        System.out.println(clipboard.getString());

            String url = clipboard.getString();
            if (checkUrl(url))
                urlField.setText(url);

    }
}
