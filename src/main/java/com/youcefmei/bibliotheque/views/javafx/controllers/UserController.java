package com.youcefmei.bibliotheque.views.javafx.controllers;

import com.youcefmei.bibliotheque.exceptions.DuplicateException;
import com.youcefmei.bibliotheque.exceptions.InvalidInputException;
import com.youcefmei.bibliotheque.manage.Library;
import com.youcefmei.bibliotheque.models.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class UserController implements Initializable {

    @FXML
    private ComboBox<String> userNameMailCombo;

    @FXML
    private TextField userSearchTextField,userLastNameRegisterTextField,userFirstNameRegisterTextField,userMailRegisterTextField;

    @FXML
    private TableView userTable;

    @FXML
    private TableColumn<Customer, String> userMailCol,userFirstnameCol,userLastnameCol,userDateRegisterCol;

    private Library library = Library.getInstance();
    private List<Customer> customers =  library.getCustomers();
    private FilteredList<Customer> filteredCustomers;
    private Alert alertDelete = new Alert(Alert.AlertType.CONFIRMATION, "Etes-vous certains de vouloir supprimer ?");
    private Alert alertInfo = new Alert(Alert.AlertType.INFORMATION, "Veuillez selectionner un utilisateur");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ///
        ObservableList<String> userComboItems =
                FXCollections.observableArrayList(
                        "Nom",
                        "Mail"
                );
        userNameMailCombo.setItems(userComboItems);
        userNameMailCombo.getSelectionModel().selectFirst();

        ///
        ///
        initUserTable();
        populateUserTable();
    }


    @FXML
    private void handleSearchUser(KeyEvent event) {
        System.out.println(library.getCustomers());
        populateUserTable();
    }

    @FXML
    private void handleFilterSelectedUser(ActionEvent event) {
        populateUserTable();
    }

    @FXML
    private void handleClearUser(ActionEvent event) {
        userFirstNameRegisterTextField.clear();
        userLastNameRegisterTextField.clear();
        userMailRegisterTextField.clear();
        populateUserTable();
    }

    @FXML
    private void handleRegisterUser(ActionEvent event) {
        System.out.println(library.getCustomers());
        String userFirstName = userFirstNameRegisterTextField.getText();
        String userLastName = userLastNameRegisterTextField.getText();
        String userMail = userMailRegisterTextField.getText();

        try {
            Customer customer = new Customer(userFirstName,userLastName,userMail);
            try {
                library.addCustomer(customer);
            } catch (DuplicateException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING,e.getMessage());
                alert.showAndWait();
            }
            populateUserTable();

        } catch (InvalidInputException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING,e.getMessage());
            alert.showAndWait();
        }
        populateUserTable();
    }

    private void initUserTable(){
        userFirstnameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        userLastnameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        userMailCol.setCellValueFactory(new PropertyValueFactory<>("mail"));
        userDateRegisterCol.setCellValueFactory(new PropertyValueFactory<>("dateRegister"));
        userFirstnameCol.setEditable(true);
        userLastnameCol.setEditable(true);
        userDateRegisterCol.setEditable(true);
        userTable.setEditable(true);

        userFirstnameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        userLastnameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        userMailCol.setCellFactory(TextFieldTableCell.forTableColumn());

        userFirstnameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Customer, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Customer, String> event) {
                try {
                    ((Customer)event.getTableView().getItems().get(
                            event.getTablePosition().getRow())).setFirstName(event.getNewValue());
                } catch (InvalidInputException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        userLastnameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Customer, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Customer, String> event) {
                try {
                    ((Customer)event.getTableView().getItems().get(
                            event.getTablePosition().getRow())).setLastName(event.getNewValue());
                } catch (InvalidInputException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        userMailCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Customer, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Customer, String> event) {
                try {
                    ((Customer)event.getTableView().getItems().get(
                            event.getTablePosition().getRow())).setMail(event.getNewValue());
                } catch (InvalidInputException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    private void populateUserTable() {
        ObservableList<Customer> customerTableItems =
                FXCollections.observableArrayList(
                        customers
                );
        filteredCustomers = new FilteredList<>(customerTableItems);
        String selectedItem = userNameMailCombo.getSelectionModel().getSelectedItem();

        filteredCustomers.setPredicate(
                new Predicate<Customer>() {
                    @Override
                    public boolean test(Customer customer) {
                        if ( selectedItem.equals("Nom")   ) {
                            return customer.getLastName().toLowerCase().contains(userSearchTextField.getText().toLowerCase());

                        }else{
                            return customer.getMail().toLowerCase().contains(userSearchTextField.getText().toLowerCase());
                        }
                    }
                }
        );
        userTable.setItems(filteredCustomers);
    }


    @FXML
    private void handleDeleteUser(ActionEvent event) {
        System.out.println(library.getCustomers());
        if (userTable.getSelectionModel().getSelectedItem() == null){
            alertInfo.showAndWait();
        }
        else{
            alertDelete.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK){
                    customers.remove(userTable.getSelectionModel().getSelectedItem());
                    populateUserTable();
                }
            });
        }
        System.out.println(library.getCustomers());
    }
}
