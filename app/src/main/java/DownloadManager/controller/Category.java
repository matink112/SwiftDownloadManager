package DownloadManager.controller;

public class Category {

    private static Category instance;

    private String categoryDir;

    private Category() {
        categoryDir = Utils.getLocation("categories");


    }

    public static Category getInstance() {
        if (instance == null)
            instance = new Category();
        return instance;
    }

}
