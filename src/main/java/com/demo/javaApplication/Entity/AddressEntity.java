package com.demo.javaApplication.Entity;

import com.demo.javaApplication.Shared.UserConstants;
import com.demo.javaApplication.SharedDTO.UserDTO;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "addresses")
public class AddressEntity implements Serializable {
    private static final long serialVersionUID = -4989047224841802603L;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, length = UserConstants.addressIDLength)
    private String addressID;

    @Column(nullable = false, length = 100)
    private String streetName;

    @Column(nullable = false, length = 6)
    private String pincode;

    @Column(nullable = false, length = 30)
    private String city;

    @Column(nullable = false, length = 30)
    private String country;

    @Column(nullable = false, length = 30)
    private String type;

    @ManyToOne
    @JoinColumn(name = "users_id")  //name of join column is table_colname
    private UserEntity userDetails;

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddressID() {
        return addressID;
    }

    public void setAddressID(String addressID) {
        this.addressID = addressID;
    }

    public UserEntity getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserEntity userDetails) {
        this.userDetails = userDetails;
    }
}
