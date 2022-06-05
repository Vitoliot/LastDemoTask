package ru.vitoliot.namornik.ui;

import ru.vitoliot.namornik.Main;
import ru.vitoliot.namornik.entities.UserEntity;
import ru.vitoliot.namornik.managers.UserManager;
import ru.vitoliot.namornik.utils.BaseForm;
import ru.vitoliot.namornik.utils.DialogUtil;

import javax.swing.*;
import java.sql.SQLException;

public class AuthForm extends BaseForm {
    private JTextField loginField;
    private JButton authButton;
    private JButton guestAuthButton;
    private JPasswordField passwordField;
    private JPanel mainPanel;

    public AuthForm() {
        super(400, 600);
        setContentPane(mainPanel);
        initButtons();
        setVisible(true);
    }

    public void initButtons(){
        guestAuthButton.addActionListener(e -> {
            dispose();
            new TableForm();
        });
        authButton.addActionListener(e -> {
            String login = loginField.getText();
            if (login.isEmpty()){
                DialogUtil.showError(this, "Поле логина пусто");
                return;
            }
            String pass = String.valueOf(passwordField.getPassword());
            if (pass.isEmpty()){
                DialogUtil.showError(this, "Поле пароля пусто");
                return;
            }
            try {
                UserEntity user = UserManager.selectOne(login);
                System.out.println(user);
                if (user != null){
                    if (user.getPass().equals(pass)){
                        Main.role=user.getRole();
                        dispose();
                        new TableForm();
                        return;
                    }
                    else{
                        DialogUtil.showError(this, "Неверный пароль");
                        return;
                    }
                }
                DialogUtil.showError(this, "Неверный логин");
                return;
            } catch (SQLException ex) {
                DialogUtil.showError(this, "Ошибка запроса авторизации" + ex.getMessage());
                return;
            }
        });
    }
}
