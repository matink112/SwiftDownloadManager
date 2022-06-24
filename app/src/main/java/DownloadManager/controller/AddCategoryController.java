package DownloadManager.controller;

import DownloadManager.model.Category;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class AddCategoryController {

    @FXML
    private JFXTextField catField;

    @FXML
    private JFXButton addbtn;

    private JFXPopup popup;
    private JFXComboBox<Category> comboBox;

    public void init(JFXComboBox<Category> confirmCombo , JFXPopup popup){

        comboBox = confirmCombo;

        this.popup = popup;

        addbtn.setOnAction(event -> addCategory());
    }

    private void addCategory(){
        String cat = catField.getText();

        if (cat.length()>0){
            for (Category a : StaticData.getCategories()) {
                if (a.getName().equals(cat)) {
                    popup.hide();
                    return;
                }
            }

            updateCategories(cat);

            popup.hide();

            return;
        }

        popup.hide();
    }

    private void updateCategories(String name){

        Category newCat = new Category(name);

        StaticData.getCategories().add(newCat);

        comboBox.setValue(newCat);

        comboBox.getItems().add(newCat);

        StaticData.getMainController().addNewCategory(newCat);
    }



}
