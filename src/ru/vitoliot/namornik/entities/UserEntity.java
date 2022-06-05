package ru.vitoliot.namornik.entities;

public class UserEntity {
//    `idusers`, `Surname`, `Name`, `Patronomyc`, `login`, `pass`, `role`
    int id;
    String surname;
    String name;
    String patronomyc;
    String login;
    String pass;
    String role;

    public UserEntity(int id, String surname, String name, String patronomyc, String login, String pass, String role) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.patronomyc = patronomyc;
        this.login = login;
        this.pass = pass;
        this.role = role;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronomyc() {
        return patronomyc;
    }

    public void setPatronomyc(String patronomyc) {
        this.patronomyc = patronomyc;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", patronomyc='" + patronomyc + '\'' +
                ", login='" + login + '\'' +
                ", pass='" + pass + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
