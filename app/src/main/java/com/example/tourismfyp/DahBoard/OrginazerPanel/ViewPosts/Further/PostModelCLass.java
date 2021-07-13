package com.example.tourismfyp.DahBoard.OrginazerPanel.ViewPosts.Further;

public class PostModelCLass {
    String cnic;
    String currentdate ;
    String desc ;
    String id ;
    String item;
    String latitude ;
    String loginuser;
    String longitude;
    String perhead;
    String phoneNumber;
    String title ;
    String url ;
    String deadlinedate ;
    String city;
    String category;

    public PostModelCLass(String cnic, String currentdate, String desc, String id, String item, String latitude, String loginuser, String longitude, String perhead, String phoneNumber, String title, String url, String deadlinedate, String city, String category) {
        this.cnic = cnic;
        this.currentdate = currentdate;
        this.desc = desc;
        this.id = id;
        this.item = item;
        this.latitude = latitude;
        this.loginuser = loginuser;
        this.longitude = longitude;
        this.perhead = perhead;
        this.phoneNumber = phoneNumber;
        this.title = title;
        this.url = url;
        this.deadlinedate = deadlinedate;
        this.city = city;
        this.category = category;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getCurrentdate() {
        return currentdate;
    }

    public void setCurrentdate(String currentdate) {
        this.currentdate = currentdate;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLoginuser() {
        return loginuser;
    }

    public void setLoginuser(String loginuser) {
        this.loginuser = loginuser;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPerhead() {
        return perhead;
    }

    public void setPerhead(String perhead) {
        this.perhead = perhead;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDeadlinedate() {
        return deadlinedate;
    }

    public void setDeadlinedate(String deadlinedate) {
        this.deadlinedate = deadlinedate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
