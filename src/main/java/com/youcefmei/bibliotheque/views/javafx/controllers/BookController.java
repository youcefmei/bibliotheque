package com.youcefmei.bibliotheque.views.javafx.controllers;

import com.youcefmei.bibliotheque.exceptions.DuplicateException;
import com.youcefmei.bibliotheque.exceptions.InvalidInputException;
import com.youcefmei.bibliotheque.manage.Library;
import com.youcefmei.bibliotheque.models.Book;
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
import javafx.util.converter.IntegerStringConverter;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class BookController implements Initializable {

    @FXML
    private ComboBox<String> bookTitleAuthorCombo;

    @FXML
    private TextField bookSearchTextField;
    @FXML
    private TextField bookTitleTextField;
    @FXML
    private TextField bookAuthorTextField;
    @FXML
    private Spinner bookQuantitySpinner;

    @FXML
    private TableView bookTable;
    @FXML
    private TableColumn<Book, String> bookTitleCol;
    @FXML
    private TableColumn<Book, String> bookAuthorCol;
    @FXML
    private TableColumn<Book, Integer> bookQuantityCol;

    private Library library = Library.getInstance();
    private List<Book> books =  library.getBooks();
    private FilteredList<Book> filteredBooks;
    private Alert alertDelete = new Alert(Alert.AlertType.CONFIRMATION, "Etes-vous certains de vouloir supprimer ?");
    private Alert alertInfo = new Alert(Alert.AlertType.INFORMATION, "Veuillez selectionner un livre");

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<String> bookComboItems =
                FXCollections.observableArrayList(
                        "Titre",
                        "Auteur"
                );
        bookTitleAuthorCombo.setItems(bookComboItems);
        bookTitleAuthorCombo.getSelectionModel().selectFirst();

        bookQuantitySpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(
                        1,
                        50
                )
        );
        initBookTable();
        populateBookTable();
    }

    @FXML
    private void handleSearchBook(KeyEvent event) {
        System.out.println(library.getCustomers());
        populateBookTable();
    }

    @FXML
    private void handleFilterSelectedBook(ActionEvent event) {
        populateBookTable();
    }

    @FXML
    private void handleClearBook(ActionEvent event) {
        bookAuthorTextField.clear();
        bookTitleTextField.clear();
        bookQuantitySpinner.getValueFactory().setValue(1);
        populateBookTable();
    }

    @FXML
    private void handleRegisterBook(ActionEvent event) {
        System.out.println(library.getCustomers());
        String bookAuthor = bookAuthorTextField.getText();
        String bookTitle = bookTitleTextField.getText();
        Integer bookQuantity = (Integer) bookQuantitySpinner.getValue();

        try {
            Book book = new Book(bookTitle, bookAuthor, bookQuantity);
            try {
                library.addBook(book);
            } catch (DuplicateException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING,e.getMessage());
                alert.showAndWait();
            }
            populateBookTable();

        } catch (InvalidInputException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING,e.getMessage());
            alert.showAndWait();
        }
        populateBookTable();
    }

    private void initBookTable(){
        bookTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        bookAuthorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
//        bookQuantityCol.setCellValueFactory(new PropertyValueFactory<>("quantityStr"));
        bookQuantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        bookTitleCol.setEditable(true);
        bookAuthorCol.setEditable(true);
        bookQuantityCol.setEditable(true);
        bookTable.setEditable(true);

        bookTitleCol.setCellFactory(TextFieldTableCell.forTableColumn());
        bookAuthorCol.setCellFactory(TextFieldTableCell.forTableColumn());
//        bookQuantityCol.setCellFactory( TextFieldTableCell.forTableColumn());
        bookQuantityCol.setCellFactory( TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        bookTitleCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Book, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Book, String> event) {
                try {
                    ((Book)event.getTableView().getItems().get(
                            event.getTablePosition().getRow())).setTitle(event.getNewValue());
                } catch (InvalidInputException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        bookAuthorCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Book, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Book, String> event) {
                try {
                    ((Book)event.getTableView().getItems().get(
                            event.getTablePosition().getRow())).setAuthor(event.getNewValue());
                } catch (InvalidInputException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        bookQuantityCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Book, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Book, Integer> event) {
                try {
                    ((Book)event.getTableView().getItems().get(
                            event.getTablePosition().getRow())).setQuantity(event.getNewValue());
                } catch (InvalidInputException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    private void populateBookTable() {
        ObservableList<Book> bookTableItems =
                FXCollections.observableArrayList(
                        books
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
    private void handleDeleteBook(ActionEvent event) {

        System.out.println(library.getCustomers());
        if (bookTable.getSelectionModel().getSelectedItem() == null){
            alertInfo.showAndWait();
        }
        else{
            alertDelete.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK){
                    books.remove(bookTable.getSelectionModel().getSelectedItem());
                    System.out.println("ok");
                    populateBookTable();
                }
            });
        }
        System.out.println(library.getCustomers());
    }

}
