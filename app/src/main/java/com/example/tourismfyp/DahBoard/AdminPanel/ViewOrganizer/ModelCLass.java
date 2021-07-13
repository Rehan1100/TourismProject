package com.example.tourismfyp.DahBoard.AdminPanel.ViewOrganizer;

public class ModelCLass {

   String name,email,image,pass;

    public ModelCLass(String name, String email, String image, String pass) {
        this.name = name;
        this.email = email;
        this.image = image;
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
