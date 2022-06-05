package ru.vitoliot.namornik.ui;

import ru.vitoliot.namornik.entities.ProductEntity;
import ru.vitoliot.namornik.managers.ProductManager;
import ru.vitoliot.namornik.utils.BaseForm;
import ru.vitoliot.namornik.utils.DialogUtil;

import javax.swing.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Set;

public class UpdateForm extends BaseForm {
    private JTextField titleTextField;
    private JTextField pathTextField;
    private JComboBox typeComboBox;
    private JTextArea descTextArea;
    private JTextField costTextField;
    private JComboBox yearComboBox;
    private JComboBox dayComboBox;
    private JComboBox monthComboBox;
    private JButton addButton;
    private JButton backButton;
    private JPanel mainPanel;
    private JTextField idTextField;
    private JButton deleteButton;
    private final Set<String> types;
    private final ProductEntity product;

    public UpdateForm(Set<String> types, ProductEntity product) {
        super(600, 800);
        setContentPane(mainPanel);
        this.types = types;
        this.product = product;
        initBoxes();
        initButtons();
        initFields();
        setVisible(true);
    }

    private void initFields(){
        idTextField.setText(String.valueOf(product.getID()));
        idTextField.setEditable(false);
        titleTextField.setText(product.getTitle());
        descTextArea.setText(product.getDesc());
        pathTextField.setText(product.getImage());
        costTextField.setText(String.valueOf(product.getCost()));
        typeComboBox.setSelectedItem(product.getType());
        yearComboBox.setSelectedItem(product.getRegDate().getYear());
        monthComboBox.setSelectedItem(product.getRegDate().getMonth());
        dayComboBox.setSelectedItem(product.getRegDate().getDayOfMonth());
    }

    private void initBoxes(){
        for (String type:types){
            typeComboBox.addItem(type);
        }

        for (int i =1; i<32;i++){
            dayComboBox.addItem(i);
        }
        for (int i =1; i<13;i++){
            monthComboBox.addItem(i);
        }
        for (int i =1979; i<2023;i++){
            yearComboBox.addItem(i);
        }
    }

    private void initButtons(){
        backButton.addActionListener(e -> {
            dispose();
            new TableForm();
        });
        deleteButton.addActionListener(e -> {
            try {
                int r = ProductManager.delete(product.getID());
                DialogUtil.showInfo(this, "Успешно удалено. Кол-во удалённых записей " + r);
                dispose();
                new TableForm();
            } catch (SQLException ex) {
                DialogUtil.showError(this, "Ошибка выполнения запроса на удаление" + ex.getMessage());
            }
        });
        addButton.addActionListener(e -> {
            String title = titleTextField.getText();
            if (title.isEmpty() || title.length() > 100){
                DialogUtil.showError(this, "Поле названия не заполнено, либо переполнено (>100)");
                return;
            }
            String path = pathTextField.getText();
            if (path.length()>100){
                DialogUtil.showError(this, "Поле картинки переполнено (>100)");
                return;
            }
            double cost = -1;
            try {
                cost = Double.parseDouble(costTextField.getText());
            }
            catch (Exception exception){
                DialogUtil.showError(this, "Ошибка заполнения цены");
                return;
            }
            if (cost<0){
                DialogUtil.showError(this, "Ошибка заполнения цены");
                return;
            }
            LocalDate date = null;
            try {
                date = LocalDate.of(Integer.parseInt(yearComboBox.getSelectedItem().toString()), Integer.parseInt(monthComboBox.getSelectedItem().toString()), Integer.parseInt(dayComboBox.getSelectedItem().toString()));
            }
            catch (Exception ex){
                DialogUtil.showError(this, "Ошибка заполнения даты");
                return;
            }
            try {
                ProductManager.update(
                        new ProductEntity(
                                product.getID(),
                                title,
                                typeComboBox.getSelectedItem().toString(),
                                descTextArea.getText(),
                                path,
                                cost,
                                date
                        )
                );
                DialogUtil.showInfo(this, "Успешно отредактировано");
                dispose();
                new TableForm();
            } catch (SQLException ex) {
                DialogUtil.showError(this, "Ошибка выполнения запроса на редактирование" + ex.getMessage());
                return;
            }
        });
    }

}
