package com.youcefmei.bibliotheque.views.swing.tablemodel;

import com.youcefmei.bibliotheque.manage.Library;
import com.youcefmei.bibliotheque.models.Book;
import lombok.Getter;
import lombok.Setter;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class BookRentTableModel extends AbstractTableModel {

    private Library library = Library.getInstance() ;
    @Getter
    private List<Boolean> checkedBooks = new ArrayList<>();
    @Getter @Setter
    private List<Book> books ;
    @Getter
    private List<Book> booksToRent = new ArrayList<>(); ;
    private String[] columnNames = {"Titre", "Auteur", "Quantité","Emprunter"};

    public BookRentTableModel() {
        setBooks(
                library.getBooks().stream().filter(
                        book -> book.getQuantity()>0
                ).toList()
        );
        this.getBooks().forEach(
                book -> checkedBooks.add(false)
        );
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Book book = books.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return book.getTitle();
            case 1:
                return book.getAuthor();
            case 2:
                return book.getQuantity();
            case 3:
                return checkedBooks.get(rowIndex);
            default :
                return null ;
        }
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
    public Class getColumnClass(int column) {
        return getValueAt(0, column).getClass();
    }


    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        if (col != 3) {
            return false;
        } else {
            return true;
        }
    }

    public void setValueAt(Object value, int row, int col) {
        if (col == 3 ){

            checkedBooks.set(row, !checkedBooks.get(row));
            booksToRent.clear();
            for (int i = 0; i < checkedBooks.size(); i++) {
                if (checkedBooks.get(i)) {
                    booksToRent.add(books.get(i));
                }
            }

            fireTableCellUpdated(row, col);
        }
    }


}
