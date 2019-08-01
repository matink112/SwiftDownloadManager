package DownloadManager.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.shape.SVGPath;

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

    }


    private void getContentFromClipBoard(){

        Clipboard clipboard = Clipboard.getSystemClipboard();

        System.out.println(clipboard.getString());

            String url = clipboard.getString();
            if (checkUrl(url))
                urlField.setText(url);

    }
}
