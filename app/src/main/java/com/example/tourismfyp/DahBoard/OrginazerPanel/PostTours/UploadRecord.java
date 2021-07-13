package com.example.tourismfyp.DahBoard.OrginazerPanel.PostTours;

public class UploadRecord {
    String title;
    String url;
    String desc;
    String item;
    String id;
    String deadlinedate;
    String currentdate;
    String loginuser;
    String phoneNumber;
    String cnic;
    String perhead;
    String longitude;
    String latitude;
    String category;
    String Deadline;
    String city;

    public UploadRecord(String title, String url, String desc, String item, String id, String deadlinedate, String currentdate, String loginuser, String phoneNumber, String cnic, String perhead, String longitude, String latitude, String category, String deadline, String city) {
        this.title = title;
        this.url = url;
        this.desc = desc;
        this.item = item;
        this.id = id;
        this.deadlinedate = deadlinedate;
        this.currentdate = currentdate;
        this.loginuser = loginuser;
        this.phoneNumber = phoneNumber;
        this.cnic = cnic;
        this.perhead = perhead;
        this.longitude = longitude;
        this.latitude = latitude;
        this.category = category;
        Deadline = deadline;
        this.city = city;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeadlinedate() {
        return deadlinedate;
    }

    public void setDeadlinedate(String deadlinedate) {
        this.deadlinedate = deadlinedate;
    }

    public String getCurrentdate() {
        return currentdate;
    }

    public void setCurrentdate(String currentdate) {
        this.currentdate = currentdate;
    }

    public String getLoginuser() {
        return loginuser;
    }

    public void setLoginuser(String loginuser) {
        this.loginuser = loginuser;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getPerhead() {
        return perhead;
    }

    public void setPerhead(String perhead) {
        this.perhead = perhead;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDeadline() {
        return Deadline;
    }

    public void setDeadline(String deadline) {
        Deadline = deadline;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
