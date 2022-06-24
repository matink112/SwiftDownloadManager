package DownloadManager.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Service extends Thread {

    private ArrayList<Stage> stages;
    private Thread main;

    MainViewController controller;

    FXMLLoader loader;



    public Service(Stage mainStage , MainViewController controller) {

        stages = new ArrayList<>();
        stages.add(mainStage);

        this.controller = controller;
    }





    public Thread getMain() {
        return main;
    }

    public void setMain(Thread main) {
        this.main = main;
    }

    private void run2() {
        stages.set(0 , new Stage());
        stages.get(0).setScene(new Scene(loader.getRoot()));

        stages.get(0).show();
    }
}
