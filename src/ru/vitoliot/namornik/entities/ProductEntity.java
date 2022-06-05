package ru.vitoliot.namornik.entities;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;

public class ProductEntity {
    int ID;
    String title;
    String type;
    String desc;
    String image;
    double cost;
    LocalDate regDate;
    ImageIcon icon;

    public ProductEntity(int ID, String title, String type, String desc, String image, double cost, LocalDate regDate) {
        this.ID = ID;
        this.title = title;
        this.type = type;
        this.desc = desc;
        this.image = image;
        this.cost = cost;
        this.regDate = regDate;

        if (image!= null && !image.isEmpty()){
            try {
                icon = new ImageIcon(ImageIO.read(ProductEntity.class.getClassLoader().getResource(image)).getScaledInstance(100,100, Image.SCALE_DEFAULT));
            } catch (Exception e) {
                try {
                    icon = new ImageIcon(ImageIO.read(ProductEntity.class.getClassLoader().getResource("picture.png")).getScaledInstance(100,100, Image.SCALE_DEFAULT));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public ProductEntity(String title, String type, String desc, String image, double cost, LocalDate regDate) {
        this.ID = -1;
        this.title = title;
        this.type = type;
        this.desc = desc;
        this.image = image;
        this.cost = cost;
        this.regDate = regDate;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public LocalDate getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDate regDate) {
        this.regDate = regDate;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }
}
