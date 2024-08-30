package com.youcefmei.bibliotheque.views.javafx.controllers;

import com.youcefmei.bibliotheque.exceptions.DuplicateException;
import com.youcefmei.bibliotheque.exceptions.InvalidInputException;
import com.youcefmei.bibliotheque.manage.Library;
import com.youcefmei.bibliotheque.models.Customer;
import com.youcefmei.bibliotheque.models.Librarian;
import com.youcefmei.bibliotheque.models.Rent;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.util.converter.LocalDateStringConverter;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class RentController implements Initializable {


    @FXML
    private ComboBox<String> rentTitleAuthorMailCombo;

    @FXML
    private ComboBox<Librarian> librarianCombo;

    @FXML
    private TextField rentSearchTextField;
    @FXML
    private TextField userLastNameRegisterTextField;
    @FXML
    private TextField userFirstNameRegisterTextField;
    @FXML
    private TextField userMailRegisterTextField;

    @FXML
    private TableView rentTable;
    @FXML
    private TableColumn<Rent, String> rentBookTitleCol;
    @FXML
    private TableColumn<Rent, String> rentBookAuthorCol;
    @FXML
    private TableColumn<Rent, String> rentCustomerMailCol;
    @FXML
    private TableColumn<Rent, String> rentLibrarianIdCol;
    @FXML
    private TableColumn<Rent, LocalDate> rentDateBeginCol;
    @FXML
    private TableColumn<Rent , LocalDate> rentDateEndCol;

    private Library library = Library.getInstance();
    private List<Rent> rents =  library.getRents();
    private FilteredList<Rent> filteredRents;
    
    private Alert alertDelete = new Alert(Alert.AlertType.CONFIRMATION, "Etes-vous certains de vouloir supprimer ?");
    private Alert alertInfo = new Alert(Alert.AlertType.INFORMATION, "Veuillez selectionner un pret");

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<String> rentComboItems =
                FXCollections.observableArrayList(
                        "Titre",
                        "Auteur",
                        "Mail"
                );
        rentTitleAuthorMailCombo.setItems(rentComboItems);
        rentTitleAuthorMailCombo.getSelectionModel().selectFirst();

        initRentTable();
        populateRentTable();
    }


    @FXML
    private void handleSearchUser(KeyEvent event) {
        System.out.println(library.getCustomers());
        populateRentTable();
    }

    @FXML
    private void handleFilterSelectedUser(ActionEvent event) {
        populateRentTable();
    }

    @FXML
    private void handleClearUser(ActionEvent event) {
        userFirstNameRegisterTextField.clear();
        userLastNameRegisterTextField.clear();
        userMailRegisterTextField.clear();
        populateRentTable();
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
            populateRentTable();

        } catch (InvalidInputException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING,e.getMessage());
            alert.showAndWait();
        }

        populateRentTable();
    }

    private void initRentTable(){

        rentBookTitleCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBook().getTitle()));
        rentBookAuthorCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBook().getAuthor()));
        rentCustomerMailCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomer().getMail()));
        rentDateBeginCol.setCellValueFactory(new PropertyValueFactory<>("dateBegin"));
        rentDateEndCol.setCellValueFactory(new PropertyValueFactory<>("dateEnd"));
        rentLibrarianIdCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLibrarian().getId()));
        rentDateEndCol.setCellFactory(column -> {
            TableCell<Rent, LocalDate> cell = new TableCell<Rent, LocalDate>() {
                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty) {
                        setText(null);
                    }
                    else {
                        setText(dateFormatter.format(item));
                    }
                }
            };
            return cell;
        });

        rentDateBeginCol.setCellFactory(column -> {
            TableCell<Rent, LocalDate> cell = new TableCell<Rent, LocalDate>() {
                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty) {
                        setText(null);
                    }
                    else {
                        setText(dateFormatter.format(item));
                    }
                }
            };
            return cell;
        });



        rentBookTitleCol.setEditable(true);
        rentBookAuthorCol.setEditable(true);
        rentCustomerMailCol.setEditable(true);
        rentDateBeginCol.setEditable(true);
        rentDateEndCol.setEditable(true);
//        userLastnameCol.setEditable(true);
//        userDateRegisterCol.setEditable(true);
        rentTable.setEditable(true);

        rentBookTitleCol.setCellFactory(TextFieldTableCell.forTableColumn());
        rentBookAuthorCol.setCellFactory(TextFieldTableCell.forTableColumn());
        rentCustomerMailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        rentLibrarianIdCol.setCellFactory(TextFieldTableCell.forTableColumn());

        rentDateBeginCol.setCellFactory(TextFieldTableCell.forTableColumn( new LocalDateStringConverter()));
        rentDateEndCol.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));


//        userMailCol.setCellFactory(TextFieldTableCell.forTableColumn());
//        userFirstnameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Customer, String>>() {
//            @Override
//            public void handle(TableColumn.CellEditEvent<Customer, String> event) {
//                try {
//                    ((Customer)event.getTableView().getItems().get(
//                            event.getTablePosition().getRow())).setFirstName(event.getNewValue());
//                } catch (InvalidInputException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
//
//        userLastnameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Customer, String>>() {
//            @Override
//            public void handle(TableColumn.CellEditEvent<Customer, String> event) {
//                try {
//                    ((Customer)event.getTableView().getItems().get(
//                            event.getTablePosition().getRow())).setLastName(event.getNewValue());
//                } catch (InvalidInputException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
//
//        userMailCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Customer, String>>() {
//            @Override
//            public void handle(TableColumn.CellEditEvent<Customer, String> event) {
//                try {
//                    ((Customer)event.getTableView().getItems().get(
//                            event.getTablePosition().getRow())).setMail(event.getNewValue());
//                } catch (InvalidInputException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
    }

    private void populateRentTable() {
        ObservableList<Rent> rentTableItems =
                FXCollections.observableArrayList(
                        rents
                );
        filteredRents = new FilteredList<>(rentTableItems);
        String selectedItem = rentTitleAuthorMailCombo.getSelectionModel().getSelectedItem();

        filteredRents.setPredicate(
                new Predicate<Rent>() {
                    @Override
                    public boolean test(Rent rent) {
                        if ( selectedItem.equals("Titre")   ) {
                            return rent.getBook().getTitle().toLowerCase().contains(rentSearchTextField.getText().toLowerCase());
                        }
                        else if ( selectedItem.equals("Auteur") ) {
                            return rent.getBook().getAuthor().toLowerCase().contains(rentSearchTextField.getText().toLowerCase());
                        }
                        else{
                            return rent.getCustomer().getMail().toLowerCase().contains(rentSearchTextField.getText().toLowerCase());
                        }
                    }
                }
        );
        rentTable.setItems(filteredRents);
    }


    @FXML
    private void handleDeleteUser(ActionEvent event) {
        System.out.println(library.getCustomers());
        if (rentTable.getSelectionModel().getSelectedItem() == null){
            alertInfo.showAndWait();
        }
        else{
            alertDelete.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK){
                    rents.remove(rentTable.getSelectionModel().getSelectedItem());
                    System.out.println("ok");
                    populateRentTable();
                }
            });
        }
        System.out.println(library.getCustomers());

    }

    @FXML
    private void handleSearchRent (KeyEvent event) throws Exception {
        populateRentTable();
    }


    @FXML
    private void handleDeleteRent (ActionEvent event) throws Exception {
        System.out.println(library.getRents());
        if (rentTable.getSelectionModel().getSelectedItem() == null){
            alertInfo.showAndWait();
        }
        else{
            alertDelete.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK){
                    rents.remove(rentTable.getSelectionModel().getSelectedItem());
                    populateRentTable();
                }
            });
        }
        System.out.println(library.getRents());
    }

    @FXML
    private void handleFilterSelectedRent (ActionEvent event) throws Exception {
        populateRentTable();
    }

}
