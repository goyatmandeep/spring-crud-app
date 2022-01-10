package com.demo.javaApplication.Controllers;

import com.demo.javaApplication.Exceptions.UserServiceException;
import com.demo.javaApplication.Models.ErrorMessages;
import com.demo.javaApplication.Models.OperationStatusModel;
import com.demo.javaApplication.Models.UserDetailsRequestModel;
import com.demo.javaApplication.Models.UserDetailsResponseModel;
import com.demo.javaApplication.Service.UserService;
import com.demo.javaApplication.Shared.SecurityConstants;
import com.demo.javaApplication.SharedDTO.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(SecurityConstants.SIGN_UP_URL)
public class UserController {

    @Autowired
    UserService userService;

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

        UserDetailsResponseModel userDetailsResponseModel = modelMapper.map(userResponseDTO, UserDetailsResponseModel.class);
        return userDetailsResponseModel;
    }

    @PutMapping(path = "/{userID}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
                                    produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserDetailsResponseModel updateUser(@PathVariable String userID, @RequestBody UserDetailsRequestModel userDetails){
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userDetails, userDTO);
        UserDTO returnValue = userService.updateUser(userID, userDTO);
        UserDetailsResponseModel returnObject = new UserDetailsResponseModel();
        BeanUtils.copyProperties(returnValue, returnObject);
        return returnObject;
    }

    @DeleteMapping(path = "/{userID}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
                   produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public OperationStatusModel deleteUser(@PathVariable String userID){
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName("Delete");
        userService.deleteUser(userID);
        returnValue.setOperationResult("Success");
        return returnValue;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<UserDetailsResponseModel> getUsers(@RequestParam(value = "page", defaultValue="0") int page,
                                                   @RequestParam(value = "limit", defaultValue="20" ) int limit){
        List<UserDetailsResponseModel> returnValue = new ArrayList<>();
        List<UserDTO> userDetails = userService.getUsers(page, limit);
        for(UserDTO userDetail: userDetails){
            UserDetailsResponseModel userDetailReturn = new UserDetailsResponseModel();
            BeanUtils.copyProperties(userDetail, userDetailReturn);
            returnValue.add(userDetailReturn);
        }
        return returnValue;
    }

}
