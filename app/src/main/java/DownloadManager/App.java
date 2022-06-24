package DownloadManager;

import DownloadManager.controller.MainViewController;
import DownloadManager.controller.Service;
import DownloadManager.controller.StaticData;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class App extends Application {

    private double xOffset =0;
    private double yOffset =0;

    private static Service service;

    public static Service getService() {
        return service;
    }

    public static void setService(Service service) {
        App.service = service;
    }


    @Override
    public void start(final Stage primaryStage)  {

        FXMLLoader loader = new FXMLLoader();

        URL location = getClass().getResource("/view"+ File.separator +"MainView.fxml");
        System.out.println(location);

        loader.setLocation(location);

        try{
            System.out.println("is here");
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        StackPane root = loader.getRoot();


        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });

        primaryStage.setScene(new Scene(root));

        MainViewController controller = loader.getController();

        controller.setPrimaryStage(primaryStage);

        StaticData.setMainController(controller);

        primaryStage.initStyle(StageStyle.UNDECORATED);

        primaryStage.show();

        StaticData.setPrimaryStage(primaryStage);

        Platform.setImplicitExit(false);

    }


    public static void main(String[] args) {
        launch(args);
    }
}