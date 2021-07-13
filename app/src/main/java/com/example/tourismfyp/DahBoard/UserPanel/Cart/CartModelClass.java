package com.example.tourismfyp.DahBoard.UserPanel.Cart;

public class CartModelClass {
    String Bookinguserfirstname;
    String Bookinguserlastname;
    String Bookinguseremail;
    String category;
    String cnic;
    String currentdate;
    String desc;
    String perhead;
    String latitude;
    String longitude;
    String phoneNumber;
    String url;
    String title;
    String lastDate;
    String city;
    String item;
    String owner;
    String status;
    String id;

    public CartModelClass(String bookinguserfirstname, String bookinguserlastname, String bookinguseremail, String category, String cnic, String currentdate, String desc, String perhead, String latitude, String longitude, String phoneNumber, String url, String title, String lastDate, String city, String item, String owner, String status, String id) {
        Bookinguserfirstname = bookinguserfirstname;
        Bookinguserlastname = bookinguserlastname;
        Bookinguseremail = bookinguseremail;
        this.category = category;
        this.cnic = cnic;
        this.currentdate = currentdate;
        this.desc = desc;
        this.perhead = perhead;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phoneNumber = phoneNumber;
        this.url = url;
        this.title = title;
        this.lastDate = lastDate;
        this.city = city;
        this.item = item;
        this.owner = owner;
        this.status = status;
        this.id = id;
    }

    public String getBookinguserfirstname() {
        return Bookinguserfirstname;
    }

    public void setBookinguserfirstname(String bookinguserfirstname) {
        Bookinguserfirstname = bookinguserfirstname;
    }

    public String getBookinguserlastname() {
        return Bookinguserlastname;
    }

    public void setBookinguserlastname(String bookinguserlastname) {
        Bookinguserlastname = bookinguserlastname;
    }

    public String getBookinguseremail() {
        return Bookinguseremail;
    }

    public void setBookinguseremail(String bookinguseremail) {
        Bookinguseremail = bookinguseremail;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
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

    public String getPerhead() {
        return perhead;
    }

    public void setPerhead(String perhead) {
        this.perhead = perhead;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
