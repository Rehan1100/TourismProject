package com.example.tourismfyp.Authentication;

public class Users {

    String FirstName;
    String LastName;
    String Email;
    String Password;
    String NickName;
    String Role;

    public Users(String firstName, String lastName, String email, String password, String nickName, String role) {
        FirstName = firstName;
        LastName = lastName;
        Email = email;
        Password = password;
        NickName = nickName;
        Role = role;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }
}
