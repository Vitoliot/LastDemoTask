package ru.vitoliot.namornik.ui;

import ru.vitoliot.namornik.entities.ProductEntity;
import ru.vitoliot.namornik.managers.ProductManager;
import ru.vitoliot.namornik.utils.BaseForm;
import ru.vitoliot.namornik.utils.DialogUtil;

import javax.swing.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

public class InsertForm extends BaseForm {
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
    private Set<String> types;

    public InsertForm(Set<String> types) {
        super(600, 800);
        setContentPane(mainPanel);
        this.types = types;
        initBoxes();
        initButtons();
        setVisible(true);
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
                ProductManager.insert(
                        new ProductEntity(
                                title,
                                typeComboBox.getSelectedItem().toString(),
                                descTextArea.getText(),
                                path,
                                cost,
                                date
                        )
                );
                DialogUtil.showInfo(this, "Успешно добавлено");
                dispose();
                new TableForm();
            } catch (SQLException ex) {
                DialogUtil.showError(this, "Ошибка выполнения запроса на добавление" + ex.getMessage());
                return;
            }
        });
    }

}
