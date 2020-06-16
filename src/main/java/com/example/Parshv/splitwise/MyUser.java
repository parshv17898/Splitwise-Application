package com.example.prince.splitwise;

public class MyUser {
    public String name1,dob1,email1,phonenumber1,profilepic1;

    public MyUser() {
    }

    public MyUser(String name1, String dob1, String email1, String phonenumber1, String profilepic1) {
        this.name1 = name1;
        this.dob1 = dob1;
        this.email1 = email1;
        this.phonenumber1 = phonenumber1;
        this.profilepic1 = profilepic1;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getDob1() {
        return dob1;
    }

    public void setDob1(String dob1) {
        this.dob1 = dob1;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getPhonenumber1() {
        return phonenumber1;
    }

    public void setPhonenumber1(String phonenumber1) {
        this.phonenumber1 = phonenumber1;
    }

    public String getProfilepic1() {
        return profilepic1;
    }

    public void setProfilepic1(String profilepic1) {
        this.profilepic1 = profilepic1;
    }
}
