package com.demo.javaApplication.Controllers;

import com.demo.javaApplication.Exceptions.UserServiceException;
import com.demo.javaApplication.Models.*;
import com.demo.javaApplication.Service.AddressService;
import com.demo.javaApplication.Service.UserService;
import com.demo.javaApplication.Shared.ErrorMessages;
import com.demo.javaApplication.Shared.OperationStatus;
import com.demo.javaApplication.Shared.OperationsName;
import com.demo.javaApplication.Shared.ErrorMessages;
import com.demo.javaApplication.Shared.SecurityConstants;
import com.demo.javaApplication.SharedDTO.AddressDTO;
import com.demo.javaApplication.SharedDTO.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(SecurityConstants.SIGN_UP_URL)
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AddressService addressService;

    @GetMapping(path = "/{userID}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
                                    produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserDetailsResponseModel getUser(@PathVariable String userID){
        UserDTO userDetails = userService.getUserByUserID(userID);
        UserDetailsResponseModel returnValue = new UserDetailsResponseModel();
        BeanUtils.copyProperties(userDetails, returnValue);
        return returnValue;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
                produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserDetailsResponseModel createUser(@RequestBody UserDetailsRequestModel userDetailsRequestModel) throws Exception{

        if(userDetailsRequestModel.getFirstName().isEmpty() ||
           userDetailsRequestModel.getLastName().isEmpty()  ||
           userDetailsRequestModel.getEmail().isEmpty()     ||
           userDetailsRequestModel.getPassword().isEmpty()) {
            throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }

        ModelMapper modelMapper = new ModelMapper();
        UserDTO userDTO = modelMapper.map(userDetailsRequestModel, UserDTO.class);

        UserDTO userResponseDTO = userService.createUser(userDTO);

        return modelMapper.map(userResponseDTO, UserDetailsResponseModel.class);
    }

    @PutMapping(path = "/{userID}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
                                    produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserDetailsResponseModel updateUser(@PathVariable String userID, @RequestBody UserDetailsRequestModel userDetails){
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userDetails, userDTO);
        UserDTO returnValue = userService.updateUser(userID, userDTO);
        ModelMapper modelMapper = new ModelMapper();
        UserDetailsResponseModel returnObject = modelMapper.map(modelMapper, UserDetailsResponseModel.class);
        return returnObject;
    }

    @DeleteMapping(path = "/{userID}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
                   produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public OperationStatusModel deleteUser(@PathVariable String userID){
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(OperationsName.DELETE);
        userService.deleteUser(userID);
        returnValue.setOperationStatus(OperationStatus.SUCCESS);
        return returnValue;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<UserDetailsResponseModel> getUsers(@RequestParam(value = "page", defaultValue="0") int page,
                                                   @RequestParam(value = "limit", defaultValue="20" ) int limit){
        List<UserDetailsResponseModel> returnValue = new ArrayList<>();
        List<UserDTO> userDetails = userService.getUsers(page, limit);
        ModelMapper modelMapper = new ModelMapper();
        for(UserDTO userDetail: userDetails){
            UserDetailsResponseModel userDetailReturn = modelMapper.map(userDetail, UserDetailsResponseModel.class);
            returnValue.add(userDetailReturn);
        }
        return returnValue;
    }
    @GetMapping(path = "/{userID}/addresses", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<AddressResponseModel> getUserAddresses(@PathVariable String userID){
        List<AddressResponseModel> returnValue = new ArrayList<AddressResponseModel>();
        List<AddressDTO> userAddresses = addressService.getUserAddresses(userID);
        for(AddressDTO addressDTO: userAddresses){
            AddressResponseModel addressResponseModel = new AddressResponseModel();
            BeanUtils.copyProperties(addressDTO, addressResponseModel);
            returnValue.add(addressResponseModel);
        }
        return returnValue;
    }

    @GetMapping(path = "/{userID}/addresses/{addressID}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public AddressResponseModel getUserAddress(@PathVariable String addressID){
        AddressResponseModel returnValue = new AddressResponseModel();
        AddressDTO userAddress = addressService.getUserAddress(addressID);
        BeanUtils.copyProperties(userAddress, returnValue);
        return returnValue;
    }

    @GetMapping(path = "/email-verification",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public OperationStatusModel verifyUserEmail(@RequestParam(value = "token") String token){
        OperationStatusModel operationStatus = new OperationStatusModel(OperationsName.VERIFY_EMAIL);
        if(userService.verifyEmailToken(token))
            operationStatus.setOperationStatus(OperationStatus.SUCCESS);
        else
            operationStatus.setOperationStatus(OperationStatus.ERROR);
        return operationStatus;
    }


    @PostMapping(path = "/forgot-password", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public OperationStatusModel forgotPassword(@RequestBody ForgotPasswordRequestModel forgotPasswordRequestModel){
        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setOperationName(OperationsName.FORGOT_PASSWORD);
        operationStatusModel.setOperationStatus(OperationStatus.ERROR);
         if(userService.forgotPassword(forgotPasswordRequestModel.getEmailID())){
             operationStatusModel.setOperationStatus(OperationStatus.SUCCESS);
         }
         return operationStatusModel;
    }

    @PostMapping(path = "/reset-password", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public OperationStatusModel resetPassword(@RequestBody ResetPasswordRequestModel resetPasswordRequestModel){
        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setOperationName(OperationsName.RESET_PASSWORD);
        operationStatusModel.setOperationStatus(OperationStatus.ERROR);
        if(userService.resetPassword(resetPasswordRequestModel.getPasswordResetToken(), resetPasswordRequestModel.getPassword())){
            operationStatusModel.setOperationStatus(OperationStatus.SUCCESS);
        }
        return operationStatusModel;
    }

}
