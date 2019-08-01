package DownloadManager.controller;

import DownloadManager.Main;
import com.jfoenix.controls.JFXSnackbar;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class TrayIconSystem {

    private SystemTray systemTray;
    private TrayIcon trayIcon;
    private PopupMenu mainPopUpMenu;
    private ArrayList menuItems;
    MainViewController mainViewController;


    public TrayIconSystem(){


        if(SystemTray.isSupported()){

            if(StaticData.getTrayIcon() == null )
                createTray();

        }else {

            //logger System
            System.out.println("SystemTray Icon Not Supported!");
        }

    }


    private void createTray(){

        systemTray = SystemTray.getSystemTray();

        ImageIcon imageIcon = new ImageIcon(Main.class.getResource("view"+ File.separator+"trayicon.png"));
        Image image = imageIcon.getImage();


        mainPopUpMenu = new PopupMenu();


        MenuItem exitbtn = new MenuItem("Exit");
        exitbtn.addActionListener(e->closeProgram());
        exitbtn.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC
                , 12));

        MenuItem runBackgroundbtn = new MenuItem("Run In Background");
        runBackgroundbtn.addActionListener(e -> Platform.runLater(()->StaticData.getPrimaryStage().close()));
        runBackgroundbtn.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC
                , 12));


        MenuItem openbtn = new MenuItem("Open");
        openbtn.addActionListener(e -> Platform.runLater(()->StaticData.getPrimaryStage().show()));
        openbtn.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC
                , 12));

        mainPopUpMenu.add(openbtn);
        mainPopUpMenu.add(runBackgroundbtn);
        mainPopUpMenu.addSeparator();
        mainPopUpMenu.add(exitbtn);

        trayIcon = new TrayIcon(image , "Download Manager", mainPopUpMenu);

        StaticData.setTrayIcon(trayIcon);

        try {
            systemTray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }


    }

    private  void showMainStage(){
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/MainView.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Stage stage = new Stage();
        stage.setScene(loader.getRoot());

        stage.show();
    }

    private void ssa(){
        Application.launch(Main.class);
    }

    private void closeProgram(){

        Platform.exit();
        systemTray.remove(trayIcon);

    }
}
