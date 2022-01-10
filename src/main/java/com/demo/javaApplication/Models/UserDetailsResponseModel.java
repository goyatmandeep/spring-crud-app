package com.demo.javaApplication.Models;

import com.demo.javaApplication.SharedDTO.AddressDTO;

import java.util.List;

public class UserDetailsResponseModel {
    private String userID;
    private String firstName;
    private String lastName;
    private String email;
    private List<AddressResponseModel> addresses;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userId) {
        this.userID = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<AddressResponseModel> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressResponseModel> addresses) {
        this.addresses = addresses;
    }
}
