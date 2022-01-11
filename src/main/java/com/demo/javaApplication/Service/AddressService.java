package com.demo.javaApplication.Service;

import com.demo.javaApplication.SharedDTO.AddressDTO;

import java.util.List;

public interface AddressService {
    public List<AddressDTO> getUserAddresses(String userID);
    public AddressDTO getUserAddress(String addressID);
}
