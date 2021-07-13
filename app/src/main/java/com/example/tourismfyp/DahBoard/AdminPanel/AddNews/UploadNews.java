package com.example.tourismfyp.DahBoard.AdminPanel.AddNews;

public class UploadNews {
    String News;
    String loginUserEmail;
    String Name;
    String id;
    String PostDate;
    String PostTime;
    String deadlinedate;

    public UploadNews(String news, String loginUserEmail, String name, String id, String postDate, String postTime, String deadlinedate) {
        News = news;
        this.loginUserEmail = loginUserEmail;
        Name = name;
        this.id = id;
        PostDate = postDate;
        PostTime = postTime;
        this.deadlinedate = deadlinedate;
    }

    public String getNews() {
        return News;
    }

    public void setNews(String news) {
        News = news;
    }

    public String getLoginUserEmail() {
        return loginUserEmail;
    }

    public void setLoginUserEmail(String loginUserEmail) {
        this.loginUserEmail = loginUserEmail;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDeadlinedate() {
        return deadlinedate;
    }

    public void setDeadlinedate(String deadlinedate) {
        this.deadlinedate = deadlinedate;
    }
}
