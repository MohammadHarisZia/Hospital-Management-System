package Attributes;

import java.io.Serializable;

public class Doctor implements Serializable {
    private int ID;
    private String name;
    private String email;
    private String phoneNo;
    private String Category;

    public Doctor() {
    }

    public Doctor(int ID, String name, String email, String phoneNo, String category) {
        this.ID = ID;
        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
        this.Category = category;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    @Override
    public String toString() {
        return "Doctor : "+ ID +"\n" +
                "Name : " + name + "\n" +
                "Email :" + email + "\n" +
                "Phone No : " + phoneNo +
                "Category : "+Category;
    }
}
