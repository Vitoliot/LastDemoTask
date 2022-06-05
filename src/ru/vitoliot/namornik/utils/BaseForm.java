package ru.vitoliot.namornik.utils;

import ru.vitoliot.namornik.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class BaseForm extends JFrame {
    private String APP_TITLE = Main.role != null ? Main.role.equals("Администратор") ? "Намордник [администратор] " : Main.role.equals("Продавец") ? "Намордник [продавец] ": "Намордник [Покупатель] ": "Намордник [гость] ";
    private static Image APP_ICON = null;

    static {
        try {
            APP_ICON = ImageIO.read(BaseForm.class.getClassLoader().getResource("icon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BaseForm(int width, int height){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(width, height));
        setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2 - width/2, Toolkit.getDefaultToolkit().getScreenSize().height/2 - height/2);
        setTitle(APP_TITLE);
        if (APP_ICON!=null) setIconImage(APP_ICON);
    }
}
