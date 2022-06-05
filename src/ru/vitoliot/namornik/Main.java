package ru.vitoliot.namornik;

import ru.vitoliot.namornik.ui.AuthForm;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

public class Main {
    public static String role = null;

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/newdemo", "root", "1234");
    }

    public static void main(String[] args){

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        changeFont(new Font("Candara", Font.ITALIC, 12));
        new AuthForm();
    }


    public static void changeFont(Font font){
        Enumeration keys = UIManager.getDefaults().keys();
        while(keys.hasMoreElements()){
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource){
                UIManager.put(key, font);
            }
        }
    }
}
