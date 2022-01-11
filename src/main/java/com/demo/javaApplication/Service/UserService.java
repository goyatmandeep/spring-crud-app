package com.demo.javaApplication.Service;

import com.demo.javaApplication.SharedDTO.AddressDTO;
import com.demo.javaApplication.SharedDTO.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserDTO createUser(UserDTO userDTO);
    UserDTO getUser(String email);
    UserDTO getUserByUserID(String userID);
    UserDTO updateUser(String userID, UserDTO newDetails);
    void deleteUser(String userID);
    List<UserDTO> getUsers(int page, int limit);
}
