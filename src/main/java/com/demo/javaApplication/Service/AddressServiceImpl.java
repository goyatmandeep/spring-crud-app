package com.demo.javaApplication.Service;

import com.demo.javaApplication.Entity.AddressEntity;
import com.demo.javaApplication.Entity.UserEntity;
import com.demo.javaApplication.Exceptions.UserServiceException;
import com.demo.javaApplication.Models.ErrorMessages;
import com.demo.javaApplication.Repository.AddressRepo;
import com.demo.javaApplication.Repository.UserRepo;
import com.demo.javaApplication.SharedDTO.AddressDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class AddressServiceImpl implements AddressService{

    @Autowired
    UserRepo userRepo;

    @Autowired
    AddressRepo addressRepo;

    private static final ModelMapper modelMapper = new ModelMapper();
    private static final Logger LOGGER = Logger.getLogger(AddressServiceImpl.class.getName());

    @Override
    public List<AddressDTO> getUserAddresses(String userID){
        UserEntity userInfo = userRepo.findByUserID(userID);
        if(userInfo == null){
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        List<AddressEntity> addressEntities = addressRepo.findAllByUserDetails(userInfo);
        List<AddressDTO> returnValue = modelMapper.map(addressEntities,new TypeToken<List<AddressDTO>>(){}.getType());
//        for(AddressEntity addressEntity: addressEntities){
//            AddressDTO addressDTO = new AddressDTO();
//            returnValue.add(modelMapper.map(addressEntity, addressDTO.getClass()));
//        }
        return returnValue;
    }

    @Override
    public AddressDTO getUserAddress(String addressID) {
        AddressEntity addressInfo = addressRepo.findByAddressID(addressID);
        if(addressID == null)
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        AddressDTO returnValue = modelMapper.map(addressInfo, AddressDTO.class);

        return returnValue;
    }
}
