package DownloadManager.controller;

import DownloadManager.model.Category;

import java.io.IOException;

public class test {


    public static void main(String[] args) throws IOException {

        Category c = Category.getInstance();
        for (String s: c.getCategories())
            System.out.println(s);
    }
}
