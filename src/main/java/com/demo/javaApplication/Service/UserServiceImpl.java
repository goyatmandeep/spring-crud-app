package com.demo.javaApplication.Service;

import com.demo.javaApplication.Entity.UserEntity;
import com.demo.javaApplication.Exceptions.UserServiceException;
import com.demo.javaApplication.Shared.ErrorMessages;
import com.demo.javaApplication.Repository.UserRepo;
import com.demo.javaApplication.Shared.UserConstants;
import com.demo.javaApplication.Shared.Utils;
import com.demo.javaApplication.SharedDTO.AddressDTO;
import com.demo.javaApplication.SharedDTO.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static com.demo.javaApplication.Shared.UserConstants.userIDLength;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepo userRepo;

    @Autowired
    EmailService emailService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    Utils utils;

    private static final ModelMapper modelMapper = new ModelMapper();
    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());

    @Override
    public UserDTO createUser(UserDTO userDTO) {

        if(userRepo.findByEmail(userDTO.getEmail()) != null){
            LOGGER.log(Level.INFO, "Email ID already exists in the DB, aborting creation process.");
            throw new RuntimeException("Email already exists");
        }

        ModelMapper modelMapper = new ModelMapper();

        for(int i=0; i<userDTO.getAddresses().size(); i++){
            AddressDTO addressDTO = userDTO.getAddresses().get(i);
            addressDTO.setUserDetails(userDTO);
            addressDTO.setAddressID(utils.generateAddressID(UserConstants.addressIDLength));
            userDTO.getAddresses().set(i, addressDTO);
        }
        UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        userEntity.setUserID(utils.generateUserID(userIDLength));
        userEntity.setEmailVerificationToken(utils.generateEmailVerificationToken(userEntity.getUserID()));

        emailService.sendEmailVerificationCode(userEntity.getEmail(), userEntity.getEmailVerificationToken(), "localhost:8080/java-application/users/email-verification?token=");
        LOGGER.log(Level.INFO, "Saving the user data into the database.");

        UserEntity userResponse = userRepo.save(userEntity);
        UserDTO userDTOResponse = modelMapper.map(userResponse, UserDTO.class);

        return userDTOResponse;
    }

    @Override
    public UserDTO getUser(String email) {
        UserDTO returnValue = new UserDTO();
        UserEntity userDetails = userRepo.findByEmail(email);
        BeanUtils.copyProperties(userDetails, returnValue);
        return returnValue;
    }

    @Override
    public UserDTO getUserByUserID(String userID) {
        UserEntity returnInfo = userRepo.findByUserID(userID);
        if(returnInfo == null){
            throw new UsernameNotFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        UserDTO returnValue = new UserDTO();
        BeanUtils.copyProperties(returnInfo, returnValue);
        return returnValue;
    }

    @Override
    public UserDTO updateUser(String userID, UserDTO newDetails) {
        UserEntity currentInfo = userRepo.findByUserID(userID);
        if(currentInfo == null)
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        if(newDetails.getPassword() != null && !newDetails.getPassword().isEmpty()){
            currentInfo.setEncryptedPassword(bCryptPasswordEncoder.encode(newDetails.getPassword()));
        }
        if(newDetails.getFirstName() != null && !newDetails.getFirstName().isEmpty()){
            currentInfo.setFirstName(newDetails.getFirstName());
        }
        if(newDetails.getLastName() != null && !newDetails.getLastName().isEmpty()){
            currentInfo.setLastName(newDetails.getLastName());
        }

        UserEntity returnInfo = userRepo.save(currentInfo);
        UserDTO returnValue = new UserDTO();
        BeanUtils.copyProperties(returnInfo, returnValue);
        return returnValue;
    }

    @Override
    public void deleteUser(String userID) {
        UserEntity userInfo = userRepo.findByUserID(userID);
        if(userInfo == null)
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        userRepo.delete(userInfo);
    }

    @Override
    public List<UserDTO> getUsers(int page, int limit) {
        if(page > 0)
            page--;
        Pageable pageableRequest = PageRequest.of(page, limit);
        List<UserEntity> userDetails = userRepo.findAll(pageableRequest).getContent();
        List<UserDTO> returnValue = new ArrayList<>(page*limit);
        for(UserEntity userDetail: userDetails){
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(userDetail, userDTO);
            returnValue.add(userDTO);
        }
        return returnValue;
    }

    @Override  //for spring security internal use
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepo.findByEmail(email);
        if(userEntity == null){
            throw new UsernameNotFoundException(email);
        }
        return new User(email, userEntity.getEncryptedPassword(), userEntity.getEmailVerificationStatus(), true, true, true, new ArrayList<>());
    }

    @Override
    public boolean verifyEmailToken(String token) {
        UserEntity userEntity = userRepo.findByEmailVerificationToken(token);
        if(userEntity == null)
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        boolean tokenExpired = false; //utils.isTokenExpired(token); Does not require expirable code
        if(!tokenExpired){
            userEntity.setEmailVerificationToken(null);
            userEntity.setEmailVerificationStatus(true);
            userRepo.save(userEntity);
            return true;
        }
        else
            return false;
    }
}