package com.youcefmei.bibliotheque.views.javafx.controllers;

import com.youcefmei.bibliotheque.manage.Library;
import com.youcefmei.bibliotheque.models.Librarian;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class LibrarianController implements Initializable {
    @FXML
    private ComboBox<Librarian> librarianCombo;

    private Library library = Library.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Librarian> librarianComboItems =
                FXCollections.observableArrayList(
                        library.getLibrarians()
                );
        librarianCombo.setItems(librarianComboItems);
        librarianCombo.getSelectionModel().selectFirst();
    }


    @FXML
    private void handleSelectLibrarianCombo(ActionEvent event) {
        library.setCurrentLibrarian(librarianCombo.getSelectionModel().getSelectedItem());
    }

}
