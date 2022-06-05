package ru.vitoliot.namornik.ui;

import ru.vitoliot.namornik.Main;
import ru.vitoliot.namornik.entities.ProductEntity;
import ru.vitoliot.namornik.managers.ProductManager;
import ru.vitoliot.namornik.utils.BaseForm;
import ru.vitoliot.namornik.utils.DialogUtil;
import ru.vitoliot.namornik.utils.ExtendedTableModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
import java.util.function.Predicate;

public class TableForm extends BaseForm {
    private JTable productTable;
    private JTextField searchTextField;
    private JButton sortButton;
    private JComboBox typeComboBox;
    private JComboBox yearComboBox;
    private JLabel countLabel;
    private JButton breakButton;
    private JButton addButton;
    private JButton helpButton;
    private JButton authorButton;
    private JPanel mainPanel;
    private ExtendedTableModel<ProductEntity> model;
    private Set<String> types = new HashSet<>();
    private Boolean sortDirection = false;

    public TableForm() {
        super(Toolkit.getDefaultToolkit().getScreenSize().width - 200, Toolkit.getDefaultToolkit().getScreenSize().height - 200);
        setContentPane(mainPanel);
        initTable();
        initComboBoxes();
        initButtons();
        initListeners();
        setVisible(true);
    }

    public void initTable(){
        productTable.getTableHeader().setReorderingAllowed(false);
        productTable.setRowHeight(50);
        model = new ExtendedTableModel<ProductEntity>(ProductEntity.class, new String[]{
                "Номер",
                "Название",
                "Тип",
                "Описание",
                "Путь до картинки",
                "Цена",
                "Дата",
                "Картинка"
        }){
            @Override
            public void eventOnUpdate(){
                countLabel.setText(model.getFilteredRows().size() + "/" + model.getRows().size());
                if (model.getRowCount() == 0){
                    DialogUtil.showInfo(TableForm.this, "Нет записей, удовлетворяющих заданным фильтрам");
                }
            }
        };
        try {
            List<ProductEntity> list = ProductManager.selectAll();
            if (list.size() == 0 ){
                DialogUtil.showInfo(this, "Записей нет");
            }
            for (ProductEntity entity:list){
                types.add(entity.getType());
            }
            model.setRows(list);
        } catch (SQLException e) {
            DialogUtil.showError(this, "Ошибка запроса записей" + e.getMessage());
        }
        productTable.setModel(model);
        TableColumn column = productTable.getColumn("Путь до картинки");
        column.setMinWidth(0);
        column.setMaxWidth(0);
        column.setPreferredWidth(0);
        column.setWidth(0);
        model.getFilters()[0] = new Predicate<ProductEntity>() {
            @Override
            public boolean test(ProductEntity product) {
                String search = searchTextField.getText();
                return product.getTitle().toLowerCase(Locale.ROOT).startsWith(search.toLowerCase(Locale.ROOT));
            }
        };
        model.getFilters()[1] = new Predicate<ProductEntity>() {
            @Override
            public boolean test(ProductEntity product) {
                if (typeComboBox.getSelectedIndex()==0){
                    return true;
                }
                return product.getType().equals(typeComboBox.getSelectedItem().toString());
            }
        };
        model.getFilters()[2] = new Predicate<ProductEntity>() {
            @Override
            public boolean test(ProductEntity product) {
                if (yearComboBox.getSelectedIndex()==0){
                    return true;
                }
                return product.getRegDate().getYear() == Integer.parseInt(yearComboBox.getSelectedItem().toString());
            }
        };

    }

    public void initComboBoxes(){
        typeComboBox.addItem("Все типы");
        for (String type:types){
            typeComboBox.addItem(type);
        }
        yearComboBox.addItem("Все года");
        for (int i =1979; i<2023;i++){
            yearComboBox.addItem(i);
        }
    }

    public void initButtons(){
        if (Main.role != null){
            if (Main.role.equals("Администратор")){
                addButton.addActionListener(e -> {
                    dispose();
                    new InsertForm(types);
                });
                productTable.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount()==2){
                            int row = productTable.rowAtPoint(e.getPoint());
                            if (row!=-1){
                                dispose();
                                new UpdateForm(types, model.getFilteredRows().get(row));
                            }
                        }
                    }
                });
            }
            else
                addButton.setVisible(false);
        }
        else {
            addButton.setVisible(false);
        }
        breakButton.addActionListener(e -> {
            searchTextField.setText("");
            yearComboBox.setSelectedIndex(0);
            typeComboBox.setSelectedIndex(0);
            model.setSorter(null);
        });
        helpButton.addActionListener(e -> {
            DialogUtil.showInfo(this, "Редактирование - двойной клик по записи, Удаление находится в форме редактирования");
        });
        authorButton.addActionListener(e -> {DialogUtil.showInfo(this, "Автор - Панаёт Виктор, Github - Vitoliot");});
        sortButton.addActionListener(e -> {
            sortDirection=!sortDirection;
            if (sortDirection){
                model.setSorter(new Comparator<ProductEntity>() {
                    @Override
                    public int compare(ProductEntity o1, ProductEntity o2) {
                        return Double.compare(o1.getCost(), o2.getCost());
                    }
                });
            }
            else{
                model.setSorter(new Comparator<ProductEntity>() {
                    @Override
                    public int compare(ProductEntity o1, ProductEntity o2) {
                        return Double.compare(o2.getCost(), o1.getCost());
                    }
                });
            }
        });
    }

    public void initListeners(){
        typeComboBox.addActionListener(e -> {
            model.filterRows();
        });
        yearComboBox.addActionListener(e -> {
            model.filterRows();
        });
        searchTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                model.filterRows();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                model.filterRows();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                model.filterRows();
            }
        });
    }
}
