package ru.vitoliot.namornik.utils;

import javax.swing.table.AbstractTableModel;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

/**
 * Модель для таблицы с одновременной сортировкой и фильтрацией
 * @param <T>
 */
public class ExtendedTableModel<T> extends AbstractTableModel {
    private Class<T> cls;
    private String[] columnNames;
    private List<T> rows=new ArrayList<>();
    private List<T> filteredRows;
    private Predicate<T>[] filters = new Predicate[10];
    private Comparator<T> sorter=null;


    public ExtendedTableModel(Class<T> cls, String[] columnNames) {
        this.cls = cls;
        this.columnNames = columnNames;
    }

    public void filterRows(){
        filteredRows=new ArrayList<>(rows);
        for (Predicate<T> filter:filters){
            if (filter!=null){
                filteredRows.removeIf(row->!filter.test(row));
            }
        }

        if (sorter!=null){
            filteredRows.sort(sorter);
        }

        fireTableDataChanged();
        eventOnUpdate();
    }

   public void eventOnUpdate(){

   }


    @Override
    public int getRowCount() {
        return filteredRows.size();
    }

    @Override
    public int getColumnCount() {
        return cls.getDeclaredFields().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Field field = cls.getDeclaredFields()[columnIndex];
        field.setAccessible(true);
        try {
            return field.get(filteredRows.get(rowIndex));
        } catch (Exception e) {
            return "ERR";
        }
    }

    @Override
    public String getColumnName(int column) {
        return column < columnNames.length ? columnNames[column] : "столбец";
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return cls.getDeclaredFields()[columnIndex].getType();
    }

    public Class<T> getCls() {
        return cls;
    }

    public void setCls(Class<T> cls) {
        this.cls = cls;
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
        filterRows();
    }

    public List<T> getFilteredRows() {
        return filteredRows;
    }

    public void setFilteredRows(List<T> filteredRows) {
        this.filteredRows = filteredRows;
    }

    public Predicate<T>[] getFilters() {
        return filters;
    }

    public void setFilters(Predicate<T>[] filters) {
        this.filters = filters;
    }

    public Comparator<T> getSorter() {
        return sorter;
    }

    public void setSorter(Comparator<T> sorter) {
        this.sorter = sorter;
        filterRows();
    }
}
