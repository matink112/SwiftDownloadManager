package DownloadManager.controller;

import DownloadManager.App;
import javafx.application.Platform;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;


public class TrayIconSystem {

    private SystemTray systemTray;
    private TrayIcon trayIcon;
    private PopupMenu mainPopUpMenu;
    private ArrayList<MenuItem> menuItems;
    MainViewController mainViewController;
    MenuItem exitbtn;


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

        URL location = Utils.getResourceLocation("image", "trayicon", "png");
        ImageIcon imageIcon = new ImageIcon(location);
        Image image = imageIcon.getImage();

        mainPopUpMenu = new PopupMenu();

        exitbtn = new MenuItem("Exit");
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
        mainPopUpMenu.add(exitbtn);

        trayIcon = new TrayIcon(image , "Download Manager", mainPopUpMenu);



        trayIcon.setImageAutoSize(true);

        StaticData.setTrayIcon(trayIcon);

        try {
            systemTray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }


    }

    private void closeProgram(){

        Platform.exit();
        systemTray.remove(trayIcon);

    }



    public void addDownloderToTryIcon(Stage downloaderStage , String name){
        mainPopUpMenu.remove(exitbtn);

        MenuItem item = new MenuItem(name);

        item.addActionListener(e -> {
            downloaderStage.show();
            mainPopUpMenu.remove(item);
        });

        item.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC
                , 12));

        mainPopUpMenu.add(item);

        mainPopUpMenu.add(exitbtn);
    }
}
