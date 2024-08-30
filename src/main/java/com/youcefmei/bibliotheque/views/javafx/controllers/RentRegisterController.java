package com.youcefmei.bibliotheque.views.javafx.controllers;

import com.youcefmei.bibliotheque.exceptions.InvalidInputException;
import com.youcefmei.bibliotheque.exceptions.NotEnoughQuantityException;
import com.youcefmei.bibliotheque.manage.Library;
import com.youcefmei.bibliotheque.models.Book;
import com.youcefmei.bibliotheque.models.Customer;
import com.youcefmei.bibliotheque.models.Librarian;
import com.youcefmei.bibliotheque.models.Rent;
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
import javafx.util.StringConverter;

import java.net.URL;
import java.security.Key;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class RentRegisterController implements Initializable {

    @FXML
    private ComboBox<Customer> mailCombo;

    @FXML
    private ComboBox<String> bookTitleAuthorCombo;

    @FXML
    private TableView<Book> bookTable;

    @FXML
    private TableColumn<Book, String> bookTitleCol,bookAuthorCol;

    @FXML
    private TextField bookSearchTextField,mailSearchTextField;

    private Library library = Library.getInstance();
    private List<Book> books =  library.getBooks();
    private FilteredList<Book> filteredBooks;
    private ObservableList<Customer> userComboItems;
    private Alert alertInfo = new Alert(Alert.AlertType.INFORMATION, "Veuillez selectionner un utilisateur");


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        ObservableList<String> bookComboItems =
                FXCollections.observableArrayList(
                        "Titre",
                        "Auteur"
                );

        bookTitleAuthorCombo.setItems(bookComboItems);
        bookTitleAuthorCombo.getSelectionModel().selectFirst();

        initUserComboItems();
        initBookTable();
        populateBookTable();
    }

    private void initUserComboItems() {
        userComboItems =
                FXCollections.observableArrayList(
                        library.getCustomers().stream().filter(
                                customer -> customer.getMail().toLowerCase().contains(mailSearchTextField.getText().toLowerCase())
                        ).toList()
                );


        mailCombo.setItems(userComboItems);
        mailCombo.getSelectionModel().selectFirst();
        mailCombo.setConverter(new StringConverter<Customer>() {
            @Override
            public String toString(Customer customer) {
                if (customer == null) {
                    return "";
                }
                else{
                    return customer.getMail();

                }
            }

            @Override
            public Customer fromString(String string) {
                return mailCombo.getItems().stream().filter(customer ->
                        customer.getMail().equals(string)).findFirst().orElse(null);
            }
        });
    }


    private void initBookTable() {
        bookTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        bookAuthorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
//        bookQuantityCol.setCellValueFactory(new PropertyValueFactory<>("quantityStr"));
        bookTitleCol.setEditable(true);
        bookAuthorCol.setEditable(true);
        bookTable.setEditable(true);

        bookTitleCol.setCellFactory(TextFieldTableCell.forTableColumn());
        bookAuthorCol.setCellFactory(TextFieldTableCell.forTableColumn());
//        bookQuantityCol.setCellFactory( TextFieldTableCell.forTableColumn());

        bookTitleCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Book, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Book, String> event) {
                try {
                    ((Book) event.getTableView().getItems().get(
                            event.getTablePosition().getRow())).setTitle(event.getNewValue());
                } catch (InvalidInputException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }


    private void populateBookTable() {
        ObservableList<Book> bookTableItems =
                FXCollections.observableArrayList(
                        books.stream().filter(book -> book.getQuantity()>0).toList()
                );
        filteredBooks = new FilteredList<>(bookTableItems);
        String selectedItem = bookTitleAuthorCombo.getSelectionModel().getSelectedItem();

        filteredBooks.setPredicate(
                new Predicate<Book>() {
                    @Override
                    public boolean test(Book book) {
                        if ( selectedItem.equals("Titre")   ) {
                            return book.getTitle().toLowerCase().contains(bookSearchTextField.getText().toLowerCase());
                        }else{
                            return book.getAuthor().toLowerCase().contains(bookSearchTextField.getText().toLowerCase());
                        }
                    }
                }
        );
        System.out.println(books);
        bookTable.setItems(filteredBooks);
    }

    @FXML
    private void handleSearchBook (KeyEvent event) throws Exception {
        populateBookTable();
    }

    @FXML
    private void handleSearchCustomer (KeyEvent event) throws Exception {
        initUserComboItems();
    }


    @FXML
    private void handleClearBook (ActionEvent event) throws Exception {
        bookSearchTextField.clear();
        mailSearchTextField.clear();
        populateBookTable();
    }

    @FXML
    private void handleRegisterRent (ActionEvent event) throws Exception {
        Customer customer = mailCombo.getSelectionModel().getSelectedItem();
        Book book = bookTable.getSelectionModel().getSelectedItem();
        Librarian librarian = library.getCurrentLibrarian();
        try{
            Rent rent = new Rent(LocalDate.now(),customer, book,librarian);
            library.getRents().add(rent);
            alertInfo.setContentText("Le pret est enregistrer");
            System.out.println(library.getRents());
        }catch (NullPointerException e){
            if (customer == null){
               alertInfo.setContentText("Veuillez selectionner un utilisateur");
            } else if (book ==null) {
                alertInfo.setContentText("Veuillez selectionner un livre");
            } else if (librarian == null) {
                alertInfo.setContentText("Veuillez selectionner une bibliothecaire");
            }

        }catch (NotEnoughQuantityException e){

            alertInfo.setContentText(e.getMessage());
        }
        alertInfo.showAndWait();


    }

    @FXML
    private void handleDeleteRent (ActionEvent event) throws Exception {

    }

    @FXML
    private void handleFilterSelectedBook (ActionEvent event) throws Exception {

        populateBookTable();
    }

    @FXML
    private void handleFilterSelectedCustomer (ActionEvent event) throws Exception {


    }
}
