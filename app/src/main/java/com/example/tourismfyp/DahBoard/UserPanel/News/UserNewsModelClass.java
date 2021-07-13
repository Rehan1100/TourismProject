package com.example.tourismfyp.DahBoard.UserPanel.News;

public class UserNewsModelClass {
    String id;
    String loginuseremail;
    String name;
    String news;
    String postdate;
    String posttime;

    public UserNewsModelClass(String id, String loginuseremail, String name, String news, String postdate, String posttime) {
        this.id = id;
        this.loginuseremail = loginuseremail;
        this.name = name;
        this.news = news;
        this.postdate = postdate;
        this.posttime = posttime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginuseremail() {
        return loginuseremail;
    }

    public void setLoginuseremail(String loginuseremail) {
        this.loginuseremail = loginuseremail;
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

    public String getPostdate() {
        return postdate;
    }

    public void setPostdate(String postdate) {
        this.postdate = postdate;
    }

    public String getPosttime() {
        return posttime;
    }

    public void setPosttime(String posttime) {
        this.posttime = posttime;
    }
}
