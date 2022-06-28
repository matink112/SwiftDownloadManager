package DownloadManager.controller;

import DownloadManager.model.Category;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;

public class AddCategoryController {

    @FXML
    private JFXTextField catField;

    @FXML
    private JFXButton addbtn;

    private JFXPopup popup;
    private JFXComboBox<String> comboBox;

    public void init(JFXComboBox<String> confirmCombo , JFXPopup popup){
        comboBox = confirmCombo;

        this.popup = popup;

        addbtn.setOnAction(event -> addCategory());
    }

    private void addCategory(){
        Category categoryInstance = Category.getInstance();
        String newCategoryName = catField.getText();

        if (newCategoryName.length()>0){
            for (String a : categoryInstance.getCategories()) {
                if (a.equals(newCategoryName)) {
                    comboBox.setValue(a);
                    popup.hide();
                    return;
                }
            }

            updateCategories(newCategoryName, categoryInstance);

            popup.hide();

            return;
        }

        popup.hide();
    }

    private void updateCategories(String name, Category category){

        category.addCategory(name);

        comboBox.setValue(name);

        comboBox.getItems().add(name);

        StaticData.getMainController().addNewCategory(name);
    }



}
