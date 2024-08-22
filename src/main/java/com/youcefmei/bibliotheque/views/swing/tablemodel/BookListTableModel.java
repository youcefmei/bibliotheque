package com.youcefmei.bibliotheque.views.swing.tablemodel;

import com.youcefmei.bibliotheque.manage.Library;
import com.youcefmei.bibliotheque.models.Book;
import lombok.Getter;
import lombok.Setter;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class BookListTableModel extends AbstractTableModel {
    private Library library = Library.getInstance() ;
    private String[] columnNames = {"Titre", "Auteur", "Quantité"};
    private String filterStr = "";
    @Getter @Setter
    private List<Book> books ;
//    private final String[] filterFields = {"title","author","titre","auteur"};
//    private String filterField = "title";


    public BookListTableModel(String filterStr, String filterType) {
        setFilterStr(filterStr);
//        setFilterField(filterField);
        switch (filterType) {
            case "title","titre":
                this.books = library.getBooks().stream().filter(book -> book.getTitle().toLowerCase().contains(this.filterStr)).toList();
                break;
            case "author","auteur":
                this.books = library.getBooks().stream().filter(book -> book.getAuthor().toLowerCase().contains(this.filterStr)).toList();
                break;
            default:
                this.books = library.getBooks();
        }
    }


//    public String getFilterField() {
//        return filterField;
//    }

//    public void setFilterField(String filterField) throws InvalidInputException {
//        if (Arrays.asList(filterFields).contains(filterField) ){
//            this.filterField = filterField;
//        } else {
//            throw new InvalidInputException("Please enter a valid filter field." + filterFields);
//        }
//    }

    public void setFilterStr(String filterStr) {
        this.filterStr = filterStr.toLowerCase();
    }

//    public String getFilterStr() {
//        return filterStr;
//    }

    public String getColumnName(int col) {
        return columnNames[col];
    }



    @Override
    public int getRowCount() {
        return books.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }





    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Book book = books.get(rowIndex);

//        System.out.println(book.getTitle());

        switch (columnIndex) {
            case 0:
                return book.getTitle();
            case 1:
                return book.getAuthor();
            case 2:
                return book.getQuantity();
            default :
                return null ;
        }
    }
}
