package com.example.tourismfyp.DahBoard.AdminPanel.ViewNews;

public class NewsModelCLass {

   String id,loginuserEmail,name,news,PostDate,PostTime;

    public NewsModelCLass(String id, String loginuserEmail, String name, String news, String postDate, String postTime) {
        this.id = id;
        this.loginuserEmail = loginuserEmail;
        this.name = name;
        this.news = news;
        PostDate = postDate;
        PostTime = postTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginuserEmail() {
        return loginuserEmail;
    }

    public void setLoginuserEmail(String loginuserEmail) {
        this.loginuserEmail = loginuserEmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public String getPostDate() {
        return PostDate;
    }

    public void setPostDate(String postDate) {
        PostDate = postDate;
    }

    public String getPostTime() {
        return PostTime;
    }

    public void setPostTime(String postTime) {
        PostTime = postTime;
    }
}
