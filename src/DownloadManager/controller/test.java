package DownloadManager.controller;

import DownloadManager.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.html.ImageView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;

public class test {



    public static void main(String[] args) throws IOException {

        if(SystemTray.isSupported()){
            SystemTray systemTray = SystemTray.getSystemTray();

            PopupMenu popupMenu = new PopupMenu();

            ImageIcon imageIcon = new ImageIcon(Main.class.getResource("view"+File.separator+"trayicon.png"));
            Image image = imageIcon.getImage();


            TrayIcon trayIcon = new TrayIcon(image.getScaledInstance(16 ,16 , Image.SCALE_SMOOTH)
            ,"download",popupMenu);


            MenuItem menuItem = new MenuItem("Download Manager");
            MenuItem menuItem1 = new MenuItem("open");
            MenuItem menuItem2= new MenuItem("close");

            menuItem.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 15));

            popupMenu.add("aaa");
            popupMenu.add(menuItem);
            popupMenu.add(menuItem1);
            popupMenu.add(menuItem2);




            try {
                systemTray.add(trayIcon);
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }



    }

}
