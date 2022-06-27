package DownloadManager.controller;

import DownloadManager.model.Config;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.ListIterator;

public class test {


    public static void main(String[] args) throws IOException {

        Category c = Category.getInstance();
        for (String s: c.getCategories())
            System.out.println(s);
    }
}
