package com.youcefmei.bibliotheque.views.swing.tablemodel;

import com.youcefmei.bibliotheque.manage.Library;
import com.youcefmei.bibliotheque.models.Rent;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class RentTableModel extends AbstractTableModel {

    private Library library = Library.getInstance();
    private List<Rent> rents = library.getRents();
    private String[] columnNames = {"Titre", "Auteur", "Utilisateur","Début","Fin","Bibliothécaire"};

    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public int getRowCount() {
        return rents.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        System.out.println(rents.size());
        Rent rent = rents.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return rent.getBook().getTitle();
            case 1:
                return rent.getBook().getAuthor();
            case 2:
                return rent.getCustomer().getMail();
            case 3:
                return rent.getDateBegin();
            case 4:
                return rent.getDateEnd();
            case 5:
                return rent.getLibrarian().getId();
            default:
                return null;
        }
    }
}
