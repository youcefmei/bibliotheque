package com.youcefmei.bibliotheque.views.swing.tablemodel;

import com.youcefmei.bibliotheque.manage.Library;
import com.youcefmei.bibliotheque.models.Customer;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class UserTableModel extends AbstractTableModel {

    private Library library = Library.getInstance();
    private String[] columnNames = {"Nom", "Prénom", "Email"};
    private String filterStr = "";
    private List<Customer> customers = library.getCustomers();
//    private String filterField = "nom";


    public UserTableModel(String filterStr,String filterType) {
        setFilterStr(filterStr);
        switch (filterType.toLowerCase()) {
            case "firstname","prénom":
                this.customers = library.getCustomers().stream().filter(customer -> customer.getFirstName().toLowerCase().contains(this.filterStr)).toList();
                break;
            case "lastname","nom":
                this.customers = library.getCustomers().stream().filter(customer -> customer.getLastName().toLowerCase().contains(this.filterStr)).toList();
                break;
            case "mail","email","e-mail":
                this.customers = library.getCustomers().stream().filter(customer -> customer.getMail().toLowerCase().contains(this.filterStr)).toList();
                break;
            default:
                this.customers = library.getCustomers();
        }
    }


    public void setFilterStr(String filterStr) {
        this.filterStr = filterStr.toLowerCase();
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public int getRowCount() {
        return this.customers.size();
    }

    @Override
    public int getColumnCount() {
        return this.columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Customer customer = this.customers.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return customer.getFirstName();
            case 1:
                return customer.getLastName();
            case 2:
                return customer.getMail();
            default:
                return null;

        }
    }
}
